package com.mycompany.myapp;

import org.json.JSONException;
import org.json.JSONObject;
 



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class RegisterActivity extends LoginActivity {
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
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
 
        // Importing all assets like buttons, text fields
        inputFullName = (EditText) findViewById(R.id.registerName);
        inputEmail = (EditText) findViewById(R.id.email_register);
        inputPassword = (EditText) findViewById(R.id.password_register);
        btnRegister = (Button) findViewById(R.id.email_register_button);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLogInScreen);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
         
        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {         
            public void onClick(View view) {
            	Log.d("mycompany.myapp", "Smo v register onClick!");
                String name = inputFullName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                Log.d("mycompany.myapp", "Smo pred new UserFunctions!");
                UserFunctions userFunction = new UserFunctions();
                Log.d("mycompany.myapp", "Smo pred registerUser!");
                JSONObject json = userFunction.registerUser(name, email, password);
                Log.d("mycompany.myapp", "Smo v register, za json register user!");
                // check for login response
                try {
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
            }
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
}