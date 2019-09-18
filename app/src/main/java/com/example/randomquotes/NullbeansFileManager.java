package com.example.randomquotes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class NullbeansFileManager {
    public static byte[] readFile(String path) throws IOException {
        File file = new File(path);
        byte[] fileData = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(fileData);
        return fileData;
    }

    public static void writeFile(String path, byte[] data) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        fileOutputStream.write(data);
    }
}
