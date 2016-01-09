package com.github.overlayApp;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;

/**
 * Created by max on 08.01.16.
 */
public abstract class ActivityOverOther extends Activity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(com.github.overlayApp.R.layout.activity_main);
        Intent i = new Intent();
        i.setClass(this, getSurvivableForegroundServiceImpl());
        startService(i);
    }

    protected abstract Class getSurvivableForegroundServiceImpl();

}
