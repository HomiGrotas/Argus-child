package com.company.utils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Files {
    public static boolean createFile(String filename, String content)
    {
        boolean created = false;
        File file = new File(filename);
        FileWriter fileWriter;

        try {
            created =  file.createNewFile();
            if (created)
            {
                fileWriter = new FileWriter(filename);
                fileWriter.write(content);
                fileWriter.close();
            }

        }catch (IOException ignored) {
        }
        return created;
    }
}
