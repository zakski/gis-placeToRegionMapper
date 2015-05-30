package com.szadowsz.place

import java.io.File

import com.szadowsz.place.gis.tools.GeoToolsMapLoader
import com.szadowsz.place.names.{UKOSGazRecord, PlaceRecord}

/**
 * @author Zakski : 29/05/2015.
 */
class GISTestUtil {

//  private def loadPlaces(fileSourcePath: String, encoding: String) = {
//    val read = new FReader(fileSourcePath, encoding)
//    val buffer = Buffer[PlaceRecord]()
//    read.init()
//
//    var line = read.readLine()
//    while (line != null) {
//      val gaz = new UKOSGazRecord(line)
//
//      if (gaz.featureType == "O") {
//        buffer += gaz
//      }
//
//      line = read.readLine()
//    }
//
//    read.close()
//    buffer.toList
//  }
}