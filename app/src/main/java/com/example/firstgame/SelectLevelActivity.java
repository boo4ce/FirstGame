package com.example.firstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.firstgame.attributes.Level;

public class SelectLevelActivity extends Activity {
    @Override
    protected void onCreate(Bundle saveInstanceBundle) {
        super.onCreate(saveInstanceBundle);
        this.setContentView(R.layout.select_level);

        int[] selected_level = {R.id.slow, R.id.normal, R.id.fast};
        int[] level = {Level.SLOW, Level.NORMAL, Level.FAST};

        for(int i = 0; i < 3; i++) {
            final int j = i;
            findViewById(selected_level[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openNewActivity(level[j]);
                    SelectLevelActivity.this.finish();
                }
            });
        }
    }

    private void openNewActivity(int level) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Level.setLevel(level);
        startActivity(intent);
    }
}
