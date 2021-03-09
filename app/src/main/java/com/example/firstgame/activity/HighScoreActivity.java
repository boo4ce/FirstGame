package com.example.firstgame.activity;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.firstgame.R;

public class HighScoreActivity extends FullScreenActivity {
    private float first_xCoordinate_of_touch = -1, current_x;
    private boolean pullable = true;
    private ImageView[] list;
    private Animation[] animations;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

//        ConstraintLayout mainLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.high_score, null);
//        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                mainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//
//                list = new ImageView[]{findViewById(R.id.left_view), findViewById(R.id.center_view),
//                        findViewById(R.id.right_view)};
//
//                int translationX = list[1].getLeft();
//                int width = list[1].getWidth();
//                DisplayMetrics displayMetrics = new DisplayMetrics();
//                HighScoreActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                int screen_width = displayMetrics.widthPixels;
//
//                list[0].setLeft(-translationX - width);
//                list[0].setRight(-translationX);
//                list[2].setLeft(screen_width + translationX);
//                list[2].setRight(screen_width + translationX + width);
//            }
//        });

        this.setContentView(R.layout.high_score);
        list = new ImageView[]{findViewById(R.id.left_view), findViewById(R.id.center_view),
                findViewById(R.id.right_view)};

        animations = new Animation[5];
        animations[0] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in_right);
        animations[1] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.out_right);
        animations[2] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in_left);
        animations[3] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.out_left);
        animations[4] = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.nothing);

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
                reset();
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
        Drawable tmp = list[0].getBackground();
        for(int i = 1; i < list.length; i++)
            list[i-1].setBackground(list[i].getBackground());
        list[list.length-1].setBackground(tmp);
    }

    private void rotateRight() {
        Drawable tmp = list[list.length - 1].getBackground();
        for(int i = list.length - 1; i > 0; i--)
            list[i].setBackground(list[i-1].getBackground());
        list[0].setBackground(tmp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void animProcess() {
        if(isPullLeft(first_xCoordinate_of_touch, current_x)) {
            list[2].startAnimation(animations[4]);
            list[1].startAnimation(animations[1]);
            list[0].startAnimation(animations[2]);
            rotateRight();
        } else if(isPullRight(first_xCoordinate_of_touch, current_x)) {
            list[0].startAnimation(animations[4]);
            list[1].startAnimation(animations[3]);
            list[2].startAnimation(animations[0]);
            rotateLeft();
        }

        System.out.println(list[0].getBackground().toString() + " " + list[1].getBackground().toString() + " " +
                list[2].getBackground().toString());
    }

    private void reset() {
        list[0].startAnimation(animations[4]);
        list[1].startAnimation(animations[4]);
        list[2].startAnimation(animations[4]);
    }
}
