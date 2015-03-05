package vaadin.scala.tests

import vaadin.scala.converter._
import vaadin.scala.server.Resource

/**
 *
 * @author Henri Kerola / Vaadin
 */
class DefaultConverterFactoryTests extends ScaladinTestSuite {

  test("String, Double") {
    assert(DefaultConverterFactory.createConverter[String, Double].get.isInstanceOf[StringToDoubleConverter])
  }

  test("Double, String") {
    assert(DefaultConverterFactory.createConverter[Double, String].get.isInstanceOf[StringToDoubleConverter])
  }

  test("String, java.lang.Double") {
    assert(DefaultConverterFactory.createConverter[String, java.lang.Double].get.isInstanceOf[StringToDoubleConverter])
  }

  test("java.lang.Double, String") {
    assert(DefaultConverterFactory.createConverter[java.lang.Double, String].get.isInstanceOf[StringToDoubleConverter])
  }

  //

  test("String, Float") {
    assert(DefaultConverterFactory.createConverter[String, Float].get.isInstanceOf[StringToFloatConverter])
  }

  test("Float, String") {
    assert(DefaultConverterFactory.createConverter[Float, String].get.isInstanceOf[StringToFloatConverter])
  }

  test("String, java.lang.Float") {
    assert(DefaultConverterFactory.createConverter[String, java.lang.Float].get.isInstanceOf[StringToFloatConverter])
  }

  test("java.lang.Float, String") {
    assert(DefaultConverterFactory.createConverter[java.lang.Float, String].get.isInstanceOf[StringToFloatConverter])
  }

  //

  test("String, Integer") {
    assert(DefaultConverterFactory.createConverter[String, Integer].get.isInstanceOf[StringToIntegerConverter])
  }

  test("Integer, String") {
    assert(DefaultConverterFactory.createConverter[Integer, String].get.isInstanceOf[StringToIntegerConverter])
  }

  test("String, java.lang.Integer") {
    assert(DefaultConverterFactory.createConverter[String, java.lang.Integer].get.isInstanceOf[StringToIntegerConverter])
  }

  test("java.lang.Integer, String") {
    assert(DefaultConverterFactory.createConverter[java.lang.Integer, String].get.isInstanceOf[StringToIntegerConverter])
  }

  //

  test("String, Long") {
    assert(DefaultConverterFactory.createConverter[String, Long].get.isInstanceOf[StringToLongConverter])
  }

  test("Long, String") {
    assert(DefaultConverterFactory.createConverter[Long, String].get.isInstanceOf[StringToLongConverter])
  }

  test("String, java.lang.Long") {
    assert(DefaultConverterFactory.createConverter[String, java.lang.Long].get.isInstanceOf[StringToLongConverter])
  }

  test("java.lang.Long, String") {
    assert(DefaultConverterFactory.createConverter[java.lang.Long, String].get.isInstanceOf[StringToLongConverter])
  }

  //

  test("String, BigDecimal") {
    assert(DefaultConverterFactory.createConverter[String, BigDecimal].get.isInstanceOf[StringToBigDecimalConverter])
  }

  test("BigDecimal, String") {
    assert(DefaultConverterFactory.createConverter[BigDecimal, String].get.isInstanceOf[StringToBigDecimalConverter])
  }

  test("String, java.lang.BigDecimal") {
    assert(DefaultConverterFactory.createConverter[String, java.math.BigDecimal].get.isInstanceOf[StringToBigDecimalConverter])
  }

  test("java.lang.BigDecimal, String") {
    assert(DefaultConverterFactory.createConverter[java.math.BigDecimal, String].get.isInstanceOf[StringToBigDecimalConverter])
  }

  //

  test("String, Boolean") {
    assert(DefaultConverterFactory.createConverter[String, Boolean].get.isInstanceOf[StringToBooleanConverter])
  }

  test("Boolean, String") {
    assert(DefaultConverterFactory.createConverter[Boolean, String].get.isInstanceOf[StringToBooleanConverter])
  }

  test("String, java.lang.Boolean") {
    assert(DefaultConverterFactory.createConverter[String, java.lang.Boolean].get.isInstanceOf[StringToBooleanConverter])
  }

  test("java.lang.Boolean, String") {
    assert(DefaultConverterFactory.createConverter[java.lang.Boolean, String].get.isInstanceOf[StringToBooleanConverter])
  }

  //

  test("String, Date") {
    assert(DefaultConverterFactory.createConverter[String, java.util.Date].get.isInstanceOf[StringToDateConverter])
  }

  test("Date, String") {
    assert(DefaultConverterFactory.createConverter[java.util.Date, String].get.isInstanceOf[StringToDateConverter])
  }

  //

  test("String, BigInt") {
    assert(DefaultConverterFactory.createConverter[String, BigInt].get.isInstanceOf[StringToBigIntegerConverter])
  }

  test("BigInt, String") {
    assert(DefaultConverterFactory.createConverter[BigInt, String].get.isInstanceOf[StringToBigIntegerConverter])
  }

  test("String, java.math.BigInteger") {
    assert(DefaultConverterFactory.createConverter[String, java.math.BigInteger].get.isInstanceOf[StringToBigIntegerConverter])
  }

  test("java.math.BigInteger, String") {
    assert(DefaultConverterFactory.createConverter[java.math.BigInteger, String].get.isInstanceOf[StringToBigIntegerConverter])
  }

  //

  test("String, Short") {
    assert(DefaultConverterFactory.createConverter[String, Short].get.isInstanceOf[StringToShortConverter])
  }

  test("Short, String") {
    assert(DefaultConverterFactory.createConverter[Short, String].get.isInstanceOf[StringToShortConverter])
  }

  test("String, java.lang.Short") {
    assert(DefaultConverterFactory.createConverter[String, java.lang.Short].get.isInstanceOf[StringToShortConverter])
  }

  test("java.lang.Short, String") {
    assert(DefaultConverterFactory.createConverter[java.lang.Short, String].get.isInstanceOf[StringToShortConverter])
  }

  //

  test("String, Byte") {
    assert(DefaultConverterFactory.createConverter[String, Byte].get.isInstanceOf[StringToByteConverter])
  }

  test("Byte, String") {
    assert(DefaultConverterFactory.createConverter[Byte, String].get.isInstanceOf[StringToByteConverter])
  }

  test("String, java.lang.Byte") {
    assert(DefaultConverterFactory.createConverter[String, java.lang.Byte].get.isInstanceOf[StringToByteConverter])
  }

  test("java.lang.Byte, String") {
    assert(DefaultConverterFactory.createConverter[java.lang.Byte, String].get.isInstanceOf[StringToByteConverter])
  }

  //

  test("Date, Long") {
    assert(DefaultConverterFactory.createConverter[java.util.Date, Long].get.isInstanceOf[DateToLongConverter])
  }

  test("Long, Date") {
    assert(DefaultConverterFactory.createConverter[Long, java.util.Date].get.isInstanceOf[DateToLongConverter])
  }

  test("Date, java.lang.Long") {
    assert(DefaultConverterFactory.createConverter[java.util.Date, java.lang.Long].get.isInstanceOf[DateToLongConverter])
  }

  test("java.lang.Long, Date") {
    assert(DefaultConverterFactory.createConverter[java.lang.Long, java.util.Date].get.isInstanceOf[DateToLongConverter])
  }

  //

  test("Resource, com.vaadin.server.Resource") {
    assert(DefaultConverterFactory.createConverter[Resource, com.vaadin.server.Resource].get.isInstanceOf[ResourceConverter])
  }

  test("com.vaadin.server.Resource, Resource") {
    assert(DefaultConverterFactory.createConverter[com.vaadin.server.Resource, Resource].get.isInstanceOf[ResourceConverter])
  }
}
