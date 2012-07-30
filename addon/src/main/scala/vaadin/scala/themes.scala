package vaadin.scala

object BaseTheme extends BaseTheme

class BaseTheme {

  val THEME_NAME = "base"

  val BUTTON_LINK: String = "link"

  val PANEL_LIGHT: String = "light"

  val TREE_CONNECTORS: String = "connectors"
}

object Reindeer extends Reindeer

class Reindeer extends BaseTheme {
  override val THEME_NAME: String = "reindeer"

  val LABEL_H1: String = "h1"

  val LABEL_H2: String = "h2"

  val LABEL_SMALL: String = "light"

  val BUTTON_DEFAULT: String = "primary"

  val BUTTON_SMALL: String = "small"

  val TEXTFIELD_SMALL: String = "small"

  override val PANEL_LIGHT: String = "light"

  val SPLITPANEL_SMALL: String = "small"

  val TABSHEET_BORDERLESS: String = "borderless"

  val TABSHEET_SMALL: String = "bar"

  val TABSHEET_MINIMAL: String = "minimal"

  val TABSHEET_HOVER_CLOSABLE: String = "hover-closable"

  val TABSHEET_SELECTED_CLOSABLE: String = "selected-closable"

  val TABLE_BORDERLESS: String = "borderless"

  val TABLE_STRONG: String = "strong"

  val LAYOUT_WHITE: String = "white"

  val LAYOUT_BLUE: String = "blue"

  val LAYOUT_BLACK: String = "black"

  val WINDOW_LIGHT: String = "light"

  val WINDOW_BLACK: String = "black"
}
