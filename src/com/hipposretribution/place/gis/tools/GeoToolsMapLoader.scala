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
import com.hipposretribution.io.find.FileFinder
import com.hipposretribution.io.find.filters.ExtensionRFilter
import java.io.FilenameFilter
import com.hipposretribution.io.find.filters.ExtensionFilter

object GeoToolsMapLoader {

    val filter = new ExtensionFilter(".shp")
    val filterR = new ExtensionRFilter(".shp")

    def getMapsRFromFile(dirName : String) = {
        getMaps(new File(dirName), filterR)
    }

    def getMapsFromFile(dirName : String) = {
        getMaps(new File(dirName), filter)
    }

    def getMapsRFromFile(dir : File) = {
        getMaps(dir, filterR)
    }

    def getMapsFromFile(dir : File) = {
        getMaps(dir, filter)
    }

    protected def getMaps(dir : File, fileFilter : FilenameFilter) = {
        val files = FileFinder.getFiles(dir, filterR)
        files.map(f => new BoundaryMap(f)).toList
    }
}