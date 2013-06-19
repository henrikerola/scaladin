import sbt._
import Keys._
import com.earldouglas.xsbtwebplugin.WebPlugin._
import de.johoop.jacoco4sbt._
import JacocoPlugin._
import java.util.jar.{Attributes, Manifest}
import com.typesafe.sbt.SbtScalariform._

object BuildSettings {
  val buildOrganization = "vaadin.scala"
  val buildName = "Scaladin"
  val buildVersion = "3.0-SNAPSHOT"
  val buildScalaVersion = "2.10.2"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := buildOrganization,
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-encoding", "UTF-8"),
    resolvers := Seq("Vaadin Snapshots" at "https://oss.sonatype.org/content/repositories/vaadin-snapshots/"),
    autoScalaLibrary := true,
    offline := false)

  val manifestAttributes = Seq(Package.ManifestAttributes(
    ("Implementation-Title" -> buildName),
    ("Implementation-Version" -> buildVersion),
    ("Vaadin-Package-Version" -> "1"),
    ("Vaadin-License-Title" -> "Apache License 2.0")))
}


object Format {

  lazy val settings = scalariformSettings ++ Seq(
    ScalariformKeys.preferences := formattingPreferences
  )
 
  lazy val formattingPreferences = {
    import scalariform.formatter.preferences._
    FormattingPreferences().
      setPreference(AlignParameters, true).
      setPreference(DoubleIndentClassDeclaration, true)
  }
}

object Dependencies {
  val vaadinVersion = "7.1.7"
  val jettyVersion = "7.3.0.v20110203"
  val scalaTestVersion = "2.0.M5b"
  val junitVersion = "4.9"
  val mockitoVersion = "1.9.5"

  val scala = "org.scala-lang" % "scala-library" % BuildSettings.buildScalaVersion % "provided"
  val scalaReflect = "org.scala-lang" % "scala-reflect" % BuildSettings.buildScalaVersion
  val scalaActors = "org.scala-lang" % "scala-actors" % BuildSettings.buildScalaVersion % "test"
  val vaadin = "com.vaadin" % "vaadin-server" % vaadinVersion
  val vaadinClientCompiled = "com.vaadin" % "vaadin-client-compiled" % vaadinVersion
  val vaadinThemes = "com.vaadin" % "vaadin-themes" % vaadinVersion
  val servletApi = "javax.servlet" % "servlet-api" % "2.4"
  val portletApi = "javax.portlet" % "portlet-api" % "2.0"
  val jetty = "org.eclipse.jetty" % "jetty-webapp" % jettyVersion % "container"
  val scalaTest = "org.scalatest" % "scalatest_2.10" % scalaTestVersion % "test"
  val junitInterface = "com.novocode" % "junit-interface" % "0.7" % "test->default"
  val mockito = "org.mockito" % "mockito-all" % mockitoVersion % "test" 
}

object ScaladinBuild extends Build {
  import Dependencies._
  import BuildSettings._

  lazy val addonSettings = buildSettings ++ jacoco.settings ++ Format.settings ++ Seq(
    name := buildName,
    libraryDependencies := Seq(scala, scalaActors, scalaReflect, vaadin, servletApi, portletApi, scalaTest, junitInterface, mockito),
    packageConfiguration in Compile in packageBin ~= { 
      (config: Package.Configuration) => new Package.Configuration(config.sources, config.jar, manifestAttributes) 
    },
    unmanagedResourceDirectories in Compile <<= Seq(resourceDirectory in Compile, scalaSource in Compile).join,
    dist,
    publishTo := Some(Resolver.file("file", file(Option(System.getProperty("scaladin.repository.path")).getOrElse("../henrikerola.github.io/repository/snapshots")))))

  lazy val demoSettings = buildSettings ++ webSettings ++ Seq(
    name := buildName + "-demo",
    libraryDependencies ++= Seq(jetty, vaadinClientCompiled, vaadinThemes))

  lazy val addon = Project("addon", file("addon"), settings = addonSettings)
  lazy val demo = Project("demo", file("demo"), settings = demoSettings) dependsOn (addon)
  
  val dist = TaskKey[Unit]("dist", "Produces a zip for Vaadin Directory") <<= {
    (baseDirectory, target, packageBin in Compile, packageSrc in Compile) map { (base, target, addonBin, addonSrc) =>
      IO.withTemporaryDirectory { tmpDir =>
        
        val metaInf = tmpDir / "META-INF"
        IO.createDirectories(Seq(metaInf))

        // Create a manifest file for zip, needed by Vaadin Directory
        val manifest = new Manifest
        val mainAttributes = manifest.getMainAttributes
        // Manifest-Version is needed, see: http://bugs.sun.com/view_bug.do?bug_id=4271239
        mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0")
        mainAttributes.put(new Attributes.Name("Vaadin-Addon"), addonBin.getName)
        manifestAttributes.foreach {
          _.attributes.foreach { attr =>
            mainAttributes.put(attr._1, attr._2)
          }
        }
        val fos = new java.io.FileOutputStream(metaInf / "MANIFEST.MF");
        manifest.write(fos)
        fos.close()
        
        IO.copyFile(addonBin, tmpDir / addonBin.getName)
        IO.copyFile(addonSrc, tmpDir / addonSrc.getName)
            
        val output = target / ("%s-%s.zip" format (buildName, buildVersion))
        IO.zip((tmpDir ** (-DirectoryFilter)) x relativeTo(tmpDir), output)
      }
    }
  }
}
