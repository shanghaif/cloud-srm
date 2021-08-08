package com.midea.cloud.srm.rbac.security.common.facepass;

import java.security.SecureRandom;

public class RandomBytesKeyGenerator {
    private final SecureRandom random;

    private final int keyLength;

    RandomBytesKeyGenerator() {
        this(DEFAULT_KEY_LENGTH);
    }

    RandomBytesKeyGenerator(int keyLength) {
        this.random = new SecureRandom();
        this.keyLength = keyLength;
    }

    public int getKeyLength() {
        return keyLength;
    }

    public byte[] generateKey() {
        byte[] bytes = new byte[keyLength];
        random.nextBytes(bytes);
        return bytes;
    }

    private static final int DEFAULT_KEY_LENGTH = 8;
}
