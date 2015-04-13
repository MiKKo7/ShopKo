package com.mycompany.myapp;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.os.Bundle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class MainApplication extends Application {
    private static MainApplication instance = new MainApplication();

    public MainApplication() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
       // Parse.initialize(this, "DibF0HiaV6L8X709dfr9ogz5StQCKLGuO3F2Ph8I", "Yont4omFCs6auCQ2hSBPKyNAfvrazVymurkjN2lC");
        //PushService.setDefaultPushCallback(this, MainActivity.class);
       /* PushService.subscribe(this, "majcka", MainActivity.class);*/
       // ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
