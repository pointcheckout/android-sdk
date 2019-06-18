package com.pc.android.sdk;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;

public class PointCheckoutClient {

    private Environment environment;
    private boolean autoDismiss;
    private AlertDialog modal;

    public PointCheckoutClient() throws PointCheckoutException {
        this(Environment.PRODUCTION, true);

    }

    public PointCheckoutClient(boolean autoDismiss) throws PointCheckoutException {
        this(Environment.PRODUCTION, autoDismiss);
    }

    public PointCheckoutClient(Environment environment, boolean autoDismiss) throws PointCheckoutException {
        PointCheckoutUtils.assertNotNull(environment);
        this.environment = environment;
        this.autoDismiss = autoDismiss;
    }

    private String getCheckoutUrl(String checkoutKey) {
        return String.format(environment.getUrl() + "/pay-mobile?checkoutKey=%s", checkoutKey);
    }

    public void pay(
            final Context context,
            final String checkoutKey,
            final PointCheckoutEventListener listener) throws PointCheckoutException {

        PointCheckoutUtils.assertNotNull(context);
        PointCheckoutUtils.assertNotNull(checkoutKey);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        WebView webView = new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(getCheckoutUrl(checkoutKey));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());

                if (!request.getUrl().toString().startsWith(environment.getUrl()) ||
                        request.getUrl().toString().startsWith(environment.getUrl() + "/cancel/") ||
                        request.getUrl().toString().startsWith(environment.getUrl() + "/complete")) {
                    requestDismiss(listener);
                }

                return true;
            }
        });

        alert.setView(webView);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                requestDismiss(listener);
            }
        });
        modal = alert.show();
    }


    private void requestDismiss(PointCheckoutEventListener listener) {
        if (autoDismiss)
            dismiss();

        if (Objects.nonNull(listener))
            listener.onDismiss();


    }

    public void dismiss() {
        CookieManager.getInstance().removeAllCookies(null);
        if (modal.isShowing())
            modal.dismiss();
    }
}
