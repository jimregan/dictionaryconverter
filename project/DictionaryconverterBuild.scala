import sbt._
import sbt.Keys._

object DictionaryconverterBuild extends Build {

  lazy val dictionaryconverter = Project(
    id = "dictionaryconverter",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "DictionaryConverter",
      organization := "ie.tcd.slscs.itut",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.2"
      // add other settings here
    )
  )
}
