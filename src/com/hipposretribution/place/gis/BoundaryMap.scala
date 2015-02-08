package com.hipposretribution.place.gis

import org.opengis.referencing.crs.CoordinateReferenceSystem
import com.hipposretribution.place.gis.tools.GeoToolsRegionLoader
import com.vividsolutions.jts.geom.Point
import org.geotools.referencing.CRS
import org.geotools.referencing.crs.DefaultGeographicCRS
import org.geotools.geometry.jts.JTS
import java.io.File
import java.util.regex.Pattern

/**
 * Companion Object
 */
object BoundaryMap {

  private val YEAR_REGEX = Pattern.compile("([12][0-9]{3})")
  
  def getYear(file : File) = { var mat = BoundaryMap.YEAR_REGEX.matcher(file.getName()); mat.find(); mat.group(1) }
}

/**
 * Class to hold all extracted regions of interest from one gis file
 *
 * Also contains methods to identify if locations are within a region.
 *
 */
class BoundaryMap protected (file: File, results: Tuple2[CoordinateReferenceSystem, List[Region]]) {

  // constrains shapefields into having the year they represent in their file name, most reliable way to get it based on VisionOfGB datasets
  protected val _year = BoundaryMap.getYear(file)

  // need for the mapping of default point system into the region specified one when identifying if a location is contained within a region
  protected val _crs = results._1
  
  // the regions with their year correctly set.
  protected val _regions = results._2
  
  
  // main constructor, used to automatically pull the regions out of a file
  def this(file: File) {
    this(file,GeoToolsRegionLoader.getRegions(file, BoundaryMap.getYear(file)))
  }
  
  // finds a region that the point is contained within, if any
  def getRegionForLocation(point: Point) : Option[Region] = {
    var target = point
    if (!DefaultGeographicCRS.WGS84.equals(_crs)) {
      val transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, _crs, true)
      target = JTS.transform(point, transform).asInstanceOf[Point]
    }
    _regions.find(reg => reg.contains(target))
  }

  // finds a region by name, if it exists
  def getRegionByName(name: String) : Option[Region] = {
    _regions.find(reg => reg.name == name)
  }

  // get all regions
  def getRegions() = _regions.toArray
}