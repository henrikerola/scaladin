package vaadin.scala

import java.util.{ Date, Locale }

/**
 * @see com.vaadin.server.WebBrowser
 * @author Henri Kerola / Vaadin
 */
trait WebBrowser extends Wrapper {

  val p: com.vaadin.server.WebBrowser

  def screenHeight: Int = p.getScreenHeight

  def screenWidth: Int = p.getScreenWidth

  def browserApplication: String = p.getBrowserApplication

  def address: Option[String] = Option(p.getAddress)

  def locale: Locale = p.getLocale

  def isSecureConnection: Boolean = p.isSecureConnection

  def isFirefox: Boolean = p.isFirefox

  def isIE: Boolean = p.isIE

  def isSafari: Boolean = p.isSafari

  def isOpera: Boolean = p.isOpera

  def isChrome: Boolean = p.isChrome

  def isChromeFrame: Boolean = p.isChromeFrame

  def isChromeFrameCapable: Boolean = p.isChromeFrameCapable

  def browserMajorVersion: Int = p.getBrowserMajorVersion

  def browserMinorVersion: Int = p.getBrowserMinorVersion

  def isLinux: Boolean = p.isLinux

  def isMacOSX: Boolean = p.isMacOSX

  def isWindows: Boolean = p.isWindows

  def timezoneOffset: Int = p.getTimezoneOffset

  def rawTimezoneOffset: Int = p.getRawTimezoneOffset

  def dstSavings: Int = p.getDSTSavings

  def isDSTInEffect: Boolean = p.isDSTInEffect

  def currentDate: Date = p.getCurrentDate

  def isTooOldToFunctionProperly: Boolean = p.isTooOldToFunctionProperly

  def isTouchDevice: Boolean = p.isTouchDevice

}
