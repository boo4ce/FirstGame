package com.example.firstgame.score;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.firstgame.main.GameView;

public class Score {
    private int score;
    private boolean lock;

    private final int width, height;
    private int num_digits = 1;
    private int key_point = 10;
    private int last_posi;
    private final int const_posi;

    private GameView gameView;
    Bitmap[] digits;

    public Score(GameView gameView, Bitmap[] digits, int width, int height) {
        this.width = width;
        this.height = height;
        this.lock = true;

        this.gameView = gameView;
        this.const_posi = gameView.getWidth()/2 - width;

        // 1080 - (1080 - num_digits*width)/2 - 250
        this.last_posi = const_posi + num_digits*width/2;

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

            canvas.drawBitmap(digits[needed_digit], last_posi1, 660, null);

            last_posi1 -= 250;
        }
        while(cur_score != 0);
    }

    synchronized public void gainScore() {
        if(!lock) {
            this.score++;
            lock = true;
            if(this.score == key_point) {
                if(key_point == 10000) {
                    int buffer_width = width*2/3, buffer_height = height*2/3;
                    for(int i = 0; i < 10; i++) {
                        digits[i] = Bitmap.createScaledBitmap(digits[i], buffer_width, buffer_height, false);
                    }
                }
                num_digits++;
                key_point *= 10;
                this.last_posi = const_posi + num_digits*width/2;
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
