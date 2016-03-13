package com.github.overotherSample;

import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.funakoshi.resolveInfoAsyncLoader.IconImageView;
import com.github.overlayApp.SurvivableForegroundService;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by max on 08.01.16.
 */
public class ImplSurvivableForegroundService extends SurvivableForegroundService {

    public static final String PACKAGE_KEY = "package";

    public static Map<String, Date> apps = new LinkedHashMap<>();

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra(PACKAGE_KEY)) {
            String packageName = intent.getStringExtra(PACKAGE_KEY);
            if (TextUtils.equals("android.intent.action.PACKAGE_ADDED", intent.getAction())) {
                apps.put(packageName, new Date());
            } else {
                apps.remove(packageName);
            }
            fillRecentlyApps();
            updateBitmap();
            return START_STICKY;
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public Class getImplClass() {
        return ImplSurvivableForegroundService.class;
    }

    private void fillRecentlyApps() {
        Object[] array = apps.entrySet().toArray();
        int size = Math.min(5, array.length);
        for (int i = 0; i < size; i++) {
            String packageName;
            Map.Entry<String, Date> entry = (Map.Entry<String,Date>) array[i];
            if (appsIconImageViews[i] != null) {
                packageName = entry.getKey();
                appsIconImageViews[i].setPackageName(packageName);
                appsIconImageViews[i].setTag(packageName);
                appsIconImageViews[i].setVisibility(View.VISIBLE);
            }
        }
        for (int i = 4; i >= size; i--) {
            if (appsIconImageViews[i] != null) {
                appsIconImageViews[i].setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected View createOverlayView() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.overlay_button_layout, null, false);
        overlayImageButton = (ImageButton) view.findViewById(R.id.image_button);
        overlayCircleLayout = view.findViewById(R.id.circle_layout);

        appsIconImageViews[0] = (IconImageView) view.findViewById(R.id.image_1);
        appsIconImageViews[1] = (IconImageView) view.findViewById(R.id.image_2);
        appsIconImageViews[2] = (IconImageView) view.findViewById(R.id.image_3);
        appsIconImageViews[3] = (IconImageView) view.findViewById(R.id.image_4);
        appsIconImageViews[4] = (IconImageView) view.findViewById(R.id.image_5);

        for (int i = 0; i < appsIconImageViews.length; i++) {
            appsIconImageViews[i].setOnClickListener(new UninstallClickListener());
        }

        fillRecentlyApps();

        overlayImageButton.setImageBitmap(createNumberBitmap());
        overlayImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (overlayCircleLayout.getVisibility() == View.VISIBLE) {
                    overlayCircleLayout.setVisibility(View.GONE);
                } else {
                    overlayCircleLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        overlayImageButton.setBackgroundColor(Color.TRANSPARENT);
        view.setFilterTouchesWhenObscured(true);
        return view;
    }

    private void updateBitmap() {
        getOverlayView();
        overlayImageButton.setImageBitmap(createNumberBitmap());
    }

    private Bitmap createNumberBitmap() {
        int width = 100;
        int height = 100;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);

        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(30.f);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.CENTER);

        int cx = width / 2;
        int cy = height / 2 - (height / 8);
        int r = height / 4;

        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.FILL);

        Paint circlePaint2 = new Paint();
        circlePaint2.setColor(Color.BLACK);
        circlePaint2.setStyle(Paint.Style.STROKE);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_bucket);

        canvas.drawBitmap(bm, 15 , 5, new Paint());
        canvas.drawCircle(cx, cy, r, circlePaint);
        canvas.drawCircle(cx, cy, r, circlePaint2);
        canvas.drawText(String.valueOf(apps.size()), (width / 2.f), (height / 2.f), paint);

        return bitmap;

    }

    @Override
    protected int getIconId() {
        return R.drawable.ic_bucket;
    }

    @Override
    protected Class getActivityClass() {
        return MainActivity.class;
    }

}
