package com.example.firstgame.score;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.example.firstgame.main.GameView;

public class Score {
    private int score;
    private boolean lock;

    private int width, height;
    private int num_digits = 1;
    private int key_point = 10;
    private int last_posi;

    private final int score_x, score_y, score_y2;
    private GameView gameView;
    private final Bitmap[] digits;
    private final Bitmap scoreB;

    public Score(GameView gameView, Bitmap scoreB, Bitmap[] digits, int width, int height) {
        this.score = 0;

        this.width = width;
        this.height = height;
        this.lock = true;

        this.gameView = gameView;

        score_x = (gameView.getWidth() - scoreB.getWidth())/2;
        score_y = gameView.getHeight()/2 - height;
        score_y2 = score_y + scoreB.getHeight()*2;
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

            canvas.drawBitmap(digits[needed_digit], last_posi1, this.score_y2, null);

            last_posi1 -= width;
        }
        while(cur_score != 0);
    }

    public void draw(Canvas canvas) {
        this.displayScore(canvas);
        canvas.drawBitmap(scoreB, score_x, score_y, null);
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

    synchronized public void unlock(){
        lock = false;
    }

    public void reset() {
        score = 0;
        lock = true;
    }
}
