package com.example.linetestapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LocationManager extends Activity {
	
	DrawLines drawers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("LOG", "onCreate in LocationManager");
		Toast.makeText(this, "LocationManager Active!", Toast.LENGTH_SHORT).show();
		drawers = new DrawLines(this);
		setContentView(drawers);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}
