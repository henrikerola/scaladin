package vaadin.scala.demo

import vaadin.scala._

class DemoUI extends UI(title = "Hello World") {

  val layout = new VerticalLayout {
    sizeFull()
  }

  val headerLayout = new VerticalLayout {
    width = 100 pct;
    height = 70 px;
  }

  val contentLayout = new VerticalLayout {
    sizeFull()
    margin = true
  }

  override def init(request: ScaladinRequest) {
    val navigator = Navigator(this, contentLayout)
    navigator.addView(DemoView.VIEW1, new DemoView)
    navigator.addView(DemoView.VIEW2, classOf[DemoView])

    navigator_=(navigator)

    content_=(layout)

    headerLayout.add(buildApplicationHeader)
    headerLayout.add(buildApplicationMenu(navigator))

    layout.add(headerLayout)
    layout.add(contentLayout, ratio = 1)

  }

  private def buildApplicationHeader: HorizontalLayout = new HorizontalLayout {
    width = 100 pct;
    height = 45 px;
    add(alignment = Alignment.MiddleLeft, component = new Label {
      value = "Scaladin"
    })
    add(alignment = Alignment.MiddleCenter, component = new Label {
      value = "Demo Application"
    })
    add(alignment = Alignment.MiddleRight, component = new Label {
      value = "Hello World!"
    })
  }

  private def buildApplicationMenu(navigator: Navigator): HorizontalLayout = new HorizontalLayout {
    width = 100 pct;
    height = 25 px;
    val menuBar = new MenuBar {
      addItem("DemoView1", (e: MenuBar.MenuItem) => navigator.navigateTo(DemoView.VIEW1))
      addItem("DemoView2", (e: MenuBar.MenuItem) => navigator.navigateTo(DemoView.VIEW2))
    }
    addComponent(menuBar)
  }

}

object DemoView {
  val VIEW1 = ""
  val VIEW2 = "ClassBasedView"
}

class DemoView extends Navigator.PanelView {
  val label = Label("Hello from DemoView")

  def init() {

    val layout = new VerticalLayout() {
      sizeFull()
      add(label)
    }
    layout.margin = true
    content = layout
  }

  init()

  override def enter(event: Navigator.ViewChangeEvent) {
    label.value = "Hello from view " + event.viewName
    Notification.show("Entering view " + event.viewName)
  }
}