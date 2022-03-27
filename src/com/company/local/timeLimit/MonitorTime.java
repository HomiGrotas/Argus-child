package com.company.local.timeLimit;

import com.company.ui.TimeLimitWindow;
import com.company.utils.Child;
import com.company.utils.Config;
import com.company.utils.Files;
import kong.unirest.json.JSONObject;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class MonitorTime extends Thread{
    private final Child child;
    private JSONObject limits;
    private final String filePathLimits;
    private final String getFilePathPassed;

    public MonitorTime(@NotNull Child child){
        this.child = child;
        this.limits = child.usage_limits;
        this.filePathLimits = Config.properties.getProperty("LIMIT_FILE_NAME");
        this.getFilePathPassed = Config.properties.getProperty("SPENT_FILE_NAME");
        Files.createFile(filePathLimits, limits.toString());
        System.out.println(limits);
    }

    public static String getCurrentDay()
    {
        String[] days = new String[]{"sunday","monday","tuesday","wednesday","thursday","friday","saturday"};
        Date date=new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return days[c.get(Calendar.DAY_OF_WEEK)-1];
    }


    @Override
    public void run()
    {
        String today = getCurrentDay();
        float todayHoursLimit = this.limits.getFloat(today);
        System.out.println("today time limit: "+todayHoursLimit);
        try {
            sleep((long) (todayHoursLimit* 60 * 1000)); // hours * 60 sec per hour * 1000 milliseconds
            System.out.println("reached time limit");
            TimeLimitWindow.create();
        } catch (InterruptedException ignored) {}
    }
}
