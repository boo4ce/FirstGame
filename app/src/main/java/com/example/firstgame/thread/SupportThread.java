package com.example.firstgame.thread;

import android.view.SurfaceHolder;

import com.example.firstgame.controller.GameController;
import com.example.firstgame.object.RespawnTime;
import com.example.firstgame.view.GameView;

public class SupportThread extends Thread {
    private GameController gameController;
    private GameView gameView;
    private SurfaceHolder surfaceHolder;

    public SupportThread(GameView gameView, GameController gameController) {
        this.gameController = gameController;
        this.gameView = gameView;
        this.surfaceHolder = gameView.getHolder();
    }

    @Override
    public void run() {
        while(gameController.isPause()) {

        }
    }
}
