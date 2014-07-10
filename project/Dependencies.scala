import sbt._

object Dependencies {

  private val scalaVersion = "2.10.4"

  private val vaadinVersion = "7.2.4"
  private val jettyVersion = "7.3.0.v20110203"
  private val scalaTestVersion = "2.2.0"
  private val mockitoVersion = "1.9.5"

  private val scalaReflect = "org.scala-lang" % "scala-reflect" % scalaVersion
  private val scalaActors = "org.scala-lang" % "scala-actors" % scalaVersion % "test"
  private val vaadin = "com.vaadin" % "vaadin-server" % vaadinVersion
  private val vaadinClientCompiled = "com.vaadin" % "vaadin-client-compiled" % vaadinVersion
  private val vaadinThemes = "com.vaadin" % "vaadin-themes" % vaadinVersion
  private val servletApi = "javax.servlet" % "servlet-api" % "2.4"
  private val portletApi = "javax.portlet" % "portlet-api" % "2.0"
  private val jetty = "org.eclipse.jetty" % "jetty-webapp" % jettyVersion % "container"
  private val scalaTest = "org.scalatest" % "scalatest_2.10" % scalaTestVersion % "test"
  private val junitInterface = "com.novocode" % "junit-interface" % "0.7" % "test->default"
  private val mockito = "org.mockito" % "mockito-all" % mockitoVersion % "test"

  val addonDeps = Seq(scalaActors, scalaReflect, vaadin, servletApi, portletApi, scalaTest, junitInterface, mockito)

  val demoDeps = Seq(jetty, vaadinClientCompiled, vaadinThemes)
}