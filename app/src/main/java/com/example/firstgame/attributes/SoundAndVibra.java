package com.example.firstgame.attributes;

public class SoundAndVibra {
    private static boolean sound;
    private static boolean vibra;

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
}
