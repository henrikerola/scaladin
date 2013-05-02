package vaadin.scala.event

import vaadin.scala.Property

case class ValueChangeEvent(property: Property[_]) extends Event
