package com.szadowsz.place.gis;

import com.szadowsz.place.gis.BoundaryMap;
import com.szadowsz.place.gis.Region;
import com.szadowsz.place.gis.tools.GeoToolsMapLoader;
import com.vividsolutions.jts.geom.Point;
import junit.framework.TestCase;
import org.geotools.geometry.GeometryBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.geometry.jts.JTS;
import scala.Option;
import scala.collection.Iterator;
import scala.collection.immutable.List;

import java.io.File;

/**
 * @author Zakski : 30/05/2015.
 */
public class BoundaryMapTestCase extends TestCase {

    private static final String PATH = "." + File.separator + "src" + File.separator + "test" + File.separator + "resources";
    private static final String PATH_VGB = PATH + File.separator + "boundaries" + File.separator + "uk" + File.separator + "visionOfGB";
    private static final String PATH_1890 = PATH_VGB + File.separator + "1890";


    /*
* Kinross(shire),Clackmannan(shire), Haddington/East Lothian, Linlithgow/West Lothian, Edinburgh/Midlothian,
* Renfrew(shire), Bute(shire), Lanark(shire), Berwick(shire), Ayr(shire), Peebles(shire),Roxburgh(shire),
* Selkirk(shire), Dumfries(shire), Kirkcudbright(shire), Aberdeen(shire), Shetland, Orkney, Sutherland,
* Inverness(shire) Elgin(shire)/Moray, Banff(shire), Wigtown(shire),Nairn(shire), Argyll, Kincardine(shire), Forfar(shire),
* Perth(shire), Fife, Dumbarton(shire), Ross(shire), Cromarty(shire) ,Caithness, Stirling.
*
* 34 total, however in this dataset Ross and Cromarty are joined so we are looking for 33.
*
 */
    public void testRegionsCount() {
        List<BoundaryMap> maps = GeoToolsMapLoader.getMapsRecursively(PATH_1890);
        assertEquals(1, maps.length());

        BoundaryMap scot_map = maps.head();
        assertEquals(33, scot_map.size());
    }

    public void testGetRegionsByName_NotFound() {
        List<BoundaryMap> maps = GeoToolsMapLoader.getMapsRecursively(PATH_1890);
        assertEquals(1, maps.length());

        BoundaryMap scot_map = maps.head();
        assertTrue(scot_map.getRegionByName("Dublin").isEmpty());
    }

    public void testGetRegionsByName_Found() {
        List<BoundaryMap> maps = GeoToolsMapLoader.getMapsRecursively(PATH_1890);
        assertEquals(1, maps.length());

        BoundaryMap scot_map = maps.head();
        assertTrue(scot_map.getRegionByName("Shetland").isDefined());
    }

    public void testGetRegionsByPoint_OutOfRange() {
        List<BoundaryMap> maps = GeoToolsMapLoader.getMapsRecursively(PATH_1890);
        assertEquals(1, maps.length());

        BoundaryMap scot_map = maps.head();

        double[] point = {-77.376709,25.025884}; // Nassau
        Point coord = JTS.toGeometry(new GeometryBuilder(DefaultGeographicCRS.WGS84).createDirectPosition(point));

        assertTrue(scot_map.getRegionForLocation(coord).isEmpty());
    }

    public void testGetRegionsByPoint_NotFound() {
        List<BoundaryMap> maps = GeoToolsMapLoader.getMapsRecursively(PATH_1890);
        assertEquals(1, maps.length());

        BoundaryMap scot_map = maps.head();
        double[] point = {-8.261719, 53.435719}; // Ireland
        Point coord = JTS.toGeometry(new GeometryBuilder(DefaultGeographicCRS.WGS84).createDirectPosition(point));

        assertTrue(scot_map.getRegionForLocation(coord).isEmpty());
    }

    public void testGetRegionsByPoint_Found() {
        List<BoundaryMap> maps = GeoToolsMapLoader.getMapsRecursively(PATH_1890);
        assertEquals(1, maps.length());

        BoundaryMap scot_map = maps.head();

        double[] point = {-3.185, 55.95};
        Point coord = JTS.toGeometry(new GeometryBuilder(DefaultGeographicCRS.WGS84).createDirectPosition(point));

        Option<Region> region = scot_map.getRegionForLocation(coord);
        assertTrue(region.isDefined());
    }
}
