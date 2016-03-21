package com.github.overotherSample;

import android.content.*;

/**
 * Created by max on 09.01.16.
 */
public class NewAppsAddedOrRemovedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        String str = intent.getDataString();
//        int index = str.indexOf(":");
//        String packageName = str.substring(index + 1);
//        Intent changeAppIntent = new Intent(context, ImplSurvivableForegroundService.class);
//        changeAppIntent.setAction(intent.getAction());
//        changeAppIntent.putExtra(ImplSurvivableForegroundService.PACKAGE_KEY, packageName);
//        context.startService(changeAppIntent);
    }

}
