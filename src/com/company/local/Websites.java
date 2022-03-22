package com.company.local;

import com.company.API.BlockedWebsitesAPI;
import com.company.utils.Config;
import kong.unirest.json.JSONObject;

import java.io.*;
import java.util.Set;


public class Websites {
    public static void modifyHosts(Set<String> apps){
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
            int startInd = oldContent.indexOf("# Added by argus");
            StringBuilder newContent;
            if (startInd != -1) {
                newContent = new StringBuilder(oldContent.substring(0, startInd));
            }
            else{
                newContent = new StringBuilder(oldContent);
            }

            newContent.append("# Added by argus").append(System.lineSeparator());
            for (String app: apps) {
                newContent.append(ipRoute).append(" ").append(app).append(System.lineSeparator());
            }

            System.out.println(newContent);
            writer.write(newContent.toString());
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

    public static void loadBlockedWebsites()
    {
        JSONObject blockedWebsites = BlockedWebsitesAPI.getBlockedWebsites();
        System.out.println("Blocked websites: " + blockedWebsites);
        if (blockedWebsites != null) {
            Set<String> apps = blockedWebsites.keySet();
            modifyHosts(apps);
        }
    }
}
