package vaadin.scala

import vaadin.scala.mixins.MenuBarMixin
import vaadin.scala.internal.WrapperUtil
import scala.collection._
import vaadin.scala.server.Resource

package mixins {
  trait MenuBarMixin extends AbstractComponentMixin { self: com.vaadin.ui.MenuBar => }
}

object MenuBar {

  class Command(val action: MenuItem => Unit, menuItemsMap: mutable.Map[com.vaadin.ui.MenuBar#MenuItem, MenuBar.MenuItemTrait]) extends com.vaadin.ui.MenuBar.Command {
    override def menuSelected(selectedItem: com.vaadin.ui.MenuBar#MenuItem) = action(menuItemsMap.get(selectedItem).get.asInstanceOf[MenuItem])
  }

  abstract class MenuItemTrait(menuItemsMap: mutable.Map[com.vaadin.ui.MenuBar#MenuItem, MenuBar.MenuItemTrait]) extends Wrapper {

    val p: com.vaadin.ui.MenuBar#MenuItem
    menuItemsMap += p -> this

    def parent: Option[MenuItem] = menuItemsMap.get(p.getParent) map (_.asInstanceOf[MenuItem])

    def visible = p.isVisible
    def visible_=(visible: Boolean) = p.setVisible(visible)
  }

  class Separator(val p: com.vaadin.ui.MenuBar#MenuItem, menuItemsMap: mutable.Map[com.vaadin.ui.MenuBar#MenuItem, MenuBar.MenuItemTrait]) extends MenuItemTrait(menuItemsMap)

  class MenuItem(val p: com.vaadin.ui.MenuBar#MenuItem, menuItemsMap: mutable.Map[com.vaadin.ui.MenuBar#MenuItem, MenuBar.MenuItemTrait]) extends MenuItemTrait(menuItemsMap) {

    def hasChildren = p.hasChildren

    def addSeparator() = new Separator(p.addSeparator(), menuItemsMap)
    def addSeparatorBefore(itemToAddBefore: MenuItemTrait) = new Separator(p.addSeparatorBefore(itemToAddBefore.p), menuItemsMap)

    def addItem(caption: String) = new MenuItem(p.addItem(caption, null), menuItemsMap)
    def addItem(caption: String, command: MenuItem => Unit) = new MenuItem(p.addItem(caption, new Command(command, menuItemsMap)), menuItemsMap)

    def addItem(caption: String, icon: Resource) = new MenuItem(p.addItem(caption, icon.pResource, null), menuItemsMap)
    def addItem(caption: String, icon: Resource, command: MenuItem => Unit) = new MenuItem(p.addItem(caption, icon.pResource, new Command(command, menuItemsMap)), menuItemsMap)

    def addItemBefore(caption: String, icon: Resource, itemToAddBefore: MenuItemTrait) = new MenuItem(p.addItemBefore(caption, icon.pResource, null, itemToAddBefore.p), menuItemsMap)
    def addItemBefore(caption: String, icon: Resource, command: MenuItem => Unit, itemToAddBefore: MenuItemTrait) = new MenuItem(p.addItemBefore(caption, icon.pResource, new Command(command, menuItemsMap), itemToAddBefore.p), menuItemsMap)

    def addCheckableItem(caption: String) = new CheckableMenuItem(p.addItem(caption, null), menuItemsMap)
    def addCheckableItem(caption: String, command: MenuItem => Unit) = new CheckableMenuItem(p.addItem(caption, new Command(command, menuItemsMap)), menuItemsMap)

    def addCheckableItem(caption: String, icon: Resource) = new CheckableMenuItem(p.addItem(caption, icon.pResource, null), menuItemsMap)
    def addCheckableItem(caption: String, icon: Resource, command: MenuItem => Unit) = new CheckableMenuItem(p.addItem(caption, icon.pResource, new Command(command, menuItemsMap)), menuItemsMap)

    def addCheckableItemBefore(caption: String, icon: Resource, itemToAddBefore: MenuItemTrait) = new CheckableMenuItem(p.addItemBefore(caption, icon.pResource, null, itemToAddBefore.p), menuItemsMap)
    def addCheckableItemBefore(caption: String, icon: Resource, command: MenuItem => Unit, itemToAddBefore: MenuItemTrait) = new CheckableMenuItem(p.addItemBefore(caption, icon.pResource, new Command(command, menuItemsMap), itemToAddBefore.p), menuItemsMap)

    def command = p.getCommand match {
      case null => None
      case command: MenuBar.Command => Some(command.action)
    }
    def command_=(command: Option[MenuItem => Unit]) = p.setCommand(if (command.isDefined) new Command(command.get, menuItemsMap) else null)
    def command_=(command: MenuItem => Unit) = p.setCommand(new Command(command, menuItemsMap))

    def icon: Option[Resource] = WrapperUtil.wrapperFor[Resource](p.getIcon)
    def icon_=(icon: Option[Resource]) = p.setIcon(if (icon.isDefined) icon.get.pResource else null)
    def icon_=(icon: Resource) = p.setIcon(icon.pResource)

    // TODO: for consistency rename to caption?
    def text = p.getText
    def text_=(text: String) = p.setText(text)

    def enabled = p.isEnabled
    def enabled_=(enabled: Boolean) = p.setEnabled(enabled)

    def styleName = Option(p.getStyleName)
    def styleName_=(styleName: Option[String]) = p.setStyleName(styleName.orNull)
    def styleName_=(styleName: String) = p.setStyleName(styleName)
  }

  class CheckableMenuItem(override val p: com.vaadin.ui.MenuBar#MenuItem, menuItemsMap: mutable.Map[com.vaadin.ui.MenuBar#MenuItem, MenuBar.MenuItemTrait]) extends MenuItem(p, menuItemsMap) {

    p.setCheckable(true)

    def checked = p.isChecked
    def checked_=(checked: Boolean) = p.setChecked(checked)

  }

}

class MenuBar(override val p: com.vaadin.ui.MenuBar with MenuBarMixin = new com.vaadin.ui.MenuBar with MenuBarMixin) extends AbstractComponent(p) {

  private val menuItems = mutable.Map.empty[com.vaadin.ui.MenuBar#MenuItem, MenuBar.MenuItemTrait]

  new MenuBar.MenuItem(p.getMoreMenuItem, menuItems)

  def addItem(caption: String): MenuBar.MenuItem = new MenuBar.MenuItem(p.addItem(caption, null), menuItems)
  def addItem(caption: String, command: MenuBar.MenuItem => Unit): MenuBar.MenuItem = new MenuBar.MenuItem(p.addItem(caption, new MenuBar.Command(command, menuItems)), menuItems)
  def addItem(caption: String, icon: Resource): MenuBar.MenuItem = new MenuBar.MenuItem(p.addItem(caption, icon.pResource, null), menuItems)
  def addItem(caption: String, icon: Resource, command: MenuBar.MenuItem => Unit): MenuBar.MenuItem = new MenuBar.MenuItem(p.addItem(caption, icon.pResource, new MenuBar.Command(command, menuItems)), menuItems)
  def addItemBefore(caption: String, icon: Resource, itemToAddBefore: MenuBar.MenuItem): MenuBar.MenuItem = new MenuBar.MenuItem(p.addItemBefore(caption, icon.pResource, null, itemToAddBefore.p), menuItems)
  def addItemBefore(caption: String, icon: Resource, command: MenuBar.MenuItem => Unit, itemToAddBefore: MenuBar.MenuItem): MenuBar.MenuItem = new MenuBar.MenuItem(p.addItemBefore(caption, icon.pResource, new MenuBar.Command(command, menuItems), itemToAddBefore.p), menuItems)

  def moreMenuItem = menuItems.get(p.getMoreMenuItem) map { _.asInstanceOf[MenuBar.MenuItem] }
  def moreMenuItem_=(menuItem: Option[MenuBar.MenuItem]) = menuItem match {
    case None =>
      p.setMoreMenuItem(null)
      new MenuBar.MenuItem(p.getMoreMenuItem, menuItems)
    case Some(menuItem) =>
      p.setMoreMenuItem(menuItem.p)
  }
  def moreMenuItem_=(menuItem: MenuBar.MenuItem) = p.setMoreMenuItem(menuItem.p)

  def autoOpen = p.isAutoOpen
  def autoOpen_=(autoOpen: Boolean) = p.setAutoOpen(autoOpen)

  def htmlContentAllowed = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean) = p.setHtmlContentAllowed(htmlContentAllowed)

}