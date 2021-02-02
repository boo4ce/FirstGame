package com.example.firstgame.thread;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.firstgame.attributes.Level;
import com.example.firstgame.controller.GameController;
import com.example.firstgame.object.RespawnTime;
import com.example.firstgame.view.GameView;

public class SupportThread extends Thread {
    private GameController gameController;
    private RespawnTime respawnTime;
    private boolean running = true;

    public SupportThread(GameController gameController, RespawnTime respawnTime) {
        this.gameController = gameController;
        this.respawnTime = respawnTime;
    }

    @Override
    public void run() {
        while(running) {
            if (gameController.checkCollision()) {
                gameController.setGameOver();
            }
            else {
                this.respawnTime.increase();
                if(respawnTime.getCount() == Level.getLevel()) {
                    gameController.threatController();
                    respawnTime.reset();
                }
            }
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final void wakeup() {
        synchronized (this) {
            this.notify();
        }
    }

    public final void kill() {
        running = false;
    }
}
