package com.example.firstgame.menu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PauseDrawable extends Drawable {
    private final Rect rectSrc, rectSpeaker, rectVibra;
    private final Rect rectQuit, rectRestart, rectResume;
    private Paint paint;
    private Bitmap[] bitmaps;

    public PauseDrawable(int screenWidth, int screenHeight, Bitmap[] bitmaps) {
        int segmentWidth = screenWidth/5, segmentHeight = screenHeight/15;
        paint.setStrokeWidth(2);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);

        rectSrc = new Rect(0, 0, 160, 160);
        rectSpeaker = new Rect(segmentWidth, segmentHeight*5,
                segmentWidth + 160, segmentHeight*5 + 160);

        rectVibra = new Rect(screenWidth - segmentWidth - 160, rectSpeaker.top,
                screenWidth - segmentWidth, rectSpeaker.bottom);

        rectQuit = new Rect(segmentWidth, rectSpeaker.bottom + segmentHeight*2,
                segmentWidth + 160, segmentHeight*10 + 160);

        rectResume = new Rect(segmentWidth*4, rectQuit.top,
                segmentWidth*4 + 160, rectQuit.bottom);

        int middle = (rectQuit.left + rectResume.left)/2;
        rectRestart = new Rect(middle, rectQuit.top, middle+160, rectQuit.bottom);

        this.bitmaps = bitmaps;
    }

    public void setup() {


    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawARGB(200, 0, 0, 0);
        canvas.drawBitmap(bitmaps[16], null, rectSpeaker, paint);
        canvas.drawBitmap(bitmaps[15], null, rectVibra, paint);
        canvas.drawBitmap(bitmaps[18], null, rectResume, paint);
        canvas.drawBitmap(bitmaps[19], null, rectRestart, paint);
        canvas.drawBitmap(bitmaps[21], null, rectQuit, paint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
