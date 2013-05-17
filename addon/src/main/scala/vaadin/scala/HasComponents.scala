package vaadin.scala

import vaadin.scala.mixins.HasComponentsMixin
import scala.collection.JavaConverters._

package mixins {
  trait HasComponentsMixin extends ComponentMixin { self: com.vaadin.ui.HasComponents => }
}

/**
 *
 * @author Henri Kerola / Vaadin
 */
trait HasComponents extends Component {

  override def p: com.vaadin.ui.HasComponents with HasComponentsMixin

  def components: Iterable[Component] = new Iterable[Component] {
    def iterator: Iterator[Component] = p.iterator.asScala map { wrapperFor(_).get }
  }

  // TODO: Component attach, detach...
}
