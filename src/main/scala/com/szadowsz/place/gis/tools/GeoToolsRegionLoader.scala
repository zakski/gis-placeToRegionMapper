package com.szadowsz.place.gis.tools

import java.io.File
import com.szadowsz.place.gis.{BoundaryMap, Region}
import org.geotools.data.DataStoreFinder
import collection.JavaConversions._
import org.opengis.feature.Feature
import org.geotools.data.FileDataStoreFinder
import com.vividsolutions.jts.geom.MultiPolygon
import org.geotools.feature.simple.SimpleFeatureImpl
import org.opengis.referencing.crs.CoordinateReferenceSystem
import org.geotools.referencing.CRS
import org.opengis.feature.simple.SimpleFeature

import scala.util.Try

/**
 * Convenience Object to help extract regions of interest from a GIS file containing multi-polygons. In particular we want to open .shp files, also
 * known as shapefiles, which is the main file of Esri format GIS projects.
 *
 * FROM WIKIPEDIA
 * The shapefile format is a popular geospatial vector data format for geographic information system (GIS) software. It
 * is developed and regulated by Esri as a (mostly) open specification for data interoperability among Esri and other GIS software products.
 * The shapefile format can spatially describe vector features: points, lines, and polygons, representing, for example, water wells, rivers,
 * and lakes. Each item usually has attributes that describe it, such as name or temperature.
 */
object GeoToolsRegionLoader {

  /**
   * Method to initialise a region.
   *
   * @param feature the feature, or in our case region.
   * @param year the year the regions existed in.
   * @return the Region
   */
  private def featureToRegion(feature: SimpleFeature, year: String) = {
    new Region(feature, feature.getDefaultGeometryProperty.getValue.asInstanceOf[MultiPolygon], year)
  }


  /**
   * Method to extract regions as multi-polygons from the shape file, takes file name for convenience.
   *
   * @param fileName the shape file name, generally witht the extension .shp
   * @param year the year that the regions are defined for
   * @return if successful, a tuple, first member is the coordinate system the regions use, second is the list of regions,
   *         otherwise its probably an IOException.
   */
  def getRegions(fileName: String, year: String): Try[(CoordinateReferenceSystem, List[Region])] = {
    getRegions(new File(fileName), year: String)
  }

  /**
   * Method to extract regions as multi-polygons from the shape file
   *
   * @param file the shape file, generally witht the extension .shp
   * @param year the year that the regions are defined for
   * @return if successful, a tuple, first member is the coordinate system the regions use, second is the list of regions,
   *         otherwise its probably an IOException.
   */
  def getRegions(file: File, year: String): Try[(CoordinateReferenceSystem, List[Region])] = {
    Try {
      // Get the content in a queryable format, the datastore is where the main information is held.
      val dataStore = FileDataStoreFinder.getDataStore(file)

      // get the coordinate system,  as noted this is needed for compatibility with other datasets
      val crs = dataStore.getSchema.getCoordinateReferenceSystem

      // get Features, TODO generalise this code to allow for arbitrary types, rather than just the first one.
      val source = dataStore.getFeatureSource(dataStore.getTypeNames()(0))
      val collection = source.getFeatures.toArray.asInstanceOf[Array[SimpleFeature]]

      // map the simple features to a class with more structure to make them easier to work with
      val found = collection.map(f => featureToRegion(f, year)).toList

      // tie off loose ends, this closes the datastore conenction.
      dataStore.dispose()

      (crs, found) //
    }
  }
}