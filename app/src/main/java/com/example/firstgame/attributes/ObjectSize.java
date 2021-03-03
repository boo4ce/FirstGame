package com.example.firstgame.attributes;

import com.example.firstgame.view.GameView;

public class ObjectSize {
    // default in screen size 1600x900
    private static final int BALL_WIDTH = 160;
    private static final int BALL_HEIGHT = 160;
    private static final int HOLD_HEIGHT = 80;
    private static final int SETTING_BUTTON = 225;
    private static final int CONTROL_BUTTON = 150;
    private static final int SETTING_ICON = 80;
    private static final int SCORE_SCRIPT_WIDTH = 410;
    private static final int SCORE_SCRIPT_HEIGHT = 120;
    private static final int SCORE_WIDTH = 250;
    private static final int SCORE_HEIGHT = 300;


    public static int getBallWidth() {
        return GameView.getRatio(BALL_WIDTH);
    }

    public static int getBallHeight() {
        return GameView.getRatio(BALL_HEIGHT);
    }

    public static int getHoldHeight() {
        return GameView.getRatio(HOLD_HEIGHT);
    }

    public static int getSettingButton() {
        return GameView.getRatio(SETTING_BUTTON);
    }

    public static int getControlButton() {
        return GameView.getRatio(CONTROL_BUTTON);
    }

    public static int getSettingIcon() {
        return GameView.getRatio(SETTING_ICON);
    }

    public static int getScoreScriptWidth() {
        return GameView.getRatio(SCORE_SCRIPT_WIDTH);
    }

    public static int getScoreScriptHeight() {
        return GameView.getRatio(SCORE_SCRIPT_HEIGHT);
    }

    public static int getScoreWidth() {
        return GameView.getRatio(SCORE_WIDTH);
    }

    public static int getScoreHeight() {
        return GameView.getRatio(SCORE_HEIGHT);
    }
}
