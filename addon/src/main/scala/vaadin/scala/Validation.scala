package vaadin.scala

import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.mixins.ValidatorMixin
import vaadin.scala.mixins.ValidatableMixin
import scala.collection.mutable
import scala.collection.immutable.List

package mixins {
  trait ValidatorMixin extends ScaladinMixin
  trait ValidatableMixin extends ScaladinMixin
}

object Validation {
  def exceptionToInvalid(e: com.vaadin.data.Validator.InvalidValueException): Validation = Invalid(e.getMessage :: e.getCauses().toList.map(_.getMessage))
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

object Validator {
  def apply(validatorFunction: Option[Any] => Validation): Validator = {
    new Validator() { def validate(value: Option[Any]) = validatorFunction(value) }
  }
}

class Validators(p: com.vaadin.data.Validatable with ValidatableMixin)
    extends mutable.Set[Validator] with Serializable {

  def contains(key: Validator) = iterator.contains(key)

  import scala.collection.JavaConverters._
  def iterator(): Iterator[Validator] =
    p.getValidators.asScala.filter(_.isInstanceOf[ValidatorMixin]).map(_.asInstanceOf[ValidatorMixin].wrapper).map(_.asInstanceOf[Validator]).iterator

  def +=(elem: Option[Any] => Validation) = { p.addValidator(Validator(elem).p); this }
  def +=(elem: Validator) = { p.addValidator(elem.p); this }
  def -=(elem: Validator) = {
    iterator.foreach { e =>
      if (e == elem) {
        p.removeValidator(e.p)
      }
    }
    this
  }
}

trait Validatable extends Wrapper {
  override def p: com.vaadin.data.Validatable with ValidatableMixin

  lazy val validators: Validators = new Validators(p)

  def validate: Validation = Validation.wrapToValidation(p.validate)
}

class ValidatorDelegator extends com.vaadin.data.Validator with ValidatorMixin {
  def isValid(value: Any): Boolean = internalValidate(value) == Valid

  def validate(value: Any): Unit = internalValidate(value) match {
    case Valid =>
    case Invalid(reasons) => {
      if (reasons.isEmpty)
        throw new com.vaadin.data.Validator.InvalidValueException("")
      else
        throw new com.vaadin.data.Validator.InvalidValueException(reasons.head, reasons.tail.map(new com.vaadin.data.Validator.InvalidValueException(_)).toArray)
    }
  }

  protected def internalValidate(value: Any): Validation = wrapper.asInstanceOf[Validator].validate(Option(value))
}

trait Validator extends Wrapper {
  override val p: com.vaadin.data.Validator = new ValidatorDelegator { wrapper = Validator.this }
  def validate(value: Option[Any]): Validation
}