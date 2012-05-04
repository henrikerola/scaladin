package vaadin.scala.tests

import vaadin.scala.UriFragmentUtility
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito
import org.mockito.Matchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class UriFragmentUtilityTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  var uriFragmentUtility: UriFragmentUtility = _
  var spy: com.vaadin.ui.UriFragmentUtility = _

  before {
    val vaadinUriFragmentUtility = new com.vaadin.ui.UriFragmentUtility
    spy = Mockito.spy(vaadinUriFragmentUtility)
    uriFragmentUtility = new UriFragmentUtility(spy)
  }
  
  test("fragment") {
    assert(uriFragmentUtility.fragment === None)
    
    uriFragmentUtility.fragment = "test"
    assert(uriFragmentUtility.fragment === Some("test"))
    
    uriFragmentUtility.fragment = None
    assert(uriFragmentUtility.fragment === None)
    
    uriFragmentUtility.fragment = Some("test")
    assert(uriFragmentUtility.fragment === Some("test"))
  }
  
  test("fragmentChangedListeners") {
    val listener = { e: UriFragmentUtility.FragmentChangedEvent =>
    }

    assert(uriFragmentUtility.fragmentChangedListeners.size === 0)
    assert(!uriFragmentUtility.fragmentChangedListeners.contains(listener))
    
    uriFragmentUtility.fragmentChangedListeners += listener
    assert(uriFragmentUtility.fragmentChangedListeners.size === 1)
    assert(uriFragmentUtility.fragmentChangedListeners.contains(listener))
    assert(uriFragmentUtility.fragmentChangedListeners.head === listener)
    
    uriFragmentUtility.fragmentChangedListeners -= listener
    assert(uriFragmentUtility.fragmentChangedListeners.size === 0)
    assert(!uriFragmentUtility.fragmentChangedListeners.contains(listener))
    
    
    Mockito.verify(spy, Mockito.times(8)).getListeners(classOf[com.vaadin.ui.UriFragmentUtility#FragmentChangedEvent])
    Mockito.verify(spy).addListener(Matchers.any())
    Mockito.verify(spy).removeListener(Matchers.any())
  }
  
  // TODO test fire fragment change event and event's arguments
  
  test("fragment(fragment, fireEvent)") {
    uriFragmentUtility.fragment("test", false)
    Mockito.verify(spy).setFragment("test", false)
    
    Mockito.reset(spy) // Yes, reset is code smell...
    uriFragmentUtility.fragment("test", true)
    Mockito.verify(spy).setFragment("test", true)
    
    Mockito.reset(spy)
    uriFragmentUtility.fragment(Some("test"), false)
    Mockito.verify(spy).setFragment("test", false)
    
    Mockito.reset(spy)
    uriFragmentUtility.fragment(Some("test"), true)
    Mockito.verify(spy).setFragment("test", true)
    
    Mockito.reset(spy)
    uriFragmentUtility.fragment(None, false)
    Mockito.verify(spy).setFragment(null, false)
    
    Mockito.reset(spy)
    uriFragmentUtility.fragment(None, true)
    Mockito.verify(spy).setFragment(null, true)
  }

}
