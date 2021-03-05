package com.example.firstgame.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.firstgame.R;
import com.example.firstgame.attributes.Config;
import com.example.firstgame.controller.IOFile;
import com.example.firstgame.thread.AnimationThread;

import java.io.File;

public class MenuActivity extends FullScreenActivity implements Runnable{
    public final static int ENABLE_TO_CLOSE = 132;
    public final static int START_WITH_PREVIOUS_GAME = 20;
    public final static int START_NEW_GAME = 19;

    private ImageView game_name;
    private AnimationThread animationThread;
    private int[] list;
    private Handler mHandler;

    private File file;
    private Button button;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        this.setContentView(R.layout.menu_activity);
        game_name = findViewById(R.id.name);
        mHandler = new Handler(Looper.getMainLooper());

        final Button[] buttons = {findViewById(R.id.continues), findViewById(R.id.startGame),
                findViewById(R.id.ball), findViewById(R.id.quit)};

        button = buttons[0];

        //set animation
        new Thread(new Runnable() {
            @Override
            public void run() {
                //set touch listener new game
                buttons[1].setOnTouchListener((v, event) -> {
                    setProcess(buttons[1], event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(button.isEnabled()) {
                            Toast.makeText(MenuActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                        }
//                        startActivityForResult(intent, ENABLE_TO_CLOSE);
                        MenuActivity.this.openActivity(SelectLevelActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                    return true;
                });

                // ball
                buttons[2].setOnTouchListener((v, event) -> {
                    setProcess(buttons[2], event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        MenuActivity.this.openActivity(SelectBallActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                    return true;
                });

                // quit
                buttons[3].setOnTouchListener((v, event) -> {
                    setProcess(buttons[3], event);
                    if(event.getAction() == MotionEvent.ACTION_UP)
                        MenuActivity.this.finishAndRemoveTask();
                    return true;
                });

                //continues
                buttons[0].setOnTouchListener((v, event) -> {
                    setProcess(buttons[0], event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("flag", MenuActivity.START_WITH_PREVIOUS_GAME);
                        startActivity(intent);
                        overridePendingTransition(R.anim.appear, R.anim.nothing);
                    }
                    return true;
                });

                // list of game title drawable
                list = new int[]{R.drawable.game_name_0, R.drawable.game_name_10, R.drawable.game_name_20,
                        R.drawable.game_name_30, R.drawable.game_name_40, R.drawable.game_name_50,
                        R.drawable.game_name_60, R.drawable.game_name_70, R.drawable.game_name_80,
                        R.drawable.game_name_90, R.drawable.game_name_100, R.drawable.game_name_110,
                        R.drawable.game_name_120, R.drawable.game_name_130, R.drawable.game_name_140,
                        R.drawable.game_name_150_240, R.drawable.game_name_150_240, R.drawable.game_name_150_240,
                        R.drawable.game_name_150_240, R.drawable.game_name_150_240, R.drawable.game_name_150_240,
                        R.drawable.game_name_150_240, R.drawable.game_name_150_240, R.drawable.game_name_150_240,
                        R.drawable.game_name_150_240, R.drawable.game_name_250, R.drawable.game_name_260,
                        R.drawable.game_name_270, R.drawable.game_name_280, R.drawable.game_name_290,
                        R.drawable.game_name_300, R.drawable.game_name_310, R.drawable.game_name_320,
                        R.drawable.game_name_330, R.drawable.game_name_340, R.drawable.game_name_350,
                        R.drawable.game_name_360};

                animationThread = new AnimationThread(MenuActivity.this);
                MenuActivity.this.getSetting();
                file = new File(getFilesDir(), "filesave");
                IOFile.setFile(file);
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

        // if have previous game -> set continues button
        if(file != null) {
            if(!file.exists() || file.length() == 0) {
                button.setEnabled(false);
                button.setAlpha(0.3F);
            }
            else {
                button.setEnabled(true);
                button.setAlpha(1);
            }
        } else {
            button.setEnabled(false);
            button.setAlpha(0.3F);
        }

        // resume or start new animation of ball
        this.animationThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // stop the animation when this activity is paused
        // able to reduce memory consumed
        animationThread.kill();
    }

    @Override
    protected void onDestroy() {
        // destroy and write file setting
        file = new File(getFilesDir(), "setting");
        IOFile.setFile(file);
        IOFile ioFile = new IOFile();

        try {
            ioFile.writeData(Config.getSound() + " " + Config.getVibra() + " "
                    + Config.getBall_resId() + " " + Config.getBall_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    // set click animation
    private void setProcess(Button button, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            button.setAlpha(0.3F);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            button.setAlpha(1);
        }
    }

    // current frame of ball
    private int frame_index;

    @Override
    public void run() {
        game_name.setBackgroundResource(list[frame_index]);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
//        switch(requestCode) {
//            case ENABLE_TO_CLOSE:
////                if(resultCode == RESULT_CANCELED) this.finish();
//
//        }
//    }

    // load all configure setting previous
    private void getSetting() {
        file = new File(getFilesDir(), "setting");

        if(!file.exists()) {
            Config.turnVibraON();
            Config.turnSoundON();
            Config.setBall_resId(R.drawable.vn_ball);
            Config.setBall_id(R.id.vn_ball);

            return;
        }

        IOFile.setFile(file);
        IOFile ioFile = new IOFile();

        // set default setting if can not load file setting
        String string = "true true 0 0";
        try {
            string = ioFile.readData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] stt = string.split(" ");
        if(stt.length != 4) {
            string = "true true 0 0";
            stt = string.split(" ");
        }

        if(stt[0].equals("false")) Config.turnSoundOFF();
        else Config.turnVibraON();

        if(stt[1].equals("false")) Config.turnVibraOFF();
        else Config.turnVibraON();

        if(stt[2].equals("0")) Config.setBall_resId(R.drawable.vn_ball);
        else Config.setBall_resId(Integer.parseInt(stt[2]));

        if(stt[3].equals("0")) Config.setBall_id(R.id.vn_ball);
        else Config.setBall_id(Integer.parseInt(stt[3]));
    }

    // open activity without intent's content by this activity
    private void openActivity(Class<? extends FullScreenActivity> cls) {
        Intent intent = new Intent(MenuActivity.this, cls);
        startActivity(intent);
    }
}
