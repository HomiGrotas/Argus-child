package com.company.utils;


import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import static java.util.prefs.Preferences.systemRoot;

public class Privileges
{
    public static final boolean IS_RUNNING_AS_ADMINISTRATOR;

    static
    {
        IS_RUNNING_AS_ADMINISTRATOR = isRunningAsAdministrator();
    }

    /**
     * this method helps the program to determine if
     * it has admin privilege
     * @return boolean (isAdmin)
     */
    private static boolean isRunningAsAdministrator()
    {
        Logger.getLogger("java.util.prefs").setLevel(Level.OFF);  // turn down java preference logger
        Preferences preferences = systemRoot();

        try
        {
            preferences.put("argus", "test"); // SecurityException on Windows
            preferences.remove("argus");
            preferences.flush(); // BackingStoreException on Linux
            return true;
        } catch (Exception exception)
        {
            return false;
        }
    }
}