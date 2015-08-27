package vaadin.scala.internal

import vaadin.scala.FieldGroup._
import scala.util.Try
import vaadin.scala.FieldGroup.PreCommitEvent
import vaadin.scala.FieldGroup.PostCommitEvent
import vaadin.scala.FieldGroup

class PreCommitHandler(val action: PreCommitEvent => CommitResult) extends com.vaadin.data.fieldgroup.FieldGroup.CommitHandler with Handler[PreCommitEvent, CommitResult] {
  def preCommit(e: com.vaadin.data.fieldgroup.FieldGroup.CommitEvent): Unit = action(PreCommitEvent(wrapperFor[FieldGroup](e.getFieldBinder()).get)) fold (
    failure => throw new com.vaadin.data.fieldgroup.FieldGroup.CommitException(failure.exception),
    success => ())
  def postCommit(e: com.vaadin.data.fieldgroup.FieldGroup.CommitEvent): Unit = {}
}

class PostCommitHandler(val action: PostCommitEvent => CommitResult) extends com.vaadin.data.fieldgroup.FieldGroup.CommitHandler with Handler[PostCommitEvent, CommitResult] {
  def preCommit(e: com.vaadin.data.fieldgroup.FieldGroup.CommitEvent): Unit = {}
  def postCommit(e: com.vaadin.data.fieldgroup.FieldGroup.CommitEvent): Unit = action(PostCommitEvent(wrapperFor[FieldGroup](e.getFieldBinder()).get)) fold (
    failure => throw new com.vaadin.data.fieldgroup.FieldGroup.CommitException(failure.exception),
    success => ())
}