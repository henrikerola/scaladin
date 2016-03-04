package vaadin.scala.tests

/**
 *
 * @author Henri Kerola / Vaadin
 */
class ValoThemeTests extends ScaladinTestSuite {

  ignore("generate ValoTheme constants") {
    classOf[com.vaadin.ui.themes.ValoTheme].getDeclaredFields.foreach { f =>
      val javaName = f.getName
      val scalaName = javaName.split("_").map(p => p.substring(0, 1).toUpperCase + p.substring(1).toLowerCase).mkString
      println(s"val $scalaName: String = $javaName");

    }
  }

}
