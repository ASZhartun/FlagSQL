package com.example.flagsql;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView lv;
	RadioButton byCode;
	RadioButton byName;
	EditText input;
	Button searchButton;
	ControllerDB controllerDB;
	
	ArrayList<Country> allCountries;
	CountryAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lv = (ListView) findViewById(R.id.countries_list);

		controllerDB = new ControllerDB(getApplication());
		controllerDB.open();
		allCountries = controllerDB.getAllCountries();
		controllerDB.close();

		adapter = new CountryAdapter(this, allCountries);

		lv.setAdapter(adapter);

		byCode = (RadioButton) findViewById(R.id.by_code);
		byName = (RadioButton) findViewById(R.id.by_name);
		input = (EditText) findViewById(R.id.input);
		searchButton = (Button) findViewById(R.id.search_action);

		searchButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (byCode.isChecked()) {
					Toast.makeText(getApplicationContext(), "byCode was chosen", Toast.LENGTH_LONG).show();
					allCountries = controllerDB.getCountriesByCode(input.getText().toString());
					adapter.clear();
					adapter.addAll(allCountries);
					adapter.notifyDataSetChanged();
				} else if (byName.isChecked()) {
					Toast.makeText(getApplicationContext(), "byName was chosen", Toast.LENGTH_LONG).show();
					allCountries = controllerDB.getCountriesByName(input.getText().toString());
					adapter.clear();
					adapter.addAll(allCountries);
					adapter.notifyDataSetChanged();
				} else {
					// do nothing...
				}

			}
		});
	}
}
