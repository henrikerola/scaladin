package vaadin.scala.internal

import vaadin.scala.Action
import vaadin.scala.mixins.ActionMixin

/**
 * @author Henri Kerola / Vaadin
 */
class ActionHandler(val actions: Action.GetActionsEvent => Seq[Action]) extends com.vaadin.event.Action.Handler {

  def getActions(target: Any, sender: Any) =
    actions(Action.GetActionsEvent(Option(target), sender)).toArray map (_.p)

  def handleAction(action: com.vaadin.event.Action, sender: Any, target: Any) {
    val handler = action.asInstanceOf[ActionMixin].wrapper.asInstanceOf[Action].handler
    handler.foreach(_(Action.HandleActionEvent(WrapperUtil.wrapperFor[Action](action).get, sender, Option(target))))
  }
}
