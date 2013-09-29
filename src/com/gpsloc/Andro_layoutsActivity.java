package com.gpsloc;

import java.lang.reflect.Array;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
public class Andro_layoutsActivity extends Activity{
    /** Called when the activity is first created. */
	SMSReceiver r1;
	 Intent myint ;
	IntentFilter filter;
	public static Button btshow;
	public static EditText lat;
	public static EditText lg;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        r1 = new SMSReceiver();
        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        this.registerReceiver(r1,filter);
        setContentView(R.layout.home);
        btshow=(Button)findViewById(R.id.btn_show);
        lat=(EditText)findViewById(R.id.edt_latitude);
        lg=(EditText)findViewById(R.id.edt_longitude);
    
    
    }
    public void onResume(){
    	super.onResume();
    	//r1 = new SMSReceiver();
        //filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    	this.registerReceiver(r1,filter);
    	btshow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myint = new Intent(getBaseContext(), com.gpsloc.GPSLocatorActivity.class);
                 // (int) (8.54926297 * 1E6), 
                  //     (int) (76.90257501 * 1E6));
           	
                  myint.putExtra("message","-|-|"+ lat.getText() +"|-|"+lg.getText()+"|");
                  
                  myint.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  myint.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                  getBaseContext().startActivity(myint);
				
			}
		});
    }
    public void onStop()
    {
    	super.onStop();
    	this.unregisterReceiver(r1);
    }
  
}