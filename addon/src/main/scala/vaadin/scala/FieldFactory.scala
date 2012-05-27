package vaadin.scala

import java.util.Date
import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.mixins.FormFieldFactoryMixin
import vaadin.scala.mixins.TableFieldFactoryMixin

package mixins {
  trait FormFieldFactoryMixin extends ScaladinMixin
  trait TableFieldFactoryMixin extends ScaladinMixin
}

trait FormFieldFactory extends Wrapper {
  def p: com.vaadin.ui.FormFieldFactory with FormFieldFactoryMixin = new FormFieldFactoryDelegator { val formFieldFactoryDelegate = FormFieldFactory.this }

  def createField(item: Item, propertyId: Any, uiContext: Component): Field
}

trait FormFieldFactoryDelegator extends com.vaadin.ui.FormFieldFactory with FormFieldFactoryMixin {
  def formFieldFactoryDelegate: FormFieldFactory

  def createField(container: com.vaadin.data.Item, propertyId: Any,
    uiContext: com.vaadin.ui.Component): com.vaadin.ui.Field = {
    uiContext match {

      case mixin: ScaladinMixin if mixin.wrapper.get.isInstanceOf[Form] => {
        val contextWrapper: Form = uiContext.asInstanceOf[Form]
        formFieldFactoryDelegate.createField(contextWrapper.item.get, propertyId, contextWrapper).p
      }

      case _ => null
    }
  }
}
trait TableFieldFactory extends Wrapper {
  def p: com.vaadin.ui.TableFieldFactory = new TableFieldFactoryDelegator { val tableFieldFactoryDelegate = TableFieldFactory.this }

  def createField(container: Container, itemId: Any, propertyId: Any,
    uiContext: Component): Field
}

trait TableFieldFactoryDelegator extends com.vaadin.ui.TableFieldFactory with TableFieldFactoryMixin {
  def tableFieldFactoryDelegate: TableFieldFactory

  def createField(container: com.vaadin.data.Container, itemId: Any, propertyId: Any,
    uiContext: com.vaadin.ui.Component): com.vaadin.ui.Field = {
    uiContext match {

      case mixin: ScaladinMixin if mixin.wrapper.get.isInstanceOf[Table] => {
        val contextWrapper: Table = uiContext.asInstanceOf[Table]
        tableFieldFactoryDelegate.createField(contextWrapper.container.get, itemId, propertyId, contextWrapper).p
      }

      case _ => null
    }
  }
}

object DefaultFieldFactory extends FormFieldFactory with TableFieldFactory {

  override def p: com.vaadin.ui.FormFieldFactory with FormFieldFactoryMixin with com.vaadin.ui.TableFieldFactory = new FormFieldFactoryDelegator with TableFieldFactoryDelegator {
    val tableFieldFactoryDelegate = DefaultFieldFactory.this
    val formFieldFactoryDelegate = DefaultFieldFactory.this
  }

  val Item = classOf[Item]
  val Date = classOf[Date]
  val Bool = classOf[Boolean]

  def createField(item: Item, propertyId: Any, uiContext: Component): Field = item.property(propertyId) match {
    case Some(property) => {
      val propertyType: Class[_] = property.getType
      val field: Field = createFieldByPropertyType(propertyType);
      field.caption = com.vaadin.ui.DefaultFieldFactory.createCaptionByPropertyId(propertyId)
      field
    }

    case None => null
  }

  def createField(container: Container, itemId: Any, propertyId: Any,
    uiContext: Component): Field = container.property(itemId, propertyId) match {
    case Some(property) => {
      val propertyType: Class[_] = property.getType;
      val field: Field = createFieldByPropertyType(propertyType);
      field.caption = com.vaadin.ui.DefaultFieldFactory.createCaptionByPropertyId(propertyId)
      field
    }

    case None => null
  }

  def createFieldByPropertyType(propertyType: Class[_]): Field = propertyType match {
    case null => null
    case Item => new Form
    case Date => new DateField { resolution = DateField.Resolution.Day }
    case Bool => new CheckBox
    case _ => new TextField
  }
}