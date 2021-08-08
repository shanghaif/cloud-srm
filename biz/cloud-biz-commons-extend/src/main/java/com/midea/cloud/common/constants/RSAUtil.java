package com.midea.cloud.common.constants;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * Description: RSA公钥/私钥/签名工具包
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * @Param
 * @return
 * @Author wuwl18@meicloud.com
 * @Date 2020.07.23
 * @throws
 **/
public class RSAUtil {
    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDDKF7JJRQaYO1sPXMyrQQZh9DVZWLcHQyqgQxRy3soLFgeywb7iyaFlWyF0WWTG00hBamoWjeCmCsGOriCxlUfuDFBplWj03EOxgmdJSesrZvsUrNjJj7zrnonHk5d0iYURrUvoYv9z2r9v4kjF7UNn8YLxE+CirHcIOyvgvY6wIDAQAB";
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * RSA最大解密密文大小
     */
    private static final String CHARSET = "UTF-8";;
    /**
     * 校验数字签名
     *
     * @param data 已加密数据
     *             公钥(BASE64编码)
     * @param sign 数字签名
     * @throws Exception
     */
    public static boolean verify(byte[] data, String sign)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * 公钥解密
     */
    public static String decryptByPublicKey(String encrypteContext) throws Exception {
        byte[] bytes = decryptByPublicKey(Base64.decodeBase64(encrypteContext));
        return new String(bytes);
    }


    /**
     * 公钥解密
     *
     * @param encryptedData 已加密数据
     *                      公钥(BASE64编码)
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 公钥加密
     */
    public static String encryptByPublicKey(String context) throws Exception {
        return Base64.encodeBase64String(encryptByPublicKey(context.getBytes(CHARSET)));
    }

    /**
     * 公钥加密后使用urlcode编码
     */
    public static String encryptByPublicKeyUrlCode(String context) throws Exception {
        return URLEncoder.encode(encryptByPublicKey(context), CHARSET);
    }

    /**
     * 公钥加密
     *
     * @param data 源数据
     *             公钥(BASE64编码)
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static void main(String[] args) {
        try {
            String password = "xxx***xxx";
//            System.out.println(RSAUtil.encryptByPublicKeyUrlCode(password));
            String pass = "123qwe##";
            String pass2 = RSAUtil.encryptByPublicKey(pass);
            System.out.println("pass2:"+pass2);
            System.out.println("解密："+RSAUtil.decryptByPublicKey(pass2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

