package com.example.firstgame.activity;

import android.os.Bundle;
import android.view.View;

import com.example.firstgame.R;

public class SettingActivity extends FullScreenActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        this.setContentView(R.layout.setting);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
}
