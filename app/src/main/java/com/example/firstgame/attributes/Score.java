package com.example.firstgame.attributes;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import androidx.annotation.experimental.Experimental;

import com.example.firstgame.view.GameView;

public class Score {
    private static int low_highScore;
    private static int normal_highScore;
    private static int fast_highScore;

    private int score;
    private boolean lock;

    private int width, height;
    private int num_digits;
    private int key_point;
    private int last_posi;

    private final int score_x, score_y;
    private GameView gameView;
    private final Bitmap[] digits;
    private final Bitmap scoreB;

    public Score(GameView gameView, Bitmap scoreB, Bitmap[] digits, int width, int height) {
        this.score = 0;
        this.num_digits = 1;
        this.key_point = 10;

        this.width = width;
        this.height = height;
        this.lock = true;

        this.gameView = gameView;

        score_x = (gameView.getWidth() - scoreB.getWidth())/2;
        score_y = Math.max(gameView.getHeight()/2 - height*5/4, 150);

        // 1080 - (1080 - num_digits*width)/2 - 250
        this.last_posi = gameView.getWidth()/2 - width + num_digits*width/2;

        this.digits = digits;
        this.scoreB = scoreB;
    }

    public int getScore() {
        return score;
    }

    private void displayScore(Canvas canvas) {
        int cur_score = new Integer(score);
        int needed_digit = 0;
        int last_posi1 = new Integer(last_posi);

        do {
            needed_digit = cur_score%10;
            cur_score/=10;

            canvas.drawBitmap(digits[needed_digit], last_posi1, this.score_y, null);

            last_posi1 -= width;
        }
        while(cur_score != 0);
    }

    public void draw(Canvas canvas) {
        this.displayScore(canvas);
        canvas.drawBitmap(scoreB, score_x, 30, null);
    }

    synchronized public void gainScore() {
        if(!lock) {
            this.score++;

            lock = true;
            if(this.score == key_point) {
                num_digits++;
                if(num_digits >= 5) {
                    width = width * 4/5;
                    height = height * 4/5;
                    for(int i = 0; i < 10; i++) {
                        digits[i] = Bitmap.createScaledBitmap(digits[i], width, height, false);
                    }
                }

                key_point *= 10;
                this.last_posi = gameView.getWidth()/2 - width + num_digits*width/2;
            }
        }
    }

    public void updateHighScore() {
        switch (Level.getLevel()) {
            case Level.SLOW:
                if(this.score > Score.getLow_highScore()) Score.setLow_highScore(this.score);
                break;
            case Level.NORMAL:
                if(this.score > Score.getNormal_highScore()) Score.setNormal_highScore(this.score);
                break;
            case Level.FAST:
                if(this.score > Score.getFast_highScore()) Score.setFast_highScore(this.score);
                break;
            default:
        }
    }

    synchronized public void unlock(){
        lock = false;
    }

    public void reset() {
        score = 0;
        lock = true;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static int getLow_highScore() {
        return low_highScore;
    }

    public static void setLow_highScore(int low_highScore) {
        Score.low_highScore = low_highScore;
    }

    public static int getNormal_highScore() {
        return normal_highScore;
    }

    public static void setNormal_highScore(int normal_highScore) {
        Score.normal_highScore = normal_highScore;
    }

    public static int getFast_highScore() {
        return fast_highScore;
    }

    public static void setFast_highScore(int fast_highScore) {
        Score.fast_highScore = fast_highScore;
    }
}
