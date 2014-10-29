import sbt._
import sbt.Keys._

object CopricapoBuild extends Build {
  val VERSION = "0.1-SNAPSHOT"
  val SCALA_VERSION = "2.10.4"
  
  lazy val copricapo = Project(
    id = "copricapo",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "copricapo",
      organization := "com.freevariable",
      version := VERSION,
      scalaVersion := SCALA_VERSION,
      libraryDependencies ++= Seq(
        "com.typesafe.slick" %% "slick" % "2.0.2",
        "org.postgresql" % "postgresql" % "9.3-1102-jdbc41",
        "org.slf4j" % "slf4j-nop" % "1.6.4",
        "org.scalatest" %% "scalatest" % "2.0" % "test",
        "org.scala-lang" % "scala-reflect" % SCALA_VERSION
      )
    )
  )
}
