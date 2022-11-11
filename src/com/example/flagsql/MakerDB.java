package com.example.flagsql;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.DownloadManager.Query;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MakerDB extends SQLiteOpenHelper {

	Context ctx;

	static final String DB_NAME = "myDB";
	static Integer DB_VERSION = 1;

	static final String TABLE_NAME = "flags";
	static final String ID = "_id";
	static final String ABBR = "abbr";
	static final String COUNTRY = "country";
	static final String PIC = "pic";

	static String createTableSQL = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + COUNTRY
			+ " TEXT, " + ABBR + " TEXT, " + PIC + " BLOB);";

	static String deleteTableSQL = "DROP TABLE IF EXISTS " + TABLE_NAME;

	static final String FLAG_PIC_PATH = "flag_pic";

	public MakerDB(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.ctx = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createTableSQL);
		fillFlagTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(deleteTableSQL);
		onCreate(db);
	}

	public Cursor getAllCountries(SQLiteDatabase db) {
		String[] columns = new String[] { COUNTRY, ABBR, PIC, ID };
		Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public Cursor getCountriesByName(String name, SQLiteDatabase db) {
		String[] columns = new String[] { COUNTRY, ABBR, PIC, ID };
		Cursor cursor;
		if (name == null || name.equals("")) {
			cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}
		} else {
			String like = "%" + name + "%";
			cursor = db.query(TABLE_NAME, columns, "country LIKE ?", new String[] { like }, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}
		}
		return cursor;
	}

	public Cursor getCountriesByCode(String name, SQLiteDatabase db) {
		String[] columns = new String[] { COUNTRY, ABBR, PIC, ID };
		Cursor cursor;
		if (name == null || name.equals("")) {
			cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}
		} else {
			String like = "%" + name + "%";
			cursor = db.query(TABLE_NAME, columns, "abbr LIKE ?", new String[] { like }, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}
		}
		return cursor;
	}

	private void fillFlagTable(SQLiteDatabase currentDB) {
		ArrayList<byte[]> images = getImages();
		ArrayList<String[]> countries = getCountries();

		for (int i = 0; i < countries.size(); i++) {
			ContentValues values = new ContentValues();
			values.put(ABBR, countries.get(i)[0]);
			values.put(COUNTRY, countries.get(i)[1]);
			values.put(PIC, images.get(i));
			currentDB.insert(TABLE_NAME, null, values);
		}
	}

	private ArrayList<String[]> getCountries() {
		ArrayList<String[]> countries = new ArrayList<String[]>();
		try {
			InputStream currStream = ctx.getAssets().open("country_codes.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(currStream));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] temp = line.split("\\t");
				countries.add(temp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return countries;
	}

	private ArrayList<byte[]> getImages() {
		ArrayList<byte[]> images = new ArrayList<byte[]>();
		AssetManager assetManager = ctx.getAssets();
		try {
			String[] fileNamesOfFlagPic = assetManager.list(FLAG_PIC_PATH);
			for (int i = 0; i < fileNamesOfFlagPic.length; i++) {
				// get image from assets
				byte imageInByte[] = getImageByPath(fileNamesOfFlagPic[i], assetManager);
				images.add(imageInByte);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return images;
	}

	private byte[] getImageByPath(String path, AssetManager assetManager) {
		try {
			InputStream currStream = assetManager.open(FLAG_PIC_PATH + '/' + path);
			Bitmap image = BitmapFactory.decodeStream(currStream);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			return stream.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
