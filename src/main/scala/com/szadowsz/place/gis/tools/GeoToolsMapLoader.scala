package com.szadowsz.place.gis.tools

import java.io.File
import com.szadowsz.io.find.FileFinder
import com.szadowsz.io.find.filters.{ExtensionFilter, ExtensionRFilter}
import com.szadowsz.place.gis.{BoundaryMap, Region}
import org.geotools.data.DataStoreFinder
import collection.JavaConversions._
import org.geotools.data.FileDataStoreFinder
import com.vividsolutions.jts.geom.MultiPolygon
import org.geotools.feature.simple.SimpleFeatureImpl
import java.io.FilenameFilter


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

  def getMapsRecursively(dirName: String):List[BoundaryMap] = {
    getMaps(new File(dirName), filterR)
  }

  def getMapsFromFolder(dirName: String):List[BoundaryMap] = {
    getMaps(new File(dirName), filter)
  }

  def getMapsRecursively(dir: File):List[BoundaryMap] = {
    getMaps(dir, filterR)
  }

  def getMapsFromFolder(dir: File):List[BoundaryMap] = {
    getMaps(dir, filter)
  }

  protected def getMaps(dir: File, fileFilter: FilenameFilter):List[BoundaryMap] = {
    val files = FileFinder.getFiles(dir, fileFilter)
    files.map(f => new BoundaryMap(f)).toList
  }
}