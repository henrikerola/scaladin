package vaadin.scala

import vaadin.scala.mixins.FieldGroupMixin
import vaadin.scala.mixins.FieldMixin
import scala.util.Try
import vaadin.scala.FieldGroup.PreCommitEvent
import vaadin.scala.internal.PreCommitHandler
import vaadin.scala.internal.HandlersTrait
import vaadin.scala.FieldGroup.PostCommitEvent
import vaadin.scala.internal.PostCommitHandler

package mixins {
  trait FieldGroupMixin extends TypedScaladinMixin[FieldGroup] { self: com.vaadin.data.fieldgroup.FieldGroup =>
    override def getField(propertyId: Any): com.vaadin.ui.Field[_] with FieldMixin[_] = self.getField(propertyId).asInstanceOf[com.vaadin.ui.Field[_] with FieldMixin[_]]
    override def getFieldFactory(): com.vaadin.data.fieldgroup.FieldGroupFieldFactory with FieldGroupFieldFactoryMixin = self.getFieldFactory().asInstanceOf[com.vaadin.data.fieldgroup.FieldGroupFieldFactory with FieldGroupFieldFactoryMixin]
  }
}

object FieldGroup {
  case class PreCommitEvent(fieldBinder: FieldGroup) extends Event
  case class PostCommitEvent(fieldBinder: FieldGroup) extends Event
}

class FieldGroup(override val p: com.vaadin.data.fieldgroup.FieldGroup with FieldGroupMixin = new com.vaadin.data.fieldgroup.FieldGroup with FieldGroupMixin) extends Wrapper {

  import scala.collection.JavaConverters._
  import scala.util.control.Exception._
  import scala.collection.mutable

  val preCommitHandlers: mutable.Set[PreCommitEvent => Try[Unit]] = new HandlersTrait[PreCommitEvent, PreCommitHandler] {
    def addHandler(handler: PreCommitHandler) = p.addCommitHandler(handler)

    def removeHandler(handler: PreCommitHandler) = p.removeCommitHandler(handler)

    def createListener(action: PreCommitEvent => Try[Unit]): PreCommitHandler = new PreCommitHandler(action)
  }

  val postCommitHandlers: mutable.Set[PostCommitEvent => Try[Unit]] = new HandlersTrait[PostCommitEvent, PostCommitHandler] {
    def addHandler(handler: PostCommitHandler) = p.addCommitHandler(handler)

    def removeHandler(handler: PostCommitHandler) = p.removeCommitHandler(handler)

    def createListener(action: PostCommitEvent => Try[Unit]): PostCommitHandler = new PostCommitHandler(action)
  }

  def this(item: Option[Item]) = this(new com.vaadin.data.fieldgroup.FieldGroup(item map (_.p) orNull) with FieldGroupMixin)

  //make sure that the Item wrapper instance is the same (type) all the time
  protected var itemWrapper: Option[Item] = None
  protected def internalSetItem(optionWrap: Option[Item]): Unit = optionWrap match {
    case Some(wrapper) => itemWrapper = optionWrap; p.setItemDataSource(wrapper.p)
    case None => itemWrapper = None; p.setItemDataSource(null)
  }

  def item: Option[Item] = itemWrapper

  def item_=(item: Item) = internalSetItem(Some(item))
  def item_=(item: Option[Item]) = internalSetItem(item)

  def buffered: Boolean = p.isBuffered
  def buffered_=(isBuffered: Boolean): Unit = p.setBuffered(isBuffered)

  def enabled: Boolean = p.isEnabled
  def enabled_=(isEnabled: Boolean): Unit = p.setEnabled(isEnabled)

  def readOnly: Boolean = p.isReadOnly
  def readOnly_=(isReadOnly: Boolean): Unit = p.setReadOnly(isReadOnly)

  def fields: Iterable[Field[_]] = p.getFields.asScala.map(vaadinField => vaadinField.asInstanceOf[com.vaadin.ui.Field[_] with FieldMixin[_]].wrapper)

  def bind(field: Field[_], propertyId: Any): Try[Unit] = catching(classOf[com.vaadin.data.fieldgroup.FieldGroup.BindException]) withTry (p.bind(field.p, propertyId))
  def unbind(field: Field[_]): Try[Unit] = catching(classOf[com.vaadin.data.fieldgroup.FieldGroup.BindException]) withTry (p.unbind(field.p))

  def boundPropertyIds: Iterable[Any] = p.getBoundPropertyIds.asScala
  def unboundPropertyIds: Iterable[Any] = p.getUnboundPropertyIds.asScala

  def commit: Try[Unit] = catching(classOf[com.vaadin.data.fieldgroup.FieldGroup.CommitException]) withTry (p.commit)
  def discard: Unit = p.discard

  def field(propertyId: Any): Option[Field[_]] = Option(p.getField(propertyId)) map (_.wrapper)
  def propertyId(field: Field[_]): Option[Any] = Option(p.getPropertyId(field.p))

  def valid: Boolean = p.isValid
  def modified: Boolean = p.isModified

  def fieldFactory: Option[FieldGroupFieldFactory] = wrapperFor(p.getFieldFactory)
  def fieldFactory_=(factory: Option[FieldGroupFieldFactory]) = p.setFieldFactory(factory map (_.p) orNull)

  //build & bind currently unsupported
}