package com.company.utils;

import com.company.API.ChildAPI;
import kong.unirest.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class Child {
    public final int id;
    public final String mac_address;
    public  String nickname;
    public  boolean blocked;
    public  JSONObject usage_limits;
    public float timeSpent;

    public Child(JSONObject childData)
    {   if (childData != null) {
        id = childData.getInt("id");
        mac_address = childData.getString("mac_address");
        nickname = childData.getString("nickname");
        blocked = childData.getBoolean("blocked");
        usage_limits = childData.getJSONObject("usage_limits");
        timeSpent = childData.getFloat("time_spent");
        }else{
            id = -1;
            mac_address = null;
            nickname = null;
            blocked = false;
            usage_limits = null;
        }
    }

    public void refresh(){
        JSONObject childData = ChildAPI.getChild();
        nickname = childData.getString("nickname");
        blocked = childData.getBoolean("blocked");
        usage_limits = childData.getJSONObject("usage_limits");
        timeSpent = childData.getFloat("time_spent");
    }

    public static String getCurrentDay()
    {
        String[] days = new String[]{"sunday","monday","tuesday","wednesday","thursday","friday","saturday"};
        Date date=new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return days[c.get(Calendar.DAY_OF_WEEK)-1];
    }

    public float getCurrentLimit()
    {
        String today = getCurrentDay();
        return this.usage_limits.getFloat(today);
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
