package vaadin.scala

object Property {
  def apply[T](value: T): com.vaadin.data.Property = new com.vaadin.data.util.ObjectProperty[T](value)
}

object Item {
  def apply(properties: Tuple2[Any, Any]*): com.vaadin.data.Item = {
    val item = new com.vaadin.data.util.PropertysetItem
    properties foreach(p => item.addItemProperty(p._1, Property(p._2)))
    item
  }
}

object Container {
  def apply(items: Tuple2[Any, Seq[Tuple2[Any, Any]]]*): com.vaadin.data.Container = {
    val container = new com.vaadin.data.util.IndexedContainer
    for(item <- items) {
      for(property <- item._2) {
        container.addContainerProperty(property._1, property._2.getClass, null)
        val containerItem = container.addItem(item._1)
        containerItem.getItemProperty(property._1).setValue(property._2)
      }
    }
    
    container
  }
}





// container \ ALL \ (propertyId, property) => propertyId == "foo" && property getValue == null 