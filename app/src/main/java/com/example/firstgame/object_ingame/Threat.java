package com.example.firstgame.object_ingame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.firstgame.attributes.Level;
import com.example.firstgame.attributes.MyMath;
import com.example.firstgame.view.GameView;

import java.util.Random;

public class  Threat extends GameObject implements CommonFunction {
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

    private int hold_width;

    private final int hori_move, verti_move, verti_move_boost;

    private static short red;
    private static boolean changeOn;
    private static Paint wall_paint;

    static {
        red = 150;
        changeOn = false;

        wall_paint = new Paint();
        wall_paint.setStyle(Paint.Style.FILL);
        wall_paint.setARGB(255, red, 0, 0);
    }

    public Threat(GameView gameView, Ball ball, Bitmap image, int width, int height) {
//        super(Bitmap.createScaledBitmap(image, image.getWidth(),
//                GameView.getRatio(image.getHeight()), false),
//                GameView.getRatio(width), GameView.getRatio(height));
        super(image, width, height);

        this.gameView = gameView;
        this.ball = ball;

        hori_move = ball.getMove_per_time()*2;

        int relative_y = ball.getY() - height;
        this.verti_move = relative_y/Level.getLevel();
        this.y = -(height/verti_move + 1)*verti_move + relative_y%verti_move;

        this.speedUp_line_y = relative_y - verti_move*30;
        this.verti_move_boost = verti_move*2;

        this.x = 0;
        this.hold_width = ball.width + hori_move*Level.getLevel()/15;

        // 1080 - 300 = 780
        x_max = gameView.getWidth() - hold_width;

        int tmp = Math.abs(new Random().nextInt() % 31);
        this.x_hold = ((x_max/30*tmp)/hori_move)*(hori_move/2);

        this.direct = (new Random()).nextBoolean();

        if(this.x_hold <= 0) direct = true;
        else if(this.x_hold >= x_max) {
            direct = false;
        }

        running = true;
        effected = false;

        if(changeOn) {
            red--;
            wall_paint.setARGB(255, red, 0, 0);
        }
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
        if(x_hold > 0) {
//            canvas.drawBitmap(this.getSubBitmap(0, 0, x_hold), this.x, this.y, null);
            canvas.drawRect(new Rect(0, this.y, x_hold, this.y + this.height), wall_paint);
        }

        if(x_hold < x_max) {
//            canvas.drawBitmap(this.getSubBitmap(0, 0, x_max - x_hold),
//                    this.x + x_hold + hold_width, this.y, null);
            canvas.drawRect(new Rect(x_hold+hold_width, this.y, gameView.getWidth(), this.y + this.height), wall_paint);
        }
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
        Bitmap bitmap = Bitmap.createBitmap(image, x, y, width, this.height);
        return bitmap;
    }

    public boolean getHoldState() {
        return running;
    }

    public void stopHold() {
        running = false;
    }

    public Threat clone() {
        return new Threat(this.gameView, this.ball, this.image, this.width, this.height);
    }

    public final int checkCollision_and_getScore() {
        if(this.isEffected() || this.y < ball.y - this.height) return Threat.NO_COLLISION;
        int hold_center_x = x_hold + hold_width/2, hold_center_y = this.y + height/2;

        if(this.y <= ball.y + this.height + ball.height) {
            if(this.y < ball.y || this.y > ball.getCenter_y()) {
                if (x_hold > ball.getCenter_x() || x_hold + this.hold_width < ball.getCenter_x())
                    return Threat.COLLISION;

                double a = MyMath.lengthOfThirdEdge(ball.getCenter_x(), ball.getCenter_y(),
                        hold_center_x, hold_center_y, x_hold + hold_width, this.y);
                if (a < ball.getPerimeter()) {
                    return Threat.COLLISION;
                }

                a = MyMath.lengthOfThirdEdge(ball.getCenter_x(), ball.getCenter_y(),
                        hold_center_x, hold_center_y, x_hold, this.y);
                if (a < ball.getPerimeter()) {
                    return Threat.COLLISION;
                }

                a = MyMath.lengthOfThirdEdge(ball.getCenter_x(), ball.getCenter_y(),
                        hold_center_x, hold_center_y, x_hold, this.y + height);
                if (a < ball.getPerimeter()) {
                    return Threat.COLLISION;
                }

                a = MyMath.lengthOfThirdEdge(ball.getCenter_x(), ball.getCenter_y(),
                        hold_center_x, hold_center_y, x_hold + hold_width, this.y + height);
                if (a < ball.getPerimeter()) {
                    return Threat.COLLISION;
                }
            }
            else {
                if(this.x_hold > ball.x || this.x_hold + this.hold_width < ball.x + ball.width)
                    return Threat.COLLISION;
            }
            return Threat.IN_HOLD;
        }

        this.set_Effected();

        return Threat.GET_SCORE;
    }

    public void setHoldWidth(int hold_width) {
        this.hold_width = hold_width;
    }

    public String getStatus() {
        return this.x_hold + " " + this.y + " " + this.direct + " " + running + " " + effected;
    }

    public void setStatus(String status) throws NumberFormatException{
        String[] values = status.split(" ");
        this.x_hold = Integer.parseInt(values[0]);
        this.y = Integer.parseInt(values[1]);
        this.direct = Boolean.parseBoolean(values[2]);
        this.running = Boolean.parseBoolean(values[3]);
        this.effected = Boolean.parseBoolean(values[4]);
    }

    public void changeColor(int a, int r, int g, int b) {
        wall_paint.setARGB(a, r, g, b);
    }

    public static void enableChangeColor() {
        changeOn = true;
    }
}
