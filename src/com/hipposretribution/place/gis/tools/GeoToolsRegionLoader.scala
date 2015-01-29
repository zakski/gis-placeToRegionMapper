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

object GeoToolsRegionLoader {
    def getRegions(fileName : String) : Tuple2[CoordinateReferenceSystem, List[com.hipposretribution.place.gis.Region]] = {
      getRegions(new File(fileName))
    }
    
    def getRegions(file: File) : Tuple2[CoordinateReferenceSystem, List[com.hipposretribution.place.gis.Region]] = {
        var found = List[Region]()

        val dataStore = FileDataStoreFinder.getDataStore(file)
        val crs = dataStore.getSchema().getCoordinateReferenceSystem()
        val source = dataStore.getFeatureSource(dataStore.getTypeNames()(0))
        
        val collection = source.getFeatures()
        val iterator = collection.features()

        while (iterator.hasNext()) {
            var feature = iterator.next().asInstanceOf[SimpleFeatureImpl]
            var geom = feature.getDefaultGeometryProperty().getValue().asInstanceOf[MultiPolygon]
            found = found :+ (new Region(feature, geom))
        }
        iterator.close()
        dataStore.dispose()
       
        (crs,found)
    }
}