package com.comscience15.devicetracker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainMenu extends Activity{
	private Button scanBtn, deviceBtn, userBtn;
	private TextView formatTxt, contentTxt;
	
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    scanBtn = (Button)findViewById(R.id.scan_button);
	    formatTxt = (TextView)findViewById(R.id.scan_format);
	    contentTxt = (TextView)findViewById(R.id.scan_content);
	    
	    deviceBtn = (Button)findViewById(R.id.viewDevices);
	    userBtn = (Button)findViewById(R.id.viewEmp);
	    
	    //create Databases for Devices and Employees
	    SQLiteDatabase db = openOrCreateDatabase("DeviceTracker", MODE_PRIVATE, null);
	    db.execSQL("CREATE TABLE IF NOT EXISTS Devices (ID INTEGER primary key, BrandModel TEXT, Platform TEXT, OS REAL, zTag TEXT, DeviceFirstIn NUMERIC, DateChkOut NUMERIC, ChkOutPerson TEXT, DateChkIn NUMERIC, ChkInPerson TEXT);");
	    db.execSQL("INSERT INTO Devices VALUES(null, 'iPhone 5', 'iOS', '7.0.6', 'ZML12345', '1/1/2014', '7/17/2014', 'Nat', '7/18/2014', 'Nat');");
	    
	    Cursor c = db.rawQuery("SELECT * from Devices", null);
	    c.moveToFirst();
	    Log.d("RESULT", c.getString(c.getColumnIndex("BrandModel")));
	    db.close();
	}
	
	//Scanner button click
	public void Scan(View V){
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();
	}
	
	//Open devices list and details
	public void deviceInventory(View V){
		Intent intent = new Intent(this, DeviceList.class);
		startActivity(intent);
	}
		
	//Open employees list and details
	public void empInventory(View V){
		Intent intent = new Intent(this, EmployeeList.class);
		startActivity(intent);
	}	
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve scan result
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		
		if (scanningResult != null) {
			//we have a result
		}else{
		    Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
		    toast.show();
		}
		String scanContent = scanningResult.getContents();
		String scanFormat = scanningResult.getFormatName();
		
		formatTxt.setText("FORMAT: " + scanFormat);
		contentTxt.setText("CONTENT: " + scanContent);
	}
	
//	Button bScanner, bDeviceList;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
//		AllFindViewById();
//	}
//
//	private void AllFindViewById() {
//		// TODO Auto-generated method stub
//		bScanner = (Button) findViewById(R.id.btnScanner);
//		bDeviceList = (Button) findViewById(R.id.btnDeviceList);
//	}
//
//	//Scanner button click
//	public void Scanner(View V){
//		Intent intent = new Intent(this, Scanner.class);
//		startActivity(intent);
//	}
//	
//	//open device list information button click
//	public void DeviceListIntent(View V){
//		Intent intent = new Intent(this, DeviceList.class);
//		startActivity(intent);
//		
////		Intent intent = new Intent(this, ZBarScannerActivity.class);
////		startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
//	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
}
