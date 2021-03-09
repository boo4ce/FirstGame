package com.example.firstgame.attributes;

public class Config {
    private static boolean sound;
    private static boolean vibra;
    private static int ball_resId;
    private static int ball_id;
    private static int screen_width;
    private static int screen_height;

    public static boolean getSound() {
        return sound;
    }

    public static boolean getVibra() {
        return vibra;
    }

    public static void turnSoundON() {
        sound = true;
    }

    public static void turnSoundOFF() {
        sound = false;
    }

    public static void turnVibraON() {
        vibra = true;
    }

    public static void turnVibraOFF() {
        vibra = false;
    }

    public static void changeSoundState() {
        sound = !sound;
    }

    public static void changeVibraState() {
        vibra = !vibra;
    }

    public static int getBall_resId() {
        return ball_resId;
    }

    public static void setBall_resId(int ball_resId) {
        Config.ball_resId = ball_resId;
    }

    public static int getBall_id() {
        return ball_id;
    }

    public static void setBall_id(int ball_id) {
        Config.ball_id = ball_id;
    }

    public static int getScreenWidth() {
        return screen_width;
    }

    public static void setScreenWidth(int screenWidth) {
        Config.screen_width = screen_width;
    }

    public static int getScreenHeight() {
        return screen_height;
    }

    public static void setScreenHeight(int screenHeight) {
        Config.screen_height = screen_height;
    }
}
