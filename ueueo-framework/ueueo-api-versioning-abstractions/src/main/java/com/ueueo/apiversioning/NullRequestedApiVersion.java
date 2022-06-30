package com.ueueo.apiversioning;

public class NullRequestedApiVersion implements IRequestedApiVersion {

    public static NullRequestedApiVersion Instance = new NullRequestedApiVersion();

    private NullRequestedApiVersion() {

    }

    @Override
    public String getCurrent() {
        return null;
    }
}
