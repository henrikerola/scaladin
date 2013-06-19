package vaadin.scala

import event.ComponentEvent
import vaadin.scala.mixins.FieldGroupMixin
import vaadin.scala.mixins.FieldMixin
import scala.util.Try
import vaadin.scala.FieldGroup.{ CommitSuccess, CommitFailed, PreCommitEvent, PostCommitEvent }
import vaadin.scala.internal.PreCommitHandler
import vaadin.scala.internal.HandlersTrait
import vaadin.scala.internal.PostCommitHandler
import scala.reflect.runtime.universe._
import scala.reflect.ClassTag
import scala.annotation.StaticAnnotation
import com.vaadin.data.fieldgroup.{ FieldGroup => VaadinFieldGroup }
import com.vaadin.data.fieldgroup.FieldGroup.{ CommitException => VaadinCommitException }

package mixins {
  trait FieldGroupMixin extends TypedScaladinMixin[FieldGroup]
}

object FieldGroup {
  case class PreCommitEvent(fieldBinder: FieldGroup)
  case class PostCommitEvent(fieldBinder: FieldGroup)

  case class CommitSuccess()
  case class CommitFailed(error: String)
  type CommitResult = Either[CommitFailed, CommitSuccess]

  case class propertyId(value: String) extends StaticAnnotation

  def apply(item: Item): FieldGroup = {
    new FieldGroup(new VaadinFieldGroup(item.p) with FieldGroupMixin)
  }
}

class FieldGroup(override val p: VaadinFieldGroup with FieldGroupMixin = new VaadinFieldGroup with FieldGroupMixin)
    extends Wrapper {

  import scala.collection.JavaConverters._
  import scala.util.control.Exception._
  import scala.collection.mutable

  fieldFactory = DefaultFieldGroupFieldFactory

  val preCommitHandlers: mutable.Set[PreCommitEvent => FieldGroup.CommitResult] =
    new HandlersTrait[PreCommitEvent, PreCommitHandler, FieldGroup.CommitResult] {
      def addHandler(handler: PreCommitHandler) = p.addCommitHandler(handler)

      def removeHandler(handler: PreCommitHandler) = p.removeCommitHandler(handler)

      def createListener(action: PreCommitEvent => Either[CommitFailed, CommitSuccess]): PreCommitHandler =
        new PreCommitHandler(action)
    }

  val postCommitHandlers: mutable.Set[PostCommitEvent => Either[FieldGroup.CommitFailed, FieldGroup.CommitSuccess]] =
    new HandlersTrait[PostCommitEvent, PostCommitHandler, FieldGroup.CommitResult] {
      def addHandler(handler: PostCommitHandler) = p.addCommitHandler(handler)

      def removeHandler(handler: PostCommitHandler) = p.removeCommitHandler(handler)

      def createListener(action: PostCommitEvent => Either[CommitFailed, CommitSuccess]): PostCommitHandler =
        new PostCommitHandler(action)
    }

  //make sure that the Item wrapper instance is the same (type) all the time
  protected var itemWrapper: Option[Item] = None
  protected def internalSetItem(optionWrap: Option[Item]) {
    optionWrap match {
      case Some(wrapper) =>
        itemWrapper = optionWrap; p.setItemDataSource(wrapper.p)
      case None => itemWrapper = None; p.setItemDataSource(null)
    }
  }

  def item: Option[Item] = itemWrapper

  def item_=(item: Item) { internalSetItem(Some(item)) }
  def item_=(item: Option[Item]) { internalSetItem(item) }

  def buffered: Boolean = p.isBuffered
  def buffered_=(isBuffered: Boolean) { p.setBuffered(isBuffered) }

  def enabled: Boolean = p.isEnabled
  def enabled_=(isEnabled: Boolean) { p.setEnabled(isEnabled) }

  def readOnly: Boolean = p.isReadOnly
  def readOnly_=(isReadOnly: Boolean) { p.setReadOnly(isReadOnly) }

  def fields: Iterable[Field[_]] =
    p.getFields.asScala.map(vaadinField => vaadinField.asInstanceOf[com.vaadin.ui.Field[_] with FieldMixin[_]].wrapper)

  def bind(field: Field[_], propertyId: Any): Try[Unit] =
    catching(classOf[com.vaadin.data.fieldgroup.FieldGroup.BindException]) withTry (p.bind(field.p, propertyId))
  def unbind(field: Field[_]): Try[Unit] =
    catching(classOf[com.vaadin.data.fieldgroup.FieldGroup.BindException]) withTry (p.unbind(field.p))

  def boundPropertyIds: Iterable[Any] = p.getBoundPropertyIds.asScala
  def unboundPropertyIds: Iterable[Any] = p.getUnboundPropertyIds.asScala

  def commit(): FieldGroup.CommitResult = catching(classOf[VaadinCommitException]) either (p.commit) fold (
    exception => Left(FieldGroup.CommitFailed(exception.getMessage)),
    nothing => Right(FieldGroup.CommitSuccess()))

  def discard() { p.discard() }

  def field(propertyId: Any): Option[Field[_]] =
    Option(p.getField(propertyId)) map (_.asInstanceOf[com.vaadin.ui.Field[_] with FieldMixin[_]]) map (_.wrapper)
  def propertyId(field: Field[_]): Option[Any] = Option(p.getPropertyId(field.p))

  def valid: Boolean = p.isValid
  def modified: Boolean = p.isModified

  def fieldFactory: Option[FieldGroupFieldFactory] = wrapperFor(p.getFieldFactory)
  def fieldFactory_=(factory: Option[FieldGroupFieldFactory]) { p.setFieldFactory(peerFor(factory)) }
  def fieldFactory_=(factory: FieldGroupFieldFactory) { p.setFieldFactory(factory.p) }
  def fieldFactory_=[FT <: Field[_]](fieldFunction: (Class[_], Class[_]) => Option[FT]) {
    this.fieldFactory = FieldGroupFieldFactory(fieldFunction)
  }

  protected def build[T <: Field[_]](caption: String, dataType: Class[_], fieldType: Class[T]): Option[T] = {
    fieldFactory.get.createField[T](dataType, fieldType) map { f =>
      f.caption = caption
      f
    }
  }

  private def findAnnotation[T: TypeTag](member: Symbol): Option[T] = {
    val propertyIdAnnotation = member.annotations.find { a => a.tpe <:< typeOf[T] }
    propertyIdAnnotation map { constructAnnotationInstance(_) }
  }

  private def constructAnnotationInstance[T: TypeTag](annotation: Annotation): T = {
    val rm = runtimeMirror(getClass.getClassLoader)
    val annotationArgValues = annotation.scalaArgs.map { _.productElement(0).asInstanceOf[Constant].value }
    val classSymbol = typeOf[T].typeSymbol.asClass
    val classMirror = rm.reflectClass(classSymbol)
    val constructorMethodSymbol = typeOf[T].declaration(nme.CONSTRUCTOR).asMethod
    val constructorMethodMirror = classMirror.reflectConstructor(constructorMethodSymbol)
    constructorMethodMirror(annotationArgValues: _*).asInstanceOf[T]
  }

  private def getFieldValue[T: TypeTag](objectWithMemberFields: T, name: Name): Option[Any] = {
    val rm = runtimeMirror(objectWithMemberFields.getClass.getClassLoader)
    val symbol = typeOf[T].declaration(name).asTerm
    implicit val classTag = ClassTag[T](objectWithMemberFields.getClass) // rm.reflect needs ClassTag
    val mirror = rm.reflect(objectWithMemberFields).reflectField(symbol)
    Option(mirror.get)
  }

  def bindMemberFields[T: TypeTag](objectWithMemberFields: T) {
    buildAndBindMemberFields(objectWithMemberFields, false)
  }

  protected def buildAndBindMemberFields[T: TypeTag](objectWithMemberFields: T, buildFields: Boolean) {
    typeOf[T].members.filter(_.typeSignature <:< typeOf[Field[_]]).foreach { m =>
      val field = getFieldValue(objectWithMemberFields, m.name).asInstanceOf[Option[Field[_]]]
      val annotatedPropertyId = findAnnotation[FieldGroup.propertyId](m)
      // See why .trim is needed: https://issues.scala-lang.org/browse/SI-5736
      val propertyId = annotatedPropertyId.fold(m.name.decoded.trim) { _.value }

      // TODO: implement the build part

      field.foreach {
        bind(_, propertyId)
      }
    }

  }

  //TODO build & bind
}