package com.mycompany.myapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

//import com.mycompany.myapp.MainActivity.GcmIntentService;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    	Log.i("com.mycompany.myapp", "Smo v GcmBroadcastReceiver!");
    	Log.i("com.mycompany.myapp", "GcmIntentService v GcmBroadcastReceiver je: " + GcmIntentService.class.getName());
        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
