package com.example.firstgame.thread;

import android.graphics.Canvas;
import android.os.Build;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;

import com.example.firstgame.attributes.Level;
import com.example.firstgame.controller.GameController;
import com.example.firstgame.object.RespawnTime;
import com.example.firstgame.view.GameView;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameController gameController;
    private RespawnTime count_frm;
    private GameView gameView;

    public MainThread(GameView gameView, GameController gameController, RespawnTime count_frm) {
        this.gameController = gameController;
        this.surfaceHolder = gameView.getHolder();
        this.count_frm = count_frm;
        this.gameView = gameView;
    }

    private void updateCanvas() {
        Canvas canvas = null;
        try {
            canvas = this.surfaceHolder.lockCanvas();
            synchronized (canvas) {
                gameView.draw(canvas);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            if(canvas != null) {
                this.surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public void run() {
        while(true) {
            if(gameController.isRunning()) {
                if(gameController.checkCollision()) {
                    gameController.setGameOver();
                }
                else {
                    gameController.update();
                    this.updateCanvas();

                    this.count_frm.increase();
                    if(count_frm.getCount() == Level.getLevel()) {
                        gameController.threatController();
                        count_frm.reset();
                    }
                }
            }
            else {
                this.updateCanvas();
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
