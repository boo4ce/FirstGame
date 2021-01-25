package com.example.firstgame.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.renderscript.ScriptGroup;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.firstgame.R;
import com.example.firstgame.object.Background;
import com.example.firstgame.object.Ball;
import com.example.firstgame.object.CountFrame;
import com.example.firstgame.object.Geometry;
import com.example.firstgame.object.Hold;
import com.example.firstgame.object.ObjectSize;
import com.example.firstgame.object.Threat;
import com.example.firstgame.score.Score;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Vector;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread mainThread;
    private SupportThread supportThread;

    private Ball ball;
    private Background background;
    private Vector<Threat> threats;
    private Score score;

    private Threat basicThreat;

    public boolean running;

    public GameView(Context context) {
        super(context);
        this.setFocusable(true);
        getHolder().addCallback(this);

        threats = new Vector<>();
    }

    public void update() {
        this.ball.update();
//        this.background.update();
        for(int i = 0; i < threats.size(); i++) {
            threats.get(i).update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        this.background.draw(canvas);
        score.draw(canvas);
        for(int i = 0; i < threats.size(); i++) {
            threats.get(i).draw(canvas);
        }
        this.ball.draw(canvas);
    }

    public void threatController() {
        for(int i = 0; i < threats.size(); i++) {
            if(threats.get(i).die()) {
                threats.remove(i);
            }
        }

        threats.add(basicThreat.clone());
    }

    public boolean checkCollision() {
        for(int i = 0; i < threats.size(); i++) {
            Threat threat = threats.get(i);
            if(threat.isEffected() || threat.getY() < ball.getY() - 80) continue;

            Hold hold1 = threat.getHold();
            if(hold1.getY() <= ball.getY() + 240) {
                score.unlock();
                double a = Geometry.lengthOfThirdEdge(ball.getCenter_x(), ball.getCenter_y(),
                        hold1.getCenter_x(), hold1.getCenter_y(),
                        hold1.getX() + hold1.getWidth(), hold1.getY());
                if(a < ball.getPerimeter()) return true;

                a = Geometry.lengthOfThirdEdge(ball.getCenter_x(), ball.getCenter_y(),
                        hold1.getCenter_x(), hold1.getCenter_y(),
                        hold1.getX(), hold1.getY());
                if(a < ball.getPerimeter()) return true;

                a = Geometry.lengthOfThirdEdge(ball.getCenter_x(), ball.getCenter_y(),
                        hold1.getCenter_x(), hold1.getCenter_y(),
                        hold1.getX(), hold1.getY() + hold1.getHeight());
                if(a < ball.getPerimeter()) return true;

                a = Geometry.lengthOfThirdEdge(ball.getCenter_x(), ball.getCenter_y(),
                        hold1.getCenter_x(), hold1.getCenter_y(),
                        hold1.getX() + hold1.getWidth(), hold1.getY() + hold1.getHeight());
                if(a < ball.getPerimeter()) return true;

                if(hold1.getX() > ball.getCenter_x() || hold1.getX() + 300 < ball.getCenter_x())
                    return true;
            }
            else {
                score.gainScore();
                threat.set_Effected();
            }
        }
        return false;
    }

    @SuppressLint("ResourceType")
    private void createObject() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Bitmap[] bitmaps = new Bitmap[20];
                int[] list = {R.drawable.zero, R.drawable.one, R.drawable.two, R.drawable.three, //0, 1, 2, 3
                        R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, //4, 5, 6, 7
                        R.drawable.eight, R.drawable.nine, R.drawable.score, R.drawable.vn_ball, //8, 9, 10, 11
                        R.drawable.setting_icon, R.drawable.threat}; //12, 13
                InputStream inputStream;

                for(int i = 0; i < list.length; i++) {
                    inputStream = getResources().openRawResource(list[i]);
                    bitmaps[i] = BitmapFactory.decodeStream(inputStream);

                }

                if(true) {
                    ball = new Ball(GameView.this, bitmaps[11], ObjectSize.BALL_WIDTH, ObjectSize.BALL_HEIGHT);
                    basicThreat = new Threat(GameView.this, bitmaps[13], ObjectSize.ROAD_WIDTH,
                            ObjectSize.HOLD_HEIGHT, 300, 80);
                    score = new Score(GameView.this, bitmaps, 250, 300);
                }
            }
        }).start();
    }

    private void newGame() {
        CountFrame count_frm = new CountFrame(0);

        this.mainThread = new MainThread(getHolder(), this, count_frm);
        this.running = true;

        this.supportThread = new SupportThread(this, count_frm);

        this.supportThread.start();
        this.mainThread.start();
    }

    private void clear() {
        score.reset();
        threats.clear();
        ball.reset();
        background.reset();

        running = true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        this.createObject();
        this.newGame();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(running == false) clear();
                else
                    for(int i = 0; i < threats.size(); i++)
                        if(threats.get(i).getHoldState()) {
                            threats.get(i).stopHold(); break;
                        }
            }
        });
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
