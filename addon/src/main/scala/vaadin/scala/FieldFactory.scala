package vaadin.scala

import java.util.Date
import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.mixins.FormFieldFactoryMixin
import vaadin.scala.mixins.TableFieldFactoryMixin
import vaadin.scala.mixins.FieldGroupFieldFactoryMixin
import scala.reflect.ClassTag

package mixins {
  trait FormFieldFactoryMixin extends ScaladinMixin
  trait TableFieldFactoryMixin extends ScaladinMixin
  trait FieldGroupFieldFactoryMixin extends TypedScaladinMixin[FieldGroupFieldFactory] { self: com.vaadin.data.fieldgroup.FieldGroupFieldFactory => }
}

case class TableFieldIngredients(container: Container, itemId: Any, propertyId: Any, uiContext: Component)

object FieldGroupFieldFactory {
  //TODO fix the types of the map 
  private val vaadinFieldToWrapperField = Map[Class[_], Class[_]](
    classOf[com.vaadin.ui.TextField] -> classOf[TextField],
    classOf[com.vaadin.ui.OptionGroup] -> classOf[OptionGroup],
    classOf[com.vaadin.ui.CheckBox] -> classOf[CheckBox],
    classOf[com.vaadin.ui.PasswordField] -> classOf[PasswordField],
    classOf[com.vaadin.ui.ComboBox] -> classOf[ComboBox],
    classOf[com.vaadin.ui.ListSelect] -> classOf[ListSelect],
    classOf[com.vaadin.ui.Table] -> classOf[Table],
    classOf[com.vaadin.ui.TreeTable] -> classOf[TreeTable],
    classOf[com.vaadin.ui.DateField] -> classOf[DateField],
    classOf[com.vaadin.ui.Slider] -> classOf[AbstractSlider])

  private val wrapperFieldToVaadinField = vaadinFieldToWrapperField map (_ swap)

  //TODO    		
  //classOf[com.vaadin.ui.CustomField[_]] -> classOf[CustomField[_]]

  def mapVaadinType[VT <: com.vaadin.ui.Field[_], ST <: Field[_]](vaadinType: Class[VT]): Class[ST] = vaadinFieldToWrapperField(vaadinType).asInstanceOf[Class[ST]]
  def mapScalaType[VT <: com.vaadin.ui.Field[_], ST <: Field[_]](scalaType: Class[ST]): Class[VT] = wrapperFieldToVaadinField(scalaType).asInstanceOf[Class[VT]]

  def apply[FT <: Field[_]](fieldFunction: (Class[_], Class[_]) => Option[FT]): FieldGroupFieldFactory = {
    new FieldGroupFieldFactory {
      override def createField[T <: Field[_]](dataType: Class[_], fieldType: Class[T]): Option[T] = fieldFunction(dataType, fieldType).map(_.asInstanceOf[T])
    }
  }
}

trait FieldGroupFieldFactory extends Wrapper {
  override val p: com.vaadin.data.fieldgroup.FieldGroupFieldFactory with FieldGroupFieldFactoryMixin = new FieldGroupFieldFactoryDelegator { wrapper = FieldGroupFieldFactory.this }

  def createField[T <: Field[_]](dataType: Class[_], fieldType: Class[T]): Option[T]

  def mapFieldToWrapper[VT <: com.vaadin.ui.Field[_], ST <: Field[_]](fieldType: Class[VT]): Class[ST] = FieldGroupFieldFactory.mapVaadinType(fieldType)
}

trait FieldGroupFieldFactoryDelegator extends com.vaadin.data.fieldgroup.FieldGroupFieldFactory with FieldGroupFieldFactoryMixin {

  def createField[T <: com.vaadin.ui.Field[_]](dataType: Class[_], fieldType: Class[T]): T = {
    val wrapperType: Class[Field[_]] = wrapper.mapFieldToWrapper(fieldType)
    val result = wrapper.createField(dataType, wrapperType).map[com.vaadin.ui.Field[_]](_.p).orNull
    result.asInstanceOf[T]
  }
}

object DefaultFieldGroupFieldFactory extends DefaultFieldGroupFieldFactory

class DefaultFieldGroupFieldFactory extends FieldGroupFieldFactory {
  override val p: com.vaadin.data.fieldgroup.FieldGroupFieldFactory with FieldGroupFieldFactoryMixin = new FieldGroupFieldFactoryDelegator { wrapper = DefaultFieldGroupFieldFactory.this }
  private val vaadinFactory = new com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory

  def createField[T <: Field[_]](dataType: Class[_], fieldType: Class[T]): Option[T] = {
    if (Boolean.getClass.isAssignableFrom(dataType)) createBooleanField(fieldType)
    else if (AbstractTextField.getClass.isAssignableFrom(fieldType))
      createAbstractTextField(fieldType.asSubclass(classOf[AbstractTextField])) map (fieldType.cast(_))
    else if (fieldType == RichTextArea.getClass) createRichTextArea() map (fieldType.cast(_))
    else createDefaultField(dataType, fieldType)
  }

  def createRichTextArea(): Option[RichTextArea] = Some(new RichTextArea { immediate = true })

  def createCompatibleSelect(fieldType: Class[_ <: AbstractSelect]): AbstractSelect = {
    val select: AbstractSelect =
      if (fieldType.isAssignableFrom(ListSelect.getClass)) new ListSelect() {
        multiSelect = false
      }
      else if (fieldType.isAssignableFrom(classOf[NativeSelect])) new NativeSelect()
      else if (fieldType.isAssignableFrom(classOf[OptionGroup])) new OptionGroup() {
        multiSelect = false
      }
      else if (fieldType.isAssignableFrom(Table.getClass)) new Table() { selectionMode = SelectionMode.Single }
      else new ComboBox

    select.immediate = true
    select.nullSelectionAllowed = false

    select
  }

  def createBooleanField[T <: Field[_]](fieldType: Class[T]): Option[T] = {
    if (fieldType.isAssignableFrom(CheckBox.getClass)) Some(new CheckBox { immediate = true }) map (_.asInstanceOf[T])
    else if (classOf[AbstractTextField].isAssignableFrom(fieldType))
      createAbstractTextField[AbstractTextField](fieldType.asInstanceOf[Class[AbstractTextField]]) map (_.asInstanceOf[T])
    else None
  }

  def createAbstractTextField[T <: AbstractTextField](fieldType: Class[T]): Option[T] = {
    val field: T = if (fieldType == AbstractTextField.getClass)
      new TextField().asInstanceOf[T]
    else fieldType.newInstance()

    field.immediate = true
    Some(field)
  }

  def createDefaultField[T <: Field[_]](dataType: Class[_],
                                        fieldType: Class[T]): Option[T] = {
    if (fieldType.isAssignableFrom(TextField.getClass))
      createAbstractTextField[TextField](classOf[TextField]) map (fieldType.cast(_))

    else None
  }
}

object TableFieldFactory {
  def apply(tableFieldFunction: TableFieldIngredients => Option[Field[_]]): TableFieldFactory = new TableFieldFactory {
    def createField(ingredients: TableFieldIngredients): Option[Field[_]] = tableFieldFunction(ingredients)
  }
}

trait TableFieldFactory extends Wrapper {
  override val p: com.vaadin.ui.TableFieldFactory with TableFieldFactoryMixin = new TableFieldFactoryDelegator { wrapper = TableFieldFactory.this }

  def createField(ingredients: TableFieldIngredients): Option[Field[_]]
}

trait TableFieldFactoryDelegator extends com.vaadin.ui.TableFieldFactory with TableFieldFactoryMixin {
  def createField(container: com.vaadin.data.Container, itemId: Any, propertyId: Any,
                  uiContext: com.vaadin.ui.Component): com.vaadin.ui.Field[_] = {
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

object DefaultFieldFactory extends TableFieldFactory {

  override val p: com.vaadin.ui.TableFieldFactory with TableFieldFactoryMixin = new TableFieldFactoryDelegator {
    val tableFieldFactoryDelegate = DefaultFieldFactory.this
    val formFieldFactoryDelegate = DefaultFieldFactory.this
    wrapper = DefaultFieldFactory.this
  }

  val Item = classOf[Item]
  val Date = classOf[Date]
  val Bool = classOf[Boolean]

  def createField(ingredients: TableFieldIngredients): Option[Field[_]] =
    ingredients.container.getPropertyOption(ingredients.itemId, ingredients.propertyId) match {
      case Some(property) => {
        val propertyType: Class[_] = property.getType
        val field: Field[_] = createFieldByPropertyType(propertyType)
        field.caption = createCaptionByPropertyId(ingredients.propertyId)
        Option(field)
      }

      case None => None
    }

  def createFieldByPropertyType(propertyType: Class[_]): Field[_] = propertyType match {
    case null => null
    case Date => new DateField { resolution = DateField.Resolution.Day }
    case Bool => new CheckBox
    case _ => new TextField
  }

  def createCaptionByPropertyId(propertyId: Any): String = com.vaadin.ui.DefaultFieldFactory.createCaptionByPropertyId(propertyId)
}