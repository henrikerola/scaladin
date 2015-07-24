import sbt._

object Dependencies {

  private val vaadinVersion = "7.5.2"
  private val scalaTestVersion = "2.2.0"
  private val mockitoVersion = "1.9.5"

  private def scalaReflect(scalaVersion: String) = "org.scala-lang" % "scala-reflect" % scalaVersion
  private val vaadin = "com.vaadin" % "vaadin-server" % vaadinVersion
  private val vaadinClientCompiled = "com.vaadin" % "vaadin-client-compiled" % vaadinVersion
  private val vaadinThemes = "com.vaadin" % "vaadin-themes" % vaadinVersion
  private val servletApi = "javax.servlet" % "servlet-api" % "2.4"
  private val portletApi = "javax.portlet" % "portlet-api" % "2.0"
  private val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion
  private val junitInterface = "com.novocode" % "junit-interface" % "0.7"
  private val mockito = "org.mockito" % "mockito-all" % mockitoVersion

  def addonDeps(scalaVersion: String) = Seq(
    scalaReflect(scalaVersion),
    vaadin,
    servletApi,
    portletApi,
    scalaTest % "test",
    junitInterface % "test->default",
    mockito % "test"
  )

  val demoDeps = Seq(
    vaadinClientCompiled,
    vaadinThemes
  )
}
