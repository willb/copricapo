package com.freevariable.copricapo

import scala.slick.direct._
import scala.slick.direct.AnnotationMapper._
import scala.slick.driver.PostgresDriver
import scala.slick.driver.PostgresDriver.simple._
import scala.reflect.runtime.universe
import scala.slick.jdbc.StaticQuery.interpolation

import com.freevariable.copricapo.schema._

object Importer {
  import org.json4s._
  import org.json4s.ext._
  import org.json4s.JsonDSL._
  import org.joda.time.DateTime
  import org.json4s.jackson.JsonMethods.{parse, render, compact}
  
  object JSONImplicits {
    private def tupleIfPresent[T](name: String, v: Option[T])
      (implicit cnv: T => JValue): JObject =
      v.map(some => (name -> some) ~ JObject()) getOrElse JObject()
    
    implicit def message2json(m: Tables.MessagesRow) = {
      ("id" -> m.id) ~
      ("i" -> m.i) ~
      ("timestamp" -> (new DateTime(m.timestamp.getTime()).toString)) ~
      tupleIfPresent("topic", m.topic) ~
      tupleIfPresent("category", m.category) ~
      ("msg" -> parseJSON(m._Msg)) ~
      tupleIfPresent("msg_id", m.msgId) ~
      tupleIfPresent("source_name", m.sourceName) ~
      tupleIfPresent("source_version", m.sourceVersion)
    }
  }
  
  def parseJSON(s: String) = parse(s)
  def renderJSON[T](v: T)(implicit c: T => JValue) = compact(render(v))
  
  def apply(dburl: String, output_dir: String, limit: Int = 0) {
    import JSONImplicits._
    
    new java.io.File(output_dir).mkdir()
    
    Database.forURL(dburl, driver="org.postgresql.Driver") withSession { implicit session =>
      val messages = TableQuery[Tables.Messages]
      
      (if (limit == 0) messages else messages.take(limit)).elements foreach { m =>
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