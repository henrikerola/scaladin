package vaadin.scala

import vaadin.scala.internal.VaadinShortcutAction

case class ShortcutAction(val caption: String, keyCode: KeyCode, modifiers: KeyModifier*)
  extends VaadinShortcutAction(caption, keyCode.value, modifiers map { _.value } toArray)

case class KeyShortcut(keyCode: KeyCode, modifiers: KeyModifier*)

case class KeyCode(value: Int)

case class KeyModifier(value: Int)

object KeyCode {

  val Enter = KeyCode(13)

  val Escape = KeyCode(27)

  val PageUp = KeyCode(33)

  val PageDown = KeyCode(34)

  val Tab = KeyCode(9)

  val ArrowLeft = KeyCode(37)

  val ArrowUp = KeyCode(38)

  val ArrowRight = KeyCode(39)

  val ArrowDown = KeyCode(40)

  val Backspace = KeyCode(8)

  val Delete = KeyCode(46)

  val Insert = KeyCode(45)

  val End = KeyCode(35)

  val Home = KeyCode(36)

  val F1 = KeyCode(112)

  val F2 = KeyCode(113)

  val F3 = KeyCode(114)

  val F4 = KeyCode(115)

  val F5 = KeyCode(116)

  val F6 = KeyCode(117)

  val F7 = KeyCode(118)

  val F8 = KeyCode(119)

  val F9 = KeyCode(120)

  val F10 = KeyCode(121)

  val F11 = KeyCode(122)

  val F12 = KeyCode(123)

  val A = KeyCode(65)

  val B = KeyCode(66)

  val C = KeyCode(67)

  val D = KeyCode(68)

  val E = KeyCode(69)

  val F = KeyCode(70)

  val G = KeyCode(71)

  val H = KeyCode(72)

  val I = KeyCode(73)

  val J = KeyCode(74)

  val K = KeyCode(75)

  val L = KeyCode(76)

  val M = KeyCode(77)

  val N = KeyCode(78)

  val O = KeyCode(79)

  val P = KeyCode(80)

  val Q = KeyCode(81)

  val R = KeyCode(82)

  val S = KeyCode(83)

  val T = KeyCode(84)

  val U = KeyCode(85)

  val V = KeyCode(86)

  val W = KeyCode(87)

  val X = KeyCode(88)

  val Y = KeyCode(89)

  val Z = KeyCode(90)

  val Num0 = KeyCode(48)

  val Num1 = KeyCode(49)

  val Num2 = KeyCode(50)

  val Num3 = KeyCode(51)

  val Num4 = KeyCode(52)

  val Num5 = KeyCode(53)

  val Num6 = KeyCode(54)

  val Num7 = KeyCode(55)

  val Num8 = KeyCode(56)

  val Num9 = KeyCode(57)

  val Spacebar = KeyCode(32)
}

object KeyModifier {
  val Shift = KeyModifier(16)

  val Ctrl = KeyModifier(17)

  val Alt = KeyModifier(18)

  val Meta = KeyModifier(91)
}