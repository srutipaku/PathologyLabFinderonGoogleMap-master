/*
    This Class is used for Drawing Path 
    between two location on GoogleMap
    For Detail Explanation and understanding Visit Below Link
	 * http://csie-tw.blogspot.com/2009/06/android-driving-direction-route-path.html#ixzz1L6pHhyYd
 */
package pathology.finder;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Projection;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Drawingpath 
{
	public GeoPoint geoPoint1;
    public GeoPoint geoPoint2;
    public Point point1;
    public Point point2;
    public Projection projection;
    public GeoPoint gp1;  
    public GeoPoint gp2;  
    public int color;  
    public Canvas canvas;
    public Drawingpath(GeoPoint gp1, GeoPoint gp2, int color,MapView mapView)
	{
		// TODO Auto-generated constructor stub
    	this.gp1 = gp1;  
        this.gp2 = gp2;  
        this.color = color; 
        Projection projection = mapView.getProjection();
	    Paint paint = new Paint();  
	    Point point = new Point();  
	    projection.toPixels(gp1, point);  
	    paint.setColor(color);  
	    Point point2 = new Point();  
	    projection.toPixels(gp2, point2);  
	    paint.setStrokeWidth(5);  
	    paint.setAlpha(120);  
	    canvas.drawLine(point.x, point.y, point2.x, point2.y, paint);  
	}
	   
    public void draw(Canvas canvas, MapView mapView, boolean shadow)
    {
    	 Projection projection = mapView.getProjection();  
    	    Paint paint = new Paint();  
    	    Point point = new Point();  
    	    projection.toPixels(gp1, point);  
    	    paint.setColor(color);  
    	    Point point2 = new Point();  
    	    projection.toPixels(gp2, point2);  
    	    paint.setStrokeWidth(5);  
    	    paint.setAlpha(120);  
    	    canvas.drawLine(point.x, point.y, point2.x, point2.y, paint);  
    	  draw(canvas, mapView, shadow);  
    	    
    }

}
