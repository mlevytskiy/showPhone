package com.github.overlayApp;

import android.content.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by max on 08.01.16.
 */
public abstract class ActivityOverOther extends AppCompatActivity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(com.github.overlayApp.R.layout.activity_main);
        Intent i = new Intent();
        i.setClass(this, getSurvivableForegroundServiceImpl());
        startService(i);
    }

    protected abstract Class getSurvivableForegroundServiceImpl();

}
