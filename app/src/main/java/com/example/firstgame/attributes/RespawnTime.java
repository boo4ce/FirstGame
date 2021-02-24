package com.example.firstgame.attributes;

public class RespawnTime {
    private int count;

    public RespawnTime(int count) {
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

    public void reset() {
        count = 0;
    }
}
