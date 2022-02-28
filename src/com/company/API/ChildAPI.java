package com.company.API;

import com.company.utils.Config;
import com.company.utils.Pair;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import unirest.shaded.com.google.gson.JsonObject;

public class ChildAPI {
    public static final String endpoint = "/child";

    public static Pair<Boolean, String> createChild(String mac_address, String nickname, String registration_token)
    {
        boolean created;
        String message;
        JSONObject json;

        JsonObject bodyParams = new JsonObject();
        bodyParams.addProperty("mac_address", mac_address);
        bodyParams.addProperty("nickname", nickname);
        bodyParams.addProperty("parent_token", registration_token);

        try {
            HttpResponse<JsonNode> response = Unirest.post(
                            Config.properties.getProperty("BASE_URL") + endpoint)
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
                message = json.getString("message");
            }
            else{
                message = "Unknown error";
            }
        }
        return new Pair<>(created, message);
    }


    public static void main(String[] args) {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6NTcsImV4cCI6MTY0NTEwNTcyOS4xMjE5ODA0fQ.JA8ZQR1zYgAnxJyF6V8P0nVIOANj7UVLqDMJopoxOJs";
        System.out.println(createChild("10:10:5e:02:23:aa", "12ksmksmx3",
        token));
    }
}
