package com.example.firstgame.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.firstgame.R;
import com.example.firstgame.attributes.Level;

public class SelectLevelActivity extends FullScreenActivity {
    private int current_button_pressing = -1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle saveInstanceBundle) {
        super.onCreate(saveInstanceBundle);
        this.setContentView(R.layout.select_level);

        final int[] selected_level = {R.id.slow, R.id.normal, R.id.fast};
        int[] level = {Level.SLOW, Level.NORMAL, Level.FAST};

        for(int i = 0; i < 3; i++) {
            final int j = i;
            findViewById(selected_level[i]).setOnTouchListener((v, event) -> {
                if(current_button_pressing == -1)
                    current_button_pressing = j;
                if(current_button_pressing != j) return false;

                setAnimClick(findViewById(selected_level[j]), event);
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    openNewActivity(level[j]);
//                    setResult(RESULT_OK);
                    SelectLevelActivity.this.finish();
                    current_button_pressing = -1;
                }
                return true;
            });
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setResult(RESULT_OK);
                SelectLevelActivity.this.onBackPressed();
            }
        });
    }

    private void openNewActivity(int level) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Level.setLevel(level);
        intent.putExtra("flag", MenuActivity.START_NEW_GAME);
//        intent.putExtra("level", level);
        startActivity(intent);
        overridePendingTransition(R.anim.appear, R.anim.nothing);
    }

    private void setAnimClick(Button button, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            button.setAlpha(0.3F);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            button.setAlpha(1);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
