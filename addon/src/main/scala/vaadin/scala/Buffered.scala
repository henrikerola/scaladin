package vaadin.scala

import vaadin.scala.mixins.BufferedMixin
import vaadin.scala.mixins.BufferedValidatableMixin

package mixins {
  trait BufferedMixin extends ScaladinMixin
  trait BufferedValidatableMixin extends BufferedMixin with ValidatableMixin
}

trait Buffered extends Wrapper {

  def p: com.vaadin.data.Buffered with BufferedMixin

  def commit(): Validation = Validation.wrapToValidation(p.commit)

  def discard(): Unit = p.discard()

  def writeThrough: Boolean = p.isWriteThrough
  def writeThrough_=(writeThrough: Boolean) = p.setWriteThrough(writeThrough)

  def readThrough: Boolean = p.isReadThrough
  def readThrough_=(readThrough: Boolean) = p.setReadThrough(readThrough)

  def modified(): Boolean = p.isModified

}

trait BufferedValidatable extends Buffered with Validatable {

  def p: com.vaadin.data.BufferedValidatable with BufferedValidatableMixin

  def invalidCommitted: Boolean = p.isInvalidCommitted
  def invalidCommitted_=(invalidCommitted: Boolean): Unit = p.setInvalidCommitted(invalidCommitted)
}