import sbt._
import Keys._

object BuildSettings {
  val buildOrganization = "vaadin.scala"
  val buildVersion      = "0.1-SNAPSHOT"
  val buildScalaVersion = "2.9.0-1"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := buildOrganization,
    version := buildVersion,
    scalaVersion := buildScalaVersion
  )
}

object Dependencies {
	val vaadinVersion = "6.6.2"
	val jettyVersion = "7.3.0.v20110203"
	
	val vaadin = "com.vaadin" % "vaadin" % vaadinVersion
	val jetty = "org.eclipse.jetty" % "jetty-webapp" % jettyVersion % "jetty"
}


object ScalaWrappersForVaadinBuild extends Build {
  import Dependencies._
  import BuildSettings._
    
  val addonSettings = buildSettings ++ Seq (
  	name := "scala-wrappers",
  	libraryDependencies := Seq(vaadin)
  )
  
  val demoSettings = buildSettings ++ WebPlugin.webSettings ++ Seq (
  	name := "scala-wrappers-demo",
  	//autoScalaLibrary := true,
  	//fullClasspath <<= (fullClasspath in Runtime).identity,
  	libraryDependencies := Seq(jetty)
  )
  
  //lazy val root = Project("root", file("."), settings = buildSettings) aggregate(addon)
  lazy val addon = Project("addon", file("addon"), settings = addonSettings)
  lazy val demo = Project("demo", file("demo"), settings = demoSettings) dependsOn(addon) 
}