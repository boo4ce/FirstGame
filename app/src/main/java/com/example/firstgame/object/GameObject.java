package com.example.firstgame.object;

import android.graphics.Bitmap;

public abstract class GameObject {
    //
    protected Bitmap image;

    // num of object in image (col x row)
    protected final int col;
    protected final int row;

    // size of object
    protected final int width;
    protected final int height;

    //coordinate of object
    protected int x; // horizontal
    protected int y; // vertical

    protected GameObject(Bitmap image, int width, int height) {
        this.image = image;

        this.width = width;
        this.height = height;

        this.col = image.getWidth() / width;
        this.row = image.getHeight() / height;
    }

    protected Bitmap getSubImage(int x, int y) {
        Bitmap bitmap = Bitmap.createBitmap(image, x, y, width, height);
        return bitmap;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
