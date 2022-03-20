package com.company.local;

import com.company.API.*;
import com.company.utils.Child;
import com.company.utils.States;

/**
 * This class is called after the client was logged
 * in successfully and started running
 */
public class LocalMain {
    public static void run()
    {
        System.out.println("Loading data...");
        //new Apps().monitorApps();
        AliveAPI.postActive();
        System.out.println("Started running...");
    }
}
