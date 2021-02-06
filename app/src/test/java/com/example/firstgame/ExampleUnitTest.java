package com.example.firstgame;

import org.junit.Test;

import java.io.InputStream;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        float ratio = ((float)1500*700)/((float)1600*900);
        ratio = (float) Math.sqrt(ratio);
        ratio = Math.round(ratio*10)/10F;
        System.out.println(ratio);
    }
}