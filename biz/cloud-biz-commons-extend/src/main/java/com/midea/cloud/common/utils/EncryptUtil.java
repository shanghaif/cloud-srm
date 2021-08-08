package com.midea.cloud.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 *  加密工具类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2019-11-27 10:03
 *  修改内容:
 * </pre>
 */
public class EncryptUtil {

    private static final Logger _logger = LoggerFactory.getLogger(EncryptUtil.class);

    private EncryptUtil() {
    }

    /**
     * SHA256
     * @param str
     * @return
     */
    public static String getSHA256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            _logger.warn(e.getLocalizedMessage());
        }
        _logger.info(encodestr);
        return encodestr;
    }

    /**
     * MD5
     * @param text
     * @return
     */
    public static String getMD5(String text) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(text.getBytes(StandardCharsets.UTF_8));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            _logger.warn(e.getLocalizedMessage());
        }
        _logger.debug(encodestr);
        return encodestr;
    }

    /**
     *
     * @param bytes
     * @return
     */
    static String byte2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                sb.append("0");
            }
            sb.append(temp);
        }
        return sb.toString();
    }

}
