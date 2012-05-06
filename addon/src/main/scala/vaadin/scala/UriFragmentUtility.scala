package vaadin.scala

import vaadin.scala.internal.FragmentChangedListener

package internal {
  class FragmentChangedListener(val action: UriFragmentUtility.FragmentChangedEvent => Unit) extends com.vaadin.ui.UriFragmentUtility.FragmentChangedListener with Listener {
    def fragmentChanged(e: com.vaadin.ui.UriFragmentUtility#FragmentChangedEvent) = action(UriFragmentUtility.FragmentChangedEvent(WrapperRegistry.get[UriFragmentUtility](e.getUriFragmentUtility).get, Option(e.getUriFragmentUtility.getFragment)))
  }
}

object UriFragmentUtility {
  case class FragmentChangedEvent(uriFragmentUtility: UriFragmentUtility, fragment: Option[String]) extends Event
}

/**
 * @see com.vaadin.ui.UriFragmentUtility
 * @author Henri Kerola / Vaadin
 */
class UriFragmentUtility(override val p: com.vaadin.ui.UriFragmentUtility = new com.vaadin.ui.UriFragmentUtility) extends AbstractComponent(p) {
  
  WrapperRegistry.put(this)

  def fragment = Option(p.getFragment)
  def fragment_=(fragment: Option[String]) = p.setFragment(fragment.getOrElse(null))
  def fragment_=(fragment: String) = p.setFragment(fragment)
  
  def fragment(fragment: String, fireEvent: Boolean) = p.setFragment(fragment, fireEvent)
  def fragment(fragment: Option[String], fireEvent: Boolean) = p.setFragment(fragment.getOrElse(null), fireEvent)

  lazy val fragmentChangedListeners = new ListenersTrait[UriFragmentUtility.FragmentChangedEvent => Unit, FragmentChangedListener] {
    override def listeners = p.getListeners(classOf[com.vaadin.ui.UriFragmentUtility#FragmentChangedEvent])
    override def addListener(elem: UriFragmentUtility.FragmentChangedEvent => Unit) = p.addListener(new FragmentChangedListener(elem))
    override def removeListener(elem: FragmentChangedListener) = p.removeListener(elem)
  }

}