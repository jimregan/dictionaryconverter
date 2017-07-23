name := "DictionaryConverter"

organization := "ie.tcd.slscs.itut"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.2"

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

libraryDependencies ++= {
  	Seq(
  	    "org.scalatest" % "scalatest_2.10" % "2.0" % Test,
            "junit" % "junit" % "4.12" % Test
  	)
}
