package com.company.API;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;


import com.company.Config;


public class Auth {
    public static String token = null;
    public static String mac_address = null;
    public static String static_token = null;
    public static final String endpoint = "/token";

    /**
     * Note that mac_address should be separated with '*' instead of ':'
     */
    public static void updateToken()
    {
        HttpResponse<String> response = Unirest.get(
                Config.properties.getProperty("BASE_URL") + endpoint)
                .basicAuth(mac_address, static_token)
                .asString();
        token = response.getBody();
        System.out.println(token);
    }

    public static void main(String[] args) {
        Auth.mac_address = "10*10*5e*00*23*ad";
        Auth.static_token = "dVN732hnJRSQR1t86yNyKMkqmdg4IdyP8gus8UnMvjqVntV5luXra5GtMjtAbamrsEgq1iAzqbzb3OqsVnKBiw";
        Auth.updateToken();
    }
}
