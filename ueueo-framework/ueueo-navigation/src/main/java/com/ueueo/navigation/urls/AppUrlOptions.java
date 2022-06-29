package com.ueueo.navigation.urls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppUrlOptions {

    private Map<String, ApplicationUrlInfo> applications;

    private List<String> redirectAllowedUrls;

    public AppUrlOptions() {
        applications = new HashMap<>();
        redirectAllowedUrls = new ArrayList<>();
    }

    public Map<String, ApplicationUrlInfo> getApplications() {
        return applications;
    }

    public List<String> getRedirectAllowedUrls() {
        return redirectAllowedUrls;
    }
}
