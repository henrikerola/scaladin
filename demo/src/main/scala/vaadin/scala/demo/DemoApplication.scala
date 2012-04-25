package vaadin.scala.demo

import com.vaadin.ui.Component
import java.util.Date
import com.vaadin.terminal.ExternalResource
import com.vaadin.ui.themes.Reindeer
import vaadin.scala._
import com.vaadin.ui.themes.Reindeer
import com.vaadin.ui.themes.BaseTheme
import com.vaadin.data.Property.ValueChangeListener
import com.vaadin.data.Property.ValueChangeEvent
import com.vaadin.ui.{ Alignment => VaadinAlignment }
import com.vaadin.event.ShortcutAction.KeyCode

class DemoApplication extends Application(title = "Vaadin Reindeer Theme", applicationTheme = "reindeer") {

  val DATE = new Date()

  val help = new Window { caption = "Help" }

  override def main = {
    new VerticalLayout(size = Full) {
      val tabs = new TabSheet {
        sizeFull()
      }

      add(buildScalaWrappersAd)
      addComponent(getTopMenu)
      add(getHeader(this, tabs))
      add(ratio = 1, component = new CssLayout(size = Full) {
        margin(false, true, true, true)
        add(tabs)
      })

      tabs.add(buildWelcomeScreen)
      tabs.add(buildLabels)
      tabs.add(buildButtons)
      tabs.add(buildTextFields)
      tabs.add(buildSelects)
      tabs.add(buildDateFields)
      //tabs.add(buildTabSheets) // FIXME
      tabs.add(buildPanels)
      tabs.add(buildTables)
      tabs.add(buildWindows(tabs))
      tabs.add(buildSplitPanels)
    }
  }

  def buildScalaWrappersAd(): Layout = {
    new VerticalLayout(width = 100 percent, style = Reindeer.LAYOUT_BLACK) {
      add(alignment = Alignment.MiddleCenter, component = new VerticalLayout(width = 700 px, style = Reindeer.LAYOUT_BLACK, margin = true) {
        add(alignment = Alignment.MiddleCenter, component = new HtmlLabel(width = None, style = Reindeer.LABEL_H2,
          content = <span>This demo is implemented completely in <a href="http://www.scala-lang.org">Scala</a>,</span>))
        add(alignment = Alignment.MiddleCenter, component = new HtmlLabel(width = None, style = Reindeer.LABEL_H2,
          content = <span>with the help of the Scaladin Vaadin addon.</span>))
        add(alignment = Alignment.MiddleCenter, component = new HtmlLabel(width = None, style = Reindeer.LABEL_H2,
          content = <span>You can find more info about the add-on from the <a href="http://vaadin.com/addon/scaladin">Directory page</a>.</span>))
      })
    }
  }

  def buildLabels(): Layout = {
    new GridLayout(columns = 2, rows = 1, width = 560 px, spacing = true, margin = true, caption = "Labels", style = Reindeer.LAYOUT_WHITE) {
      add(new HtmlLabel(<span>Header Style (<code>Reindeer.LABEL_H1</code>)</span>))
      add(new H1("Lorem Ipsum"))

      add(new HtmlLabel(<span>Sub-header Style (<code>Reindeer.LABEL_H2</code>)</span>))
      add(new H2("Lorem Ipsum Dolor"))

      add(new HtmlLabel(<span>Normal Label</span>))
      add(new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit."))

      add(new HtmlLabel(<span>Small Style (<code>Reindeer.LABEL_SMALL</code>)</span>))
      add(new SmallText("Lorem ipsum dolor sit amet, consectetur adipiscing elit."))
    }
  }

  def buildButtons(): Layout = {
    new GridLayout(columns = 2, rows = 1, caption = "Buttons", margin = true, spacing = true, width = 370 px) {
      columnExpandRatio(0, 1)

      add(new HtmlLabel(<span>"Default" Style (<code>Reindeer.BUTTON_DEFAULT</code>)</span>))
      add(new Button(caption = "Default Button", style = Reindeer.BUTTON_DEFAULT))

      add(new HtmlLabel(<span>Normal Button</span>))
      add(new Button("Normal Button"))

      add(new HtmlLabel(<span>Disabled Button (<code>Button.enabled(false)</code>)</span>))
      add(new Button("Disabled Button", enabled = false))

      add(new HtmlLabel(<span>Small Style (<code>Reindeer.BUTTON_SMALL</code>)</span>))
      add(new Button(caption = "Small Button", style = Reindeer.BUTTON_SMALL))

      add(new HtmlLabel(<span>Link Style (<code>Reindeer.BUTTON_LINK</code>)</span>))
      add(new Button(caption = "Link Button", style = BaseTheme.BUTTON_LINK)) //no way to access inherited static members in Scala
    }
  }

  def buildTextFields(): Layout = {
    new GridLayout(columns = 2, rows = 1, caption = "Text fields", margin = true, spacing = true, width = 400 px, style = Reindeer.LAYOUT_WHITE) {
      columnExpandRatio(0, 1)

      add(new HtmlLabel(<span>Normal TextField</span>))
      //      add(new TextField(prompt = "Enter text"))

      add(new HtmlLabel(<span>Small Style (<code>Reindeer.TEXTFIELD_SMALL</code>)</span>))
      //      add(new TextField(style = Reindeer.TEXTFIELD_SMALL, prompt = "Enter text"))

      add(new HtmlLabel(<span>Normal TextArea</span>))
      add(new TextArea { height = 5 em; prompt = "Enter text" })

      add(new HtmlLabel(<span>Small Style TextArea (<code>Reindeer.TEXTFIELD_SMALL</code>)</span>))
      add(new TextArea { height = 5 em; styleNames += Reindeer.TEXTFIELD_SMALL; prompt = "Enter text" })
    }
  }

  def buildSelects(): Layout = {
    val selectsLayout = new HorizontalLayout(spacing = true) {
      margin(top = true)
      val comboBox = add(new ComboBox())
      val nativeSelect = add(new NativeSelect())
      val listSelect = add(new ListSelect())
      val twinColSelect = add(new TwinColSelect())
    }

    for (i <- 0 until 25) {
      selectsLayout.comboBox.p.addItem("Item " + i)
      selectsLayout.nativeSelect.p.addItem("Item " + i)
      selectsLayout.listSelect.p.addItem("Item " + i)
      selectsLayout.twinColSelect.p.addItem("Item " + i)
    }

    new VerticalLayout(caption = "Selects", margin = true, spacing = true) {
      add(new Label(
        "Selects don't currently have any additional style names, but here you can see how they behave with the different background colors."))
      add(selectsLayout)
    }
  }

  def buildDateFields(): Layout = {
    val hl = new HorizontalLayout {
      spacing = true
      margin(top = true)
      add(new PopupDateField { value = DATE; resolution = DateField.Resolution.Minute })
      add(new InlineDateField { value = DATE; resolution = DateField.Resolution.Day })
      add(new InlineDateField { value = DATE; resolution = DateField.Resolution.Year })
    }

    new VerticalLayout(caption = "Date fields", margin = true, spacing = true) {
      add(new Label(
        "Date fields don't currently have any additional style names, but here you can see how they behave with the different background colors."))
      add(hl)
    }
  }

  /*- FIXME FilterableComponentContainer
  def buildTabSheets(): Layout = {
    val closable = new CheckBox(caption = "Closable tabs", immediate = true)
    val hoverOnly = new CheckBox(caption = "Only on hover", immediate = true, enabled = false, description = "Adds style <code>Reindeer.TABSHEET_HOVER_CLOSABLE</code> to all tabs")
    val selectedOnly = new CheckBox(caption = "Selected only", immediate = true, enabled = false, description = "Adds style <code>Reindeer.TABSHEET_SELECTED_CLOSABLE</code> to all tabs")
    val checks = new HorizontalLayout(spacing = true) {
      add(closable)
      add(hoverOnly)
      add(selectedOnly)
    }
    
    val tabSheetLayout = new GridLayout(columns = 2, rows = 1, caption = "Tabs", margin = true, spacing = true, width = 700 px, style = Reindeer.LAYOUT_WHITE) with FilterableComponentContainer {
      setColumnExpandRatio(0, 7)
      setColumnExpandRatio(1, 4)

      add(component = checks, col = 1, row = 0)
      space()

      add(new HtmlLabel(<span>Normal Tabs</span>).p)
      val normalTabSheet = add(new TabSheet(height = 100 px))

      add(new HtmlLabel(<span>Borderless Style (<code>Reindeer.TABSHEET_BORDERLESS</code>)</span>).p)
      val borderlessTabSheet = add(new TabSheet(height = 100 px, style = Reindeer.TABSHEET_BORDERLESS))

      add(new HtmlLabel(<span>Small Style (<code>Reindeer.TABSHEET_SMALL</code>)</span>).p)
      val smallTabSheet = add(new TabSheet(style = Reindeer.TABSHEET_SMALL))

      add(new HtmlLabel(<span>Minimal Style (<code>Reindeer.TABSHEET_MINIMAL</code>)</span>).p)
      val minimalTabSheet = add(new TabSheet(style = Reindeer.TABSHEET_MINIMAL))
    }

    for (i <- 1 until 10) {
      tabSheetLayout.normalTabSheet.addTab(new Label().p).setCaption("Tab " + i)
      tabSheetLayout.borderlessTabSheet.addTab(new Label().p).setCaption("Tab " + i)
      tabSheetLayout.smallTabSheet.addTab(new Label().p).setCaption("Tab " + i)
      tabSheetLayout.minimalTabSheet.addTab(new Label().p).setCaption("Tab " + i)
    }

    closable.addListener(event => {

      val eventValue = event.getButton.booleanValue
      tabSheetLayout.normalTabSheet.getComponents.foreach(c => tabSheetLayout.normalTabSheet.getTab(c).setClosable(eventValue))
      tabSheetLayout.borderlessTabSheet.getComponents.foreach(c => tabSheetLayout.borderlessTabSheet.getTab(c).setClosable(eventValue))
      tabSheetLayout.smallTabSheet.getComponents.foreach(c => tabSheetLayout.smallTabSheet.getTab(c).setClosable(eventValue))
      tabSheetLayout.minimalTabSheet.getComponents.foreach(c => tabSheetLayout.minimalTabSheet.getTab(c).setClosable(eventValue))

      hoverOnly.setEnabled(eventValue)
      selectedOnly.setEnabled(eventValue)
    })

    hoverOnly.addListener(event => {
      if (event.getButton.booleanValue)
        tabSheetLayout filter (_.isInstanceOf[TabSheet]) foreach (_.addStyleName(Reindeer.TABSHEET_HOVER_CLOSABLE))
      else
        tabSheetLayout filter (_.isInstanceOf[TabSheet]) foreach (_.removeStyleName(Reindeer.TABSHEET_HOVER_CLOSABLE))
    })

    selectedOnly.addListener(event => {
      if (event.getButton.booleanValue)
        tabSheetLayout filter (_.isInstanceOf[TabSheet]) foreach (_.addStyleName(Reindeer.TABSHEET_SELECTED_CLOSABLE))
      else
        tabSheetLayout filter (_.isInstanceOf[TabSheet]) foreach (_.removeStyleName(Reindeer.TABSHEET_SELECTED_CLOSABLE))
    })

    tabSheetLayout
  }*/

  def buildPanels(): Layout = {
    new GridLayout(columns = 2, rows = 1, caption = "Panels", margin = true, spacing = true, width = 700 px, style = Reindeer.LAYOUT_WHITE) {
      columnExpandRatio(0, 2)
      columnExpandRatio(1, 5)

      add(new HtmlLabel(<span>Normal Panel</span>))
      val normalPanel = add(new Panel { caption = "Normal Panel"; height = 100 px })
      normalPanel.components += new Label("Panel content")

      add(new HtmlLabel(<span>Light Style (<code>Reindeer.PANEL_LIGHT</code>)</span>))
      val lightPanel = add(new Panel { caption = "Light Style Panel"; styleNames += Reindeer.PANEL_LIGHT })
      lightPanel.components += new Label("Panel content")
    }
  }

  def buildTables(): Layout = {
    val tableLayout = new GridLayout(columns = 2, rows = 1, caption = "Tables", margin = true, spacing = true, width = 700 px, style = Reindeer.LAYOUT_WHITE)
    tableLayout.columnExpandRatio(0, 3)
    tableLayout.columnExpandRatio(1, 5)

    for (i <- 0 until 4) {
      val table = new Table(width = 100 percent)
      table.pageLength = 4
      table.p.setSelectable(true)
      table.columnCollapsingAllowed = true
      table.columnReorderingAllowed = true

      i match {

        case 0 => tableLayout.add(new HtmlLabel(<span>Normal Table</span>))

        case 1 =>
          table.p.setStyleName("strong")
          tableLayout.add(new HtmlLabel(<span>Strong Style (<code>Reindeer.TABLE_STRONG</code>)</span>))

        case 2 =>
          table.p.setStyleName("borderless")
          tableLayout.add(new HtmlLabel(<span>Borderless Style (<code>Reindeer.TABLE_BORDERLESS</code>)</span>))

        case 3 =>
          table.p.setStyleName("borderless strong")
          tableLayout.add(new Label("Borderless & Strong Combined"))

      }

      table.p.addContainerProperty("First", classOf[String], null)
      table.p.addContainerProperty("Second", classOf[String], null)
      table.p.addContainerProperty("Third", classOf[String], null)

      for (j <- 0 until 100) {
        table.p.addItem(Array("Foo " + j, "Bar value " + j,
          "Last column value " + j), j)
      }

      tableLayout.add(table)
    }

    tableLayout
  }

  def buildWindows(tabs: TabSheet): Layout = {
    val windowLayout = new CssLayout(caption = "Windows")

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
      components += new HtmlLabel(<span><code>Window.setResizable(false)</code></span>)
    }

    val lightWindow = new Window {
      caption = " Light window"
      width = (280 px)
      height = (230 px)
      styleNames += Reindeer.WINDOW_LIGHT
      positionX = 40
      positionY = 460
      components += new HtmlLabel(<span><code>Reindeer.WINDOW_LIGHT</code></span>)
    }

    val blackWindow = new Window {
      caption = "Black window"
      width = (280 px)
      height = (230 px)
      styleNames += Reindeer.WINDOW_BLACK
      positionX = 350
      positionY = 460
      components += new HtmlLabel(<span><code>Reindeer.WINDOW_BLACK</code></span>)
    }

    /*- FIXME:
    tabs.addListener(event => {
      if (event.getTabSheet.getSelectedTab == windowLayout.p) {
        mainWindow.childWindows += (normalWindow, notResizableWindow, lightWindow, blackWindow)

      } else {
        if (normalWindow.parent == mainWindow) {
          mainWindow.childWindows -= normalWindow
        }

        if (notResizableWindow.parent == mainWindow) {
          mainWindow.childWindows -= notResizableWindow
        }

        if (lightWindow.parent == mainWindow) {
          mainWindow.childWindows -= lightWindow
        }

        if (blackWindow.parent == mainWindow) {
          mainWindow.childWindows -= blackWindow
        }
      }
    })*/

    windowLayout
  }

  def buildSplitPanels(): Layout = {
    new GridLayout(columns = 2, rows = 1, caption = "Split panels", margin = true, spacing = true, width = 400 px, style = Reindeer.LAYOUT_WHITE) {
      columnExpandRatio(0, 1)

      add(new HtmlLabel(<span>Normal SplitPanel</span>))
      add(new HorizontalSplitPanel(width = 100 px, height = 200 px))

      add(new HtmlLabel(<span>Small Style (<code>Reindeer.SPLITPANEL_SMALL</code>)</span>))
      add(new HorizontalSplitPanel(width = 100 px, height = 200 px, style = Reindeer.SPLITPANEL_SMALL))

    }
  }

  def buildWelcomeScreen(): Layout = {
    val l = new VerticalLayout(margin = true, spacing = true, caption = "Welcome", style = Reindeer.LAYOUT_WHITE)

    val welcomeText1 = <span><h4>A Complete Theme</h4><p>The Reindeer theme is a complete, general purpose theme suitable for almost all types of applications.</p><p>While a general purpose theme should not try to cater for every possible need, the Reindeer theme provides a set of useful styles that you can use to make the interface a bit more lively and interesing, emphasizing different parts of the application.</p></span>
    val welcomeText2 = <span><h4>Everything You Need Is Here</h4><p>Everything you see inside this application, all the different styles, are provided by the Reindeer theme, out-of-the-box. That means you don't necessarily need to create any custom CSS for your application: you can build a cohesive result writing plain Java code.</p><p>A little creativity, good organization and careful typography carries a long way.</p></span>
    val welcomeText3 = <span><h4>The Names of The Styles</h4><p>Look for a class named <code>Reindeer</code> inside the Vaadin JAR (<code>com.vaadin.ui.themes.Reindeer</code>). All the available style names are documented and available there as constants, prefixed by component names, e.g. <code>Reindeer.BUTTON_SMALL</code>.</p></span>

    val texts = new HorizontalLayout(spacing = true, width = 100 percent) {
      margin = Margin(bottom = true)
      add(new HtmlLabel(welcomeText1), 1)
      add(new Spacer())
      add(new HtmlLabel(welcomeText2), 1)
      add(new Spacer())
      add(new HtmlLabel(content = welcomeText3), 1)
    }

    val stylesHeading = new H2("One Theme &ndash; Three Styles")
    stylesHeading.contentMode = Label.ContentMode.Xhtml

    val colors = new HorizontalLayout(100 percent, 250 px)

    val whiteLayout = new CssLayout(size = Full, style = Reindeer.LAYOUT_WHITE, margin = true) {
      add(new H1("White"))
      add(new HtmlLabel(<span><p><strong><code>Reindeer.LAYOUT_WHITE</code></strong></p><p>Changes the background to white. Has no other effect on contained components, they all behave like on the default gray background.</p></span>))
    }

    val blueLayout = new CssLayout(size = Full, style = Reindeer.LAYOUT_BLUE, margin = true) {
      add(new H1("Blue"))
      add(new HtmlLabel(<span><p><strong><code>Reindeer.LAYOUT_BLUE</code></strong></p><p>Changes the background to a shade of blue. A very few components have any difference here compared to the white style.</p></span>))
    }

    val blackLayout = new CssLayout(size = Full, style = Reindeer.LAYOUT_BLACK, margin = true) {
      add(new H1("Black"))
      add(new HtmlLabel(<span><p><strong><code>Reindeer.LAYOUT_BLACK</code></strong></p><p>Reserved for small parts of the application. Or alternatively, use for the whole application.</p><p><strong>This style is non-overridable</strong>, meaning that everything you place inside it will transform to their corresponding black styles when available, excluding Labels.</p></span>))
    }

    colors.add(whiteLayout)
    colors.add(blueLayout)
    colors.add(blackLayout)

    val note = new HtmlLabel(<span><p>Note, that you cannot nest the layout styles infinitely inside each other. After a couple levels, the result will be undefined, due to limitations in CSS (which are in fact caused by Internet Explorer 6).</p></span>)

    l.add(new CssLayout(margin = true, width = 100 percent) {
      add(new H1("Guide to the Reindeer Theme"))
      add(new Ruler())
      add(texts)
      add(stylesHeading)
      add(new Ruler())
      add(new HtmlLabel(<span><p>You can easily change the feel of some parts of your application by using the three layout styles provided by Reindeer: white, blue and black. The colored area contains the margins of the layout. All contained components will switch their style if an alternative style is available for that color.</p></span>))
      add(colors)
      add(note)
    })

    return l
  }

  def getTopMenu(): MenuBar = {
    val menubar = new MenuBar(width = 100 percent)
    val file = menubar.addItem("File", null)
    val newItem = file.addItem("New", null)
    file.addItem("Open file...", null)
    file addSeparator

    newItem.addItem("File", null)
    newItem.addItem("Folder", null)
    newItem.addItem("Project...", null)

    file.addItem("Close", null)
    file.addItem("Close All", null)
    file.addSeparator()

    file.addItem("Save", null)
    file.addItem("Save As...", null)
    file.addItem("Save All", null)

    val edit = menubar.addItem("Edit", null)
    edit.addItem("Undo", null)
    edit.addItem("Redo", null).setEnabled(false)
    edit.addSeparator()

    edit.addItem("Cut", null)
    edit.addItem("Copy", null)
    edit.addItem("Paste", null)
    edit.addSeparator()

    val find = edit.addItem("Find/Replace", null)

    find.addItem("Google Search", new MenuBarCommand(_ => getMainWindow.open(new ExternalResource("http://www.google.com"))))
    find.addSeparator()
    find.addItem("Find/Replace...", null)
    find.addItem("Find Next", null)
    find.addItem("Find Previous", null)

    val view = menubar.addItem("View", null)
    val statusBarItem = view.addItem("Show/Hide Status Bar", null)
    statusBarItem.setCheckable(true)
    statusBarItem.setChecked(true)
    view.addItem("Customize Toolbar...", null)
    view.addSeparator()

    view.addItem("Actual Size", null)
    view.addItem("Zoom In", null)
    view.addItem("Zoom Out", null)

    menubar
  }

  def getHeader(mainLayout: Layout, tabs: TabSheet): Layout = {

    val titleLayout = new CssLayout() {
      add(new H2("Vaadin Reindeer Theme"))
      add(new SmallText("Documentation and Examples of Available Styles")).sizeUndefined()
    }

    val toggles = new HorizontalLayout { spacing = true }
    val bgColor = new Label("Background color")
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
    // FIXME:
    //colors.addListener(event => mainLayout.p.setStyleName(event.getProperty.getValue.toString.toLowerCase))

    colors.value = "Blue"
    toggles.add(colors)
    val transparent = new CheckBox(caption = "Transparent tabs", immediate = true, action = event =>
      {
        if (event.getButton.booleanValue) {
          tabs.styleNames += Reindeer.TABSHEET_MINIMAL
        } else {
          tabs.styleNames -= Reindeer.TABSHEET_MINIMAL
        }

        tabs.components.foreach(c => {
          if (event.getButton.booleanValue) {
            c.styleNames -= Reindeer.LAYOUT_WHITE
          } else {
            c.styleNames += Reindeer.LAYOUT_WHITE
          }
        })
        // Force refresh
        getMainWindow.open(new ExternalResource(getURL))
      })
    transparent.description = "Set style Reindeer.TABSHEET_MINIMAL to the main tab sheet (preview components on different background colors)."
    toggles.add(transparent)

    val userLayout = new CssLayout()
    val user = new Label("Welcome, Guest")
    user.sizeUndefined()
    userLayout.add(user)

    val help = new Button(caption = "Help", action = _ => openHelpWindow(), style = Reindeer.BUTTON_SMALL)
    val logout = new Button(caption = "Logout", action = _ => openLogoutWindow(), style = Reindeer.BUTTON_SMALL)

    val buttons = new HorizontalLayout(spacing = true) {
      add(component = help, alignment = Alignment.MiddleLeft)
      add(logout)
    }
    userLayout.add(buttons)

    new HorizontalLayout(width = 100 percent, margin = true, spacing = true) {
      add(titleLayout)
      add(component = toggles, alignment = Alignment.MiddleLeft)
      add(component = userLayout, alignment = Alignment.TopRight)
    }
  }

  def openHelpWindow() {
    if ("initialized" != help.data) {
      help.data = "initialized"
      help.setCloseShortcut(KeyCode.ESCAPE)
      help.center
      help.width = 400 px

      help.resizable = false

      help.components += new HtmlLabel(<span><strong>How To Use This Application</strong><p>Click around, explore. The purpose of this app is to show you what is possible to achieve with the Reindeer theme and its different styles.</p><p>Most of the UI controls that are visible in this application don't actually do anything. They are purely for show, like the menu items and the components that demostrate the different style names assosiated with the components.</p><strong>So, What Then?</strong><p>Go and use the styles you see here in your own application and make them beautiful!</p></span>)
    }
    if (!mainWindow.childWindows.contains(help)) {
      mainWindow.childWindows += help
    }
  }

  def openLogoutWindow() {
    val logoutLayout = new VerticalLayout(spacing = true)

    val logout = new Window {
      caption = "Logout"
      modal = true
      styleNames += Reindeer.WINDOW_BLACK
      width = 260 px;
      resizable = false
      closable = false
      draggable = false
      content = logoutLayout
      setCloseShortcut(KeyCode.ESCAPE)
      components += new HtmlLabel(<span>Are you sure you want to log out? You will be redirected to vaadin.com.</span>)
    }

    val buttons = new HorizontalLayout(spacing = true) {
      val yes = add(new Button(caption = "Logout", style = Reindeer.BUTTON_DEFAULT, action = _ => getMainWindow.open(new ExternalResource("http://vaadin.com"))))
      val no = add(new Button("Cancel", action = event => getMainWindow.removeWindow(event.button.window)))
    }
    buttons.yes.focus()

    logoutLayout.add(component = buttons, alignment = Alignment.TopCenter)

    mainWindow.childWindows += logout
  }

  class H1(caption: String) extends Label(caption) {
    sizeUndefined()
    p.setStyleName(Reindeer.LABEL_H1)
  }

  class H2(caption: String) extends Label(caption) {
    sizeUndefined()
    p.setStyleName(Reindeer.LABEL_H2)
  }

  class SmallText(caption: String) extends Label(caption) {
    p.setStyleName(Reindeer.LABEL_SMALL)
  }

  class Ruler extends HtmlLabel(<hr/>)

  class Spacer extends Label(width = 20 px)
}