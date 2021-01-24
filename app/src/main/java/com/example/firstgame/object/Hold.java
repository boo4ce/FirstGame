package com.example.firstgame.object;

public class Hold {
    private int x, y;
    private int width, height;
    private int center_x, center_y;

    public Hold(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.center_x = x + width/2;
        this.center_y = y + height/2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCenter_x() {
        return center_x;
    }

    public int getCenter_y() {
        return center_y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
