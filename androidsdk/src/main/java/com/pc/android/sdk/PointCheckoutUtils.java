/************************************************************************
 * Copyright PointCheckout, Ltd.
 */
package com.pc.android.sdk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.JsonReader;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.safetynet.SafetyNetClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author pointcheckout
 */
public class PointCheckoutUtils {

    private static final String API_KEY = "AIzaSyDcQMrv-SM5vvPBiaSrmrFlEVo2HPFmh3I";
    private static final String msgNotSecure = "PointCheckout payment can not run on this device due to security reasons.";


    public static void assertNotNull(Object obj) throws PointCheckoutException {
        if (Objects.isNull(obj))
            throw new PointCheckoutException(String.format("%s can not be null", obj.getClass().getSimpleName()));
    }

    public static void evaluateSafetyNetAsync(Context context, final PointCheckoutSafetyNetListener listener) {

        SafetyNet.getClient(context).attest(generateNonce(), API_KEY)
                .addOnSuccessListener(new OnSuccessListener<SafetyNetApi.AttestationResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.AttestationResponse attestationResponse) {
                        listener.callback(evaluateJws(attestationResponse.getJwsResult()), msgNotSecure);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        listener.callback(false, msgNotSecure);
                    }
                });

    }

    public static void evaluateSafetyNet(Context context, final PointCheckoutSafetyNetListener listener) {

        final ProgressDialog dialog = ProgressDialog.show(context, "",
                "Checking your device. Please wait...", true);
        dialog.show();

        SafetyNet.getClient(context).attest(generateNonce(), API_KEY)
                .addOnSuccessListener(new OnSuccessListener<SafetyNetApi.AttestationResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.AttestationResponse attestationResponse) {
                        dialog.dismiss();
                        listener.callback(evaluateJws(attestationResponse.getJwsResult()), msgNotSecure);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        e.printStackTrace();
                    }
                });

    }

    private static boolean evaluateJws(String jwsResult) {

        try {
            Map<String, Object> values = jsonToMap(decodeJws(jwsResult));
            return Boolean.valueOf(values.get("ctsProfileMatch").toString()) && Boolean.valueOf(values.get("ctsProfileMatch").toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    private static String decodeJws(String jwsResult) {

        if (jwsResult == null) {
            return null;
        }
        final String[] jwtParts = jwsResult.split("\\.");
        if (jwtParts.length == 3) {
            String decodedPayload = new String(Base64.decode(jwtParts[1], Base64.DEFAULT));
            return decodedPayload;
        } else {
            return null;
        }
    }

    private static byte[] generateNonce() {
        byte[] nonce = new byte[16];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    private static Map<String, Object> jsonToMap(String json) throws JSONException {
        Map<String, Object> retMap = new HashMap<>();

        if (Objects.nonNull(json)) {
            retMap = toMap(new JSONObject(json));
        }

        return retMap;
    }

    private static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    private static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

}
