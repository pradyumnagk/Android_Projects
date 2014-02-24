package com.example.intentexmp;

import java.util.Random;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class DisplayMessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
	    // Get the message from the intent
	    Intent intent = getIntent();
	    String firstName = intent.getStringExtra(MainActivity.FIRST_NAME);
	    String lastName = intent.getStringExtra(MainActivity.LAST_NAME);
	    String address = intent.getStringExtra(MainActivity.ADDRESS);
	    String cardNo = intent.getStringExtra(MainActivity.CARDNO);

	    // Create the text view
//	    TextView textView = (TextView)findViewById(R.id.showMessage);
//	    textView.setTextSize(40);
//	    textView.setText(message);
	    
	    final ImageView successImage = (ImageView)findViewById(R.id.result);
//	    successImage.setVisibility(View.INVISIBLE);
	    
	    try {
	    	
	    	new CountDownTimer(3000, 1) {
				
				@Override
				public void onTick(long millisUntilFinished) {
					
				}
				
				@Override
				public void onFinish() {
					Random rand = new Random();
					int n = rand.nextInt(10);
					
					
					if(n%2==0){
						successImage.setImageResource(R.drawable.success);
					}else{
						successImage.setImageResource(R.drawable.fail);
						
					}
				}
			}.start();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    // Set the text view as the activity layout
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-backs
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
