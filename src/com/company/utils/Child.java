package com.company.utils;

import kong.unirest.json.JSONObject;

public class Child {
    public final int id;
    public final String mac_address;
    public final String nickname;
    public final boolean blocked;
    public final JSONObject usage_limits;

    public Child(JSONObject childData)
    {   if (childData != null) {
        id = childData.getInt("id");
        mac_address = childData.getString("mac_address");
        nickname = childData.getString("nickname");
        blocked = childData.getBoolean("blocked");
        usage_limits = childData.getJSONObject("usage_limits");
        }else{
            id = -1;
            mac_address = null;
            nickname = null;
            blocked = false;
            usage_limits = null;
        }
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", mac_address='" + mac_address + '\'' +
                ", nickname='" + nickname + '\'' +
                ", blocked=" + blocked +
                ", usage_limits=" + usage_limits +
                '}';
    }
}
