package com.szadowsz.place.gis

import com.szadowsz.io.PropertiesHandler
import com.szadowsz.place.gis.tools.GeoToolsRegionLoader
import com.szadowsz.place.names.PlaceRecord
import com.vividsolutions.jts.geom.Point
import com.vividsolutions.jts.geom.Geometry
import org.opengis.feature.Property
import org.geotools.feature.simple.SimpleFeatureImpl
import scala.collection.mutable.Buffer
import org.opengis.feature.simple.SimpleFeature

/**
 * Companion Object to help extract data from SimpleFeatureImpl to build a region
 *
 */
object Region {

  val fields = Map("name" -> List("NAME", "G_NAME"), "lang" -> List("G_LANGUAGE"),
    "regionType" -> List("G_STATUS"), "regionLevel" -> List("UNITTYPE"))

  // Static method to get useful region metadata using one of many alternative names
  protected def getProperty(feature: SimpleFeature, name: String): String = {
    val variations = fields.getOrElse(name, List())
    var field = variations.find(feature.getProperty(_) != null)
    if (field.isDefined) {
      feature.getProperty(field.get).getValue.toString
    } else {
      ""
    }
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