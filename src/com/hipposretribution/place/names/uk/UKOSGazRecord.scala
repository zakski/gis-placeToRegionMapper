
/**
 * Field number 	Field name 			Full name 			Format 		Example
 *   1 			    SEQ 		Sequence number 	 	Int (6) 	 86415
 *   2 				KM_REF  	Kilometre reference 	Char (6) 	 ST5265
 *   3 				DEF_NAM 	Definitive name 		Char (60) 	 Felton
 *   4 				TILE_REF 	Tile reference 			Char (4) 	 ST46
 *   5 				LAT_DEG 	Latitude degrees 		Int (2) 	 51
 *   6					LAT_MIN 	Latitude minutes 		Float (3.1)  23.1
 *   7 				LONG_DEG 	Longitude degrees 		Int (2) 	 2
 *   8 				LONG_MIN 	Longitude minutes 		Float (3.1)  41
 *   9 				NORTH 		Northings 				Int (7) 	 165500
 *  10 				EAST 		Eastings 				Int (7)		 352500
 *  11 				GMT 		Greenwich Meridian 		Char (1) 	 W
 *  12 				CO_CODE 	County code 			Char (2) 	 NS
 *  13 				COUNTY 		County name 			Char (20) 	 N Som
 *  14 				FULL_COUNTY Full county name 		Char (60) 	 North Somerset
 *  15 				F_CODE 		Feature code 			Char (3) 	 O
 *  16 				E_DATE 		Edit date 				Char (11) 	 01-MAR-1993
 *  17 				UPDATE_CO 	Update code 			Char (1) 	 l
 *  18 				SHEET_1 	Primary sheet no 		Int (3) 	 172
 *  19				    SHEET_2 	Second sheet no 		Int (3)      182
 *  20 				SHEET_3 	Third sheet no 			Int (3)      0
 */
package com.hipposretribution.place.names.uk;

import java.text.SimpleDateFormat
import com.hipposretribution.place.names.PlaceRecord
import com.hipposretribution.place.gis.BoundaryMap
import com.hipposretribution.place.gis.Region

object UKOSGazRecord {

  /**
   * Method to convert Ordnance Survey Data Longitude W/E +longitude degrees minutes to +/- longitude to make it compatible with 2D WGS 84 Datum
   */
  protected def getLong(gmt: String, degrees: String, minutes: String) = (if (gmt.charAt(0) == 'W') -1 else 1) * (degrees.toInt + minutes.toFloat / 60.0f)

  protected def getLat(degrees: String, minutes: String) = degrees.toInt + minutes.toFloat / 60.0f
}

class UKOSGazRecord(fields: Array[String]) extends PlaceRecord(fields(2), fields(14), UKOSGazRecord.getLong(fields(10), fields(6), fields(7)), UKOSGazRecord.getLat(fields(4), fields(5))) {

  val seq = fields(0).toInt
  val km_ref = fields(1)
  val title_ref = fields(3)
  val gmt = fields(10).charAt(0)
  val north = fields(8).toInt
  val east = fields(9).toInt
  val co_code = fields(11)
  val county = fields(12)
  val full_county = fields(13)
  val e_date = (new SimpleDateFormat("dd-MMM-yyyy")).parse(fields(15))
  val update_code = fields(16).charAt(0)
  val sheet = (fields(17).toInt, fields(18).toInt, fields(19).toInt)
  var regions = List[Region]()

  def this(record: String) = this(record.split(":")) //secondary constructor

  def setRegions(maps: List[BoundaryMap]) {
    maps.foreach(map => regions = regions :+ map.getRegionForLocation(coord))
    regions = regions.filter(_ != null)
  }

  override def toString = {
    var builder = new StringBuilder()

    builder.append(name + "|")
    builder.append(km_ref + "|")
    builder.append(title_ref + "|")
    builder.append(coord.getX() + "|")
    builder.append(coord.getY() + "|")
    builder.append(north + "|")
    builder.append(east + "|")
    builder.append(gmt + "|")
    builder.append(co_code + "|")
    builder.append(county + "|")
    builder.append(full_county.toString + "|")
    builder.append(regions.mkString("( ", ", ", ")|"))
    builder.append(featureType + "|")
    builder.append(e_date + "|")
    builder.append(update_code + "|")
    builder.append(sheet)

    builder.toString
  }

  override def toShortFormString = {
    var builder = new StringBuilder()

    builder.append(name + "|")
    builder.append(coord.getX() + "|")
    builder.append(coord.getY() + "|")
    builder.append(regions.mkString("( ", ", ", ")|"))
    builder.append(featureType + "|")

    builder.toString
  }

  override def toShortestFormString = {
    var builder = new StringBuilder()

    builder.append(name + "|")
    builder.append(regions.mkString("( ", ", ", ")|"))

    builder.toString
  }
}