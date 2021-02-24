package com.example.firstgame;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        writeContents("./hello");
        System.out.println(Boolean.parseBoolean(getContents("./hello")));
    }

    private String getContents(String file_path) {
        String a = "";
        try {
            FileInputStream fin = new FileInputStream(file_path);
            ObjectInputStream in = new ObjectInputStream(fin);
            a = (String) in.readObject();
            in.close();
            fin.close();
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
        return a;
    }

    private void writeContents(String file_path) {
        try {
            FileOutputStream fout = new FileOutputStream(file_path);
            ObjectOutputStream out = new ObjectOutputStream(fout);

            out.writeObject("True");
            out.close();
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}