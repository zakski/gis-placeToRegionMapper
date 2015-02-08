package com.hipposretribution.place.gis.tools

import java.io.File
import org.geotools.data.DataStoreFinder
import collection.JavaConversions._
import com.hipposretribution.place.gis.Region
import org.geotools.data.FileDataStoreFinder
import com.vividsolutions.jts.geom.MultiPolygon
import org.geotools.feature.simple.SimpleFeatureImpl
import com.hipposretribution.place.gis.BoundaryMap
import com.hipposretribution.io.find.FileFinder
import com.hipposretribution.io.find.filters.ExtensionRFilter
import java.io.FilenameFilter
import com.hipposretribution.io.find.filters.ExtensionFilter


/** 
 * Convenience Object to help extract regions of interest from GIS files containing multi-polygons. In particular we want to open .shp files, also
 *  known as shapefiles, which is the main file of Esri format GIS projects.
 * 
 * Any object/method in this class with an R in its name is used for recursively loading files from multiple directories.
 * 
 * FROM WIKIPEDIA
 * The shapefile format is a popular geospatial vector data format for geographic information system (GIS) software. It 
 * is developed and regulated by Esri as a (mostly) open specification for data interoperability among Esri and other GIS software products.
 * The shapefile format can spatially describe vector features: points, lines, and polygons, representing, for example, water wells, rivers,
 * and lakes. Each item usually has attributes that describe it, such as name or temperature. 
 */
object GeoToolsMapLoader {

  val filter = new ExtensionFilter(".shp") // for single directory targeting
  val filterR = new ExtensionRFilter(".shp") // for root directory targeting that will sniff out all sub-folders

  def getMapsRFromFile(dirName: String) = {
    getMaps(new File(dirName), filterR)
  }

  def getMapsFromFile(dirName: String) = {
    getMaps(new File(dirName), filter)
  }

  def getMapsRFromFile(dir: File) = {
    getMaps(dir, filterR)
  }

  def getMapsFromFile(dir: File) = {
    getMaps(dir, filter)
  }

  protected def getMaps(dir: File, fileFilter: FilenameFilter) = {
    val files = FileFinder.getFiles(dir, filterR)
    files.map(f => new BoundaryMap(f)).toList
  }
}