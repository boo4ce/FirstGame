package com.example.firstgame.controller;

import android.content.Context;
import android.os.Vibrator;
import android.view.MotionEvent;

import com.example.firstgame.attributes.Level;
import com.example.firstgame.thread.SupportThread;
import com.example.firstgame.view.GameView;
import com.example.firstgame.thread.MainThread;
import com.example.firstgame.object_ingame.Ball;
import com.example.firstgame.attributes.RespawnTime;
import com.example.firstgame.object_ingame.Threat;
import com.example.firstgame.attributes.Score;

import java.util.Vector;

public class GameController {
    //pause
    public static final int PAUSE = 1;
    public static final int NOT_PAUSE = 0;
    public static final int HAVE_FILESAVE = 2;
    public static final int EMPTY_FILESAVE = 3;

    // game view
    private final GameView gameView;

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
    private final MainThread mainThread;
    private final SupportThread supportThread;

    //
    private String content = "";

    //effect
    private final Vibrator vibrator;

    public GameController(GameView gameView, RespawnTime respawnTime, Ball ball, Threat basicThreat,
                          Vector<Threat> threats, Score score) {
        this.gameView = gameView;
        this.ball = ball;
        this.threats = threats;
        this.score = score;
        this.basicThreat = basicThreat;
        this.respawnTime = respawnTime;

        this.supportThread = new SupportThread(this, respawnTime);
        this.mainThread = new MainThread(this.gameView,  this, supportThread);

        vibrator = (Vibrator) gameView.getContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void runGame() {
        mainThread.start();
        supportThread.start();

    }

    public final void update() {
        ball.update();
        for(int i = 0; i < threats.size(); i++) {
            threats.get(i).update();
        }
    }

    public final void threatController() {
        for(int i = 0; i < threats.size(); i++) {
            if(threats.get(i).die()) {
                threats.remove(i);
            }
        }

        threats.add(basicThreat.clone());
    }

    public final boolean checkCollision() {
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

    public final int touchProcess(MotionEvent motionEvent) {
        if(motionEvent.getX() >= gameView.getWidth() - 80 && motionEvent.getY() <= 80) {
            return GameController.PAUSE;
        }
        else {
            for(int i = 0; i < threats.size(); i++)
                if(threats.get(i).getHoldState()) {
                    threats.get(i).stopHold(); break;
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

    public final void setGameOver() {
        running = false;
    }

    public void gamePause() {
        pause = true;
    }

    public boolean isPause() {
        return pause;
    }

    // wake mainthread up to update setting
    public void notifyOnce() {
        synchronized (this.mainThread) {
            this.mainThread.notify();
        }
    }

    public final boolean isOver() {
        return (running==false);
    }

    // init file save for game
    public final void setFilesaveValues(int flag) {
        if(flag == GameController.EMPTY_FILESAVE) {
            content = "";
        }
        else {
            content += Level.getLevel() + "\n";
            content += this.ball.getStatus() + "\n";
            content += this.respawnTime.getCount() + "\n";
            content += this.score.getScore() + "\n";
            content += this.threats.size() + "\n";
            for(Threat threat : threats)
                content += threat.getStatus() + "\n";
        }
    }


    public final void setStatus(String content) throws NumberFormatException{
        String values[] = content.split("\n");
        this.ball.setStatus(values[1]);
        this.respawnTime.setCount(Integer.parseInt(values[2]));
        this.score.setScore(Integer.parseInt(values[3]));
        int threat_size = Integer.parseInt(values[4]);
        for(int i = 0; i < threat_size; i++) {
            Threat threat = basicThreat.clone();
            threat.setStatus(values[5+i]);
            threats.add(threat);
        }
    }

    public final String filesave() {
        return content;
    }

    public final void updateHighScore() {
        this.score.updateHighScore();
    }

    public void clear() {
        this.ball = null;
        this.threats = null;
        this.basicThreat = null;
        this.score = null;
        this.respawnTime = null;
        this.mainThread.kill();
        this.supportThread.kill();
        System.gc();
    }

    public void vibrate() {
        vibrator.vibrate(200);
    }
}
