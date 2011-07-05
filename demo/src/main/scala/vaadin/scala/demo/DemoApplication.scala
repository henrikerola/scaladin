package vaadin.scala.demo

import com.vaadin.ui.Alignment
import com.vaadin.Application
import vaadin.scala._
import com.vaadin.terminal.ThemeResource

class DemoApplication extends Application {
  def init(): Unit = {
    
    // TODO: replace this quick test code with a real demo application
    
    setMainWindow(new Window(caption = "Scala Rocks!"))

    getMainWindow addComponent new Button("Click me!", _ -> {
      val popupLayout = new VerticalLayout(100 percent, 100 percent, margin = true)
        .add(new Label("Hello!"), 1)
        .add(new HorizontalLayout(spacing = true)
          add new Button("OK", _ -> getMainWindow.showNotification("Ok clicked!"))
          add new Button("Cancel"), alignment = Alignment.MIDDLE_RIGHT)
      popupLayout addLayoutClickListener (_ -> getMainWindow.showNotification("Clicked!"))
      getMainWindow addWindow new Window(width = 300 px, height = 200 px, caption = "Caption", modal = true, content = popupLayout)
    })

    getMainWindow addComponent (new CssLayout() add (new LinkButton("Link", icon = new ThemeResource("../runo/icons/16/globe.png")), "background-color: red"))

    val customLayout = new CustomLayout(contents = "<p location='location'></p>")
      .add(new CheckBox("Check box"), "location")
    getMainWindow addComponent customLayout

    getMainWindow addComponent new HtmlLabel(content = "<b>Hello World!</b>");
  }
}