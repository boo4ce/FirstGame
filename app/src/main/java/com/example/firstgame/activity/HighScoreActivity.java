package com.example.firstgame.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.firstgame.R;
import com.example.firstgame.attributes.Score;

public class HighScoreActivity extends FullScreenActivity {
    private float first_xCoordinate_of_touch = -1, current_x;
    private boolean pullable = true, firstTime = true, flag = true;
    private LinearLayout[] list;
    private Animation[] animations;

    //left, right -> (0,1):0 (2,3):1 (4,5):2
    private int x[];
    private int left_view_state, center_view_state, right_view_state;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        ConstraintLayout mainLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.high_score, null);
        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                list = new LinearLayout[]{findViewById(R.id.left_view), findViewById(R.id.center_view),
                        findViewById(R.id.right_view)};

                int translationX = list[1].getLeft();
                int width = list[1].getWidth();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                HighScoreActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screen_width = displayMetrics.widthPixels;

                x = new int[]{-translationX - width, -translationX, translationX, translationX + width,
                        screen_width + translationX, screen_width + translationX + width};

                list[0].setLeft(x[0]);
                list[0].setRight(x[1]);
                list[2].setLeft(x[4]);
                list[2].setRight(x[5]);

                left_view_state = 0; center_view_state = 1; right_view_state = 2;

            }
        });

        this.setContentView(mainLayout);

        animations = new Animation[3];
        animations[0] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.go_right);
        animations[1] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.go_left);
        animations[2] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.nothing);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighScoreActivity.this.onBackPressed();
            }
        });

        ((TextView)findViewById(R.id.score_low)).setText(Score.getLow_highScore() + "");
        ((TextView)findViewById(R.id.score_normal)).setText(Score.getNormal_highScore() + "");
        ((TextView)findViewById(R.id.score_fast)).setText(Score.getFast_highScore() + "");
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

                current_x = event.getX();
                animProcess();

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
        if(firstTime) {
            firstTime = false;
            return;
        }

        left_view_state--; center_view_state--; right_view_state--;

        if(left_view_state == -1) left_view_state = 2;
        if(center_view_state == -1) center_view_state = 2;
        if(right_view_state == -1) right_view_state = 2;

        list[0].setLeft(x[left_view_state*2]);
        list[0].setRight(x[left_view_state*2 + 1]);

        list[1].setLeft(x[center_view_state*2]);
        list[1].setRight(x[center_view_state*2 + 1]);

        list[2].setLeft(x[right_view_state*2]);
        list[2].setRight(x[right_view_state*2 + 1]);
    }

    private void rotateRight() {
        if(firstTime) {
            firstTime = false;
            return;
        }

        left_view_state++; center_view_state++; right_view_state++;

        if(left_view_state == 3) left_view_state = 0;
        if(center_view_state == 3) center_view_state = 0;
        if(right_view_state == 3) right_view_state = 0;

        list[0].setLeft(x[left_view_state*2]);
        list[0].setRight(x[left_view_state*2 + 1]);

        list[1].setLeft(x[center_view_state*2]);
        list[1].setRight(x[center_view_state*2 + 1]);

        list[2].setLeft(x[right_view_state*2]);
        list[2].setRight(x[right_view_state*2 + 1]);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void animProcess() {
        if(isPullLeft(first_xCoordinate_of_touch, current_x)) {
            if(!flag) {
                rotateLeft();
            }
            else rotateRight();

            list[right_view_state].startAnimation(animations[0]);
            list[center_view_state].startAnimation(animations[0]);
            list[left_view_state].startAnimation(animations[0]);
            flag = true;
        } else if(isPullRight(first_xCoordinate_of_touch, current_x)) {
            if(flag) {
                rotateRight();
            }
            else rotateLeft();

            list[left_view_state].startAnimation(animations[1]);
            list[center_view_state].startAnimation(animations[1]);
            list[right_view_state].startAnimation(animations[1]);
            flag = false;
        }
    }
}
