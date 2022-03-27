package com.company.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    public static Properties properties = createProps("/com/company/config.properties");


    private static Properties createProps(String name)
    {
        Properties prop = new Properties();
        InputStream in = null;
        try
        {
            in = Config.class.getResourceAsStream(name);
            prop.load(in);
        }
        catch (IOException ex)
        {
            System.err.println("failed to load \"" + name + "\": " + ex);
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException ex)
            {
                System.err.println("failed to close InputStream for \"" + name);
            }
        }
        return prop;
    }
}
