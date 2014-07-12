package vaadin.scala.internal

import vaadin.scala.mixins.AbstractSingleComponentContainerMixin
import vaadin.scala.server.ScaladinRequest
import com.vaadin.server.VaadinRequest

class WrappedVaadinUI extends com.vaadin.ui.UI with AbstractSingleComponentContainerMixin {

  override def wrapper = super.wrapper.asInstanceOf[vaadin.scala.UI]

  protected def init(request: com.vaadin.server.VaadinRequest): Unit = {
    wrapper.doInit(new ScaladinRequest {
      val p = request
    })
  }

  override def refresh(request: VaadinRequest): Unit = {
    super.refresh(request)
    wrapper.refresh(new ScaladinRequest {
      val p = request
    })
  }
}