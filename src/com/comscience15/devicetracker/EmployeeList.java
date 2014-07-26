package com.comscience15.devicetracker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

public class EmployeeList extends Activity{
	public String employeeDB = "Employee";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.employeelist);
		
		
		createDB();
		//create employee DB to external memory
		try {
			copyDBtoSD();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createDB() {
		//create Databases for Devices and Employees
	    SQLiteDatabase db = openOrCreateDatabase("DeviceTracker", MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS Employee (ID INTEGER primary key, FirstName TEXT, LastName TEXT, Title TEXT, Team TEXT;");
	    db.execSQL("INSERT INTO Employee VALUES(null, 'Nat', 'Srileeannop', 'QA Engineer IV', 'QA Functional');");
	    db.execSQL("INSERT INTO Employee VALUES(null, 'Masae', 'Kellogg', 'QA Manager', 'QA Functional');");
	    db.execSQL("INSERT INTO Employee VALUES(null, 'Jonathan', 'Quilo', 'QA Engineer III', 'QA Functional');");
	    
	    Cursor c = db.rawQuery("SELECT FirstName, LastName from Employee", null);
	    c.moveToFirst();
	    Log.d("RESULT", c.getString(c.getColumnIndex("FirstName")));
	    
	    List<PackageInfo> empList = new ArrayList<PackageInfo>();
	    
	    db.close();
	}

	private void copyDBtoSD() throws IOException {
		// TODO Auto-generated method stub
		File sd = Environment.getExternalStorageDirectory();
		
		if (sd.canWrite()){
			String currentDBPath = employeeDB;
		}
	}
	
}
