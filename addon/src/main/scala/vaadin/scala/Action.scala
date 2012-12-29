package vaadin.scala

import internal.ActionHandler
import vaadin.scala.mixins.{ ActionContainerMixin, ActionMixin }

package mixins {
  trait ActionMixin extends ScaladinMixin
  trait ActionContainerMixin extends ScaladinMixin
}

object Action {

  trait Container {

    def p: com.vaadin.event.Action.Container with ActionContainerMixin

    private[this] var actionHandler: Option[ActionHandler] = None

    def actions: Option[GetActionsEvent => Seq[Action]] = {
      actionHandler.map(_.actions)
    }

    def actions_=(actions: Option[GetActionsEvent => Seq[Action]]): Unit = actions match {
      case Some(actions) => this.actions = actions
      case None =>
        actionHandler.foreach(p.removeActionHandler(_))
        actionHandler = None
    }

    def actions_=(actions: GetActionsEvent => Seq[Action]): Unit = {
      actionHandler.foreach(p.removeActionHandler(_))
      actionHandler = Some(new ActionHandler(actions))
      p.addActionHandler(actionHandler.get)
    }

    def actions_=(actions: Seq[Action]): Unit = this.actions = (a => actions)
  }

  case class GetActionsEvent(target: Option[Any], sender: Any) extends Event
  case class HandleActionEvent(action: Action, sender: Any, target: Option[Any]) extends Event

  def apply(caption: String): Action = {
    val action = new Action
    action.caption = caption
    action
  }

  def apply(caption: String, handler: HandleActionEvent => Unit): Action = {
    val action = new Action
    action.caption = caption
    action.handler = Option(handler)
    action
  }

  def apply(caption: String, handler: => Unit): Action = {
    val action = new Action
    action.caption = caption
    action.handler = Option(a => handler)
    action
  }

  def apply(caption: String, icon: Resource): Action = {
    val action = new Action
    action.caption = caption
    action.icon = icon
    action
  }

  def apply(caption: String, icon: Resource, handler: HandleActionEvent => Unit): Action = {
    val action = new Action
    action.caption = caption
    action.icon = icon
    action.handler = Option(handler)
    action
  }

  def apply(caption: String, icon: Resource, handler: => Unit): Action = {
    val action = new Action
    action.caption = caption
    action.icon = icon
    action.handler = Option(a => handler)
    action
  }

}

/**
 * @see com.vaadin.event.Action
 * @author Henri Kerola / Vaadin
 */
class Action(val p: com.vaadin.event.Action with ActionMixin = new com.vaadin.event.Action(null) with ActionMixin)
    extends Wrapper {

  p.wrapper = this

  def caption: Option[String] = Option(p.getCaption)
  def caption_=(caption: Option[String]): Unit = p.setCaption(caption.orNull)
  def caption_=(caption: String): Unit = p.setCaption(caption)

  def icon: Option[Resource] = wrapperFor[Resource](p.getIcon)
  def icon_=(icon: Option[Resource]): Unit = if (icon.isDefined) p.setIcon(icon.get.p) else p.setIcon(null)
  def icon_=(icon: Resource): Unit = p.setIcon(icon.p)

  var handler: Option[Action.HandleActionEvent => Unit] = None
}
