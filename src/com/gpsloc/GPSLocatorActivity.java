package com.gpsloc;


import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MapController;
import com.google.android.maps.GeoPoint;
//import android.content.Context;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;

import android.location.Geocoder;
import android.location.Address;

import com.google.android.maps.Overlay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.io.IOException;

import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
//import android.widget.LinearLayout;
import android.widget.Toast;

public class GPSLocatorActivity extends MapActivity {
	public String pt1,pt2;
	private MapView mapView;
	private MapController mapController;
	//SMSReceiver r1;
	IntentFilter filter;
     public String ConvertPointToLocation(GeoPoint point) {   
    	 
    	 
    	String address = "";
        Geocoder geoCoder = new Geocoder(
                getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(
            	point.getLatitudeE6()  / 1E6, 
                point.getLongitudeE6() / 1E6, 1);
 
	        if (addresses.size() > 0) {
	            for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++)
	            	address += addresses.get(0).getAddressLine(index) + " ";
	        }
        }
        catch (IOException e) {                
            e.printStackTrace();
        }   
        
        return address;
    } 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("message");
        
       //String[] points = msg.split("");
        StringTokenizer tokens=new StringTokenizer(msg,"|"); 
       // String[] points = StringTokenizer.(msg,'|'); 
        String first = tokens.nextToken();
        first = tokens.nextToken();
         first = tokens.nextToken();// this will contain "Fruit"
        String second = tokens.nextToken();
         second = tokens.nextToken();
       
        String msgstr; 
      // msgstr=points[0];
       //pt1=points[2];
       //pt2=points[4];
     //  Toast.makeText(this,first +"hai"+ second, 1500).show();
      // Toast.makeText(this,  points[2]+" points  "+ points[1] +points[0], 3500).show();
        
        pt1=first;
        pt2=second;

      
        mapView = (MapView) findViewById(R.id.mapView);
       // mapView=new MapView(this,api);
        
      //setLayoutParams(new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT));
		  
		 // mapView = new MapView(getContext(),api);
		  mapView.setEnabled(true);
		  mapView.setClickable(true);
		  
		  //overlay = new MapLocationOverlay(this);
		 // mapView.getOverlays().add(overlay);
		
        
        
        // enable Street view by default
        mapView.setStreetView(true);
        
        
        // enable to show Satellite view
        //mapView.setSatellite(true);
        
        
        // enable to show Traffic on map
        // mapView.setTraffic(true);
        mapView.setBuiltInZoomControls(true);
        
        mapController = mapView.getController();
        
        mapController.setZoom(16); 
        

        
        getpoint();
       
    }
    
    void getpoint()
    {
     	GeoPoint point = new GeoPoint(
                (int) (Double.parseDouble(pt1) * 1E6), 
                (int) (Double.parseDouble(pt2) * 1E6));
		
        mapController.animateTo(point);
        mapController.setZoom(16);
        MapOverlay mapOverlay = new MapOverlay();
		mapOverlay.setPointToDraw(point);
		List<Overlay> listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();
		listOfOverlays.add(mapOverlay);
		
		//Toast.makeText(getBaseContext(), "hai", 500).show();
		
        String address = ConvertPointToLocation(point);
        //Toast.makeText(getBaseContext(), address, Toast.LENGTH_SHORT).show();
       
       // mapView.refreshDrawableState();
       //mapView.setSatellite(true);
     
        mapView.invalidate();
        //end geo
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    class MapOverlay extends Overlay
    {
    	private GeoPoint pointToDraw;

    	public void setPointToDraw(GeoPoint point) {
    		pointToDraw = point;
    	}

    	public GeoPoint getPointToDraw() {
    		return pointToDraw;
    	}
    	
        @Override
        public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
            super.draw(canvas, mapView, shadow);                   
 
            // convert point to pixels
            Point screenPts = new Point();
            mapView.getProjection().toPixels(pointToDraw, screenPts);
 
            // add marker
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.loc);
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y - 24, null); // 24 is the height of image        
            return true;
        }
    } 
}