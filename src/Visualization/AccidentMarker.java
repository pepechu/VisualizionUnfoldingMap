package Visualization;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;
import processing.core.PConstants;
import processing.core.PApplet;

public class AccidentMarker extends SimplePointMarker{

	public AccidentMarker(Feature Accident) {
		super(((PointFeature)Accident).getLocation(), Accident.getProperties());
		// TODO Auto-generated constructor stub
	}
	
	public void draw(PGraphics pg, float x, float y) {
		// If we don't need to check the data when our mouse reaches the point, just drawMaker() is enough
		drawMarker(pg,x,y);
		if(selected){
			showTitle(pg,x,y);
		}
		if(selected){
			showTitle(pg,x,y);
		}
		if(selected){
			showTitle(pg,x,y);
		}
		if(selected){
			showTitle(pg,x,y);
		}
	}

	public void drawMarker(PGraphics pg, float x, float y) {
		//setting accident point without casulties to light grey and with deaths into red
		pg.fill(150,150,150);
		//pg.noFill();
		pg.noStroke();
		int death=Integer.parseInt(getStringProperty("death"));
		// if there is death we make a triangle marker
		if (death>=1){
			pg.fill(255,0,0);
			pg.triangle(x, y-10, (float)(x+5*Math.sqrt(3)), y+5, (float)(x-5*Math.sqrt(3)), y+5);
		}
		else pg.ellipse(x, y, 5, 5);
	}

	public void showTitle(PGraphics pg, float x, float y) {
		// TODO Auto-generated method stub
		//adding data to show if mouse is inside the marker
		String date = "Date: "+getStringProperty("date");
		String time = "Time: "+getStringProperty("time");
		String street= "Street: "+getStringProperty("street");
		String cause ="Cause: "+getStringProperty("cause");
		String death="Deaths: "+getStringProperty("death");
		pg.fill(240, 240, 240);
		pg.textSize(12);
		//draw a rectangle to put data on it then show them to users
		pg.rect(x, y + 15, Math.max(Math.max(Math.max(Math.max(pg.textWidth(date),pg.textWidth(time)),pg.textWidth(street)),pg.textWidth(cause)),pg.textWidth(death)) +6, 78, 10);	
		//putting texts onto the pop up window
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.fill(0);
		pg.text(date, x+3, y+18);
		pg.text(time, x+3, y+32);
		pg.text(street, x+3, y+46);
		pg.text(cause, x+3, y+60);
		pg.text(death, x+3, y+74);
	}
}
