package com.company.API;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import unirest.shaded.com.google.gson.JsonObject;

public class WebHistoryAPI implements API{
    public static final String endpoint = "/child/web_history";

    public static void reportWebsiteHistory(String url, String title, String date) {

        JsonObject bodyParams = new JsonObject();
        bodyParams.addProperty("url", url);
        bodyParams.addProperty("title", title);
        bodyParams.addProperty("date", date);

        try {
            HttpResponse<JsonNode> response = Unirest.post(
                            base_url + endpoint)
                    .basicAuth(ApiAuth.mac_address, ApiAuth.static_token)
                    .header("Content-Type", "application/json")
                    .body(bodyParams)
                    .asJson();

            System.out.println(response.getStatusText());
        }
        catch (kong.unirest.UnirestException ignored) {}
    }
}
