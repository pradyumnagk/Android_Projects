package com.example.firstrun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Button button;
	ImageView image,larrow,rarrow;
	private String TAG = "STOCK";
	
	int[] images = {R.drawable.google,R.drawable.microsoft};
	String[] stockCodes = {"GOOG","YHOO"};
	String urlFirst = "http://query.yahooapis.com/v1/public/yql?q=select%20BidRealtime%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22";
	String urlLast = "%22)&env=store://datatables.org/alltableswithkeys";
	String stockVal;
	static int imgIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstrun_main);
        addArrowListner();
        addButtonListener();
        Button button = (Button) findViewById(R.id.sjsuLink);
        button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String url = "http://www.sjsu.edu/";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
        
        
    }
    
    private void addButtonListener() {
		button = (Button)findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AsyncCallWS task = new AsyncCallWS();
				task.execute();
			}
		});
	}

	public void addArrowListner() {
    	        image = (ImageView) findViewById(R.id.image);
    	        larrow = (ImageView) findViewById(R.id.larrow);
    	        rarrow = (ImageView) findViewById(R.id.rarrow);
    	        
    	        larrow.setOnClickListener(new OnClickListener() {
    	 
    	            @Override
    	            public void onClick(View view) {
    	            	--imgIndex;
    	            	if(imgIndex<0){
    	            		imgIndex=1;
    	            	}
    	            	int imageNo = images[imgIndex%2];
    	                image.setImageResource(imageNo);
    	            }
    	        });
    	        
    	        rarrow.setOnClickListener(new OnClickListener() {
    	       	 
    	            @Override
    	            public void onClick(View view) {
    	            	++imgIndex;
    	            	if(imgIndex>1){
    	            		imgIndex=0;
    	            	}
    	            	int imageNo = images[imgIndex%2];
    	                image.setImageResource(imageNo);
    	            }
    	        });
    	    }

    
	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		
		@Override
		protected Void doInBackground(String... params) {
			Log.i(TAG, "doInBackground");
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(urlFirst+ stockCodes[imgIndex] + urlLast);
			System.out.println(urlFirst+ stockCodes[imgIndex] + urlLast);
			try {
				HttpResponse resp = client.execute(request);
				HttpEntity entity = resp.getEntity();
				if (entity != null) {

	                InputStream instream = entity.getContent();
	                String response = convertStreamToString(instream);
	                readResponse(response);
					
	                // Closing the input stream will trigger connection release
	                instream.close();
	            }

				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		 private String convertStreamToString(InputStream is) {

		        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		        StringBuilder sb = new StringBuilder();

		        String line = null;
		        try {
		            while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                is.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		        return sb.toString();
		    }
		
		 public void readResponse(String response)
			{
				System.out.println("My Result::" + response);
				CharSequence seq;
				int index = response.indexOf("<BidRealtime>");
				seq = response.subSequence((index + 13), (index + 18));
				System.out.println("My Result:: " + seq.toString());
				
				stockVal = seq.toString();
			}
		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			TextView stockRes = (TextView)findViewById(R.id.tv_result);
			if(stockVal!=null){
            	stockRes.setText(stockVal);
            }else{
            	stockRes.setText("Hi");
            }
			
			
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
		}

	}
	
    public void buttonClicked(View v)
    {
		String url = "http://www.google.com";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
