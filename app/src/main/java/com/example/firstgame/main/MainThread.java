package com.example.firstgame.main;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.firstgame.menu.GameMenuView;
import com.example.firstgame.object.CountFrame;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private CountFrame count_frm;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView, CountFrame count_frm) {
        this.gameView = gameView;
        this.surfaceHolder = surfaceHolder;
        this.count_frm = count_frm;
    }

    @Override
    public void run() {
        while(true) {
            if(gameView.running && !gameView.pause) {
                Canvas canvas = null;
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (canvas) {
                        gameView.update();

                        if(gameView.checkCollision()) {
                            gameView.running = false;
                        }

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

                if(count_frm.getCount() == 112) {
                    synchronized (this.count_frm) {
                        count_frm.notify();
                        count_frm.setCount(0);
                    }
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
