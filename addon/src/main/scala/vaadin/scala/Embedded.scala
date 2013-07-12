package vaadin.scala

import vaadin.scala.event.ClickNotifier
import vaadin.scala.mixins.EmbeddedMixin
import scala.collection.mutable
import vaadin.scala.server.Resource

package mixins {
  trait EmbeddedMixin extends AbstractComponentMixin { self: com.vaadin.ui.Embedded => }
}

/**
 * @see com.vaadin.ui.Embedded
 * @author Henri Kerola / Vaadin
 */
class Embedded(override val p: com.vaadin.ui.Embedded with EmbeddedMixin = new com.vaadin.ui.Embedded with EmbeddedMixin)
    extends AbstractComponent(p) with ClickNotifier {

  lazy val parameters: mutable.Map[String, String] = new mutable.Map[String, String] with Serializable {
    def -=(name: String): this.type = { p.removeParameter(name); this }

    def +=(parameter: (String, String)): this.type = { update(parameter._1, parameter._2); this }

    override def update(name: String, value: String) {
      p.setParameter(name, value)
    }

    def get(name: String) = Option(p.getParameter(name))

    override def size = {
      import scala.collection.JavaConverters._
      p.getParameterNames.asScala.size
    }

    def iterator: Iterator[(String, String)] = {
      import scala.collection.JavaConverters._
      p.getParameterNames.asScala.map { name =>
        (name, p.getParameter(name))
      }
    }
  }

  def alternateText: Option[String] = Option(p.getAlternateText)
  def alternateText_=(alternateText: String) = p.setAlternateText(alternateText)
  def alternateText_=(alternateText: Option[String]) = p.setAlternateText(alternateText.orNull)

  def source: Option[Resource] = wrapperFor(p.getSource)
  def source_=(source: Option[Resource]) = p.setSource(peerFor(source))
  def source_=(source: Resource) = p.setSource(source.p)

  def codebase: Option[String] = Option(p.getCodebase)
  def codebase_=(codebase: Option[String]) = p.setCodebase(codebase.orNull)
  def codebase_=(codebase: String) = p.setCodebase(codebase)

  def codetype: Option[String] = Option(p.getCodetype)
  def codetype_=(codetype: Option[String]) = p.setCodetype(codetype.orNull)
  def codetype_=(codetype: String) = p.setCodetype(codetype)

  def standby: Option[String] = Option(p.getStandby)
  def standby_=(standby: Option[String]) = p.setStandby(standby.orNull)
  def standby_=(standby: String) = p.setStandby(standby)

  def mimeType: Option[String] = Option(p.getMimeType)
  def mimeType_=(mimeType: Option[String]) = p.setMimeType(mimeType.orNull)
  def mimeType_=(mimeType: String) = p.setMimeType(mimeType)

  def classId: Option[String] = Option(p.getClassId)
  def classId_=(classId: Option[String]) = p.setClassId(classId.orNull)
  def classId_=(classId: String) = p.setClassId(classId)

  def archive: Option[String] = Option(p.getArchive)
  def archive_=(archive: Option[String]) = p.setArchive(archive.orNull)
  def archive_=(archive: String) = p.setArchive(archive)
}