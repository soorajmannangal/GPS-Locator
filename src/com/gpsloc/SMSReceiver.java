package com.gpsloc;



import java.util.StringTokenizer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.telephony.SmsMessage;
import android.widget.Toast;


public class SMSReceiver extends BroadcastReceiver {
	Intent myInt;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		 

		Bundle bundle = intent.getExtras();
        if (bundle != null) {
                // This will put every new message into a array of
                // SmsMessages. The message is received as a pdu,
                // and needs to be converted to a SmsMessage, if you want to
                // get information about the message.
                Object[] pdus = (Object[]) bundle.get("pdus");
               // final SmsMessage[] 
                final SmsMessage[]		messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++)
                        messages[i] = SmsMessage
                                        .createFromPdu((byte[]) pdus[i]);
                if (messages.length > -1) {
                        // Shows a Toast with the phone number of the sender,
                        // and the message.
                        String smsToast = "New SMS received from "
                                        + messages[0].getOriginatingAddress() + "\n'"
                                        + messages[0].getMessageBody() + "'";
                       //Toast.makeText(context, smsToast, Toast.LENGTH_LONG).show();
                        
                      //  unregisterReceiver(this);
                       // Intent i = new Intent(context, GPSLocatorActivity.class);
                        //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //context.startActivity(i); 
                        
                        StringTokenizer tok = new StringTokenizer(messages[0].getMessageBody(),"|");
                        if(tok.countTokens()== 6){// Matches greenAlert SMS format                        	
                        Intent myint = new Intent(context, com.gpsloc.GPSLocatorActivity.class);
                       // (int) (8.54926297 * 1E6), 
                        //     (int) (76.90257501 * 1E6));
                 	
                        myint.putExtra("message", messages[0].getMessageBody().toString());
                        
                        myint.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        myint.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(myint);
                        }
                        

                        
               		// myInt = new Intent(context,GPSLocatorActivity.class);  
               		 
               		//this.startActivityForResult(myInt, 0);
                        
                }
        }
	}

}
