package vaadin.scala.converter

import vaadin.scala.converter.mixins.ConverterMixin

class DateToLongConverter extends DeletagePeerConverter[java.util.Date, java.lang.Long](
  new com.vaadin.data.util.converter.DateToLongConverter with ConverterMixin[java.util.Date, java.lang.Long])

class StringToBooleanConverter extends DeletagePeerConverter[java.lang.String, java.lang.Boolean](
  new com.vaadin.data.util.converter.StringToBooleanConverter with ConverterMixin[java.lang.String, java.lang.Boolean])

class StringToDateConverter extends DeletagePeerConverter[java.lang.String, java.util.Date](
  new com.vaadin.data.util.converter.StringToDateConverter with ConverterMixin[java.lang.String, java.util.Date])

class StringToDoubleConverter extends DeletagePeerConverter[java.lang.String, java.lang.Double](
  new com.vaadin.data.util.converter.StringToDoubleConverter with ConverterMixin[java.lang.String, java.lang.Double])

class StringToFloatConverter extends DeletagePeerConverter[java.lang.String, java.lang.Float](
  new com.vaadin.data.util.converter.StringToFloatConverter with ConverterMixin[java.lang.String, java.lang.Float])

class StringToIntegerConverter extends DeletagePeerConverter[java.lang.String, java.lang.Integer](
  new com.vaadin.data.util.converter.StringToIntegerConverter with ConverterMixin[java.lang.String, java.lang.Integer])

class StringToNumberConverter extends DeletagePeerConverter[java.lang.String, java.lang.Number](
  new com.vaadin.data.util.converter.StringToNumberConverter with ConverterMixin[java.lang.String, java.lang.Number])