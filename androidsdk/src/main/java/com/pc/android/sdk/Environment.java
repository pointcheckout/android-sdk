/************************************************************************
 * Copyright PointCheckout, Ltd.
 */
package com.pc.android.sdk;

/**
 * The environment of {@link PointCheckoutClient}
 *
 * @author pointcheckout
 */
public enum Environment {
    /**
     * Use for production
     */
    PRODUCTION("https://pay.pointcheckout.com"),
    /**
     * Use for testing
     */
    TEST("https://pay.staging.pointcheckout.com");

    private Environment(String url) {
        this.url = url;
    }

    private String url;

    /**
     * @return the url of the environment
     */
    public String getUrl(){
        return url;
    }

}
