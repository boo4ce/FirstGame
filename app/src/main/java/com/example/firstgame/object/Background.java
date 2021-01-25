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
//    private final int move = 10;
    private final int speed_up_line_coor;

//    Bitmap[] bitmaps;
    Bitmap bitmap;

    public Background(GameView gameView, Bitmap image, int width, int height) {
        super(image, width, height);

//        bitmaps = new Bitmap[num_frame];
//        getBitmap();
        // getBitmap instead
        this.bitmap = this.getSubImage(0, 0);

        this.gameView = gameView;
        this.x = (gameView.getWidth() - width)/2;
        this.y = Math.max((gameView.getHeight() - height)/2, 0);

        this.speed_up_line_coor = gameView.getHeight()*3/5;
    }

//    private void getBitmap() {
//        for(int i = 0; i < num_frame; i++) {
//            bitmaps[i] = this.getSubImage(0, i*move);
//        }
//    }

//    public void update() {
//        current_frame--;
//        if(current_frame == -1) current_frame = num_frame-1;
//    }

//    public void draw(Canvas canvas) {
//        canvas.drawBitmap(bitmaps[0], this.x, this.y, null);
//    }

//    int getMove() {
//        return move;
//    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, this.x, this.y, null);
    }

    public void reset() {
        this.x = (gameView.getWidth() - width)/2;
        this.y = Math.max((gameView.getHeight() - height)/2, 0);

        current_frame = 0;
    }

    public int getSpeed_up_line_coor() {
        return speed_up_line_coor;
    }
}
