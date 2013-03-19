package vaadin.scala

import event.Event
import vaadin.scala.mixins.FieldGroupMixin
import vaadin.scala.mixins.FieldMixin
import scala.util.Try
import vaadin.scala.FieldGroup.PreCommitEvent
import vaadin.scala.internal.PreCommitHandler
import vaadin.scala.internal.HandlersTrait
import vaadin.scala.FieldGroup.PostCommitEvent
import vaadin.scala.internal.PostCommitHandler

package mixins {
  trait FieldGroupMixin extends TypedScaladinMixin[FieldGroup]
}

object FieldGroup {
  case class PreCommitEvent(fieldBinder: FieldGroup) extends Event
  case class PostCommitEvent(fieldBinder: FieldGroup) extends Event

  case class CommitSuccess()
  case class CommitFailed(error: String)
  type CommitResult = Either[CommitFailed, CommitSuccess]
}

class FieldGroup(override val p: com.vaadin.data.fieldgroup.FieldGroup with FieldGroupMixin = new com.vaadin.data.fieldgroup.FieldGroup with FieldGroupMixin) extends Wrapper {

  import scala.collection.JavaConverters._
  import scala.util.control.Exception._
  import scala.collection.mutable

  fieldFactory = DefaultFieldGroupFieldFactory

  val preCommitHandlers: mutable.Set[PreCommitEvent => FieldGroup.CommitResult] = new HandlersTrait[PreCommitEvent, PreCommitHandler, FieldGroup.CommitResult] {
    def addHandler(handler: PreCommitHandler) = p.addCommitHandler(handler)

    def removeHandler(handler: PreCommitHandler) = p.removeCommitHandler(handler)

    def createListener(action: PreCommitEvent => Either[FieldGroup.CommitFailed, FieldGroup.CommitSuccess]): PreCommitHandler = new PreCommitHandler(action)
  }

  val postCommitHandlers: mutable.Set[PostCommitEvent => Either[FieldGroup.CommitFailed, FieldGroup.CommitSuccess]] = new HandlersTrait[PostCommitEvent, PostCommitHandler, FieldGroup.CommitResult] {
    def addHandler(handler: PostCommitHandler) = p.addCommitHandler(handler)

    def removeHandler(handler: PostCommitHandler) = p.removeCommitHandler(handler)

    def createListener(action: PostCommitEvent => Either[FieldGroup.CommitFailed, FieldGroup.CommitSuccess]): PostCommitHandler = new PostCommitHandler(action)
  }

  def this(item: Option[Item]) = this(new com.vaadin.data.fieldgroup.FieldGroup(item map (_.p) orNull) with FieldGroupMixin)

  //make sure that the Item wrapper instance is the same (type) all the time
  protected var itemWrapper: Option[Item] = None
  protected def internalSetItem(optionWrap: Option[Item]): Unit = optionWrap match {
    case Some(wrapper) =>
      itemWrapper = optionWrap; p.setItemDataSource(wrapper.p)
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

  def commit: FieldGroup.CommitResult = catching(classOf[com.vaadin.data.fieldgroup.FieldGroup.CommitException]) either (p.commit) fold (
    exception => Left(FieldGroup.CommitFailed(exception.getMessage)),
    nothing => Right(FieldGroup.CommitSuccess()))

  def discard: Unit = p.discard

  def field(propertyId: Any): Option[Field[_]] = Option(p.getField(propertyId)) map (_.asInstanceOf[com.vaadin.ui.Field[_] with FieldMixin[_]]) map (_.wrapper)
  def propertyId(field: Field[_]): Option[Any] = Option(p.getPropertyId(field.p))

  def valid: Boolean = p.isValid
  def modified: Boolean = p.isModified

  def fieldFactory: Option[FieldGroupFieldFactory] = wrapperFor(p.getFieldFactory)
  def fieldFactory_=(factory: Option[FieldGroupFieldFactory]): Unit = p.setFieldFactory(factory map (_.p) orNull)
  def fieldFactory_=(factory: FieldGroupFieldFactory): Unit = p.setFieldFactory(if (factory != null) factory.p else null)
  def fieldFactory_=[FT <: Field[_]](fieldFunction: (Class[_], Class[_]) => Option[FT]): Unit = this.fieldFactory = FieldGroupFieldFactory(fieldFunction)

  //TODO build & bind
}