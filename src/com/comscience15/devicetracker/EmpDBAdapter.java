package com.comscience15.devicetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EmpDBAdapter {
	private static final String TAG = "EmpDBAdapter";
	public static final String KEY_ROWID = "id";
	public static final int COL_ROWID = 0;
	public static final String KEY_FIRSTNAME = "firstname";
	public static final String KEY_LASTNAME = "lastname";
	public static final String KEY_TITLE = "title";
	public static final String KEY_TEAM = "team";
	
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_TITLE, KEY_TEAM};
	public static final String DATABASE_NAME = "DeviceTracker";
	public static final String EMPLOYEEDB_TABLE = "Employees";
//	public static final String DEVICEDB_TABLE = "Devices";
	public static final int DATABASE_VERSION = 1;
	
	private static final String CREATE_EMPLOYEEDB_SQL = 
			"CREATE TABLE IF NOT EXISTS " + EMPLOYEEDB_TABLE + " (" + KEY_ROWID + " integer primary key autoincrement, "
			+ KEY_FIRSTNAME + " TEXT not null, "
			+ KEY_LASTNAME + " TEXT not null, "
			+ KEY_TITLE + " TEXT not null, "
			+ KEY_TEAM + " TEXT not null" + ");";
	
	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;
			
	public EmpDBAdapter(Context ctx){
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	public EmpDBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		myDBHelper.close();
	}
	
	public long insertRow(String firstName, String lastName, String title, String team) {
		ContentValues initValues = new ContentValues();
		initValues.put(KEY_FIRSTNAME, firstName);
		initValues.put(KEY_LASTNAME, lastName);
		initValues.put(KEY_TITLE, title);
		initValues.put(KEY_TEAM, team);
		
		return db.insert(EMPLOYEEDB_TABLE, null, initValues);
	}
	
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(EMPLOYEEDB_TABLE, where, null) != 0;
	}
	
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if(c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));
			} while (c.moveToNext());
		}
		c.close();
	}
	
	private Cursor getAllRows() {
		String where = null;
		Cursor c = db.query(true, EMPLOYEEDB_TABLE, ALL_KEYS, where, null, null, null, null, null);
		
		if (c != null) {
			c.moveToFirst();
		}
		return null;
	}

	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = db.query(true, EMPLOYEEDB_TABLE, ALL_KEYS, where, null, null, null, null, null);
		
		if(c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	public boolean updateRow(long rowId, String firstName, String lastName, String title, String team) {
		String where = KEY_ROWID + "=" + rowId;
		
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_FIRSTNAME, firstName);
		newValues.put(KEY_LASTNAME, lastName);
		newValues.put(KEY_TITLE, title);
		newValues.put(KEY_TEAM, team);
		
		return db.update(EMPLOYEEDB_TABLE, newValues, where, null) != 0;
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_EMPLOYEEDB_SQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's employee database from version " + oldVersion
					+ " to" + newVersion + ", which will destroy all old data!");
			
			db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEEDB_TABLE);
			onCreate(db);
		}
	}
}
