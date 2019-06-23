/************************************************************************
 * Copyright PointCheckout, Ltd.
 */
package com.pc.android.sdk;

import com.pc.android.sdk.internal.PointCheckoutInternalClient;

/**
 * The environment of {@link PointCheckoutInternalClient}
 *
 * @author pointcheckout
 */
public enum Environment {
    /**
     * Use for production
     */
    PRODUCTION(R.string.pointcheckout_base_url_production),
    /**
     * Use for testing
     */
    TEST(R.string.pointcheckout_base_url_test);

    private Environment(int stringIndex) {
        this.stringIndex = stringIndex;
    }

    private int stringIndex;

    /**
     * @return the string index of the environment
     */
    public int getStringIndex(){
        return stringIndex;
    }

}
