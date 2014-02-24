package com.example.localbound;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

public class BoundService extends Service{
	
	private final IBinder myBinder = new MyLocalBinder();
	private int downloadType;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return myBinder;
	}
	
	public String getCurrentTime() {
		SimpleDateFormat dateformat = 
                 new SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US);
		return (dateformat.format(new Date()));
	}
	
	public String downloadFile(int type){
		
		downloadType = type;
		AsyncDnldService donldTask = new AsyncDnldService();
		donldTask.execute();
		return "hey";
	}
	public class MyLocalBinder extends Binder {
        BoundService getService() {
            return BoundService.this;
        }
    }
	
	private class AsyncDnldService extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			ArrayList<String> urls = null;
			String fileExt = "";
			switch(downloadType){
			case 1:  urls =getPdfUrls();
					fileExt=".pdf";
					break;
			case 2: urls =getImageURLs();
					fileExt=".jpg";
					break;	
			case 3: urls =getDocURLs();
					fileExt=".doc";
					break;
			default: urls =getPdfUrls();
					fileExt=".pdf";
					break;
					
			}
			
			try {

				int i =0;
				for(String dwnldUrl : urls){
					URL url = new URL(dwnldUrl);
					URLConnection connection = url.openConnection();
					connection.connect();

					// download the file
					File root = android.os.Environment.getExternalStorageDirectory();
					File dir = new File(root.getAbsolutePath() + "/file"+i+fileExt);

					InputStream input = new BufferedInputStream(url.openStream());
					OutputStream output = new FileOutputStream(dir);

					byte data[] = new byte[1024];
					long total = 0;
					int count;
					while ((count = input.read(data)) != -1) {
						total += count;
						output.write(data, 0, count);
					}

					output.flush();
					output.close();
					input.close();
					appendLog("Local Bound Activity download file  "+dir.getName()+" by bindserive BIND_AUTO_CREATE method");
					//appendLog("Local Bound Activity download file  "+dir.getName()+" by bindserive BIND_ADJUST_WITH_ACTIVITY method");
					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public void appendLog(String text)
		{       
		   //File logFile = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/AdjustWithActivityLog.file");
			File logFile = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/AutoCreateLog.file");
		   if (!logFile.exists())
		   {
		      try
		      {
		         logFile.createNewFile();
		      } 
		      catch (IOException e)
		      {
		         e.printStackTrace();
		      }
		   }
		   try
		   {
		      BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true)); 
		      buf.append(text);
		      buf.newLine();
		      buf.close();
		   }
		   catch (IOException e)
		   {
		      e.printStackTrace();
		   }
		}

	}
	
	public ArrayList<String> getPdfUrls(){
		ArrayList<String> urls = new ArrayList<String>();
		urls.add("http://brainlens.org/content/newsletters/Spring%202013.pdf");
		urls.add("http://www.adobe.com/enterprise/accessibility/pdfs/acro6_pg_ue.pdf");
		urls.add("http://www.ancestralauthor.com/download/sample.pdf");
		urls.add("http://www.bankofengland.co.uk/publications/Documents/quarterlybulletin/qb0704.pdf");
		urls.add("http://www.learningseed.com/_guides/1278_stock_market_basics_guide.pdf");
		
		return urls;
	}
	
	public ArrayList<String> getDocURLs(){
		ArrayList<String> urls = new ArrayList<String>();
		urls.add("http://www.pittband.com/colorguardapp2014.doc");
		urls.add("www.stealthskater.com/Documents/Book_01.doc");
		urls.add("http://www.escholarship.org/sample_author_agreement_final.doc");
		urls.add("www.moranos.com/DlySpec.doc‎");
		return urls;
	}
	
	public ArrayList<String> getImageURLs(){
		ArrayList<String> urls = new ArrayList<String>();
		urls.add("http://blogs.sjsu.edu/today/files/2014/01/mima-mounds-1l9fs40.jpg");
		return urls;
	}
}
