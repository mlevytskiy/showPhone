package com.github.overlayApp;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.*;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RemoteViews;

/**
 * Created by max on 08.01.16.
 */
public abstract class SurvivableForegroundService extends Service {

    private static final int NOTIFICATION_ID = 52488;
    private static final int REQUEST_CODE = 321;

    protected static final String CHANGE_STATE_KEY = "change state key";

    private boolean stateIsEnable = true;

    private static final int LayoutParamFlags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

    private View overlayView;
    protected ImageButton overlayImageButton;
    protected View overlayCircleLayout;

//    protected IconImageView[] appsIconImageViews = new IconImageView[5];

    public void onCreate() {
        super.onCreate();
    }

    public abstract Class getImplClass();

    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getExtras() != null && !intent.getExtras().isEmpty()) {
            boolean changeStateKey = intent.getBooleanExtra(CHANGE_STATE_KEY, false);
            if (changeStateKey) {
                stateIsEnable = !stateIsEnable;
                if (stateIsEnable) {
                    showOverlayView();
                } else {
                    removeOverlayView();
                }
                startForeground(NOTIFICATION_ID, getCompatNotification());
                return START_STICKY;
            }
        }

        if (overlayView != null && overlayView.isShown()) {
            // do nothing
            return START_STICKY;
        }

        startForeground(NOTIFICATION_ID, getCompatNotification());
        showOverlayView();

        return START_STICKY;
    }

    protected View getOverlayView() {
        if (overlayView == null) {
            overlayView = createOverlayView();
        }
        return overlayView;
    }

    private void removeOverlayView() {

        if (overlayView == null) {
            return;
        }

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.removeView(overlayView);
        overlayView = null;
        overlayImageButton = null;
    }

    private void showOverlayView() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PRIORITY_PHONE,
                LayoutParamFlags,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        params.setTitle("Load Average");
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(getOverlayView(), params);
    }

    protected abstract View createOverlayView();

    protected abstract int getIconId();

    protected abstract Class getActivityClass();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification getCompatNotification() {

        RemoteViews remoteViews = new RemoteViews(getPackageName(), stateIsEnable ? R.layout.widget_on : R.layout.widget_off);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContent(remoteViews);
        builder.setSmallIcon(getIconId())
                .setWhen(System.currentTimeMillis());

        Intent mainActivity = new Intent(getApplicationContext(), getActivityClass());

        PendingIntent contentIntent = PendingIntent.getActivity(this,
                REQUEST_CODE, mainActivity, 0);

        Intent serviceIntent = new Intent(getBaseContext(), getImplClass());
        serviceIntent.putExtra(CHANGE_STATE_KEY, true);

        remoteViews.setOnClickPendingIntent(R.id.off_on, PendingIntent.getService(this, 22, serviceIntent, 0));

//        remoteViews.setOnClickFillInIntent(R.id.off_on, new Intent());

        builder.setContentIntent(contentIntent);
        return builder.build();
    }

    public void onDestroy() {
        super.onDestroy();
        removeOverlayView();
    }

}
