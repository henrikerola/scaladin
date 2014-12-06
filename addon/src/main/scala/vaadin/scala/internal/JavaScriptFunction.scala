package vaadin.scala.internal

import elemental.json.JsonArray

class JavaScriptFunction(val function: JsonArray => Unit) extends com.vaadin.ui.JavaScriptFunction {

  def call(arguments: JsonArray): Unit = function(arguments)
}
