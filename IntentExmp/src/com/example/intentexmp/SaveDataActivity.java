package com.example.intentexmp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SaveDataActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.save_data);
	    
	    TextView testResult = (TextView)findViewById(R.id.saveResult);
	    testResult.setText("This is a temporary result");
	    
	    Button preference = (Button)findViewById(R.id.preference);
	    preference.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveContactInPreference();
			}
		});
	    
	    final DBManager dbManager = new DBManager(this);
	    Button sqlite = (Button)findViewById(R.id.sqlite);
	    sqlite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Contact contact = readData();
				dbManager.insertContact(contact);
			}
		});
	}
	
	private Contact readData() {
		Intent intent = getIntent();
	    String firstName = intent.getStringExtra(MainActivity.FIRST_NAME);
	    String lastName = intent.getStringExtra(MainActivity.LAST_NAME);
	    String address = intent.getStringExtra(MainActivity.ADDRESS);
	    String cardNo = intent.getStringExtra(MainActivity.CARDNO);
	    
	    Contact contact = new Contact();
	    contact.setFirstName(firstName);
	    contact.setLastName(lastName);
	    contact.setAddress(address);
	    contact.setCardNumber(cardNo);
		return contact;
	}
	
	public void saveContactInPreference(){
		String MyPreference = "MYPREF";
		Contact contact = readData();
		SharedPreferences pref = getSharedPreferences(MyPreference, Context.MODE_PRIVATE);
		
		Editor editor = pref.edit();
		editor.putString(MainActivity.FIRST_NAME, contact.getFirstName());
		editor.putString(MainActivity.LAST_NAME, contact.getLastName());
		editor.putString(MainActivity.ADDRESS, contact.getAddress());
		editor.putString(MainActivity.CARDNO, contact.getCardNumber());
		
		editor.commit();
	}
}
