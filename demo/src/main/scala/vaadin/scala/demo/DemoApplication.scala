package vaadin.scala.demo

import vaadin.scala._

class DemoApplication extends UI {
  override def init(request: ScaladinRequest) {
    add(Label("Hello World!"))
  }
}