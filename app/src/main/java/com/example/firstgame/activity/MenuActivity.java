package com.example.firstgame.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.example.firstgame.R;
import com.example.firstgame.thread.AnimationThread;

import java.io.Serializable;

public class MenuActivity extends Activity implements Runnable, Serializable {
    public final static int ENABLE_TO_CLOSE = 132;

    private ImageView game_name;
    private AnimationThread animationThread;
    private int[] list;
    private Handler mHandler;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.menu_activity);
        game_name = findViewById(R.id.name);
        mHandler = new Handler(Looper.getMainLooper());

        final Button[] button = {findViewById(R.id.continues), findViewById(R.id.startGame),
                findViewById(R.id.ball), findViewById(R.id.setting), findViewById(R.id.quit)};

        button[0].setClickable(false);
        button[0].setAlpha(0.3F);

        //set touch listener new game
        button[1].setOnTouchListener((v, event) -> {
            setProcess(button[1], event);
            if(event.getAction() == MotionEvent.ACTION_UP) {
                Intent intent = new Intent(MenuActivity.this, SelectLevelActivity.class);
                startActivityForResult(intent, ENABLE_TO_CLOSE);
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
                MenuActivity.this.finishAndRemoveTask();
            return true;
        });

        //set animation
        new Thread(new Runnable() {
            @Override
            public void run() {
                list = new int[]{R.drawable.game_name_0, R.drawable.game_name_10, R.drawable.game_name_20,
                        R.drawable.game_name_30, R.drawable.game_name_40, R.drawable.game_name_50,
                        R.drawable.game_name_60, R.drawable.game_name_70, R.drawable.game_name_80,
                        R.drawable.game_name_90, R.drawable.game_name_100, R.drawable.game_name_110,
                        R.drawable.game_name_120, R.drawable.game_name_130, R.drawable.game_name_140,
                        R.drawable.game_name_150_240, R.drawable.game_name_250, R.drawable.game_name_260,
                        R.drawable.game_name_270, R.drawable.game_name_280, R.drawable.game_name_290,
                        R.drawable.game_name_300, R.drawable.game_name_310, R.drawable.game_name_320,
                        R.drawable.game_name_330, R.drawable.game_name_340, R.drawable.game_name_350,
                        R.drawable.game_name_360};

                animationThread = new AnimationThread(MenuActivity.this);
            }
        }).start();

    }

    public void updateFrame(int frame_index) {
        this.frame_index = frame_index;
        mHandler.post(this::run);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.animationThread.start();
    }

    private void setProcess(Button button, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            button.setAlpha(0.3F);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            button.setAlpha(1);
            animationThread.kill();
        }
    }

    private int frame_index;
    @Override
    public void run() {
        game_name.setBackgroundResource(list[frame_index]);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        switch(requestCode) {
            case ENABLE_TO_CLOSE:
                if(resultCode == RESULT_CANCELED) this.finish();
        }
    }
}
