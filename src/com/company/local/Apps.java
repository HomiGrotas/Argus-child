package com.company.local;

import com.company.API.AppsHistoryAPI;
import com.company.API.BlockedAppsAPI;
import com.company.utils.Config;
import com.company.utils.Files;
import com.company.utils.ProcBuilder;
import com.company.utils.States;
import kong.unirest.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Apps extends Thread{
    private JSONObject blockedApps;
    private final long UPDATE_RATE_SECONDS = 1;


    public Apps()
    {
        this.blockedApps = loadBlockedApps();
        System.out.println("Blocked apps: " + blockedApps);
    }

    private List<String> getOpenedApps()
    {
        List<String> apps = null;

        List<String> psL = new ArrayList<>();
        psL.add("powershell");
        psL.add("\"");
        psL.add("gps | where {$_.MainWindowTitle } | select ProcessName");

        try {
            apps = ProcBuilder.runProc(psL);
            apps = apps.subList(2, apps.size());
            apps.replaceAll(String::trim);
        }catch (IOException ignored)
        {
            System.out.println("Couldn't get opened apps");
        }
        return apps;
    }

    private void blockApp(String app) {
        System.out.println("trying to block " + app);
        List<String> killApp = new ArrayList<>();
        killApp.add("cmd.exe");
        killApp.add("/c");
        killApp.add("taskkill");
        killApp.add("/IM");
        killApp.add(app + "*");
        killApp.add("/f");
        try {
            ProcBuilder.runProc(killApp);
        } catch (IOException ignored) {}
    }

    private JSONObject loadBlockedApps()
    {
        JSONObject blockedApps = BlockedAppsAPI.getUnAllowedApps();
        String filePath = Config.properties.getProperty("DIRECTORY_NAME") + Config.properties.getProperty("BLOCKED_FILE_NAME");

        // create blocked apps if got from server, else-> try to read from the file
        if (blockedApps != null) {
            Files.createFile(filePath, blockedApps.toString());
        }else
        {
            File blockedAppsFile = new File(filePath);
            Scanner reader;

            try {
                reader = new Scanner(blockedAppsFile);
                blockedApps = new JSONObject(reader.nextLine());
            } catch (FileNotFoundException ignored) {
            }
        }
        return blockedApps;
    }


    private void updateBlockedApps()
    {
        JSONObject newBlockedApps = BlockedAppsAPI.getUnAllowedApps();
        if (newBlockedApps != null && !newBlockedApps.equals(this.blockedApps))
        {
            this.blockedApps = newBlockedApps;
            System.out.println("new blocked apps:" + newBlockedApps);

            // making sure new blocked app are closed
            for (String blockedApp: this.blockedApps.keySet()) {
                blockApp(blockedApp);
            }
        }
    }

    /**
     * this method gets current opened apps and reports to server about the new opened apps
     * if an app in the blocked apps, it's being closed
     */
    public void run() {
        List<String> opened= getOpenedApps();
        List<String> current, newApps;

        // block opened app if it's in the blocked list
        opened.forEach(app -> {
            if (blockedApps.has(app)){
                blockApp(app);
            }
        });

        do {
            current = getOpenedApps();

            newApps =  new ArrayList<>(current);
            newApps.removeAll(opened);

            System.out.println("new opened apps: " + newApps);

            // for every new app, report to server and specify if it was blocked
            newApps.forEach(

                    app -> {
                        boolean blocked;
                        if (blockedApps.has(app)){
                            blockApp(app);
                            blocked = true;
                        }else{
                            blocked = false;
                        }
                        AppsHistoryAPI.postNewStateApps(app, States.OPENED, blocked);
                    }
            );

            opened.removeAll(current);
            System.out.println("Closed apps: " + opened + "\n\n");

            // for every closed app, report to server
            opened.forEach(
                    app ->
                        AppsHistoryAPI.postNewStateApps(app, States.CLOSED, false)
            );

            opened = current;

            updateBlockedApps();
            try {
                sleep(UPDATE_RATE_SECONDS*1000);
            } catch (InterruptedException ignored) {}
        }while (true);
    }
}
