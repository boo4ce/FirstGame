package com.example.firstgame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewOverlay;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class PauseView extends View {
    private Paint paint;
    private RectF rectF;

    public PauseView(Context context) {
        super(context);
    }

}
