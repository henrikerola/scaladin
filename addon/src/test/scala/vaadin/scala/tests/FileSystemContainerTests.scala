package vaadin.scala.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import vaadin.scala.FilesystemContainer
import java.io.File
import vaadin.scala.ThemeResource

@RunWith(classOf[JUnitRunner])
class FileSystemContainerTests extends FunSuite with BeforeAndAfter {

  var container: FilesystemContainer = _
  val temp1 = File.createTempFile("FileSystemContainerTest1", "txt")
  val temp2 = File.createTempFile("FileSystemContainerTest2", "txt")

  before {
    container = new FilesystemContainer(root = temp1, recursiveFromRoot = false)
    container.addRoot(temp2)
  }

  after {
    temp1.deleteOnExit
    temp2.deleteOnExit
  }

  ignore("getItem") {
    assert(2 === container.size)
    val item = container.getItem(temp1).get

    val nameProperty = item.property(FilesystemContainer.PropertyName)
    val iconProperty = item.property(FilesystemContainer.PropertyIcon)

    assert(nameProperty != None)
    assert(nameProperty.flatMap(_.value).map(_.asInstanceOf[String]).exists(_.startsWith("FileSystemContainerTest")))

    assert(iconProperty != None)
    // how should work with Vaadin 7 and generics: ??
    assert(iconProperty.flatMap(_.value).get.isInstanceOf[ThemeResource], "File icon was " + item.property(FilesystemContainer.PropertyIcon).get.value.get.getClass + " instead of ThemeResource")
  }
}