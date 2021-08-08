package com.midea.cloud.common.constants;

import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @Author vent
 * @Description
 **/
@Slf4j
public class DESUtilForRisk {
    private final static String DES = "DES";

    private final static Base64.Decoder decoder = Base64.getDecoder();
    private final static Base64.Encoder encoder = Base64.getEncoder();

    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes("utf-8"), key.getBytes("utf-8"));
        String strs = encoder.encodeToString(bt);
        return strs;
    }

    public static void main(String[] args) throws Exception {
        String data = "隆基绿能科技股份有限公司";
        String password = encrypt(data,"2c90808f763ac4a80176f5ef6b2102f6");
        System.out.println(password);

        //GhJQtJqeLkD4XQKk6Eio1ocWknmbQKof5S3T6NNKVaVTudw+/WZqQA==  中天科技海缆股份有限公司
        //10UaH1Z5c01DPK+zSNHFelFGxTsNmMeN5S3T6NNKVaVTudw+/WZqQA==  隆基绿能科技股份有限公司
    }

    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) {
        try {
            if (data == null){
                return null;
            }

            byte[] buf = decoder.decode(data);
            byte[] bt = decrypt(buf,key.getBytes());

            return new String(bt, "utf-8");
        }catch (Exception e){
            e.printStackTrace();
            JSONObject param=new JSONObject();
            param.put("key",key);
            param.put("data",data);
            log.error("解密失败！==>decrypt Arg : {}", JSONObject.toJSONString(param));
            throw new BaseException("解密失败！");
        }

    }

    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }


    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }
}
