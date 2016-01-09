package com.github.overlayApp;

import android.content.*;

/**
 * Created by max on 08.01.16.
 */
public abstract class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent();
        i.setClass(context, getSurvivableForegroundServiceImpl());
        context.startService(i);
    }

    protected abstract Class getSurvivableForegroundServiceImpl();

}
