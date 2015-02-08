package com.hipposretribution.place.gis

import com.hipposretribution.place.gis.tools.GeoToolsRegionLoader
import com.vividsolutions.jts.geom.Point
import com.vividsolutions.jts.geom.Geometry
import org.opengis.feature.Property
import com.hipposretribution.io.PropertiesHandler
import org.geotools.feature.simple.SimpleFeatureImpl
import com.hipposretribution.place.names.PlaceRecord
import scala.collection.mutable.Buffer
import org.opengis.feature.simple.SimpleFeature

/**
 * Companion Object to help extract data from SimpleFeatureImpl to build a region
 *
 */
object Region {

  val fields = List("name", "lang", "regionType", "regionLevel")

  val vars = getProps()

  // Static method to get useful region metadata using one of many alternative names
  protected def getProps() = {
    val props = PropertiesHandler.loadProperties("./resources/config/dataset.properties")
    var variations = Map[String, Array[String]]()

    fields.foreach(f => variations += f -> props.getProperty(f).split(","))

    variations.toMap
  }

  // Static method to get useful region metadata using one of many alternative names
  protected def getProperty(feature: SimpleFeature, name: String): String = {
    val variations = vars.getOrElse(name, null)

    for (i <- variations.indices) {
      val att = feature.getProperty(variations(i))
      if (att != null) {
        return att.getValue().toString()
      }
    }
    ""
  }
}

/**
 * Class to hold all relevant data from a single region
 *
 */
class Region(feature: SimpleFeature, geom: Geometry, val year: String) {

  // pull the properties off the simple feature
  val name = Region.getProperty(feature, "name")
  val lang = Region.getProperty(feature, "lang")
  val level = Region.getProperty(feature, "regionLevel")
  val rType = Region.getProperty(feature, "regionType")

  protected val _locales = Buffer[PlaceRecord]()

  protected val _geo = geom

  def contains(coordinate: Point) = {
    coordinate.within(_geo)
  }

  def appendLocale(record: PlaceRecord) {
    _locales += record
  }

  def locales = _locales.toList

  override def toString = name + "[language: " + lang + " tier: " + level + " type: " + rType + " year: " + year + "]"
}