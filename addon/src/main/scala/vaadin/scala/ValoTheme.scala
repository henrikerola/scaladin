package vaadin.scala

import com.vaadin.ui.themes.ValoTheme._

object ValoTheme extends ValoTheme

/**
 *
 * @author Henri Kerola / Vaadin
 */
trait ValoTheme {

  val ThemeName: String = THEME_NAME

  val NotificationTray: String = NOTIFICATION_TRAY
  val NotificationWarning: String = NOTIFICATION_WARNING
  val NotificationError: String = NOTIFICATION_ERROR
  val NotificationSystem: String = NOTIFICATION_SYSTEM
  val NotificationBar: String = NOTIFICATION_BAR
  val NotificationSmall: String = NOTIFICATION_SMALL
  val NotificationClosable: String = NOTIFICATION_CLOSABLE
  val NotificationSuccess: String = NOTIFICATION_SUCCESS
  val NotificationFailure: String = NOTIFICATION_FAILURE

  val LabelH1: String = LABEL_H1
  val LabelH2: String = LABEL_H2
  val LabelH3: String = LABEL_H3
  val LabelH4: String = LABEL_H4
  val LabelNoMargin: String = LABEL_NO_MARGIN
  val LabelTiny: String = LABEL_TINY
  val LabelSmall: String = LABEL_SMALL
  val LabelLarge: String = LABEL_LARGE
  val LabelHuge: String = LABEL_HUGE
  val LabelLight: String = LABEL_LIGHT
  val LabelBold: String = LABEL_BOLD
  val LabelColored: String = LABEL_COLORED
  val LabelSuccess: String = LABEL_SUCCESS
  val LabelFailure: String = LABEL_FAILURE
  val LabelSpinner: String = LABEL_SPINNER

  val ButtonPrimary: String = BUTTON_PRIMARY
  val ButtonFriendly: String = BUTTON_FRIENDLY
  val ButtonDanger: String = BUTTON_DANGER
  val ButtonBorderless: String = BUTTON_BORDERLESS
  val ButtonBorderlessColored: String = BUTTON_BORDERLESS_COLORED
  val ButtonQuiet: String = BUTTON_QUIET
  val ButtonLink: String = BUTTON_LINK
  val ButtonTiny: String = BUTTON_TINY
  val ButtonSmall: String = BUTTON_SMALL
  val ButtonLarge: String = BUTTON_LARGE
  val ButtonHuge: String = BUTTON_HUGE
  val ButtonIconAlignRight: String = BUTTON_ICON_ALIGN_RIGHT
  val ButtonIconAlignTop: String = BUTTON_ICON_ALIGN_TOP
  val ButtonIconOnly: String = BUTTON_ICON_ONLY

  val LinkSmall: String = LINK_SMALL
  val LinkLarge: String = LINK_LARGE

  val TextfieldTiny: String = TEXTFIELD_TINY
  val TextfieldSmall: String = TEXTFIELD_SMALL
  val TextfieldLarge: String = TEXTFIELD_LARGE
  val TextfieldHuge: String = TEXTFIELD_HUGE
  val TextfieldBorderless: String = TEXTFIELD_BORDERLESS
  val TextfieldAlignRight: String = TEXTFIELD_ALIGN_RIGHT
  val TextfieldAlignCenter: String = TEXTFIELD_ALIGN_CENTER
  val TextfieldInlineIcon: String = TEXTFIELD_INLINE_ICON

  val TextareaTiny: String = TEXTAREA_TINY
  val TextareaSmall: String = TEXTAREA_SMALL
  val TextareaLarge: String = TEXTAREA_LARGE
  val TextareaHuge: String = TEXTAREA_HUGE
  val TextareaBorderless: String = TEXTAREA_BORDERLESS
  val TextareaAlignRight: String = TEXTAREA_ALIGN_RIGHT
  val TextareaAlignCenter: String = TEXTAREA_ALIGN_CENTER

  val DatefieldTiny: String = DATEFIELD_TINY
  val DatefieldSmall: String = DATEFIELD_SMALL
  val DatefieldLarge: String = DATEFIELD_LARGE
  val DatefieldHuge: String = DATEFIELD_HUGE
  val DatefieldBorderless: String = DATEFIELD_BORDERLESS
  val DatefieldAlignRight: String = DATEFIELD_ALIGN_RIGHT
  val DatefieldAlignCenter: String = DATEFIELD_ALIGN_CENTER

  val ComboboxTiny: String = COMBOBOX_TINY
  val ComboboxSmall: String = COMBOBOX_SMALL
  val ComboboxLarge: String = COMBOBOX_LARGE
  val ComboboxHuge: String = COMBOBOX_HUGE
  val ComboboxBorderless: String = COMBOBOX_BORDERLESS
  val ComboboxAlignRight: String = COMBOBOX_ALIGN_RIGHT
  val ComboboxAlignCenter: String = COMBOBOX_ALIGN_CENTER

  val CheckboxSmall: String = CHECKBOX_SMALL
  val CheckboxLarge: String = CHECKBOX_LARGE

  val OptiongroupSmall: String = OPTIONGROUP_SMALL
  val OptiongroupLarge: String = OPTIONGROUP_LARGE
  val OptiongroupHorizontal: String = OPTIONGROUP_HORIZONTAL

  val SliderNoIndicator: String = SLIDER_NO_INDICATOR

  val ProgressbarPoint: String = PROGRESSBAR_POINT

  val MenubarSmall: String = MENUBAR_SMALL
  val MenubarBorderless: String = MENUBAR_BORDERLESS

  val TableNoStripes: String = TABLE_NO_STRIPES
  val TreetableNoStripes: String = TREETABLE_NO_STRIPES
  val TableNoVerticalLines: String = TABLE_NO_VERTICAL_LINES
  val TreetableNoVerticalLines: String = TREETABLE_NO_VERTICAL_LINES
  val TableNoHorizontalLines: String = TABLE_NO_HORIZONTAL_LINES
  val TreetableNoHorizontalLines: String = TREETABLE_NO_HORIZONTAL_LINES
  val TableNoHeader: String = TABLE_NO_HEADER
  val TreetableNoHeader: String = TREETABLE_NO_HEADER
  val TableBorderless: String = TABLE_BORDERLESS
  val TreetableBorderless: String = TREETABLE_BORDERLESS
  val TableCompact: String = TABLE_COMPACT
  val TreetableCompact: String = TREETABLE_COMPACT
  val TableSmall: String = TABLE_SMALL
  val TreetableSmall: String = TREETABLE_SMALL

  val DragAndDropWrapperNoBoxDragHints: String = DRAG_AND_DROP_WRAPPER_NO_BOX_DRAG_HINTS
  val DragAndDropWrapperNoVerticalDragHints: String = DRAG_AND_DROP_WRAPPER_NO_VERTICAL_DRAG_HINTS
  val DragAndDropWrapperNoHorizontalDragHints: String = DRAG_AND_DROP_WRAPPER_NO_HORIZONTAL_DRAG_HINTS

  val PanelBorderless: String = PANEL_BORDERLESS
  val PanelScrollIndicator: String = PANEL_SCROLL_INDICATOR
  val PanelWell: String = PANEL_WELL

  val SplitpanelLarge: String = SPLITPANEL_LARGE

  val TabsheetFramed: String = TABSHEET_FRAMED
  val TabsheetCenteredTabs: String = TABSHEET_CENTERED_TABS
  val TabsheetEqualWidthTabs: String = TABSHEET_EQUAL_WIDTH_TABS
  val TabsheetPaddedTabbar: String = TABSHEET_PADDED_TABBAR
  val TabsheetCompactTabbar: String = TABSHEET_COMPACT_TABBAR
  val TabsheetIconsOnTop: String = TABSHEET_ICONS_ON_TOP
  val TabsheetOnlySelectedTabIsClosable: String = TABSHEET_ONLY_SELECTED_TAB_IS_CLOSABLE

  val AccordionBorderless: String = ACCORDION_BORDERLESS

  val WindowTopToolbar: String = WINDOW_TOP_TOOLBAR
  val WindowBottomToolbar: String = WINDOW_BOTTOM_TOOLBAR

  val FormlayoutLight: String = FORMLAYOUT_LIGHT

  val LayoutCard: String = LAYOUT_CARD
  val LayoutWell: String = LAYOUT_WELL
  val LayoutHorizontalWrapping: String = LAYOUT_HORIZONTAL_WRAPPING
  val LayoutComponentGroup: String = LAYOUT_COMPONENT_GROUP

  val UiWithMenu: String = UI_WITH_MENU

  val MenuRoot: String = MENU_ROOT
  val MenuPart: String = MENU_PART
  val MenuPartLargeIcons: String = MENU_PART_LARGE_ICONS
  val MenuTitle: String = MENU_TITLE
  val MenuSubtitle: String = MENU_SUBTITLE
  val MenuItem: String = MENU_ITEM
  val MenuBadge: String = MENU_BADGE
  val MenuLogo: String = MENU_LOGO
}
