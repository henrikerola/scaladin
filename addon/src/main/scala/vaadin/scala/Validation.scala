package vaadin.scala

import vaadin.scala.mixins.ScaladinMixin
import vaadin.scala.mixins.ValidatorMixin
import vaadin.scala.mixins.ValidatableMixin
import scala.collection.mutable

package mixins {
  trait ValidatorMixin extends ScaladinMixin
  trait ValidatableMixin extends ScaladinMixin
}

abstract sealed class ValidationResult
case object Valid extends ValidationResult
case class Invalid(reason: String = "") extends ValidationResult

object Validator {
  def apply(validatorFunction: Option[Any] => ValidationResult): Validator = {
    new Validator() { def validate(value: Option[Any]) = validatorFunction(value) }
  }
}

class Validators(p: com.vaadin.data.Validatable with ValidatableMixin) extends mutable.Set[Validator] {

  def contains(key: Validator) = iterator.contains(key)

  import scala.collection.JavaConverters._
  def iterator(): Iterator[Validator] =
    p.getValidators.asScala.filter(_.isInstanceOf[ValidatorMixin]).map(_.asInstanceOf[ValidatorMixin].wrapper).map(_.asInstanceOf[Validator]).iterator

  def +=(elem: Any => ValidationResult) = { p.addValidator(Validator(elem).p); this }
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

  def validate: ValidationResult = try {
    p.validate
    Valid //no exception -> valid
  } catch {
    case e: com.vaadin.data.Validator.InvalidValueException => Invalid(e.getMessage)
  }
}

class ValidatorDelegator extends com.vaadin.data.Validator with ValidatorMixin {
  def isValid(value: Any): Boolean = internalValidate(value) == Valid

  def validate(value: Any): Unit = internalValidate(value) match {
    case Valid =>
    case Invalid(message) => throw new com.vaadin.data.Validator.InvalidValueException(message)
  }

  protected def internalValidate(value: Any): ValidationResult = wrapper.asInstanceOf[Validator].validate(Option(value))
}

trait Validator extends Wrapper {
  override val p: com.vaadin.data.Validator = new ValidatorDelegator { wrapper = Validator.this }
  def validate(value: Option[Any]): ValidationResult
}