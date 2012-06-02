import sbt._
import Keys._
import com.github.siasia.WebPlugin._
import de.johoop.jacoco4sbt._
import JacocoPlugin._

object BuildSettings {
  val buildOrganization = "vaadin.scala"
  val buildName = "Scaladin"
  val buildVersion = "2.0.0-SNAPSHOT"
  val buildScalaVersion = "2.9.1"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := buildOrganization,
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-encoding", "UTF-8"),
    autoScalaLibrary := true)

  var manifestAttributes = Seq(
    Package.ManifestAttributes("Implementation-Title" -> buildName),
    Package.ManifestAttributes("Implementation-Version" -> buildVersion),
    Package.ManifestAttributes("Vaadin-Package-Version" -> "1"),
    Package.ManifestAttributes("Vaadin-License-Title" -> "Apache License 2.0"))
}

object Dependencies {
  val vaadinVersion = "6.7.9"
  val jettyVersion = "7.3.0.v20110203"
  val scalaTestVersion = "1.8"
  val junitVersion = "4.9"
  val mockitoVersion = "1.9.0"

  val vaadin = "com.vaadin" % "vaadin" % vaadinVersion
  val jetty = "org.eclipse.jetty" % "jetty-webapp" % jettyVersion % "container"
  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
  val junitInterface = "com.novocode" % "junit-interface" % "0.7" % "test->default"
  val mockito = "org.mockito" % "mockito-all" % mockitoVersion % "test" 
}

object ScalaWrappersForVaadinBuild extends Build {
  import Dependencies._
  import BuildSettings._

  val addonSettings = buildSettings ++ jacoco.settings ++ Seq(
    name := buildName,
    libraryDependencies := Seq(vaadin, scalaTest, junitInterface, mockito),
    packageConfiguration in Compile in packageBin ~= { 
      (config: Package.Configuration) => new Package.Configuration(config.sources, config.jar, manifestAttributes) 
    },
    unmanagedResourceDirectories in Compile <<= Seq(resourceDirectory in Compile, scalaSource in Compile).join)

  val demoSettings = buildSettings ++ webSettings ++ Seq(
    name := buildName + "-demo",
    libraryDependencies := Seq(jetty))

  lazy val addon = Project("addon", file("addon"), settings = addonSettings)
  lazy val demo = Project("demo", file("demo"), settings = demoSettings) dependsOn (addon)
}
