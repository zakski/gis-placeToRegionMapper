package com.szadowsz.place.gis.tools;

import com.szadowsz.place.gis.BoundaryMap;
import com.szadowsz.place.gis.Region;
import junit.framework.TestCase;
import scala.collection.Iterator;
import scala.collection.immutable.List;

import java.io.File;

/**
 * @author Zakski : 29/05/2015.
 */
public class GeoToolsMapLoaderTestCase extends TestCase {

    private static final String PATH = "." + File.separator + "src" + File.separator + "test" + File.separator + "resources";
    private static final String PATH_VGB = PATH + File.separator + "boundaries" + File.separator + "uk" + File.separator + "visionOfGB";
    private static final String PATH_1890 = PATH_VGB + File.separator + "1890";
    private static final String PATH_SCOTS_1890 = PATH_1890 + File.separator + "Spre1890_scocounties";

    public void testGetMapsFromFolder_Empty() {
        assertEquals(0, GeoToolsMapLoader.getMapsFromFolder(PATH).length());
    }

    public void testGetMapsFromFolder_One() {
        assertEquals(1, GeoToolsMapLoader.getMapsFromFolder(PATH_SCOTS_1890).length());
    }

    public void testGetMapsRecursively_One() {
        assertEquals(1, GeoToolsMapLoader.getMapsRecursively(PATH_1890).length());
    }

    public void testGetMapsRecursively_All() {
        assertEquals(16, GeoToolsMapLoader.getMapsRecursively(PATH).length());
    }
}
