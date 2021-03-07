package com.example.firstgame.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.firstgame.R;

public class HighScoreActivity extends FullScreenActivity{
    private float first_xCoordinate_of_touch = -1;
    private boolean pullable = true;
    private ImageView[] list;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.high_score);

        list = new ImageView[]{findViewById(R.id.left_view), findViewById(R.id.center_view),
                        findViewById(R.id.right_view)};

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
                    list[1].startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.out_right));
                    list[0].startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in_left));
//                    rotateRight();
                } else if(isPullRight(first_xCoordinate_of_touch, event.getX())) {
                    list[1].startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left));
                    list[2].startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right));
//                    rotateLeft();
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

    private void rotateLeft() {
        ImageView tmp = list[0];
        for(int i = 1; i < list.length; i++)
            list[i-1] = list[i];
        list[list.length-1] = tmp;
    }

    private void rotateRight() {
        ImageView tmp = list[list.length - 1];
        for(int i = list.length - 1; i > 0; i--)
            list[i] = list[i-1];
        list[0] = tmp;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
