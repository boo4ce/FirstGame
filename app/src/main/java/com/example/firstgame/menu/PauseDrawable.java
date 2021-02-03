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

import com.example.firstgame.view.GameView;

public class PauseDrawable extends Drawable {
    public static final int SPEAKER = 0;
    public static final int VIBRATION = 1;
    public static final int QUIT = 2;
    public static final int RESTART = 3;
    public static final int RESUME = 4;

    private final RectF rectSpeaker, rectVibra;
    private final RectF rectQuit, rectRestart, rectResume;
    private Bitmap[] bitmaps;
    private RectF[] rectFs;
    private int stt_speaker, stt_vibra;

    public PauseDrawable(int screenWidth, int screenHeight, Bitmap[] bitmaps) {
        int segmentWidth = screenWidth/6, segmentHeight = screenHeight/15;

        stt_speaker = 16; //17
        stt_vibra = 15; //20

        int size = screenWidth/4;
        rectSpeaker = new RectF(segmentWidth, segmentHeight*5,
                segmentWidth + size, segmentHeight*5 + size);

        rectVibra = new RectF(screenWidth - rectSpeaker.left - size, rectSpeaker.top,
                screenWidth - rectSpeaker.left, rectSpeaker.bottom);

        size = screenWidth/6;
        rectQuit = new RectF(segmentWidth, rectSpeaker.bottom + segmentHeight*2,
                segmentWidth + size, rectSpeaker.bottom + segmentHeight*2 + size);

        rectResume = new RectF(screenWidth - segmentWidth - size, rectQuit.top,
                screenWidth - segmentWidth, rectQuit.bottom);

        float middle = (rectQuit.left + rectResume.left)/2;
        rectRestart = new RectF(middle, rectQuit.top, middle+size, rectQuit.bottom);

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
