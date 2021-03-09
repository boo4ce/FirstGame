package com.example.firstgame.activity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.firstgame.R;
import com.example.firstgame.attributes.Config;

public class HighScoreActivity extends FullScreenActivity{
    private float first_xCoordinate_of_touch = -1;
    private boolean pullable = true;
    private Drawable[] list;
    private int current_drawable;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.high_score);

        list = new Drawable[]{ContextCompat.getDrawable(getApplicationContext(), R.drawable.yellow_ball_icon),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.blue_ball_icon),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.dark_red_ball_icon)};
        current_drawable = 1;

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighScoreActivity.this.onBackPressed();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                first_xCoordinate_of_touch = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                if(!pullable) break;

                pullable = false;
                if(isPullLeft(first_xCoordinate_of_touch, event.getX())) {
                    current_drawable--;
                    if(current_drawable == -1) current_drawable = 2;
                    findViewById(R.id.center_view).setBackground(list[current_drawable]);
                } else if(isPullRight(first_xCoordinate_of_touch, event.getX())) {
                    current_drawable++;
                    if(current_drawable == 3) current_drawable = 0;
                    findViewById(R.id.center_view).setBackground(list[current_drawable]);
                }

                break;

            case MotionEvent.ACTION_UP:
                pullable = true;
                break;
            default: break;
        }

        return true;
    }

    // if touch pull left
    private boolean isPullLeft(float x1, float x2) {
        return x1 < x2;
    }

    // if touch pull right
    private boolean isPullRight(float x1, float x2) {
        return x1 > x2;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
