package com.mounika.bookingdonation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	public DBHelper(Context context) {
		super(context, "BookTable", null, 3);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(" create table Details( ID INTEGER PRIMARY KEY   AUTOINCREMENT," +
				"UserName varchar(20),Password varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//db.execSQL("DROP TABLE IF EXITS History");
			
			onCreate (db);
	}

}
