package com.github.overotherSample;

import android.content.*;
import android.net.Uri;
import android.view.View;

/**
 * Created by max on 10.01.16.
 */
public class UninstallClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        String packageName = (String) v.getTag();
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        uninstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        v.getContext().startActivity(uninstallIntent);
    }

}
