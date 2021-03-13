package com.example.firstgame.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.firstgame.controller.GameController;
import com.example.firstgame.controller.IOFile;
import com.example.firstgame.view.GameView;

public class MainActivity extends FullScreenActivity {
    private IOFile ioFile;
    private GameView gameView;
    private boolean isPause, isDispose;
    private String current_game;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        isPause = false; isDispose = false;

        ioFile = new IOFile();
        gameView = new GameView(this);

        // load filesave
        // if don't have filesave, create new game
        Intent intent = getIntent();
        if(intent.getIntExtra("flag", 0) == MenuActivity.START_WITH_PREVIOUS_GAME) {
            try {
                gameView.loadContent(ioFile.readData(), GameController.NOT_PAUSE);
            } catch (Exception e) {
                Toast.makeText(this, "Error: Can not continue game !!", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }

        this.setContentView(gameView);
    }

    @Override
    protected void onStart() {
        if(isPause) {
            gameView.loadContent(current_game, GameController.PAUSE);
//            gameView.pause(false);
            isPause = false;
        }
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!isDispose) {
            gameView.pause(true);
            current_game = gameView.filesave();
            isPause = true;
        }
    }

    private void save() {
        // write filesave
        if(gameView.filesave().equals("")) {
            ioFile.delete();
        }
        else {
            try {
                ioFile.writeData(gameView.filesave());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void finish() {
        this.save();
        isDispose = true;
        super.finish();
    }

    @Override
    public void onBackPressed() {
        if(gameView.pause(false)) {
            super.onBackPressed();
        }
    }
}
