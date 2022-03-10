package com.company.local.AppsHistory;

import com.company.utils.ProcBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Apps {
    private static List<String> getOpenedApps()
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

    public static void monitorApps() {
        List<String> opened= getOpenedApps();
        List<String> current, newApps;
        do {
            current = getOpenedApps();

            newApps =  new ArrayList<>(current);
            newApps.removeAll(opened);

            System.out.println("new opened apps: " + newApps);
            opened.removeAll(current);
            System.out.println("Closed apps: " + opened + "\n\n");
            opened = current;
        }while (true);
    }
}
