name := "DictionaryConverter"

organization := "ie.tcd.slscs.itut"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.2"

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

javaOptions += "-Xmx1G"

mainClass in (Compile, run) := Some("ie.tcd.slscs.itut.DictionaryConverter.dix.Mapper")

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

libraryDependencies ++= {
  	Seq(
			"org.scalatest" % "scalatest_2.10" % "2.0" % Test,
			"com.novocode" % "junit-interface" % "0.11" % Test,
			"junit" % "junit" % "4.12" % Test
  	)
}
