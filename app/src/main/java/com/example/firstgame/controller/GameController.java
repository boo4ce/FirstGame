package com.example.firstgame.controller;

import android.view.MotionEvent;

import com.example.firstgame.main.GameView;
import com.example.firstgame.object.Ball;
import com.example.firstgame.object.RespawnTime;
import com.example.firstgame.object.Threat;
import com.example.firstgame.score.Score;

import java.util.Vector;

public class GameController {
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

    public GameController(GameView gameView, RespawnTime respawnTime, Ball ball, Threat basicThreat,
                          Vector<Threat> threats, Score score) {
        this.gameView = gameView;
        this.ball = ball;
        this.threats = threats;
        this.score = score;
        this.basicThreat = basicThreat;
        this.respawnTime = respawnTime;
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

        threats.add(basicThreat);
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

    public void touchProcess(MotionEvent motionEvent) {
        int num_touch = motionEvent.getPointerCount();
        switch(num_touch) {
            case 1:
                if(running == false) reset();
                else {
                    for(int i = 0; i < threats.size(); i++)
                        if(threats.get(i).getHoldState()) {
                            threats.get(i).stopHold(); break;
                        }
                }
                break;
            case 2: pause = !pause; break;

            default: break;
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
}