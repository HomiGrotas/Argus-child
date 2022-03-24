package com.company.local.timeLimit;

import com.company.utils.Child;
import com.company.utils.Config;
import com.company.utils.Files;
import kong.unirest.json.JSONObject;

import java.io.File;

public class MonitorTime extends Thread{
    private final Child child;
    private JSONObject limits;
    private final String filePathLimits;
    private final String getFilePathPassed;

    public MonitorTime(Child child){
        this.child = child;
        this.limits = child.usage_limits;
        this.filePathLimits = Config.properties.getProperty("LIMIT_FILE_NAME");
        this.getFilePathPassed = Config.properties.getProperty("SPENT_FILE_NAME");
        Files.createFile(filePathLimits, limits.toString());
        System.out.println(limits);
    }

    // todo: get limit for current day
    //  ->  get time spent from file
    //  -> get current time running
    //  -> check if current_time_running + time_spent >= limit
}
