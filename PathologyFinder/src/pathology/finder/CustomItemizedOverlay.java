/*
 * This Class is used for Displaying 
   Balloon Markers on GoogleMap which are initiated in "Displaymap Class"
   *** For Detail Explanation and understanding of the usage of this class 
   *Visit Below Link
   http://developer.android.com/resources/tutorials/views/hello-mapview.html
 */
package pathology.finder;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;


public class CustomItemizedOverlay extends ItemizedOverlay<OverlayItem>
{
	public MapView mapView1;
	private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();
	
	private Context context;
	
	public CustomItemizedOverlay(Drawable defaultMarker) {
		  super(boundCenterBottom(defaultMarker));
	}
	
	public CustomItemizedOverlay(Drawable defaultMarker, Context context,MapView mapv) {
		  this(defaultMarker);
		  this.context = context;
		  this.mapView1=mapv;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mapOverlays.get(i);
	}

	@Override
	public int size() {
		return mapOverlays.size();
	}
	/*
	Whenever Clicked on Balloon Markers the "onTap Method is called"
	 */
	@Override
	protected boolean onTap(final int index) 
	{
		OverlayItem item = mapOverlays.get(index);
		// Displaying Alert about selected Pathology Lab
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(item.getTitle().toString());
		builder.setMessage(item.getSnippet().toString());
		builder.setCancelable(false);
	       builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) 
	           {     	  
	        	 try
	        	   {
	        	   Displaymap gm=new Displaymap();
	       	       MapView  mapView2=gm.getMapView();
	       	    /*
	       	       Navigating to Pathology List activity 
	       	       which displays about various test and prices 
	       	     */
	        	   Intent intent = new Intent(); 
	        	   intent.setClass(mapView2.getContext(), PathologyTest.class); 
	        	   intent.putExtra("indexvalue",index);
	        	   mapView2.getContext().startActivity(intent);
	        	   }
	        	 catch (Exception e) 
	        	    {
	        		   e.printStackTrace();
				    }
	           }
	       });
	       builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               // dialog.cancel();
	           }
	           
	       });
	       
	AlertDialog alert = builder.create();
	alert.show();
		return true;
	}
	
	public void addOverlay(OverlayItem overlay) {
		mapOverlays.add(overlay);
	    this.populate();
	}
}
