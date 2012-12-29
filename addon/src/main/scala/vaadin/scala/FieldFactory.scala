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
  def apply(formFieldFunction: FormFieldIngredients => Option[Field]): FormFieldFactory = new FormFieldFactory {
    def createField(ingredients: FormFieldIngredients): Option[Field] = formFieldFunction(ingredients)
  }
}

trait FormFieldFactory extends Wrapper {
  override val p: com.vaadin.ui.FormFieldFactory with FormFieldFactoryMixin = new FormFieldFactoryDelegator { wrapper = FormFieldFactory.this }

  def createField(ingredients: FormFieldIngredients): Option[Field]
}

trait FormFieldFactoryDelegator extends com.vaadin.ui.FormFieldFactory with FormFieldFactoryMixin {

  def createField(container: com.vaadin.data.Item, propertyId: Any, uiContext: com.vaadin.ui.Component): com.vaadin.ui.Field = {
    uiContext match {

      case mixin: ScaladinMixin if mixin.wrapper.isInstanceOf[Form] => {
        val contextWrapper: Form = mixin.wrapper.asInstanceOf[Form]
        val ingredients = FormFieldIngredients(contextWrapper.item.get, propertyId, contextWrapper)

        wrapper.asInstanceOf[FormFieldFactory].createField(ingredients) match {
          case Some(field) => field.p
          case _ => null
        }
      }

      case _ => null
    }
  }
}

object TableFieldFactory {
  def apply(tableFieldFunction: TableFieldIngredients => Option[Field]): TableFieldFactory = new TableFieldFactory {
    def createField(ingredients: TableFieldIngredients): Option[Field] = tableFieldFunction(ingredients)
  }
}

trait TableFieldFactory extends Wrapper {
  override val p: com.vaadin.ui.TableFieldFactory with TableFieldFactoryMixin = new TableFieldFactoryDelegator { wrapper = TableFieldFactory.this }

  def createField(ingredients: TableFieldIngredients): Option[Field]
}

trait TableFieldFactoryDelegator extends com.vaadin.ui.TableFieldFactory with TableFieldFactoryMixin {
  def createField(container: com.vaadin.data.Container, itemId: Any, propertyId: Any,
    uiContext: com.vaadin.ui.Component): com.vaadin.ui.Field = {
    uiContext match {

      case mixin: ScaladinMixin if mixin.wrapper.isInstanceOf[Table] => {
        val contextWrapper: Table = mixin.wrapper.asInstanceOf[Table]
        val newField = wrapper.asInstanceOf[TableFieldFactory].createField(TableFieldIngredients(contextWrapper.container.get, itemId, propertyId, contextWrapper))
        newField match {
          case Some(field) => field.p
          case _ => null
        }
      }

      case _ => null
    }
  }
}

object DefaultFieldFactory extends FormFieldFactory with TableFieldFactory {

  override val p: com.vaadin.ui.FormFieldFactory with FormFieldFactoryMixin with com.vaadin.ui.TableFieldFactory with TableFieldFactoryMixin = new FormFieldFactoryDelegator with TableFieldFactoryDelegator {
    val tableFieldFactoryDelegate = DefaultFieldFactory.this
    val formFieldFactoryDelegate = DefaultFieldFactory.this
    wrapper = DefaultFieldFactory.this
  }

  val Item = classOf[Item]
  val Date = classOf[Date]
  val Bool = classOf[Boolean]

  def createField(ingredients: FormFieldIngredients): Option[Field] = ingredients.item.property(ingredients.propertyId) match {
    case Some(property) => {
      val propertyType: Class[_] = property.getType
      val field: Field = createFieldByPropertyType(propertyType);
      field.caption = createCaptionByPropertyId(ingredients.propertyId)
      Option(field)
    }

    case None => None
  }

  def createField(ingredients: TableFieldIngredients): Option[Field] = ingredients.container.property(ingredients.itemId, ingredients.propertyId) match {
    case Some(property) => {
      val propertyType: Class[_] = property.getType;
      val field: Field = createFieldByPropertyType(propertyType);
      field.caption = createCaptionByPropertyId(ingredients.propertyId)
      Option(field)
    }

    case None => None
  }

  def createFieldByPropertyType(propertyType: Class[_]): Field = propertyType match {
    case null => null
    case Item => new Form
    case Date => new DateField { resolution = DateField.Resolution.Day }
    case Bool => new CheckBox
    case _ => new TextField
  }

  def createCaptionByPropertyId(propertyId: Any): String = com.vaadin.ui.DefaultFieldFactory.createCaptionByPropertyId(propertyId)
}