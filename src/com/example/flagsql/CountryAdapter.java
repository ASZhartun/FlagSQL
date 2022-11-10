package com.example.flagsql;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryAdapter extends ArrayAdapter<Country> {
	
	ArrayList<Country> countries;
	Context ctx;

	public CountryAdapter(Context context, ArrayList<Country> resource) {
		super(context, 0, resource);
		this.ctx = context;
		this.countries = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Country country = countries.get(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(ctx).inflate(R.layout.country_item, parent, false);
		}

		ImageView flagPic = (ImageView) convertView.findViewById(R.id.flag_pic);
		TextView abbr = (TextView) convertView.findViewById(R.id.abbr);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		
		abbr.setText(country.getCode());
		name.setText(country.getName());
		byte[] flag = country.getFlag();
		Bitmap pic = BitmapFactory.decodeByteArray(flag, 0, flag.length);
		flagPic.setImageBitmap(pic);
		
		return convertView;
	}

}
