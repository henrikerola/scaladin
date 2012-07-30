package vaadin.scala

trait FilterableComponentContainer extends ComponentContainer {

  def filter(filterFunction: Component => Boolean): List[Component] = components.filter(filterFunction).toList

  def filterRecursive(filterFunction: Component => Boolean): List[Component] = {
    var newList: List[Component] = Nil
    for (component <- components) {
      if (filterFunction(component))
        newList = component :: newList
      if (component.isInstanceOf[ComponentContainer]) {
        newList = component.asInstanceOf[FilterableComponentContainer].filterRecursive(filterFunction) ::: newList
      }
    }
    newList
  }
}