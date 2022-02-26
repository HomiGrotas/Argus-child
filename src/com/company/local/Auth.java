package com.company.local;

import com.company.API.ApiAuth;
import com.company.API.ChildAPI;
import com.company.utils.Config;
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
import java.util.Objects;
import java.util.Scanner;


public class Auth {

    public static boolean authExists()
    {
        String filename = Config.properties.getProperty("AUTH_FILE_NAME");
        File authFile = new File(filename);
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

        boolean created = false;
        String filename = Config.properties.getProperty("AUTH_FILE_NAME");
        File authFile = new File(filename);
        FileWriter fileWriter;

        try {
            created =  authFile.createNewFile();
            if (created)
            {
                fileWriter = new FileWriter(filename);
                fileWriter.write(ApiAuth.static_token);
                fileWriter.close();
            }

        }catch (IOException ignored) {
        }
        return created;
    }

    private static String loadAuth() {
        String token = null;
        String filename = Config.properties.getProperty("AUTH_FILE_NAME");
        File authFile = new File(filename);
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
                sb.append(String.format("%02X%s", b, ':'));
            }
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();

        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
