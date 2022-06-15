package com.company.local.timeLimit;

import com.company.ui.TimeLimitWindow;
import com.company.utils.Child;


public class MonitorTime extends Thread{
    private final Child child;
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

        System.out.println("today time limit: "+todayHoursLimit + ", child spent: " + this.child.timeSpent);
        try {
            while (todayHoursLimit > this.child.timeSpent)
            {
                sleep (5  * 1000); // 5 seconds * 1000 milliseconds
                this.child.refresh();
                todayHoursLimit = this.child.getCurrentLimit();
                System.out.println(this.child.timeSpent+ ", " +todayHoursLimit);
            }
            System.out.println("reached time limit");
            TimeLimitWindow.create(this.child);
            this.interrupt();
        } catch (InterruptedException ignored) {}
    }
}
