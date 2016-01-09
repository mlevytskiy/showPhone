package com.github.overotherSample;

import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.overlayApp.SurvivableForegroundService;

/**
 * Created by max on 08.01.16.
 */
public class ImplSurvivableForegroundService extends SurvivableForegroundService {

    @Override
    public Class getImplClass() {
        return ImplSurvivableForegroundService.class;
    }

    @Override
    protected View createOverlayView() {
        ImageButton imageButton = new ImageButton(getApplicationContext());
        imageButton.setImageBitmap(createNumberBitmap());
        imageButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        imageButton.setFilterTouchesWhenObscured(true);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        imageButton.setBackgroundColor(Color.TRANSPARENT);
        return imageButton;
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
        canvas.drawText("13", (width / 2.f), (height / 2.f), paint);

        return bitmap;

    }

    @Override
    protected int getIconId() {
        return R.drawable.ic_bucket;
    }

    @Override
    protected Class getActivityClass() {
        return TestActivity.class;
    }

}
