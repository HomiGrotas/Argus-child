package com.company.local;

import com.company.API.BlockedWebsitesAPI;
import com.company.utils.Config;
import kong.unirest.json.JSONObject;

import java.io.*;


public class Websites extends Thread {
    JSONObject blockedWebsites;
    private final long UPDATE_RATE_SECONDS = 5;

    public Websites()
    {
        loadBlockedWebsites();
    }

    private void addHost(String host, JSONObject hostData)
    {
        this.blockedWebsites.put(host, hostData);
        modifyHosts();
    }

    private void modifyHosts(){
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
            for (String app: this.blockedWebsites.keySet()) {
                newContent.append(ipRoute).append(" ").append(app).append(System.lineSeparator());
            }

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

    private void loadBlockedWebsites()
    {
        JSONObject newBlockedWebsites = BlockedWebsitesAPI.getBlockedWebsites();
        System.out.println("Blocked websites: " + this.blockedWebsites);
        System.out.println("new: "+ newBlockedWebsites);

        if (newBlockedWebsites != null) {

            // modify hosts file only if the hosts were changed
            if (!newBlockedWebsites.equals(this.blockedWebsites)) {
                this.blockedWebsites = newBlockedWebsites;
                modifyHosts();
                System.out.println("modified");
            }
        }
    }

    public void run()
    {
        while (true){
            try {
                loadBlockedWebsites();
                sleep(UPDATE_RATE_SECONDS*1000);
            } catch (InterruptedException ignored) {}
        }
    }
}
