package com.example.firstgame.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.firstgame.main.GameView;

public class Background extends GameObject{
    private GameView gameView;


    private final int num_frame = 8;
    private int current_frame = 7;
    private final int move = 10;

    Bitmap[] bitmaps;

    public Background(GameView gameView, Bitmap image, int width, int height) {
        super(image, width, height);

        bitmaps = new Bitmap[num_frame];
        getBitmap();

        this.gameView = gameView;
        this.x = (gameView.getWidth() - image.getWidth())/2;
        this.y = 0;
    }

    private void getBitmap() {
        for(int i = 0; i < num_frame; i++) {
            bitmaps[i] = this.getSubImage(0, i*move);
        }
    }

    public void update() {
        current_frame--;
        if(current_frame == -1) current_frame = num_frame-1;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmaps[0], this.x, this.y, null);
    }

    int getMove() {
        return move;
    }

    public int getCurrent_frame() { return current_frame; }

    public void reset() {
        this.x = (gameView.getWidth() - image.getWidth())/2;
        this.y = 0;

        current_frame = 0;
    }
}
