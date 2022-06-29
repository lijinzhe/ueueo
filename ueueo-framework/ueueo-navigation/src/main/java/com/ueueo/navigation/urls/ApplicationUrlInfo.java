package com.ueueo.navigation.urls;

import java.util.HashMap;
import java.util.Map;

public class ApplicationUrlInfo {
    private String rootUrl;

    private Map<String, String> urls;

    public ApplicationUrlInfo() {
        urls = new HashMap<>();
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public Map<String, String> getUrls() {
        return urls;
    }
}
