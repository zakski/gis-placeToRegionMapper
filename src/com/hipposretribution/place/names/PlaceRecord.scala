package com.hipposretribution.place.names

import org.geotools.geometry.GeometryBuilder
import org.geotools.referencing.crs.DefaultGeographicCRS
import org.geotools.geometry.jts.JTS

class PlaceRecord(theName: String, theType: String, theLong: Float, theLat: Float) {

  val name = theName

  val featureType = theType

  val coord = {
    val builder = new GeometryBuilder(DefaultGeographicCRS.WGS84);
    JTS.toGeometry(builder.createDirectPosition(Array(theLong, theLat)))
  }

  def toShortFormString = {
    var builder = new StringBuilder()

    builder.append(name + "|")
    builder.append(coord.getX() + "|")
    builder.append(coord.getY() + "|")
    builder.append(featureType + "|")

    builder.toString
  }

  def toShortestFormString = {
    var builder = new StringBuilder()

    builder.append(name + "|")
    builder.append(featureType + "|")

    builder.toString
  }

}