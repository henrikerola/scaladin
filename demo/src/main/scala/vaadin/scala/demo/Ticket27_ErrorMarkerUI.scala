package vaadin.scala.demo

import vaadin.scala.UI
import vaadin.scala.Button
import vaadin.scala.VerticalLayout

class Ticket27_ErrorMarkerUI extends UI {

  val run = Button("Run", e => {
    println("About to die")
    throw new RuntimeException("Argh!!!")
  })
  content = new VerticalLayout { add(run) }
}
