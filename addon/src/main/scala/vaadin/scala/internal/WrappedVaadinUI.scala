package vaadin.scala.internal

import vaadin.scala.mixins.AbstractSingleComponentContainerMixin
import vaadin.scala.server.ScaladinRequest

class WrappedVaadinUI extends com.vaadin.ui.UI with AbstractSingleComponentContainerMixin {
  protected def init(request: com.vaadin.server.VaadinRequest): Unit = {
    wrapper.asInstanceOf[vaadin.scala.UI].doInit(new ScaladinRequest {
      val p = request
    })
  }
}