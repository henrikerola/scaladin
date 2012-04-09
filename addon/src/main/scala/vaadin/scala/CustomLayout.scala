package vaadin.scala

import scala.xml.Node

class CustomLayout(override val p: com.vaadin.ui.CustomLayout = new com.vaadin.ui.CustomLayout(null.asInstanceOf[String])) extends AbstractLayout {

  def templateName = Option(p.getTemplateName)
  def templateName_=(templateName: String) = p.setTemplateName(templateName)

  def templateContents = Option(p.getTemplateContents)
  def templateContents_=(templateContents: String) = p.setTemplateContents(templateContents)
  def templateContents_=(templateContents: Node) = p.setTemplateContents(templateContents.toString)

  def add[C <: Component](component: C, location: String): C = {
    p.addComponent(component.p, location)
    component
  }
  
  def component(location: String) = WrapperRegistry.get[Component](p.getComponent(location))
  
  // TODO provide add and component with Symbol location? 
  // TODO: "unimplemented" margin methods
}