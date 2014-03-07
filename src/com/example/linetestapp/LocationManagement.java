package com.example.linetestapp;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class LocationManagement extends FragmentActivity implements 
	GooglePlayServicesClient.ConnectionCallbacks, 
	GooglePlayServicesClient.OnConnectionFailedListener,
	LocationListener {
	
	private final static String LOG_OUT = "LOG";
	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public static final String KEY_UPDATES_REQUESTED =
            "com.example.android.location.KEY_UPDATES_REQUESTED";
	
	//handle errors for location
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	
	private static final int MILLISECONDS_PER_SECOND = 1000;
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    private static final long UPDATE_INTERVAL =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    private static final long FASTEST_INTERVAL =
            MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
	
	// Stores the current instantiation of the location client in this object
    private LocationClient mLocationClient;
    
    // Define an object that holds accuracy and frequency parameters
    private LocationRequest mLocationRequest;
    
    private boolean mUpdatesRequested;
    
    // Handle to SharedPreferences for this app
    SharedPreferences mPrefs;
    
    // Handle to a SharedPreferences editor
    SharedPreferences.Editor mEditor;
	
	DrawLines drawer;
	
	Canvas canvas;
	
	Bitmap b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(LOG_OUT, "onCreate in LocationManager");
		Toast.makeText(this, "LocationManagement Active!", Toast.LENGTH_SHORT).show();
		
		drawer = new DrawLines(this);
		
		b = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(b);
		
		mLocationRequest = LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		
		mPrefs = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
		
		// Get an editor
        mEditor = mPrefs.edit();
        
		mLocationClient = new LocationClient(this, this, this);
		
		mUpdatesRequested = false; // starts turned off, until user turns on
		setContentView(drawer);
	}

	@Override
	protected void onPause() {
		mEditor.putBoolean(KEY_UPDATES_REQUESTED, mUpdatesRequested);
		mEditor.commit();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (mPrefs.contains(KEY_UPDATES_REQUESTED)) {
			mUpdatesRequested = true; 
					//mPrefs.getBoolean(KEY_UPDATES_REQUESTED, false);   // FIX THIS!			
		} else {
			mEditor.putBoolean(KEY_UPDATES_REQUESTED, false);
			mEditor.commit();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Connecting client
		mLocationClient.connect();
	}

	@Override
	protected void onStop() {
		if (mLocationClient.isConnected()) {
			mLocationClient.removeLocationUpdates(this);
		}
		
		// Disconnecting the client
		mLocationClient.disconnect();
		
		super.onStop();
	}

	@SuppressLint("WrongCall")
	@Override
	public void onLocationChanged(Location location) {
		// Report to the UI that the location was updated
		double x = location.getLongitude();
		double y = location.getLatitude();
        String msg = "Updated " +
                Double.toString(y) + "," +
                Double.toString(x);
        Log.d(LOG_OUT, "Location-Change:     " + msg);
        
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        
        drawer.setPrevCurX((float)x);
        drawer.setPrevCurY((float)y);
        drawer.onDraw(canvas);	
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
        	
        	showDialog(connectionResult.getErrorCode());
            //showErrorDialog(connectionResult.getErrorCode());
        }
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		 if (mUpdatesRequested) {
			 mLocationClient.requestLocationUpdates(mLocationRequest, this);
		 }
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disonnected. Please re-connect.", Toast.LENGTH_SHORT).show();
	}
	
	/*********************************/
	/* REGARDING LOCATION MANAGEMENT */
	/*********************************/
	 
	
	// Handle error dialog 
	public static class ErrorDialogFragment extends DialogFragment {
		
		private Dialog mDialog;
		
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}
		
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
		
	}
	
	/*
	 * handles results from google play services 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {

        case CONNECTION_FAILURE_RESOLUTION_REQUEST :
        /*
         * If the result code is Activity.RESULT_OK, try
         * to connect again
         */
            switch (resultCode) {
                case Activity.RESULT_OK :
                /*
                 * Try the request again
                 */

                break;
            }
		}
	}
	
	
	// Checks that google play services are available
	private boolean servicesConnected() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		if (ConnectionResult.SUCCESS == resultCode) {
			// Google play services is available
			Log.d("Location Updates", "Google Play services is available");
			return true;
		} else {
			// Google play services is not available
			
			// Get the error code
			// Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
            		resultCode, 
            		this, 
            		CONNECTION_FAILURE_RESOLUTION_REQUEST);
            
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getSupportFragmentManager(), "Location Updates");
            }
            return false;
		}
		
	}

}
