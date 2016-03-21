package com.github.overotherSample;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.overlayApp.SurvivableForegroundService;

/**
 * Created by max on 08.01.16.
 */
public class ImplSurvivableForegroundService extends SurvivableForegroundService {

    public static final String PACKAGE_KEY = "package";

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public Class getImplClass() {
        return ImplSurvivableForegroundService.class;
    }

    @Override
    protected View createOverlayView() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.overlay_button_layout2, null, false);
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.image_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipMan = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipMan.setPrimaryClip(ClipData.newPlainText("phone", App.loadPhone()));

                Toast.makeText(v.getContext(), "copy phone number in buffer", Toast.LENGTH_LONG).show();
            }
        });
        ImageButton close = (ImageButton) view.findViewById(R.id.close_image_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(getBaseContext(), getImplClass());
                serviceIntent.putExtra(CHANGE_STATE_KEY, true);
                startService(serviceIntent);
            }
        });
        textView.setText(App.loadPhone());
        return view;
    }

    @Override
    protected int getIconId() {
        return R.mipmap.ic_launcher;
    }

    @Override
    protected Class getActivityClass() {
        return MainActivity.class;
    }

}
