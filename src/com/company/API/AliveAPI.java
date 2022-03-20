package com.company.API;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class AliveAPI implements API {
    public static final String endpoint = "/child/activity";

    public static void postActive()
    {
        HttpResponse<JsonNode> response = null;

        try {
             Unirest.post(
                    base_url + endpoint)
                    .basicAuth(ApiAuth.mac_address, ApiAuth.static_token)
                    .asJson();
        }catch (kong.unirest.UnirestException ignored){}
    }
}
