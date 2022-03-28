package com.company.local.timeLimit;

import com.company.utils.ProcBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LockComputer {
    public static void lock() {
        killExplorer();
        //ToDo: thread to close task manager
    }

    public static void unlock()
    {
        startExplorer();
    }

    /**
     *  kill explorer.exe (windows graphics)
     */
    private static void killExplorer(){
        List<String> commands = new ArrayList<>();
        commands.add("cmd.exe");
        commands.add("/c");
        commands.add("taskkill");
        commands.add("/IM");
        commands.add("explorer.exe");
        commands.add("/F");
        try {
            ProcBuilder.runProc(commands);
        } catch (IOException ignored) {}
    }

    private static void startExplorer() {
        List<String> commands = new ArrayList<>();
        commands.add("cmd.exe");
        commands.add("/c");
        commands.add("explorer.exe");
        try {
            ProcBuilder.runProc(commands);
        } catch (IOException ignored) {}
    }
}
