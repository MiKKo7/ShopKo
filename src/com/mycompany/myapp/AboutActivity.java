package com.mycompany.myapp;

import com.google.android.gms.common.SignInButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutActivity extends MainActivity{
	
	private Button okButton;

	 @Override
	    public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_about);
			
			okButton = (Button) findViewById(R.id.license_agree_reject);
			
				// Set a listener to connect the user when the G+ button is clicked.
			okButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						 Intent intent = new Intent(AboutActivity.this,
                        		 MainActivity.class);
                        startActivity(intent);
                         finish();
					}   
			});
		}
}