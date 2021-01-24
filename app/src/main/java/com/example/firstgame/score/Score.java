package com.example.firstgame.score;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Score {
    private int score;
    private boolean lock;

    private int width, height;
    private int scale = 1;
    private int num_digits = 1;
    private int key_point = 10;
    private int last_posi;

    Bitmap[] digits;

    public Score(Bitmap[] digits) {
        this.width = 250;
        this.height = 300;
        this.lock = true;

        // 1080 - (1080 - num_digits*width)/2 - 250
        this.last_posi = 290 + num_digits*width/2;

        this.digits = digits;
    }

    public int getScore() {
        return score;
    }

    public void displayScore(Canvas canvas) {
        int cur_score = new Integer(score);
        int needed_digit = 0;
        int last_posi1 = new Integer(last_posi);

        do {
            needed_digit = cur_score%10;
            cur_score/=10;

            canvas.drawBitmap(Bitmap.createScaledBitmap(digits[needed_digit], width, height, false),
                    last_posi1, 660, null);

            last_posi1 -= 250;
        }
        while(cur_score != 0);
    }

    synchronized public void gainScore() {
        if(!lock) {
            this.score++;
            lock = true;
            if(this.score == key_point) {
                num_digits++;
                key_point *= 10;
                this.last_posi = 290 + num_digits*width/2;
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
