package com.example.localbound;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.localbound.BoundService.MyLocalBinder;

public class LocalBoundActivity extends Activity {
	
	BoundService myService;
    boolean isBound = false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_bound);
		Intent intent = new Intent(this, BoundService.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
        //bindService(intent, myConnection, Context.BIND_ADJUST_WITH_ACTIVITY);
        
        Button b = (Button)findViewById(R.id.pdf);
        b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(isBound){
					myService.downloadFile(1);
					TextView text = (TextView)findViewById(R.id.myTextView);
					text.setText("Downloading pdf files");
				}
			}
		});
        
        Button text = (Button)findViewById(R.id.text);
        text.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(isBound){
					myService.downloadFile(3);
					TextView text = (TextView)findViewById(R.id.myTextView);
					text.setText("Downloading text files");
				}
			}
		});
        
        Button image = (Button)findViewById(R.id.Image);
        image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(isBound){
					myService.downloadFile(2);
					TextView text = (TextView)findViewById(R.id.myTextView);
					text.setText("Downloading image files");
				}
			}
		});
	}
	
	private ServiceConnection myConnection = new ServiceConnection() {

	    public void onServiceConnected(ComponentName className,
	            IBinder service) {
	        MyLocalBinder binder = (MyLocalBinder) service;
	        myService = binder.getService();
	        isBound = true;
	    }
	    
	    public void onServiceDisconnected(ComponentName arg0) {
	        isBound = false;
	    }
	    
	   };
	   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.local_bound, menu);
		return true;
	}
	

}
