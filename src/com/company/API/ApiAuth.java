package com.company.API;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import com.company.utils.Config;


public class ApiAuth {
    public static String token = null;
    public static String mac_address = null;
    public static String static_token = null;

    /**
     * Note that mac_address should be separated with '*' instead of ':'
     */
    public static void updateToken() {
        HttpResponse<String> response = Unirest.get(
                        Config.properties.getProperty("BASE_URL") + "/token")
                .basicAuth(mac_address, static_token)
                .asString();
        token = response.getBody();
        System.out.println(token);
    }
}
