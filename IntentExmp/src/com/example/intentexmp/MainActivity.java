package com.example.intentexmp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	public final static String FIRST_NAME = "First Name";
	public final static String LAST_NAME = "Last Name";
	public final static String ADDRESS = "Address";
	public final static String CARDNO = "Card Number";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intent_activity);
		
		Button b = (Button)findViewById(R.id.processData);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				processData();
			}
		});
		
		//CREATE the instance of DBManager 
		final DBManager dbManager = new DBManager(this);
		
		Button save = (Button)findViewById(R.id.saveData);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendMessage(v);
//				Contact contact = readData();
//				dbManager.insertContact(contact);
			}
		});
		
		Button report = (Button)findViewById(R.id.getData);
		report.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ArrayList<Contact> contacts = dbManager.getContacts();
				showSavedData(contacts);
			}
		});
	}
	
	private void showSavedData(ArrayList<Contact> contacts){
		Intent intent = new Intent(this, ShowDataActivity.class);
		intent.putExtra("contacts", contacts);
		String MyPreference = "MYPREF";
		SharedPreferences pref = getSharedPreferences(MyPreference, Context.MODE_PRIVATE);
		if(pref!=null){
			intent.putExtra(FIRST_NAME, pref.getString(FIRST_NAME, ""));
			intent.putExtra(LAST_NAME, pref.getString(LAST_NAME, ""));
			intent.putExtra(ADDRESS, pref.getString(ADDRESS, ""));
			intent.putExtra(CARDNO, pref.getString(CARDNO, ""));
			
		}
		startActivity(intent);
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void processData(){
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		startActivity(intent);
	}
	/** Called when the user clicks the Send button */
	public void sendMessage(View view) {
		Intent intent = new Intent(this, SaveDataActivity.class);
		EditText editText = (EditText) findViewById(R.id.fName);
	    String message = editText.getText().toString();
	    intent.putExtra(FIRST_NAME, message);
	    
	    editText = (EditText) findViewById(R.id.lName);
	    message = editText.getText().toString();
	    intent.putExtra(LAST_NAME, message);
	    
	    editText = (EditText) findViewById(R.id.address);
	    message = editText.getText().toString();
	    intent.putExtra(ADDRESS, message);
	    
	    editText = (EditText) findViewById(R.id.cardNumber);
	    message = editText.getText().toString();
	    intent.putExtra(CARDNO, message);
	    
	    startActivity(intent);
	}

}
