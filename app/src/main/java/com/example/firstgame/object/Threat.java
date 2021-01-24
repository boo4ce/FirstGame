package com.example.firstgame.object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import java.util.Random;

public class Threat extends GameObject {
    private Background background;
    private int x_hold; // coordinate in bitmap
    private boolean direct, running, effected;
    private int hold_width, hold_height;

    public Threat(Background background, Bitmap image, int width, int height,
                  int hold_width, int hold_height) {
        super(image, width, height);

        this.background = background;
        this.x = ObjectSize.WALL_WIDTH;
        this.y = -height;
        this.hold_width = hold_width;
        this.hold_height = hold_height;

        // 1000 - 300 = 700 => 71
        this.x_hold = Math.abs(new Random().nextInt() % 71)*10;

        this.direct = (new Random()).nextBoolean();

        if(this.x_hold == 0) direct = true;
        else if(this.x_hold == 700) {
            direct = false;
        }

        running = true;
        effected = false;
    }

    public void update() {
        if(this.y < 1200) this.y += background.getMove();
        else this.y += background.getMove()*2;

        if(!running) return;
        if(direct) this.x_hold += 10;
        else this.x_hold -= 10;
        if(x_hold <= 0 || x_hold >= 700)
            direct = !direct;
    }

    public void draw(Canvas canvas) {
        if(x_hold > 0)
            canvas.drawBitmap(this.getSubBitmap(0, 0, x_hold), this.x, this.y, null);

        if(x_hold < 700)
            canvas.drawBitmap(this.getSubBitmap(0, 0, 700 - x_hold),
                    this.x + x_hold + hold_width, this.y, null);
    }

    public boolean die() {
        return this.y >= background.getHeight();
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
        return new Threat(this.background, this.image, this.width, this.height,
                this.hold_width, this.hold_height);
    }
}
