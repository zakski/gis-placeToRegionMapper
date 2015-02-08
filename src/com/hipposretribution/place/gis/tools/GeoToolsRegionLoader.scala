package com.hipposretribution.place.gis.tools

import java.io.File
import org.geotools.data.DataStoreFinder
import collection.JavaConversions._
import org.opengis.feature.Feature
import com.hipposretribution.place.gis.Region
import org.geotools.data.FileDataStoreFinder
import com.vividsolutions.jts.geom.MultiPolygon
import org.geotools.feature.simple.SimpleFeatureImpl
import com.hipposretribution.place.gis.BoundaryMap
import org.opengis.referencing.crs.CoordinateReferenceSystem
import org.geotools.referencing.CRS
import org.opengis.feature.simple.SimpleFeature

/**
 * Convenience Object to help extract regions of interest from a GIS file containing multi-polygons. In particular we want to open .shp files, also
 *  known as shapefiles, which is the main file of Esri format GIS projects.
 *
 * FROM WIKIPEDIA
 * The shapefile format is a popular geospatial vector data format for geographic information system (GIS) software. It
 * is developed and regulated by Esri as a (mostly) open specification for data interoperability among Esri and other GIS software products.
 * The shapefile format can spatially describe vector features: points, lines, and polygons, representing, for example, water wells, rivers,
 * and lakes. Each item usually has attributes that describe it, such as name or temperature.
 */
object GeoToolsRegionLoader {

  private def featureToRegion(feature: SimpleFeature, year : String) = {
    new Region(feature, feature.getDefaultGeometryProperty().getValue().asInstanceOf[MultiPolygon], year)
  }

  // convenience method if all you have is a string of the file path
  def getRegions(fileName: String, year : String): Tuple2[CoordinateReferenceSystem, List[com.hipposretribution.place.gis.Region]] = {
    getRegions(new File(fileName), year : String)
  }

  // method to extract regions as multi-polygons from the shape file
  def getRegions(file: File, year : String): Tuple2[CoordinateReferenceSystem, List[com.hipposretribution.place.gis.Region]] = {

    val dataStore = FileDataStoreFinder.getDataStore(file) // Get the content in a queryable format
    val crs = dataStore.getSchema().getCoordinateReferenceSystem() // get the coordinate system, needed when working with different datasets

    val source = dataStore.getFeatureSource(dataStore.getTypeNames()(0))
    val collection = source.getFeatures().toArray().asInstanceOf[Array[SimpleFeature]]

    // map the simple features to a class with more structure to make them easier to work with
    val found = collection.map(f => featureToRegion(f, year)).toList 

    dataStore.dispose()

    (crs, found)
  }
}