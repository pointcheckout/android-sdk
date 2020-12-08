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
    public PointCheckoutClient() throws PointCheckoutException {
        client = new PointCheckoutInternalClient(true);

    }

    /**
     * @param autoDismiss auto close the modal on payment success or failure
     * @throws PointCheckoutException if the environment is null
     */
    public PointCheckoutClient(boolean autoDismiss) throws PointCheckoutException {
        client = new PointCheckoutInternalClient(autoDismiss);
    }

    public void initialize(Context context) {
        client.initialize(context);
    }

    public void pay(
            final Context context,
            final String redirectUrl,
            final PointCheckoutEventListener listener) throws PointCheckoutException {
        validate(redirectUrl);
        client.pay(context, redirectUrl, null, listener);
    }

    public void pay(
            final Context context,
            final String redirectUrl,
            final String resultUrl,
            final PointCheckoutEventListener listener) throws PointCheckoutException {
        validate(redirectUrl);
        client.pay(context, redirectUrl, resultUrl, listener);
    }

    /**
     * Dismisses the modal
     *
     * @throws PointCheckoutException if the modal is already dismissed
     */
    public void dismiss() throws PointCheckoutException {
        client.dismiss();
    }

    private void validate(String redirectUrl) throws PointCheckoutException {
        if (Environment.getEnviornment(redirectUrl) == null)
            throw new PointCheckoutException("THe provided payment url is invalid");

    }
}

