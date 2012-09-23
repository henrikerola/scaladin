package vaadin.scala.internal

import vaadin.scala.mixins.AbstractComponentContainerMixin
import vaadin.scala.ScaladinRequest

class WrappedVaadinUI extends com.vaadin.ui.UI with AbstractComponentContainerMixin {
  protected def init(request: com.vaadin.server.VaadinRequest): Unit = {
    wrapper.asInstanceOf[vaadin.scala.UI].doInit(new ScaladinRequest {
      val p = request
    })
  }
}