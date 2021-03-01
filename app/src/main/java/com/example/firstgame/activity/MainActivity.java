package com.example.firstgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.firstgame.controller.GameController;
import com.example.firstgame.controller.IOFile;
import com.example.firstgame.view.GameView;

import java.io.File;

public class MainActivity extends FullScreenActivity {
    private IOFile ioFile;
    private GameView gameView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        ioFile = new IOFile();
        gameView = new GameView(this);

        Intent intent = getIntent();
        if(intent.getIntExtra("flag", 0) == MenuActivity.START_WITH_PREVIOUS_GAME) {
            try {
                gameView.addContent(ioFile.readData());
            } catch (Exception e) {
                Toast.makeText(this, "Error: Can not continue game !!", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
        this.setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        this.save();
        super.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if(gameView.pause()) {
            return super.onKeyDown(keyCode, keyEvent);
        }

        return false;
    }

    private void save() {
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

}
