package com.example.firstgame.controller;

import com.example.firstgame.main.GameView;
import com.example.firstgame.main.MainThread;
import com.example.firstgame.main.SupportThread;
import com.example.firstgame.object.Ball;
import com.example.firstgame.object.CountFrame;
import com.example.firstgame.object.Threat;
import com.example.firstgame.score.Score;

import java.util.Vector;

public class ControlGame {
    private GameView gameView;

    public ControlGame(GameView gameView) {
        this.gameView = gameView;
    }

    public void update(Ball ball, Vector<Threat> threats) {
        ball.update();
        for(int i = 0; i < threats.size(); i++) {
            threats.get(i).update();
        }
    }

    public void threatController(Vector<Threat> threats, Threat basicThreat) {
        for(int i = 0; i < threats.size(); i++) {
            if(threats.get(i).die()) {
                threats.remove(i);
            }
        }

        threats.add(basicThreat.clone());
    }

    public boolean checkCollision(Vector<Threat> threats, Score score) {
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

    public void newGame(GameView gameView, MainThread mainThread, SupportThread supportThread) {
        CountFrame count_frm = new CountFrame(0);

        mainThread = new MainThread(gameView.getHolder(), gameView, count_frm);
        gameView.running = true;
        gameView.pause = false;

        supportThread = new SupportThread(gameView, count_frm);

        supportThread.start();
        mainThread.start();
    }


}
