package vaadin.scala

case class KeyShortcut(keyCode: KeyCode, modifiers: KeyModifier*)

case class KeyCode(value: Int)
case class KeyModifier(value: Int)

object KeyCode {
  //FIXME: case correct
  val ENTER = KeyCode(13)

  val ESCAPE = KeyCode(27)

  val PAGE_UP = KeyCode(33)

  val PAGE_DOWN = KeyCode(34)

  val TAB = KeyCode(9)

  val ARROW_LEFT = KeyCode(37)

  val ARROW_UP = KeyCode(38)

  val ARROW_RIGHT = KeyCode(39)

  val ARROW_DOWN = KeyCode(40)

  val BACKSPACE = KeyCode(8)

  val DELETE = KeyCode(46)

  val INSERT = KeyCode(45)

  val END = KeyCode(35)

  val HOME = KeyCode(36)

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

  val NUM0 = KeyCode(48)

  val NUM1 = KeyCode(49)

  val NUM2 = KeyCode(50)

  val NUM3 = KeyCode(51)

  val NUM4 = KeyCode(52)

  val NUM5 = KeyCode(53)

  val NUM6 = KeyCode(54)

  val NUM7 = KeyCode(55)

  val NUM8 = KeyCode(56)

  val NUM9 = KeyCode(57)

  val SPACEBAR = KeyCode(32)
}

object KeyModifier {
  //FIXME: case correct
  val SHIFT = KeyModifier(16)

  val CTRL = KeyModifier(17)

  val ALT = KeyModifier(18)

  val META = KeyModifier(91)
}