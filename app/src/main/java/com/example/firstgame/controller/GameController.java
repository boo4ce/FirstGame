package com.example.firstgame.controller;

import android.view.MotionEvent;

import com.example.firstgame.view.GameView;
import com.example.firstgame.thread.MainThread;
import com.example.firstgame.object.Ball;
import com.example.firstgame.object.RespawnTime;
import com.example.firstgame.object.Threat;
import com.example.firstgame.score.Score;

import java.util.Vector;

public class GameController {
    //level
    public static final int EASY = 200;
    public static final int NORMAL = 150;
    public static final int HARD = 100;

    //pause
    public static final int PAUSE = 1;
    public static final int NOT_PAUSE = 0;

    // game view
    private GameView gameView;

    // game object
    private Ball ball;
    private Vector<Threat> threats;
    private Score score;
    private Threat basicThreat;
    private RespawnTime respawnTime;

    //flag
    private boolean running = true;
    private boolean pause = false;

    // thread
    private MainThread mainThread;

    private int level;

    public GameController(GameView gameView, RespawnTime respawnTime, Ball ball, Threat basicThreat,
                          Vector<Threat> threats, Score score) {
        this.gameView = gameView;
        this.ball = ball;
        this.threats = threats;
        this.score = score;
        this.basicThreat = basicThreat;
        this.respawnTime = respawnTime;

        this.mainThread = new MainThread(gameView, this, respawnTime);

    }

    public void runGame() {
        mainThread.start();
    }

    public void update() {
        ball.update();
        for(int i = 0; i < threats.size(); i++) {
            threats.get(i).update();
        }
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
        int flag;
        for(int i = 0; i < threats.size(); i++) {
            flag = threats.get(i).checkCollision_and_getScore();
            switch(flag) {
                case Threat.COLLISION: return true;
                case Threat.GET_SCORE: score.gainScore(); break;
                case Threat.IN_HOLD: score.unlock(); break;
                case Threat.NO_COLLISION:
            }
        }
        return false;
    }

    public void restart() {
        synchronized (this.mainThread) {
            reset();
            this.mainThread.notify();
        }
    }

    public void resume() {
        synchronized (this.mainThread) {
            pause = false;
            this.mainThread.notify();
        }
    }

    public int touchProcess(MotionEvent motionEvent) {
        if(motionEvent.getX() >= gameView.getWidth() - 80 && motionEvent.getY() <= 80) {
            if(pause == true) resume();
            else pause = !pause;

            return GameController.PAUSE;
        }
        else {
            if(running == false) restart();
            else {
                for(int i = 0; i < threats.size(); i++)
                    if(threats.get(i).getHoldState()) {
                        threats.get(i).stopHold(); break;
                    }
            }
            return GameController.NOT_PAUSE;
        }
    }

    private void reset() {
        score.reset();
        threats.clear();
        ball.reset();
        respawnTime.reset();

        running = true;
        pause = false;
    }

    public Ball getBall() {
        return ball;
    }

    public Vector<Threat> getThreats() {
        return threats;
    }

    public Score getScore() {
        return score;
    }

    public boolean isRunning() {
        return running && !pause;
    }

    public void setGameOver() {
        running = false;
    }

    public void gamePause() {
        pause = true;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isPause() {
        return pause;
    }
}
