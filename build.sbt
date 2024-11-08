name := "scala-class"

version := "1.0"

scalaVersion := "2.11.12"

libraryDependencies ++= List(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scala-lang" % "scala-compiler" % scalaVersion.value,
  "joda-time" % "joda-time" % "2.13.0",
  "org.joda" % "joda-convert" % "3.0.1",
  "io.circe" %% "circe-core" % "0.11.2",
  "io.circe" %% "circe-generic" % "0.11.2",
  "io.circe" %% "circe-parser" % "0.11.2",
  "com.typesafe.akka" %% "akka-http" % "10.0.4",
  "org.scalatest" %% "scalatest" % "2.2.6"
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xlog-free-types", "-Xlog-free-terms")

addCommandAlias("go", "~ testOnly HandsOnScala")
addCommandAlias("bonus", "~ testOnly HandsOnBonus")
