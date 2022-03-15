package com.company.local;

import com.company.API.BlockedAppsAPI;
import com.company.utils.Config;
import com.company.utils.Files;
import com.company.utils.ProcBuilder;
import kong.unirest.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Apps {
    private JSONObject blockedApps;

    public Apps()
    {
        this.blockedApps = loadBlockedApps();
        System.out.println("blocked: " + blockedApps);
        monitorApps();
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

        if (blockedApps != null) {
            Files.createFile(Config.properties.getProperty("DIRECTORY_NAME") + Config.properties.getProperty("BLOCKED_FILE_NAME"), blockedApps.toString());
        }else
        {
            File blockedAppsFile = new File(Config.properties.getProperty("DIRECTORY_NAME") + Config.properties.getProperty("BLOCKED_FILE_NAME"));
            Scanner reader;

            try {
                reader = new Scanner(blockedAppsFile);
                blockedApps = new JSONObject(reader.nextLine());
            } catch (FileNotFoundException ignored) {
            }
        }
        return blockedApps;
    }

    /**
     * this method gets current opened apps and reports to server about the new opened apps
     * if an app in the blocked apps, it's being closed
     */
    public void monitorApps() {
        List<String> opened= getOpenedApps();
        List<String> current, newApps;
        do {
            current = getOpenedApps();

            newApps =  new ArrayList<>(current);
            newApps.removeAll(opened);

            System.out.println("new opened apps: " + newApps);
            newApps.forEach(
                    app -> {
                        if (blockedApps.has(app)){
                            blockApp(app);
                        }
                    }
            );

            opened.removeAll(current);
            System.out.println("Closed apps: " + opened + "\n\n");
            opened = current;
        }while (true);
    }
}
