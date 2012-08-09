package vaadin.scala.demo

import java.util.Date
import vaadin.scala._

class DemoApplication extends Application(title = "Vaadin Reindeer Theme", applicationTheme = "reindeer") {

  val DATE = new Date()

  val help = new Window { caption = "Help" }

  override def main = new VerticalLayout {
    sizeFull()
    val tabs = new TabSheet {
      sizeFull()
    }

    add(buildScalaWrappersAd)
    add(getTopMenu)
    add(getHeader(this, tabs))
    add(ratio = 1, component = new CssLayout {
      sizeFull()
      margin(false, true, true, true)
      add(tabs)
    })

    tabs.add(buildWelcomeScreen)
    tabs.add(buildLabels)
    tabs.add(buildButtons)
    tabs.add(buildTextFields)
    tabs.add(buildSelects)
    tabs.add(buildDateFields)
    tabs.add(buildTabSheets)
    tabs.add(buildPanels)
    tabs.add(buildTables)
    tabs.add(buildWindows(tabs))
    tabs.add(buildSplitPanels)
  }

  def buildScalaWrappersAd(): Layout = new VerticalLayout {
    width = (100 percent)
    styleName = Reindeer.LAYOUT_BLACK
    add(alignment = Alignment.MiddleCenter, component = new VerticalLayout {
      width = 700 px;
      styleName = Reindeer.LAYOUT_BLACK;
      margin = true;
      add(alignment = Alignment.MiddleCenter, component = new Label() {
        width = None; styleName = Reindeer.LABEL_H2; contentMode = Label.ContentMode.Xhtml;
        value = <span>This demo is implemented completely in <a href="http://www.scala-lang.org">Scala</a>,</span>
      })
      add(alignment = Alignment.MiddleCenter, component = new Label {
        width = None; styleName = Reindeer.LABEL_H2; contentMode = Label.ContentMode.Xhtml;
        value = <span>with the help of the Scaladin Vaadin addon.</span>
      })
      add(alignment = Alignment.MiddleCenter, component = new Label {
        width = None; styleName = Reindeer.LABEL_H2; contentMode = Label.ContentMode.Xhtml;
        value = <span>You can find more info about the add-on from the <a href="http://vaadin.com/addon/scaladin">Directory page</a>.</span>
      })
    })
  }

  def buildLabels(): Layout = new GridLayout {
    columns = 2
    rows = 1
    spacing = true
    margin = true
    caption = "Labels"
    styleName = Reindeer.LAYOUT_WHITE
    width = 560 px

    add(Label.html(<span>Header Style (<code>Reindeer.LABEL_H1</code>)</span>))
    add(new H1("Lorem Ipsum"))

    add(Label.html(<span>Sub-header Style (<code>Reindeer.LABEL_H2</code>)</span>))
    add(new H2("Lorem Ipsum Dolor"))

    add(Label.html(<span>Normal Label</span>))
    add(new Label { value = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." })

    add(Label.html(<span>Small Style (<code>Reindeer.LABEL_SMALL</code>)</span>))
    add(new SmallText("Lorem ipsum dolor sit amet, consectetur adipiscing elit."))
  }

  def buildButtons(): Layout = new GridLayout {
    columnExpandRatio(0, 1)
    columns = 2
    rows = 1
    caption = "Buttons"
    margin = true
    spacing = true
    width = 370 px

    add(Label.html(<span>"Default" Style (<code>Reindeer.BUTTON_DEFAULT</code>)</span>))
    add(new Button { caption = "Default Button"; styleName = Reindeer.BUTTON_DEFAULT })

    add(Label.html(<span>Normal Button</span>))
    add(new Button { caption = "Normal Button" })

    add(Label.html(<span>Disabled Button (<code>Button.enabled(false)</code>)</span>))
    add(new Button { caption = "Disabled Button"; enabled = false })

    add(Label.html(<span>Small Style (<code>Reindeer.BUTTON_SMALL</code>)</span>))
    add(new Button { caption = "Small Button"; styleName = Reindeer.BUTTON_SMALL })

    add(Label.html(<span>Link Style (<code>Reindeer.BUTTON_LINK</code>)</span>))
    add(new Button { caption = "Link Button"; styleName = BaseTheme.BUTTON_LINK })
  }

  def buildTextFields(): Layout = new GridLayout {
    columns = 2
    rows = 1
    caption = "Text fields"
    margin = true
    columnExpandRatio(0, 1)
    spacing = true
    styleName = Reindeer.LAYOUT_WHITE
    width = 400 px

    add(Label.html(<span>Normal TextField</span>))
    add(new TextField { prompt = "Enter text" })

    add(Label.html(<span>Small Style (<code>Reindeer.TEXTFIELD_SMALL</code>)</span>))
    add(new TextField { styleName = Reindeer.TEXTFIELD_SMALL; prompt = "Enter text" })

    add(Label.html(<span>Normal TextArea</span>))
    add(new TextArea { height = 5 em; prompt = "Enter text" })

    add(Label.html(<span>Small Style TextArea (<code>Reindeer.TEXTFIELD_SMALL</code>)</span>))
    add(new TextArea { height = 5 em; styleName = Reindeer.TEXTFIELD_SMALL; prompt = "Enter text" })
  }

  def buildSelects(): Layout = new VerticalLayout {
    caption = "Selects"
    margin = true
    spacing = true
    add(new Label { value = "Selects don't currently have any additional style names, but here you can see how they behave with the different background colors." })
    components += new HorizontalLayout {
      spacing = true
      margin(top = true)
      val comboBox = add(new ComboBox())
      val nativeSelect = add(new NativeSelect())
      val listSelect = add(new ListSelect())
      val twinColSelect = add(new TwinColSelect())

      for (i <- 0 until 25) {
        comboBox.p.addItem("Item " + i)
        nativeSelect.p.addItem("Item " + i)
        listSelect.p.addItem("Item " + i)
        twinColSelect.p.addItem("Item " + i)
      }
    }

  }

  def buildDateFields(): Layout = new VerticalLayout {
    caption = "Date fields"
    margin = true
    spacing = true

    components += Label("Date fields don't currently have any additional style names, but here you can see how they behave with the different background colors.")

    components += new HorizontalLayout {
      spacing = true
      margin(top = true)
      add(new PopupDateField { value = DATE; resolution = DateField.Resolution.Minute })
      add(new InlineDateField { value = DATE; resolution = DateField.Resolution.Day })
      add(new InlineDateField { value = DATE; resolution = DateField.Resolution.Year })
    }
  }

  def buildTabSheets(): Layout = {
    val closable = new CheckBox {
      caption = "Closable tabs"
      immediate = true
    }
    val hoverOnly = new CheckBox {
      caption = "Only on hover"
      immediate = true
      enabled = false
      description = "Adds style <code>Reindeer.TABSHEET_HOVER_CLOSABLE</code> to all tabs"
    }
    val selectedOnly = new CheckBox {
      caption = "Selected only"
      immediate = true
      enabled = false
      description = "Adds style <code>Reindeer.TABSHEET_SELECTED_CLOSABLE</code> to all tabs"
    }
    val checks = new HorizontalLayout {
      spacing = true
      components ++= List(closable, hoverOnly, selectedOnly)
    }

    val tabSheetLayout = new GridLayout with FilterableComponentContainer {
      columns = 2
      rows = 1
      caption = "Tabs"
      margin = true
      spacing = true
      styleName = Reindeer.LAYOUT_WHITE
      columnExpandRatio(0, 7)
      columnExpandRatio(1, 4)
      width = 700 px

      add(component = checks, col = 1, row = 0)
      space()

      add(Label.html(<span>Normal Tabs</span>))
      val normalTabSheet = add(new TabSheet { height = 100 px })

      add(Label.html(<span>Borderless Style (<code>Reindeer.TABSHEET_BORDERLESS</code>)</span>))
      val borderlessTabSheet = add(new TabSheet { height = 100 px; styleName = Reindeer.TABSHEET_BORDERLESS })

      add(Label.html(<span>Small Style (<code>Reindeer.TABSHEET_SMALL</code>)</span>))
      val smallTabSheet = add(new TabSheet { styleName = Reindeer.TABSHEET_SMALL })

      add(Label.html(<span>Minimal Style (<code>Reindeer.TABSHEET_MINIMAL</code>)</span>))
      val minimalTabSheet = add(new TabSheet { styleName = Reindeer.TABSHEET_MINIMAL })
    }

    for (i <- 1 until 10) {
      tabSheetLayout.normalTabSheet.addTab(new Label).caption = "Tab " + i
      tabSheetLayout.borderlessTabSheet.addTab(new Label).caption = "Tab " + i
      tabSheetLayout.smallTabSheet.addTab(new Label).caption = "Tab " + i
      tabSheetLayout.minimalTabSheet.addTab(new Label).caption = "Tab " + i
    }

    closable.valueChangeListeners += (event => {

      val eventValue = event.property.value.get.asInstanceOf[Boolean]
      tabSheetLayout.normalTabSheet.components.foreach(c => tabSheetLayout.normalTabSheet.tab(c).map(_.closable = eventValue))
      tabSheetLayout.borderlessTabSheet.components.foreach(c => tabSheetLayout.borderlessTabSheet.tab(c).map(_.closable = eventValue))
      tabSheetLayout.smallTabSheet.components.foreach(c => tabSheetLayout.smallTabSheet.tab(c).map(_.closable = eventValue))
      tabSheetLayout.minimalTabSheet.components.foreach(c => tabSheetLayout.minimalTabSheet.tab(c).map(_.closable = eventValue))

      hoverOnly.enabled = eventValue
      selectedOnly.enabled = eventValue
    })

    hoverOnly.valueChangeListeners += (event => {
      if (event.property.value.get.asInstanceOf[Boolean])
        tabSheetLayout filter (_.isInstanceOf[TabSheet]) foreach (_.styleNames += Reindeer.TABSHEET_HOVER_CLOSABLE)
      else
        tabSheetLayout filter (_.isInstanceOf[TabSheet]) foreach (_.styleNames -= Reindeer.TABSHEET_HOVER_CLOSABLE)
    })

    selectedOnly.valueChangeListeners += (event => {
      if (event.property.value.get.asInstanceOf[Boolean])
        tabSheetLayout filter (_.isInstanceOf[TabSheet]) foreach (_.styleNames += Reindeer.TABSHEET_SELECTED_CLOSABLE)
      else
        tabSheetLayout filter (_.isInstanceOf[TabSheet]) foreach (_.styleNames += Reindeer.TABSHEET_SELECTED_CLOSABLE)
    })

    tabSheetLayout
  }

  def buildPanels(): Layout = new GridLayout {
    columns = 2
    rows = 1
    caption = "Panels"
    margin = true
    spacing = true
    styleName = Reindeer.LAYOUT_WHITE
    columnExpandRatio(0, 2)
    columnExpandRatio(1, 5)
    width = 700 px

    add(Label.html(<span>Normal Panel</span>))
    add(new Panel {
      caption = "Normal Panel"
      components += Label("Panel content")
      height = 100 px
    })

    add(Label.html(<span>Light Style (<code>Reindeer.PANEL_LIGHT</code>)</span>))
    val lightPanel = add(new Panel { caption = "Light Style Panel"; styleNames += Reindeer.PANEL_LIGHT })
    lightPanel.components += new Label { value = "Panel content" }
  }

  def buildTables(): Layout = new GridLayout {
    columns = 2
    rows = 1
    caption = "Tables"
    margin = true
    spacing = true
    styleName = Reindeer.LAYOUT_WHITE
    columnExpandRatio(0, 3)
    columnExpandRatio(1, 5)
    width = 700 px

    for (i <- 0 until 4) {
      val table = new Table {
        width = (100 percent)
        pageLength = 4
        selectionMode = SelectionMode.Single
        columnCollapsingAllowed = true
        columnReorderingAllowed = true
      }

      i match {

        case 0 => add(Label.html(<span>Normal Table</span>))

        case 1 =>
          table.p.setStyleName("strong")
          add(Label.html(<span>Strong Style (<code>Reindeer.TABLE_STRONG</code>)</span>))

        case 2 =>
          table.p.setStyleName("borderless")
          add(Label.html(<span>Borderless Style (<code>Reindeer.TABLE_BORDERLESS</code>)</span>))

        case 3 =>
          table.p.setStyleName("borderless strong")
          add(new Label { value = "Borderless & Strong Combined" })

      }

      table.p.addContainerProperty("First", classOf[String], null)
      table.p.addContainerProperty("Second", classOf[String], null)
      table.p.addContainerProperty("Third", classOf[String], null)

      for (j <- 0 until 100) {
        table.p.addItem(Array("Foo " + j, "Bar value " + j,
          "Last column value " + j), j)
      }

      add(table)
    }
  }

  def buildWindows(tabs: TabSheet): Layout = new CssLayout {
    caption = "Windows"

    val normalWindow = new Window {
      caption = "Normal window"
      width = (280 px)
      height = (180 px)
      positionX = 40
      positionY = 250
    }

    val notResizableWindow = new Window {
      caption = "Window, no resize"
      width = (280 px)
      resizable = false
      height = (180 px)
      positionX = 350
      positionY = 250
      components += Label.html(<span><code>Window.setResizable(false)</code></span>)
    }

    val lightWindow = new Window {
      caption = " Light window"
      width = (280 px)
      height = (230 px)
      styleNames += Reindeer.WINDOW_LIGHT
      positionX = 40
      positionY = 460
      components += Label.html(<span><code>Reindeer.WINDOW_LIGHT</code></span>)
    }

    val blackWindow = new Window {
      caption = "Black window"
      width = (280 px)
      height = (230 px)
      styleNames += Reindeer.WINDOW_BLACK
      positionX = 350
      positionY = 460
      components += Label.html(<span><code>Reindeer.WINDOW_BLACK</code></span>)
    }

    tabs.selectedTabChangeListeners += (event => {
      if (event.tabSheet.selectedTab.component == this) {
        mainWindow.childWindows += (normalWindow, notResizableWindow, lightWindow, blackWindow)

      } else {
        if (normalWindow.parent.exists(_ == mainWindow)) {
          mainWindow.childWindows -= normalWindow
        }

        if (notResizableWindow.parent.exists(_ == mainWindow)) {
          mainWindow.childWindows -= notResizableWindow
        }

        if (lightWindow.parent.exists(_ == mainWindow)) {
          mainWindow.childWindows -= lightWindow
        }

        if (blackWindow.parent.exists(_ == mainWindow)) {
          mainWindow.childWindows -= blackWindow
        }
      }
    })
  }

  def buildSplitPanels(): Layout = new GridLayout {
    columns = 2
    rows = 1
    caption = "Split panels"
    margin = true
    spacing = true
    styleName = Reindeer.LAYOUT_WHITE
    columnExpandRatio(0, 1)
    width = 400 px

    add(Label.html(<span>Normal SplitPanel</span>))
    add(new HorizontalSplitPanel { width = 100 px; height = 200 px })

    add(Label.html(<span>Small Style (<code>Reindeer.SPLITPANEL_SMALL</code>)</span>))
    add(new HorizontalSplitPanel { width = 100 px; height = 200 px; styleName = Reindeer.SPLITPANEL_SMALL })
  }

  def buildWelcomeScreen(): Layout = new VerticalLayout {
    margin = true
    spacing = true
    caption = "Welcome"
    styleName = Reindeer.LAYOUT_WHITE

    val welcomeText1 = <span><h4>A Complete Theme</h4><p>The Reindeer theme is a complete, general purpose theme suitable for almost all types of applications.</p><p>While a general purpose theme should not try to cater for every possible need, the Reindeer theme provides a set of useful styles that you can use to make the interface a bit more lively and interesing, emphasizing different parts of the application.</p></span>
    val welcomeText2 = <span><h4>Everything You Need Is Here</h4><p>Everything you see inside this application, all the different styles, are provided by the Reindeer theme, out-of-the-box. That means you don't necessarily need to create any custom CSS for your application: you can build a cohesive result writing plain Java code.</p><p>A little creativity, good organization and careful typography carries a long way.</p></span>
    val welcomeText3 = <span><h4>The Names of The Styles</h4><p>Look for a class named <code>Reindeer</code> inside the Vaadin JAR (<code>com.vaadin.ui.themes.Reindeer</code>). All the available style names are documented and available there as constants, prefixed by component names, e.g. <code>Reindeer.BUTTON_SMALL</code>.</p></span>

    val texts = new HorizontalLayout {
      spacing = true
      margin = Margin(bottom = true)
      width = (100 percent)
      add(Label.html(welcomeText1), 1)
      add(new Spacer())
      add(Label.html(welcomeText2), 1)
      add(new Spacer())
      add(Label.html(welcomeText3), 1)
    }

    val stylesHeading = new H2("One Theme &ndash; Three Styles")
    stylesHeading.contentMode = Label.ContentMode.Xhtml

    val colors = new HorizontalLayout { width = 100 percent; height = 250 px }

    val whiteLayout = new CssLayout {
      sizeFull()
      styleName = Reindeer.LAYOUT_BLUE
      margin = true
      add(new H1("White"))
      add(Label.html(<span><p><strong><code>Reindeer.LAYOUT_WHITE</code></strong></p><p>Changes the background to white. Has no other effect on contained components, they all behave like on the default gray background.</p></span>))
    }

    val blueLayout = new CssLayout {
      sizeFull()
      styleName = Reindeer.LAYOUT_BLUE
      margin = true
      add(new H1("Blue"))
      add(Label.html(<span><p><strong><code>Reindeer.LAYOUT_BLUE</code></strong></p><p>Changes the background to a shade of blue. A very few components have any difference here compared to the white style.</p></span>))
    }

    val blackLayout = new CssLayout {
      sizeFull()
      styleName = Reindeer.LAYOUT_BLACK
      margin = true
      add(new H1("Black"))
      add(Label.html(<span><p><strong><code>Reindeer.LAYOUT_BLACK</code></strong></p><p>Reserved for small parts of the application. Or alternatively, use for the whole application.</p><p><strong>This style is non-overridable</strong>, meaning that everything you place inside it will transform to their corresponding black styles when available, excluding Labels.</p></span>))
    }

    colors.components += (whiteLayout, blueLayout, blackLayout)

    val note = Label.html(<span><p>Note, that you cannot nest the layout styles infinitely inside each other. After a couple levels, the result will be undefined, due to limitations in CSS (which are in fact caused by Internet Explorer 6).</p></span>)

    components += new CssLayout {
      margin = true
      width = 100 percent

      add(new H1("Guide to the Reindeer Theme"))
      add(new Ruler())
      add(texts)
      add(stylesHeading)
      add(new Ruler())
      add(Label.html(<span><p>You can easily change the feel of some parts of your application by using the three layout styles provided by Reindeer: white, blue and black. The colored area contains the margins of the layout. All contained components will switch their style if an alternative style is available for that color.</p></span>))
      add(colors)
      add(note)
    }
  }

  def getTopMenu(): MenuBar = new MenuBar {
    width = 100 percent

    val file = addItem("File")
    val newItem = file.addItem("New")
    file.addItem("Open file...")
    file.addSeparator

    newItem.addItem("File")
    newItem.addItem("Folder")
    newItem.addItem("Project...")

    file.addItem("Close")
    file.addItem("Close All")
    file.addSeparator()

    file.addItem("Save")
    file.addItem("Save As...")
    file.addItem("Save All")

    val edit = addItem("Edit")
    edit.addItem("Undo")
    edit.addItem("Redo").enabled = false
    edit.addSeparator()

    edit.addItem("Cut")
    edit.addItem("Copy")
    edit.addItem("Paste")
    edit.addSeparator()

    val find = edit.addItem("Find/Replace")

    find.addItem("Google Search", (e: MenuBar.MenuItem) => mainWindow.open(new ExternalResource("http://www.google.com")))
    find.addSeparator()
    find.addItem("Find/Replace...")
    find.addItem("Find Next")
    find.addItem("Find Previous")

    val view = addItem("View")
    val statusBarItem = view.addCheckableItem("Show/Hide Status Bar")
    statusBarItem.checked = true
    view.addItem("Customize Toolbar...")
    view.addSeparator()

    view.addItem("Actual Size")
    view.addItem("Zoom In")
    view.addItem("Zoom Out")
  }

  def getHeader(mainLayout: Layout, tabs: TabSheet): Layout = {

    val titleLayout = new CssLayout() {
      add(new H2("Vaadin Reindeer Theme"))
      add(new SmallText("Documentation and Examples of Available Styles")).sizeUndefined()
    }

    val toggles = new HorizontalLayout { spacing = true }
    val bgColor = new Label { value = "Background color" }
    bgColor.description = "Set the style name for the main layout of this window:<ul><li>Default - no style</li><li>White - Reindeer.LAYOUT_WHITE</li><li>Blue - Reindeer.LAYOUT_BLUE</li><li>Black - Reindeer.LAYOUT_BLACK</li></ul>"
    toggles.add(bgColor)
    val colors = new NativeSelect()
    colors.nullSelectionAllowed = false
    colors.description = "Set the style name for the main layout of this window:<ul><li>Default - no style</li><li>White - Reindeer.LAYOUT_WHITE</li><li>Blue - Reindeer.LAYOUT_BLUE</li><li>Black - Reindeer.LAYOUT_BLACK</li></ul>"
    colors.p.addItem("Default")
    colors.p.addItem("White")
    colors.p.addItem("Blue")
    colors.p.addItem("Black")
    colors.immediate = true
    colors.valueChangeListeners += (event => mainLayout.p.setStyleName(event.property.value.get.toString.toLowerCase))

    colors.value = "Blue"
    toggles.add(colors)
    val transparent = new CheckBox {
      caption = "Transparent tabs"
      immediate = true
      description = "Set style Reindeer.TABSHEET_MINIMAL to the main tab sheet (preview components on different background colors)."

      valueChangeListeners += (event => {
        if (value.get) {
          tabs.styleNames += Reindeer.TABSHEET_MINIMAL
        } else {
          tabs.styleNames -= Reindeer.TABSHEET_MINIMAL
        }

        tabs.components.foreach(c => {
          if (value.get) {
            c.styleNames -= Reindeer.LAYOUT_WHITE
          } else {
            c.styleNames += Reindeer.LAYOUT_WHITE
          }
        })
        // Force refresh
        mainWindow.open(new ExternalResource(getURL toString))
      })
    }
    toggles.add(transparent)

    val userLayout = new CssLayout()
    val user = Label("Welcome, Guest")
    user.sizeUndefined()
    userLayout.add(user)

    val help = new Button { caption = "Help"; clickListeners += openHelpWindow(); styleName = Reindeer.BUTTON_SMALL }
    val logout = new Button { caption = "Logout"; clickListeners += openLogoutWindow(); styleName = Reindeer.BUTTON_SMALL }

    val buttons = new HorizontalLayout {
      spacing = true
      add(component = help, alignment = Alignment.MiddleLeft)
      add(logout)
    }
    userLayout.add(buttons)

    new HorizontalLayout {
      width = (100 percent)
      margin = true
      spacing = true
      add(titleLayout)
      add(component = toggles, alignment = Alignment.MiddleLeft)
      add(component = userLayout, alignment = Alignment.TopRight)
    }
  }

  def openHelpWindow() {
    if ("initialized" != help.data) {
      help.data = "initialized"
      help.closeShortcut = KeyShortcut(KeyCode.ESCAPE)
      help.center
      help.width = 400 px

      help.resizable = false

      help.components += Label.html(<span><strong>How To Use This Application</strong><p>Click around, explore. The purpose of this app is to show you what is possible to achieve with the Reindeer theme and its different styles.</p><p>Most of the UI controls that are visible in this application don't actually do anything. They are purely for show, like the menu items and the components that demostrate the different style names assosiated with the components.</p><strong>So, What Then?</strong><p>Go and use the styles you see here in your own application and make them beautiful!</p></span>)
    }
    if (!mainWindow.childWindows.contains(help)) {
      mainWindow.childWindows += help
    }
  }

  def openLogoutWindow() {
    val logoutLayout = new VerticalLayout { spacing = true }

    val logout = new Window {
      caption = "Logout"
      modal = true
      styleName = Reindeer.WINDOW_BLACK
      width = 260 px;
      resizable = false
      closable = false
      draggable = false
      content = logoutLayout
      closeShortcut = KeyShortcut(KeyCode.ESCAPE)
      components += Label.html(<span>Are you sure you want to log out? You will be redirected to vaadin.com.</span>)
    }

    val buttons = new HorizontalLayout {
      spacing = true
      val yes = add(new Button { caption = "Logout"; styleName = Reindeer.BUTTON_DEFAULT; clickListeners += mainWindow.open(new ExternalResource("http://vaadin.com")) })
      val no = add(new Button { caption = "Cancel"; clickListeners += { event => mainWindow.childWindows -= event.button.window.get } })
    }
    buttons.yes.focus()

    logoutLayout.add(component = buttons, alignment = Alignment.TopCenter)

    mainWindow.childWindows += logout
  }

  class H1(c: String) extends Label {
    value = c
    sizeUndefined()
    styleName = Reindeer.LABEL_H1
  }

  class H2(c: String) extends Label {
    value = c
    sizeUndefined()
    styleName = Reindeer.LABEL_H2
  }

  class SmallText(v: String) extends Label {
    value = v
    styleName = Reindeer.LABEL_SMALL
  }

  class Ruler extends Label {
    value = <hr/>
    contentMode = Label.ContentMode.Xhtml
  }

  class Spacer extends Label {
    width = 20 px
  }
}