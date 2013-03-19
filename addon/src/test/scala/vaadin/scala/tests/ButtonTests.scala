package vaadin.scala.tests

import vaadin.scala._
import event.FocusEvent
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ButtonTests extends FunSuite {

  val listener1 = (e: Button.ClickEvent) => println("1")
  val listener2 = (e: Button.ClickEvent) => println(e)

  test("disableOnClick") {
    val button = new Button

    assert(!button.disableOnClick)
    button.disableOnClick = true
    assert(button.disableOnClick)
  }

  test("Button.apply(caption)") {
    val button = Button("Test")
    assert(button.caption === Some("Test"))
    assert(button.clickListeners.isEmpty)
  }

  test("Button.apply(caption, clickListener") {
    val listener = { e: Button.ClickEvent => }
    val button = Button("Test", listener)
    assert(button.caption === Some("Test"))
    assert(button.clickListeners.size === 1)
    assert(button.clickListeners.head === listener)
  }

  test("Button.apply(caption, clickListener 2") {
    val listener = {}
    val button = Button("Test", listener)
    assert(button.caption === Some("Test"))
    assert(button.clickListeners.size === 1)
    assert(button.clickListeners.head != listener) // listener is wrapped
  }

  test("clickShortcut") {
    val button = new Button

    val clickShortcut = KeyShortcut(KeyCode.Enter, KeyModifier.Shift)

    assert(button.clickShortcut === None)

    button.clickShortcut = clickShortcut
    assert(button.clickShortcut === Some(clickShortcut))

    button.clickShortcut = None
    assert(button.clickShortcut === None)

    button.clickShortcut = Some(clickShortcut)
    assert(button.clickShortcut === Some(clickShortcut))
  }

  test("htmlContentAllowed") {
    val button = new Button

    assert(!button.htmlContentAllowed)

    button.htmlContentAllowed = true
    assert(button.htmlContentAllowed)

  }

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

  test("focusListeners, add a listener") {
    val button = new Button

    button.focusListeners += (println(_))

    assert(button.focusListeners.size === 1)
  }

  val focusListener = (e: FocusEvent) => println("1")
  val focusListener2 = (e: FocusEvent) => println(e)

  test("focusListeners, remove a listener") {
    val button = new Button

    button.focusListeners += focusListener

    button.focusListeners -= focusListener

    assert(button.clickListeners.size === 0)
  }

  test("focusListeners.iterator returns added listeners") {
    val button = new Button

    button.focusListeners += focusListener
    button.focusListeners += focusListener2

    val iter = button.focusListeners.iterator
    assert(iter.next() === focusListener)
    assert(iter.next() === focusListener2)
    assert(!iter.hasNext)
  }

  test("focusListeners.contains returns true for added listener") {
    val button = new Button

    button.focusListeners.add(focusListener)

    assert(button.focusListeners.contains(focusListener))
  }

  test("focusListeners.contains returns false for non-added listener") {
    val button = new Button

    assert(!button.focusListeners.contains(focusListener))
  }
}