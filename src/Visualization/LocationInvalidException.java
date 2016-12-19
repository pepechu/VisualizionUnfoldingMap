package Visualization;

public class LocationInvalidException extends Exception{

	public int line;
	public LocationInvalidException(int badline) {
		// TODO Auto-generated constructor stub
		super("Locaton data is missing in Line: "+badline);
		line=badline;
	}
}
