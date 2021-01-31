package com.example.firstgame.attributes;

public class Level {
    //level
    public static final int SLOW = 200;
    public static final int NORMAL = 150;
    public static final int FAST = 100;
    public static final int HELL = 50;

    private static int level;

    public static void setLevel(int level) {
        Level.level = level;
    }

    public static int getLevel() {
        return level;
    }
}
