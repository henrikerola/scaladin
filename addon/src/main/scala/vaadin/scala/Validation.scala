package vaadin.scala

import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.mixins.ValidatorMixin
import vaadin.scala.mixins.ValidatableMixin
import scala.collection.mutable

package mixins {
  class ValidatorMixin extends ScaladinMixin
  class ValidatableMixin extends ScaladinMixin
}

abstract sealed class ValidationResult
case object Valid extends ValidationResult
case class Invalid(reason: String = "") extends ValidationResult

object Validator {
  def apply(validatorFunction: Any => ValidationResult): Validator = {
    new Validator() { def validate(value: Any) = validatorFunction(value) }
  }
}

class Validators(val p: com.vaadin.data.Validatable with ValidatableMixin) extends mutable.Set[Any => ValidationResult] {

  import scala.collection.JavaConverters._

  def contains(key: Any => ValidationResult) = iterator.contains(key)

  def iterator(): Iterator[Validator] =
    p.getValidators.asScala.filter(_.isInstanceOf[ValidatorMixin]).flatMap(_.asInstanceOf[ValidatorMixin].wrapper).map(_.asInstanceOf[Validator]).iterator

  def +=(elem: Any => ValidationResult) = { p.addValidator(Validator(elem).p); this }
  def +=(elem: Validator) = { p.addValidator(elem.p); this }
  def -=(elem: Validatable => Unit) = {
     iterator.foreach { e =>
      if (e == elem) {
        p.removeValidator(e.p)
      }
    }
    this
  }
}

trait Validatable extends Wrapper {
  def p: com.vaadin.data.Validatable with ValidatableMixin

  lazy val validators = new Validators(p)
}

class ValidatorDelegator extends com.vaadin.data.Validator with ValidatorMixin {
  def isValid(value: Any): Boolean = internalValidate(value) == Valid

  def validate(value: Any): Unit = internalValidate(value) match {
    case Valid =>
    case Invalid(message) => throw new com.vaadin.data.Validator.InvalidValueException(message)
  }

  protected def internalValidate(value: Any): ValidationResult = wrapper.get.asInstanceOf[Validator].validate(value)
}

trait Validator extends Wrapper with Function1[Any, ValidationResult] {
  override val p: com.vaadin.data.Validator = new ValidatorDelegator { wrapper = Validator.this }
  def validate(value: Any): ValidationResult
  def apply(value: Any): ValidationResult = validate(value)
}