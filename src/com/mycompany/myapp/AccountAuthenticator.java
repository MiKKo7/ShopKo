package com.mycompany.myapp;

import com.mycompany.myapp.MainActivity.globalAccess;

import android.accounts.AbstractAccountAuthenticator;  
import android.accounts.Account;  
import android.accounts.AccountAuthenticatorResponse;  
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;  
import android.content.Context;  
import android.content.Intent;
import android.os.Bundle;  
import android.provider.SyncStateContract.Constants;
import android.util.Log;
  
public class AccountAuthenticator extends AbstractAccountAuthenticator {  
  
	 private Context mContext;
	 
    public AccountAuthenticator(Context context) {  
        super(context);  
        mContext = context;
     //   mContext = globalAccess.kontext;
        
        // TODO Auto-generated constructor stub  
    }  
  
    @Override  
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options)  
        throws NetworkErrorException {  
        
      final Bundle result;  
      final Intent intent;  
   //   String accountType1 = "com.mycompany.myapp";
                      
    //  intent = new Intent(this.mContext, AuthenticationActivity.class);
      intent = new Intent(mContext, AuthenticationActivity.class);
      intent.putExtra(AuthenticationActivity.PARAM_AUTHTOKEN_TYPE, authTokenType);  
      intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);  
      
      result = new Bundle();  
      result.putParcelable(AccountManager.KEY_INTENT, intent);  
              
      return result;
    //	 return null;
    }  
  
    @Override  
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    @Override  
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    @Override  
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    @Override  
    public String getAuthTokenLabel(String authTokenType) {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    @Override  
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    @Override  
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
}  
