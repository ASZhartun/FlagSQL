package com.example.flagsql;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		MakerDB makerDB = new MakerDB(getApplication());
		SQLiteDatabase db = makerDB.getWritableDatabase();
		Cursor cursor = makerDB.getAllCountries(db);

		ListView lv = (ListView) findViewById(R.id.countries_list);
		String[] from = { MakerDB.ABBR, MakerDB.COUNTRY};
		int[] to = { R.id.abbr, R.id.name };

		ControllerDB controllerDB = new ControllerDB(getApplication());
		controllerDB.open();
		ArrayList<Country> allCountries = controllerDB.getAllCountries();
		controllerDB.close();
		
		CountryAdapter adapter2 = new CountryAdapter(this, allCountries);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.country_item, cursor, from, to);
		
		lv.setAdapter(adapter2);

	}
}
