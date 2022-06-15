package com.company.utils;


import java.io.*;

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

    public static boolean exists(String filename)
    {
        File authFile = new File(filename);
        return authFile.exists();
    }

    public static void copyFile(String from, String to) throws IOException {
        InputStream fromStream = new BufferedInputStream(new FileInputStream(from));
        OutputStream toStream = new BufferedOutputStream(new FileOutputStream(to));
        byte[] buffer = new byte[1024];

        int lengthRead;
        while ((lengthRead = fromStream.read(buffer)) > 0) {
            toStream.write(buffer, 0, lengthRead);
            toStream.flush();
        }
    }
}
