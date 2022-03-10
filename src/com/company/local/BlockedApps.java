package com.company.local;

import com.company.API.BlockedAppsAPI;
import com.company.utils.Config;
import com.company.utils.Files;
import kong.unirest.json.JSONObject;

public class BlockedApps {
    public static boolean loadBlockedApps()
    {
        JSONObject blockedApps = BlockedAppsAPI.getUnAllowedApps();
        return Files.createFile(Config.properties.getProperty("DIRECTORY_NAME") + Config.properties.getProperty("BLOCKED_FILE_NAME"), blockedApps.toString());
    }
}
