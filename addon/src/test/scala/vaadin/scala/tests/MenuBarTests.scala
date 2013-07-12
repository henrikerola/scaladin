package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import vaadin.scala.server.ThemeResource

class MenuBarTests extends FunSuite with BeforeAndAfter {

  var menuBar: MenuBar = _
  var item: MenuBar.MenuItem = _
  var subItem: MenuBar.MenuItem = _

  val command = (e: MenuBar.MenuItem) => {}
  val icon = new ThemeResource("icon.png")

  before {
    menuBar = new MenuBar
    item = menuBar.addItem("Caption", command)
    subItem = item.addItem("Catpion", command)
  }

  test("addItem(text)") {
    val addedItem: MenuBar.MenuItem = menuBar.addItem("Caption")
    assert(addedItem.parent === None)
    assert(addedItem.command === None)
    assert(!addedItem.p.isSeparator)
    assert(!addedItem.p.isCheckable)
  }

  test("addItem(text, command)") {
    val addedItem: MenuBar.MenuItem = menuBar.addItem("Caption", command)
    assert(addedItem.parent === None)
    assert(addedItem.command === Some(command))
    assert(!addedItem.p.isSeparator)
    assert(!addedItem.p.isCheckable)
  }

  test("addItem(text, icon)") {
    val addedItem: MenuBar.MenuItem = menuBar.addItem("Caption", icon)
    assert(addedItem.parent === None)
    assert(addedItem.command === None)
    assert(!addedItem.p.isSeparator)
    assert(!addedItem.p.isCheckable)
  }

  test("addItem(text, icon, command)") {
    val addedItem: MenuBar.MenuItem = menuBar.addItem("Caption", icon, command)
    assert(addedItem.parent === None)
    assert(addedItem.command === Some(command))
    assert(!addedItem.p.isSeparator)
    assert(!addedItem.p.isCheckable)
  }

  test("addItemBefore(text, icon, item)") {
    val addedItem: MenuBar.MenuItem = menuBar.addItemBefore("Caption", icon, item)
    assert(addedItem.parent === None)
    assert(addedItem.command === None)
    assert(!addedItem.p.isSeparator)
    assert(!addedItem.p.isCheckable)
  }

  test("addItemBefore(text, icon, command, item)") {
    val addedItem: MenuBar.MenuItem = menuBar.addItemBefore("Caption", icon, command, item)
    assert(addedItem.parent === None)
    assert(addedItem.command === Some(command))
    assert(!addedItem.p.isSeparator)
    assert(!addedItem.p.isCheckable)
  }

  test("moreMenuItem") {
    assert(menuBar.moreMenuItem != None)

    menuBar.moreMenuItem = item
    assert(menuBar.moreMenuItem === Some(item))

    menuBar.moreMenuItem = None
    assert(menuBar.moreMenuItem != None)
    assert(menuBar.moreMenuItem != Some(item))

    menuBar.moreMenuItem = Some(item)
    assert(menuBar.moreMenuItem === Some(item))
  }

  test("autoOpen") {
    assert(!menuBar.autoOpen)
    menuBar.autoOpen = true
    assert(menuBar.autoOpen)
  }

  test("htmlContentAllowed") {
    assert(!menuBar.htmlContentAllowed)
    menuBar.htmlContentAllowed = true
    assert(menuBar.htmlContentAllowed)
  }

  test("MenuBar.MenuItemTrait.parent") {
    assert(item.parent === None)
    assert(subItem.parent === Some(item))
  }

  test("MenuBar.MenuItemTrait.visible") {
    assert(item.visible)
    item.visible = false
    assert(!item.visible)
  }

  test("MenuBar.MenuItem.addSeparator") {
    val addedSeparator: MenuBar.Separator = item.addSeparator
    assert(addedSeparator.parent === Some(item))
    assert(addedSeparator.p.isSeparator)
  }

  test("MenuBar.MenuItem.addSeparatorBefore") {
    val addedSeparator: MenuBar.Separator = item.addSeparatorBefore(subItem)
    assert(addedSeparator.parent === Some(item))
    assert(addedSeparator.p.isSeparator)
  }

  test("MenuBar.MenuItem.addItem(text)") {
    val addedItem: MenuBar.MenuItem = item.addItem("Caption")
    assert(addedItem.parent === Some(item))
    assert(addedItem.text === "Caption")
    assert(addedItem.icon === None)
    assert(addedItem.command === None)
    assert(!addedItem.p.isSeparator)
    assert(!addedItem.p.isCheckable)
  }

  test("MenuBar.MenuItem.addItem(text, command)") {
    val addedItem: MenuBar.MenuItem = item.addItem("Caption", command)
    assert(addedItem.parent === Some(item))
    assert(addedItem.text === "Caption")
    assert(addedItem.icon === None)
    assert(addedItem.command === Some(command))
    assert(!addedItem.p.isSeparator)
    assert(!addedItem.p.isCheckable)
  }

  test("MenuBar.MenuItem.addItem(text, icon)") {
    val addedItem: MenuBar.MenuItem = item.addItem("Caption", icon)
    assert(addedItem.parent === Some(item))
    assert(addedItem.text === "Caption")
    assert(addedItem.icon === Some(icon))
    assert(addedItem.command === None)
    assert(!addedItem.p.isSeparator)
    assert(!addedItem.p.isCheckable)
  }

  test("MenuBar.MenuItem.addItem(text, icon, command)") {
    val addedItem: MenuBar.MenuItem = item.addItem("Caption", icon, command)
    assert(addedItem.parent === Some(item))
    assert(addedItem.text === "Caption")
    assert(addedItem.icon === Some(icon))
    assert(addedItem.command === Some(command))
    assert(!addedItem.p.isSeparator)
    assert(!addedItem.p.isCheckable)
  }

  test("MenuBar.MenuItem.addItemBefore(caption, icon, item)") {
    val addedItem: MenuBar.MenuItem = item.addItemBefore("Caption", icon, subItem)
    assert(addedItem.parent === Some(item))
    assert(addedItem.text === "Caption")
    assert(addedItem.icon === Some(icon))
    assert(addedItem.command === None)
    assert(!addedItem.p.isCheckable)
  }

  test("MenuBar.MenuItem.addItemBefore(caption, icon, command, item)") {
    val addedItem: MenuBar.MenuItem = item.addItemBefore("Caption", icon, command, subItem)
    assert(addedItem.parent === Some(item))
    assert(addedItem.text === "Caption")
    assert(addedItem.icon === Some(icon))
    assert(addedItem.command === Some(command))
    assert(!addedItem.p.isCheckable)
  }

  test("MenuBar.MenuItem.addCheckableItem(text)") {
    val addedItem: MenuBar.CheckableMenuItem = item.addCheckableItem("Caption")
    assert(addedItem.parent === Some(item))
    assert(addedItem.text === "Caption")
    assert(addedItem.icon === None)
    assert(addedItem.command === None)
    assert(addedItem.p.isCheckable)
  }

  test("MenuBar.MenuItem.addCheckableItem(text, command)") {
    val addedItem: MenuBar.CheckableMenuItem = item.addCheckableItem("Caption", command)
    assert(addedItem.parent === Some(item))
    assert(addedItem.text === "Caption")
    assert(addedItem.icon === None)
    assert(addedItem.command === Some(command))
    assert(addedItem.p.isCheckable)
  }

  test("MenuBar.MenuItem.addCheckableItem(text, icon)") {
    val addedItem: MenuBar.CheckableMenuItem = item.addCheckableItem("Caption", icon)
    assert(addedItem.parent === Some(item))
    assert(addedItem.text === "Caption")
    assert(addedItem.icon === Some(icon))
    assert(addedItem.command === None)
    assert(addedItem.p.isCheckable)
  }

  test("MenuBar.MenuItem.addCheckableItem(text, icon, command)") {
    val addedItem: MenuBar.CheckableMenuItem = item.addCheckableItem("Caption", icon, command)
    assert(addedItem.parent === Some(item))
    assert(addedItem.text === "Caption")
    assert(addedItem.icon === Some(icon))
    assert(addedItem.command === Some(command))
    assert(addedItem.p.isCheckable)
  }

  test("MenuBar.MenuItem.addCheckableItemBefore(caption, icon, item)") {
    val addedItem: MenuBar.CheckableMenuItem = item.addCheckableItemBefore("Caption", icon, subItem)
    assert(addedItem.parent === Some(item))
    assert(addedItem.text === "Caption")
    assert(addedItem.icon === Some(icon))
    assert(addedItem.command === None)
    assert(addedItem.p.isCheckable)
  }

  test("MenuBar.MenuItem.addCheckableItemBefore(caption, icon, command, item)") {
    val addedItem: MenuBar.CheckableMenuItem = item.addCheckableItemBefore("Caption", icon, command, subItem)
    assert(addedItem.parent === Some(item))
    assert(addedItem.command === Some(command))
    assert(addedItem.text === "Caption")
    assert(addedItem.icon === Some(icon))
    assert(addedItem.p.isCheckable)
  }

  test("MenuBar.MenuItem.command") {
    val cmd = (e: MenuBar.MenuItem) => {}

    item.command = cmd
    assert(item.command === Some(cmd))
    assert(item.p.getCommand != null)

    item.command = None
    assert(item.command === None)
    assert(item.p.getCommand === null)

    item.command = Some(cmd)
    assert(item.command === Some(cmd))
    assert(item.p.getCommand != null)
  }

  test("MenuBar.MenuItem.hasChildren") {
    assert(item.hasChildren)
    assert(!subItem.hasChildren)
  }

  test("MenuBar.MenuItem.enabled") {
    assert(item.enabled)
    item.enabled = false
    assert(!item.enabled)
  }

  test("MenuBar.MenuItem.icon") {
    assert(item.icon === None)

    val icon = new ThemeResource("foobar.png")

    item.icon = icon
    assert(item.icon === Some(icon))

    item.icon = None
    assert(item.icon === None)

    item.icon = Some(icon)
    assert(item.icon === Some(icon))
  }

  test("MenuBar.MenuItem.text") {
    item.text = "test test"
    assert(item.text === "test test")
  }

  test("MenuBar.MenuItem.styleName") {
    assert(item.styleName === None)

    item.styleName = "style1 style2"
    assert(item.styleName === Some("style1 style2"))

    item.styleName = None
    assert(item.styleName === None)

    item.styleName = Some("style1 style2")
    assert(item.styleName === Some("style1 style2"))
  }

  test("MenuBar.CheckableMenuItem.checked") {
    val checkableItem: MenuBar.CheckableMenuItem = item.addCheckableItem("Caption", command)
    assert(!checkableItem.checked)
    checkableItem.checked = true
    assert(checkableItem.checked)
  }

  test("Usage Example") {
    val menuBar = new MenuBar {
      autoOpen = true
      val command = (e: MenuBar.MenuItem) => {}

      val fileItem = addItem("File")
      fileItem.addItem("Open", command)
      fileItem.addItem("Close", command)
      val openRecent = fileItem.addItem("Open recent", command)
      openRecent.addItem("File1.txt", new ThemeResource("../runo/icons/16/document.png"), command)
      openRecent.addItem("File2.txt", new ThemeResource("../runo/icons/16/document.png"), command)
      fileItem.addSeparator()
      fileItem.addItem("Quit", command)

      val editItem = addItem("Edit")
      editItem.addItem("Copy", command)
      editItem.addItem("Cut", command)
      editItem.addItem("Paste", command)

      val viewItem = addItem("View", command)
      val showToolBar = viewItem.addCheckableItem("Show Toolbar", command)
      showToolBar.checked = true
      viewItem.addCheckableItem("Show Statusbar", command)

      val help = addItem("Help")
    }
  }

}