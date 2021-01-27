package com.example.firstgame.main;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.firstgame.controller.GameController;
import com.example.firstgame.object.RespawnTime;

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

    @Override
    public void run() {
        while(true) {
            if(gameController.isRunning()) {
                if(gameController.checkCollision()) {
                    gameController.setGameOver();
                }
                else {
                    Canvas canvas = null;
                    try {
                        canvas = this.surfaceHolder.lockCanvas();
                        synchronized (canvas) {
                            gameController.update();
                            gameView.draw(canvas);

                            this.count_frm.increase();
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

                    if(count_frm.getCount() == gameController.getLevel()) {
                        synchronized (this.count_frm) {
                            count_frm.notify();
                            count_frm.reset();
                        }
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
