package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite

class ButtonTests extends FunSuite {

  val listener1 = (e: ButtonClickEvent) => println("1")
  val listener2 = (e: ButtonClickEvent) => println(e)

  test("clickListeners, add a clicklistener") {
    val button = new Button

    button.clickListeners += (println(_))

    assert(button.clickListeners.size === 1)
  }
  
  test("clickListener remove a clicklistener") {
    val button = new Button

    button.clickListeners += listener1
    
    button.clickListeners -= listener1
    
    assert(button.clickListeners.size === 0)
  }

  test("clickListeners.iterator returns added listeners") {
    val button = new Button

    button.clickListeners += listener1
    button.clickListeners += listener2

    val iter = button.clickListeners.iterator
    assert(iter.next() === listener1)
    assert(iter.next() === listener2)
    assert(!iter.hasNext)
  }
  
  test("clickListeners.contains returns true for added listener") {
    val button = new Button

    button.clickListeners += (listener1)
    
    assert(button.clickListeners.contains(listener1))
  }
  
  test("clickListeners.contains returns false for non-added listener") {
    val button = new Button

    assert(!button.clickListeners.contains(listener2))
  }
  

}