package com.example.intentexmp;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "contacts.db";
	private static final String TABLE_NAME = "Contact";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String ADDRESS = "address";
    private static final String CREDITCARD = "card_no";
    
    private static final String CREATE_CONTACT =
                "CREATE TABLE " + TABLE_NAME + " (" +
                FIRST_NAME + " TEXT, " + 
                LAST_NAME + " TEXT,"	+
                ADDRESS + " TEXT,"	+
                CREDITCARD + " TEXT);";
    
	public DBManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_CONTACT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public void insertContact(Contact contact){
		SQLiteDatabase database = this.getWritableDatabase();
		if(contact!=null){
			ContentValues values = new ContentValues();
			values.put(FIRST_NAME, contact.getFirstName());
			values.put(LAST_NAME, contact.getLastName());
			values.put(ADDRESS, contact.getAddress());
			values.put(CREDITCARD, contact.getCardNumber());
			database.insert(TABLE_NAME, null, values);
			database.close();
		}
	}
	
	public ArrayList<Contact> getContacts(){
		SQLiteDatabase database = this.getWritableDatabase();
		String selectQuery = "SELECT  * FROM "+TABLE_NAME;
		Cursor cursor = database.rawQuery(selectQuery, null);
		
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		
		if (cursor.moveToFirst()) {
			do{
				Contact contact = new Contact();
				contact.setFirstName(cursor.getString(0));
				contact.setLastName(cursor.getString(1));
				contact.setAddress(cursor.getString(2));
				contact.setCardNumber(cursor.getString(3));
				contacts.add(contact);
			}while(cursor.moveToNext());

		}
		
		return contacts;
	}
	
}
