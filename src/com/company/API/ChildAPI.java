package com.company.API;

import com.company.utils.Pair;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import unirest.shaded.com.google.gson.JsonObject;

public class ChildAPI implements API{
    public static final String endpoint = "/child";


    public static JSONObject getChild()
    {
        JSONObject childData = null;
        HttpResponse<JsonNode> response = null;

        try {
            response = Unirest.get(
                    base_url + endpoint)
                    .basicAuth(ApiAuth.mac_address, ApiAuth.static_token)
                    .asJson();
        }catch (kong.unirest.UnirestException ignored){}

        if (response != null){
            JsonNode body = response.getBody();
            if (body != null) {
                childData =  body.getObject();
            }
        }

        return childData;
    }

    public static Pair<Boolean, String> createChild(String mac_address, String nickname, String registration_token)
    {
        boolean created;
        String message;
        JSONObject json;

        JsonObject bodyParams = new JsonObject();
        bodyParams.addProperty("mac_address", mac_address.replaceAll("\\*", ":"));
        bodyParams.addProperty("nickname", nickname);
        bodyParams.addProperty("parent_token", registration_token);

        try {
            HttpResponse<JsonNode> response = Unirest.post(
                            base_url + endpoint)
                    .header("Content-Type", "application/json")
                    .body(bodyParams)
                    .asJson();

            json = response.getBody().getObject();
        }catch (kong.unirest.UnirestException e)
        {
            json = new JSONObject();
            json.put("message", "couldn't connect to server");
        }

        // return (boolean success + String msg)
        if (json.has("token")){
            ApiAuth.static_token = json.getString("token");
            ApiAuth.mac_address = mac_address;
            created = true;
            message = "success";
        }
        else{
            created = false;
            if (json.has("message"))
            {
                System.out.println(json);
                message = json.getString("message");
            }
            else{
                message = "Unknown error";
            }
        }
        return new Pair<>(created, message);
    }


    public static void main(String[] args) {
    }
}
