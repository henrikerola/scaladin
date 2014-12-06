package vaadin.scala

import vaadin.scala.internal.JavaScriptFunction

object JavaScript {
  def current: JavaScript = Option(com.vaadin.ui.JavaScript.getCurrent) match {
    case Some(js) => new JavaScript { val p = js }
    case None => null
  }
}

trait JavaScript extends Wrapper {

  val p: com.vaadin.ui.JavaScript

  def execute(script: String): Unit = p.execute(script)

  def addFunction(name: String, function: elemental.json.JsonArray => Unit): Unit =
    p.addFunction(name, new JavaScriptFunction(function))

  def removeFunction(name: String): Unit = p.removeFunction(name)

}
