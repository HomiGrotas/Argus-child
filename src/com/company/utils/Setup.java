package com.company.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Setup {
    public static boolean mkdir() {
        File dir = new File(Config.properties.getProperty("DIRECTORY_NAME"));
        return dir.mkdir();
    }
}
