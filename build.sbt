import org.vaadin.sbt.VaadinPlugin._

name := "Scaladin"

version in ThisBuild := "3.1-SNAPSHOT"

organization in ThisBuild := "vaadin.scala"

scalaVersion in ThisBuild := "2.10.4"

scalacOptions in ThisBuild ++= Seq("-deprecation", "-unchecked", "-encoding", "UTF-8")

scalariformSettings

lazy val root = project.in(file(".")).aggregate(addon, demo)

lazy val addon = project.settings(vaadinAddOnSettings :_*).settings(
  name := "Scaladin",
  libraryDependencies := Dependencies.addonDeps
)

lazy val demo = project.settings(vaadinWebSettings :_*).settings(
  name := "scaladin-demo",
  libraryDependencies := Dependencies.demoDeps
).dependsOn(addon)
