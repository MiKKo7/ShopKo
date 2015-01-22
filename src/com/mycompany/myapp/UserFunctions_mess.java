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

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
 
public class UserFunctions_mess {
 
    private JSONParser jsonParser;

  // private static String loginURL = "http://192.168.1.140:3306/android_api";
  // private static String registerURL = "http://192.168.1.140:3306/android_api";
    
   private static String loginURL = "https://192.168.1.140";
   private static String registerURL = "https://192.168.1.140";
    
    //private static String loginURL = "http://10.10.0.8:3306/android_api/";
    //private static String registerURL = "http://10.10.0.8:3306/android_api/";

    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String question_tag = "question";
 
    // constructor
    public UserFunctions_mess(){
        jsonParser = new JSONParser();
    }
    
     //login with user provided email/pass
  //  public JSONObject loginUser(String email, String password){
    public List loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        //Log.v("userfunctions", "loginuser");
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        //JSONObject json = jsonParser.execute(loginURL, params);
        //jsonParser.execute(loginURL, params);
        //return json
        return params;
        }
    //@Override
    
    public JSONObject getJSON(List<NameValuePair> paramtrs) {
    	 JSONParser jsonParser = new JSONParser();
    	jsonParser.execute(loginURL, paramtrs);
		return null;	
    }
   
    public JSONObject  onRequestCompleted(JSONObject result) {
        //Hooray, here's my JSONObject for the Activity to use!
    	return result;
       // return json;
    }
 
    //register a new user with name/email/pass
    public JSONObject registerUser(String name, String email, String password){
        // Building Parameters
    	//JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
 
        // getting JSON Object
        Log.d("mycompany.myapp", "Smo v UserFunctions, pred getting json object!");
        Log.d("mycompany.myapp", "registerURL je: " +registerURL);
        Log.d("mycompany.myapp", "params je: " +params);
       
        //new JSONParser().execute(registerURL, params);
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        //JSONObject json = getJSONFromUrl(registerURL, params);
        Log.d("mycompany.myapp", "Smo v UserFunctions, za getting json object!");
       // Log.d("mycompany.myapp", "json v UserFunctions je: " +json.toString());
        // return json
        return json;
    }
 
    //determine if the user is logged in
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
 
    //logout the user
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
    
// TUKAJ COPY-PEJSTAMO JSONParser class
    
    public class JSONParser extends AsyncTask<Object, Void, JSONObject> {
    	
    	//public interface MyCallbackInterface {
        //    public JSONObject onRequestCompleted(JSONObject result);
        //}
        InputStream is = null;
        JSONObject jObj = null;
        JSONObject[] jsonArray = null;
        String json = "";
        
 //       private MyCallbackInterface mCallback;
     
        // constructor
  //      public JSONParser(MyCallbackInterface callback) {
  //          mCallback = callback;
  //      }

     
      /*  public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {
        	 
            // Making HTTP request
        try {
        		//Provajmo mi z HttpURLConnection namesto z DefaultHttpClient
        	//URL serverUrl = new URL("https://192.168.1.140:443");
        	//HttpsURLConnection urlConnection = serverUrl.openConnection();
        	//HttpsURLConnection httpUrlConnection = null;
        	//URL serverUrl = new URL("https://192.168.1.140:443");
        	HttpsURLConnection urlConnection = setUpHttpsConnection(url);
        	//final HttpsURLConnection urlConnection = setUpHttpsConnection("https://192.168.1.140:443");
        	//urlConnection.setRequestMethod("GET");
        	
        	//DataOutputStream request = new DataOutputStream(HttpsURLConnection.getOutputStream());
        	urlConnection.setRequestMethod("POST");
        	urlConnection.setDoInput(true);
        	urlConnection.setDoOutput(true);
        	
        	    	
        	try {
        		OutputStream os = urlConnection.getOutputStream();
        		
        		BufferedWriter writer = new BufferedWriter(
        	    new OutputStreamWriter(os, "UTF-8"));
        		os.close();
        		} catch (Exception ex) {
        			Log.e("Buffer Error", "getOutputStream ne dela: " + ex.toString());
        			ex.printStackTrace();
        		}
        	
        	is = urlConnection.getInputStream();
       
        	/*
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                //httpPost.setEntity(new UrlEncodedFormEntity(params, "iso-8859-1"));
                Log.d("com.mycompany.myapp", "params v JSONParser je: " + params);
                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                //httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
     
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                
                is = httpEntity.getContent();
     */
        	
/*
            } catch (UnsupportedEncodingException e) {
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
      		};
     
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                		   is, "UTF-8"), 8);
                
                    //    is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    Log.v("err", line);
                    
                }
                is.close();
                json = sb.toString();
                //this is the error
                
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
            
            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(json);
            } catch (JSONException e) {
            	Log.e("JSON Parser", "Error parsing data, to ne moremo zdzezonirat: " + json);
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
     
            // return JSON String
            return jObj;
     
        }
        */
        @Override
        protected JSONObject doInBackground(Object... paramts) {
            String url = (String) paramts[0];
            List<NameValuePair> params = (List<NameValuePair>) paramts[1];
            
           // return getJSONFromUrl(url, params);
            // COPY PEJSTAMO
            
            // Making HTTP request
            try {
            		//Provajmo mi z HttpURLConnection namesto z DefaultHttpClient
            	//URL serverUrl = new URL("https://192.168.1.140:443");
            	//HttpsURLConnection urlConnection = serverUrl.openConnection();
            	//HttpsURLConnection httpUrlConnection = null;
            	//URL serverUrl = new URL("https://192.168.1.140:443");
            	HttpsURLConnection urlConnection = setUpHttpsConnection(url);
            	//final HttpsURLConnection urlConnection = setUpHttpsConnection("https://192.168.1.140:443");
            	//urlConnection.setRequestMethod("GET");
            	
            	//DataOutputStream request = new DataOutputStream(HttpsURLConnection.getOutputStream());
            	urlConnection.setRequestMethod("POST");
            	urlConnection.setDoInput(true);
            	urlConnection.setDoOutput(true);
            	
            	    	
            	try {
            		OutputStream os = urlConnection.getOutputStream();
            		
            		BufferedWriter writer = new BufferedWriter(
            	    new OutputStreamWriter(os, "UTF-8"));
            		os.close();
            		} catch (Exception ex) {
            			Log.e("Buffer Error", "getOutputStream ne dela: " + ex.toString());
            			ex.printStackTrace();
            		}
            	
            	is = urlConnection.getInputStream();
           
            	/*
                    // defaultHttpClient
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
                    //httpPost.setEntity(new UrlEncodedFormEntity(params, "iso-8859-1"));
                    Log.d("com.mycompany.myapp", "params v JSONParser je: " + params);
                    httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    //httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                    
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
         
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    
                    is = httpEntity.getContent();
         */
            	

                } catch (UnsupportedEncodingException e) {
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
          		};
         
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                    		   is, "UTF-8"), 8);
                    
                        //    is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        Log.v("err", line);
                        
                    }
                    is.close();
                    json = sb.toString();
                    //this is the error
                    
                } catch (Exception e) {
                    Log.e("Buffer Error", "Error converting result " + e.toString());
                }
                
                // try parse the string to a JSON object
                try {
                    jObj = new JSONObject(json);
                } catch (JSONException e) {
                	Log.e("JSON Parser", "Error parsing data, to ne moremo zdzezonirat: " + json);
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                }
         
                // return JSON String
                return jObj;
         
            }

            
            // COPY PEJSTAMO
        }
        
        //@Override
        protected void onPostExecute(JSONObject result) {
            //In here, call back to Activity or other listener that things are done
           // mCallback.onRequestCompleted(result);
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
                

                InputStream caInput = new BufferedInputStream(MainApplication.getContext().getAssets().open("ShopCoDejmo.crt"));
              
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
                
                // InputStream in = urlConnection.getInputStream();
                
                return urlConnection;
            }
            catch (Exception ex)
            {
                Log.e("mycompany.myapp", "Failed to establish SSL connection to server: " + ex.toString());
                return null;
            }
        }
        
     
    }
    
// TUKAJ NEHAMO COPY-PEJSTAT JSONParser class    
    
}