package com.example.firstgame.main;

import com.example.firstgame.object.CountFrame;

public class SupportThread extends Thread {
    private GameView gameView;
    private CountFrame count_frm;

    public SupportThread(GameView gameView, CountFrame count_frm) {
        this.gameView = gameView;
        this.count_frm = count_frm;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (count_frm) {
                try {
                    count_frm.wait();
                    this.gameView.threatController();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }
}
