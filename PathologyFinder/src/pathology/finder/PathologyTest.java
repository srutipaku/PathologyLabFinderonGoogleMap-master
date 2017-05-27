/*
 * This Activity is used for fetching the information of particular pathology lab from database and display
 * the "lab location"," Testname "and" price " in a gridview Component
 */

package pathology.finder;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PathologyTest extends Activity implements OnItemClickListener 
{
	int indexnum;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridviewfile);
        Bundle extras = getIntent().getExtras();
        int indexnumber = extras.getInt("indexvalue");
        indexnum=indexnumber;
        //opening the Database
	    SQLiteDatabase db1;
	      /*
		  * For Accessing database file which is stored in SDCard i.e. "Memory Card".
		  */
	   
	 //    db1 = openOrCreateDatabase("/sdcard/DatabaseFiles/PathologyLab.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
	      
	    /*
		  * For Accessing database file which is stored in Internal Memory.
		  */
	    
	   db1 = openOrCreateDatabase("PathologyLab.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
	     /*
	     * setting the version of database as 3
	     */
	        db1.setVersion(3);
	        try
	        {
	          Cursor cursor;
	        /*
	           Querying for Retrieving the labName,tests,price from database
	           and here identifying the particular lab by index values 
	           and the index value of particular pathology lab is got from
	           overlay items Class
	           
	         */
	          String sel="SELECT labname,tests,price FROM labinfo WHERE indexval='"+indexnum+"'";
	           cursor=db1.rawQuery(sel,null);
	           int j=0; // for count
	           //creating arrays
	       String[] avalues=new String[9];
	       String[] spacearr=new String[9];
	          int i=0;
	          
	           while(cursor.moveToNext()) 
	        	   {
	        	   // placing the elements in array
	        	      avalues[i]=cursor.getString(0);
	        	      spacearr[i]=avalues[i];
	        	      i++;
	        	      avalues[i]=cursor.getString(1);
	        	      spacearr[i]="       "+avalues[i];
	        	      i++;
	        	      avalues[i]=cursor.getString(2);
	        	      spacearr[i]="       "+avalues[i];
	        	      i++;
	        	      j++; // incrementing Count
	        	   }
	           GridView gridview = (GridView) findViewById(R.id.gridview);
	          /*
	           * giving the array as input to GridView component
	           */
	           gridview.setAdapter(new MyAdapter(this,spacearr));
	        // for calling the itemClick Listener of GridView Component
	            gridview.setOnItemClickListener(this);
	       
	        }
	        catch (Exception e) 
	        {
				e.printStackTrace();
			}    
    }

    /*
     * Inner Class Used for Representation of GridView 
     * and INHERITING the Super Class BaseAdapter
     */
    public class MyAdapter extends BaseAdapter 
    { 
    	
    	private Context context;
 
    	private String[] texts;

    	public MyAdapter() 
    	{
    		
    	}
    	public MyAdapter(Context context,String[] arr) 
    	{
    	 
    	  this.context = context;
    	  texts=arr;
    	}
    	public int getCount() 
    	{
    	    return texts.length;
    	}

    	public Object getItem(int position) 
    	{
    	    return null;
    	}

    	public long getItemId(int position) 
    	{
    	    return 0;
    	}

    	/*
    	 *  Method used to add information in GridView
    	 */
    	public View getView(int position, View convertView, ViewGroup parent) 
    	{
    		TextView tv;
    	    if (convertView == null) 
    	    {
    	        tv = new TextView(context);
    	        tv.setTextSize(20);
    	        tv.setLayoutParams(new GridView.LayoutParams(80, 100));     
    	    }
    	    else 
    	    {
    	        tv = (TextView) convertView;
    	        tv.setTextSize(20);
    	    }

    	   tv.setText(texts[position]);
    	     return tv;
    	}
    }
    
    /*
     * Whenever we click on GridView Items the below method is called
     */
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
	{
		// Write logic for click handler on GridView Component	
	}
	
}
