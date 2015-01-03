package vaadin.scala.tests

import com.vaadin.server.Extension
import org.mockito.{ Matchers, Mockito }
import vaadin.scala.Label
import vaadin.scala.mixins.LabelMixin
import vaadin.scala.server.Responsive

/**
 *
 * @author Henri Kerola / Vaadin
 */
class ResponsiveTests extends ScaladinTestSuite {

  class VaadinLabel extends com.vaadin.ui.Label with LabelMixin {
    // make addExtension public so we can use that in tests
    override def addExtension(extension: Extension): Unit = super.addExtension(extension)
  }

  test("makeResponsive") {
    val vaadinLabelSpy1 = Mockito.spy(new VaadinLabel)
    val label1 = new Label(vaadinLabelSpy1)
    val vaadinLabelSpy2 = Mockito.spy(new VaadinLabel)
    val label2 = new Label(vaadinLabelSpy2)

    Responsive.makeResponsive(label1, label2)

    Mockito.verify(vaadinLabelSpy1).addExtension(Matchers.any(classOf[com.vaadin.server.Responsive]))
    Mockito.verify(vaadinLabelSpy2).addExtension(Matchers.any(classOf[com.vaadin.server.Responsive]))
  }

}
