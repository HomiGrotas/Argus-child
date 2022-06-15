package com.company.local.WEB;

import java.net.MalformedURLException;
import java.net.URL;

public class WebRecord {
    URL url;
    String title;
    String date;

    public WebRecord(String url, String title, String date) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            this.url = null;
        }
        this.title = title;
        this.date  = date;
    }

    @Override
    public String toString() {
        return "WebRecord{" +
                "url=" + url +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
