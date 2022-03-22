package com.company.local;

import com.company.API.ApiAuth;
import com.company.API.ChildAPI;
import com.company.utils.Config;
import com.company.utils.Files;
import com.company.utils.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Auth {
    private static final String authPath = Config.properties.getProperty("DIRECTORY_NAME") + Config.properties.getProperty("AUTH_FILE_NAME");

    public static boolean authExists()
    {
        File authFile = new File(authPath);
        return authFile.exists();
    }

    public static Pair<Boolean, String> register(String nickname, String token)
    {
        Pair<Boolean, String> success_and_msg;
        success_and_msg =  ChildAPI.createChild(Auth.getMacAddress(), nickname, token);
        success_and_msg.a = success_and_msg.a && Auth.createAuthFile();
        return success_and_msg;
    }

    public static boolean login()
    {
        ApiAuth.mac_address = Auth.getMacAddress();
        ApiAuth.static_token = Auth.loadAuth();
        return !(ApiAuth.static_token == null);
    }


    private static boolean createAuthFile(){
        if (ApiAuth.mac_address == null || ApiAuth.static_token == null) {
            return false;
        }
        return Files.createFile(authPath, ApiAuth.static_token);
    }

    private static String loadAuth() {
        String token = null;
        File authFile = new File(authPath);
        try {
            Scanner scanner = new Scanner(authFile);
            token = scanner.nextLine();
        } catch (FileNotFoundException | NoSuchElementException ignored) {
        }
        return token;
    }

    private static String getMacAddress()
    {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (byte b : mac) {
                sb.append(String.format("%02X%s", b, '*'));
            }
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();

        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        return "";
    }
}
