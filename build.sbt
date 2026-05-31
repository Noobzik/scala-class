name := "scala-class"

version := "1.0"

scalaVersion := "2.13.18"

libraryDependencies ++= List(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scala-lang" % "scala-compiler" % scalaVersion.value,
  "joda-time" % "joda-time" % "2.14.2",
  "org.joda" % "joda-convert" % "3.0.1",
  "io.circe" %% "circe-core" % "0.14.9",
  "io.circe" %% "circe-generic" % "0.14.9",
  "io.circe" %% "circe-parser" % "0.14.9",
  "com.typesafe.akka" %% "akka-http" % "10.4.0",
  "org.scalatest" %% "scalatest" % "3.2.20"
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xlog-free-types", "-Xlog-free-terms")

addCommandAlias("go", "~ testOnly HandsOnScala")
addCommandAlias("bonus", "~ testOnly HandsOnBonus")
