package com.example.firstgame.object_ingame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import com.example.firstgame.view.GameView;

import java.util.Random;

public class Ball extends GameObject implements CommonFunction{
    // status of ball
    private static final int TO_LEFT = 0;
    private static final int TO_RIGHT = 1;

    // number of pixel to move ball per time
    private final int move_per_time;

    // exactly position of ball's state
    private int current_col = 0;
    private int current_row = 0;

    // left or right move
    private int way;

    private Bitmap[][] rolling;

    private GameView gameView;

    private final int x_min, x_max;
    private final int needed_y;

    public Ball(GameView gameView, Bitmap image, int width, int height) {
//        super(Bitmap.createScaledBitmap(image, gameView.getRatio(image.getWidth()),
//                GameView.getRatio(image.getHeight()), false),
//                GameView.getRatio(width), GameView.getRatio(height));
        super(image, width, height);

        this.gameView = gameView;

        rolling = new Bitmap[row][col];
        getRolling(col, row);

        move_per_time = (gameView.getWidth() - this.width)/100;

        x_min = move_per_time*3;
        x_max = gameView.getWidth() - x_min - width;

        int tmp = Math.abs(new Random().nextInt() % 31);
        this.x = ((x_max/30*tmp)/x_min)*x_min;
        this.x = Math.min(x_max - move_per_time, this.x);
        this.x = Math.max(x_min + move_per_time, this.x);

        this.needed_y = gameView.getHeight()*14/15 - height;
        this.y = gameView.getHeight() + height;

        this.way = (new Random().nextBoolean())?Ball.TO_LEFT:Ball.TO_RIGHT;
    }

    // get each state of ball
    private void getRolling(int col, int row) {
        for(int i = 0; i < row; i++)
            for(int j = 0; j < col; j++)
                rolling[i][j] = super.getSubImage(j*width, i*height);
    }

    public void update() {
        if(this.y > needed_y) {
            this.y -= 5;
        } else {
            if(this.way == Ball.TO_LEFT) x -= move_per_time;
            else x += move_per_time;
            if(x <= x_min || x >= x_max)
                this.way = 1 - this.way;
        }

        current_col++;
        if(current_col == col) {
            current_col = 0;
            current_row++;
            if(current_row == row) current_row = 0;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(rolling[current_row][current_col], this.x, this.y, null);
    }

    public int getCenter_x() {
        return this.x + this.width/2;
    }

    public int getCenter_y() {
        return this.y + this.height/2;
    }

    public int getPerimeter() { return this.width/2; }

    public void reset() {
        int tmp = Math.abs(new Random().nextInt() % 31);
        this.x = ((x_max/30*tmp)/x_min)*x_min;
        this.x = Math.min(x_max - move_per_time, this.x);
        this.x = Math.max(x_min + move_per_time, this.x);

        this.way = (new Random().nextBoolean())?Ball.TO_LEFT:Ball.TO_RIGHT;

        current_row = 0; current_col = 0;
    }

    public void changeState() {
        this.way = 1 - this.way;
    }

    public int getMove_per_time() {
        return move_per_time;
    }

    public String getStatus() {
        return this.x + " " + this.y + " " + this.way + " " + this.current_row + " " + this.current_col;
    }

    public void setStatus(String status) throws NumberFormatException {
        String[] values = status.split(" ");
        this.x = Integer.parseInt(values[0]);
        this.y = Integer.parseInt(values[1]);
        this.way = Integer.parseInt(values[2]);
        this.current_row = Integer.parseInt(values[3]);
        this.current_col = Integer.parseInt(values[4]);
    }

}
