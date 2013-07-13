package vaadin.scala

import vaadin.scala.mixins.BufferedMixin
import vaadin.scala.mixins.BufferedValidatableMixin

package mixins {
  trait BufferedMixin extends ScaladinMixin
  trait BufferedValidatableMixin extends BufferedMixin with ValidatableMixin
}

trait Buffered extends Wrapper {

  def p: com.vaadin.data.Buffered with BufferedMixin

  def commit(): Validator.Validation = Validator.Validation.wrapToValidation(p.commit)

  def discard(): Unit = p.discard()

  def buffered: Boolean = p.isBuffered
  def buffered_=(buffered: Boolean) = p.setBuffered(buffered)

  def modified(): Boolean = p.isModified

}

trait BufferedValidatable extends Buffered with Validatable {

  def p: com.vaadin.data.BufferedValidatable with BufferedValidatableMixin

  def invalidCommitted: Boolean = p.isInvalidCommitted
  def invalidCommitted_=(invalidCommitted: Boolean): Unit = p.setInvalidCommitted(invalidCommitted)
}