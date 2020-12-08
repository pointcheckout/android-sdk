/************************************************************************
 * Copyright PointCheckout, Ltd.
 */
package com.pc.android.sdk;

import com.pc.android.sdk.internal.PointCheckoutInternalClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The environment of {@link PointCheckoutInternalClient}
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
    TEST("https://pay.test.pointcheckout.com"),
    /**
     * Use for debugging
     */
    DEBUG("https://pay.staging.pointcheckout.com");

    private Environment(String url) {
        this.url = url;
    }

    private String url;

    /**
     * @return the string index of the environment
     */
    public String getUrl() {
        return url;
    }

    public static Environment getEnviornment(String redirectUrl) {

        try {
            URI uri = new URI(redirectUrl);
            String domain = uri.getHost();

            if (domain.equals(Environment.PRODUCTION.url))
                return Environment.PRODUCTION;

            if (domain.equals(Environment.TEST.url))
                return Environment.TEST;

            return Environment.DEBUG;

        } catch (URISyntaxException e) {
            return null;
        }


    }

}
