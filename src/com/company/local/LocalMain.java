package com.company.local;


import com.company.API.ApiAuth;
import com.company.API.ChildAPI;
import com.company.local.timeLimit.MonitorTime;
import com.company.ui.TimeLimitWindow;
import com.company.utils.Child;


/**
 * This class is called after the client was logged
 * in and started running
 */
public class LocalMain {
    private static final Child child = new Child(ChildAPI.getChild());

    public static void run() {
        System.out.println("Loading data...");

        // check internet connectivity and correct running machine
        if (child.mac_address == null){
            System.out.println("Error: No Internet");
        }else
            if (!child.mac_address.replace(":", "*").equals(ApiAuth.mac_address)){
                System.out.println("Error: couldn't authenticate with the server." +
                        "Can be caused by a broken or stolen child token.");
            }else {

                Websites websites = new Websites();
                Apps apps = new Apps();
                MonitorTime monitorTime = new MonitorTime(child);

                System.out.println("Starting all threads...");

                //apps.start();
                //websites.start();
                monitorTime.start();
            }

    }
}
