package com.example.firstgame.menu;

import android.view.SurfaceHolder;

public class MenuThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameMenuView gameMenuView;

    public MenuThread(SurfaceHolder surfaceHolder, GameMenuView gameMenuView) {
        this.gameMenuView = gameMenuView;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {

    }
}
