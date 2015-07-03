package com.mycompany.myapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.mycompany.myapp.LoginActivity.UserLoginTask;
import com.mycompany.myapp.app.Config;
//import com.mycompany.myapp.LoginActivity.ProfileQuery;
import com.mycompany.myapp.helper.SQLiteHandler;
import com.mycompany.myapp.helper.SessionManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
 
//public class RegisterActivity extends LoginActivity {
public class RegisterActivity extends PlusBaseActivity implements ConnectionCallbacks, OnConnectionFailedListener, LoaderCallbacks<Cursor> {
    
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
    public static final String SCOPES = "https://www.googleapis.com/auth/plus.login";
    
    static final String TAG = "mycompany.myapp";
    
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int REQUEST_CODE_TOKEN_AUTH = 100; // Preglej, kaj 100ka pomeni
    private static final int RC_SIGN_IN = 0;
    private static String register_tag = "register";
    final List<NameValuePair> params_reg = new ArrayList<NameValuePair>();
    
 //   private String registerURL = "https://192.168.1.100";
 //  private String registerURL = "https://10.10.0.148";
   private String registerURL = Config.URL_MYSQL_SERVER;
    //private String registerURL = "https://localhost";
    
    GoogleCloudMessaging gcm;
    
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private View mProgressView;
 
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
//    private EditText inputEmail;
  //  private EditText inputPassword;
    private TextView registerErrorMsg;
    private Integer mGooglePlusFlag;
     
    
    static InputStream is = null;
    static OutputStream os = null;
    static JSONObject jObj = null;
    //static String json = "";
    static String json_str = "";
    private JSONObject json = null;
    String regId;
    private SignInButton mPlusSignInButton;
	private Button mPlusSignOutButton;
	private Button mPlusDisconnectButton;
	private LinearLayout mPlusDisconnectButtonS;
    
    private SessionManager session;
	private SQLiteHandler db;
	private GoogleApiClient mGoogleApiClient;
	private String regid;
	private boolean mSignInClicked;
	private ConnectionResult mConnectionResult;
	private String personName;
	private String personEmail;
	private String email;
	private String password;
	
	private String token = null;
	
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	//private UserLoginTask mAuthTask = null;
	private JSONParser_IN mAuthTask = null;
	/**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;
	
	/**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    String SENDER_ID = "440103715165";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
 
        // Importing all assets like buttons, text fields
        inputFullName = (EditText) findViewById(R.id.registerName);
       // inputEmail = (EditText) findViewById(R.id.email_register);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email_register);
        mPasswordView = (EditText) findViewById(R.id.password_register);
        btnRegister = (Button) findViewById(R.id.email_register_button);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLogInScreen);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
         
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        
        // Session manager
        session = new SessionManager(getApplicationContext());
        
     // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
 
        // SQLite database handler
     //   db = new SQLiteHandler(getApplicationContext());
        db = SQLiteHandler.getInstance(getApplicationContext());
        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }
        
        if (checkPlayServices()) {
    		
        	gcm = GoogleCloudMessaging.getInstance(this);
        	regId = getRegistrationId(getApplicationContext());
        	if (regId.isEmpty()) {
    		registerInBackground();
        	}
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
        
     // Find the Google+ sign in button.
     		mPlusSignInButton = (SignInButton) findViewById(R.id.plus_sign_in_button);
     		
     		if (supportsGooglePlayServices()) {
     			// Set a listener to connect the user when the G+ button is clicked.
     			mPlusSignInButton.setOnClickListener(new OnClickListener() {
     				@Override
     				public void onClick(View v) {
     			        switch (v.getId()) {
     			        case R.id.plus_sign_in_button:
     			            // Signin button clicked
     			            signInWithGplus();
     			            break;
//     			        case R.id.plus_sign_out_button:
     			            // Signout button clicked
//     			            signOutFromGplus();
//     			            break;
//     			        case R.id.plus_disconnect_button:
     			            // Revoke access button clicked
//     			            revokeGplusAccess();
//     			            break;
     			        }
     				}
     			//	public void onClick(View view) {
     			//		signInWithGplus();
     					//signIn();
     			//	}
     			});
     		} else {
     			// Don't offer G+ sign in if the app's version is too low to support
     			// Google Play
     			// Services.
     			mPlusSignInButton.setVisibility(View.GONE);
     			return;
     		}
        
        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {         
            public void onClick(View view) {
            	/*Log.d("mycompany.myapp", "Smo v register onClick!");
                String name = inputFullName.getText().toString();
                Log.d("mycompany.myapp", "name je: "+name);
                String email = inputEmail.getText().toString();
                Log.d("mycompany.myapp", "email je: "+email);
                String password = inputPassword.getText().toString();
                Log.d("mycompany.myapp", "password je: "+password);
               
               // Log.d("mycompany.myapp", "Smo pred new UserFunctions!");
           //     UserFunctions userFunction = new UserFunctions();
                
                Log.d("mycompany.myapp", "Smo pred registerUser!");
                // JSONObject json = userFunction.registerUser(name, email, password);
         //        JSONObject json = userFunction.registerUser(name, email, password);
                
                 // Provajmo use spravit sem, da ne klicarimo naokoli 100x:
                 
                 final List<NameValuePair> params_reg = new ArrayList<NameValuePair>();
                 params_reg.add(new BasicNameValuePair("tag", register_tag));
                 params_reg.add(new BasicNameValuePair("name", name));
                 params_reg.add(new BasicNameValuePair("email", email));
                 params_reg.add(new BasicNameValuePair("password", password));
                 params_reg.add(new BasicNameValuePair("gcmRegId", regId));
                 
                 Log.d("mycompany.myapp", "Smo pred jsonParser v RegisterActivity!");*/	
                 //JSONObject json = jsonParser.getJSONFromUrl(registerURL, params_reg);
              //    new JSONParser_IN().execute(registerURL, params_reg);
            	attemptRegister();
            	
                 //new JSONParser_IN().execute(params_reg);
                 Log.d("mycompany.myapp", "Smo za jsonParser za RegisterActivity!");	
                 
                 /// Provajmo use spravit sem, da ne klicarimo naokoli 100x: KONEC
              
                Log.d("mycompany.myapp", "Smo v register, za json register user!");
                
   
                
                // check for login response
               /* try {
                    if (json.getString(KEY_SUCCESS) != null) {
                    	Log.d("mycompany.myapp", "KEY_SUCCESS v Register Activity je uspeu!");
                        registerErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS); 
                        Log.d("mycompany.myapp", "json.getString v Register Activity je uspeu!");
                        Log.d("mycompany.myapp", "res v Register Activity je: " +res);
                        if(Integer.parseInt(res) == 1){
                            // user successfully registred
                            // Store user details in SQLite Database
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");
                            Log.d("mycompany.myapp", "json_user v RegisterActivity je: " +json_user.getString(name));
                            
                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
                            db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                        
                            // Launch Dashboard Screen
                            //Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                            // Close all views before launching Dashboard
                            //dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //startActivity(dashboard);
                            String WelcomeToast = "Sign up successful. Welcome, ".concat(json_user.toString()).concat("!");
                            Toast.makeText(RegisterActivity.this, WelcomeToast, Toast.LENGTH_LONG).show();
                            // Close Registration Screen
                            finish();
                        }else{
                            // Error in registration
                            registerErrorMsg.setText("Error occurred in registration");
                            Log.e("mycompany.myapp", "Error occurred in registration");
                        }
                    }
                } catch (JSONException e) {
                	Log.e("mycompany.myapp", "KEY_SUCCESS ni uspel!");
                    e.printStackTrace();
                }
 */           }
        });
 
        
        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                // Close Registration View
                finish();
            }
        });
    }
    
    @Override
	public void onConnected(Bundle arg0) {
	    mSignInClicked = false;
	    Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
	    
	    // Create login session
        //session.setLogin(true);
	    // Google+ authentication OK
	    mGooglePlusFlag = 1;
	 
	    // Get user's information
	    getProfileInformation();
	 
	    // Update the UI after signin
	    updateUI(true);
	    
	    // Get token
		//GetGPlusTokenTask.execute();
		
	 
	 // Set up sign out and disconnect buttons.
	 		Button signOutButton = (Button) findViewById(R.id.plus_sign_out_button);
	 		signOutButton.setOnClickListener(new OnClickListener() {
	 			@Override
	 			public void onClick(View view) {
	 				//signOut();
	 				signOutFromGplus();
	 				
	 			}
	 		});
	 		Button disconnectButton = (Button) findViewById(R.id.plus_disconnect_button);
	 		disconnectButton.setOnClickListener(new OnClickListener() {
	 			@Override
	 			public void onClick(View view) {
	 				//revokeAccess();
	 				revokeGplusAccess();
	 			}
	 		});
	 		
	 	//	getProfileInformation();
	 	//	  final List<NameValuePair> params_reg = new ArrayList<NameValuePair>();
	         //    params_reg.add(new BasicNameValuePair("tag", register_tag));
	         //    params_reg.add(new BasicNameValuePair("name", personName));
	         //    params_reg.add(new BasicNameValuePair("email", email));
	         //    params_reg.add(new BasicNameValuePair("password", password));
	          //   params_reg.add(new BasicNameValuePair("gcmRegId", regId));
	             
	           //  Log.d("mycompany.myapp", "Smo pred jsonParser v RegisterActivity!");
				
				// Nehamo ustauljat
				
	          //   new JSONParser_IN().execute(params_reg);
			//	mAuthTask = new UserLoginTask(email, password);
	             
	         	if (token != null) {
	         	//	params_reg.add(new BasicNameValuePair("googlePlusToken", token));
	         	//	mAuthTask =  new JSONParser_IN();
		         	//.execute(params_reg);
				//	mAuthTask.execute(params_reg);
	    		//	mAuthTask = new UserLoginTask(token);
	    		//	mAuthTask.execute((Void) null);
	    		} else {
	    			Log.d(TAG, "token v RegisterActivity == null");
	    		}
	         
	 		
	 		// Provajmo it nazaj u MainActivity, ker smo se glih registrirali
	 		//Intent intent = new Intent(RegisterActivity.this,
            //       MainActivity.class);
            //startActivity(intent);
            //finish();
	}
	 
    /**
	 * Revoking access from google
	 * */
	private void revokeGplusAccess() {
	    if (mGoogleApiClient.isConnected()) {
	        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
	        Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
	                .setResultCallback(new ResultCallback<Status>() {
	                    @Override
	                    public void onResult(Status arg0) {
	                        Log.e(TAG, "User access revoked!");
	                        mGoogleApiClient.connect();
	                        updateUI(false);
	                    }
	 
	                });
	    }
	}
	
	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private void getProfileInformation() {
	    try {
	        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
	            Person currentPerson = Plus.PeopleApi
	                    .getCurrentPerson(mGoogleApiClient);
	            personName = currentPerson.getDisplayName();
	 //           String personPhotoUrl = currentPerson.getImage().getUrl();
	            String personGooglePlusProfile = currentPerson.getUrl();
	            personEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);
	            
	            String nameOnly = null;
	            if(personName.contains(" ")){
	            	nameOnly = personName.substring(0, personName.indexOf(" ")); 
	            }
	            Log.e("Login Activity", "Name: " + personName + ", plusProfile: "
	                    + personGooglePlusProfile + ", email: " + personEmail);
	//                    + ", Image: " + personPhotoUrl);
	            
	        //    final List<NameValuePair> params_reg = new ArrayList<NameValuePair>();
	             params_reg.add(new BasicNameValuePair("tag", register_tag));
	             params_reg.add(new BasicNameValuePair("name", personName));
	             params_reg.add(new BasicNameValuePair("email", personEmail));
	           //  params_reg.add(new BasicNameValuePair("password", password));
	             params_reg.add(new BasicNameValuePair("gcmRegId", regId));
	             params_reg.add(new BasicNameValuePair("googlePlusFlag", String.valueOf(mGooglePlusFlag)));
	       //      params_reg.add(new BasicNameValuePair("googlePlusToken", mGooglePlusToken));
	             // mGooglePlusToken
	 			
	            
	     //       txtName.setText(personName);
	     //       txtEmail.setText(email);
	               Toast.makeText(RegisterActivity.this, "Welcome, " + nameOnly + "!", Toast.LENGTH_LONG).show();
	               GetGPlusTokenTask.execute();
	            // by default the profile url gives 50x50 px image only
	            // we can replace the value with whatever dimension we want by
	            // replacing sz=X
//	            personPhotoUrl = personPhotoUrl.substring(0,
//	                    personPhotoUrl.length() - 2)
//	                    + PROFILE_PIC_SIZE;
	 
	//            new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);
	 
	        } else {
	            Toast.makeText(getApplicationContext(),
	                    "Person information is null", Toast.LENGTH_LONG).show();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	 
    
    /**
	 * Sign-out from google
	 * */
	//private void signOutFromGplus() {
	protected void signOutFromGplus() {
	    if (mGoogleApiClient.isConnected()) {
	        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
	        mGoogleApiClient.disconnect();
	        mGoogleApiClient.connect();
	        Log.d(TAG, "Google Plus disconnected!");
	        updateUI(false);
	    }
	}
    
    
    @Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

 // Vstavimo blok za Google+ callback
	
 	@Override
 	public void onConnectionFailed(ConnectionResult result) {
 	    if (!result.hasResolution()) {
 	        GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
 	                0).show();
 	        return;
 	    }
 	 
 	    if (!mIntentInProgress) {
 	        // Store the ConnectionResult for later usage
 	        mConnectionResult = result;
 	 
 	        if (mSignInClicked) {
 	            // The user has already clicked 'sign-in' so we attempt to
 	            // resolve all
 	            // errors until the user is signed in, or they cancel.
 	            resolveSignInError();
 	        }
 	    }
 	 
 	}
    
    @Override
	protected void onPlusClientRevokeAccess() {
		// TODO: Access to the user's G+ account has been revoked. Per the
		// developer terms, delete
		// any stored user data here.
	}
    
    @Override
	protected void onPlusClientBlockingUI(boolean show) {
		showProgress(show);
	}
    
    /**
	 * Successfully disconnected (called by PlusClient)
	 */
	@Override
	public void onDisconnected() {
	    updateConnectButtonState();
	    onPlusClientSignOut();
	}
	
	@Override
	protected void onPlusClientSignOut() {

	}
    
	@Override
	protected void updateConnectButtonState() {
		// TODO: Update this logic to also handle the user logged in by email.
		boolean connected = getPlusClient().isConnected();

		//mSignOutButtons.setVisibility(connected ? View.VISIBLE : View.GONE);
		//mPlusSignInButton.setVisibility(connected ? View.GONE : View.VISIBLE);
		mEmailView.setVisibility(connected ? View.GONE : View.VISIBLE);
	}
    
	
	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
		GetGPlusTokenTask.execute();
	    updateUI(false);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int responseCode,
	        Intent intent) {
	    if (requestCode == RC_SIGN_IN) {
	        if (responseCode != RESULT_OK) {
	            mSignInClicked = false;
	        }
	 
	        mIntentInProgress = false;
	 
	        if (!mGoogleApiClient.isConnecting()) {
	        	mGoogleApiClient.connect();
	        }
	    }
	}
	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptRegister() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		String name = inputFullName.getText().toString();
		email = mEmailView.getText().toString();
		password = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password, if the user entered one.
		if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!isEmailValid(email)) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			
			/// Ustauljamo
			
			Log.d("mycompany.myapp", "Smo v register onClick!");
            name = inputFullName.getText().toString();
            Log.d("mycompany.myapp", "name je: "+name);
            email = mEmailView.getText().toString();
            Log.d("mycompany.myapp", "email je: "+email);
            password = mPasswordView.getText().toString();
            Log.d("mycompany.myapp", "password je: "+password);
           
           // Log.d("mycompany.myapp", "Smo pred new UserFunctions!");
       //     UserFunctions userFunction = new UserFunctions();
            
            Log.d("mycompany.myapp", "Smo pred registerUser!");
            // JSONObject json = userFunction.registerUser(name, email, password);
     //        JSONObject json = userFunction.registerUser(name, email, password);
            
            mGooglePlusFlag = 0;
             // Provajmo use spravit sem, da ne klicarimo naokoli 100x:
             
             final List<NameValuePair> params_reg = new ArrayList<NameValuePair>();
             params_reg.add(new BasicNameValuePair("tag", register_tag));
             params_reg.add(new BasicNameValuePair("name", name));
             params_reg.add(new BasicNameValuePair("email", email));
             params_reg.add(new BasicNameValuePair("password", password));
             params_reg.add(new BasicNameValuePair("gcmRegId", regId));
             params_reg.add(new BasicNameValuePair("googlePlusFlag", String.valueOf(mGooglePlusFlag)));
             
             Log.d("mycompany.myapp", "Smo pred jsonParser v RegisterActivity!");
			
			// Nehamo ustauljat
			
          //   new JSONParser_IN().execute(params_reg);
		//	mAuthTask = new UserLoginTask(email, password);
         	mAuthTask =  new JSONParser_IN();
         	//.execute(params_reg);
			mAuthTask.execute(params_reg);
		}
	}

	private boolean isEmailValid(String email) {
		// TODO: Replace this with your own logic
		return email.contains("@");
	}

	private boolean isPasswordValid(String password) {
		// TODO: Replace this with your own logic
		return password.length() > 4;
	}
	
	protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
 
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

	
    
    
	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}
	
	
	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(emails);
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
				ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?",
				new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE },

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}
	
	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		// Create adapter to tell the AutoCompleteTextView what to show in its
		// dropdown list.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				RegisterActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

		mEmailView.setAdapter(adapter);
	}
	
	@Override
	protected void onPlusClientSignIn() {
	}
	
	/**
	 * Check if the device supports Google Play Services. It's best practice to
	 * check first rather than handling this as an error case.
	 *
	 * @return whether the device supports Google Play Services
	 */
	private boolean supportsGooglePlayServices() {
		return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS;
	}
	
	AsyncTask<Void, Void, String> GetGPlusTokenTask = new AsyncTask<Void, Void, String>() {
        @Override
        protected String doInBackground(Void... params) {
          //  String token = null;

            try {
                token = GoogleAuthUtil.getToken(
                        RegisterActivity.this,
                        Plus.AccountApi.getAccountName(mGoogleApiClient),
                //        mGoogleApiClient.getAccountName(),
                        "oauth2:" + SCOPES);
            } catch (IOException transientEx) {
                // Network or server error, try later
                Log.e(TAG, transientEx.toString());
            } catch (UserRecoverableAuthException e) {
                // Recover (with e.getIntent())
                Log.e(TAG, e.toString());
                Intent recover = e.getIntent();
                startActivityForResult(recover, REQUEST_CODE_TOKEN_AUTH);
            } catch (GoogleAuthException authEx) {
                // The call is not ever expected to succeed
                // assuming you have already verified that 
                // Google Play services is installed.
                Log.e(TAG, authEx.toString());
            }

            return token;
        }

        @Override
        protected void onPostExecute(String token) {
        	if (token != null) {
        	//	params_reg.add(new BasicNameValuePair("name", personName));
        	//	params_reg.add(new BasicNameValuePair("email", token));
        	//	params_reg.add(new BasicNameValuePair("gcmRegId", token));
        	//	params_reg.add(new BasicNameValuePair("googlePlusFlag", token));
    			params_reg.add(new BasicNameValuePair("googlePlusToken", token));
         		mAuthTask =  new JSONParser_IN();
	         	//.execute(params_reg);
				mAuthTask.execute(params_reg);
    			
    		} else {
    			Log.d(TAG, "token v LoginActivity == null");
    		}
            Log.i(TAG, "Access token retrieved:" + token);
        }

    };
	
	
    //TUKAJ KOPIRAMO JSONParser
    
    public class JSONParser_IN extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
    	 
       /* static InputStream is = null;
        static JSONObject jObj = null;
        static String json = "";*/
     
        // constructor
        public JSONParser_IN() {
     
        }
        
        @Override
     //   protected JSONObject doInBackground(Object... paramts) {
        protected JSONObject doInBackground(List<NameValuePair>... paramts) {
        //    String url = (String) paramts[0];
        //    List<NameValuePair> params = (List<NameValuePair>) paramts[1];
            List<NameValuePair> params = (List<NameValuePair>) paramts[0];
            Log.d("setEntity v JSONParserju je: Paramts[0] je: ", paramts[0].toString());
            
            
      //      HttpsURLConnection urlConnection = setUpHttpsConnection("https://192.168.1.141/");
            
            
        /*    DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            try {
    			httpPost.setEntity(new UrlEncodedFormEntity(params));
    		} catch (UnsupportedEncodingException e1) {
    			// TODO Auto-generated catch block
    			 Log.d("setEntity ne dela v JSONParserju! Paramts[1] je: ", paramts[1].toString());
    			e1.printStackTrace();
    		}
            Log.d("getJSONFromUrl", "Smo za setEntity!");
       	 Log.d("setEntity v JSONParserju je: Paramts[1] je: ", paramts[1].toString());
         // Making HTTP Request
            try {
            	
                HttpResponse response = httpClient.execute(httpPost);
             
                // writing response to log
                Log.d("Http Response:", response.toString());
             
            } catch (ClientProtocolException e) {
                // writing exception to log
                e.printStackTrace();
                     
            } catch (IOException e) {
                // writing exception to log
                e.printStackTrace();
            }
            */
            
            
            
            
            
            
            
            try {
            	//HttpsURLConnection urlConnection = setUpHttpsConnection(url);
            	
            	/*  HttpClient httpclient = new DefaultHttpClient();
            	    HttpPost httppost = new HttpPost("http://192.168.1.142");
*/
            	/*    try {
            	        // Add your data
            	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            	        nameValuePairs.add(new BasicNameValuePair("id", "12345"));
            	        nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
            	        httppost.setEntity(new UrlEncodedFormEntity(params));

            	        // Execute HTTP Post Request
            	        HttpResponse response = httpclient.execute(httppost);
            	        
            	     //   response = client.execute(post);
            	        String responseStr = EntityUtils.toString(response.getEntity());
            	    	Log.d("JSONParser", "response je: " + responseStr);   
            	        
            	        
            	    } catch (ClientProtocolException e) {
            	        // TODO Auto-generated catch block
            	    } catch (IOException e) {
            	        // TODO Auto-generated catch block
            	    }*/
            	
            	
            //	HttpsURLConnection conn = setUpHttpsConnection(url);
            	Log.d("JSONParser", "Smo prisli v try!");
            	
            	
            	HttpsURLConnection conn = setUpHttpsConnection(registerURL);
            	
            	/// Copy
            	
            	//URL url_ = new URL(url);
            	//HttpsURLConnection conn = (HttpsURLConnection) url_.openConnection();
            	//conn.setReadTimeout(10000);
            	//conn.setConnectTimeout(15000);
            /*	conn.setRequestMethod("POST");
            	conn.setDoInput(true);
            	conn.setDoOutput(true);
*/
            	/*List<NameValuePair> params = new ArrayList<NameValuePair>();
            	params.add(new BasicNameValuePair("firstParam", paramValue1));
            	params.add(new BasicNameValuePair("secondParam", paramValue2));
            	params.add(new BasicNameValuePair("thirdParam", paramValue3));
    */		
            	 // Allow Inputs
             //   conn.setDoInput(true);
                // Allow Outputs
             //   conn.setDoOutput(true);
            //	InputStream in = conn.getInputStream();
            	//is = conn.getInputStream();
            	
            	try {
            	//	if (os == null) {
            			// do nothing
            	//	} else {
            			os = conn.getOutputStream();
            	//	}
            	} catch (Exception e) {  
                    e.printStackTrace();
                    Log.e("getOutputStream Error", "Error je: " + e.toString());
                }
            	
            	
            	/*BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
            	 String inputLine;
                 while ((inputLine = in.readLine()) != null) 
                     System.out.println(inputLine);*/
            
            	
            	
            	BufferedWriter writer = new BufferedWriter(
            	        new OutputStreamWriter(os, "UTF-8"));
            	writer.write(getQuery(params));
            	writer.flush();
            	writer.close();
            	os.close();

            //	conn.connect();
            	
            	
            	
            	
            	
            	//// Paste
            	
            	/*urlConnection.setRequestMethod("POST");
            	urlConnection.setDoInput(true);
            	urlConnection.setDoOutput(true);
            	try {
            		OutputStream os = urlConnection.getOutputStream();
            		
            		BufferedWriter writer = new BufferedWriter(
            	    new OutputStreamWriter(os, "UTF-8"));
            		os.close();
            		}
            	catch (Exception ex) {
            			Log.e("Buffer Error", "getOutputStream ne dela: " + ex.toString());
            			ex.printStackTrace();
            		}
            	
            	is = urlConnection.getInputStream();*/
           
          /*  } catch (UnsupportedEncodingException e) {
            	Log.e("com.mycompany.myapp", "UnsupportedEncodingException v JSONParser");
                e.printStackTrace();
            } catch (ClientProtocolException e) {
            	Log.e("com.mycompany.myapp", "ClientProtocolException v JSONParser");
                e.printStackTrace();
            } catch (IOException e) {
            	Log.e("com.mycompany.myapp", "IOException v JSONParser");
                e.printStackTrace();
            } catch (Exception e) {
    	         e.printStackTrace();
      		};*/
     
           // try {is = conn.getInputStream();
            	is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                		   is, "UTF-8"), 8);
                
                    //    is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    Log.v("Line je: ", line);
                    }
                is.close();
            	//conn.connect();
                
                json_str = sb.toString();
                //this is the error
                
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
            
            // try parse the string to a JSON object
            try {
               // jObj = new JSONObject(json);
            	if (json_str.isEmpty()) {
	            	//	 String ErrorLoginToast = "Login failed. Cannot connect to server :-(";
                    //  Toast.makeText(LoginActivity.this, ErrorLoginToast, Toast.LENGTH_LONG).show();
	            	} else {
	            		jObj = new JSONObject(json_str);
	            	}
            } catch (JSONException e) {
            //	Log.e("JSON Parser", "Error parsing data, to ne moremo zdzezonirat: " + json);
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
     
            // return JSON String
            return jObj;
     
        }

        protected void onPostExecute(JSONObject result) {
            //In here, call back to Activity or other listener that things are done
           //mCallback.onRequestCompleted(result);
        	if (result == null) {
				// do nothing
				String ErrorLoginToast = "Registration failed. Cannot connect to server :-(";
                Toast.makeText(RegisterActivity.this, ErrorLoginToast, Toast.LENGTH_LONG).show();
			} else {
        	 try {
        		 json = result;
        		 Log.d("RegisterActivity", "json v Register Activity je: " +json.toString());
                 if (json.getString(KEY_SUCCESS) != null) {
                 	Log.d("mycompany.myapp", "KEY_SUCCESS v Register Activity je uspeu!");
                     registerErrorMsg.setText("");
                     String res = json.getString(KEY_SUCCESS); 
                     Log.d("mycompany.myapp", "json.getString v Register Activity je uspeu!");
                     Log.d("mycompany.myapp", "res v Register Activity je: " +res);
                     if(Integer.parseInt(res) == 1){
                         // user successfully registred
                         // Store user details in SQLite Database
             ///            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
             //            SQLiteHandler db = new SQLiteHandler(getApplicationContext());
                    	
                         JSONObject json_user = json.getJSONObject("user");
                         Log.d("mycompany.myapp", "json_user v RegisterActivity je: " +json_user.getString("name"));
                         
                         // user successfully logged in
                         
                         // Create login session
                         session.setLogin(true);
                         
                         
                         // Clear all previous data in database
                  //       userFunction.logoutUser(getApplicationContext());
                         db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
                         session.setLogin(true);
                         // Launch Dashboard Screen
                         //Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                         // Close all views before launching Dashboard
                         //dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         //startActivity(dashboard);
                         //String WelcomeToast = "Sign up successful. Welcome, ".concat(json_user.toString()).concat("!");
                         String WelcomeToast = "Sign up successful. Welcome, " .concat(json_user.getString("name").toString()).concat("!");
                         Toast.makeText(RegisterActivity.this, WelcomeToast, Toast.LENGTH_LONG).show();
                         // Launch login activity
                   /*      Intent intent = new Intent(
                                 RegisterActivity.this,
                                 LoginActivity.class);
                         startActivity(intent);*/
                         
                         
                         // Launch authenticator activity
                 /*        if (mGooglePlusFlag == 0) {
                        	 Intent intentAuth = new Intent(RegisterActivity.this,
                        			 AuthenticationActivity.class);
                        	 intentAuth.putExtra("username", email);
                        	 intentAuth.putExtra("password", password);
                        	 startActivity(intentAuth);
                        	 finish();
                         }*/
                         
                         
                         
                         // Launch main activity
                         Intent intent_back = new Intent(RegisterActivity.this,
                                 MainActivity.class);
                         startActivity(intent_back);

                         // Close Registration Screen
                         finish();
                     }else{
                         // Error in registration
                         registerErrorMsg.setText("Error occurred in registration");
                         Log.e("mycompany.myapp", "Error occurred in registration");
                     }
                 }
             } catch (JSONException e) {
             	Log.e("mycompany.myapp", "KEY_SUCCESS ni uspel!");
                 e.printStackTrace();
             }

        
        }
        }
        // COPY PEJSTAMO
        // TUKAJ KOPIRAMO JSONParser: KONEC
    }
    
    
    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
    
    /**
	 * Sign-in into google
	 * */
	private void signInWithGplus() {
	    if (!mGoogleApiClient.isConnecting()) {
	        mSignInClicked = true;
	        resolveSignInError();
	    }
	}
	
	 /**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
	
    public HttpsURLConnection setUpHttpsConnection(String urlString)
    {
		/*try
        {
		
			//InputStream caPrviInput = new BufferedInputStream(MainActivity.context.getAssets().open("ShopCo.crt"));
			InputStream caPrviInput = new BufferedInputStream(this.getAssets().open("ShopCo.crt"));
			//String[] caPrviInput = this.getAssets().list("");
			//for(String name:caPrviInput){
			//     System.out.println(name);    
			//}
			Log.e("mycompany.myapp", "Assets: " + caPrviInput);
			Log.e("mycompany.myapp", "ShopCo.crt je loudan na zacetku v LoginActivity!");
        }
		catch (Exception ex)
        {
            Log.e("mycompany.myapp", "Failed to open ShopCo.crt v LoginActivity: " + ex.toString());
            return null;
        }
		*/
		
		
		try
        {
            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            if (cf == null) {
            	Log.e("mycompany.myapp", "X.509 ni loudan!");
            }
            else {
            	Log.d("mycompany.myapp", "X.509 je loudan!");
            }
            // My CRT file that I put in the assets folder
            // I got this file by following these steps:
            // * Go to https://littlesvr.ca using Firefox
            // * Click the padlock/More/Security/View Certificate/Details/Export
            // * Saved the file as littlesvr.crt (type X.509 Certificate (PEM))
            // The MainActivity.context is declared as:
            // public static Context context;
            // And initialized in MainActivity.onCreate() as:
            // MainActivity.context = getApplicationContext();
            
            //InputStream caInput = null;
            //caInput = new BufferedInputStream(MainActivity.context.getAssets().open("ShopCo.crt"));
            InputStream caInput = new BufferedInputStream(this.getAssets().open("ShopCoDejmo.crt"));
          
            caInput.available();
            if (caInput.available() == 0) {
            	Log.e("mycompany.myapp", "ShopCo.crt ni loudan!");
            	}
            else {
            	Log.d("mycompany.myapp", "ShopCo.crt je loudan!");
            	}
            
            java.security.cert.Certificate ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            
            caInput.close();
            
            
            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            
            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            
            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            
            //SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            context.init(null, tmf.getTrustManagers(), null);
            
            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL(urlString);
            // To spodaj je zaradi problema s povezavo na server s certifikatom preko IPja, ne pa imena serverja
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            	  public boolean verify(String hostname, SSLSession session) {
            	  return true;
            	  }
            	});
            HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
            //urlConnection.setHostnameVerifier(new BrowserCompatHostnameVerifier());
            urlConnection.setSSLSocketFactory(context.getSocketFactory());
            Log.d("mycompany.myapp", "SSL connection to server established.");
            
            //InputStream in = urlConnection.getInputStream();
            
            //urlConnection.setRequestMethod("GET");
            //urlConnection.connect();
            
          //  InputStream in = urlConnection.getInputStream();
            
            
            //URL url = new URL(registerURL);
        //	HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
        	
        	
        //	conn.setUseCaches(false);
            
            
            
            return urlConnection;
        }
        catch (Exception ex)
        {
            Log.e("mycompany.myapp", "Failed to establish SSL connection to server: " + ex.toString());
            return null;
        }
    }
	
    private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing registration ID is not guaranteed to work with
	    // the new app version.
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
    }
    
    private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the registration ID in your app is up to you.
	    return getSharedPreferences(MainActivity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
    }
    
    /**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	               .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
    
	private void registerInBackground() {
	    new AsyncTask<Void, Void, String>() {
	        @Override
	        protected String doInBackground(Void... params) {
	            String msg = "";
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
	                }
	                regId = gcm.register(SENDER_ID);
	                Log.d("mycompany.myapp", "Device registered, registration ID = " + regId);
	                msg = "Device registered, registration ID=" + regId;

	                // You should send the registration ID to your server over HTTP,
	                // so it can use GCM/HTTP or CCS to send messages to your app.
	                // The request to your server should be authenticated if your app
	                // is using accounts.
	      //          sendRegistrationIdToBackend();

	                // For this demo: we don't need to send it because the device
	                // will send upstream messages to a server that echo back the
	                // message using the 'from' address in the message.

	                // Persist the registration ID - no need to register again.
	                storeRegistrationId(getApplicationContext(), regId);
	            } catch (IOException ex) {
	                msg = "Error :" + ex.getMessage();
	                // If there is an error, don't just keep trying to register.
	                // Require the user to click a button again, or perform
	                // exponential back-off.
	            }
	            return msg;
	        }

	        @Override
	        protected void onPostExecute(String msg) {
	        	Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
	           // mDisplay.append(msg + "\n");
	        }
	    }.execute(null, null, null);
	}
	
	private boolean checkPlayServices() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (status != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
				showErrorDialog(status);
			} else {
				Toast.makeText(this, "This device is not supported.", 
							   Toast.LENGTH_LONG).show();
				finish();
			}
			return false;
		}
		 // In debug mode, log the status
        Log.d("Location Updates",
                "Google Play services is available.");
        // Continue
		return true;
	} 
	
	void showErrorDialog(int code) {
		GooglePlayServicesUtil.getErrorDialog(code, this, 
											  REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
	}
	
	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param context application's context.
	 * @param regId registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
	
	/**
	 * Updating the UI, showing/hiding buttons and profile layout
	 * */
	private void updateUI(boolean isSignedIn) {
	    if (isSignedIn) {
	    	Log.d(TAG, "isSignedIn je true v updateUI");
	       // btnSignIn.setVisibility(View.GONE);
	    	mPlusSignInButton.setVisibility(View.GONE);
	       // btnSignOut.setVisibility(View.VISIBLE);
	    //	mPlusSignOutButton.setVisibility(View.VISIBLE);
	       // btnRevokeAccess.setVisibility(View.VISIBLE);
	    //	mPlusDisconnectButton.setVisibility(View.VISIBLE);
	       // llProfileLayout.setVisibility(View.VISIBLE);
	    //	mPlusDisconnectButtonS.setVisibility(View.VISIBLE);
	    } else {
	    	Log.d(TAG, "isSignedIn je false v updateUI");
	       // btnSignIn.setVisibility(View.VISIBLE);
	    	mPlusSignInButton.setVisibility(View.VISIBLE);
	       // btnSignOut.setVisibility(View.GONE);
	//   	mPlusSignOutButton.setVisibility(View.GONE); 
	       // btnRevokeAccess.setVisibility(View.GONE);
	//   	mPlusDisconnectButton.setVisibility(View.GONE);
	       // llProfileLayout.setVisibility(View.GONE);
	 //  	mPlusDisconnectButtonS.setVisibility(View.GONE);
	    }
	}
		
	
}