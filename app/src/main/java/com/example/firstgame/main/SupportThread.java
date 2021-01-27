package com.example.firstgame.main;

import com.example.firstgame.controller.GameController;
import com.example.firstgame.object.RespawnTime;

public class SupportThread extends Thread {
    private GameController gameController;
    private RespawnTime count_frm;

    public SupportThread(GameController gameController, RespawnTime count_frm) {
        this.gameController = gameController;
        this.count_frm = count_frm;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (count_frm) {
                try {
                    count_frm.wait();
                    this.gameController.threatController();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }
}
