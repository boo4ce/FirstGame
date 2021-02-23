package com.example.firstgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.firstgame.controller.GameController;
import com.example.firstgame.controller.IOFile;
import com.example.firstgame.view.GameView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends Activity {
    private IOFile ioFile;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ioFile = new IOFile(new File(getFilesDir(), "filesave"));

//        Level.setLevel(getIntent().getIntExtra("level", -1));
        Intent intent = getIntent();
        if(intent.getIntExtra("flag", 0) == MenuActivity.START_WITH_PREVIOUS_GAME) {
            try {
                this.setContentView(new GameView(this, ioFile.readData()));
            } catch (Exception e) {
                Toast.makeText(this, "Error: Can not continue game !!", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        } else {
            this.setContentView(new GameView(this));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void finish() {
        if(GameController.filesave() == "") {
            ioFile.delete();
        }
        else {
            try {
                ioFile.writeData(GameController.filesave());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.finish();
    }

}
