package com.company.API.Child;

import com.company.API.Auth;
import com.company.Config;
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
        JsonObject bodyParams = new JsonObject();
        bodyParams.addProperty("mac_address", mac_address);
        bodyParams.addProperty("nickname", nickname);
        bodyParams.addProperty("access_token", registration_token);

        HttpResponse<JsonNode> response = Unirest.post(
                Config.properties.getProperty("BASE_URL") + endpoint)
                .header("Content-Type", "application/json")
                .body(bodyParams)
                .asJson();

        JSONObject json = response.getBody().getObject();

        // return boolean success + msg
        if (json.has("token")){
            Auth.static_token = json.getString("token");
            Auth.mac_address = mac_address;
            return new Pair<>(true, "success");
        }
        else if (json.has("message"))
        {
            return new Pair<>(false, json.getString("message"));
        }
        return new Pair<>(false, "unknown error");
    }


    public static JsonNode getChildInfo()
    {
        HttpResponse<JsonNode> response = Unirest.get(
                        Config.properties.getProperty("BASE_URL") + endpoint)
                .basicAuth(Auth.mac_address, Auth.static_token)
                .asJson();

        return response.getBody();
    }


    public static void main(String[] args) {
        //System.out.println(createChild("10:10:5e:02:23:aa", "123",
        //"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTYzODY1MzM1MSwianRpIjoiMTYyYmNlODktMTJlMC00MWQ4LTk1ZWQtMTFhZTVmMzRkODZhIiwidHlwZSI6ImNoaWxkX3JlZ2lzdHJhdGlvbiIsInN1YiI6MSwibmJmIjoxNjM4NjUzMzUxLCJleHAiOjE2Mzg2NTQyNTF9.GpefJXMymu7cXEniMDqRYmTZRs31FMBpbIg1vw0Hmck"));
        //Auth.static_token = "Pncg0WCoe7KrqQumYUrylKE8Y4mQQn24G0ROHHehPWifp7jNmMpaL4iu7hcURCejMLAtObNehpXlv4wqN6EyKw";
        //Auth.mac_address = "10:10:5e:02:23:aa".replace(":", "*");
        //System.out.println(getChildInfo());
    }
}
