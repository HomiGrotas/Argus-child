package com.company.API;

import kong.unirest.json.JSONObject;

public class AppsHistoryAPI {

    /**
     * posts new opened/ closed app (state change)
     * @param app new opened/ closed apps
     */
    static void postNewStateApps(String app)
    {
        JSONObject output = new JSONObject();
        output.put("app", app);
    }
}
