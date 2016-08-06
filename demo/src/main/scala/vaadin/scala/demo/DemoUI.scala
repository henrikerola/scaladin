package vaadin.scala.demo

import vaadin.scala._
import vaadin.scala.server.ScaladinRequest

/**
 * Demo of Scaladin components using the Vaadin Addressbook tutorial.
 *
 * https://vaadin.com/tutorial
 * https://github.com/vaadin/addressbook
 * git://github.com/vaadin/addressbook.git
 */


class DemoUI extends UI(title = "Scaladin Demo") {

  val contentLayout = new VerticalLayout {
    styleName = "content-layout"
    sizeFull()
    margin = true
  }

  override def init(request: ScaladinRequest) {
    val navigator = new Navigator(this, contentLayout) {
      addView(AddressBookView.VIEW_NAME, classOf[AddressBookView])
      addView(DemoView.VIEW1, new DemoView)
      addView(DemoView.VIEW2, classOf[DemoView])
    }

    navigator_=(navigator)

    initLayout()
  }

  def initLayout() {

    val verticalLayout = new VerticalLayout {
      styleName = "scaladin-demo-layout"
      sizeFull()
    }
    content = verticalLayout

    val headerLayout = new VerticalLayout {
      styleName = "header-layout"
      width = 100 pct;
      height = 70 px;
    }

    headerLayout.add(buildApplicationHeader)
    headerLayout.add(buildApplicationMenu(navigator))

    verticalLayout.add(headerLayout)
    verticalLayout.add(contentLayout, ratio = 1)

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
      addItem("Address Book", (e: MenuBar.MenuItem) => navigator.navigateTo(AddressBookView.VIEW_NAME))
      addItem("DemoView1", (e: MenuBar.MenuItem) => navigator.navigateTo(DemoView.VIEW1))
      addItem("DemoView2", (e: MenuBar.MenuItem) => navigator.navigateTo(DemoView.VIEW2))
    }
    addComponent(menuBar)
  }

}

object AddressBookView {
  // Empty view name is the default for navigator.
  val VIEW_NAME = ""
  val FNAME = "First Name"
  val LNAME = "Last Name"
  val COMPANY = "Company"
  val fieldNames = Array(FNAME, LNAME,
    COMPANY, "Mobile Phone", "Work Phone", "Home Phone", "Work Email",
    "Home Email", "Street", "City", "Zip", "State", "Country")
}

class AddressBookView extends Panel with Navigator.View {

  private val contactList = new Table() {
    sizeFull
  }
  private val searchField = new TextField() {
    width = 100 pct
  }
  private val addNewContactButton = Button("New")
  private val removeContactButton = Button("Remove this contact")
  private val editorLayout = new FormLayout() {
    margin = true
    visible = false
  }
  private val editorFields = new FieldGroup()

  val contactContainer = createDummyDatasource

  def initLayout() {
    sizeFull

    val splitPanel = new HorizontalSplitPanel {
      sizeFull
    }
    content = splitPanel

    val leftLayout = new VerticalLayout()
    splitPanel.addComponent(leftLayout)
    splitPanel.addComponent(editorLayout)
    leftLayout.add(component = contactList, ratio = 1)
    val bottomLeftLayout = new HorizontalLayout() {
      width = 100 pct
    }
    leftLayout.addComponent(bottomLeftLayout)
    bottomLeftLayout.add(component = searchField, ratio = 1)
    bottomLeftLayout.addComponent(addNewContactButton)

    leftLayout.sizeFull()
  }

  def initEditor() {
    AddressBookView.fieldNames.foreach(fieldName => {
      val field = new TextField {
        caption = fieldName
        width = 100 pct
      }
      editorLayout.addComponent(field)
      editorFields.bind(field, fieldName)
    })
    editorLayout.addComponent(removeContactButton)

    editorFields.buffered = false
  }

  def initSearch() {

    searchField.prompt = "Search contacts"
    searchField.textChangeEventMode = AbstractTextField.TextChangeEventMode.Lazy
    searchField.textChangeListeners.add((event) => {

      // TODO: filtering container
      contactContainer.p.removeAllContainerFilters()
      contactContainer.p.addContainerFilter(new ContactFilter(event.text))
    })

  }

  class ContactFilter(val needle: String) extends com.vaadin.data.Container.Filter {
    override def passesFilter(itemId: Any, item: com.vaadin.data.Item): Boolean = {
      val haystack = ("" + item.getItemProperty(AddressBookView.FNAME).getValue
        + item.getItemProperty(AddressBookView.LNAME).getValue + item
        .getItemProperty(AddressBookView.COMPANY).getValue).toLowerCase
      haystack.contains(needle.toLowerCase)
    }

    override def appliesToProperty(id: Any): Boolean = {
      true
    }
  }

  private def initAddRemoveButtons() {
    addNewContactButton.clickListeners.add((event) => {
      contactContainer.p.removeAllContainerFilters()
      val contactId = contactContainer.addItemAt(0)

      contactList.property(contactId, AddressBookView.FNAME).map(_.value = "New")
      contactList.property(contactId, AddressBookView.LNAME).map(_.value = "Contact")

      contactList.select(contactId)
    })

    removeContactButton.clickListeners.add((event) => {
      contactList.value.map(id => contactList.removeItem(id))
    })
  }

  private def initContactList() {
    contactList.container = contactContainer
    contactList.visibleColumns = Array(AddressBookView.FNAME, AddressBookView.LNAME, AddressBookView.COMPANY)
    contactList.selectionMode = SelectionMode.Single
    contactList.immediate = true

    contactList.valueChangeListeners.add((event) => {
      val contactId = contactList.value

      contactId.map(id => editorFields.item = contactList.item(id).get)

      editorLayout.visible = contactId.isDefined
    })

  }

  private def createDummyDatasource: IndexedContainer = {
    val ic = new IndexedContainer()

    AddressBookView.fieldNames.foreach(p => ic.addContainerProperty(p, classOf[String], Some("")))

    val fnames = Array("Peter", "Alice", "Joshua", "Mike", "Olivia",
      "Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene",
      "Lisa", "Marge")
    val lnames = Array("Smith", "Gordon", "Simpson", "Brown", "Clavel",
      "Simons", "Verne", "Scott", "Allison", "Gates", "Rowling",
      "Barks", "Ross", "Schneider", "Tate")

    (0 to 1000)

    for (i <- 0 to 1000) {
      val id = ic.addItem()
      ic.property(id, AddressBookView.FNAME).map(_.value = fnames((fnames.length * Math.random()).toInt))
      ic.property(id, AddressBookView.LNAME).map(_.value = lnames((lnames.length * Math.random()).toInt))
    }

    ic
  }

  override def enter(event: Navigator.ViewChangeEvent) {
    initLayout()
    initEditor()
    initSearch()
    initAddRemoveButtons()
    initContactList()
  }
}

object DemoView {
  val VIEW1 = "StaticView"
  val VIEW2 = "ClassBasedView"

  private var count = 1

  private def inc = {
    count += 1;
    count
  }
}

class DemoView extends VerticalLayout with Navigator.View {
  val label = Label("Hello from DemoView")

  def init() {

    val layout = new VerticalLayout() {
      sizeFull()
      add(label)
    }
    layout.margin = true
    add(layout)
  }

  init()

  override def enter(event: Navigator.ViewChangeEvent) {
    val viewName = event.viewName.getOrElse("")
    if (viewName == DemoView.VIEW2) {
      DemoView.inc
    }
    label.value = "Hello from view '" + viewName + "', the view has been created " + DemoView.count + " times."
  }


}