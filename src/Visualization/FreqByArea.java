package Visualization;

import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.providers.Google.*;

import java.util.List;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PApplet;

public class FreqByArea extends PApplet{
	UnfoldingMap map;
	UnfoldingMap map2;
	Map<String,Integer> AccidentByPostcode;
	Map<String,Integer> AccidentByBorough;
	List<Feature> FeatureByPostcode;
	List<Feature> FeatureByBorough;
	List<Marker> PostcodeMarker;
	List<Marker> BoroughMarker;
	
	public void setup(){
		size(1200, 450, OPENGL);
		map = new UnfoldingMap(this, 0, 0, 600, 450);
		map2 = new UnfoldingMap(this, 600, 0, 600, 450);
		MapUtils.createDefaultEventDispatcher(this, map);
		MapUtils.createDefaultEventDispatcher(this, map2);
		map.zoomAndPanTo(10,new Location(40.7454838,-73.9536799));
		map2.zoomAndPanTo(10,new Location(40.7454838,-73.9536799));
		
		//------left map with post code-----------//
		//load data
		AccidentByPostcode=CSVRead("zipcode.csv");	
		//load zipcode covering area as polygons
		FeatureByPostcode=GeoJSONReader.loadData(this, "NYC-zipcode.geojson");
		PostcodeMarker=MapUtils.createSimpleMarkers(FeatureByPostcode);	
		//add and print those markers
		map.addMarkers(PostcodeMarker);
		ColouringPostcode();
		
		//------right map with borough-----------//
		//load data
		AccidentByBorough=CSVRead("Borough.csv");
		//load borough covering area as polygons
		FeatureByBorough=GeoJSONReader.loadData(this, "NYC-borough.geojson");
		BoroughMarker=MapUtils.createSimpleMarkers(FeatureByBorough);
		//add and print those markers
		map2.addMarkers(BoroughMarker);
		ColouringBorough();
	}
		
	public void draw(){
		map.draw();
		map2.draw();
	}	
		
	private Map<String, Integer> CSVRead(String fileName) {
	    Map<String, Integer> CSV = new HashMap<String, Integer>();
	    String[] rows = loadStrings(fileName);
	    for (String row : rows) {
		    //for columns in csv file they are add together with comma
			String[] columns = row.split(",");
			CSV.put(columns[0], Integer.parseInt(columns[1]));
		}
		return CSV;
	}

    private void ColouringBorough(){
    	for (Marker marker : BoroughMarker) {
    		//since the raw data doesn't contain a Id field, we need to arbitarily set it by calling the "setId method"
			marker.setId(marker.getStringProperty("borough"));
			//then we need to find the data in the Hashmap by their keys
			String BoroughCode = marker.getId();
			if (AccidentByBorough.containsKey(BoroughCode)) {
				int accidentCount = AccidentByBorough.get(BoroughCode);
				//the range of the incidents is from 221 to 1532, so we set lower value to be more
				//blue and higher value to be more red
				float colourPercent=((float)accidentCount-221)/(1532-221);
				//System.out.println(accidentCount+"  "+colourPercent);
				//getting the percentage of the colour and then apply them into the RGB system
				marker.setColor(color(0+(int)(255*colourPercent), 0, 255-(int)(255*colourPercent)));
			}
			else marker.setColor(color(255,255,255));
		}
    }
	
    private void ColouringPostcode(){
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
    }
}