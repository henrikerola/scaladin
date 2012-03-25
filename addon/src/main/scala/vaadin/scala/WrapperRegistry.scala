package vaadin.scala
import scala.collection.mutable
import scala.reflect.Manifest

trait Wrapper extends Serializable {
  def p: Any
}

class WrapperRegistry {

  private var map = mutable.WeakHashMap.empty[Any, (Manifest[_], Any)]

  def put[T <: Wrapper](wrapper: T)(implicit m: Manifest[T]) {
    if (wrapper == null) {
      throw new IllegalArgumentException("wrapper cannot be null")
    }
    if (wrapper.p == null) {
      throw new IllegalArgumentException("wrapper.p cannot be null")
    }
    map.update(wrapper.p, m -> wrapper)
  }

  def get[T](key: Any)(implicit m: Manifest[T]): Option[T] = {
    if (key == null) {
      return None
    }
    if(key.isInstanceOf[Wrapper]) {
      return Some(key.asInstanceOf[T])
    }
    val v = map get key flatMap {
      case (om, s) => if (om <:< m) Some(s.asInstanceOf[T]) else None
    }
    if (v.isDefined) v else throw new IllegalStateException("Cannot find wrapper for " + key)
  }
}