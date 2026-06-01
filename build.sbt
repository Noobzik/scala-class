ThisBuild / name := "scala-class"
ThisBuild / scalaVersion := "2.13.18"
ThisBuild / version := "1.0"
Test / testOptions += Tests.Argument(
  TestFrameworks.ScalaTest,
  "-oNCXELOPQRM"
)
lazy val AkkaVersion = "2.7.0"

lazy val macros = (project in file("macros"))
  .settings(
    name := "scala-class-macros",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scala-lang" % "scala-compiler" % scalaVersion.value
    ),
    publish / skip := true
  )

lazy val root = (project in file("."))
  .dependsOn(macros)
  .settings(
    name := "scala-class",
    libraryDependencies ++= List(
      "joda-time" % "joda-time" % "2.14.2",
      "org.joda" % "joda-convert" % "3.0.1",
      "io.circe" %% "circe-core" % "0.14.9",
      "io.circe" %% "circe-generic" % "0.14.9",
      "io.circe" %% "circe-parser" % "0.14.9",
      "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
      "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
      "com.typesafe.akka" %% "akka-http" % "10.4.0",
      "org.scalatest" %% "scalatest" % "3.2.20"
    ),
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-feature",
      "-Xlog-free-types",
      "-Xlog-free-terms"
    ),
    addCommandAlias("go", "~ testOnly HandsOnScala"),
    addCommandAlias("bonus", "~ testOnly HandsOnBonus")
  )
