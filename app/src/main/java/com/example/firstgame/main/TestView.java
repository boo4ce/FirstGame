package com.example.firstgame.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.firstgame.R;
import com.example.firstgame.object.Threat;

import java.io.InputStream;

public class TestView extends SurfaceView implements SurfaceHolder.Callback {


    public TestView(Context context) {
        super(context);
        this.setFocusable(true);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        @SuppressLint("ResourceType") InputStream inputStream = getResources().openRawResource(R.drawable.threat);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        Threat threat = new Threat(null, bitmap, 1000, 80, 300, 80);
        while(true) {
            Canvas canvas = getHolder().lockCanvas();
            threat.draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
