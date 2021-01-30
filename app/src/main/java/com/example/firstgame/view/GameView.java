package com.example.firstgame.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewOverlay;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.firstgame.R;
import com.example.firstgame.controller.GameController;
import com.example.firstgame.menu.PauseDrawable;
import com.example.firstgame.object.Ball;
import com.example.firstgame.object.RespawnTime;
import com.example.firstgame.object.ObjectSize;
import com.example.firstgame.object.Threat;
import com.example.firstgame.score.Score;

import java.io.InputStream;
import java.util.Vector;

@SuppressLint("ResourceType")
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameController gameController;
    private PauseDrawable drawable;

    Bitmap[] bitmaps = new Bitmap[20];

    public GameView(Context context) {
        super(context);
        this.setFocusable(true);
        getHolder().addCallback(this);

//        pauseView = new PauseView(this.getContext());
        drawable = new PauseDrawable();
    }

    @Override
    public void draw(Canvas canvas) {
        synchronized (gameController) {
            super.draw(canvas);
            gameController.getScore().draw(canvas);
            Vector<Threat> threats = gameController.getThreats();
            for(int i = 0; i < threats.size(); i++) {
                threats.get(i).draw(canvas);
            }
            gameController.getBall().draw(canvas);
            canvas.drawBitmap(bitmaps[14], this.getWidth() - 80, 0, null);
            if(gameController.isPause()) drawable.draw(canvas);
        }
    }

    private Bitmap[] subArray(Bitmap[] arr, int start_index, int end_index) {
        if(start_index > end_index) return null;

        Bitmap[] subArr = new Bitmap[end_index - start_index + 1];
        for(int i = start_index; i <= end_index; i++) {
            subArr[i-start_index] = arr[i];
        }
        return subArr;
    }

    private void initGame() {
        int[] list = {R.drawable.zero, R.drawable.one, R.drawable.two, R.drawable.three, //0, 1, 2, 3
                R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, //4, 5, 6, 7
                R.drawable.eight, R.drawable.nine, R.drawable.score, R.drawable.vn_ball, //8, 9, 10, 11
                R.drawable.setting_icon, R.drawable.threat, R.drawable.setting_icon}; //12, 13, 14
        InputStream inputStream;

        for(int i = 0; i < list.length; i++) {
            inputStream = getResources().openRawResource(list[i]);
            bitmaps[i] = BitmapFactory.decodeStream(inputStream);

        }

        bitmaps[14] = Bitmap.createScaledBitmap(bitmaps[14], bitmaps[14].getWidth()/2,
                bitmaps[14].getHeight()/2, false);

        Ball ball = new Ball(GameView.this, bitmaps[11], ObjectSize.BALL_WIDTH, ObjectSize.BALL_HEIGHT);
        Threat basicThreat = new Threat(GameView.this, ball, bitmaps[13], ObjectSize.ROAD_WIDTH,
                ObjectSize.HOLD_HEIGHT);
        Score score = new Score(GameView.this, bitmaps[10],
                subArray(bitmaps, 0, 9), 250, 300);

        RespawnTime respawnTime = new RespawnTime(0);
        this.gameController = new GameController(GameView.this, respawnTime, ball, basicThreat,
                new Vector<>(), score);

        gameController.setLevel(GameController.EASY);
//        basicThreat.setHoldWidth(gameController.getLevel()*2);
    }

    int a = 1;
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                initGame();
                gameController.runGame();
            }
        }).start();

        this.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                synchronized (this.gameController) {
                    switch (gameController.touchProcess(motionEvent)) {
                        case GameController.PAUSE:

                        case GameController.NOT_PAUSE:
                    }
                }
            return true;
        });
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
