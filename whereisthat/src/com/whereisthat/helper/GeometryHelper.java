package com.whereisthat.helper;

import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;

public abstract class GeometryHelper {

	public static Polygon createCircle(Point from, double distance)
	{		
		Polygon _circleTemp = new Polygon();

        Point _startPoint = from;            

        int pointsAroundCircle = 50; 
        double radius = distance;
        for (int i = 0; i < pointsAroundCircle; i++) {
            double fi = 2 * Math.PI * i / pointsAroundCircle;
            double x = radius * Math.sin(fi + Math.PI) + _startPoint.getX();
            double y = radius * Math.cos(fi + Math.PI) + _startPoint.getY();
            if (i == 0) 
                _circleTemp.startPath(x, y);
            else if (i == pointsAroundCircle - 1) 
                _circleTemp.closeAllPaths();
            else
                _circleTemp.lineTo(x, y);
        }

        return _circleTemp;
	}	
	
	public static double distance(Point from, Point to)
	{	    
        Polyline polyline = new Polyline();
        polyline.startPath(from.getX(), from.getY());
        polyline.lineTo((float) to.getX(), (float) to.getY());        
        return polyline.calculateLength2D();
	}
}
