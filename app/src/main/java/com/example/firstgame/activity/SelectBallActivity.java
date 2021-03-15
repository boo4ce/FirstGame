package com.example.firstgame.activity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.example.firstgame.R;
import com.example.firstgame.attributes.Config;

public class SelectBallActivity extends FullScreenActivity {
    private int selected_ball_Id;
    private int previous_ball_Id;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        this.setContentView(R.layout.ball);
        selected_ball_Id = Config.getBall_id();
        previous_ball_Id = selected_ball_Id;
        findViewById(selected_ball_Id).setBackgroundResource(R.color.light_gray_66a);

        final MediaPlayer clickSound = MediaPlayer.create(this, R.raw.click);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectBallActivity.this.onBackPressed();
            }
        });

        final int[] list_id = {R.id.yellow_ball, R.id.dark_red_ball, R.id.blue_ball,
                R.id.black_white_ball, R.id.tennis_ball};
        final int[] list_resId = {R.drawable.yellow_ball, R.drawable.dark_red_ball, R.drawable.blue_ball,
                R.drawable.black_white_ball, R.drawable.tennis_ball_b};

        for(int i = 0; i < list_id.length; i++) {
            final int j = i;
            findViewById(list_id[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    previous_ball_Id = selected_ball_Id;
                    selected_ball_Id = list_id[j];
                    setSelectBall(list_resId[j]);
                    if(Config.getSound()) clickSound.start();
                }
            });
        }
    }

    private void setSelectBall(int resId) {
        findViewById(previous_ball_Id).setBackgroundResource(R.color.transparent);
        findViewById(selected_ball_Id).setBackgroundResource(R.color.light_gray_66a);
        Config.setBall_resId(resId);
        Config.setBall_id(selected_ball_Id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
