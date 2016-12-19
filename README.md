# VisualizionUnfoldingMap
This is for the final project of Columbia University EECS 6863 Big Data Analytics course in Fall 2016.

###The visualization part is based on UnfoldingMap and Processing packages and it is shown as a Java Applet.
* Unfolding Map doc: http://unfoldingmaps.org/
* We imported a UCSD version from the Coursera open source class from: https://www.coursera.org/learn/object-oriented-java/home/welcome
* Processing (including PApplet, OpenGL map display): http://processing.github.io/processing-javadocs/core/ 
https://processing.org/reference/

###Accident Draw
The NYCMap.java gives an visualization of accidents happenend in NYC by their coordinates

This following in line 22 gives user to select the map source
```
	public static final boolean GoogleAccessDeny=false;
```
If user face internet connection problem or access denied by Google for too many auto-generated trials (a possible error message shown as follows), he could change the value into true to avoid using the provider and getting exception.
```
	the file "http://mt1.google.com/vt/lyrs=m@116&hl=de&x=1209&y=1540&z=12&s=Galileo" is missing or 
    inaccessible, make sure the URL is valid or that the file has been added to your sketch and is
    readable.
```

###Frequency by ZipCode/Borough
The FreqByArea.java gives a visualization for the frequency count classified by Zipcode (left map) and Borough (right map), and the more red in the area means accident happens more frequently, and the more blue it is, that mean accidents happened less frequently.
