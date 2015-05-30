Notes to accompany 1891 county polygon shapefiles for England and Wales
Prepared June 2012 to accompany data release 2

Index to README file:
- Files supplied
- Coverage Area
- Biblographic Citation
- General Notes
- Polygon Attribute descriptions

***********************************************
***** Files supplied *****
***********************************************

# README_EW1891_regcounties_v2.txt
Notes on what is contained within the files

# EW1891_regcounties.shp (county polygons for England & Wales)

# End User Licence.txt
Specific Terms and conditions of use


***********************************************
***** Coverage Area *****
***********************************************

# National coverage only


**********************************************
***** Bibliographic Citation *****
***********************************************

All works which use or refer to these materials should acknowledge this source by means of bibliographic citation. To ensure that such source attributions are captured for bibliographic indexes, citations must appear in footnotes or in the reference section of publications. The citation format below is composed of; Author, Date, Title, Publisher. The bibliographic citation for this data collection is:
 
"Great Britain Historical GIS Project (2012) 'Great Britain Historical GIS'. University of Portsmouth"

For further details please refer to the End User Licence file supplied with this download.


***********************************************
***** General Notes *****
***********************************************

# The polygons in this coverage all relate to registration counties. 

# G_UNIT
This value is a unique identifier from our gazetteer. The g_unit value can be used to identify the same unit in different years even if the name/boundary/status of the unit has changed.

# There has been some minor tidying up done to these polygons, for example merging polygons to become multi-polygons and cleaning up the names, however, there has been no large-scale changes to the original spatial data.

# This dataset uses the OSGB National Grid

# Relationships to container units have been omitted as there are frequently relationships to more than one higher-level unit.

# NAMESTATUS
Equates to "G_NAME_STATUS" in gazetteer.
As far as possible all unit names are the Preferred English name at the date given in the file name. Where this was unavailable they have been assigned their Preferred Welsh name in that year.
Where the name status is given as 'O' this indicates it was the preferred official name at this particular date, although another name later replaced it as the preferred name. 

# UNITTYPE
Equates to "G_UNIT_TYPE" in gazetteer.
The Unittype value is the administrative unit type for this unit at the date given in the file name.

# Further gazetteer information is available at:  http://www.visionofbritain.org.uk

**********************************************
***** Polygon Attribute descriptions *****
**********************************************

# G_UNIT		~ Unique identifier for unit
# G_NAME	~ Name of unit at this date
# NAMESTATUS	~ Status of name: P = Preferred or O = Official (N.B. see general notes)
# G_LANGUAGE	~ Language of name: eng = English, cym = Welsh (N.B. see general notes)
# UNITTYPE 	~ Administrative unit type of the unit: ADM_CNTY = Administrative County, PR_CNTY = Poor Law County/Registration County, ANC_CNTY = Ancient County (N.B. see general notes)
# IM_AUTH	~ Source of information