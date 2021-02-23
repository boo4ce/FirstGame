package com.example.firstgame.object_ingame;

import android.graphics.Canvas;

public interface CommonFunction {
    public void draw(Canvas canvas);
    public void update();
    public void reset();
    public String getStatus();
}
