package com.example.firstgame.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.firstgame.main.GameView;

import java.util.Random;

public class Ball extends GameObject {
    // status of ball
    private static final int TO_LEFT = 0;
    private static final int TO_RIGHT = 1;

    // number of pixel to move ball per time
    private final int move_per_time = 10;

    // exactly position of ball's state
    private int current_col = 0;
    private int current_row = 0;

    // left or right move
    private int way;

    private Bitmap[][] rolling;

    private GameView gameView;

    public Ball(GameView gameView, Bitmap image, int width, int height) {
        super(image, width, height);

        this.gameView = gameView;

        rolling = new Bitmap[row][col];
        getRolling(col, row);

        this.x = (gameView.getWidth() - width) / 2;
        this.y = (gameView.getHeight()/10)*10 - height*2;

        this.way = (new Random().nextBoolean())?Ball.TO_LEFT:Ball.TO_RIGHT;
    }

    // get each state of ball
    private void getRolling(int col, int row) {
        for(int i = 0; i < row; i++)
            for(int j = 0; j < col; j++)
                rolling[i][j] = super.getSubImage(j*width, i*height);
    }

    public void update() {
        if(this.way == Ball.TO_LEFT) x -= move_per_time;
        else x += move_per_time;
        if(x == ObjectSize.WALL_WIDTH ||
                x == gameView.getWidth() - ObjectSize.WALL_WIDTH - this.width)
            this.way = 1 - this.way;

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
        this.x = (gameView.getWidth() - width) / 2;

        this.way = (new Random().nextBoolean())?Ball.TO_LEFT:Ball.TO_RIGHT;

        current_row = 0; current_col = 0;
    }

    public void changeState() {
        this.way = 1 - this.way;
    }
}
