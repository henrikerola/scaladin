package vaadin.scala

import event.{ FocusNotifier, BlurNotifier, ComponentEvent }
import vaadin.scala.mixins.DateFieldMixin
import internal.WrapperUtil
import java.util.TimeZone

package mixins {
  trait DateFieldMixin extends AbstractFieldMixin[java.util.Date] { self: com.vaadin.ui.DateField =>
    override def handleUnparsableDateString(dateString: String): java.util.Date = {
      // FIXME: asInstanceOf
      wrapper.asInstanceOf[DateField].unparsableDateStringHandler match {
        case Some(handler) => {
          handler(new DateField.UnparsableDateStringEvent(WrapperUtil.wrapperFor(this).get, dateString)).orNull
        }
        case None => null
      }
    }
  }
}

object DateField {
  object Resolution extends Enumeration {
    import com.vaadin.shared.ui.datefield.Resolution._
    val Second = Value(SECOND.ordinal)
    val Minute = Value(MINUTE.ordinal)
    val Hour = Value(HOUR.ordinal)
    val Day = Value(DAY.ordinal)
    val Month = Value(MONTH.ordinal)
    val Year = Value(YEAR.ordinal)
  }

  case class UnparsableDateStringEvent(dateField: DateField, dateString: String) extends ComponentEvent(dateField)

  val DefaultUnparsableDateStringHandler: (UnparsableDateStringEvent => Option[java.util.Date]) = e => {
    throw new RuntimeException(e.dateField.parseErrorMessage.getOrElse(""))
  }
}

class DateField(
  override val p: com.vaadin.ui.DateField with DateFieldMixin = new com.vaadin.ui.DateField with DateFieldMixin)
    extends AbstractField[java.util.Date](p) with BlurNotifier with FocusNotifier {

  resolution = DateField.Resolution.Second

  var unparsableDateStringHandler: Option[DateField.UnparsableDateStringEvent => Option[java.util.Date]] =
    Some(DateField.DefaultUnparsableDateStringHandler)
  def unparsableDateStringHandler_=(handler: DateField.UnparsableDateStringEvent => Option[java.util.Date]) {
    unparsableDateStringHandler = Option(handler)
  }

  def resolution = DateField.Resolution(p.getResolution.ordinal)
  def resolution_=(resolution: DateField.Resolution.Value) {
    p.setResolution(com.vaadin.shared.ui.datefield.Resolution.values.apply(resolution.id))
  }

  def dateFormat: Option[String] = Option(p.getDateFormat)
  def dateFormat_=(dateFormat: Option[String]) { p.setDateFormat(dateFormat.orNull) }
  def dateFormat_=(dateFormat: String) { p.setDateFormat(dateFormat) }

  def lenient: Boolean = p.isLenient
  def lenient_=(lenient: Boolean) { p.setLenient(lenient) }

  def showISOWeekNumbers: Boolean = p.isShowISOWeekNumbers
  def showISOWeekNumbers_=(showISOWeekNumbers: Boolean) { p.setShowISOWeekNumbers(showISOWeekNumbers) }

  def parseErrorMessage: Option[String] = Option(p.getParseErrorMessage)
  def parseErrorMessage_=(parseErrorMessage: Option[String]) { p.setParseErrorMessage(parseErrorMessage.orNull) }
  def parseErrorMessage_=(parseErrorMessage: String) { p.setParseErrorMessage(parseErrorMessage) }

  def timeZone: Option[TimeZone] = Option(p.getTimeZone)
  def timeZone_=(timeZone: Option[java.util.TimeZone]) { p.setTimeZone(timeZone.orNull) }
  def timeZone_=(timeZone: java.util.TimeZone) { p.setTimeZone(timeZone) }

}