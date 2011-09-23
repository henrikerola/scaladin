package vaadin.scala.demo

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

  val mainLayout = new VerticalLayout(width = 100 px, height = 100 px)
  val mainWindow = new Window(caption = "Vaadin Reindeer Theme", content = mainLayout)
  val tabs = new TabSheet(width = 100 px, height = 100 px)
  val help = new Window("Help")

  override def init() = {
    setTheme("reindeer")
    setMainWindow(mainWindow)

    buildMainView()
  }

  def buildMainView() = {

    val margin = new CssLayout(width = 100 px, height = 100 px)
    margin.setMargin(false, true, true, true)
    margin.add(tabs)

    mainLayout.add(getTopMenu)
      .add(getHeader)
      .add(component = margin, ratio = 1)

    tabs.addComponent(buildWelcomeScreen)
    tabs.addComponent(buildLabels)
    tabs.addComponent(buildButtons)
    tabs.addComponent(buildTextFields)
    tabs.addComponent(buildSelects)
    tabs.addComponent(buildDateFields)
    tabs.addComponent(buildTabSheets)
    tabs.addComponent(buildPanels)
    tabs.addComponent(buildTables)
    tabs.addComponent(buildWindows)
    tabs.addComponent(buildSplitPanels)
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
      add(new TextField(style = "small", prompt = "Enter text"))

      add(new HtmlLabel("Normal TextArea"))
      add(new TextArea(height = 5 em, prompt = "Enter text"))

      add(new HtmlLabel("Small Style TextArea (<code>Reindeer.TEXTFIELD_SMALL</code>)"))
      add(new TextField(height = 5 em, style = "small", prompt = "Enter text"))
    }
  }

  def buildSelects(): Layout = {

    val cb = new ComboBox()
    val nat = new NativeSelect()
    val list = new ListSelect()
    val twincol = new TwinColSelect()

    for (i <- 0 until 25) {
      cb.addItem("Item " + i)
      nat.addItem("Item " + i)
      list.addItem("Item " + i)
      twincol.addItem("Item " + i)
    }

    val hl = new HorizontalLayout(spacing = true) {
      setMargin(true, false, false, false)
      add(cb)
      add(nat)
      add(list)
      add(twincol)
    }

    new VerticalLayout(caption = "Selects", margin = true, spacing = true) {
      add(new Label(
        "Selects don't currently have any additional style names, but here you can see how they behave with the different background colors."))
      add(hl)
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
    val tabSheetLayout = new GridLayout(columns = 2, rows = 1, caption = "Tabs", margin = true, spacing = true, width = 700 px, style = Reindeer.LAYOUT_WHITE)
    tabSheetLayout.setColumnExpandRatio(0, 7)
    tabSheetLayout.setColumnExpandRatio(1, 4)

    val closable = new CheckBox(caption = "Closable tabs", immediate = true)
    val hoverOnly = new CheckBox(caption = "Only on hover", immediate = true, enabled = false, description = "Adds style <code>Reindeer.TABSHEET_HOVER_CLOSABLE</code> to all tabs")
    val selectedOnly = new CheckBox(caption = "Selected only", immediate = true, enabled = false, description = "Adds style <code>Reindeer.TABSHEET_SELECTED_CLOSABLE</code> to all tabs")
    val checks = new HorizontalLayout(spacing = true)

    checks.add(closable)
      .add(hoverOnly)
      .add(selectedOnly)

    tabSheetLayout.addComponent(checks, 1, 0)
    tabSheetLayout.setCursorX(0)
    tabSheetLayout.setCursorY(1)

    tabSheetLayout.addComponent(new HtmlLabel("Normal Tabs"))

    val normalTabSheet = new TabSheet(height = 100 px)
    tabSheetLayout.addComponent(normalTabSheet)

    tabSheetLayout.addComponent(new HtmlLabel("Borderless Style (<code>Reindeer.TABSHEET_BORDERLESS</code>)"))

    val borderlessTabSheet = new TabSheet(height = 100 px, style = Reindeer.TABSHEET_BORDERLESS)
    tabSheetLayout.addComponent(borderlessTabSheet)

    tabSheetLayout.addComponent(new HtmlLabel("Small Style (<code>Reindeer.TABSHEET_SMALL)</code>"))

    val smallTabSheet = new TabSheet(style = Reindeer.TABSHEET_SMALL)
    tabSheetLayout.addComponent(smallTabSheet)

    tabSheetLayout.addComponent(new HtmlLabel("Minimal Style (<code>Reindeer.TABSHEET_MINIMAL</code>)"))

    val minimalTabSheet = new TabSheet(style = Reindeer.TABSHEET_MINIMAL)
    tabSheetLayout.addComponent(minimalTabSheet)

    for (i <- 1 until 10) {
      normalTabSheet.addTab(new Label()).setCaption("Tab " + i)
      borderlessTabSheet.addTab(new Label()).setCaption("Tab " + i)
      smallTabSheet.addTab(new Label()).setCaption("Tab " + i)
      minimalTabSheet.addTab(new Label()).setCaption("Tab " + i)
    }

    closable.addListener(event => {

      val eventValue = event.getButton.booleanValue
      normalTabSheet.getComponents.foreach(c => normalTabSheet.getTab(c).setClosable(eventValue))
      borderlessTabSheet.getComponents.foreach(c => borderlessTabSheet.getTab(c).setClosable(eventValue))
      smallTabSheet.getComponents.foreach(c => smallTabSheet.getTab(c).setClosable(eventValue))
      minimalTabSheet.getComponents.foreach(c => minimalTabSheet.getTab(c).setClosable(eventValue))

      hoverOnly.setEnabled(eventValue)
      selectedOnly.setEnabled(eventValue)
    })

    hoverOnly.addListener(event => {

      if (event.getButton.booleanValue) {
        normalTabSheet.addStyleName(Reindeer.TABSHEET_HOVER_CLOSABLE)
        borderlessTabSheet.addStyleName(Reindeer.TABSHEET_HOVER_CLOSABLE)
        smallTabSheet.addStyleName(Reindeer.TABSHEET_HOVER_CLOSABLE)
        minimalTabSheet.addStyleName(Reindeer.TABSHEET_HOVER_CLOSABLE)
      } else {
        normalTabSheet.removeStyleName(Reindeer.TABSHEET_HOVER_CLOSABLE)
        borderlessTabSheet.removeStyleName(Reindeer.TABSHEET_HOVER_CLOSABLE)
        smallTabSheet.removeStyleName(Reindeer.TABSHEET_HOVER_CLOSABLE)
        minimalTabSheet.removeStyleName(Reindeer.TABSHEET_HOVER_CLOSABLE)
      }
    })

    selectedOnly.addListener(event => {

      if (event.getButton.booleanValue) {
        normalTabSheet.addStyleName(Reindeer.TABSHEET_SELECTED_CLOSABLE)
        borderlessTabSheet.addStyleName(Reindeer.TABSHEET_SELECTED_CLOSABLE)
        smallTabSheet.addStyleName(Reindeer.TABSHEET_SELECTED_CLOSABLE)
        minimalTabSheet.addStyleName(Reindeer.TABSHEET_SELECTED_CLOSABLE)
      } else {
        normalTabSheet.removeStyleName(Reindeer.TABSHEET_SELECTED_CLOSABLE)
        borderlessTabSheet.removeStyleName(Reindeer.TABSHEET_SELECTED_CLOSABLE)
        smallTabSheet.removeStyleName(Reindeer.TABSHEET_SELECTED_CLOSABLE)
        minimalTabSheet.removeStyleName(Reindeer.TABSHEET_SELECTED_CLOSABLE)
      }
    })

    tabSheetLayout
  }

  def buildPanels(): Layout = {
    val panelLayout = new GridLayout(columns = 2, rows = 1, caption = "Panels", margin = true, spacing = true, width = 700 px, style = Reindeer.LAYOUT_WHITE)
    panelLayout.setColumnExpandRatio(0, 2)
    panelLayout.setColumnExpandRatio(1, 5)

    val normalPanel = new Panel(caption = "Normal Panel", height = 100 px)
    normalPanel.add(new Label("Panel content"))

    val lightPanel = new Panel(caption = "Light Style Panel", style = Reindeer.PANEL_LIGHT)
    lightPanel.add(new Label("Panel content"))

    panelLayout.add(new HtmlLabel("Normal Panel"))
      .add(normalPanel)
      .add(new HtmlLabel("Light Style (<code>Reindeer.PANEL_LIGHT</code>)"))
      .add(lightPanel)
    panelLayout
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

  def buildWindows(): Layout = {
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
    val splitPanelLayout = new GridLayout(columns = 2, rows = 1, caption = "Split panels", margin = true, spacing = true, width = 400 px, style = Reindeer.LAYOUT_WHITE)
    splitPanelLayout.setColumnExpandRatio(0, 1)

    val horizontalSplitPanel = new HorizontalSplitPanel(width = 100 px, height = 200 px)

    val smallHorizontalSplitPanel = new HorizontalSplitPanel(width = 100 px, height = 200 px, style = Reindeer.SPLITPANEL_SMALL)

    splitPanelLayout.add(new HtmlLabel("Normal SplitPanel"))
      .add(horizontalSplitPanel)
      .add(new HtmlLabel("Small Style (<code>Reindeer.SPLITPANEL_SMALL</code>)"))
      .add(smallHorizontalSplitPanel)

    splitPanelLayout
  }

  def buildWelcomeScreen(): Layout = {
    val l = new VerticalLayout(margin = true, spacing = true, caption = "Welcome", style = Reindeer.LAYOUT_WHITE)

    val margin = new CssLayout(margin = true, width = 100 percent)
    l.addComponent(margin)

    val texts = new HorizontalLayout(spacing = true, width = 100 percent)
    texts.setMargin(false, false, true, false)

    val welcomeText1 = "<h4>A Complete Theme</h4><p>The Reindeer theme is a complete, general purpose theme suitable for almost all types of applications.<p>While a general purpose theme should not try to cater for every possible need, the Reindeer theme provides a set of useful styles that you can use to make the interface a bit more lively and interesing, emphasizing different parts of the application.</p>"
    val welcomeText2 = "<h4>Everything You Need Is Here</h4><p>Everything you see inside this application, all the different styles, are provided by the Reindeer theme, out-of-the-box. That means you don't necessarily need to create any custom CSS for your application: you can build a cohesive result writing plain Java code.</p><p>A little creativity, good organization and careful typography carries a long way."
    val welcomeText3 = "<h4>The Names of The Styles</h4><p>Look for a class named <code>Reindeer</code> inside the Vaadin JAR (<code>com.vaadin.ui.themes.Reindeer</code>). All the available style names are documented and available there as constants, prefixed by component names, e.g. <code>Reindeer.BUTTON_SMALL</code>."

    texts.add(new HtmlLabel(welcomeText1), 1)
      .add(new Spacer())
      .add(new HtmlLabel(welcomeText2), 1)
      .add(new Spacer())
      .add(new HtmlLabel(content = welcomeText3), 1)

    val stylesHeading = new H2("One Theme &ndash; Three Styles")
    stylesHeading.setContentMode(com.vaadin.ui.Label.CONTENT_XHTML)

    val colors = new HorizontalLayout(width = 100 percent, height = 250 px)

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

  def getHeader(): Layout = {
    val header = new HorizontalLayout(width = 100 percent, margin = true, spacing = true)

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

    val buttons = new HorizontalLayout(spacing = true)
    val help = new Button(caption = "Help", action = _ => openHelpWindow(), style = Reindeer.BUTTON_SMALL)
    val logout = new Button(caption = "Logout", action = _ => openLogoutWindow(), style = Reindeer.BUTTON_SMALL)

    buttons.add(help, alignment = Alignment.MIDDLE_LEFT)
      .add(logout)

    userLayout.addComponent(buttons)

    header.add(titleLayout)
      .add(toggles, alignment = Alignment.MIDDLE_LEFT)
      .add(userLayout, alignment = Alignment.TOP_RIGHT)

    header
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

    val buttons = new HorizontalLayout(spacing = true)
    val yes = new Button(caption = "Logout", style = Reindeer.BUTTON_DEFAULT, action = _ => getMainWindow.open(new ExternalResource("http://vaadin.com")))
    yes.focus()
    val no = new Button("Cancel", action = event => getMainWindow.removeWindow(event.getButton.getWindow))

    buttons.add(yes)
      .add(no)

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