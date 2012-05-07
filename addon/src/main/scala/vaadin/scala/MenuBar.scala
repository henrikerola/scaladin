package vaadin.scala

import vaadin.scala.mixins.MenuBarMixin

package mixins {
  trait MenuBarMixin extends AbstractComponentMixin
}

object MenuBar {

  class Command(val action: MenuItem => Unit) extends com.vaadin.ui.MenuBar.Command {
    override def menuSelected(selectedItem: com.vaadin.ui.MenuBar#MenuItem) = action(WrapperRegistry.get[MenuItem](selectedItem).get)
  }

  trait MenuItemTrait extends Wrapper {

    val p: com.vaadin.ui.MenuBar#MenuItem

    def parent: Option[MenuItem] = WrapperRegistry.get[MenuItem](p.getParent)

    def visible = p.isVisible
    def visible_=(visible: Boolean) = p.setVisible(visible)
  }

  class Separator(val p: com.vaadin.ui.MenuBar#MenuItem) extends MenuItemTrait {
    WrapperRegistry.put(this)
  }

  class MenuItem(val p: com.vaadin.ui.MenuBar#MenuItem) extends MenuItemTrait {
    WrapperRegistry.put(this)

    def hasChildren = p.hasChildren

    def addSeparator() = new Separator(p.addSeparator())
    def addSeparatorBefore(itemToAddBefore: MenuItemTrait) = new Separator(p.addSeparatorBefore(itemToAddBefore.p))

    def addItem(caption: String) = new MenuItem(p.addItem(caption, null))
    def addItem(caption: String, command: MenuItem => Unit) = new MenuItem(p.addItem(caption, new Command(command)))

    def addItem(caption: String, icon: Resource) = new MenuItem(p.addItem(caption, icon.p, null))
    def addItem(caption: String, icon: Resource, command: MenuItem => Unit) = new MenuItem(p.addItem(caption, icon.p, new Command(command)))

    def addItemBefore(caption: String, icon: Resource, itemToAddBefore: MenuItemTrait) = new MenuItem(p.addItemBefore(caption, icon.p, null, itemToAddBefore.p))
    def addItemBefore(caption: String, icon: Resource, command: MenuItem => Unit, itemToAddBefore: MenuItemTrait) = new MenuItem(p.addItemBefore(caption, icon.p, new Command(command), itemToAddBefore.p))

    def addCheckableItem(caption: String) = new CheckableMenuItem(p.addItem(caption, null))
    def addCheckableItem(caption: String, command: MenuItem => Unit) = new CheckableMenuItem(p.addItem(caption, new Command(command)))

    def addCheckableItem(caption: String, icon: Resource) = new CheckableMenuItem(p.addItem(caption, icon.p, null))
    def addCheckableItem(caption: String, icon: Resource, command: MenuItem => Unit) = new CheckableMenuItem(p.addItem(caption, icon.p, new Command(command)))

    def addCheckableItemBefore(caption: String, icon: Resource, itemToAddBefore: MenuItemTrait) = new CheckableMenuItem(p.addItemBefore(caption, icon.p, null, itemToAddBefore.p))
    def addCheckableItemBefore(caption: String, icon: Resource, command: MenuItem => Unit, itemToAddBefore: MenuItemTrait) = new CheckableMenuItem(p.addItemBefore(caption, icon.p, new Command(command), itemToAddBefore.p))

    def command = p.getCommand match {
      case null => None
      case command: MenuBar.Command => Some(command.action)
    }
    def command_=(command: Option[MenuItem => Unit]) = p.setCommand(if (command.isDefined) new Command(command.get) else null)
    def command_=(command: MenuItem => Unit) = p.setCommand(new Command(command))

    def icon: Option[Resource] = WrapperRegistry.get[Resource](p.getIcon)
    def icon_=(icon: Option[Resource]) = p.setIcon(if (icon.isDefined) icon.get.p else null)
    def icon_=(icon: Resource) = p.setIcon(icon.p)

    // TODO: for consistency rename to caption?
    def text = p.getText
    def text_=(text: String) = p.setText(text)

    def enabled = p.isEnabled
    def enabled_=(enabled: Boolean) = p.setEnabled(enabled)

    def styleName = Option(p.getStyleName)
    def styleName_=(styleName: Option[String]) = p.setStyleName(styleName.getOrElse(null))
    def styleName_=(styleName: String) = p.setStyleName(styleName)
  }

  class CheckableMenuItem(override val p: com.vaadin.ui.MenuBar#MenuItem) extends MenuItem(p) {

    p.setCheckable(true)

    def checked = p.isChecked
    def checked_=(checked: Boolean) = p.setChecked(checked)

  }

}

class MenuBar(override val p: com.vaadin.ui.MenuBar with MenuBarMixin = new com.vaadin.ui.MenuBar with MenuBarMixin) extends AbstractComponent(p) {

  WrapperRegistry.put(this)
  WrapperRegistry.put(new MenuBar.MenuItem(p.getMoreMenuItem))

  def addItem(caption: String) = new MenuBar.MenuItem(p.addItem(caption, null))
  def addItem(caption: String, command: MenuBar.MenuItem => Unit) = new MenuBar.MenuItem(p.addItem(caption, new MenuBar.Command(command)))
  def addItem(caption: String, icon: Resource) = new MenuBar.MenuItem(p.addItem(caption, icon.p, null))
  def addItem(caption: String, icon: Resource, command: MenuBar.MenuItem => Unit) = new MenuBar.MenuItem(p.addItem(caption, icon.p, new MenuBar.Command(command)))
  def addItemBefore(caption: String, icon: Resource, itemToAddBefore: MenuBar.MenuItem) = new MenuBar.MenuItem(p.addItemBefore(caption, icon.p, null, itemToAddBefore.p))
  def addItemBefore(caption: String, icon: Resource, command: MenuBar.MenuItem => Unit, itemToAddBefore: MenuBar.MenuItem) = new MenuBar.MenuItem(p.addItemBefore(caption, icon.p, new MenuBar.Command(command), itemToAddBefore.p))

  def moreMenuItem = WrapperRegistry.get[MenuBar.MenuItem](p.getMoreMenuItem)
  def moreMenuItem_=(menuItem: Option[MenuBar.MenuItem]) = menuItem match {
    case None =>
      p.setMoreMenuItem(null)
      WrapperRegistry.put(new MenuBar.MenuItem(p.getMoreMenuItem))
    case Some(menuItem) =>
      p.setMoreMenuItem(menuItem.p)
  }
  def moreMenuItem_=(menuItem: MenuBar.MenuItem) = p.setMoreMenuItem(menuItem.p)

  def autoOpen = p.isAutoOpen
  def autoOpen_=(autoOpen: Boolean) = p.setAutoOpen(autoOpen)

  def htmlContentAllowed = p.isHtmlContentAllowed
  def htmlContentAllowed_=(htmlContentAllowed: Boolean) = p.setHtmlContentAllowed(htmlContentAllowed)

}