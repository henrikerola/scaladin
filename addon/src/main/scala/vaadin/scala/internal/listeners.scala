package vaadin.scala.internal

import vaadin.scala._

class LayoutClickListener(val action: LayoutClickEvent => Unit) extends com.vaadin.event.LayoutEvents.LayoutClickListener with Listener {
  def layoutClick(e: com.vaadin.event.LayoutEvents.LayoutClickEvent) = action(LayoutClickEvent(wrapperFor[Component](e.getComponent).get, wrapperFor[Component](e.getClickedComponent).get, wrapperFor[Component](e.getChildComponent).get, MouseButton(e.getButton.ordinal), e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}

class BlurListener(val action: BlurEvent => Unit) extends com.vaadin.event.FieldEvents.BlurListener with Listener {
  def blur(e: com.vaadin.event.FieldEvents.BlurEvent) = action(BlurEvent(wrapperFor[Component](e.getComponent()).get))
}

class FocusListener(val action: FocusEvent => Unit) extends com.vaadin.event.FieldEvents.FocusListener with Listener {
  def focus(e: com.vaadin.event.FieldEvents.FocusEvent) = action(FocusEvent(wrapperFor[Component](e.getComponent()).get))
}

class ExpandListener(val action: ExpandEvent => Unit) extends com.vaadin.ui.Tree.ExpandListener with Listener {
  def nodeExpand(e: com.vaadin.ui.Tree.ExpandEvent) = action(ExpandEvent(wrapperFor[Component](e.getComponent).get, e.getItemId))
}

class CollapseListener(val action: CollapseEvent => Unit) extends com.vaadin.ui.Tree.CollapseListener with Listener {
  def nodeCollapse(e: com.vaadin.ui.Tree.CollapseEvent) = action(CollapseEvent(wrapperFor[Component](e.getComponent).get, e.getItemId))
}

class FooterClickListener(val action: Table.FooterClickEvent => Unit) extends com.vaadin.ui.Table.FooterClickListener with Listener {
  def footerClick(e: com.vaadin.ui.Table.FooterClickEvent) = action(Table.FooterClickEvent(wrapperFor[Table](e.getComponent).get, e.getPropertyId, MouseButton(e.getButton.ordinal), e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}

class HeaderClickListener(val action: Table.HeaderClickEvent => Unit) extends com.vaadin.ui.Table.HeaderClickListener with Listener {
  def headerClick(e: com.vaadin.ui.Table.HeaderClickEvent) = action(Table.HeaderClickEvent(wrapperFor[Table](e.getComponent).get, e.getPropertyId, MouseButton(e.getButton.ordinal), e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}

class ColumnResizeListener(val action: Table.ColumnResizeEvent => Unit) extends com.vaadin.ui.Table.ColumnResizeListener with Listener {
  def columnResize(e: com.vaadin.ui.Table.ColumnResizeEvent) = action(Table.ColumnResizeEvent(wrapperFor[Table](e.getComponent).get, e.getPropertyId, e.getPreviousWidth, e.getCurrentWidth))
}

class SelectedTabChangeListener(val action: TabSheet.SelectedTabChangeEvent => Unit) extends com.vaadin.ui.TabSheet.SelectedTabChangeListener with Listener {
  def selectedTabChange(e: com.vaadin.ui.TabSheet.SelectedTabChangeEvent) = action(TabSheet.SelectedTabChangeEvent(wrapperFor[TabSheet](e.getTabSheet).get))
}

class UploadReceiver(val receiver: Upload.ReceiveEvent => java.io.OutputStream) extends com.vaadin.ui.Upload.Receiver {
  def receiveUpload(filename: String, mimeType: String): java.io.OutputStream = receiver(Upload.ReceiveEvent(filename, mimeType))
}

class UploadProgressListener(val action: Upload.ProgressEvent => Unit) extends com.vaadin.ui.Upload.ProgressListener with Listener {
  def updateProgress(readBytes: Long, contentLength: Long) = action(Upload.ProgressEvent(readBytes, contentLength))
}

class UploadStartedListener(val action: Upload.StartedEvent => Unit) extends com.vaadin.ui.Upload.StartedListener with Listener {
  def uploadStarted(e: com.vaadin.ui.Upload.StartedEvent) = action(Upload.StartedEvent(wrapperFor[Upload](e.getUpload).get, e.getFilename, e.getMIMEType, e.getContentLength))
}

class UploadFinishedListener(val action: Upload.FinishedEvent => Unit) extends com.vaadin.ui.Upload.FinishedListener with Listener {
  def uploadFinished(e: com.vaadin.ui.Upload.FinishedEvent) = action(Upload.FinishedEvent(wrapperFor[Upload](e.getUpload).get, e.getFilename, e.getMIMEType, e.getLength))
}

class UploadFailedListener(val action: Upload.FailedEvent => Unit) extends com.vaadin.ui.Upload.FailedListener with Listener {
  def uploadFailed(e: com.vaadin.ui.Upload.FailedEvent) = action(Upload.FailedEvent(wrapperFor[Upload](e.getUpload).get, e.getFilename, e.getMIMEType, e.getLength, e.getReason))
}

class UploadSucceededListener(val action: Upload.SucceededEvent => Unit) extends com.vaadin.ui.Upload.SucceededListener with Listener {
  def uploadSucceeded(e: com.vaadin.ui.Upload.SucceededEvent) = action(Upload.SucceededEvent(wrapperFor[Upload](e.getUpload).get, e.getFilename, e.getMIMEType, e.getLength))
}

class ValueChangeListener(val action: ValueChangeEvent => Unit) extends com.vaadin.data.Property.ValueChangeListener with Listener {
  def valueChange(e: com.vaadin.data.Property.ValueChangeEvent) = action(ValueChangeEvent(new BasicProperty(e.getProperty)))
}

class SplitterClickListener(val action: AbstractSplitPanel.SplitterClickEvent => Unit) extends com.vaadin.ui.AbstractSplitPanel.SplitterClickListener with Listener {
  def splitterClick(e: com.vaadin.ui.AbstractSplitPanel.SplitterClickEvent) = action(AbstractSplitPanel.SplitterClickEvent(wrapperFor[AbstractSplitPanel](e.getComponent()).get, MouseButton(e.getButton.ordinal), e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}

class ButtonClickListener(val action: Button.ClickEvent => Unit) extends com.vaadin.ui.Button.ClickListener with Listener {
  def buttonClick(e: com.vaadin.ui.Button.ClickEvent) = action(Button.ClickEvent(wrapperFor[Button](e.getButton()).get, e.getClientX(), e.getClientY(), e.getRelativeX(), e.getRelativeY(), e.isAltKey(), e.isCtrlKey(), e.isMetaKey(), e.isShiftKey()))
}

class TextChangeListener(val action: AbstractTextField.TextChangeEvent => Unit) extends com.vaadin.event.FieldEvents.TextChangeListener with Listener {
  def textChange(e: com.vaadin.event.FieldEvents.TextChangeEvent) = action(AbstractTextField.TextChangeEvent(wrapperFor[AbstractTextField](e.getComponent).get, e.getText, e.getCursorPosition))
}

class ClickListener(val action: ClickEvent => Unit) extends com.vaadin.event.MouseEvents.ClickListener with Listener {
  def click(e: com.vaadin.event.MouseEvents.ClickEvent) = action(ClickEvent(wrapperFor[Table](e.getComponent).get, MouseButton(e.getButton.ordinal), e.getClientX, e.getClientY, e.getRelativeX, e.getRelativeY, e.isDoubleClick, e.isAltKey, e.isCtrlKey, e.isMetaKey, e.isShiftKey))
}

class PopupVisibilityListener(val action: PopupView.PopupVisibilityEvent => Unit) extends com.vaadin.ui.PopupView.PopupVisibilityListener with Listener {
  def popupVisibilityChange(e: com.vaadin.ui.PopupView.PopupVisibilityEvent) = action(PopupView.PopupVisibilityEvent(wrapperFor[PopupView](e.getPopupView).get, e.isPopupVisible))
}

class UriFragmentChangedListener(val action: Page.UriFragmentChangedEvent => Unit)
    extends com.vaadin.server.Page.UriFragmentChangedListener with Listener {
  def uriFragmentChanged(e: com.vaadin.server.Page.UriFragmentChangedEvent) = {
    val page = new Page { val p = e.getPage }
    action(Page.UriFragmentChangedEvent(page, Option(e.getUriFragment)))
  }
}

class BrowserWindowResizeListener(val action: Page.BrowserWindowResizeEvent => Unit) extends com.vaadin.server.Page.BrowserWindowResizeListener with Listener {
  def browserWindowResized(e: com.vaadin.server.Page.BrowserWindowResizeEvent) = action(Page.BrowserWindowResizeEvent(wrapperFor[Page](e.getSource).get, e.getWidth, e.getHeight))
}

class WindowCloseListener(val action: Window.CloseEvent => Unit) extends com.vaadin.ui.Window.CloseListener with Listener {
  def windowClose(e: com.vaadin.ui.Window.CloseEvent) = action(Window.CloseEvent(wrapperFor[Window](e.getWindow).get))
}

class WindowResizeListener(val action: Window.ResizeEvent => Unit) extends com.vaadin.ui.Window.ResizeListener with Listener {
  def windowResized(e: com.vaadin.ui.Window.ResizeEvent) = action(Window.ResizeEvent(wrapperFor[Window](e.getWindow).get))
}

trait ViewChangeListener extends com.vaadin.navigator.ViewChangeListener with Listener {
  def beforeViewChange(e: com.vaadin.navigator.ViewChangeListener.ViewChangeEvent): Boolean = true
  def afterViewChange(e: com.vaadin.navigator.ViewChangeListener.ViewChangeEvent) {}
}

class BeforeViewChangeListener(val action: Navigator.ViewChangeEvent => Boolean) extends ViewChangeListener {
  override def beforeViewChange(e: com.vaadin.navigator.ViewChangeListener.ViewChangeEvent): Boolean = action(Navigator.ViewChangeEvent(wrapperFor[Navigator](e.getNavigator).get, wrapperFor[Navigator.View](e.getOldView), wrapperFor[Navigator.View](e.getNewView).get, Option(e.getViewName), e.getParameters))
}

class AfterViewChangeListener(val action: Navigator.ViewChangeEvent => Unit) extends ViewChangeListener {
  override def afterViewChange(e: com.vaadin.navigator.ViewChangeListener.ViewChangeEvent) {
    action(Navigator.ViewChangeEvent(wrapperFor[Navigator](e.getNavigator).get, wrapperFor[Navigator.View](e.getOldView), wrapperFor[Navigator.View](e.getNewView).get, Option(e.getViewName), e.getParameters))
  }
}
