package vaadin.scala.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{ BeforeAndAfter, FunSuite }
import org.scalatest.mock.MockitoSugar
import vaadin.scala._
import internal.ActionHandler
import vaadin.scala.mixins.ActionContainerMixin
import org.mockito.{ Matchers, ArgumentCaptor, Mockito }
import vaadin.scala.Action.{ HandleActionEvent, GetActionsEvent }
import vaadin.scala.server.ThemeResource

@RunWith(classOf[JUnitRunner])
class ActionTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  var actionContainer: Action.Container = _

  before {
    actionContainer = new Action.Container {
      val p = mock[com.vaadin.event.Action.Container with ActionContainerMixin]
    }
  }

  val icon = ThemeResource("test.png")
  val actionFunction = { a: Action.HandleActionEvent => }
  val actionFunction2 = {}

  test("Action(caption)") {
    val action = Action("caption")
    assert(action.caption === Some("caption"))
    assert(action.icon === None)
    assert(action.handler === None)
  }

  test("Action(caption, handler)") {
    val action = Action("caption", actionFunction)
    assert(action.caption === Some("caption"))
    assert(action.icon === None)
    assert(action.handler === Some(actionFunction))
  }

  test("Action(caption, handler2)") {
    val action = Action("caption", actionFunction2)
    assert(action.caption === Some("caption"))
    assert(action.icon === None)
    assert(action.handler.get(mock[HandleActionEvent]) === actionFunction2)
  }

  test("Action(caption, icon)") {
    val action = Action("caption", icon)
    assert(action.caption === Some("caption"))
    assert(action.icon === Some(icon))
    assert(action.handler === None)
  }

  test("Action(caption, icon, handler)") {
    val action = Action("caption", icon, actionFunction)
    assert(action.caption === Some("caption"))
    assert(action.icon === Some(icon))
    assert(action.handler === Some(actionFunction))
  }

  test("Action(caption, icon, handler2)") {
    val action = Action("caption", icon, actionFunction2)
    assert(action.caption === Some("caption"))
    assert(action.icon === Some(icon))
    assert(action.handler.get(mock[HandleActionEvent]) === actionFunction2)
  }

  test("Default value of Action.Container.actions is None") {
    assert(actionContainer.actions === None)
  }

  test("Action.Container.actions_=(actions: Option[GetActionsEvent => Seq[Action]])") {
    val argument = ArgumentCaptor.forClass(classOf[ActionHandler])
    val actions = { e: GetActionsEvent => Action("Action 1") :: Action("Action 2") :: Nil }

    actionContainer.actions = Some(actions)
    Mockito.verify(actionContainer.p).addActionHandler(argument.capture())
    assert(argument.getValue.actions === actions)
    assert(actionContainer.actions === Some(actions))

    actionContainer.actions = None
    Mockito.verify(actionContainer.p).removeActionHandler(argument.getValue)
    assert(actionContainer.actions === None)
  }

  test("Action.Container.actions_=(actions: GetActionsEvent => Seq[Action])") {
    val argument = ArgumentCaptor.forClass(classOf[ActionHandler])
    val actions = { e: GetActionsEvent => Action("Action 1") :: Action("Action 2") :: Nil }

    actionContainer.actions = actions
    Mockito.verify(actionContainer.p).addActionHandler(argument.capture())
    assert(argument.getValue.actions === actions)
    assert(actionContainer.actions === Some(actions))
  }

  test("Action.Container.actions_=(actions: Seq[Action])") {
    val argument = ArgumentCaptor.forClass(classOf[ActionHandler])
    val actions = Action("Action 1") :: Action("Action 2") :: Nil

    actionContainer.actions = actions
    Mockito.verify(actionContainer.p).addActionHandler(argument.capture())
    assert(argument.getValue.actions(mock[GetActionsEvent]) === actions)
    assert(actionContainer.actions.get(mock[GetActionsEvent]) === actions)
  }

  test("Replacing actions should remove old actions") {
    val argument = ArgumentCaptor.forClass(classOf[ActionHandler])
    val actions1 = { e: GetActionsEvent => Action("Action 1") :: Action("Action 2") :: Nil }
    val actions2 = { e: GetActionsEvent => Action("Test 1") :: Nil }

    actionContainer.actions = actions1

    Mockito.reset(actionContainer.p)

    actionContainer.actions = actions2

    Mockito.verify(actionContainer.p).removeActionHandler(argument.capture())
    assert(argument.getValue.actions === actions1)

    Mockito.verify(actionContainer.p).addActionHandler(argument.capture())
    assert(argument.getValue.actions === actions2)
  }

  test("HandleActionEvent params") {
    class HandleActionFunction extends Function1[HandleActionEvent, Unit] {
      def apply(x: HandleActionEvent) {}
    }

    val argument = ArgumentCaptor.forClass(classOf[HandleActionEvent])
    val handlerMock = mock[HandleActionFunction]

    val action = Action("Action 1", handlerMock)
    val actions = { e: GetActionsEvent => action :: Action("Action 2") :: Nil }
    val actionHandler = new ActionHandler(actions)
    val sender = "sender"
    val target = "target"

    actionHandler.handleAction(action.p, sender, target)
    Mockito.verify(handlerMock).apply(argument.capture())
    assert(argument.getValue.action === action)
    assert(argument.getValue.sender === sender)
    assert(argument.getValue.target === Some(target))

    Mockito.reset(handlerMock)

    actionHandler.handleAction(action.p, sender, null)
    Mockito.verify(handlerMock).apply(argument.capture())
    assert(argument.getValue.action === action)
    assert(argument.getValue.sender === sender)
    assert(argument.getValue.target === None)
  }

  test("GetActionsEvent params") {
    class GetActionsFunction extends Function1[GetActionsEvent, Seq[Action]] {
      def apply(x: GetActionsEvent): Seq[Action] = { null }
    }

    val sender = "sender"
    val target = "target"

    val argument = ArgumentCaptor.forClass(classOf[GetActionsEvent])
    val handlerMock = mock[GetActionsFunction]
    val action = Action("Action 1")
    val actionHandler = new ActionHandler(handlerMock)

    Mockito.when(handlerMock.apply(Matchers.anyObject[GetActionsEvent])).thenReturn(action :: Nil)
    assert(actionHandler.getActions(target, sender) === Array(action.p))
    Mockito.verify(handlerMock).apply(argument.capture())
    assert(argument.getValue.target === Some(target))
    assert(argument.getValue.sender === sender)

    Mockito.reset(handlerMock)

    Mockito.when(handlerMock.apply(Matchers.anyObject[GetActionsEvent])).thenReturn(Nil)
    assert(actionHandler.getActions(null, sender) === Array())
    Mockito.verify(handlerMock).apply(argument.capture())
    assert(argument.getValue.target === None)
    assert(argument.getValue.sender === sender)
  }

}
