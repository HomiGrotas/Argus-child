package com.company.local.timeLimit;

import com.company.API.ChildAPI;
import com.company.ui.TimeLimitWindow;
import com.company.utils.Child;
import com.company.utils.Config;
import com.company.utils.Files;
import kong.unirest.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class MonitorTime extends Thread{
    private  Child child;
    private float newLimit;

    public MonitorTime(Child child){
        this.child = child;
        this.newLimit = -1;
        System.out.println(child.usage_limits);
    }

    public MonitorTime(Child child, float time)
    {
        this.child = child;
        this.newLimit = time;
    }



    @Override
    public void run()
    {
        float todayHoursLimit;

        if (this.newLimit == -1) {
            todayHoursLimit = child.getCurrentLimit();
        }else{
            todayHoursLimit = this.newLimit;
        }

        System.out.println("today time limit: "+todayHoursLimit);
        try {
            sleep((long) (todayHoursLimit* 3600  * 1000)); // hours * 3600 sec per hour * 1000 milliseconds
            System.out.println("reached time limit");
            TimeLimitWindow.create(this.child);
            this.interrupt();
        } catch (InterruptedException ignored) {}
    }
}
