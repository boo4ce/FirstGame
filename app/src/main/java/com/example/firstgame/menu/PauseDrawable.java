package com.example.firstgame.menu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PauseDrawable extends Drawable {
    public static final int SPEAKER = 0;
    public static final int VIBRATION = 1;
    public static final int QUIT = 2;
    public static final int RESTART = 3;
    public static final int RESUME = 4;

    private final RectF rectSpeaker, rectVibra;
    private final RectF rectQuit, rectRestart, rectResume;
    private Paint paint = new Paint();
    private Bitmap[] bitmaps;
    private RectF[] rectFs;
    private int stt_speaker, stt_vibra;

    public PauseDrawable(int screenWidth, int screenHeight, Bitmap[] bitmaps) {
        int segmentWidth = screenWidth/6, segmentHeight = screenHeight/15;
        paint.setStrokeWidth(2);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);

        stt_speaker = 16; //17
        stt_vibra = 15; //20

        rectSpeaker = new RectF(segmentWidth, segmentHeight*5,
                segmentWidth + 240, segmentHeight*5 + 240);

        rectVibra = new RectF(screenWidth - rectSpeaker.left - 240, rectSpeaker.top,
                screenWidth - rectSpeaker.left, rectSpeaker.bottom);

        rectQuit = new RectF(segmentWidth, rectSpeaker.bottom + segmentHeight*2,
                segmentWidth + 160, rectSpeaker.bottom + segmentHeight*2 + 160);

        rectResume = new RectF(screenWidth - segmentWidth - 160, rectQuit.top,
                screenWidth - segmentWidth, rectQuit.bottom);

        float middle = (rectQuit.left + rectResume.left)/2;
        rectRestart = new RectF(middle, rectQuit.top, middle+160, rectQuit.bottom);

        this.bitmaps = bitmaps;
        rectFs = new RectF[]{rectSpeaker, rectVibra, rectQuit, rectRestart, rectResume};
    }

    private boolean isClick(float x, float y, RectF rectF) {
        return x >= rectF.left && x <= rectF.right && y >= rectF.top && y <= rectF.bottom;
    }

    public int touchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX(), y = motionEvent.getY();
        for(int i = 0; i < rectFs.length; i++) {
            if(isClick(x, y, rectFs[i])) {
                switch (i) {
                    case 0:
                        stt_speaker = 33 - stt_speaker;
                        return PauseDrawable.SPEAKER;
                    case 1:
                        stt_vibra = 35 - stt_vibra;
                        return PauseDrawable.VIBRATION;
                    case 2:
                        return PauseDrawable.QUIT;
                    case 3:
                        return PauseDrawable.RESTART;
                    case 4:
                        return PauseDrawable.RESUME;
                }
            }
        }
        return -1;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawARGB(200, 0, 0, 0);
        canvas.drawBitmap(bitmaps[stt_speaker], null, rectSpeaker, null);
        canvas.drawBitmap(bitmaps[stt_vibra], null, rectVibra, null);
        canvas.drawBitmap(bitmaps[18], null, rectResume, null);
        canvas.drawBitmap(bitmaps[19], null, rectRestart, null);
        canvas.drawBitmap(bitmaps[21], null, rectQuit, null);
//        canvas.drawRoundRect(rectSpeaker,rectSpeaker.width()/10, rectSpeaker.height()/10, paint);
//        canvas.drawRoundRect(rectVibra,rectVibra.width()/10, rectVibra.height()/10, paint);
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
