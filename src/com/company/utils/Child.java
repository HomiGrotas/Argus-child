package com.company.utils;

import kong.unirest.json.JSONObject;

public class Child {
    public final int id;
    public final String mac_address;
    public final String nickname;
    public final boolean blocked;
    public final JSONObject usage_limits;

    public Child(JSONObject childData)
    {
        id = childData.getInt("id");
        mac_address = childData.getString("mac_address");
        nickname = childData.getString("nickname");
        blocked = childData.getBoolean("blocked");
        usage_limits = childData.getJSONObject("usage_limits");

    }
}
