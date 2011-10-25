import sbt._
import Keys._
import com.github.siasia.WebPlugin._

object BuildSettings {
  val buildOrganization = "vaadin.scala"
  val buildVersion = "0.1-SNAPSHOT"
  val buildScalaVersion = "2.9.0-1"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := buildOrganization,
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    autoScalaLibrary := true)
}

object Dependencies {
  val vaadinVersion = "6.7.1"
  val jettyVersion = "7.3.0.v20110203"
  val scalaTestVersion = "1.6.1"
  val junitVersion = "4.9"

  val vaadin = "com.vaadin" % "vaadin" % vaadinVersion
  val jetty = "org.eclipse.jetty" % "jetty-webapp" % jettyVersion % "jetty"
  val scalaTest = "org.scalatest" % "scalatest_2.9.0" % scalaTestVersion
  val junitInterface = "com.novocode" % "junit-interface" % "0.7" % "test->default"

}

object ScalaWrappersForVaadinBuild extends Build {
  import Dependencies._
  import BuildSettings._

  val addonSettings = buildSettings ++ Seq(
    name := "scala-wrappers",
    libraryDependencies := Seq(vaadin, scalaTest, junitInterface))

  val demoSettings = buildSettings ++ webSettings ++ Seq(
    name := "scala-wrappers-demo",
    libraryDependencies := Seq(jetty))

  lazy val addon = Project("addon", file("addon"), settings = addonSettings)
  lazy val demo = Project("demo", file("demo"), settings = demoSettings) dependsOn (addon)
}
