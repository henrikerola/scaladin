package vaadin.scala

import com.vaadin.Application

abstract class SimpleApplication(title: String = "", theme: String = com.vaadin.ui.themes.Reindeer.THEME_NAME) extends Application {

  def init(): Unit = {
    val mainWindow = new Window(title)
    setMainWindow(mainWindow)
    setTheme(theme)
    mainWindow.setContent(main)
  }

  def main: com.vaadin.ui.ComponentContainer = new VerticalLayout(margin = true) { add(new Label("Replace me").p) }
  
  def main(windowName: String) :com.vaadin.ui.ComponentContainer = main
  
  override def getWindow(windowName : String) = {
    super.getWindow(windowName)
    
  }
}