package com.example.firstgame.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.opengl.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.firstgame.R;

public class MenuActivity extends Activity {
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.menu_activity);
        Button button = (Button) findViewById(R.id.startGame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectLevelActivity.class);
                startActivity(intent);
            }
        });

        button = findViewById(R.id.quit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity.this.finish();
            }
        });

        button = findViewById(R.id.continues);
        button.setAlpha(0.3F);
    }
}
