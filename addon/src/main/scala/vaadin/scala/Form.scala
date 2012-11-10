package vaadin.scala

import vaadin.scala.mixins.FormMixin
import vaadin.scala.mixins.HasComponentsMixin

package mixins {
  trait FormMixin extends AbstractFieldMixin with HasComponentsMixin { self: com.vaadin.ui.Form => }
}

class Form(override val p: com.vaadin.ui.Form with FormMixin = new com.vaadin.ui.Form with FormMixin) extends AbstractField[Object](p) with Item.Viewer with Buffered with HasComponents {

  //override default implementation with a wrapper
  formFieldFactory = DefaultFieldFactory
  layout = new FormLayout
  footer = new HorizontalLayout

  def formFieldFactory: Option[FormFieldFactory] = wrapperFor[FormFieldFactory](p.getFormFieldFactory)
  def formFieldFactory_=(factory: FormFieldFactory) = p.setFormFieldFactory(factory.p)
  def formFieldFactory_=(factoryFunction: FormFieldIngredients => Option[Field[_]]) = p.setFormFieldFactory(FormFieldFactory(factoryFunction).p)
  def formFieldFactory_=(factory: Option[FormFieldFactory]) = factory match {
    case Some(factory) => p.setFormFieldFactory(factory.p)
    case None => p.setFormFieldFactory(null)
  }

  def addField(propertyId: Any, field: Field[_]): Unit = p.addField(propertyId, field.p)
  def addField(propertyId: Option[Any], field: Option[Field[_]]): Unit = p.addField(propertyId.orNull, field.map(_.p).orNull)

  def field(propertyId: Any): Option[Field[_]] = wrapperFor[Field[_]](p.getField(propertyId))

  def removeAllProperties: Boolean = p.removeAllProperties()

  //form layout cannot be None
  def layout: Layout = wrapperFor[Layout](p.getLayout) get
  def layout_=(layout: Layout): Unit = p.setLayout(layout.p)

  import scala.collection.JavaConverters._

  def itemDataSource(newDataSource: Item, propertyIds: Iterable[Any]): Unit = p.setItemDataSource(newDataSource.p, propertyIds.asJavaCollection)

  def visibleItemProperties: Iterable[Any] = p.getVisibleItemProperties.asScala
  def visibleItemProperties_=(visibleItemProperties: Iterable[Any]): Unit = p.setVisibleItemProperties(visibleItemProperties.asJavaCollection)

  //form footer cannot be None
  def footer: Layout = wrapperFor[Layout](p.getFooter) get
  def footer_=(footer: Layout): Unit = p.setFooter(footer.p)

  def validationVisibleOnCommit: Boolean = p.isValidationVisibleOnCommit
  def validationVisibleOnCommit_=(isValidationVisibleOnCommit: Boolean): Unit = p.setValidationVisibleOnCommit(isValidationVisibleOnCommit)
}