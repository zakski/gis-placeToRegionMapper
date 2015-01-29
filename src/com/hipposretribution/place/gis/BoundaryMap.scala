package com.hipposretribution.place.gis

import org.opengis.referencing.crs.CoordinateReferenceSystem
import com.hipposretribution.place.gis.tools.GeoToolsRegionLoader
import com.vividsolutions.jts.geom.Point
import org.geotools.referencing.CRS
import org.geotools.referencing.crs.DefaultGeographicCRS
import org.geotools.geometry.jts.JTS
import java.io.File
import java.util.regex.Pattern

object BoundaryMap {

  private val YEAR_REGEX = Pattern.compile("([12][0-9]{3})")
}

class BoundaryMap protected (filePath: String, results: Tuple2[CoordinateReferenceSystem, List[com.hipposretribution.place.gis.Region]]) {

  protected val _path = filePath
  protected val _filename = _path.substring(_path.lastIndexOf("\\"))
  protected val _year = {var mat = BoundaryMap.YEAR_REGEX.matcher(_filename); mat.find(); mat.group(1)}
 
  protected val _crs = results._1
  protected val _regions = results._2.map ( x => x.year(_year) )
  def this(filePath: String) {
    this(filePath, GeoToolsRegionLoader.getRegions(filePath))
  }

  def this(file: File) {
    this(file.getPath(), GeoToolsRegionLoader.getRegions(file))
  }

  def getRegionForLocation(point: Point) = {
    var target = point
    if (!DefaultGeographicCRS.WGS84.equals(_crs)) {
      val transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, _crs, true)
      target = JTS.transform(point, transform).asInstanceOf[Point]
    }
    _regions.find(reg => reg.contains(target)).getOrElse(null)
  }

  def getRegionByName(name: String) = {
    _regions.find(reg => reg.name == name).getOrElse(null)
  }
}