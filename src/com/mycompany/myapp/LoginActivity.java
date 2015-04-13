package com.mycompany.myapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.mycompany.myapp.helper.SQLiteHandler;
import com.mycompany.myapp.helper.SessionManager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.Certificate;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLSession;

import org.apache.http.NameValuePair;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************ In order for Google+ sign in
 * to work with your app, you must first go to:
 * https://developers.google.com/+/mobile
 * /android/getting-started#step_1_enable_the_google_api and follow the steps in
 * "Step 1" to create an OAuth 2.0 client for your package.
 */
//public class LoginActivity extends PlusBaseActivity implements
public class LoginActivity extends PlusBaseActivity implements LoaderCallbacks<Cursor> {
//public class LoginActivity implements LoaderCallbacks<Cursor> {

	//public LoginActivity(){
	//    this.context = getApplicationContext();
	//}
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	//private static final String[] DUMMY_CREDENTIALS = new String[] {
	//		"foo@example.com:hello", "bar@example.com:world" };
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// UI references.
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private View mProgressView;
	private View mEmailLoginFormView;
	private Button btnLinkToRegister;
	private SignInButton mPlusSignInButton;
	private View mSignOutButtons;
	private View mLoginFormView;
	private Context context;
	private SessionManager session;
	//private TextView registerErrorMsg;
	
    private String loginURL = "https://192.168.1.149";
	//  private String loginURL = "https://10.10.0.146";
    //private String loginURL = "https://localhost";
    static OutputStream os = null;
    static InputStream is = null;
    static String json_str = "";
    static JSONObject jObj = null;
    private JSONObject json = null;
    private static String KEY_SUCCESS = "success";
    private SQLiteHandler db;
  //  TextView registerErrorMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setupActionBar();
		
		
		// Session manager
        session = new SessionManager(getApplicationContext());
 
        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
	/*	// Find the Google+ sign in button.
		mPlusSignInButton = (SignInButton) findViewById(R.id.plus_sign_in_button);
		if (supportsGooglePlayServices()) {
			// Set a listener to connect the user when the G+ button is clicked.
			mPlusSignInButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					signIn();
				}
			});
		} else {
			// Don't offer G+ sign in if the app's version is too low to support
			// Google Play
			// Services.
			mPlusSignInButton.setVisibility(View.GONE);
			return;
		}
		*/

		// Set up the login form.
		mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
		populateAutoComplete();

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
		mEmailSignInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});

		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);
		mEmailLoginFormView = findViewById(R.id.email_login_form);
		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
	//	 registerErrorMsg = (TextView) findViewById(R.id.register_error);
		//mSignOutButtons = findViewById(R.id.plus_sign_out_buttons);
		
	//	HttpsURLConnection urlConnection = setUpHttpsConnection("https://192.168.1.142/");
		
		
		// Link to Register Screen
	    btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

	        public void onClick(View view) {
	            Intent i = new Intent(getApplicationContext(),
	                    RegisterActivity.class);
	            startActivity(i);
	            finish();
	        }
	    });
		
	}

	private void populateAutoComplete() {
		if (VERSION.SDK_INT >= 14) {
			// Use ContactsContract.Profile (API 14+)
			getLoaderManager().initLoader(0, null, this);
		} else if (VERSION.SDK_INT >= 8) {
			// Use AccountManager (API 8+)
			new SetupEmailAutoCompleteTask().execute(null, null);
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			//getActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();

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
			mAuthTask = new UserLoginTask(email, password);
			mAuthTask.execute((Void) null);
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
	protected void onPlusClientSignIn() {
		// Set up sign out and disconnect buttons.
		Button signOutButton = (Button) findViewById(R.id.plus_sign_out_button);
		signOutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				signOut();
			}
		});
		Button disconnectButton = (Button) findViewById(R.id.plus_disconnect_button);
		disconnectButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				revokeAccess();
			}
		});
	}

	@Override
	protected void onPlusClientBlockingUI(boolean show) {
		showProgress(show);
	}

	@Override
	protected void updateConnectButtonState() {
		// TODO: Update this logic to also handle the user logged in by email.
		boolean connected = getPlusClient().isConnected();

		//mSignOutButtons.setVisibility(connected ? View.VISIBLE : View.GONE);
		//mPlusSignInButton.setVisibility(connected ? View.GONE : View.VISIBLE);
		mEmailLoginFormView.setVisibility(connected ? View.GONE : View.VISIBLE);
	}

	@Override
	protected void onPlusClientRevokeAccess() {
		// TODO: Access to the user's G+ account has been revoked. Per the
		// developer terms, delete
		// any stored user data here.
	}

	@Override
	protected void onPlusClientSignOut() {

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
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	/**
	 * Use an AsyncTask to fetch the user's email addresses on a background
	 * thread, and update the email text field with results on the main UI
	 * thread.
	 */
	class SetupEmailAutoCompleteTask extends
			AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... voids) {
			ArrayList<String> emailAddressCollection = new ArrayList<String>();

			// Get all emails from the user's contacts and copy them to a list.
			ContentResolver cr = getContentResolver();
			Cursor emailCur = cr.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					null, null, null);
			while (emailCur.moveToNext()) {
				String email = emailCur
						.getString(emailCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				emailAddressCollection.add(email);
			}
			emailCur.close();

			return emailAddressCollection;
		}

		@Override
		protected void onPostExecute(List<String> emailAddressCollection) {
			addEmailsToAutoComplete(emailAddressCollection);
		}
	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		// Create adapter to tell the AutoCompleteTextView what to show in its
		// dropdown list.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				LoginActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

		mEmailView.setAdapter(adapter);
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	//public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
	public class UserLoginTask extends AsyncTask<Void, Void, JSONObject> {
		private final String mEmail;
		private final String mPassword;
		
		//private ProgressDialog progressDialog;
		//private Polling activity;
	//	private JSONParser_to_delete jsonParser;
		//private static final String loginURL = "http://davidjkelley.net/android_api/";
		//private static final String registerURL = "http://davidjkelley.net/android_api/";
		private static final String KEY_SUCCESS = "success";
		private static final String KEY_ERROR = "error";
		private static final String KEY_ERROR_MSG = "error_msg";
		private static final String KEY_UID = "uid";
		private static final String KEY_NAME = "name";
		private static final String KEY_EMAIL = "email";
		private static final String KEY_CGM_REG_ID = "cgmRegId";
		private static final String KEY_CREATED_AT = "created_at";
		private int responseCode = 0;
		
		private static final String login_tag = "login";
	  //  private static final String register_tag = "register";
	    private static final String question_tag = "question";
		

		UserLoginTask(String email, String password) {
			mEmail = email;
			mPassword = password;
		}

		@Override
		//protected Boolean doInBackground(Void... params) {
		protected JSONObject doInBackground(Void... params) {
			
			 //JSONParser jsonParser;
		//	UserFunctions userFunction = new UserFunctions();
			
		
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				//Thread.sleep(2000);
				
				// Tuki zacnemo copypejstat
				// PAZI, da ni DHCP spremenu naslova racunalnika!!!!
				//HttpsURLConnection urlConnection = null;
				//Connection.setHostnameVerifier(new BrowserCompatHostnameVerifier());
			
				//URL url = new URL("http://192.168.1.140");
				//HttpsURLConnection urlConnection = setUpHttpsConnection("https://192.168.1.140/android_api");
		    	HttpsURLConnection urlConnection = setUpHttpsConnection(loginURL);
			////	HttpsURLConnection urlConnection = setUpHttpsConnection("https://192.168.1.143/");
				//HttpsURLConnection urlConnection = setUpHttpsConnection("https://10.10.0.8/");
				//HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				//HttpsURLConnection urlConnection = setUpHttpsConnection("MiKo-PC");
				//HttpsURLConnection urlConnection = setUpHttpsConnection("http://10.10.0.8:3306/android_api/");
				// PAZI, da ni DHCP spremenu naslova racunalnika tu zgoraj!!!!
				Log.d("mycompany.myapp", "getSSLSocketFactory je: "+ urlConnection.getSSLSocketFactory());
				
			   // InputStream in = urlConnection.getInputStream();
				
				// Tuki nehamo copypejstat
				
				//URL url = new URL("https://192.168.1.141");
				//URLConnection urlConnection = url.openConnection();
				//InputStream in = urlConnection.getInputStream();
		/*	} catch (Exception e) {
				Log.e("mycompany.myapp", "setUpHttpsConnection v loginu ni uspel!" );
				e.printStackTrace();
				return false;
			}*/
			
			try {
			//	if (os == null) {
        			// do nothing
			//		 Log.d("getOutputStream Error", "os je null!");
        	//	} else {
        			os = urlConnection.getOutputStream();
        	//	}
        	} catch (Exception e) {  
                e.printStackTrace();
                Log.e("getOutputStream Error", "Error pri output stream je: " + e.toString());
            }
			
			  final List<NameValuePair> params_login = new ArrayList<NameValuePair>();
              params_login.add(new BasicNameValuePair("tag", login_tag));
             // params_reg.add(new BasicNameValuePair("name", name));
              params_login.add(new BasicNameValuePair("email", mEmail));
              params_login.add(new BasicNameValuePair("password", mPassword));
			
			BufferedWriter writer = new BufferedWriter(
        	        new OutputStreamWriter(os, "UTF-8"));
        	writer.write(getQuery(params_login));
        	writer.flush();
        	writer.close();
        	os.close();
			
        	is = urlConnection.getInputStream();
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
            
            
			} catch (Exception e) {  
                e.printStackTrace();
                Log.e("getOutputStream Error", "Error je: " + e.toString());
               // String ErrorLoginToast = "Login failed. Cannot connect to server :-(";
               // Toast.makeText(LoginActivity.this, ErrorLoginToast, Toast.LENGTH_LONG).show();
            }
        	
        	
		//	JSONObject json = userFunction.loginUser(mEmail, mPassword);
			//List paramtrs = userFunction.loginUser(mEmail, mPassword);
			//userFunction.loginUser(mEmail, mPassword);
			
			//JSONObject json = userFunction.getJSON(paramtrs);
			// Tuki urinemo UserFunction del
			
			//List<NameValuePair> paramtrs = new ArrayList<NameValuePair>();
	        //paramtrs.add(new BasicNameValuePair("tag", login_tag));
	        //paramtrs.add(new BasicNameValuePair("email", mEmail));
	        //paramtrs.add(new BasicNameValuePair("password", mPassword));
	        //Log.v("userfunctions", "loginuser");
	        //JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
	        //JSONObject json = jsonParser.execute(loginURL, params);
	        //jsonParser.execute(loginURL, params);
			
	        //JSONObject  onRequestCompleted(JSONObject result) {
	        //    //Hooray, here's my JSONObject for the Activity to use!
	        //	return result;
	        //}
	        
	        
			// Tuki nehamo urivat UserFunction del
			
			 /*  try {
	               // jObj = new JSONObject(json);
	            	if (json_str.isEmpty()) {
	            		 String ErrorLoginToast = "Login failed. Cannot connect to server :-(";
                         Toast.makeText(LoginActivity.this, ErrorLoginToast, Toast.LENGTH_LONG).show();
	            	} else {
	            		jObj = new JSONObject(json_str);
	            	}
	            } catch (JSONException e) {
	            //	Log.e("JSON Parser", "Error parsing data, to ne moremo zdzezonirat: " + json);
	                Log.e("JSON Parser", "Error parsing data " + e.toString());
	            }*/
			
			
// TUKI COPY-PEJSTAMO
			
			// check for login response
		  
		/*  catch (JSONException e) {
		    e.printStackTrace();
		    Log.e("mycompany.myapp", "Zgodil se je: JSONException" );
		  }*/
			
			
// TUKI NEHAMO COPY-PEJSTAT
			
// To spodaj zakomentiramo na hitro, ker je kopirano od nekje,
//		    kjer je provizoricno cekiralo dummy spremenljiuko, ne pa bazo
	/*		for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(mEmail)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mPassword);
				}
			}  */

			// TODO: register the new account here.
			//return true;
			  return jObj;
		}

		@Override
	//	protected void onPostExecute(final Boolean success) {
		protected void onPostExecute(JSONObject result) {
			mAuthTask = null;
			showProgress(false);
			if (result == null) {
				// do nothing
				String ErrorLoginToast = "Login failed. Cannot connect to server :-(";
                Toast.makeText(LoginActivity.this, ErrorLoginToast, Toast.LENGTH_LONG).show();
			} else {
			// Tuki kopiramo register
			 try {
				 json = result;
        	//	 Log.d("RegisterActivity", "json v Register Activity je: " +json.toString());
                 if (json.getString(KEY_SUCCESS) != null) {
                 	//Log.d("mycompany.myapp", "KEY_SUCCESS v Register Activity je uspeu!");
                  //   registerErrorMsg.setText("");
                    String res = json.getString(KEY_SUCCESS); 
           //          Log.d("mycompany.myapp", "json.getString v Register Activity je uspeu!");
                     Log.d("mycompany.myapp", "res v Login Activity je: " +res);
                     if(Integer.parseInt(res) == 1){
                         // user successfully login
                         // Store user details in SQLite Database
             ///            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        // SQLiteHandler db = new SQLiteHandler(getApplicationContext());
                         db = SQLiteHandler.getInstance(context);
                         JSONObject json_user = json.getJSONObject("user");
                         Log.d("mycompany.myapp", "json_user v LoginActivity je: " +json_user.getString("name"));
                         
                         // user successfully logged in
                         // Create login session
                         session.setLogin(true);
                         
                         // Clear all previous data in database
                  //       userFunction.logoutUser(getApplicationContext());
                         db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                        
                         // Launch Dashboard Screen
                         //Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                         // Close all views before launching Dashboard
                         //dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         //startActivity(dashboard);
                         //String WelcomeToast = "Sign up successful. Welcome, ".concat(json_user.toString()).concat("!");
                         String WelcomeToast = "Sign in successful. Welcome, " .concat(json_user.getString("name").toString()).concat("!");
                         Toast.makeText(LoginActivity.this, WelcomeToast, Toast.LENGTH_LONG).show();
                         
                         // Launch main activity
                         Intent intent = new Intent(LoginActivity.this,
                                 MainActivity.class);
                         startActivity(intent);
                         finish();
                         // Close Login Screen
                         finish();
                     }else{
                         // Error in login
                    	 mAuthTask = null;
             			 showProgress(false);
                      //   registerErrorMsg.setText("Error occurred during login");
                         Log.e("mycompany.myapp", "Error occurred during login");
                     }
                 }
             } catch (JSONException e) {
             	Log.e("mycompany.myapp", "KEY_SUCCESS ni uspel!");
                 e.printStackTrace();
             }
			 
			}
			
			  /*try {
					if (json.getString(KEY_SUCCESS) != null) {
					  String res = json.getString(KEY_SUCCESS);
					  Log.d("mycompany.myapp", "json.KEY_SUCCESS je uspel!" );
					   if(Integer.parseInt(res) == 1){
						//user successfully logged in
						// Store user details in SQLite Database
						DatabaseHandler db = new DatabaseHandler(getApplicationContext());
						JSONObject json_user = json.getJSONObject("user");
						// Clear all previous data in database
						userFunction.logoutUser(getApplicationContext());
						db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL),
						  json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
				                //Login Success
						responseCode = 1;
				 
					        }else{
						  responseCode = 0;
						//Error in login
						} 
					        }
				 
				  } catch (NullPointerException e) {
				    e.printStackTrace();
				    Log.e("mycompany.myapp", "Zgodil se je: NullPointerException" );
				 
				  }*/
			
			
			// Tuki nehamo kopirat register

			/*if (success) {
				finish();
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}*/
		}

	/*	@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}*/
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
	    
	
	@SuppressLint("SdCardPath")
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
	
	
}
