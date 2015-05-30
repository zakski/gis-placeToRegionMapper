package com.szadowsz.place.names

import com.szadowsz.place.gis.BoundaryMap
import org.geotools.geometry.GeometryBuilder
import org.geotools.referencing.crs.DefaultGeographicCRS
import org.geotools.geometry.jts.JTS

class PlaceRecord(theName: String, theType: String, theLong: Float, theLat: Float, maps: List[BoundaryMap]) {

  val name = theName

  val featureType = theType
  
  val coord = JTS.toGeometry(new GeometryBuilder(DefaultGeographicCRS.WGS84).createDirectPosition(Array(theLong, theLat)))
  
  val regions = getRegions(maps)

  
  private def getRegions(maps: List[BoundaryMap])= {
    val regs = maps.map(map => map.getRegionForLocation(coord).getOrElse(null)).filter(reg => reg != null)
    regs.foreach(reg => reg.appendLocale(this))
    regs
  }
  
  
  def toShortFormString = {
    val builder = new StringBuilder()

    builder.append(name + "|")
    builder.append(coord.getX() + "|")
    builder.append(coord.getY() + "|")
    builder.append(featureType + "|")

    builder.toString
  }

  def toShortestFormString = {
    val builder = new StringBuilder()

    builder.append(name + "|")
    builder.append(featureType + "|")

    builder.toString
  }

}