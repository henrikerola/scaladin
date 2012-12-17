package vaadin.scala.internal

import vaadin.scala.FieldGroup
import scala.util.Try
import vaadin.scala.FieldGroup.PreCommitEvent
import vaadin.scala.FieldGroup.PostCommitEvent

class PreCommitHandler(val action: FieldGroup.PreCommitEvent => Try[Unit]) extends com.vaadin.data.fieldgroup.FieldGroup.CommitHandler with Handler[PreCommitEvent] {
  def preCommit(e: com.vaadin.data.fieldgroup.FieldGroup.CommitEvent): Unit = action(FieldGroup.PreCommitEvent(wrapperFor[FieldGroup](e.getFieldBinder()).get)) get
  def postCommit(e: com.vaadin.data.fieldgroup.FieldGroup.CommitEvent): Unit = {}
}

class PostCommitHandler(val action: FieldGroup.PostCommitEvent => Try[Unit]) extends com.vaadin.data.fieldgroup.FieldGroup.CommitHandler with Handler[PostCommitEvent] {
  def preCommit(e: com.vaadin.data.fieldgroup.FieldGroup.CommitEvent): Unit = {}
  def postCommit(e: com.vaadin.data.fieldgroup.FieldGroup.CommitEvent): Unit = action(FieldGroup.PostCommitEvent(wrapperFor[FieldGroup](e.getFieldBinder()).get)) get
}
