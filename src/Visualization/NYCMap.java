package Visualization;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class NYCMap extends PApplet{

	//If access to Google Map is denied, then set this value to be true
	public static final boolean GoogleAccessDeny=false;
	
	UnfoldingMap map;
	//UnfoldingMap ByPostcode;
	private List<Marker> accidentList;
	private AccidentMarker lastSelected;
	//Map<String,Integer> AccidentByPostcode;
	//List<Feature> Postcode;
	//List<Marker> PostcodeMarker;
	
	public void setup(){
		//setting the basic info of the map, the location(40.8117435,-73.9616607) is centered as Columbia University
		size(800,600,OPENGL);
		//by default we don't use the Google Map, but if we have access to that then we set the map provider as google
	    map=new UnfoldingMap(this, 0, 0, 800, 600);
	    if(!GoogleAccessDeny){
	    map=new UnfoldingMap(this, 0, 0, 800, 600,new Google.GoogleMapProvider());	
	    }
		MapUtils.createDefaultEventDispatcher(this, map);
		//setting the basic info of the map, the location(40.8117435,-73.9616607) is centered as Columbia University
		map.zoomAndPanTo(12,new Location(40.8117435,-73.9616607));	
		
		//-------the following is code for the accident print map.----------------------------------//
		List<PointFeature> features = DataReading.parseAccidents(this, "NYPD-data-part1.dat");		
		accidentList=new ArrayList<Marker>();		
		//adding point to print on our map
		for(PointFeature  feature:features){
			AccidentMarker m = new AccidentMarker(feature);
			accidentList.add(m);		
		}		
		map.addMarkers(accidentList);
				
		//-------the following is code for the postal code classify map.----------------------------------//
		//AccidentByPostcode = loadZipCSV("/Users/gbzlivint/Desktop/data/zipcode.csv");			
		//load zipcode covering area as polygons
		//Postcode = GeoJSONReader.loadData(this, "/Users/gbzlivint/Desktop/data/NYC-zipcode.geojson");
		//PostcodeMarker=MapUtils.createSimpleMarkers(Postcode);			
		//add those markers
		//ByPostcode.addMarkers(PostcodeMarker);
		//Colouring();		
	}
	
	public void draw() {	
		map.draw();	
		//ByPostcode.draw();
	}
	
	public void mouseMoved(){
		//hint(DISABLE_DEPTH_TEST);
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;	
		}
		for (Marker m : accidentList) 
		{
			AccidentMarker marker = (AccidentMarker)m;
			if (marker.isInside(map,  mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}
	}
	
    /*private void Colouring(){
	for (Marker marker : PostcodeMarker) {
		//since the raw data doesn't contain a Id field, we need to arbitarily set it by calling the "setId method"
		marker.setId(marker.getStringProperty("postalCode"));
		//then we need to find the data in the Hashmap by their keys
		String PostalCode = marker.getId();
		if (AccidentByPostcode.containsKey(PostalCode)) {
			int accidentCount = AccidentByPostcode.get(PostalCode);
			//the range of the incidents is from 1 to 88, so we set lower value to be more
			//blue and higher value to be more red
			float colourPercent=((float)accidentCount-1)/(88-1);
			//System.out.println(accidentCount+"  "+colourPercent);
			//getting the percentage of the colour and then apply them into the RGB system
			marker.setColor(color(0+(int)(255*colourPercent), 0, 255-(int)(255*colourPercent)));
		}
		else marker.setColor(color(255,255,255));
	}
}*/

/*private Map<String, Integer> loadZipCSV(String fileName) {
    Map<String, Integer> ZipCSV = new HashMap<String, Integer>();
    String[] rows = loadStrings(fileName);
    for (String row : rows) {
	    //for columns in csv file they are add together with comma
		String[] columns = row.split(",");
		ZipCSV.put(columns[0], Integer.parseInt(columns[1]));
	}
	return ZipCSV;
}*/
}
