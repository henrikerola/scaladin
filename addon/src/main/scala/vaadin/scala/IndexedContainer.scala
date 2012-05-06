package vaadin.scala

class IndexedContainer(override val p: com.vaadin.data.util.IndexedContainer = new com.vaadin.data.util.IndexedContainer)
  extends Container.Container with Container.Sortable {

  WrapperRegistry.put(this)

  protected def wrapItem(unwrapped: com.vaadin.data.Item): Item = new IndexedContainerItem(unwrapped)

  protected def wrapProperty(unwrapped: com.vaadin.data.Property): Property = new BasicProperty(unwrapped)
}

class IndexedContainerItem(override val p: com.vaadin.data.Item) extends Item {

  def wrapProperty(unwrapped: com.vaadin.data.Property): Property = new BasicProperty(unwrapped)
}

