package com.example.firstgame.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.example.firstgame.R;

import java.io.InputStream;

public class MenuActivity extends Activity {
    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.menu_activity);

        final Button[] button = {findViewById(R.id.continues), findViewById(R.id.startGame),
                findViewById(R.id.ball), findViewById(R.id.setting), findViewById(R.id.quit)};

        button[0].setClickable(false);
        button[0].setAlpha(0.3F);

        //set touch listener new game
        button[1].setOnTouchListener((v, event) -> {
            setAnimClick(button[1], event);
            if(event.getAction() == MotionEvent.ACTION_UP) {
                Intent intent = new Intent(getApplicationContext(), SelectLevelActivity.class);
                startActivity(intent);
            }
            return true;
        });

        // ball
        button[2].setOnTouchListener((v, event) -> {
            setAnimClick(button[2], event);
            return true;
        });

        // setting
        button[3].setOnTouchListener((v, event) -> {
            setAnimClick(button[3], event);
            return true;
        });

        // quit
        button[4].setOnTouchListener((v, event) -> {
            setAnimClick(button[4], event);
            if(event.getAction() == MotionEvent.ACTION_UP)
                MenuActivity.this.finish();
            return true;
        });

        //set animation
        new Thread(new Runnable() {
            @Override
            public void run() {
                ImageView game_name = findViewById(R.id.name);
                InputStream inputStream = getResources().openRawResource(R.drawable.game_name/0);
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Pause");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finishAndRemoveTask();
        System.out.println("Destroy");
    }

    private void setAnimClick(Button button, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            button.setAlpha(0.3F);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP)
            button.setAlpha(1);
    }


}
