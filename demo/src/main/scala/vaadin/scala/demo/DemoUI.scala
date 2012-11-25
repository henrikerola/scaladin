package vaadin.scala.demo

import vaadin.scala._

class DemoUI extends UI(title = "Hello World") {
  content = Label("Hello World!")
}