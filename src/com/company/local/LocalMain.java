package com.company.local;

/**
 * This class is called after the client was logged
 * in successfully and started running
 */
public class LocalMain {
    public static void run()
    {
        System.out.println("Loading data...");
        new Apps().monitorApps();
        System.out.println("Started running...");
    }
}
