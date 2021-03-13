package com.example.firstgame.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.firstgame.R;
import com.example.firstgame.attributes.Config;
import com.example.firstgame.attributes.Level;
import com.example.firstgame.controller.GameController;
import com.example.firstgame.custom_drawable.GameOver;
import com.example.firstgame.custom_drawable.PauseDrawable;
import com.example.firstgame.object_ingame.Ball;
import com.example.firstgame.attributes.RespawnTime;
import com.example.firstgame.attributes.ObjectSize;
import com.example.firstgame.object_ingame.Threat;
import com.example.firstgame.attributes.Score;

import java.io.InputStream;
import java.util.Vector;

@SuppressLint("ResourceType")
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameController gameController;
    private PauseDrawable pauseDrawable;
    private GameOver gameOver;

    private String content = "";
    private boolean _continue = false, pauseInvisible = false;
    private static float ratio = 1;
    private int flag;
    Bitmap[] bitmaps = new Bitmap[50];

    public GameView(Context context) {
        super(context);
        this.setFocusable(true);
        getHolder().addCallback(this);
    }

    public void loadContent(String content, int flag) {
        this._continue = true;
        this.content = content;
        this.flag = flag;
        Level.setLevel(Integer.parseInt(content.substring(0, content.indexOf("\n"))));
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
            canvas.drawBitmap(bitmaps[12], this.getWidth() - bitmaps[12].getWidth(), 0, null);
            gameController.getBall().draw(canvas);
            if(gameController.isOver()) {
                gameOver.draw(canvas);
                gameController.getScore().draw(canvas);
            }
            if(gameController.isPause() & !pauseInvisible) pauseDrawable.draw(canvas);
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

    private void setRatio() {
        ratio = ((float)this.getHeight()*this.getWidth())/((float)1600*900);
        ratio = (float) Math.sqrt(ratio);
        ratio = Math.round(ratio*10)/10F;
    }


    private void initGame() {
        this.setRatio();

        int[] list = {R.drawable.zero, R.drawable.one, R.drawable.two, R.drawable.three, //0, 1, 2, 3
                R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, //4, 5, 6, 7
                R.drawable.eight, R.drawable.nine, R.drawable.score, R.drawable.vn_ball, //8, 9, 10, 11
                R.drawable.setting_icon, R.drawable.threat, R.drawable.setting_icon, R.drawable.vibration, //12, 13, 14, 15
                R.drawable.speaker, R.drawable.mute, R.drawable.resume, R.drawable.restart,  // 16, 17, 18, 19
                R.drawable.unvibration, R.drawable.quit}; //20, 21
        list[11] = Config.getBall_resId();

        InputStream inputStream;

        for(int i = 0; i < list.length; i++) {
            inputStream = getResources().openRawResource(list[i]);
            bitmaps[i] = BitmapFactory.decodeStream(inputStream);

            // get size by function below
            int[] size = this.getSize(i);
            if(size[0] == 0) size[0] = bitmaps[i].getWidth();
            if(size[1] == 0) size[1] = bitmaps[i].getHeight();

            // get scale bitmap
            bitmaps[i] = Bitmap.createScaledBitmap(bitmaps[i], size[0], size[1], false);
        }

        Ball ball = new Ball(GameView.this, bitmaps[11], ObjectSize.getBallWidth(),
                ObjectSize.getBallHeight());
        Threat basicThreat = new Threat(GameView.this, ball, bitmaps[13], this.getWidth(),
                ObjectSize.getHoldHeight());
        Score score = new Score(GameView.this, bitmaps[10],
                subArray(bitmaps, 0, 9), 250, 300);

        RespawnTime respawnTime = new RespawnTime(0);
        this.gameController = new GameController(GameView.this, respawnTime, ball, basicThreat,
                new Vector<>(), score);
        if(_continue) {
            try {
                gameController.setStatus(content);
            } catch (Exception ex) {
                getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GameView.this.getContext(), "Error: Can not continue game !!", Toast.LENGTH_SHORT).show();
                        ((Activity) GameView.this.getContext()).finish();
                    }
                });
            }
        }

        pauseDrawable = new PauseDrawable(this.getWidth(), this.getHeight(), bitmaps);
        gameOver = new GameOver(this.getWidth(), this.getHeight(), bitmaps);

    }

    // get size for per object
    private int[] getSize(int i) {
        switch (i) {
            case 0: // number
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8: // still number
            case 9: return new int[]{ObjectSize.getScoreWidth(), ObjectSize.getScoreHeight()};
            case 10: return new int[]{ObjectSize.getScoreScriptWidth(), ObjectSize.getScoreScriptHeight()};
            case 11: return new int[]{ObjectSize.getBallWidth()*6, ObjectSize.getBallHeight()*6};
            case 12: return new int[]{ObjectSize.getSettingIcon(), ObjectSize.getSettingIcon()};
            case 13: return new int[]{0, ObjectSize.getHoldHeight()};
            default: return new int[]{0, 0};
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                initGame();
                gameController.runGame();
                if(flag == GameController.PAUSE) GameView.this.pause(false);

                GameView.this.setOnTouchListener((v, event) -> {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                        synchronized (gameController) {
                            if(gameController.isOver()) {
                                switch (gameOver.touchEvent(event)) {
                                    case -1: break;
                                    case GameOver.QUIT:
                                        dispose();
                                        break;
                                    case GameOver.RESTART:
                                        gameController.restart();
                                        break;
                                }
                            }
                            else if(gameController.isPause()) {
                                switch (pauseDrawable.touchEvent(event)) {
                                    case -1: break;
                                    case PauseDrawable.SPEAKER:
                                        gameController.notifyOnce();
                                        break;
                                    case PauseDrawable.VIBRATION:
                                        gameController.notifyOnce();
                                        break;
                                    case PauseDrawable.QUIT:
                                        disposeAndSave();
                                        break;
                                    case PauseDrawable.RESTART:
                                        gameController.restart();
                                    case PauseDrawable.RESUME:
                                        gameController.resume();
                                }
                            }
                            else{
                                switch (gameController.touchProcess(event)) {
                                    case GameController.PAUSE:
                                        pause(false);
                                    case GameController.NOT_PAUSE:
                                }
                            }
                        }
                    return true;
                });
            }
        }).start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    public static int getRatio(int a) {
        return (int)(a*ratio);
    }

    //
    private void dispose() {
        this.gameController.setFilesaveValues(GameController.EMPTY_FILESAVE);
        this.gameController.updateHighScore();
        this.gameController.clear();
        ((Activity) GameView.this.getContext()).finish();
    }

    private void disposeAndSave() {
        this.gameController.setFilesaveValues(GameController.HAVE_FILESAVE);
        this.gameController.updateHighScore();
        this.gameController.clear();
        ((Activity) GameView.this.getContext()).finish();
    }

    // return previous stt
    public boolean pause(boolean invisible) {
        pauseInvisible = invisible;
        if(invisible) {
            this.gameController.setFilesaveValues(GameController.HAVE_FILESAVE);
            return false;
        }

        boolean stt = gameController.isPause();
        if(stt) {
            this.gameController.setFilesaveValues(GameController.HAVE_FILESAVE);
            this.gameController.updateHighScore();
            this.gameController.clear();
        }

        gameController.gamePause();
        return stt;
    }

    public final String filesave() {
        if(gameController == null) return "";
        return gameController.filesave();
    }
}
