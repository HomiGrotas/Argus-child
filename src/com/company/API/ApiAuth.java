package com.company.API;

import com.company.utils.Config;

public class ApiAuth {
    public static String mac_address = null;
    public static String static_token = null;
}

interface API
{
    String base_url = Config.properties.getProperty("BASE_URL");
}