package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito
import vaadin.scala.mixins.TabSheetMixin
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import vaadin.scala.server.ThemeResource

@RunWith(classOf[JUnitRunner])
class TabSheetTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  class VaadinTabSheet extends com.vaadin.ui.TabSheet with TabSheetMixin

  var tabSheet: TabSheet = _

  val label = new Label
  var tab: TabSheet.Tab = _

  val label2 = new Label
  var tab2: TabSheet.Tab = _

  var spy: VaadinTabSheet = _

  before {
    spy = Mockito.spy(new VaadinTabSheet)
    tabSheet = new TabSheet(spy)
    tab = tabSheet.addTab(label)
    tab2 = tabSheet.addTab(label2)
  }

  test("Tab.visible") {
    assert(tab.visible)

    tab.visible = false
    assert(!tab.visible)
  }

  test("Tab.closable") {
    assert(!tab.closable)

    tab.closable = true
    assert(tab.closable)
  }

  test("Tab.enabled") {
    assert(tab.enabled)

    tab.enabled = false
    assert(!tab.enabled)
  }

  test("Tab.caption") {
    assert(tab.caption === Some(""))

    tab.caption = "caption"
    assert(tab.caption === Some("caption"))

    tab.caption = None
    assert(tab.caption === None)

    tab.caption = Some("caption")
    assert(tab.caption === Some("caption"))
  }

  test("Tab.icon") {
    assert(tab.icon === None)

    val icon = new ThemeResource("pic.png")

    tab.icon = icon
    assert(tab.icon === Some(icon))

    tab.icon = None
    assert(tab.icon === None)

    tab.icon = Some(icon)
    assert(tab.icon === Some(icon))
  }

  test("Tab.description") {
    assert(tab.description === None)

    tab.description = "description"
    assert(tab.description === Some("description"))

    tab.description = None
    assert(tab.description === None)

    tab.description = Some("description")
    assert(tab.description === Some("description"))
  }

  test("Tab.component") {
    assert(tab.component === label)
  }

  test("Tab.styleName") {
    assert(tab.styleName === None)

    tab.styleName = "styleName"
    assert(tab.styleName === Some("styleName"))

    tab.styleName = None
    assert(tab.styleName === None)

    tab.styleName = Some("styleName")
    assert(tab.styleName === Some("styleName"))
  }

  test("removeComponent") {
    assert(tabSheet.tabs.size === 2)
    tabSheet.removeComponent(label)

    assert(tabSheet.getTab(label) === None)
    assert(tabSheet.tabs.size === 1)
  }

  test("removeComponent, try to remove a Component that's not in the TabSheet") {
    assert(tabSheet.tabs.size === 2)
    tabSheet.removeComponent(new Label)
    assert(tabSheet.tabs.size === 2)
  }

  test("components.-= must remove Tab from the tabs map") {
    assert(tabSheet.tabs.size === 2)
    tabSheet.components -= label
    assert(tabSheet.tabs.size === 1)
  }

  test("removeTab") {
    assert(tabSheet.tabs.size === 2)

    tabSheet.removeTab(tab)
    Mockito.verify(spy).removeTab(tab.p)
    assert(tabSheet.getTab(label) === None)
    assert(tabSheet.tabs.size === 1)
  }

  test("removeTab, try to remove a Tab from another TabSheet") {
    assert(tabSheet.tabs.size === 2)

    val tabFromAnotherTabSheet = new TabSheet().addTab(new Label)

    tabSheet.removeTab(tabFromAnotherTabSheet)
    assert(tabSheet.tabs.size === 2)
  }

  test("addTab(component)") {
    val labelToBeAdded = new Label

    assert(tabSheet.tabs.size === 2)
    val addedTab: TabSheet.Tab = tabSheet.addTab(labelToBeAdded)
    Mockito.verify(spy).addTab(labelToBeAdded.p)
    assert(tabSheet.tabs.size === 3)
    assert(tabSheet.tabs.contains(addedTab.p))
    assert(tabSheet.tabs.get(addedTab.p) === Some(addedTab))
  }

  test("addTab(component, caption)") {
    val labelToBeAdded = new Label

    assert(tabSheet.tabs.size === 2)
    val addedTab: TabSheet.Tab = tabSheet.addTab(labelToBeAdded, "my caption")
    Mockito.verify(spy).addTab(labelToBeAdded.p, "my caption")
    assert(tabSheet.tabs.size === 3)
    assert(tabSheet.tabs.contains(addedTab.p))
    assert(tabSheet.tabs.get(addedTab.p) === Some(addedTab))
  }

  test("addTab(component, caption, icon)") {
    val labelToBeAdded = new Label
    val icon = new ThemeResource("icon.png")

    assert(tabSheet.tabs.size === 2)
    val addedTab: TabSheet.Tab = tabSheet.addTab(labelToBeAdded, "my caption", icon)
    Mockito.verify(spy).addTab(labelToBeAdded.p, "my caption", icon.p)
    assert(tabSheet.tabs.size === 3)
    assert(tabSheet.tabs.contains(addedTab.p))
    assert(tabSheet.tabs.get(addedTab.p) === Some(addedTab))
  }

  test("addTab(component, caption, icon, position)") {
    val labelToBeAdded = new Label
    val icon = new ThemeResource("icon.png")

    assert(tabSheet.tabs.size === 2)
    val addedTab: TabSheet.Tab = tabSheet.addTab(labelToBeAdded, "my caption", icon, 0)
    Mockito.verify(spy).addTab(labelToBeAdded.p, "my caption", icon.p, 0)
    assert(tabSheet.tabs.size === 3)
    assert(tabSheet.tabs.contains(addedTab.p))
    assert(tabSheet.tabs.get(addedTab.p) === Some(addedTab))
  }

  test("addTab(component, position)") {
    val labelToBeAdded = new Label

    assert(tabSheet.tabs.size === 2)
    val addedTab: TabSheet.Tab = tabSheet.addTab(labelToBeAdded, 0)
    Mockito.verify(spy).addTab(labelToBeAdded.p, 0)
    assert(tabSheet.tabs.size === 3)
    assert(tabSheet.tabs.contains(addedTab.p))
    assert(tabSheet.tabs.get(addedTab.p) === Some(addedTab))
  }

  ignore("addComponent") {
    val labelToBeAdded = new Label

    tabSheet.components += label
    assert(tabSheet.getTab(labelToBeAdded) != None)
  }

  test("tabsVisible") {
    assert(tabSheet.tabsVisible)

    tabSheet.tabsVisible = false
    assert(!tabSheet.tabsVisible)
  }

  test("getTab") {
    assert(tabSheet.getTab(0) === Some(tab))
  }

  test("getTab, illegal position returns None") {
    assert(tabSheet.getTab(2) === None)
  }

  test("selectedComponent") {
    assert(tabSheet.selectedComponent === label)

    tabSheet.selectedComponent = label2
    assert(tabSheet.selectedComponent === label2)
  }

  test("selectedComponent, selecting uncontained component") {
    // nothing happens, silently ignored, should an exception be thrown?
    tabSheet.selectedComponent = new Label

    assert(tabSheet.selectedComponent === label)
  }

  test("selectedTab") {
    assert(tabSheet.selectedTab === tab)

    tabSheet.selectedTab = tab2
    assert(tabSheet.selectedTab === tab2)
  }

  test("selectedTab, selecting a tab from another TabSheet") {
    val tabFromAnotherTabSheet = new TabSheet().addTab(new Label)

    // nothing happens, silently ignored, should an exception be thrown?
    tabSheet.selectedTab = tabFromAnotherTabSheet

    assert(tabSheet.selectedTab === tab)
  }

  test("setTabPosition") {
    assert(tabSheet.getTabPosition(tab) === 0)
    assert(tabSheet.getTabPosition(tab2) === 1)

    tabSheet.setTabPosition(tab, 1)

    assert(tabSheet.getTabPosition(tab) === 1)
    assert(tabSheet.getTabPosition(tab2) === 0)
  }

  test("getTabPosition, position for a Tab from another TabSheet") {
    val tabFromAnotherTabSheet = new TabSheet().addTab(new Label)

    assert(tabSheet.getTabPosition(tabFromAnotherTabSheet) === -1)
  }

  test("closeHandler") {
    assert(tabSheet.closeHandler != null)

    val myCloseHandler = (e: TabSheet.TabCloseEvent) => println(e.tabSheet)
    tabSheet.closeHandler = myCloseHandler
    assert(tabSheet.closeHandler === myCloseHandler)
  }

  test("Example") {
    val tabSheet = new TabSheet {
      sizeFull()
      closeHandler = (e: TabSheet.TabCloseEvent) => {
        // do nothing: pressing the x button doesn't close the tab
      }
      val tab1 = addTab(new Label, "Tab 1")
      tab1.closable = true
      val tab2 = addTab(new Label, "Tab 2", ThemeResource("../runo/icons/16/globe.png"))
      tab2.closable = true

      selectedTab = tab2
    }
  }
}