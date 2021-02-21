package com.example.firstgame.thread;

import com.example.firstgame.activity.MenuActivity;

public class TitleAnimationThread extends Thread {
    private MenuActivity menuActivity;
    private boolean running = true;
    private int current_frame = 0;

    public TitleAnimationThread(MenuActivity menuActivity) {
        this.menuActivity = menuActivity;
    }

    @Override
    public void run() {
        while(running) {
            menuActivity.updateFrame(current_frame++);
            if(current_frame == 28) current_frame = 0;
        }
    }

    public void kill() {
        this.running = false;
    }

    public void reset() {
        this.running = true;
        current_frame = 0;
    }
}
