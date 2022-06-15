package com.company.local.WEB;

import com.company.API.WebHistoryAPI;
import com.company.utils.Config;
import com.company.utils.Files;
import com.company.utils.ProcBuilder;
import org.sqlite.SQLiteException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;

public class Chrome {
    private static final String historyPath = Config.properties.getProperty("DIRECTORY_NAME") +
            Config.properties.getProperty("HISTORY_INDEX_FILE_NAME");
    public static final String chromeDbPath = "C:\\Users\\"+ System.getProperty("user.name")+"\\\\AppData\\\\Local\\\\Google\\\\Chrome\\\\User Data\\\\Default\\\\History";

    private final Connection conn;

    public Chrome()
    {
        this.conn = connect();

    }

    private Connection connect(){

        try {
            // db parameters
            String url = "jdbc:sqlite:" + Config.properties.getProperty("DIRECTORY_NAME")+Config.properties.getProperty("CHROME_COPIED_DB");

            // create a connection to the database
            return DriverManager.getConnection(url);

        } catch (SQLException ignored) {}
        return null;
    }

    private BigInteger loadLastTime()
    {
        BigInteger last_time = new BigInteger("-1");

        // create file if it doesn't exist
        if (!Files.exists(historyPath))
        {
            Files.createFile(historyPath, "-1");
            last_time = BigInteger.ZERO;
        }
        else{
            File history = new File(historyPath);
            try {
                Scanner reader = new Scanner(history);
                if (reader.hasNextLine()){
                    last_time = new BigInteger(reader.nextLine());
                }else{
                    last_time = new BigInteger("-1");
                }

            } catch (FileNotFoundException ignored) {
            }
        }

        return last_time;
    }

    public Set<WebRecord> reportHistory(BigInteger last_time)
    {
        Set<WebRecord> records = new HashSet<>();


        Statement stmt;

        try {
            // load the last 100 web activities
            stmt = this.conn.createStatement();
            String sql = "SELECT datetime(last_visit_time / 1000000 + (strftime('%s', '1601-01-01')), 'unixepoch', 'localtime') AS date, url, title FROM urls WHERE last_visit_time > "+ last_time.toString() +" ORDER BY last_visit_time DESC LIMIT 100";
            ResultSet rst = stmt.executeQuery(sql);


            while (rst.next()){
                WebHistoryAPI.reportWebsiteHistory(rst.getString("url"), rst.getString("title"), rst.getString("date"));
                System.out.println("Reported website- "+ rst.getString("url"));
            }

            // update the indexer file
            sql = "SELECT MAX(last_visit_time) AS lt FROM urls;";
            rst = stmt.executeQuery(sql);
            FileWriter history = new FileWriter(historyPath);
            history.write(rst.getString("lt"));
            history.close();
            stmt.close();

        }catch (SQLiteException e)
        {
            System.out.println("db is locked");
        }
        catch (SQLException e) {
            System.out.println("sql error");
        } catch (IOException e) {
            System.out.println("io error");
        }
        return records;
    }


    public static void changeIncognitoMode(int block) throws IOException {
        List<String> command = new ArrayList<>();
        command.add("cmd.exe");
        command.add("/c");
        command.add("REG ADD HKLM\\SOFTWARE\\Policies\\Google\\Chrome /v IncognitoModeAvailability /t REG_DWORD /d " + block + " /f");
        ProcBuilder.runProc(command);
    }

    public void run()
    {
        Chrome chrome = new Chrome();
        BigInteger last = chrome.loadLastTime();
        chrome.reportHistory(last);
    }
}
