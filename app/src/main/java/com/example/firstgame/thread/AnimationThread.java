package com.example.firstgame.thread;

import com.example.firstgame.activity.MenuActivity;

public class AnimationThread extends Thread {
    private MenuActivity menuActivity;
    private boolean running = true;
    private int current_frame = 0;

    public AnimationThread(MenuActivity menuActivity) {
        this.menuActivity = menuActivity;
    }

    @Override
    public void run() {
        this.running = true;
        while(running) {
            menuActivity.updateFrame(current_frame++);
            if(current_frame == 28) current_frame = 0;

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void kill() {
        this.running = false;
    }

}
