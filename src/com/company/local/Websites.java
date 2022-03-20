package com.company.local;

import com.company.utils.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Websites {
    public static void modifyHosts(String[] apps) throws FileNotFoundException {
        File hosts = new File("c:\\windows\\system32\\drivers\\etc\\hosts");
        String ipRoute = Config.properties.getProperty("IP_ROUTE");

        StringBuilder oldContent = new StringBuilder();

        BufferedReader reader = null;

        FileWriter writer = null;

        try
        {
            reader = new BufferedReader(new FileReader(hosts));
            String line = reader.readLine();

            while (line != null)
            {
                oldContent.append(line).append(System.lineSeparator());

                line = reader.readLine();
            }

            writer = new FileWriter(hosts);

            if (oldContent.indexOf("Argus") == -1)
                oldContent.append("# Added by argus").append(System.lineSeparator());
            for (String app: apps) {
                oldContent.append(ipRoute).append(" ").append(app).append(System.lineSeparator());
            }

            writer.write(oldContent.toString());
            System.out.println(oldContent);
        }
        catch (IOException ignored) {}

        finally {
            try {
                //Closing the resources
                if (reader != null)
                    reader.close();
                if (writer != null)
                    writer.close();
            } catch (IOException ignored) {}
        }
    }

    public static void main(String[] args) {
        try {
            modifyHosts(new String[]{});
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
