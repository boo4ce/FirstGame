package com.example.firstgame.thread;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.firstgame.activity.MainActivity;
import com.example.firstgame.controller.GameController;
import com.example.firstgame.view.GameView;

public class MainThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private final GameController gameController;
    private final GameView gameView;
    private final SupportThread supportThread;
    private boolean running = true;

    public MainThread(GameView gameView, GameController gameController,
                      SupportThread supportThread) {
        this.gameController = gameController;
        this.surfaceHolder = gameView.getHolder();
        this.gameView = gameView;
        this.supportThread = supportThread;
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
        while(running) {
            if(gameController.isRunning()) {
                gameController.update();
                this.updateCanvas();
                this.supportThread.wakeup();
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
                this.supportThread.wakeup();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // wait this thread to die and stop it
    public final void kill() {
        running = false;
    }

}
