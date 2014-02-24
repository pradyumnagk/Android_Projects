package com.example.intentexmp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowDataActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.show_data);
	    LinearLayout showLayout = (LinearLayout)findViewById(R.layout.show_data);
	    showPreferenceData(showLayout);
	    showSqlData();	    	
	}

	private void showSqlData() {
		Intent intent = getIntent();
		@SuppressWarnings("unchecked")
		ArrayList<Contact> contacts = (ArrayList<Contact>)intent.getSerializableExtra("contacts");
		Contact contact = contacts.get(0);
		
		TextView title = (TextView)findViewById(R.id.sqlTitle);
		title.setTextSize(40);
		title.setText("SQL DATA");
		
		TextView firstName =null;
		firstName = (TextView)findViewById(R.id.sqlFName);
		firstName.setText(contact.getFirstName());
		
		TextView lastName = (TextView)findViewById(R.id.sqlLName);
		lastName.setText(contact.getLastName());
		
		TextView address = (TextView)findViewById(R.id.sqlAddress);
		address.setText(contact.getAddress());
		
		TextView cardNo = (TextView)findViewById(R.id.sqlLContactNo);
		cardNo.setText(contact.getCardNumber());
		
	}

	private void showPreferenceData(LinearLayout showLayout) {
		Intent intent = getIntent();
		
		TextView title = (TextView)findViewById(R.id.pTitle);
		title.setText("PREFERENCES DATA");
		
		TextView firstName =null;
		firstName = (TextView)findViewById(R.id.pFName);
		firstName.setText(intent.getStringExtra(MainActivity.FIRST_NAME));
		
		TextView lastName = (TextView)findViewById(R.id.pLName);
		lastName.setText(intent.getStringExtra(MainActivity.LAST_NAME));
		
		TextView address = (TextView)findViewById(R.id.pAddress);
		address.setText(intent.getStringExtra(MainActivity.ADDRESS));
		
		TextView cardNo = (TextView)findViewById(R.id.pCardNo);
		cardNo.setText(intent.getStringExtra(MainActivity.CARDNO));
		
		
		
	}

}
