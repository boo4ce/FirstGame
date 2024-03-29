package com.example.firstgame.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.firstgame.R;
import com.example.firstgame.attributes.Config;
import com.example.firstgame.attributes.Score;
import com.example.firstgame.controller.IOFile;
import com.example.firstgame.thread.AnimationThread;

import java.io.File;

public class MenuActivity extends FullScreenActivity implements Runnable{
    public final static int ENABLE_TO_CLOSE = 132;
    public final static int START_WITH_PREVIOUS_GAME = 20;
    public final static int START_NEW_GAME = 19;

    private ImageView game_name;
    private Drawable current_drawable, next_drawable;
    private AnimationThread animationThread;
    private int[] list;
    private Handler mHandler;

    private File file;
    private Button button;

    // only one touch per time
    private enum ButtonName {
        CONTINUE, NEWGAME, BALL, HIGHSCORE, QUIT, NOTHING
    }
    private ButtonName kindOfButton = ButtonName.NOTHING;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        this.setContentView(R.layout.menu_activity);

        game_name = findViewById(R.id.name);
        mHandler = new Handler(Looper.getMainLooper());

        final Button[] buttons = {findViewById(R.id.continues), findViewById(R.id.startGame),
                findViewById(R.id.ball), findViewById(R.id.highscore), findViewById(R.id.quit)};

        button = buttons[0];

        next_drawable = getDrawable(R.drawable.game_name_0);

        final MediaPlayer clickSound = MediaPlayer.create(this, R.raw.click);

        //set animation
        new Thread(new Runnable() {
            @Override
            public void run() {
                //get screen size
                DisplayMetrics displayMetrics = new DisplayMetrics();
                MenuActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                Config.setScreenWidth(displayMetrics.widthPixels);
                Config.setScreenHeight(displayMetrics.heightPixels);

                //set touch listener new game
                buttons[1].setOnTouchListener((v, event) -> {
                    if(kindOfButton == ButtonName.NOTHING) {
                        kindOfButton = ButtonName.NEWGAME;
                    }
                    if(kindOfButton != ButtonName.NEWGAME) return false;

                    setProcess(buttons[1], event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
//                        startActivityForResult(intent, ENABLE_TO_CLOSE);
                        MenuActivity.this.openActivity(SelectLevelActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        kindOfButton = ButtonName.NOTHING;
                        if(Config.getSound()) clickSound.start();
                    }
                    return true;
                });

                // ball
                buttons[2].setOnTouchListener((v, event) -> {
                    if(kindOfButton == ButtonName.NOTHING) {
                        kindOfButton = ButtonName.BALL;
                    }
                    if(kindOfButton != ButtonName.BALL) return false;

                    setProcess(buttons[2], event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        MenuActivity.this.openActivity(SelectBallActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        kindOfButton = ButtonName.NOTHING;
                        if(Config.getSound()) clickSound.start();
                    }
                    return true;
                });

                // highscore
                buttons[3].setOnTouchListener((v, event) -> {
                    if(kindOfButton == ButtonName.NOTHING) {
                        kindOfButton = ButtonName.HIGHSCORE;
                    }
                    if(kindOfButton != ButtonName.HIGHSCORE) return false;

                    setProcess(buttons[3], event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        MenuActivity.this.openActivity(HighScoreActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        kindOfButton = ButtonName.NOTHING;
                        if(Config.getSound()) clickSound.start();
                    }
                    return true;
                });

                // quit
                buttons[4].setOnTouchListener((v, event) -> {
                    if(kindOfButton == ButtonName.NOTHING) {
                        kindOfButton = ButtonName.QUIT;
                    }
                    if(kindOfButton != ButtonName.QUIT) return false;

                    setProcess(buttons[4], event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        MenuActivity.this.finishAndRemoveTask();
                        kindOfButton = ButtonName.NOTHING;
                    }
                    return true;
                });

                //continues
                buttons[0].setOnTouchListener((v, event) -> {
                    if(kindOfButton == ButtonName.NOTHING) {
                        kindOfButton = ButtonName.CONTINUE;
                    }
                    if(kindOfButton != ButtonName.CONTINUE) return false;

                    setProcess(buttons[0], event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("flag", MenuActivity.START_WITH_PREVIOUS_GAME);
                        startActivity(intent);
                        overridePendingTransition(R.anim.appear, R.anim.nothing);
                        kindOfButton = ButtonName.NOTHING;
                        if(Config.getSound()) clickSound.start();
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
                MenuActivity.this.getScore();
                file = new File(getFilesDir(), ".filesave");
                IOFile.setFile(file);
            }
        }).start();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void updateFrame(int frame_index) {
        current_drawable = next_drawable.getCurrent();
        next_drawable = getDrawable(list[frame_index]);
        mHandler.post(this);
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
        animationThread = new AnimationThread(this);
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
        file = new File(getFilesDir(), ".config");
        IOFile.setFile(file);
        IOFile ioFile = new IOFile();

        try {
            ioFile.writeData(Config.getSound() + " " + Config.getVibra() + " "
                    + Config.getBall_resId() + " " + Config.getBall_id());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // save high score
        file = new File(getFilesDir(), ".score");
        IOFile.setFile(file);
        try {
            ioFile.writeData(Score.getLow_highScore() + " " + Score.getNormal_highScore() +
                    " " + Score.getFast_highScore() + " qwerty");
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

    @Override
    public void run() {
        game_name.setBackground(current_drawable);
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
        file = new File(getFilesDir(), ".config");

        if(!file.exists()) {
            Config.turnVibraON();
            Config.turnSoundON();
            Config.setBall_resId(R.drawable.yellow_ball);
            Config.setBall_id(R.id.yellow_ball);

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

        if(stt[2].equals("0")) Config.setBall_resId(R.drawable.yellow_ball);
        else Config.setBall_resId(Integer.parseInt(stt[2]));

        if(stt[3].equals("0")) Config.setBall_id(R.id.yellow_ball);
        else Config.setBall_id(Integer.parseInt(stt[3]));

    }

    //get high score
    private void getScore() {
        file = new File(getFilesDir(), ".score");

        if(!file.exists()) {
            Score.setLow_highScore(0);
            Score.setNormal_highScore(0);
            Score.setFast_highScore(0);
            return;
        }

        IOFile.setFile(file);
        IOFile ioFile = new IOFile();

        //set default highscore
        String string = "0 0 0 qwerty";
        try {
            string = ioFile.readData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] strs = string.split(" ");
        if(!strs[3].equals("qwerty")) {
            Score.setLow_highScore(0);
            Score.setNormal_highScore(0);
            Score.setFast_highScore(0);
            return;
        }

        Score.setLow_highScore(Integer.parseInt(strs[0]));
        Score.setNormal_highScore(Integer.parseInt(strs[1]));
        Score.setFast_highScore(Integer.parseInt(strs[2]));
    }

    // open activity without intent's content by this activity
    private void openActivity(Class<? extends FullScreenActivity> cls) {
        Intent intent = new Intent(MenuActivity.this, cls);
        startActivity(intent);
    }
}
