package Visualization;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PApplet;

public class DataReading {

	public static List<PointFeature> parseAccidents(PApplet p, String fileName){
		List<PointFeature> features = new ArrayList<PointFeature>();	
		String[] rows = p.loadStrings(fileName);
		for (String row : rows) {
            //use a try-catch clause in case of null entry and in this way we could skip those entries without a location
			//which is meaning less for drawing on a real map.
			try{				
			// split row by commas not in quotations
			String[] columns = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");			
			// get location and create feature
			//System.out.println(columns[11]);
			float lat = Float.parseFloat(columns[4]);
			float lon = Float.parseFloat(columns[5]);			
			Location loc = new Location(lat, lon);
			PointFeature point = new PointFeature(loc);			
			//the id of our data set is in column "X"
			point.setId(columns[23]);			
			// get other fields from csv
			point.putProperty("date", columns[0]);
			point.putProperty("time", columns[1]);
			point.putProperty("street", columns[7]);
			//number of people killed is in column "L"
			point.putProperty("death",columns[11]);
			point.putProperty("cause", columns[18]);
			features.add(point);
			}
			
			//if we meet an empty entry then line 26 would give an Exception on the empty entry converting to float
			//then we output the id of data which has an empty location
			catch(NumberFormatException e1){
				String[] columns = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				System.err.println("Locaton data is missing in Line with ID equals: "+columns[23]);
				continue;
				}
		}

		return features;
	}

}
