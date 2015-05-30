package com.szadowsz.place.gis

import com.szadowsz.place.gis.tools.GeoToolsRegionLoader
import org.opengis.referencing.crs.CoordinateReferenceSystem
import com.vividsolutions.jts.geom.Point
import org.geotools.referencing.CRS
import org.geotools.referencing.crs.DefaultGeographicCRS
import org.geotools.geometry.jts.JTS
import java.io.File
import java.util.regex.Pattern

import scala.util.Try

/**
 * Companion Object
 */
object BoundaryMap {

  private val YEAR_REGEX = Pattern.compile("([12][0-9]{3})")

  def getYear(file: File): String = {
    val mat = BoundaryMap.YEAR_REGEX.matcher(file.getName)
    if (mat.find()) mat.group(1) else ""
  }
}

/**
 * Class to hold all extracted regions of interest from one gis file
 *
 * Also contains methods to identify if locations are within a region.
 *
 */
class BoundaryMap protected(file: File, results: (CoordinateReferenceSystem, List[Region])) extends Iterable[Region] {

  /* constrains shapefields into having the year they represent in their file name, most reliable way to get it based on VisionOfGB datasets */
  protected val _year = BoundaryMap.getYear(file)

  /* need for the mapping of default point system into the region specified one when identifying if a location is contained within a region */
  protected val _crs: CoordinateReferenceSystem = results._1

  /* the regions with their year correctly set. */
  protected val _regions: List[Region] = results._2


  /**
   * main constructor, used to automatically pull the regions out of a file.
   *
   * @param file shape file, extension ".shp"
   */
  def this(file: File) {
    this(file, GeoToolsRegionLoader.getRegions(file, BoundaryMap.getYear(file)).getOrElse((DefaultGeographicCRS.WGS84, List())))
  }

  /**
   * Method to finds a region that the point is contained within, if any
   *
   * @param point 2D point (Latitude/Longitude, X/Y), by default uses WG84
   * @return a Regions if successful, otherwise none.
   */
  def getRegionForLocation(point: Point): Option[Region] = {
    var target = point
    val tried = Try {
      if (!DefaultGeographicCRS.WGS84.equals(_crs)) {
        val transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, _crs, true)
        target = JTS.transform(point, transform).asInstanceOf[Point]
      }
      _regions.find(reg => reg.contains(target))
    }
    if(tried.isSuccess)tried.get else None
  }

  /**
   * Method finds a region by name, if it exists
   *
   * @param name expected name for the region (case-insensitive)
   * @return a Regions if successful, otherwise none.
   */
  def getRegionByName(name: String): Option[Region] = {
    _regions.find(reg => reg.name.equalsIgnoreCase(name))
  }

  // get all regions
  def getRegions = _regions.toArray

  override def iterator: Iterator[Region] = _regions.iterator
}