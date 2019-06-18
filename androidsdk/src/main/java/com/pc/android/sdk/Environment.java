package com.pc.android.sdk;

public enum Environment {

    PRODUCTION("https://pay.pointcheckout.com"),
    TEST("https://pay.staging.pointcheckout.com");

    private Environment(String url) {
        this.url = url;
    }

    private String url;

    public String getUrl(){
        return url;
    }

}
