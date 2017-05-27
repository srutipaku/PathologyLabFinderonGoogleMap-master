/*
   This is a Map Activity 
   and used for displaying 
   1.GoogleMap with Zoom Controls
   2.Markers
   and contains options menu for 
   1.Searching location
   2.Getting Direction on Map
 */

package pathology.finder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class Displaymap extends MapActivity 
{
	public static MapView mapView;
	private static final int latitudeE6 = 37985339;
	private static final int longitudeE6 = 23716735;
	
	GeoPoint mypoint1; // my location GeoPoint
	GeoPoint searchp1; // search location GeoPoint
	Uri uri;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapView = (MapView) findViewById(R.id.map_view);
        Displaymap g=new Displaymap();
        g.setMapView(mapView);
        mapView.setBuiltInZoomControls(true);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.bubble);
		CustomItemizedOverlay itemizedOverlay = new CustomItemizedOverlay(drawable, this,mapView);
		
		/*for pathology , Pathology lab locations
		  GeoPoints which contain latitude and longitude values 
		  which are used for placing markers on GoogleMap 
		 */
		GeoPoint somepoint = new GeoPoint(latitudeE6, longitudeE6);
		GeoPoint point1 = new GeoPoint((int)(17.3915*1e6),(int)(78.4972*1e6));
		OverlayItem overlayitem1 = new OverlayItem(point1, "Pathology Lab 1,Barkatpura", "What to View the Tests!");
		
		GeoPoint point2 = new GeoPoint((int)(17.3904*1e6),(int)(78.4879*1e6));
		OverlayItem overlayitem2 = new OverlayItem(point2, "Pathology Lab 2,RamKoti", "What to View the Tests!");
		
		GeoPoint point3 = new GeoPoint((int)(17.3953*1e6),(int)(78.4903*1e6));
		OverlayItem overlayitem3 = new OverlayItem(point3, "Pathology Lab 3,Ymca X Road", "What to View the Tests!");
		
		GeoPoint point4 = new GeoPoint((int)(17.4786*1e6),(int)(78.5312*1e6));
		OverlayItem overlayitem4 = new OverlayItem(point4, "Pathology Lab 4,Secendrabad", "What to View the Tests!");
		
		GeoPoint point5 = new GeoPoint((int)(17.4376*1e6),(int)(78.4901*1e6));
		OverlayItem overlayitem5 = new OverlayItem(point5, "Pathology Lab 5,Secendrabad general bazzar", "What to View the Tests!");
		
		GeoPoint point6 = new GeoPoint((int)(17.4362*1e6),(int)(78.4884*1e6));
		OverlayItem overlayitem6 = new OverlayItem(point6, "Pathology Lab 6,Secendrabad Clock tower", "What to View the Tests!");
		
		GeoPoint point7 = new GeoPoint((int)(17.3986*1e6),(int)(78.4325*1e6));
		OverlayItem overlayitem7 = new OverlayItem(point7, "Pathology Lab 1,Mehdipatnam", "What to View the Tests!");
		
		GeoPoint point8 = new GeoPoint((int)(17.3989*1e6),(int)(78.4161*1e6));
		OverlayItem overlayitem8 = new OverlayItem(point8, "Pathology Lab 1,Tolichowki", "What to View the Tests!");
		/*
		GeoPoint point9 = new GeoPoint(latitudeE6, longitudeE6);
		OverlayItem overlayitem9 = new OverlayItem(point9, "Pathology Lab 1,barkatpura", "What to View the Tests!");
		*/
		itemizedOverlay.addOverlay(overlayitem1);
		itemizedOverlay.addOverlay(overlayitem2);
		itemizedOverlay.addOverlay(overlayitem3);
		itemizedOverlay.addOverlay(overlayitem4);
		itemizedOverlay.addOverlay(overlayitem5);
		itemizedOverlay.addOverlay(overlayitem6);
	    itemizedOverlay.addOverlay(overlayitem7);
	    itemizedOverlay.addOverlay(overlayitem8);
	 //   itemizedOverlay.addOverlay(overlayitem9);
		mapOverlays.add(itemizedOverlay);
		
		MapController mapController = mapView.getController();
		
		// changing start location
		mapController.animateTo(somepoint);
		mapController.zoomToSpan(itemizedOverlay.getLatSpanE6(), itemizedOverlay.getLonSpanE6());
		mapController.setCenter(somepoint);
   
    }

    /*
     * Method for Displaying Options Menu 
     * when clicked on Menu Button on Android Device
     */
    public boolean onCreateOptionsMenu(Menu menu)
    {
    
        menu.add(0,1,0,"Search Location");
        menu.add(0,2,0,"Get Direction");
    	
   	return super.onCreateOptionsMenu(menu);
    }
    /*
       Method Invoked when a MenuItem is Selected from Options Menu
     */
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	
    	switch (item.getItemId()) {
		case 1:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Location Name");
			builder.setMessage("Enter the location to search");
	      final EditText searchtxt=new EditText(this);
	       builder.setView(searchtxt);
		      builder.setCancelable(false);
		       builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               /*
		               Using GeoCoder so that when location name is given 
		               then using that location name 
		               it gets latitude and longitude values
		                */
		        	 
		        	 Locale loc=new Locale("hi_IN");// here "hi_IN" is the code for Indian Country
		        	   Geocoder geocoder =new Geocoder(getApplicationContext(), loc);
		        	  GeoPoint searchp;
		        	   String stext=searchtxt.getEditableText().toString();
		        	   try {
						List<Address> foundaddress=geocoder.getFromLocationName(stext,2);
						if(foundaddress.size()>0)
						{
							searchp=new GeoPoint((int)(foundaddress.get(0).getLatitude()*1E6),
									       (int)(foundaddress.get(0).getLongitude()*1E6));
							searchp1=searchp;
							MapController mapController = mapView.getController();
							mapController.animateTo(searchp);
							mapView.setSatellite(false);
							mapController.setCenter(searchp);
						}
					} catch (IOException e) {
						
						e.printStackTrace();
					} 
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
			break;
		
		case 2:
			/*
			 * drawing path between two Locations
			 */
			
			AlertDialog.Builder displayalert=new AlertDialog.Builder(this);
			displayalert.setTitle("Drawing path");
			// creating linear layout and adding EditText and TextView to it
			LinearLayout ll=new LinearLayout(this);
			ll.setOrientation(LinearLayout.VERTICAL);
			// creating TextView
			final TextView source_name=new TextView(this);
			source_name.setText("Enter Source Location");
			ll.addView(source_name);
			// creating EditText
			final EditText source_location=new EditText(this);
			ll.addView(source_location);
			// creating TextView
			final TextView destination_name=new TextView(this);
			destination_name.setText("Enter Destination Location");
			ll.addView(destination_name);
			// creating EditText
			final EditText destination_location=new EditText(this);
			ll.addView(destination_location);
			// setting the linear layout to alert object
			displayalert.setView(ll);
			
			displayalert.setPositiveButton("Get Path",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface arg0, int arg1) 
				{
					/*
		               Using GeoCoder so that when location name is given 
		               then using that location name 
		               it gets latitude and longitude values
		                */
				
					Locale loc=new Locale("hi_IN");// here "hi_IN" is the code for Indian Country
		        	   Geocoder geocoder =new Geocoder(getApplicationContext(), loc);
		        	  GeoPoint startgp = null;
		        	  GeoPoint endgp=null;
		        	   String source_n=source_location.getEditableText().toString();
		        	   String des_n=destination_location.getEditableText().toString();
		        	   try {
						List<Address> foundsource=geocoder.getFromLocationName(source_n,2);
						if(foundsource.size()>0)
						{
							// Getting Source latitude and longitude values
							startgp=new GeoPoint((int)(foundsource.get(0).getLatitude()*1E6),
									       (int)(foundsource.get(0).getLongitude()*1E6));	
						}
						List<Address> founddestination=geocoder.getFromLocationName(des_n,2);
						if(founddestination.size()>0)
						{
							// Getting Destination latitude and longitude values
							endgp=new GeoPoint((int)(founddestination.get(0).getLatitude()*1E6),
									       (int)(founddestination.get(0).getLongitude()*1E6));	
						}
	/*
	 * calling DrawPath Method to Draw path between two location and display on Google Map
	 */
						DrawPath1(startgp, endgp, Color.GREEN, mapView);

						mapView.getController().animateTo(startgp);
						mapView.getController().setZoom(10);
						
				}
		        	   catch (IOException e) 
		        	    {
						
							e.printStackTrace();
						} 
			}
			}); // closing positive button of alert dialog
			
			displayalert.show();
			break;
			
		default:
			break;
		}
    	return true;
    }
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	/*
	 * Drawing path between two Locations
	 * For Detail Explanation and understanding Visit Below Link
	 * http://csie-tw.blogspot.com/2009/06/android-driving-direction-route-path.html#ixzz1L6pHhyYd
	 */
	
	public void DrawPath1(GeoPoint src,GeoPoint dest, int color, MapView mMapView01)
	{
	// connect to map web service
	StringBuilder urlString = new StringBuilder();
	urlString.append("http://maps.google.com/maps?f=d&hl=en");
	urlString.append("&saddr=");//from (source point)
	urlString.append( Double.toString((double)src.getLatitudeE6()/1.0E6 ));
	urlString.append(",");
	urlString.append( Double.toString((double)src.getLongitudeE6()/1.0E6 ));
	urlString.append("&daddr=");//to (Destination Point)
	urlString.append( Double.toString((double)dest.getLatitudeE6()/1.0E6 ));
	urlString.append(",");
	urlString.append( Double.toString((double)dest.getLongitudeE6()/1.0E6 ));
	urlString.append("&ie=UTF8&0&om=0&output=kml");
	Log.d("xxx","URL="+urlString.toString());
	// get the KML (XML) doc. And parse it to get the coordinates(direction route).
	Document doc = null;
	HttpURLConnection urlConnection= null;
	URL url = null;
	try
	{ 
	url = new URL(urlString.toString());
	urlConnection=(HttpURLConnection)url.openConnection();
	urlConnection.setRequestMethod("GET");
	urlConnection.setDoOutput(true);
	urlConnection.setDoInput(true);
	urlConnection.connect(); 

	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = dbf.newDocumentBuilder();
	doc = db.parse(urlConnection.getInputStream()); 

	if(doc.getElementsByTagName("GeometryCollection").getLength()>0)
	{
	//String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getNodeName();
	String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getFirstChild().getNodeValue() ;
	Log.d("xxx","path="+ path);
	String [] pairs = path.split(" "); 
	String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude lngLat[1]=latitude lngLat[2]=height
	// src
	GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6));
	mMapView01.getOverlays().add(new MyOverLay(startGP,startGP,1));
	GeoPoint gp1;
	GeoPoint gp2 = startGP; 
	for(int i=1;i<pairs.length;i++) // the last one would be crash
	{
	lngLat = pairs[i].split(",");
	gp1 = gp2;
	// watch out! For GeoPoint, first:latitude, second:longitude
	gp2 = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6));
	mMapView01.getOverlays().add(new MyOverLay(gp1,gp2,2,color));
	Log.d("xxx","pair:" + pairs[i]);
	}
	mMapView01.getOverlays().add(new MyOverLay(dest,dest, 3)); // use the default color
	} 
	}
	catch (MalformedURLException e)
	{
	e.printStackTrace();
	}
	catch (IOException e)
	{
	e.printStackTrace();
	}
	catch (ParserConfigurationException e)
	{
	e.printStackTrace();
	}
	catch (SAXException e)
	{
	e.printStackTrace();
	}
	}


	public void setMapView(MapView mapView) {
		Displaymap.mapView = mapView;
	}

	public MapView getMapView() {
		return mapView;
	}
}