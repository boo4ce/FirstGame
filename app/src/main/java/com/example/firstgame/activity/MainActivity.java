package com.example.firstgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.example.firstgame.attributes.Level;
import com.example.firstgame.view.GameView;
import com.example.firstgame.view.PauseView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(new GameView(this));
//        Level.setLevel(getIntent().getIntExtra("level", -1));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Destroy2");
    }

    @Override
    public void finish() {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        super.finish();
    }
}
