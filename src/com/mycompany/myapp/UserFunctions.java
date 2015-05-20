package com.mycompany.myapp;

import java.util.ArrayList;
import java.util.List;
 




import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
 




import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
 
public class UserFunctions {
     
    private JSONParser jsonParser;
    private JSONObject dzeson; 
    
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    //private static String loginURL = "http://10.0.2.2/ah_login_api/";
    //private static String registerURL = "http://10.0.2.2/ah_login_api/";
    
 //   private static String loginURL = "https://192.168.1.149";
    private static String loginURL = "https://10.10.0.146";
    //private static String loginURL = "https://10.10.0.8";
    
    //private static String registerURL = "https://192.168.1.140:443";
    
    private static String registerURL = "https://10.10.0.8";
  //  private static String registerURL = "https://192.168.1.149";
  //  private static String registerURL = "https://10.10.0.146";
     
    private static String login_tag = "login";
    private static String register_tag = "register";
     
    // constructor
   // public UserFunctions(){
   //     jsonParser = new JSONParser();
   // }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String email, String password){
        // Building Parameters
        final List<NameValuePair> params_login = new ArrayList<NameValuePair>();
        params_login.add(new BasicNameValuePair("tag", login_tag));
        params_login.add(new BasicNameValuePair("email", email));
        params_login.add(new BasicNameValuePair("password", password));
       // JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        
        // Tuki provajmo pejstat
        
        new JSONParser() {

            //@Override
            //protected Boolean doInBackground(URL... urls) {
            //    loadJSON(url);
            //}

            @Override
            protected void onPostExecute(JSONObject jsonData) {
               
                    // Getting Array of albums

                    //albums = json.getJSONArray(TAG_ALBUMS);
                    //sngs=json.getJSONArray(TAG_SONGS);
                    dzeson = jsonParser.getJSONFromUrl(loginURL, params_login);
                    		
                    // looping through All albums
               
            }
        }.execute(loginURL, params_login);
        
        // Tuki nehamo pejstat
          // return json
        // Log.e("JSON", json.toString());
        return dzeson;
    }
     
    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String name, String email, String password){
        // Building Parameters
        final List<NameValuePair> params_reg = new ArrayList<NameValuePair>();
        params_reg.add(new BasicNameValuePair("tag", register_tag));
        params_reg.add(new BasicNameValuePair("name", name));
        params_reg.add(new BasicNameValuePair("email", email));
        params_reg.add(new BasicNameValuePair("password", password));
         
        // getting JSON Object
        Log.d("mycompany.myapp", "Smo pred jsonParser.getJSONFromUrl(registerURL, params_reg)!");	
      //JSONObject json = jsonParser.getJSONFromUrl(registerURL, params_reg);
        new JSONParser().execute(registerURL, params_reg);
      Log.d("mycompany.myapp", "Smo za jsonParser.getJSONFromUrl(registerURL, params_reg)!");	
        
  // Tuki provajmo pejstat
        
       /* new JSONParser() {

            //@Override
            //protected Boolean doInBackground(URL... urls) {
            //    loadJSON(url);
            //}

            @Override
            protected void onPostExecute(JSONObject jsonData) {
               
                    // Getting Array of albums

                    //albums = json.getJSONArray(TAG_ALBUMS);
                    //sngs=json.getJSONArray(TAG_SONGS);
            	Log.d("mycompany.myapp", "Smo pred dzeson v onPostExecute!");	
                    dzeson = jsonParser.getJSONFromUrl(loginURL, params_reg);
                    Log.d("mycompany.myapp", "Smo za dzeson v onPostExecute!");	
                    // looping through All albums
               
            }
        }.execute(loginURL, params_reg);*/
       
        // Tuki nehamo pejstat
          // return json
        // Log.e("JSON", json.toString());
        return dzeson;

        // return json
    }
     
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
     
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
     
}