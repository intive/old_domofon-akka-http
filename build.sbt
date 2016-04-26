import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import scalariform.formatter.preferences._

cancelable in Global := true

val akkaVersion = "2.4.4"
val scalatestVersion = "3.0.0-M15"
val scalacheckVersion = "1.13.0"

lazy val `domofon-akka-http` =
  (project in file("."))
    .disablePlugins(SbtScalariform)
    .enablePlugins(RevolverPlugin, JavaAppPackaging)
    .settings(
      organization := "domofon",
      name := "domofon-akka-http",
      version := "0.1.0",
      description := "Sample project to bootstrap Domofon API Server implementation using akka-http",
      homepage := Some(url("https://github.com/blstream/domofon-akka-http")),
      scalaVersion := "2.11.8",
      crossScalaVersions := Seq("2.11.8")
    )
    .settings(
      resolvers += Resolver.bintrayRepo("lustefaniak", "domofon"),
      libraryDependencies ++= Seq(
        "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
        "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion,
        "de.heikoseeberger" %% "akka-sse" % "1.7.2",
        "com.github.scopt" %% "scopt" % "3.4.0",
        "ch.megard" %% "akka-http-cors" % "0.1.1",
        "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % "test",
        "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion % "test",
        "com.blstream.domofon" %% "tck" % "0.5.1" % "test"
      )
    )
    .settings(SbtScalariform.defaultScalariformSettings)
    .settings(
      updateOptions := updateOptions.value.withCachedResolution(true),
      ScalariformKeys.preferences := ScalariformKeys.preferences.value
        .setPreference(AlignSingleLineCaseStatements, true)
        .setPreference(SpacesAroundMultiImports, false)
        .setPreference(DoubleIndentClassDeclaration, true)
    )
    .settings(
      dockerBaseImage := "anapsix/alpine-java:jre8",
      dockerUpdateLatest := true
    )

addCommandAlias("formatAll", ";scalariformFormat;test:scalariformFormat")
addCommandAlias("compileAll", "compile")
addCommandAlias("testWithCoverage", ";clean;coverage;test;coverageReport;coverageOff")
