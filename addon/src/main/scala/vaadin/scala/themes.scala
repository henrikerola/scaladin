package vaadin.scala

object BaseTheme extends BaseTheme

class BaseTheme {

  val ThemeName: String = "base"

  val ButtonLink: String = "link"

  val PanelLight: String = "light"

  val TreeConnectors: String = "connectors"
}

object Reindeer extends Reindeer

class Reindeer extends BaseTheme {
  override val ThemeName: String = "reindeer"

  val LabelH1: String = "h1"

  val LabelH2: String = "h2"

  val LabelSmall: String = "light"

  val ButtonDefault: String = "primary"

  val ButtonSmall: String = "small"

  val TextfieldSmall: String = "small"

  override val PanelLight: String = "light"

  val SplitpanelSmall: String = "small"

  val TabSheetBorderless: String = "borderless"

  val TabSheetSmall: String = "bar"

  val TabSheetMinimal: String = "minimal"

  val TabSheetHoverClosable: String = "hover-closable"

  val TabsheetSelectedClosable: String = "selected-closable"

  val TableBorderless: String = "borderless"

  val TableStrong: String = "strong"

  val LayoutWhite: String = "white"

  val LayoutBlue: String = "blue"

  val LayoutBlack: String = "black"

  val WindowLight: String = "light"

  val WindowBlack: String = "black"
}
