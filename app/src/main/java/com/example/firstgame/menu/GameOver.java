package com.example.firstgame.menu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firstgame.view.GameView;

public class GameOver extends Drawable {
    public static final int QUIT = 2;
    public static final int RESTART = 3;

    private final RectF rectQuit, rectRestart;
    private Bitmap[] bitmaps;

    public GameOver(int screenWidth, int screenHeight, Bitmap[] bitmaps) {
        int size = screenWidth/4;

        rectQuit = new RectF(screenWidth/3 - size/2, screenHeight - size*2,
                screenWidth/3 + size/2, screenHeight - size);

        rectRestart = new RectF(screenWidth*2/3 - size/2, rectQuit.top,
                screenWidth*2/3 + size/2, rectQuit.bottom);

        this.bitmaps = bitmaps;
    }

    private boolean isClick(float x, float y, RectF rectF) {
        return x >= rectF.left && x <= rectF.right && y >= rectF.top && y <= rectF.bottom;
    }

    public int touchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX(), y = motionEvent.getY();
        if(isClick(x, y, rectQuit)) {
            return GameOver.QUIT;
        }
        else if(isClick(x, y, rectRestart)) {
            return GameOver.RESTART;
        }
        return -1;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawARGB(200, 0, 0, 0);
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
        return this.getOpacity();
    }

}
