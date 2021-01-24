package com.example.firstgame.object;

public class CountFrame {
    private int count;

    public CountFrame(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increase() {
        count++;
    }
}
