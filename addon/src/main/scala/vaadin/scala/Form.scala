package vaadin.scala

import vaadin.scala.mixins.FormMixin

package mixins {
  trait FormMixin extends AbstractFieldMixin
}

class Form(override val p: com.vaadin.ui.Form with FormMixin = new com.vaadin.ui.Form with FormMixin) extends AbstractField(p) with ItemViewer with Buffered {

  //override default implementation with a wrapper
  formFieldFactory = DefaultFieldFactory

  def formFieldFactory: Option[FormFieldFactory] = wrapperFor[FormFieldFactory](p.getFormFieldFactory)
  def formFieldFactory_=(factory: FormFieldFactory) = p.setFormFieldFactory(factory.p)
  def formFieldFactory_=(factoryFunction: FormFieldIngredients => Option[Field]) = p.setFormFieldFactory(FormFieldFactory(factoryFunction).p)
  def formFieldFactory_=(factory: Option[FormFieldFactory]) = factory match {
    case Some(factory) => p.setFormFieldFactory(factory.p)
    case None => p.setFormFieldFactory(null)
  }

  def addField(propertyId: Any, field: Field): Unit = p.addField(propertyId, field.p)

  def field(propertyId: Any): Option[Field] = wrapperFor[Field](p.getField(propertyId))

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
}