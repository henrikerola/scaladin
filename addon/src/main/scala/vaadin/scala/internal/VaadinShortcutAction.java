package vaadin.scala.internal;

import com.vaadin.event.ShortcutAction;

@SuppressWarnings("serial")
public class VaadinShortcutAction extends ShortcutAction {
	public VaadinShortcutAction() {
		super("", 0, null);
	}

	public VaadinShortcutAction(String caption, int kc) {
		super(caption, kc, null);
	}

	public VaadinShortcutAction(String caption, int kc, int[] modifiers) {
		super(caption, kc, modifiers);
	}
}
