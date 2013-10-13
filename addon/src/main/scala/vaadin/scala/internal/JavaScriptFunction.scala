package vaadin.scala.internal

import org.json.JSONArray

class JavaScriptFunction(val function: org.json.JSONArray => Unit) extends com.vaadin.ui.JavaScriptFunction {

  def call(arguments: JSONArray): Unit = function(arguments)
}
