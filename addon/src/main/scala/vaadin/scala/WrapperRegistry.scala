package vaadin.scala
import scala.collection.mutable
import scala.reflect.Manifest

trait Wrapper extends Serializable {
  def p: Any
}

object WrapperRegistry {

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

  def get[T](key: Any)(implicit m: Manifest[T]): T = {
    val v = map get key flatMap {
      case (om, s) => if (om <:< m) Some(s.asInstanceOf[T]) else None
    }
    v.getOrElse(throw new IllegalStateException("Cannot find wrapper for " + key))
  }
}