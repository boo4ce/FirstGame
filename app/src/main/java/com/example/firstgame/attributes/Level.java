package com.example.firstgame.attributes;

public class Level {
    //level
    public static final int SLOW = 190;
    public static final int NORMAL = 140;
    public static final int FAST = 90;
    public static final int HELL = 50;

    private static int level;

    public static void setLevel(int level) {
        Level.level = level;
    }

    public static int getLevel() {
        return level;
    }
}
