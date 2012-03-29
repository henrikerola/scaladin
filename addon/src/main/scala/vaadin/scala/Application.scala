package vaadin.scala

import scala.collection.mutable

abstract class Application(title: String = "", applicationTheme: String = com.vaadin.ui.themes.Reindeer.THEME_NAME) extends com.vaadin.Application {

  implicit val wrapperRegistry = new WrapperRegistry

  def init(): Unit = {
    mainWindow = new Window { caption = Some(title) }
    theme = applicationTheme
    //    mainWindow.content = main
  }

  def main: com.vaadin.ui.ComponentContainer = new VerticalLayout(margin = true) { add(new Label("Replace me")) }.p

  def main(windowName: String): com.vaadin.ui.ComponentContainer = main

  def mainWindow_=(window: Window) = super.setMainWindow(window.p)
  def mainWindow = wrapperRegistry.get[Window](super.getMainWindow).get

  def theme_=(theme: String) = super.setTheme(theme)
  def theme = super.getTheme

  def windows: mutable.Set[Window] = new mutable.Set[Window] {
    import scala.collection.JavaConversions.asScalaIterator

    def contains(key: Window) = {
      Application.this.getWindows.contains(key)
    }
    def iterator: Iterator[Window] = {
      Application.this.getWindows.iterator.map(wrapperRegistry.get[Window](_).get)
    }
    def +=(elem: Window) = { Application.this.addWindow(elem.p); this }
    def -=(elem: Window) = { Application.this.removeWindow(elem.p); this }
  }
}