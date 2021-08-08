package com.midea.cloud.signature.electronicsignature.utils;

import java.util.UUID;

public class MsignUtils {

    public static String getSerialNum() {
        return UUID.randomUUID().toString();
    }
}
