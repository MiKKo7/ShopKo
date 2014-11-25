package com.mycompany.myapp;
import java.util.ArrayList;
import java.util.List;
 



import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 



import android.content.Context;
import android.util.Log;
 
public class UserFunctions {
 
    private JSONParser jsonParser;

  // private static String loginURL = "http://192.168.1.140:3306/android_api";
  // private static String registerURL = "http://192.168.1.140:3306/android_api";
    
   private static String loginURL = "https://192.168.1.142";
   private static String registerURL = "https://192.168.1.142";
    
    //private static String loginURL = "http://10.10.0.8:3306/android_api/";
    //private static String registerURL = "http://10.10.0.8:3306/android_api/";

    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String question_tag = "question";
 
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
    
     //login with user provided email/pass
    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        //Log.v("userfunctions", "loginuser");
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        return json;
    }
 
    //register a new user with name/email/pass
    public JSONObject registerUser(String name, String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
 
        // getting JSON Object
        Log.d("mycompany.myapp", "Smo v UserFunctions, pred getting json object!");
        Log.d("mycompany.myapp", "registerURL je: " +registerURL);
        Log.d("mycompany.myapp", "params je: " +params);
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
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
}