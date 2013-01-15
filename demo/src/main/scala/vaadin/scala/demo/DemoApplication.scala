package vaadin.scala.demo

import java.util.Date
import vaadin.scala._

class DemoApplication extends Application(title = "Hello World") {
  override def main = new VerticalLayout {
    add(Label("Hello World!"))
  }
}