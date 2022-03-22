package com.company.API;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class BlockedWebsitesAPI implements API {
    private static final String endpoint = "/blocked_websites";

    public static JSONObject getBlockedWebsites()
    {
        JSONObject blockedWebsites = null;
        HttpResponse<JsonNode> response = null;

        try
        {
            response = Unirest.get(base_url + endpoint)
                    .basicAuth(ApiAuth.mac_address, ApiAuth.static_token)
                    .asJson();
        }catch (kong.unirest.UnirestException ignored){}

        if (response != null){
            JsonNode body = response.getBody();
            if (body != null) {
                blockedWebsites =  body.getObject();
            }
        }

        return blockedWebsites;
    }
}
