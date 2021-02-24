package com.example.firstgame.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOFile {
    // can only open one file per time
    private static File file;

    public IOFile() {

    }

    public IOFile(File file) {
        this.file = file;
    }

    public static void setFile(File file) {
        IOFile.file = file;
    }

    public void writeData(String data) throws Exception {
        FileOutputStream fout = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fout);

        out.writeObject(data);

        out.close();
        fout.close();

    }

    public String readData() throws Exception{
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fin);

        String string = (String) in.readObject();

        in.close();
        fin.close();

        return string;
    }

    public boolean delete() {
        return file.delete();
    }
}
