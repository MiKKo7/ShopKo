package com.mycompany.myapp;


import android.accounts.Account;  
import android.accounts.AccountAuthenticatorActivity;  
import android.accounts.AccountManager;  
import android.content.ContentResolver;  
import android.content.Intent;  
import android.content.SharedPreferences;  
import android.graphics.Color;  
import android.os.Bundle;  
import android.util.Log;
import android.view.View;  
import android.widget.TextView;  
  
public class AuthenticationActivity extends AccountAuthenticatorActivity {  
//public class AuthenticationActivity extends Activity {
    public static final String PARAM_AUTHTOKEN_TYPE = "auth.token";
 //   public static final String PARAM_AUTHTOKEN_TYPE = "com.mycompany.myapp";
    public static final String PARAM_CREATE = "create";  
  
    public static final int REQ_CODE_CREATE = 1;  
  
    public static final int REQ_CODE_UPDATE = 2;  
  
    public static final String EXTRA_REQUEST_CODE = "req.code";  
  
    public static final int RESP_CODE_SUCCESS = 0;  
  
    public static final int RESP_CODE_ERROR = 1;  
  
    public static final int RESP_CODE_CANCEL = 2;  
  
    @Override  
    protected void onCreate(Bundle icicle) {  
        // TODO Auto-generated method stub  
        super.onCreate(icicle);  
        setContentView(R.layout.main);
        Log.d("AuthenticatorActivity", "Smo u onCreate!");
       
     /*   Intent intent = new Intent(this,
                LoginActivity.class);
        startActivity(intent);*/
      //  finish();
     //   this.setContentView(R.layout.user_credentials);  
 //   }  
  
 /*   public void onCancelClick(View v) {  
  
        this.finish();  
  
    }  */
  
  /*  //public void onSaveClick(View v) {  
        TextView tvUsername;  
        TextView tvPassword;  
        TextView tvApiKey;  
        String username;  
        String password;  
        String apiKey;  
        boolean hasErrors = false;  */
  
 //       tvUsername = (TextView) this.findViewById(R.id.uc_txt_username);  
 //       tvPassword = (TextView) this.findViewById(R.id.uc_txt_password);  
 //       tvApiKey = (TextView) this.findViewById(R.id.uc_txt_api_key);  
  
 /*       tvUsername.setBackgroundColor(Color.WHITE);  
        tvPassword.setBackgroundColor(Color.WHITE);  
        tvApiKey.setBackgroundColor(Color.WHITE);  
  
        username = tvUsername.getText().toString();  
        password = tvPassword.getText().toString();  
        apiKey = tvApiKey.getText().toString();  */
  
       /* if (username.length() < 3) {  
            hasErrors = true;  
            tvUsername.setBackgroundColor(Color.MAGENTA);  
        }  
        if (password.length() < 3) {  
            hasErrors = true;  
            tvPassword.setBackgroundColor(Color.MAGENTA);  
        }  
        if (apiKey.length() < 3) {  
            hasErrors = true;  
            tvApiKey.setBackgroundColor(Color.MAGENTA);  
        }  */
  
 //   public void onStart(View v) {
    
     /*   if (hasErrors) {  
        	Log.d("AuthenticatorActivity", "hasErrors 1 se je zgodiu :-(");
            return;  
        }  
          */
        // Now that we have done some simple "client side" validation it  
        // is time to check with the server  
  
        // ... perform some network activity here  
  
        // finished  
  
        String accountType = this.getIntent().getStringExtra(PARAM_AUTHTOKEN_TYPE);  
        if (accountType == null) {  
          //  accountType = AccountAuthenticator.ACCOUNT_TYPE;
        	  accountType = "com.mycompany.myapp";
        }  
        accountType = "com.mycompany.myapp";
        AccountManager accMgr = AccountManager.get(this);  
  
      /*  if (hasErrors) {  
  
            // handel errors  
        	Log.d("AuthenticatorActivity", "hasErrors 2 se je zgodiu :-(");
        } else {  */
  
            // This is the magic that addes the account to the Android Account Manager  
        	 Bundle bundle = getIntent().getExtras();
        	 

        //    String usernameAuth = bundle.getString("username");
        //    String passwordAuth = bundle.getString("password");
            String usernameAuth = "pinko@yahoo.com";
            String passwordAuth = "ernuvolo";
            final Account account = new Account(usernameAuth, accountType);  
            accMgr.get(this).addAccountExplicitly(account, passwordAuth, null);  
            Log.d("AuthenticatorActivity", "Smo za addAccountExplicitly!");
  
            // Now we tell our caller, could be the Andreoid Account Manager or even our own application  
            // that the process was successful  
  
            String accountType1  = "com.mycompany.myapp";
            final Intent intent = new Intent();  
            intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, usernameAuth);  
            intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType1);  
       //     intent.putExtra(AccountManager.KEY_AUTHTOKEN, accountType1);  
            
            this.setAccountAuthenticatorResult(intent.getExtras());  
            this.setResult(RESULT_OK, intent);  
            this.finish();  
  
        //}  
    }  
  
    @Override  
    protected void onResume() {  
        // TODO Auto-generated method stub  
        super.onResume();  
        Log.d("AuthenticatorActivity", "Smo u onResume!");
 
    }
}  
