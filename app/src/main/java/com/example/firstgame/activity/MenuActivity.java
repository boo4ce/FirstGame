package com.example.firstgame.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.example.firstgame.R;
import com.example.firstgame.thread.TitleAnimationThread;

public class MenuActivity extends Activity {
    private Drawable[] drawables;
    private ImageView game_name;
    private TitleAnimationThread titleAnimationThread;

    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.menu_activity);
        game_name = findViewById(R.id.name);

        final Button[] button = {findViewById(R.id.continues), findViewById(R.id.startGame),
                findViewById(R.id.ball), findViewById(R.id.setting), findViewById(R.id.quit)};

        button[0].setClickable(false);
        button[0].setAlpha(0.3F);

        //set touch listener new game
        button[1].setOnTouchListener((v, event) -> {
            setProcess(button[1], event);
            if(event.getAction() == MotionEvent.ACTION_UP) {
                Intent intent = new Intent(getApplicationContext(), SelectLevelActivity.class);
                startActivity(intent);
            }
            return true;
        });

        // ball
        button[2].setOnTouchListener((v, event) -> {
            setProcess(button[2], event);
            return true;
        });

        // setting
        button[3].setOnTouchListener((v, event) -> {
            setProcess(button[3], event);
            return true;
        });

        // quit
        button[4].setOnTouchListener((v, event) -> {
            setProcess(button[4], event);
            if(event.getAction() == MotionEvent.ACTION_UP)
                MenuActivity.this.finish();
            return true;
        });

        drawables = new Drawable[40];
        //set animation
        new Thread(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                ImageView game_name = findViewById(R.id.name);
                int list[] = {R.drawable.game_name_0, R.drawable.game_name_10, R.drawable.game_name_20,
                        R.drawable.game_name_30, R.drawable.game_name_40, R.drawable.game_name_50,
                        R.drawable.game_name_60, R.drawable.game_name_70, R.drawable.game_name_80,
                        R.drawable.game_name_90, R.drawable.game_name_100, R.drawable.game_name_110,
                        R.drawable.game_name_120, R.drawable.game_name_130, R.drawable.game_name_140,
                        R.drawable.game_name_150_240, R.drawable.game_name_250, R.drawable.game_name_260,
                        R.drawable.game_name_270, R.drawable.game_name_280, R.drawable.game_name_290,
                        R.drawable.game_name_300, R.drawable.game_name_310, R.drawable.game_name_320,
                        R.drawable.game_name_330, R.drawable.game_name_340, R.drawable.game_name_350,
                        R.drawable.game_name_360};

                for(int i = 0; i < list.length; i++) {
                    drawables[i] = getResources().getDrawable(list[i], null);
                }
                titleAnimationThread = new TitleAnimationThread(MenuActivity.this);
                titleAnimationThread.start();
            }
        }).start();
    }

    public void updateFrame(int frame_index) {
//        game_name.setBackground(drawables[frame_index]);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        titleAnimationThread.reset();
        titleAnimationThread.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finishAndRemoveTask();
        System.out.println("Destroy");
    }

    private void setProcess(Button button, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            button.setAlpha(0.3F);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            button.setAlpha(1);
            titleAnimationThread.kill();
        }
    }


}
