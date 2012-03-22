package vaadin.scala.demo

import com.vaadin.ui.Component
import java.util.Date
import com.vaadin.Application
import com.vaadin.terminal.ExternalResource
import com.vaadin.ui.themes.Reindeer
import vaadin.scala._
import com.vaadin.ui.themes.Reindeer
import com.vaadin.ui.themes.BaseTheme
import com.vaadin.data.Property.ValueChangeListener
import com.vaadin.data.Property.ValueChangeEvent
import com.vaadin.ui.{ Alignment => VaadinAlignment }
import com.vaadin.event.ShortcutAction.KeyCode

class DemoApplication extends SimpleApplication(title = "Vaadin Reindeer Theme", theme = "reindeer") {

  val DATE = new Date()

  val help = new Window("Help")

  override def main: com.vaadin.ui.ComponentContainer = {

    new VerticalLayout(size = Full) {
      val tabs = new TabSheet(size = Full)

      addComponent(buildScalaWrappersAd)
      addComponent(getTopMenu)
      addComponent(getHeader(this, tabs))
      add(ratio = 1, component = new CssLayout(size = Full) {
        margin(false, true, true, true)
        p.addComponent(tabs)
      })

      tabs.addComponent(buildWelcomeScreen.p)
      tabs.addComponent(buildLabels)
      tabs.addComponent(buildButtons)
      tabs.addComponent(buildTextFields)
      tabs.addComponent(buildSelects.p)
      tabs.addComponent(buildDateFields.p)
      //tabs.addComponent(buildTabSheets) // FIXME
      tabs.addComponent(buildPanels)
      tabs.addComponent(buildTables)
      tabs.addComponent(buildWindows(tabs).p)
      tabs.addComponent(buildSplitPanels)
    }.p
  }

  def buildScalaWrappersAd(): Layout = {
    new VerticalLayout(width = 100 percent, style = Reindeer.LAYOUT_BLACK) {
      add(alignment = Alignment.MiddleCenter, component = new VerticalLayout(width = 700 px, style = Reindeer.LAYOUT_BLACK, margin = true) {
        add(alignment = Alignment.MiddleCenter, component = new HtmlLabel(width = None, style = Reindeer.LABEL_H2,
          content = <span>This demo is implemented completely in <a href="http://www.scala-lang.org">Scala</a>,</span>))
        add(alignment = Alignment.MiddleCenter, component = new HtmlLabel(width = None, style = Reindeer.LABEL_H2,
          content = <span>with the help of the ScalaWrappers Vaadin addon.</span>))
        add(alignment = Alignment.MiddleCenter, component = new HtmlLabel(width = None, style = Reindeer.LABEL_H2,
          content = <span>You can find more info about the add-on from the <a href="http://vaadin.com/addon/scala-wrappers">Directory page</a>.</span>))
      })
    }
  }

  def buildLabels(): com.vaadin.ui.Layout = {
    new GridLayout(columns = 2, rows = 1, width = 560 px, spacing = true, margin = true, caption = "Labels", style = Reindeer.LAYOUT_WHITE) {
      add(new HtmlLabel(<span>Header Style (<code>Reindeer.LABEL_H1</code>)</span>).p)
      add(new H1("Lorem Ipsum").p)

      add(new HtmlLabel(<span>Sub-header Style (<code>Reindeer.LABEL_H2</code>)</span>).p)
      add(new H2("Lorem Ipsum Dolor").p)

      add(new HtmlLabel(<span>Normal Label</span>).p)
      add(new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit.").p)

      add(new HtmlLabel(<span>Small Style (<code>Reindeer.LABEL_SMALL</code>)</span>).p)
      add(new SmallText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.").p)
    }
  }

  def buildButtons(): com.vaadin.ui.Layout = {
    new GridLayout(columns = 2, rows = 1, caption = "Buttons", margin = true, spacing = true, width = 370 px) {
      setColumnExpandRatio(0, 1)

      add(new HtmlLabel(<span>"Default" Style (<code>Reindeer.BUTTON_DEFAULT</code>)</span>).p)
      add(new Button(caption = "Default Button", style = Reindeer.BUTTON_DEFAULT).p)

      add(new HtmlLabel(<span>Normal Button</span>).p)
      add(new Button("Normal Button").p)

      add(new HtmlLabel(<span>Disabled Button (<code>Button.setEnabled(false)</code>)</span>).p)
      add(new Button("Disabled Button", enabled = false).p)

      add(new HtmlLabel(<span>Small Style (<code>Reindeer.BUTTON_SMALL</code>)</span>).p)
      add(new Button(caption = "Small Button", style = Reindeer.BUTTON_SMALL).p)

      add(new HtmlLabel(<span>Link Style (<code>Reindeer.BUTTON_LINK</code>)</span>).p)
      add(new Button(caption = "Link Button", style = BaseTheme.BUTTON_LINK).p) //no way to access inherited static members in Scala
    }
  }

  def buildTextFields(): com.vaadin.ui.Layout = {
    new GridLayout(columns = 2, rows = 1, caption = "Text fields", margin = true, spacing = true, width = 400 px, style = Reindeer.LAYOUT_WHITE) {
      setColumnExpandRatio(0, 1)

      add(new HtmlLabel(<span>Normal TextField</span>).p)
      //      add(new TextField(prompt = "Enter text"))

      add(new HtmlLabel(<span>Small Style (<code>Reindeer.TEXTFIELD_SMALL</code>)</span>).p)
      //      add(new TextField(style = Reindeer.TEXTFIELD_SMALL, prompt = "Enter text"))

      add(new HtmlLabel(<span>Normal TextArea</span>).p)
      add(new TextArea(height = 5 em, prompt = "Enter text"))

      add(new HtmlLabel(<span>Small Style TextArea (<code>Reindeer.TEXTFIELD_SMALL</code>)</span>).p)
      add(new TextArea(height = 5 em, style = Reindeer.TEXTFIELD_SMALL, prompt = "Enter text"))
    }
  }

  def buildSelects(): Layout = {
    val selectsLayout = new HorizontalLayout(spacing = true) {
      margin(top = true)
      val comboBox = add(new ComboBox())
      val nativeSelect = addComponent(new NativeSelect())
      val listSelect = addComponent(new ListSelect())
      val twinColSelect = addComponent(new TwinColSelect())
    }

    for (i <- 0 until 25) {
      selectsLayout.comboBox.p.addItem("Item " + i)
      selectsLayout.nativeSelect.addItem("Item " + i)
      selectsLayout.listSelect.addItem("Item " + i)
      selectsLayout.twinColSelect.addItem("Item " + i)
    }

    new VerticalLayout(caption = "Selects", margin = true, spacing = true) {
      add(new Label(
        "Selects don't currently have any additional style names, but here you can see how they behave with the different background colors."))
      addComponent(selectsLayout)
    }
  }

  def buildDateFields(): Layout = {
    val hl = new HorizontalLayout(spacing = true) {
      margin(top = true)
      addComponent(new PopupDateField(value = DATE, resolution = com.vaadin.ui.DateField.RESOLUTION_MIN))
      addComponent(new InlineDateField(value = DATE, resolution = com.vaadin.ui.DateField.RESOLUTION_DAY))
      addComponent(new InlineDateField(value = DATE, resolution = com.vaadin.ui.DateField.RESOLUTION_YEAR))
    }

    new VerticalLayout(caption = "Date fields", margin = true, spacing = true) {
      add(new Label(
        "Date fields don't currently have any additional style names, but here you can see how they behave with the different background colors."))
      addComponent(hl)
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

  def buildPanels(): com.vaadin.ui.Layout = {
    new GridLayout(columns = 2, rows = 1, caption = "Panels", margin = true, spacing = true, width = 700 px, style = Reindeer.LAYOUT_WHITE) {
      setColumnExpandRatio(0, 2)
      setColumnExpandRatio(1, 5)

      add(new HtmlLabel(<span>Normal Panel</span>).p)
      val normalPanel = add(new Panel(caption = "Normal Panel", height = 100 px))
      normalPanel.add(new Label("Panel content").p)

      add(new HtmlLabel(<span>Light Style (<code>Reindeer.PANEL_LIGHT</code>)</span>).p)
      val lightPanel = add(new Panel(caption = "Light Style Panel", style = Reindeer.PANEL_LIGHT))
      lightPanel.add(new Label("Panel content").p)
    }
  }

  def buildTables(): com.vaadin.ui.Layout = {
    val tableLayout = new GridLayout(columns = 2, rows = 1, caption = "Tables", margin = true, spacing = true, width = 700 px, style = Reindeer.LAYOUT_WHITE)
    tableLayout.setColumnExpandRatio(0, 3)
    tableLayout.setColumnExpandRatio(1, 5)

    for (i <- 0 until 4) {
      val table = new Table(width = 100 percent)
      table.pageLength = 4
      table.p.setSelectable(true)
      table.columnCollapsingAllowed = true
      table.columnReorderingAllowed = true

      i match {

        case 0 => tableLayout.addComponent(new HtmlLabel(<span>Normal Table</span>).p)

        case 1 =>
          table.p.setStyleName("strong")
          tableLayout.addComponent(new HtmlLabel(<span>Strong Style (<code>Reindeer.TABLE_STRONG</code>)</span>).p)

        case 2 =>
          table.p.setStyleName("borderless")
          tableLayout.addComponent(new HtmlLabel(<span>Borderless Style (<code>Reindeer.TABLE_BORDERLESS</code>)</span>).p)

        case 3 =>
          table.p.setStyleName("borderless strong")
          tableLayout.addComponent(new Label("Borderless & Strong Combined").p)

      }

      table.p.addContainerProperty("First", classOf[String], null)
      table.p.addContainerProperty("Second", classOf[String], null)
      table.p.addContainerProperty("Third", classOf[String], null)

      for (j <- 0 until 100) {
        table.p.addItem(Array("Foo " + j, "Bar value " + j,
          "Last column value " + j), j)
      }

      tableLayout.addComponent(table.p)
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
    notResizableWindow.addComponent(new HtmlLabel(<span><code>Window.setResizable(false)</code></span>).p)

    val lightWindow = new Window(caption = " Light window", width = 280 px, height = 230 px, style = Reindeer.WINDOW_LIGHT)
    lightWindow.setPositionX(40)
    lightWindow.setPositionY(370)
    lightWindow.addComponent(new HtmlLabel(<span><code>Reindeer.WINDOW_LIGHT</code></span>).p)

    val blackWindow = new Window(caption = "Black window", width = 280 px, height = 230 px, style = Reindeer.WINDOW_BLACK)
    blackWindow.setPositionX(350)
    blackWindow.setPositionY(370)
    blackWindow.addComponent(new HtmlLabel(<span><code>Reindeer.WINDOW_BLACK</code></span>).p)

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

  def buildSplitPanels(): com.vaadin.ui.Layout = {
    new GridLayout(columns = 2, rows = 1, caption = "Split panels", margin = true, spacing = true, width = 400 px, style = Reindeer.LAYOUT_WHITE) {
      setColumnExpandRatio(0, 1)

      add(new HtmlLabel(<span>Normal SplitPanel</span>).p)
      add(new HorizontalSplitPanel(width = 100 px, height = 200 px).p)

      add(new HtmlLabel(<span>Small Style (<code>Reindeer.SPLITPANEL_SMALL</code>)</span>).p)
      add(new HorizontalSplitPanel(width = 100 px, height = 200 px, style = Reindeer.SPLITPANEL_SMALL).p)

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

    colors.addComponent(whiteLayout)
    colors.addComponent(blueLayout)
    colors.addComponent(blackLayout)

    val note = new HtmlLabel(<span><p>Note, that you cannot nest the layout styles infinitely inside each other. After a couple levels, the result will be undefined, due to limitations in CSS (which are in fact caused by Internet Explorer 6).</p></span>)

    l.addComponent(new CssLayout(margin = true, width = 100 percent) {
      add(new H1("Guide to the Reindeer Theme"))
      add(new Ruler())
      add(texts)
      add(stylesHeading)
      add(new Ruler())
      add(new HtmlLabel(<span><p>You can easily change the feel of some parts of your application by using the three layout styles provided by Reindeer: white, blue and black. The colored area contains the margins of the layout. All contained components will switch their style if an alternative style is available for that color.</p></span>))
      add(colors)
      addComponent(note)
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

    val toggles = new HorizontalLayout(spacing = true)
    val bgColor = new Label("Background color")
    bgColor.description = "Set the style name for the main layout of this window:<ul><li>Default - no style</li><li>White - Reindeer.LAYOUT_WHITE</li><li>Blue - Reindeer.LAYOUT_BLUE</li><li>Black - Reindeer.LAYOUT_BLACK</li></ul>"
    toggles.addComponent(bgColor.p)
    val colors = new NativeSelect()
    colors.setNullSelectionAllowed(false)
    colors.setDescription("Set the style name for the main layout of this window:<ul><li>Default - no style</li><li>White - Reindeer.LAYOUT_WHITE</li><li>Blue - Reindeer.LAYOUT_BLUE</li><li>Black - Reindeer.LAYOUT_BLACK</li></ul>")
    colors.addItem("Default")
    colors.addItem("White")
    colors.addItem("Blue")
    colors.addItem("Black")
    colors.setImmediate(true)
    colors.addListener(event => mainLayout.p.setStyleName(event.getProperty.getValue.toString.toLowerCase))

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
    transparent.description = "Set style Reindeer.TABSHEET_MINIMAL to the main tab sheet (preview components on different background colors)."
    toggles.addComponent(transparent)

    val userLayout = new CssLayout()
    val user = new Label("Welcome, Guest")
    user.sizeUndefined()
    userLayout.addComponent(user)

    val help = new Button(caption = "Help", action = _ => openHelpWindow(), style = Reindeer.BUTTON_SMALL)
    val logout = new Button(caption = "Logout", action = _ => openLogoutWindow(), style = Reindeer.BUTTON_SMALL)

    val buttons = new HorizontalLayout(spacing = true) {
      add(component = help, alignment = Alignment.MiddleLeft)
      add(logout)
    }
    userLayout.addComponent(buttons)

    new HorizontalLayout(width = 100 percent, margin = true, spacing = true) {
      add(titleLayout)
      add(component = toggles, alignment = Alignment.MiddleLeft)
      add(component = userLayout, alignment = Alignment.TopRight)
    }
  }

  def openHelpWindow() {
    if ("initialized" != help.getData) {
      help.setData("initialized")
      help.setCloseShortcut(KeyCode.ESCAPE)

      help.center()
      help.setWidth("400px")
      help.setResizable(false)

      val helpText = new HtmlLabel(<span><strong>How To Use This Application</strong><p>Click around, explore. The purpose of this app is to show you what is possible to achieve with the Reindeer theme and its different styles.</p><p>Most of the UI controls that are visible in this application don't actually do anything. They are purely for show, like the menu items and the components that demostrate the different style names assosiated with the components.</p><strong>So, What Then?</strong><p>Go and use the styles you see here in your own application and make them beautiful!</p></span>)
      help.addComponent(helpText.p)
    }
    if (!getMainWindow.getChildWindows.contains(help)) {
      getMainWindow.addWindow(help)
    }
  }

  def openLogoutWindow() {
    val logoutLayout = new VerticalLayout(spacing = true)

    val logout = new Window(caption = "Logout", modal = true, style = Reindeer.WINDOW_BLACK, width = 260 px, resizable = false, closable = false, draggable = false)
    logout.setContent(logoutLayout.p)
    logout.setCloseShortcut(KeyCode.ESCAPE)

    logout.addComponent(new HtmlLabel(<span>Are you sure you want to log out? You will be redirected to vaadin.com.</span>).p)

    val buttons = new HorizontalLayout(spacing = true) {
      val yes = add(new Button(caption = "Logout", style = Reindeer.BUTTON_DEFAULT, action = _ => getMainWindow.open(new ExternalResource("http://vaadin.com"))))
      val no = add(new Button("Cancel", action = event => getMainWindow.removeWindow(event.button.window)))
    }
    buttons.yes.focus()

    logoutLayout.add(component = buttons, alignment = Alignment.TopCenter)

    getMainWindow.addWindow(logout)
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