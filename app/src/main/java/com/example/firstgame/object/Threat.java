package com.example.firstgame.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.firstgame.main.GameView;

import java.util.Random;

public class Threat extends GameObject implements CommonFunction {
    public static final int COLLISION = 0;
    public static final int GET_SCORE = 2;
    public static final int NO_COLLISION = 1;
    public static final int IN_HOLD = 3;

    private GameView gameView;
    private Ball ball;

    private int x_hold; // coordinate in bitmap
    private final int x_max;
    private final int speedUp_line_y;

    private boolean direct, running, effected;

    private int hold_width, hold_height;

    private final int hori_move, verti_move, verti_move_boost;

    public Threat(GameView gameView, Ball ball, Bitmap image, int width, int height,
                  int hold_width, int hold_height) {
        super(image, width, height);

        this.gameView = gameView;
        this.ball = ball;

        hori_move = ball.getMove_per_time()*3/2;

        this.verti_move = gameView.getHeight()/128;
        this.speedUp_line_y = ball.getY() - verti_move*24;
        this.verti_move_boost = verti_move*3/2;

        this.x = 0;
        this.y = -20*verti_move;
        this.hold_width = hold_width;
        this.hold_height = hold_height;

        // 1080 - 300 = 780
        x_max = gameView.getWidth() - hold_width;

        int tmp = x_max/hori_move + 1;
        this.x_hold = Math.abs(new Random().nextInt() % tmp)*hori_move;

        this.direct = (new Random()).nextBoolean();

        if(this.x_hold == 0) direct = true;
        else if(this.x_hold == x_max) {
            direct = false;
        }

        running = true;
        effected = false;
    }

    @Override
    public void update() {
        if(this.y < speedUp_line_y) this.y += verti_move;
        else this.y += verti_move_boost;

        if(!running) return;
        if(direct) this.x_hold += hori_move;
        else this.x_hold -= hori_move;
        if(x_hold <= 0 || x_hold >= x_max)
            direct = !direct;
    }

    @Override
    public void reset() {

    }

    @Override
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

    public boolean getHoldState() {
        return running;
    }

    public void stopHold() {
        running = false;
    }

    public Threat clone() {
        return new Threat(this.gameView, this.ball, this.image, this.width, this.height,
                this.hold_width, this.hold_height);
    }

    public int checkCollision_and_getScore() {
        if(this.isEffected() || this.getY() < ball.getY() - this.height) return Threat.NO_COLLISION;
        int hold_center_x = x_hold + hold_width/2, hold_center_y = this.y + hold_height/2;

        // 240 = this.height + ball.height
        if(this.y <= ball.getY() + 240) {
            double a = MyMath.lengthOfThirdEdge(ball.getCenter_x(), ball.getCenter_y(),
                    hold_center_x, hold_center_y, x_hold + hold_width, this.y);
            if (a < ball.getPerimeter()) return Threat.COLLISION;

            a = MyMath.lengthOfThirdEdge(ball.getCenter_x(), ball.getCenter_y(),
                    hold_center_x, hold_center_y, x_hold, this.y);
            if (a < ball.getPerimeter()) return Threat.COLLISION;

            a = MyMath.lengthOfThirdEdge(ball.getCenter_x(), ball.getCenter_y(),
                    hold_center_x, hold_center_y, x_hold, this.y + hold_height);
            if (a < ball.getPerimeter()) return Threat.COLLISION;

            a = MyMath.lengthOfThirdEdge(ball.getCenter_x(), ball.getCenter_y(),
                    hold_center_x, hold_center_y, x_hold + hold_width, this.y + hold_height);
            if (a < ball.getPerimeter()) return Threat.COLLISION;

            if (x_hold > ball.getCenter_x() || x_hold + 300 < ball.getCenter_x())
                return Threat.COLLISION;

            return Threat.IN_HOLD;
        }

        this.set_Effected();

        return Threat.GET_SCORE;
    }
}
