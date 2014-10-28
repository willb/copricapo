import sbt._
import sbt.Keys._

object CopricapoBuild extends Build {

  lazy val copricapo = Project(
    id = "copricapo",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "copricapo",
      organization := "com.freevariable",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.4"
      // add other settings here
    )
  )
}
