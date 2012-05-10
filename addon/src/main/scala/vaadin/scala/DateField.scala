package vaadin.scala

import vaadin.scala.mixins.DateFieldMixin
import vaadin.scala.listeners.BlurListeners
import vaadin.scala.listeners.FocusListeners

package mixins {
  trait DateFieldMixin extends AbstractFieldMixin
}

object DateField {
  object Resolution extends Enumeration {
    import com.vaadin.ui.DateField._
    // Support for milliseconds is dropped in Vaadin 7 so not adding here either
    val Second = Value(RESOLUTION_SEC)
    val Minute = Value(RESOLUTION_MIN)
    val Hour = Value(RESOLUTION_HOUR)
    val Day = Value(RESOLUTION_DAY)
    val Month = Value(RESOLUTION_MONTH)
    val Year = Value(RESOLUTION_YEAR)
  }
}

class DateField(override val p: com.vaadin.ui.DateField with DateFieldMixin = new com.vaadin.ui.DateField with DateFieldMixin) extends AbstractField(p) {

  resolution = DateField.Resolution.Second

  // TODO: handleUnparsableDateString

  def resolution = DateField.Resolution(p.getResolution)
  def resolution_=(resolution: DateField.Resolution.Value) = p.setResolution(resolution.id)

  def dateFormat = Option(p.getDateFormat)
  def dateFormat_=(dateFormat: Option[String]) = p.setDateFormat(dateFormat.getOrElse(null))
  def dateFormat_=(dateFormat: String) = p.setDateFormat(dateFormat)

  def lenient = p.isLenient
  def lenient_=(lenient: Boolean) = p.setLenient(lenient)

  lazy val blurListeners = new BlurListeners(p)
  lazy val focusListeners = new FocusListeners(p)

  def showISOWeekNumbers = p.isShowISOWeekNumbers
  def showISOWeekNumbers_=(showISOWeekNumbers: Boolean) = p.setShowISOWeekNumbers(showISOWeekNumbers)

  def parseErrorMessage = Option(p.getParseErrorMessage)
  def parseErrorMessage_=(parseErrorMessage: Option[String]) = p.setParseErrorMessage(parseErrorMessage.getOrElse(null))
  def parseErrorMessage_=(parseErrorMessage: String) = p.setParseErrorMessage(parseErrorMessage)

  def timeZone = Option(p.getTimeZone)
  def timeZone_=(timeZone: Option[java.util.TimeZone]) = p.setTimeZone(timeZone.getOrElse(null))
  def timeZone_=(timeZone: java.util.TimeZone) = p.setTimeZone(timeZone)

}