package vaadin.scala

import java.util.Date
import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.mixins.FormFieldFactoryMixin
import vaadin.scala.mixins.TableFieldFactoryMixin

package mixins {
  trait FormFieldFactoryMixin extends ScaladinMixin
  trait TableFieldFactoryMixin extends ScaladinMixin
}

case class FormFieldIngredients(item: Item, propertyId: Any, uiContext: Component)
case class TableFieldIngredients(container: Container, itemId: Any, propertyId: Any, uiContext: Component)

object FormFieldFactory {
  def apply(formFieldFunction: (FormFieldIngredients) => Field): FormFieldFactory = new FormFieldFactory {
    def createField(ingredients: FormFieldIngredients): Field = formFieldFunction(ingredients)
  }
}

trait FormFieldFactory extends Wrapper {
  override val p: com.vaadin.ui.FormFieldFactory with FormFieldFactoryMixin = new FormFieldFactoryDelegator { wrapper = FormFieldFactory.this }

  def createField(ingredients: FormFieldIngredients): Field
}

trait FormFieldFactoryDelegator extends com.vaadin.ui.FormFieldFactory with FormFieldFactoryMixin {

  def createField(container: com.vaadin.data.Item, propertyId: Any,
    uiContext: com.vaadin.ui.Component): com.vaadin.ui.Field = {
    uiContext match {

      case mixin: ScaladinMixin if mixin.wrapper.isInstanceOf[Form] => {
        val contextWrapper: Form = uiContext.asInstanceOf[Form]
        val ingredients = FormFieldIngredients(contextWrapper.item.get, propertyId, contextWrapper)
        wrapper.asInstanceOf[FormFieldFactory].createField(ingredients).p
      }

      case _ => null
    }
  }
}

object TableFieldFactory {
  def apply(tableFieldFunction: (TableFieldIngredients) => Field): TableFieldFactory = new TableFieldFactory {
    def createField(ingredients: TableFieldIngredients): Field = tableFieldFunction(ingredients)
  }
}

trait TableFieldFactory extends Wrapper {
  override val p: com.vaadin.ui.TableFieldFactory = new TableFieldFactoryDelegator { wrapper = TableFieldFactory.this }

  def createField(ingredients: TableFieldIngredients): Field
}

trait TableFieldFactoryDelegator extends com.vaadin.ui.TableFieldFactory with TableFieldFactoryMixin {
  def createField(container: com.vaadin.data.Container, itemId: Any, propertyId: Any,
    uiContext: com.vaadin.ui.Component): com.vaadin.ui.Field = {
    uiContext match {

      case mixin: ScaladinMixin if mixin.wrapper.isInstanceOf[Table] => {
        val contextWrapper: Table = uiContext.asInstanceOf[Table]
        wrapper.asInstanceOf[TableFieldFactory].createField(TableFieldIngredients(contextWrapper.container.get, itemId, propertyId, contextWrapper)).p
      }

      case _ => null
    }
  }
}

object DefaultFieldFactory extends FormFieldFactory with TableFieldFactory {

  override val p: com.vaadin.ui.FormFieldFactory with FormFieldFactoryMixin with com.vaadin.ui.TableFieldFactory = new FormFieldFactoryDelegator with TableFieldFactoryDelegator {
    val tableFieldFactoryDelegate = DefaultFieldFactory.this
    val formFieldFactoryDelegate = DefaultFieldFactory.this
    wrapper = DefaultFieldFactory.this
  }

  val Item = classOf[Item]
  val Date = classOf[Date]
  val Bool = classOf[Boolean]

  def createField(ingredients: FormFieldIngredients): Field = ingredients.item.property(ingredients.propertyId) match {
    case Some(property) => {
      val propertyType: Class[_] = property.getType
      val field: Field = createFieldByPropertyType(propertyType);
      field.caption = com.vaadin.ui.DefaultFieldFactory.createCaptionByPropertyId(ingredients.propertyId)
      field
    }

    case None => null
  }

  def createField(ingredients: TableFieldIngredients): Field = ingredients.container.property(ingredients.itemId, ingredients.propertyId) match {
    case Some(property) => {
      val propertyType: Class[_] = property.getType;
      val field: Field = createFieldByPropertyType(propertyType);
      field.caption = com.vaadin.ui.DefaultFieldFactory.createCaptionByPropertyId(ingredients.propertyId)
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