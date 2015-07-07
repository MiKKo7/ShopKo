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
import java.util.HashMap;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.mycompany.myapp.app.Config;
import com.mycompany.myapp.helper.SQLiteHandler;
import com.mycompany.myapp.helper.SessionManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Process;
 
@SuppressLint("NewApi")
public class ItemFollow {
    Button btnRegister;
    Button btnLinkToLogin;
    EditText inputFullName;
    EditText inputEmail;
    EditText inputPassword;
    TextView registerErrorMsg;
     
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
    
    private static String KEY_UNIQUE_ID = "item_unique_id";
    private static String KEY_BARCODE = "barcode"; // Barcode
    private static String KEY_ITEM_BRAND = "brand";
    private static String KEY_ITEM_NAME = "name";
    private static String KEY_ITEM_DESCRIPTION = "description";
    private static String KEY_ITEM_PRICE = "price";
    private static String KEY_ITEM_DISCOUNT = "discount";
    private static String KEY_ITEM_CREATED_AT = "created_at";
    private static String KEY_UNIQUE_PLACE_ID = "unique_place_id"; // Unique place id
    private static String KEY_ITEM_FOLLOWED_AT = "item_followed_at"; 
    //private static String KEY_SHOP_ID = "shop_id"; // Shop id
    private static String KEY_FLAG_ITEM_NOT_SYNCHRONIZED = "flag_item_not_synchronized"; // Flag item not synchronized
    
    
    static final String TAG = "mycompany.myapp";
    
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    
    private static String itemFollow_tag = "itemFollow";
    
    public static final int THREAD_PRIORITY_BACKGROUND = 10;
    
    private String registerURL = Config.URL_MYSQL_SERVER;;
  //  private String registerURL = "https://192.168.1.149";
  //  private String registerURL = "https://10.10.0.146";
    //private String registerURL = "https://localhost";
    
    GoogleCloudMessaging gcm;
 
    static InputStream is = null;
    static OutputStream os = null;
    static JSONObject jObj = null;
    //static String json = "";
    static String json_str = "";
    private JSONObject json = null;
    String regId;
    
    private SessionManager session;
	public SQLiteHandler db;
	public String userUniqueId;
	public String barCode;
	public String uniquePlaceId;
	
	public Uri imagePathUri;
	
	//public boolean databasesInSync;
    
	String regid;
	
	/**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    String SENDER_ID = "440103715165";
    
    public Context context;
    
    public String imagePath;
    
    public Context djukla;
    
    public TextView bla;
    
    public Activity activityMain;
    
    public static HttpsURLConnection urlConnection;

  //save the context received via constructor in a local variable

  public ItemFollow(Context context){
      this.context = context;
 //     Activity activityMain = (Activity) context;
      djukla = context;
 //     bla = djukla.itemDescriptionTxt;
  }
    
    public void Follow(String user_unique_id, String barcode, String unique_place_id, Uri image_Path, String item_followed_at) {
 // public void Follow(String user_unique_id, String barcode, String unique_place_id, Uri image_Path) {
 
    	
    	
        // Session manager
   //     session = new SessionManager(MainActivity.context);
 
        // SQLite database handler
      // db = new SQLiteHandler(context);
    	 db = SQLiteHandler.getInstance(context);
    	 userUniqueId = user_unique_id;
    	imagePathUri = image_Path;
    	 imagePath = getPath(context, image_Path);
    	// Uri.parse(imagePath);
    	 barCode = barcode;
    	 uniquePlaceId = unique_place_id;
    	 
    	 Log.d("ItemFollow", "imagePath v Follow je: "+imagePath);
 
        // Check if user is already logged in or not
//        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
  //          Intent intent = new Intent(RegisterActivity.this,
  //                  MainActivity.class);
//            startActivity(intent);
//            finish();
 //       }
        
 //       if (checkPlayServices()) {
    		
 //       	gcm = GoogleCloudMessaging.getInstance(this);
 //       	regId = getRegistrationId(getApplicationContext());
 //       	if (regId.isEmpty()) {
 //   		registerInBackground();
 //       	}
 //       } else {
 //           Log.i(TAG, "No valid Google Play Services APK found.");
 //       }
        
        
        // Register Button Click event
  /*      btnRegister.setOnClickListener(new View.OnClickListener() {         
            public void onClick(View view) {
            	Log.d("mycompany.myapp", "Smo v register onClick!");
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
        */         
   // 	 Time today = new Time(Time.getCurrentTimezone());
   // 	 today.setToNow();
    	 
                 final List<NameValuePair> params_reg = new ArrayList<NameValuePair>();
                 Log.d("mycompany.myapp", "itemFollow_tag je: " +itemFollow_tag);
                 Log.d("mycompany.myapp", "uid je: " +user_unique_id);
                 Log.d("mycompany.myapp", "barcode je: " +barcode);
                 Log.d("mycompany.myapp", "unique_place_id je: " +unique_place_id);
                 Log.d("mycompany.myapp", "item_followed_at je: " +item_followed_at);
                 params_reg.add(new BasicNameValuePair("tag", itemFollow_tag));
                 params_reg.add(new BasicNameValuePair("uid", user_unique_id));
                 params_reg.add(new BasicNameValuePair("barcode", barcode));
                 params_reg.add(new BasicNameValuePair("unique_place_id", unique_place_id));
                 params_reg.add(new BasicNameValuePair("item_followed_at", item_followed_at));
                 //params_reg.add(new BasicNameValuePair("item_followed_at", today.toString()));
                 //getDateTime()
               //  params_reg.add(new BasicNameValuePair("server_image_path", server_image_path));
                 
                // params_reg.add(new BasicNameValuePair("name", name));
                // params_reg.add(new BasicNameValuePair("email", email));
                // params_reg.add(new BasicNameValuePair("password", password));
                // params_reg.add(new BasicNameValuePair("gcmRegId", regId));
                 
                 Log.d("mycompany.myapp", "Smo pred jsonParser v ItemFollow!");	
                 //JSONObject json = jsonParser.getJSONFromUrl(registerURL, params_reg);
              //    new JSONParser_IN().execute(registerURL, params_reg);
                 new JSONParser_IN().execute(params_reg);
                 
                 // Launch main activity
                 
         ////        ((MainActivity) djukla).redrawGallery();
          //   	activityMain.recreate();
             	
                 // Saving pic to server
                 
            /*     Intent intent = new Intent(context, UploadImage.class);
                 Log.d("ItemFollow", "imagePath v ItemFollow je: " +imagePath);
                 intent.putExtra("IMAGE_PATH", imagePath);
                 intent.putExtra("SERVER_IMAGE_PATH", );
                 context.startActivity(intent);*/
                 
                 Log.d("mycompany.myapp", "Smo za jsonParser za ItemFollow!");	
                 
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
          }*/
 //       });
 
        
        // Link to Login Screen
    /*    btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                // Close Registration View
                finish();
            }
        });*/
    }
    
    
  /*  public boolean itemExistsInBothDb(String barcode, String uniquePlaceId) {
    
    	// follow.itemExistsInBothDb(scanResult.getContents(), uniquePlaceId)	
        final List<NameValuePair> params_reg = new ArrayList<NameValuePair>();
        Log.d("mycompany.myapp", "itemFollow_tag je: " +itemFollow_tag);
        Log.d("mycompany.myapp", "barcode je: " +barcode);
        Log.d("mycompany.myapp", "shop_id je: " +shop_id);
        params_reg.add(new BasicNameValuePair("tag", itemFollow_tag));
        params_reg.add(new BasicNameValuePair("uid", user_unique_id));
        params_reg.add(new BasicNameValuePair("barcode", barcode));
        params_reg.add(new BasicNameValuePair("shop_id", shop_id));
       // params_reg.add(new BasicNameValuePair("name", name));
       // params_reg.add(new BasicNameValuePair("email", email));
       // params_reg.add(new BasicNameValuePair("password", password));
       // params_reg.add(new BasicNameValuePair("gcmRegId", regId));
        
        Log.d("mycompany.myapp", "Smo pred jsonParser v ItemFollow!");	
        //JSONObject json = jsonParser.getJSONFromUrl(registerURL, params_reg);
     //    new JSONParser_IN().execute(registerURL, params_reg);
        new JSONParser_IN().execute(params_reg);
    	
    	return true;
    } */	
    //TUKAJ KOPIRAMO JSONParser
    
    
    public class JSONParser_IN extends AsyncTask<List<NameValuePair>, Void, JSONObject> {
    	 
       /* static InputStream is = null;
        static JSONObject jObj = null;
        static String json = "";*/
 //   	public final int THREAD_PRIORITY_BACKGROUND = 10;
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
            	
          //  	try {
            	HttpsURLConnection conn = setUpHttpsConnection(registerURL);
              	Log.d("ItemFollow", "Smo za setUpHttpsConnection()!");
          //  	os = conn.getOutputStream();
          //   	is = conn.getInputStream();
           /* 	} catch (Exception e) {  
                    e.printStackTrace();
                    Log.e("getOutputStream Error", "Error je: " + e.toString());
                	String ErrorFollowToast = "Cannot follow item. Cannot connect to server :-(";
                    //   Toast.makeText(MainActivity.context, ErrorFollowToast, Toast.LENGTH_LONG).show();
       				   Toast.makeText(context, ErrorFollowToast, Toast.LENGTH_LONG).show();
                }*/
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
            //	is = conn.getInputStream();
            	
            	try {
            //		if (os == null) {
            //			// do nothing
            //		} else {
            			os = conn.getOutputStream();
            //		}
            	} catch (Exception e) {  
                    e.printStackTrace();
                    Log.e("getOutputStream Error", "Error je: " + e.toString());
                    String ErrorFollowToast = "Cannot follow item. Cannot connect to server :-(";
                    //   Toast.makeText(MainActivity.context, ErrorFollowToast, Toast.LENGTH_LONG).show();
       				   Toast.makeText(context, ErrorFollowToast, Toast.LENGTH_LONG).show();
                }
            	
            	Log.d("ItemFollow", "Smo za conn.getOutputStream()!");
            	/*BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
            	 String inputLine;
                 while ((inputLine = in.readLine()) != null) 
                     System.out.println(inputLine);*/
            
            	
            	
            	BufferedWriter writer = new BufferedWriter(
            	        new OutputStreamWriter(os, "UTF-8"));
            	Log.d("ItemFollow", "Smo za OutputStreamWriter!");
            	try {
            		writer.write(getQuery(params));
            	} catch (Exception e) {
            		 e.printStackTrace();
                     Log.e("OutputStreamWriter Error", "Error je: " + e.toString());
            	}
            	Log.d("ItemFollow", "Smo za write(getQuery(params))!");
            	writer.flush();
            	Log.d("ItemFollow", "Smo za flush()!");
            	writer.close();
            	Log.d("ItemFollow", "Smo za close()!");
            	os.close();
                Log.d("ItemFollow", "smo za os.close()");
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
     
            try {is = conn.getInputStream();} catch (Exception e) {  
                e.printStackTrace();
                Log.e("getOutputStream Error", "Error je: " + e.toString());
                String ErrorFollowToast = "Cannot follow item. Cannot connect to server :-(";
                //   Toast.makeText(MainActivity.context, ErrorFollowToast, Toast.LENGTH_LONG).show();
   				   Toast.makeText(context, ErrorFollowToast, Toast.LENGTH_LONG).show();
            }
           // 	is = conn.getInputStream();
           
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                		   is, "UTF-8"), 8);
            
                Log.d("Item", "smo za BufferedReader");
                    //    is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    Log.v("Line je: ", line);
                    }
                is.close();
   //             conn.disconnect();
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
            		Log.e("JSON Parser", "json_str je prazen v ItemFollow!");
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

      //  protected void onPostExecute(JSONObject result) {
        protected void onPostExecute(JSONObject result) {
            //In here, call back to Activity or other listener that things are done
           //mCallback.onRequestCompleted(result);
        	if (result == null) {
				// do nothing
		//		String ErrorFollowToast = "Cannot follow item. Cannot connect to server :-(";
				String ErrorFollowToast = "A problem occurred: item not in database or item has already been followed.";
             //   Toast.makeText(MainActivity.context, ErrorFollowToast, Toast.LENGTH_LONG).show();
				   Toast.makeText(context, ErrorFollowToast, Toast.LENGTH_LONG).show();
			} else {
				
        	 try {
        		 json = result;
        		 Log.d("ItemFollow", "json v ItemFollow je: " +json.toString());
                 if (json.getString(KEY_SUCCESS) != null) {
                 	Log.d("mycompany.myapp", "KEY_SUCCESS v ItemFollow je uspeu!");
                    // registerErrorMsg.setText("");
                     String res = json.getString(KEY_SUCCESS); 
                     Log.d("mycompany.myapp", "json.getString v ItemFollow je uspeu!");
                     Log.d("mycompany.myapp", "res v ItemFollow je: " +res);
                     if(Integer.parseInt(res) == 1){
                         // user successfully registred
                         // Store user details in SQLite Database
             ///            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
             //            SQLiteHandler db = new SQLiteHandler(getApplicationContext());
                    	
                       /*  JSONObject json_brand = json.getJSONObject("brand");
                         Log.d("mycompany.myapp", "json_brand v ItemFollow je: " +json_brand.getString("brand"));
                         JSONObject json_name = json.getJSONObject("name");
                         Log.d("mycompany.myapp", "json_name v ItemFollow je: " +json_name.getString("name"));
                         JSONObject json_description = json.getJSONObject("description");
                         Log.d("mycompany.myapp", "json_description v ItemFollow je: " +json_name.getString("description"));
                         JSONObject json_price = json.getJSONObject("price");
                         Log.d("mycompany.myapp", "json_price v ItemFollow je: " +json_name.getString("price"));
                         JSONObject json_discount = json.getJSONObject("discount");
                         Log.d("mycompany.myapp", "json_discount v ItemFollow je: " +json_name.getString("discount"));
                         // Clear all previous data in database
                  //       userFunction.logoutUser(getApplicationContext());*/
                         JSONObject json_item = json.getJSONObject("item");
                         

                         
                         db.deleteSingleItem(barCode, uniquePlaceId);
            //             db.deleteItems();   // TA JE SAMO ZA DEVELOPMENT!!! JE TREBA ZAKOMENTIRAT POTEM!
                         //db.addItem(json_item.getString(KEY_BARCODE),json_item.getString(KEY_ITEM_BRAND), json_item.getString(KEY_ITEM_NAME), json_item.getString(KEY_ITEM_DESCRIPTION), json_item.getString(KEY_ITEM_CREATED_AT), json_item.getDouble(KEY_ITEM_PRICE), json_item.getInt(KEY_ITEM_DISCOUNT), json_item.getString(KEY_UNIQUE_ID), json_item.getString(KEY_UNIQUE_PLACE_ID), imagePathUri.getEncodedPath(),  0);
                      //   db.addItem(json_item.getString(KEY_BARCODE),json_item.getString(KEY_ITEM_BRAND), json_item.getString(KEY_ITEM_NAME), json_item.getString(KEY_ITEM_DESCRIPTION), json_item.getString(KEY_ITEM_CREATED_AT), json_item.getDouble(KEY_ITEM_PRICE), json_item.getInt(KEY_ITEM_DISCOUNT), json_item.getString(KEY_UNIQUE_ID), json_item.getString(KEY_UNIQUE_PLACE_ID), imagePath,  0);
                    //     db.addItem(json_item.getString(KEY_BARCODE),json_item.getString(KEY_ITEM_BRAND), json_item.getString(KEY_ITEM_NAME), json_item.getString(KEY_ITEM_DESCRIPTION), "NOW()", json_item.getDouble(KEY_ITEM_PRICE), json_item.getInt(KEY_ITEM_DISCOUNT), json_item.getString(KEY_UNIQUE_ID), json_item.getString(KEY_UNIQUE_PLACE_ID), imagePath,  0);
                         db.addItem(json_item.getString(KEY_BARCODE),json_item.getString(KEY_ITEM_BRAND), json_item.getString(KEY_ITEM_NAME), json_item.getString(KEY_ITEM_DESCRIPTION), json_item.getDouble(KEY_ITEM_PRICE), json_item.getString(KEY_ITEM_DISCOUNT), json_item.getInt(KEY_ITEM_DISCOUNT), json_item.getString(KEY_UNIQUE_ID), json_item.getString(KEY_UNIQUE_PLACE_ID), json_item.getString(KEY_ITEM_FOLLOWED_AT), imagePath,  0);
                   //      db.close();
                        //(String brand, String name, String description, String item_created_at, Double price, Integer discount, String item_unique_id) {
                         // db.addItem(json.getString(KEY_ITEM_BRAND), json.getString(KEY_ITEM_NAME), json.getString(KEY_ITEM_DESCRIPTION), json.getString(KEY_ITEM_CREATED_AT), json.getDouble(KEY_ITEM_PRICE), json.getInt(KEY_ITEM_DISCOUNT), json.getString(KEY_UNIQUE_ID));
                        // session.setLogin(true);
                         // Launch Dashboard Screen
                         //Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                         // Close all views before launching Dashboard
                         //dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         //startActivity(dashboard);
                         //String WelcomeToast = "Sign up successful. Welcome, ".concat(json_user.toString()).concat("!");
                    //     String WelcomeToast = "Following item " .concat(json_item.getString("item_name").toString()).concat("!");
                       //  String WelcomeToast = "Following " .concat(json_item.getString("name").toString()).concat("!");
                         String WelcomeToast = "Following " .concat(json_item.getString("name").toString()).concat(" ").concat(json_item.getString("brand").toString()).concat("!");
                         Toast.makeText(context, WelcomeToast, Toast.LENGTH_LONG).show();
                         // Launch login activity
                   /*      Intent intent = new Intent(
                                 RegisterActivity.this,
                                 LoginActivity.class);
                         startActivity(intent);*/
                         // Launch main activity
                    /*     Intent intent_back = new Intent(RegisterActivity.this,
                                 MainActivity.class);
                         startActivity(intent_back);*/
               
                         
                         // Provajmo dat u loceni thread, da ne bo tako ruzlo UI
                       /*  Thread t = new Thread(new Runnable() {
                        	  @Override
                              public void run() {
                        		   // Start thread with low priority.
                        	        Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND);*/

                         Intent UploadPicIntent = new Intent(context, UploadImage.class);
                         Log.d("ItemFollow", "imagePath v ItemFollow je: " +imagePath);
                         UploadPicIntent.putExtra("IMAGE_PATH", imagePath);
                         
                         UploadPicIntent.putExtra("SERVER_IMAGE_NAME", json_item.getString(KEY_UNIQUE_ID));
						
                         UploadPicIntent.putExtra("UNIQUE_USER_ID", userUniqueId);
                         context.startActivity(UploadPicIntent);
                     //    }  
                      //   });
                       //  t.start();
                         
                         // Refreshamo database instance
                         db = SQLiteHandler.getInstance(context);
                         // Launch main activity
                         ((MainActivity) djukla).redrawGallery();
                         

                         // Close Registration Screen
                         //finish();
                     }else{
                         // Error in registration
                         registerErrorMsg.setText("Already following item or item not in database");
                         Log.e("mycompany.myapp", "Already following item or item not in database");
                         String WelcomeToast = "Item already followed or item not in database"; 
                         Toast.makeText(context, WelcomeToast, Toast.LENGTH_LONG).show();
                     }
                 }
             } catch (JSONException e) {
             	Log.e("mycompany.myapp", "KEY_SUCCESS ni uspel v ItemFollow!");
                 e.printStackTrace();
             }
        }
        //	MainActivity.globalAccess.databasesInSync = true;
        	urlConnection.disconnect();
        
        }
            
        // COPY PEJSTAMO
        // TUKAJ KOPIRAMO JSONParser: KONEC
    }
    
    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Log.d("mycompany.myapp", "params.toString() v getQuery je: " +params.toString());
        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
           // result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
           result.append(URLEncoder.encode(pair.getValue(), "UTF-8").replace("%3A", ":").replace("+", "%20"));
           // URLEncoder.encode(theUrl).replace("%3A", ":");
        }
        Log.d("mycompany.myapp", "result.toString v getQuery je: " +result.toString());
        return result.toString();
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
            InputStream caInput = new BufferedInputStream(context.getAssets().open("ShopCoDejmo.crt"));
          
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
        //    HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
            urlConnection = (HttpsURLConnection)url.openConnection();
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
	
/*    private String getRegistrationId(Context context) {
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
    */
 /*   private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the registration ID in your app is up to you.
	    return getSharedPreferences(MainActivity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
    }*/
    
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
    
/*	private void registerInBackground() {
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
	*/
	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param context application's context.
	 * @param regId registration ID
	 */
/*	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
	
	
	// To spodaj je za dobit string iz Uri
	
	*/	
	/**
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @author paulburke
	 */
	@SuppressLint("NewApi")
	public static String getPath(final Context context, final Uri uri) {

	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	    // DocumentProvider
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            if ("primary".equalsIgnoreCase(type)) {
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }

	            // TODO handle non-primary volumes
	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) {

	            final String id = DocumentsContract.getDocumentId(uri);
	            final Uri contentUri = ContentUris.withAppendedId(
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	            return getDataColumn(context, contentUri, null, null);
	        }
	        // MediaProvider
	        else if (isMediaDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } else if ("video".equals(type)) {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } else if ("audio".equals(type)) {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] {
	                    split[1]
	            };

	            return getDataColumn(context, contentUri, selection, selectionArgs);
	        }
	    }
	    // MediaStore (and general)
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {
	        return getDataColumn(context, uri, null, null);
	    }
	    // File
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
	        return uri.getPath();
	    }

	    return null;
	}
	
	/**
	 *  Close the connection
	 */
	
	public void closeConnection() {
        urlConnection.disconnect();
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @param selection (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection,
	        String[] selectionArgs) {

	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = {
	            column
	    };

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int column_index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(column_index);
	        }
	    } finally {
	        if (cursor != null)
	            cursor.close();
	    }
	    return null;
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
	    return "com.android.providers.media.documents".equals(uri.getAuthority());
	}
	
}
