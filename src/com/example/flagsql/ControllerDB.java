package com.example.flagsql;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ControllerDB {
	private MakerDB makerDB;
	private Context ctx;

	public ControllerDB(Context ctx) {
		this.ctx = ctx;
	}

	public void open() throws SQLException {
		makerDB = new MakerDB(ctx);
	}

	public void close() {
		makerDB.close();
	}

	public ArrayList<Country> getAllCountries() {
		ArrayList<Country> countries = new ArrayList<Country>();
		SQLiteDatabase db = makerDB.getWritableDatabase();
		Cursor countryCursor = makerDB.getAllCountries(db);
		formCountryArray(countryCursor, countries);
		makerDB.close();
		return countries;
	}

	public ArrayList<Country> getCountriesByName(String name) {
		ArrayList<Country> countries = new ArrayList<Country>();
		SQLiteDatabase db = makerDB.getWritableDatabase();
		Cursor countryCursor = makerDB.getCountriesByName(name, db);
		formCountryArray(countryCursor, countries);
		makerDB.close();
		return countries;
	}

	public ArrayList<Country> getCountriesByCode(String name) {
		if (name.length() > 2) {
			name = name.substring(0, 2);
		}
		ArrayList<Country> countries = new ArrayList<Country>();
		SQLiteDatabase db = makerDB.getWritableDatabase();
		Cursor countryCursor = makerDB.getCountriesByCode(name, db);
		formCountryArray(countryCursor, countries);
		makerDB.close();
		return countries;
	}

	private void formCountryArray(Cursor countryCursor, ArrayList<Country> countries) {
		if (countryCursor != null) {
			countryCursor.moveToFirst();
			if (countryCursor.getCount() > 0) {
				do {
					Country temp = new Country();
					temp.setName(countryCursor.getString(0));
					temp.setCode(countryCursor.getString(1));
					temp.setFlag(countryCursor.getBlob(2));
					countries.add(temp);
				} while (countryCursor.moveToNext());
			}

		}
	}
}
