package vaadin.scala

import vaadin.scala.internal.FragmentChangedListener
import vaadin.scala.internal.ListenersTrait
import vaadin.scala.mixins.UriFragmentUtilityMixin

package mixins {
  trait UriFragmentUtilityMixin extends AbstractComponentMixin
}

package internal {
  class FragmentChangedListener(val action: UriFragmentUtility.FragmentChangedEvent => Unit) extends com.vaadin.ui.UriFragmentUtility.FragmentChangedListener with Listener {
    def fragmentChanged(e: com.vaadin.ui.UriFragmentUtility#FragmentChangedEvent) = action(UriFragmentUtility.FragmentChangedEvent(wrapperFor[UriFragmentUtility](e.getUriFragmentUtility).get, Option(e.getUriFragmentUtility.getFragment)))
  }
}

object UriFragmentUtility {
  case class FragmentChangedEvent(uriFragmentUtility: UriFragmentUtility, fragment: Option[String]) extends Event
}

/**
 * @see com.vaadin.ui.UriFragmentUtility
 * @author Henri Kerola / Vaadin
 */
class UriFragmentUtility(override val p: com.vaadin.ui.UriFragmentUtility with UriFragmentUtilityMixin = new com.vaadin.ui.UriFragmentUtility with UriFragmentUtilityMixin) extends AbstractComponent(p) {

  def fragment = Option(p.getFragment)
  def fragment_=(fragment: Option[String]) = p.setFragment(fragment.orNull)
  def fragment_=(fragment: String) = p.setFragment(fragment)

  def fragment(fragment: String, fireEvent: Boolean) = p.setFragment(fragment, fireEvent)
  def fragment(fragment: Option[String], fireEvent: Boolean) = p.setFragment(fragment.orNull, fireEvent)

  lazy val fragmentChangedListeners = new ListenersTrait[UriFragmentUtility.FragmentChangedEvent, FragmentChangedListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.UriFragmentUtility#FragmentChangedEvent])
    override def addListener(elem: UriFragmentUtility.FragmentChangedEvent => Unit) = p.addListener(new FragmentChangedListener(elem))
    override def removeListener(elem: FragmentChangedListener) = p.removeListener(elem)
  }

}