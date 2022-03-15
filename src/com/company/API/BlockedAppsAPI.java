package com.company.API;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;


public class BlockedAppsAPI implements API{
    public static final String endpoint = "/blocked_apps";

    /**
     * get blocked apps
     * @return dict with all blocked apps
     */
    public static JSONObject getUnAllowedApps(){
        JSONObject appsList = null;
        HttpResponse<JsonNode> response = null;

        try {
            response = Unirest.get(
                    base_url + endpoint).basicAuth(ApiAuth.mac_address, ApiAuth.static_token).asJson();
        }catch (kong.unirest.UnirestException ignored){}

        if (response != null){
            JsonNode body = response.getBody();
            if (body != null) {
                appsList =  body.getObject();
            }
        }
        return appsList;
    }
}

