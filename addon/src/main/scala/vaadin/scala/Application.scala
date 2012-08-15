package vaadin.scala

import scala.collection.mutable
import vaadin.scala.internal.WrapperUtil

object Application {
  def current: Application = com.vaadin.Application.getCurrent.asInstanceOf[Application]
  def current_=(application: Option[Application]): Unit = com.vaadin.Application.setCurrent(application.getOrElse(null))
  def current_=(application: Application): Unit = com.vaadin.Application.setCurrent(application)
}

class Application extends com.vaadin.Application {
  
  def rootPreserved: Boolean = isRootPreserved
  def rootPreserved_=(rootPreserved: Boolean) = setRootPreserved(rootPreserved)
  
  // TODO ...

}