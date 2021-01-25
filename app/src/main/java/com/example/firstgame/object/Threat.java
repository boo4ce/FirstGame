package com.example.firstgame.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.firstgame.main.GameView;

import java.util.Random;

public class Threat extends GameObject {
    private GameView gameView;

    private int x_hold; // coordinate in bitmap
    private final int x_max;
    private final int speedUp_line_y;

    private boolean direct, running, effected;

    private int hold_width, hold_height;

    private final int move = 10;

    public Threat(GameView gameView, Bitmap image, int width, int height,
                  int hold_width, int hold_height) {
        super(image, width, height);

        this.gameView = gameView;
        this.x = 0;
        this.y = -height;
        this.hold_width = hold_width;
        this.hold_height = hold_height;

        this.speedUp_line_y = gameView.getHeight()*3/5;
        // 1080 - 300 = 780
        x_max = gameView.getWidth() - hold_width;

        int tmp = x_max/10 + 1;
        this.x_hold = Math.abs(new Random().nextInt() % tmp)*10;

        this.direct = (new Random()).nextBoolean();

        if(this.x_hold == 0) direct = true;
        else if(this.x_hold == x_max) {
            direct = false;
        }

        running = true;
        effected = false;
    }

    public void update() {
        if(this.y < speedUp_line_y) this.y += move;
        else this.y += move*2;

        if(!running) return;
        if(direct) this.x_hold += 10;
        else this.x_hold -= 10;
        if(x_hold <= 0 || x_hold >= x_max)
            direct = !direct;
    }

    public void draw(Canvas canvas) {
        if(x_hold > 0)
            canvas.drawBitmap(this.getSubBitmap(0, 0, x_hold), this.x, this.y, null);

        if(x_hold < x_max)
            canvas.drawBitmap(this.getSubBitmap(0, 0, x_max - x_hold),
                    this.x + x_hold + hold_width, this.y, null);
    }

    public boolean die() {
        return this.y >= gameView.getHeight();
    }

    public boolean isEffected() {
        return effected;
    }
    public void set_Effected() {
        effected = true;
        running = false;
    }

    private Bitmap getSubBitmap(int x, int y, int width) {
        Bitmap bitmap = Bitmap.createBitmap(image, x, y, width, this.hold_height);
        return bitmap;
    }

    public Hold getHold() {
        return new Hold(x + x_hold, y, this.hold_width, this.hold_height);
    }

    public boolean getHoldState() {
        return running;
    }

    public void stopHold() {
        running = false;
    }

    public Threat clone() {
        return new Threat(this.gameView, this.image, this.width, this.height,
                this.hold_width, this.hold_height);
    }
}
