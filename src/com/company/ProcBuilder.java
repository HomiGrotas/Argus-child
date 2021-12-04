package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcBuilder {

    /**
     * run windows process with arguments
     * @param commands proc name + its arguments
     * @throws IOException couldn't read the output
     */
    public static List<String> runProc(List<String> commands) throws IOException {
        List<String> output = new ArrayList<>();
        ProcessBuilder pb = new ProcessBuilder(commands);
        Process process = pb.start();

        // for reading the output from stream
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String s = stdInput.readLine();

        while (s != null) {
            if (!s.trim().isEmpty()) {
                output.add(s);
            }
            s = stdInput.readLine();
        }
        return output;
    }
}
