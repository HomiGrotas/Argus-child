package com.company.local.aliveAndCMD;

import com.company.API.AliveAPI;

public class Alive extends Thread{

    @Override
    public void run() {
        while (true){
            AliveAPI.postActive();
            try {
                sleep((5* 60 * 1000)); // 5 min * 60 sec per min * 1000 milliseconds = 5 minutes
                System.out.println("reported");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
