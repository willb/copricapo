package com.freevariable.copricapo

import scala.slick.direct._
import scala.slick.direct.AnnotationMapper._
import scala.slick.driver.PostgresDriver
import scala.slick.driver.PostgresDriver.simple._
import scala.reflect.runtime.universe
import scala.slick.jdbc.StaticQuery.interpolation

import com.freevariable.copricapo.schema._

trait JsonConveniences {
  object parseJSON {
    import org.json4s._
    import org.json4s.jackson.JsonMethods._

    def apply(s: String) = parse(s)
  }
  
  object renderJSON {
    import org.json4s._
    import org.json4s.jackson.JsonMethods._
    
    def apply[T](v: T)(implicit conversion: T => JValue) = compact(render(v))
  }
}

trait MessageTransforming {
  import org.json4s._
  import org.json4s.JsonDSL._
  import org.json4s.jackson.JsonMethods._
  
  def renameBranches(msg: JValue) = {
    msg transformField {
      case JField("branches", v @ JArray(JString(_)::_)) => ("pkg_branches", v)
      case JField("branches", v @ JArray(JObject(_)::_)) => ("git_branches", v)
    }
  }
  
  def transformObject(record: JValue) = {
    record transformField {
      case JField("_msg", msg) => ("_msg", renameBranches(msg))
    }
  }
}

object QueryDbImporter extends MessageTransforming with JsonConveniences {
  object JSONImplicits {
    import org.json4s._
    import org.json4s.ext._
    import org.json4s.JsonDSL._
    import org.joda.time.DateTime
    
    private def tupleIfPresent[T](name: String, v: Option[T])
      (implicit cnv: T => JValue): JObject =
      v.map(some => (name -> some) ~ JObject()) getOrElse JObject()
    
    implicit def message2json(m: Tables.MessagesRow) = {
      ("id" -> m.id) ~
      ("i" -> m.i) ~
      ("timestamp" -> (new DateTime(m.timestamp.getTime()).toString)) ~
      tupleIfPresent("topic", m.topic) ~
      tupleIfPresent("category", m.category) ~
      ("msg" -> renameBranches(parseJSON(m._Msg))) ~
      tupleIfPresent("msg_id", m.msgId) ~
      tupleIfPresent("source_name", m.sourceName) ~
      tupleIfPresent("source_version", m.sourceVersion)
    }
  }
  
  def apply(dburl: String, output_dir: String, limit: Int = 0) {
    import JSONImplicits._
    
    new java.io.File(output_dir).mkdir()
    
    Database.forURL(dburl, driver="org.postgresql.Driver") withSession { implicit session =>
      val messages = TableQuery[Tables.Messages].elements
      
      (if (limit == 0) messages else messages.take(limit)) foreach { m =>
        val id = m.id
        val outputFile = s"$output_dir/$id.json"
        println(s"rendering to $outputFile")
        val pw = new java.io.PrintWriter(new java.io.File(outputFile))
        pw.println(renderJSON(m))
        pw.close
      }
    }
  }
}

object ObjectDbImporter extends MessageTransforming with JsonConveniences {  
  import scala.slick.driver.JdbcDriver.backend.Database
  import Database.dynamicSession
  import scala.slick.jdbc.{GetResult, StaticQuery => Q}
  import Q.interpolation
  
  case class JsonString(jobject: String) {}
  
  case class FoldState(pw: java.io.PrintWriter, index: Int, objectsLeft: Int, objectsPerFile: Int, outputDir: String) {
    def update(record: String) = {
      pw.println(record)
      
      if (objectsLeft == 0) {
        pw.close ; this.copy(pw = FoldState.writerForFile(outputDir, index + 1), index = index+1, objectsLeft = objectsPerFile)
      } else {
        this.copy(objectsLeft=objectsLeft - 1)
      }
    }
  }
  
  object FoldState {
    def initial(outputDir: String, objectsPerFile: Int) = {
      FoldState(writerForFile(outputDir, 0), 0, objectsPerFile, objectsPerFile, outputDir)
    }
    
    def writerForFile(outputDir: String, index: Int) = {
      val outputFile = "%s/%020d.json".format(outputDir, index)
      new java.io.PrintWriter(new java.io.File(outputFile))
    }
  }
  
  def apply(dburl: String, output_dir: String, limit: Int = 0, recordsPerFile: Int = 10000) {
    val fs = FoldState.initial(output_dir, recordsPerFile)
    Database.forURL(dburl, driver="org.postgresql.Driver") withDynSession {
      val messages = Q.queryNA[String]("""
        select cast(row_to_json(data) as varchar) as jobject 
        from (
          select id, i, timestamp, topic, cast(_msg as json), 
                 category, source_name, source_version, msg_id
          from messages) 
        as data""").elements
        
      (if (limit == 0) messages else messages.take(limit)).foldLeft(fs) { (fs, objString) =>
        fs.update(renderJSON(transformObject(parseJSON(objString))))
      }
    }
  }
}