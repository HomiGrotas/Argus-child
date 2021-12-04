package com.company.timeMonitor;

import com.company.ProcBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LockComputer {
    public static void lock() throws IOException {
        killExplorer();
        //ToDo: thread to close task manager
    }

    /**
     *  kill explorer.exe (windows graphics)
     */
    private static void killExplorer() throws IOException {
        List<String> commands = new ArrayList<>();
        commands.add("cmd.exe");
        commands.add("/c");
        commands.add("taskkill");
        commands.add("/IM");
        commands.add("explorer.exe");
        commands.add("/F");
        ProcBuilder.runProc(commands);
    }
}
