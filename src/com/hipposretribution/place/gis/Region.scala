package com.hipposretribution.place.gis

import com.hipposretribution.place.gis.tools.GeoToolsRegionLoader
import com.vividsolutions.jts.geom.Point
import com.vividsolutions.jts.geom.Geometry
import org.opengis.feature.Property
import com.hipposretribution.io.PropertiesHandler
import org.geotools.feature.simple.SimpleFeatureImpl
import com.hipposretribution.place.names.PlaceRecord

object Region {

    val fields = List("name", "lang", "regionType", "regionLevel")

    val vars = getProps()

    protected def getProps() = {
        val props = PropertiesHandler.loadProperties("./resources/config/dataset.properties")
        var variations = Map[String, Array[String]]()

        fields.foreach(f => variations += f -> props.getProperty(f).split(","))

        variations.toMap
    }

    protected def getProperty(feature : SimpleFeatureImpl, name : String) : String = {
        val variations = vars.getOrElse(name, null)

        for (i <- variations.indices) {
            val att = feature.getProperty(variations(i))
            if (att != null) {
                return att.getValue().toString()
            }
        }
        ""
    }
}

class Region(feature : SimpleFeatureImpl, geom : Geometry) {

    val name = Region.getProperty(feature, "name")
    val lang = Region.getProperty(feature, "lang")
    val level = Region.getProperty(feature, "regionLevel")
    val rType = Region.getProperty(feature, "regionType")
    
    protected var _locales = List[PlaceRecord]()
    protected var _year = 0
    
    protected val _geo = geom

    def contains(coordinate : Point) = {
        coordinate.within(_geo)
    }

    def appendLocale(record : PlaceRecord){
       _locales = _locales :+ record
    }
    
    def locales = _locales
    
    def year(yearString : String) = {
      _year = yearString.toInt
      this
    }
    
    def year = _year
    
    override def toString = name + "[language: " + lang + " tier: " + level + " type: " + rType + " year: " + _year +"]"
}