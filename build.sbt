import org.vaadin.sbt.VaadinPlugin._

name := "Scaladin"

version in ThisBuild := "3.1-SNAPSHOT"

organization in ThisBuild := "org.vaadin.addons"

scalaVersion in ThisBuild := "2.11.4"

crossScalaVersions in ThisBuild := Seq("2.10.4", "2.11.4")

scalacOptions in ThisBuild ++= Seq("-deprecation", "-unchecked", "-encoding", "UTF-8")

// sbt -Dscaladin.repository.path=../henrikerola.github.io/repository/releases publish
publishTo in ThisBuild := Some(Resolver.file("GitHub", file(Option(System.getProperty("scaladin.repository.path")).getOrElse("../henrikerola.github.io/repository/snapshots"))))

scalariformSettings

lazy val root = project.in(file(".")).aggregate(addon, demo)

lazy val addon = project.settings(vaadinAddOnSettings :_*).settings(
  name := "Scaladin",
  libraryDependencies := Dependencies.addonDeps(scalaVersion.value)
)

lazy val demo = project.settings(vaadinWebSettings :_*).settings(
  name := "scaladin-demo",
  libraryDependencies := Dependencies.demoDeps
).dependsOn(addon)
