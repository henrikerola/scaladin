package vaadin.scala.converter

import java.util.Locale

import vaadin.scala.Wrapper
import vaadin.scala.converter.mixins.{ DelegatingConverterMixin, ConverterMixin }

import scala.reflect.ClassTag

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

class StringToLongConverter extends DeletagePeerConverter[java.lang.String, java.lang.Long](
  new com.vaadin.data.util.converter.StringToLongConverter with ConverterMixin[java.lang.String, java.lang.Long])

class StringToBigDecimalConverter extends DeletagePeerConverter[java.lang.String, java.math.BigDecimal](
  new com.vaadin.data.util.converter.StringToBigDecimalConverter with ConverterMixin[java.lang.String, java.math.BigDecimal])

class StringToBigIntegerConverter extends DeletagePeerConverter[java.lang.String, java.math.BigInteger](
  new com.vaadin.data.util.converter.StringToBigIntegerConverter with ConverterMixin[java.lang.String, java.math.BigInteger])

class StringToShortConverter extends DeletagePeerConverter[java.lang.String, java.lang.Short](
  new com.vaadin.data.util.converter.StringToShortConverter with ConverterMixin[java.lang.String, java.lang.Short])

class StringToByteConverter extends DeletagePeerConverter[java.lang.String, java.lang.Byte](
  new com.vaadin.data.util.converter.StringToByteConverter with ConverterMixin[java.lang.String, java.lang.Byte])

class DateToSqlDateConverter extends DeletagePeerConverter[java.util.Date, java.sql.Date](
  new com.vaadin.data.util.converter.DateToSqlDateConverter with ConverterMixin[java.util.Date, java.sql.Date])
