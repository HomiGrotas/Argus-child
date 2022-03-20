package com.company.API;

import com.company.utils.States;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;


public class AppsHistoryAPI implements API {
    public static final String endpoint = "/app_history";


    /**
     * posts new opened/ closed app (state change)
     * @param app new opened/ closed apps
     */
    public static void postNewStateApps(String app, Enum<States> state, boolean blocked)
    {
        HttpResponse<JsonNode> response = null;

        JSONObject reqJson = new JSONObject();
        reqJson.put("app", app);
        reqJson.put("state", state);
        reqJson.put("blocked", blocked);

        try {
            Unirest.post(base_url + endpoint)
                    .basicAuth(ApiAuth.mac_address, ApiAuth.static_token)
                    .header("Content-Type", "application/json")
                    .body(reqJson)
                    .asJson();
        }catch (kong.unirest.UnirestException ignored){}
    }
}
