package com.github.overotherSample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.github.overlayApp.ActivityOverOther;

/**
 * Created by max on 08.01.16.
 */
public class MainActivity extends ActivityOverOther {

    private EditText editText;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity);
        Intent i = new Intent();
        i.setClass(this, getSurvivableForegroundServiceImpl());
        startService(i);
        editText = (EditText) findViewById(R.id.edit_text);

    }

    public void onStart() {
        super.onStart();
        editText.setText(App.loadPhone());
    }

    public void onStop() {
        super.onStop();
        App.savePhone(editText.getText().toString());
    }

    @Override
    protected Class getSurvivableForegroundServiceImpl() {
        return ImplSurvivableForegroundService.class;
    }

}
