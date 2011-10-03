package vaadin.scala.demo

import com.vaadin.ui.Component
import java.util.Date
import com.vaadin.Application
import com.vaadin.terminal.ExternalResource
import com.vaadin.ui.themes.Reindeer
import vaadin.scala._
import com.vaadin.ui.Layout
import com.vaadin.ui.themes.Reindeer
import com.vaadin.ui.themes.BaseTheme
import com.vaadin.ui.ComboBox
import com.vaadin.ui.ListSelect
import com.vaadin.ui.TwinColSelect
import com.vaadin.data.Property.ValueChangeListener
import com.vaadin.data.Property.ValueChangeEvent
import com.vaadin.ui.Alignment
import com.vaadin.event.ShortcutAction.KeyCode

class DemoApplication extends Application {

  val DATE = new Date(2009 - 1900, 6 - 1, 2)

  val help = new Window("Help")

  override def init() = {
    setTheme("reindeer")
    val mainWindow = new Window(caption = "Vaadin Reindeer Theme")
    setMainWindow(mainWindow)

    val mainView = buildMainView
    mainView.add(index = 0, component = buildScalaWrappersAd)
    mainWindow.setContent(mainView)
  }

  def buildMainView(): VerticalLayout = {

    val tabs = new TabSheet(100 percent, 100 percent)

    val margin = new CssLayout(100 percent, 100 percent)

    margin.setMargin(false, true, true, true)
    margin.add(tabs)

    tabs.addComponent(buildWelcomeScreen)
    tabs.addComponent(buildLabels)
    tabs.addComponent(buildButtons)
    tabs.addComponent(buildTextFields)
    tabs.addComponent(buildSelects)
    tabs.addComponent(buildDateFields)
    tabs.addComponent(buildTabSheets)
    tabs.addComponent(buildPanels)
    tabs.addComponent(buildTables)
    tabs.addComponent(buildWindows(tabs))
    tabs.addComponent(buildSplitPanels)

    val mainLayout = new VerticalLayout(100 percent, 100 percent) {
      add(getTopMenu)
      add(getHeader(this, tabs))
      add(component = margin, ratio = 1)
    }

    mainLayout
  }

  def buildScalaWrappersAd(): Layout = {
    new VerticalLayout(width = 100 percent, style = Reindeer.LAYOUT_BLACK) {
      add(alignment = Alignment.MIDDLE_CENTER, component = new HtmlLabel(width = null, style = Reindeer.LABEL_H2,
        content = "This demo is implemented completely in <a href=\"http://www.scala-lang.org\">Scala</a>. "
          + "This was made possible by the scala-wrappers Vaadin addon. "
          + "You can find the sources for both this demo and the addon <a href=\"http://dev.vaadin.com/svn/incubator/scala-wrappers\">here</a>."))
    }
  }

  def buildLabels(): Layout = {
    new GridLayout(columns = 2, rows = 1, width = 560 px, spacing = true, margin = true, caption = "Labels", style = Reindeer.LAYOUT_WHITE) {
      add(new HtmlLabel("Header Style (<code>Reindeer.LABEL_H1</code>)"))
      add(new H1("Lorem Ipsum"))

      add(new HtmlLabel("Sub-header Style (<code>Reindeer.LABEL_H2</code>)"))
      add(new H2("Lorem Ipsum Dolor"))

      add(new HtmlLabel("Normal Label"))
      add(new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit."))

      add(new HtmlLabel("Small Style (<code>Reindeer.LABEL_SMALL</code>)"))
      add(new SmallText("Lorem ipsum dolor sit amet, consectetur adipiscing elit."))
    }
  }

  def buildButtons(): Layout = {
    new GridLayout(columns = 2, rows = 1, caption = "Buttons", margin = true, spacing = true, width = 370 px) {
      setColumnExpandRatio(0, 1)

      add(new HtmlLabel("\"Default\" Style (<code>Reindeer.BUTTON_DEFAULT</code>)"))
      add(new Button(caption = "Default Button", style = Reindeer.BUTTON_DEFAULT))

      add(new HtmlLabel("Normal Button"))
      add(new Button("Normal Button"))

      add(new HtmlLabel("Disabled Button (<code>Button.setEnabled(false)</code>)"))
      add(new Button("Disabled Button", enabled = false))

      add(new HtmlLabel("Small Style (<code>Reindeer.BUTTON_SMALL</code>)"))
      add(new Button(caption = "Small Button", style = Reindeer.BUTTON_SMALL))

      add(new HtmlLabel("Link Style (<code>Reindeer.BUTTON_LINK</code>)"))
      add(new Button(caption = "Link Button", style = BaseTheme.BUTTON_LINK)) //no way to access inherited static members in Scala
    }
  }

  def buildTextFields(): Layout = {
    new GridLayout(columns = 2, rows = 1, caption = "Text fields", margin = true, spacing = true, width = 400 px, style = Reindeer.LAYOUT_WHITE) {
      setColumnExpandRatio(0, 1)

      add(new HtmlLabel("Normal TextField"))
      add(new TextField(prompt = "Enter text"))

      add(new HtmlLabel("Small Style (<code>Reindeer.TEXTFIELD_SMALL</code>)"))
      add(new TextField(style = Reindeer.TEXTFIELD_SMALL, prompt = "Enter text"))

      add(new HtmlLabel("Normal TextArea"))
      add(new TextArea(height = 5 em, prompt = "Enter text"))

      add(new HtmlLabel("Small Style TextArea (<code>Reindeer.TEXTFIELD_SMALL</code>)"))
      add(new TextArea(height = 5 em, style = Reindeer.TEXTFIELD_SMALL, prompt = "Enter text"))
    }
  }

  def buildSelects(): Layout = {
    val selectsLayout = new HorizontalLayout(spacing = true) {
      setMargin(true, false, false, false)
      val comboBox = add(new ComboBox())
      val nativeSelect = add(new NativeSelect())
      val listSelect = add(new ListSelect())
      val twinColSelect = add(new TwinColSelect())
    }

    for (i <- 0 until 25) {
      selectsLayout.comboBox.addItem("Item " + i)
      selectsLayout.nativeSelect.addItem("Item " + i)
      selectsLayout.listSelect.addItem("Item " + i)
      selectsLayout.twinColSelect.addItem("Item " + i)
    }

    new VerticalLayout(caption = "Selects", margin = true, spacing = true) {
      add(new Label(
        "Selects don't currently have any additional style names, but here you can see how they behave with the different background colors."))
      add(selectsLayout)
    }
  }

  def buildDateFields(): Layout = {
    val hl = new HorizontalLayout(spacing = true) {
      setMargin(true, false, false, false)
      add(new DateField(value = DATE, resolution = com.vaadin.ui.DateField.RESOLUTION_MIN))
      add(new InlineDateField(value = DATE, resolution = com.vaadin.ui.DateField.RESOLUTION_DAY))
      add(new InlineDateField(value = DATE, resolution = com.vaadin.ui.DateField.RESOLUTION_YEAR))
    }

    new VerticalLayout(caption = "Date fields", margin = true, spacing = true) {
      add(new Label(
        "Date fields don't currently have any additional style names, but here you can see how they behave with the different background colors."))
      add(hl)
    }
  }

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

      add(new HtmlLabel("Normal Tabs"))
      val normalTabSheet = add(new TabSheet(height = 100 px))

      add(new HtmlLabel("Borderless Style (<code>Reindeer.TABSHEET_BORDERLESS</code>)"))
      val borderlessTabSheet = add(new TabSheet(height = 100 px, style = Reindeer.TABSHEET_BORDERLESS))

      add(new HtmlLabel("Small Style (<code>Reindeer.TABSHEET_SMALL</code>)"))
      val smallTabSheet = add(new TabSheet(style = Reindeer.TABSHEET_SMALL))

      add(new HtmlLabel("Minimal Style (<code>Reindeer.TABSHEET_MINIMAL</code>)"))
      val minimalTabSheet = add(new TabSheet(style = Reindeer.TABSHEET_MINIMAL))
    }

    for (i <- 1 until 10) {
      tabSheetLayout.normalTabSheet.addTab(new Label()).setCaption("Tab " + i)
      tabSheetLayout.borderlessTabSheet.addTab(new Label()).setCaption("Tab " + i)
      tabSheetLayout.smallTabSheet.addTab(new Label()).setCaption("Tab " + i)
      tabSheetLayout.minimalTabSheet.addTab(new Label()).setCaption("Tab " + i)
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
  }

  def buildPanels(): Layout = {
    new GridLayout(columns = 2, rows = 1, caption = "Panels", margin = true, spacing = true, width = 700 px, style = Reindeer.LAYOUT_WHITE) {
      setColumnExpandRatio(0, 2)
      setColumnExpandRatio(1, 5)

      add(new HtmlLabel("Normal Panel"))
      val normalPanel = add(new Panel(caption = "Normal Panel", height = 100 px))
      normalPanel.add(new Label("Panel content"))

      add(new HtmlLabel("Light Style (<code>Reindeer.PANEL_LIGHT</code>)"))
      val lightPanel = add(new Panel(caption = "Light Style Panel", style = Reindeer.PANEL_LIGHT))
      lightPanel.add(new Label("Panel content"))
    }
  }

  def buildTables(): Layout = {
    val tableLayout = new GridLayout(columns = 2, rows = 1, caption = "Tables", margin = true, spacing = true, width = 700 px, style = Reindeer.LAYOUT_WHITE)
    tableLayout.setColumnExpandRatio(0, 3)
    tableLayout.setColumnExpandRatio(1, 5)

    for (i <- 0 until 4) {
      val table = new Table(width = 100 percent)
      table.setPageLength(4)
      table.setSelectable(true)
      table.setColumnCollapsingAllowed(true)
      table.setColumnReorderingAllowed(true)

      i match {

        case 0 => tableLayout.addComponent(new HtmlLabel("Normal Table"))

        case 1 =>
          table.setStyleName("strong")
          tableLayout.addComponent(new HtmlLabel("Strong Style (<code>Reindeer.TABLE_STRONG</code>)"))

        case 2 =>
          table.setStyleName("borderless")
          tableLayout.addComponent(new HtmlLabel("Borderless Style (<code>Reindeer.TABLE_BORDERLESS</code>)"))

        case 3 =>
          table.setStyleName("borderless strong")
          tableLayout.addComponent(new Label("Borderless & Strong Combined"))

      }

      table addContainerProperty ("First", classOf[String], null)
      table addContainerProperty ("Second", classOf[String], null)
      table addContainerProperty ("Third", classOf[String], null)

      for (j <- 0 until 100) {
        table.addItem(Array("Foo " + j, "Bar value " + j,
          "Last column value " + j), j)
      }

      tableLayout.addComponent(table)
    }

    tableLayout
  }

  def buildWindows(tabs: TabSheet): Layout = {
    val windowLayout = new CssLayout(caption = "Windows")

    val normalWindow = new Window(caption = "Normal window", width = 280 px, height = 180 px)
    normalWindow.setPositionX(40)
    normalWindow.setPositionY(160)

    val notResizableWindow = new Window(caption = "Window, no resize", width = 280 px, height = 180 px, resizable = false)
    notResizableWindow.setPositionX(350)
    notResizableWindow.setPositionY(160)
    notResizableWindow.addComponent(new HtmlLabel("<code>Window.setResizable(false)</code>"))

    val lightWindow = new Window(caption = " Light window", width = 280 px, height = 230 px, style = Reindeer.WINDOW_LIGHT)
    lightWindow.setPositionX(40)
    lightWindow.setPositionY(370)
    lightWindow.addComponent(new HtmlLabel("<code>Reindeer.WINDOW_LIGHT</code>"))

    val blackWindow = new Window(caption = "Black window", width = 280 px, height = 230 px, style = Reindeer.WINDOW_BLACK)
    blackWindow.setPositionX(350)
    blackWindow.setPositionY(370)
    blackWindow.addComponent(new HtmlLabel("<code>Reindeer.WINDOW_BLACK</code>"))

    tabs.addListener(event => {
      val mainWindow = getMainWindow

      if (event.getTabSheet.getSelectedTab == windowLayout) {
        mainWindow.addWindow(normalWindow)
        mainWindow.addWindow(notResizableWindow)
        mainWindow.addWindow(lightWindow)
        mainWindow.addWindow(blackWindow)

      } else {
        if (normalWindow.getParent == mainWindow) {
          mainWindow.removeWindow(normalWindow)
        }

        if (notResizableWindow.getParent == mainWindow) {
          mainWindow.removeWindow(notResizableWindow)
        }

        if (lightWindow.getParent == mainWindow) {
          mainWindow.removeWindow(lightWindow)
        }

        if (blackWindow.getParent == mainWindow) {
          mainWindow.removeWindow(blackWindow)
        }
      }
    })

    windowLayout
  }

  def buildSplitPanels(): Layout = {
    new GridLayout(columns = 2, rows = 1, caption = "Split panels", margin = true, spacing = true, width = 400 px, style = Reindeer.LAYOUT_WHITE) {
      setColumnExpandRatio(0, 1)

      add(new HtmlLabel("Normal SplitPanel"))
      add(new HorizontalSplitPanel(width = 100 px, height = 200 px))

      add(new HtmlLabel("Small Style (<code>Reindeer.SPLITPANEL_SMALL</code>)"))
      add(new HorizontalSplitPanel(width = 100 px, height = 200 px, style = Reindeer.SPLITPANEL_SMALL))

    }
  }

  def buildWelcomeScreen(): Layout = {
    val l = new VerticalLayout(margin = true, spacing = true, caption = "Welcome", style = Reindeer.LAYOUT_WHITE)

    val margin = new CssLayout(margin = true, width = 100 percent)
    l.addComponent(margin)

    val welcomeText1 = "<h4>A Complete Theme</h4><p>The Reindeer theme is a complete, general purpose theme suitable for almost all types of applications.<p>While a general purpose theme should not try to cater for every possible need, the Reindeer theme provides a set of useful styles that you can use to make the interface a bit more lively and interesing, emphasizing different parts of the application.</p>"
    val welcomeText2 = "<h4>Everything You Need Is Here</h4><p>Everything you see inside this application, all the different styles, are provided by the Reindeer theme, out-of-the-box. That means you don't necessarily need to create any custom CSS for your application: you can build a cohesive result writing plain Java code.</p><p>A little creativity, good organization and careful typography carries a long way."
    val welcomeText3 = "<h4>The Names of The Styles</h4><p>Look for a class named <code>Reindeer</code> inside the Vaadin JAR (<code>com.vaadin.ui.themes.Reindeer</code>). All the available style names are documented and available there as constants, prefixed by component names, e.g. <code>Reindeer.BUTTON_SMALL</code>."

    val texts = new HorizontalLayout(spacing = true, width = 100 percent) {
      setMargin(false, false, true, false)
      add(new HtmlLabel(welcomeText1), 1)
      add(new Spacer())
      add(new HtmlLabel(welcomeText2), 1)
      add(new Spacer())
      add(new HtmlLabel(content = welcomeText3), 1)
    }

    val stylesHeading = new H2("One Theme &ndash; Three Styles")
    stylesHeading.setContentMode(com.vaadin.ui.Label.CONTENT_XHTML)

    val colors = new HorizontalLayout(100 percent, 250 px)

    val whiteLayout = new CssLayout(style = Reindeer.LAYOUT_WHITE, margin = true)
    whiteLayout.setSizeFull()
    whiteLayout.add(new H1("White"))
      .add(new HtmlLabel("<p><strong><code>Reindeer.LAYOUT_WHITE</code></strong></p><p>Changes the background to white. Has no other effect on contained components, they all behave like on the default gray background."))

    val blueLayout = new CssLayout(style = Reindeer.LAYOUT_BLUE, margin = true)
    blueLayout.setSizeFull()
    blueLayout.add(new H1("Blue"))
      .add(new HtmlLabel("<p><strong><code>Reindeer.LAYOUT_BLUE</code></strong></p><p>Changes the background to a shade of blue. A very few components have any difference here compared to the white style."))

    val blackLayout = new CssLayout(style = Reindeer.LAYOUT_BLACK, margin = true)
    blackLayout.setSizeFull()
    blackLayout.add(new H1("Black"))
      .addComponent(new HtmlLabel("<p><strong><code>Reindeer.LAYOUT_BLACK</code></strong></p><p>Reserved for small parts of the application. Or alternatively, use for the whole application.</p><p><strong>This style is non-overridable</strong>, meaning that everything you place inside it will transform to their corresponding black styles when available, excluding Labels.</p>"))

    colors.addComponent(whiteLayout)
    colors.addComponent(blueLayout)
    colors.addComponent(blackLayout)

    val note = new HtmlLabel("<p>Note, that you cannot nest the layout styles infinitely inside each other. After a couple levels, the result will be undefined, due to limitations in CSS (which are in fact caused by Internet Explorer 6).</p>")

    margin.add(new H1("Guide to the Reindeer Theme"))
      .add(new Ruler())
      .add(texts)
      .add(stylesHeading)
      .add(new Ruler())
      .add(new HtmlLabel("<p>You can easily change the feel of some parts of your application by using the three layout styles provided by Reindeer: white, blue and black. The colored area contains the margins of the layout. All contained components will switch their style if an alternative style is available for that color.</p>"))
      .add(colors)
      .addComponent(note)

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

    val titleLayout = new CssLayout()
    val title = new H2("Vaadin Reindeer Theme")
    val description = new SmallText(
      "Documentation and Examples of Available Styles")
    description.setSizeUndefined()
    titleLayout.add(title)
      .add(description)

    val toggles = new HorizontalLayout(spacing = true)
    val bgColor = new Label("Background color")
    bgColor.setDescription("Set the style name for the main layout of this window:<ul><li>Default - no style</li><li>White - Reindeer.LAYOUT_WHITE</li><li>Blue - Reindeer.LAYOUT_BLUE</li><li>Black - Reindeer.LAYOUT_BLACK</li></ul>")
    toggles.addComponent(bgColor)
    val colors = new NativeSelect()
    colors.setNullSelectionAllowed(false)
    colors.setDescription("Set the style name for the main layout of this window:<ul><li>Default - no style</li><li>White - Reindeer.LAYOUT_WHITE</li><li>Blue - Reindeer.LAYOUT_BLUE</li><li>Black - Reindeer.LAYOUT_BLACK</li></ul>")
    colors.addItem("Default")
    colors.addItem("White")
    colors.addItem("Blue")
    colors.addItem("Black")
    colors.setImmediate(true)
    colors.addListener(event => mainLayout.setStyleName(event.getProperty.getValue.toString.toLowerCase))

    colors.setValue("Blue")
    toggles.addComponent(colors)
    val transparent = new CheckBox(caption = "Transparent tabs", immediate = true, action = event =>
      {
        if (event.getButton.booleanValue) {
          tabs.setStyleName(Reindeer.TABSHEET_MINIMAL)
        } else {
          tabs.removeStyleName(Reindeer.TABSHEET_MINIMAL)
        }

        tabs.getComponents.foreach(c => {
          if (event.getButton.booleanValue) {
            c.removeStyleName(Reindeer.LAYOUT_WHITE)
          } else {
            c.addStyleName(Reindeer.LAYOUT_WHITE)
          }
        })
        // Force refresh
        getMainWindow.open(new ExternalResource(getURL))
      })
    transparent
      .setDescription("Set style Reindeer.TABSHEET_MINIMAL to the main tab sheet (preview components on different background colors).")
    toggles.addComponent(transparent)

    val userLayout = new CssLayout()
    val user = new Label("Welcome, Guest")
    user.setSizeUndefined()
    userLayout.addComponent(user)

    val help = new Button(caption = "Help", action = _ => openHelpWindow(), style = Reindeer.BUTTON_SMALL)
    val logout = new Button(caption = "Logout", action = _ => openLogoutWindow(), style = Reindeer.BUTTON_SMALL)

    val buttons = new HorizontalLayout(spacing = true) {
      add(component = help, alignment = Alignment.MIDDLE_LEFT)
      add(logout)
    }
    userLayout.addComponent(buttons)

    new HorizontalLayout(width = 100 percent, margin = true, spacing = true) {
      add(titleLayout)
      add(component = toggles, alignment = Alignment.MIDDLE_LEFT)
      add(component = userLayout, alignment = Alignment.TOP_RIGHT)
    }
  }

  def openHelpWindow() {
    if ("initialized" != help.getData) {
      help.setData("initialized")
      help.setCloseShortcut(KeyCode.ESCAPE)

      help.center()
      help.setWidth(400 px)
      help.setResizable(false)

      val helpText = new HtmlLabel("<strong>How To Use This Application</strong><p>Click around, explore. The purpose of this app is to show you what is possible to achieve with the Reindeer theme and its different styles.</p><p>Most of the UI controls that are visible in this application don't actually do anything. They are purely for show, like the menu items and the components that demostrate the different style names assosiated with the components.</p><strong>So, What Then?</strong><p>Go and use the styles you see here in your own application and make them beautiful!")
      help.addComponent(helpText)
    }
    if (!getMainWindow.getChildWindows.contains(help)) {
      getMainWindow.addWindow(help)
    }
  }

  def openLogoutWindow() {
    val logoutLayout = new VerticalLayout(spacing = true)

    val logout = new Window(caption = "Logout", modal = true, style = Reindeer.WINDOW_BLACK, width = 260 px, resizable = false, closable = false, draggable = false)
    logout.setContent(logoutLayout)
    logout.setCloseShortcut(KeyCode.ESCAPE)

    logout.addComponent(new HtmlLabel("Are you sure you want to log out? You will be redirected to vaadin.com."))

    val buttons = new HorizontalLayout(spacing = true) {
      val yes = add(new Button(caption = "Logout", style = Reindeer.BUTTON_DEFAULT, action = _ => getMainWindow.open(new ExternalResource("http://vaadin.com"))))
      val no = add(new Button("Cancel", action = event => getMainWindow.removeWindow(event.getButton.getWindow)))
    }
    buttons.yes.focus()

    logoutLayout.add(component = buttons, alignment = Alignment.TOP_CENTER)

    getMainWindow.addWindow(logout)
  }

  class H1(caption: String) extends Label(caption) {
    setSizeUndefined()
    setStyleName(Reindeer.LABEL_H1)
  }

  class H2(caption: String) extends Label(caption) {
    setSizeUndefined()
    setStyleName(Reindeer.LABEL_H2)
  }

  class SmallText(caption: String) extends Label(caption) {
    setStyleName(Reindeer.LABEL_SMALL)
  }

  class Ruler extends HtmlLabel("<hr />")

  class Spacer extends Label(width = 20 px)
}