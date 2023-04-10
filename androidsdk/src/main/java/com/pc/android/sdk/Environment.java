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
    PRODUCTION("https://pay.paymennt.com", "https://pay.pointcheckout.com"),
    /**
     * Use for testing
     */
    TEST("https://pay.test.paymennt.com", "https://pay.test.pointcheckout.com"),
    /**
     * Use for debugging
     */
    DEBUG("https://pay.staging.paymennt.com", "https://pay.staging.pointcheckout.com");

    private Environment(String url, String pointCheckoutUrl) {
        this.url = url;
        this.pointCheckoutUrl = pointCheckoutUrl;
    }

    private String url;
    private String pointCheckoutUrl;

    /**
     * @return the string index of the environment
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the poincheckout url string index of the environment
     */
    public String getPointCheckoutUrl() {
        return pointCheckoutUrl;
    }

    public static Environment getEnviornment(String redirectUrl) {

        try {
            URI uri = new URI(redirectUrl);
            String domain = uri.getHost();

            if (domain.equals(Environment.PRODUCTION.url) || domain.equals(Environment.PRODUCTION.pointCheckoutUrl))
                return Environment.PRODUCTION;

            if (domain.equals(Environment.TEST.url) || domain.equals(Environment.TEST.pointCheckoutUrl))
                return Environment.TEST;

            return Environment.DEBUG;

        } catch (URISyntaxException e) {
            return null;
        }


    }

}
