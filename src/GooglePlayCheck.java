//package com.mycompany.myapp;

//import 	android.support.v4.app.FragmentActivity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.*;
import android.app.*;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import java.lang.Object;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.ConnectionResult;
import android.content.*;
import android.widget.Toast;

public class GooglePlayCheck extends FragmentActivity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{
		// Global constants
		private Context context;
		/*
		 * Define a request code to send to Google Play services
		 * This code is returned in Activity.onActivityResult
		 */
		private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
		
		// Define a DialogFragment that displays the error dialog
		public static class ErrorDialogFragment extends DialogFragment {
			// Global field to contain the error dialog
			private Dialog mDialog;
			// Default constructor. Sets the dialog field to null
			public ErrorDialogFragment() {
				super();
				mDialog = null;
			}
			// Set the dialog to display
			public void setDialog(Dialog dialog) {
				mDialog = dialog;
			}
			// Return a Dialog to the DialogFragment.
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				return mDialog;
			}
		}
		
		public GooglePlayCheck(Context context) {
			this.context = context;
		}
		
		/*
		 * Handle results returned to the FragmentActivity
		 * by Google Play services
		 */
		@Override
		protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
			// Decide what to do based on the original request code
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
	//@Override
		public boolean servicesConnected() {
			// Check that Google Play services is available
			Log.i("Location Updates",
				  "Smo pred Google Play services contextom " + context);
			int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
			Log.i("Location Updates",
				  "Smo za Google Play services contextom rizalt koud: " + resultCode);
			Log.i("Location Updates",
				  "Smo za Google Play services contextom");
			// If Google Play services is available
			if (ConnectionResult.SUCCESS == resultCode) {
				// In debug mode, log the status
				Log.i("Location Updates",
					  "Google Play services is available in GooglePlayCheck.");
				// Continue
				return true;
				// Google Play services was not available for some reason
			} else {
				// Get the error code
				//int errorCode = ConnectionResult.getErrorCode();
				// Get the error dialog from Google Play services
				Log.i("Location Updates",
					  "Google Play services is unavailable in GooglePlayCheck.");
				Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					resultCode,
					this,
					CONNECTION_FAILURE_RESOLUTION_REQUEST);	

				// If Google Play services can provide an error dialog
				if (errorDialog != null) {
					// Create a new DialogFragment for the error dialog
					ErrorDialogFragment errorFragment =
                        new ErrorDialogFragment();
					// Set the dialog in the DialogFragment
					errorFragment.setDialog(errorDialog);
					// Show the error dialog in the DialogFragment
					errorFragment.show(getSupportFragmentManager(),
									   "Location Updates");
				}
				return false;
			}		
		}
	
	/*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		Log.i("Location Updates",
			  "Connected!");

    }
    
    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
					   Toast.LENGTH_SHORT).show();
    }
    
    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
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
           // showErrorDialog(connectionResult.getErrorCode());
			int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
			GooglePlayServicesUtil.getErrorDialog(errorCode, this, 0).show();
        }
    }
		
		
}
