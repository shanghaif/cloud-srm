package com.midea.cloud.srm.rbac.security.common.facepass;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Pbkdf2Encoder {
    private static final int DEFAULT_HASH_WIDTH = 256;
    private static final int DEFAULT_ITERATIONS = 5000;
    private final RandomBytesKeyGenerator saltGenerator = new RandomBytesKeyGenerator();
    private String algorithm = SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA1.name();
    private final byte[] secret;
    private final int hashWidth;
    private final int iterations;

    public Pbkdf2Encoder() {
        this("");
    }

    public Pbkdf2Encoder(String secret) {
        this(secret, DEFAULT_ITERATIONS, DEFAULT_HASH_WIDTH);
    }

    public Pbkdf2Encoder(String secret, int iterations, int hashWidth) {
        this.secret = EncodingUtils.encodeUtf8(secret);
        this.iterations = iterations;
        this.hashWidth = hashWidth;
    }

    public void setAlgorithm(SecretKeyFactoryAlgorithm secretKeyFactoryAlgorithm) {
        if (secretKeyFactoryAlgorithm == null) {
            throw new IllegalArgumentException("secretKeyFactoryAlgorithm cannot be null");
        }
        String algorithmName = secretKeyFactoryAlgorithm.name();
        try {
            SecretKeyFactory.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Invalid algorithm '" + algorithmName + "'.", e);
        }
        this.algorithm = algorithmName;
    }

    public String encode(String rawPassword) {
        byte[] salt = this.saltGenerator.generateKey();
        byte[] encoded = encode(rawPassword, salt);
        return encode(encoded);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        byte[] digested = decode(encodedPassword);
        byte[] salt = EncodingUtils.subArray(digested, 0, this.saltGenerator.getKeyLength());
        return MessageDigest.isEqual(digested, encode(rawPassword, salt));
    }

    private String encode(byte[] bytes) {
        return String.valueOf(EncodingUtils.encodeHex(bytes));
    }

    private byte[] decode(String encodedBytes) {
        return EncodingUtils.decodeHex(encodedBytes);
    }

    private byte[] encode(String rawPassword, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(rawPassword.toCharArray(),
            		EncodingUtils.concatenate(salt, this.secret), this.iterations, this.hashWidth);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(this.algorithm);
            return EncodingUtils.concatenate(salt, skf.generateSecret(spec).getEncoded());
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Could not create hash", e);
        }
    }

    public enum SecretKeyFactoryAlgorithm {
        PBKDF2WithHmacSHA1,
        PBKDF2WithHmacSHA256,
        PBKDF2WithHmacSHA512
    }

}
