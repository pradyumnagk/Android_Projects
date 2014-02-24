package com.example.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button download = (Button)findViewById(R.id.download);
		download.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startDownload();
			}
		});
	}

	protected void startDownload() {
		Intent intent = new Intent(this, DownloadService.class);
		intent.putExtra("url", "url of the file to download");
		
		startService(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
