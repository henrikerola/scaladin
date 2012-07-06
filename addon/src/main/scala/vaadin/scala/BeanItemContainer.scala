package vaadin.scala

import vaadin.scala.mixins.ContainerIndexedMixin
import vaadin.scala.mixins.BeanItemContainerMixin
import scala.collection.JavaConverters._

package mixins {
  trait BeanItemContainerMixin extends ContainerIndexedMixin
}

class BeanItemContainer[BT](override val p: com.vaadin.data.util.BeanItemContainer[BT] with BeanItemContainerMixin) extends Wrapper with ContainerIndexed {

  p.wrapper = this

  def this()(implicit m: Manifest[BT]) = {
    this(new com.vaadin.data.util.BeanItemContainer[BT](m.erasure.asInstanceOf[Class[BT]]) with BeanItemContainerMixin)
  }

  def this(beans: Iterable[BT])(implicit m: Manifest[BT]) = {
    this(new com.vaadin.data.util.BeanItemContainer[BT](m.erasure.asInstanceOf[Class[BT]], beans.asJavaCollection) with BeanItemContainerMixin)
  }

  def addBean(bean: BT): BeanItem[BT] = new BeanItem[BT](bean)

  protected def wrapItem(unwrapped: com.vaadin.data.Item): Item = new BeanItem[Any](unwrapped)
}

class BeanItem[BT](override val p: com.vaadin.data.util.BeanItem[BT]) extends Item {

  def this(bean: BT) {
    this(new com.vaadin.data.util.BeanItem[BT](bean))
  }

  def this(bean: BT, propertyIds: Iterable[Any]) = {
    this(new com.vaadin.data.util.BeanItem[BT](bean, propertyIds.asJavaCollection))
  }

  val bean = p.getBean
}