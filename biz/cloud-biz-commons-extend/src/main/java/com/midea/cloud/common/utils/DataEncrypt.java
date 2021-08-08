package com.midea.cloud.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class DataEncrypt {

    private static final Logger logger = LoggerFactory.getLogger(DataEncrypt.class);
    /**
     * 默认散列加密次数
     */
    private static int DEFAULT_HASH_COUNT = 1;


    /**
     * 返回加密串
     *
     * @param data 加密数据
     * @param salt 加密密匙
     * @return
     * @throws Exception
     */
    public static String getEncryptStr(String data, String salt) {
        if (StringUtils.isBlank(data))
            return null;
        try {
            byte[] bytesSalts = StringUtils.isNotBlank(salt) ? salt.getBytes("UTF-8") : null;
            byte[] bytesData = StringUtils.isNotBlank(data) ? data.getBytes("UTF-8") : null;
            byte[] encryptStr = md5hash(bytesData, bytesSalts, DEFAULT_HASH_COUNT);
            return byteToStr(encryptStr);
        } catch (Exception e) {
            logger.error("数据加密失败 , data:" + data + ",salt:" + salt, e);
        }
        return null;
    }

    /**
     * 返回加密串
     *
     * @param data      需要加密数据
     * @param salt      加密密匙
     * @param hashCount 加密次数
     * @return
     * @throws Exception
     */
    public static String getEncryptStr(String data, String salt, int hashCount) {
        if (StringUtils.isBlank(data))
            return null;
        try {
            byte[] bytesSalts = StringUtils.isNotBlank(salt) ? salt.getBytes("UTF-8") : null;
            byte[] bytesData = StringUtils.isNotBlank(data) ? data.getBytes("UTF-8") : null;
            byte[] encryptStr = md5hash(bytesData, bytesSalts, hashCount);
            return byteToStr(encryptStr);
        } catch (Exception e) {
            logger.error("数据加密失败 , data:" + data + ",salt:" + salt, e);
        }
        return null;
    }

    /**
     * 数据加密
     *
     * @param encryptBuff
     * @param salt
     * @param hashCount
     * @return
     */
    public static String encryptByte(byte[] encryptBuff, String salt, int hashCount) {
        if (null == encryptBuff || encryptBuff.length == 0)
            return null;
        try {
            byte[] bytesSalts = StringUtils.isNotBlank(salt) ? salt.getBytes("UTF-8") : null;
            byte[] encryptStr = md5hash(encryptBuff, bytesSalts, hashCount);
            return byteToStr(encryptStr);
        } catch (Exception e) {
            logger.error("数据加密失败 ,salt:" + salt, e);
        }
        return null;
    }

    /**
     * 数据加密
     *
     * @param encryptBuff
     * @param salt
     * @return
     */
    public static String encryptByte(byte[] encryptBuff, String salt) {
        if (null == encryptBuff || encryptBuff.length == 0)
            return null;
        try {
            byte[] bytesSalts = StringUtils.isNotBlank(salt) ? salt.getBytes("UTF-8") : null;
            byte[] encryptStr = md5hash(encryptBuff, bytesSalts, DEFAULT_HASH_COUNT);
            return byteToStr(encryptStr);
        } catch (Exception e) {
            logger.error("数据加密失败 ,salt:" + salt, e);
        }
        return null;
    }

    /**
     * 加密
     *
     * @param bytesData
     * @param bytesSalts
     * @param hashCount
     * @return
     */
    private static byte[] md5hash(byte[] bytesData, byte[] bytesSalts, int hashCount) {
        MessageDigest digest = getDigest("MD5");
        if (bytesSalts != null) {
            digest.reset();
            digest.update(bytesSalts);
        }
        byte[] hashed = digest.digest(bytesData);
        for (int i = DEFAULT_HASH_COUNT; i < hashCount; i++) {
            digest.reset();
            hashed = digest.digest(hashed);
        }
        return hashed;
    }

    /**
     * 获取加密算法
     *
     * @param algorithmName
     * @return
     */
    protected static MessageDigest getDigest(String algorithmName) {
        try {
            return MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            String msg = "No native '" + algorithmName + "' MessageDigest instance available on the current JVM.";
//            Assert.throwException(msg);
        }
        return null;
    }

    public static String byteToStr(byte[] byteData) throws Exception {

        return DatatypeConverter.printBase64Binary(byteData);
    }

    public static void main(String[] args) throws Exception {
        String dataStr = "{\"header\":{\"uid\":\"ex_lidh3\",\"transid\":\"15d31859f358f8995d55c644890b9b0b\"},\"body\":{\"result\":{},\"fdOperatorType\":\"query\",\"data\":{\"fd_id\":\"15d45bed8de6298d881ae7e4f5fbb5b7\"},\"fdCode\":\"queryTest\",\"fdResourceName\":\"mdc_test_inface\",\"fdDataModuleId\":\"15d31859f358f8995d55c644890b9b0b\"}}";
        String salts = "1211213sdfsdfsdffasdfaf123221";
        System.out.println(encryptByte(dataStr.getBytes(), salts, 1));
    }
}

							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							

