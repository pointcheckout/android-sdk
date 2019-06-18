package com.pc.android.sdk;

import java.util.Objects;

public class PointCheckoutUtils {
    public static void assertNotNull(Object obj) throws PointCheckoutException {
        if (Objects.isNull(obj))
            throw new PointCheckoutException(String.format("%s can not be null", obj.getClass().getSimpleName()));
    }
}
