package com.pc.android.sdk;


import android.content.Context;

import com.pc.android.sdk.internal.PointCheckoutInternalClient;

/**
 * @author pointcheckout
 */
public class PointCheckoutClient {

    private PointCheckoutInternalClient client;

    /**
     * @throws PointCheckoutException if the environment is null
     */
    public PointCheckoutClient(Environment environment) throws PointCheckoutException {
        client = new PointCheckoutInternalClient(environment);

    }

    public void initialize(Context context) {
        client.initialize(context);
    }

    public void pay(
            final Context context,
            final String checkoutKey,
            final PointCheckoutEventListener listener) throws PointCheckoutException {
        client.pay(context, checkoutKey, listener);
    }


    /**
     * Dismisses the modal
     *
     * @throws PointCheckoutException if the modal is already dismissed
     */
    public void dismiss() throws PointCheckoutException {
        client.dismiss();
    }

}

