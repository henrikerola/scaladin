package vaadin.scala

import vaadin.scala.mixins.{ ValidatorMixin, ValidatableMixin }
import scala.collection.mutable
import scala.collection.immutable.List
import scala.util.control.Exception.catching
import scala.util.{ Failure, Success }
import com.vaadin.data.Validator.InvalidValueException
import Validator._

package mixins {
  trait ValidatorMixin extends ScaladinInterfaceMixin {
    self: com.vaadin.data.Validator =>

    def validate(value: Any) {
      wrapper.asInstanceOf[Validator].validate(Option(value)) match {
        case Valid =>
        case Invalid(reasons) => {
          if (reasons.isEmpty)
            throw new InvalidValueException("")
          else
            throw new InvalidValueException(reasons.head, reasons.tail.map(new InvalidValueException(_)): _*)
        }
      }
    }
  }
  trait ValidatableMixin extends ScaladinMixin
}

object Validator {

  object Validation {
    def exceptionToInvalid(e: InvalidValueException): Validation =
      Invalid(e.getMessage :: e.getCauses().toList.map(_.getMessage))

    def wrapToValidation(f: () => Unit): Validation = try {
      f()
      Valid //no exception -> valid
    } catch {
      case e: com.vaadin.data.Validator.InvalidValueException => exceptionToInvalid(e)
    }
  }

  sealed abstract class Validation(val isValid: Boolean, val errorMessages: List[String] = List.empty) {
    def ::(other: Validation): Validation = (this, other) match {
      case (Valid, Valid) => Valid
      case (Invalid(reasons), Valid) => Invalid(reasons)
      case (Valid, Invalid(reasons)) => Invalid(reasons)
      case (Invalid(reasons1), Invalid(reasons2)) => Invalid(reasons1 ::: reasons2)
    }
  }

  case object Valid extends Validation(true)
  case class Invalid(reasons: List[String]) extends Validation(false, reasons)

  def apply(validatorFunction: Option[Any] => Validation): Validator = {
    new Validator() { def validate(value: Option[Any]) = validatorFunction(value) }
  }

  def apply(vaadinValidator: com.vaadin.data.Validator): Validator = {
    new Validator {
      def validate(value: Option[Any]): Validation = {
        catching(classOf[InvalidValueException]) withTry (vaadinValidator.validate(value.orNull)) match {
          case Success(value) => Valid
          case Failure(exception) => Invalid(List(exception.getMessage))
        }
      }
    }
  }
}

trait Validator extends InterfaceWrapper {
  val pValidator = new com.vaadin.data.Validator with ValidatorMixin
  pValidator.wrapper = this

  def validate(value: Option[Any]): Validation
}

class ValidatorsSet(p: com.vaadin.data.Validatable with ValidatableMixin)
    extends mutable.Set[Validator] with Serializable {

  override def contains(key: Validator) = iterator.contains(key)

  import scala.collection.JavaConverters._
  override def iterator: Iterator[Validator] =
    p.getValidators.asScala.filter(_.isInstanceOf[ValidatorMixin]).map(_.asInstanceOf[ValidatorMixin].wrapper).map(_.asInstanceOf[Validator]).iterator

  def +=(elem: Option[Any] => Validation) = { p.addValidator(Validator(elem).pValidator); this }
  override def +=(elem: Validator) = { p.addValidator(elem.pValidator); this }
  override def -=(elem: Validator) = {
    iterator.foreach { e =>
      if (e == elem) {
        p.removeValidator(e.pValidator)
      }
    }
    this
  }
}

trait Validatable extends Wrapper {
  override def p: com.vaadin.data.Validatable with ValidatableMixin

  lazy val validators: ValidatorsSet = new ValidatorsSet(p)

  def validate: Validation = Validation.wrapToValidation(p.validate)

  def valid: Boolean = p.isValid
}
