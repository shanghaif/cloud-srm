package com.midea.cloud.common.utils;

/**
 * <pre>
 *  签名工具类
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
public class SignUtil {

    /**
     * 使用Md5作为签名的算法
     *
     * @param appId
     * @param appSec
     * @param timestamp
     * @return
     */
    public static String getMD5sign(String appId, String appSec, String timestamp) {
        return EncryptUtil.getMD5(appSec + EncryptUtil.getMD5(appId + timestamp));
    }

    public static void main(String[] args) {
        String paas = getMD5sign("paas", "1234567", "123456");
        System.out.println(paas);
    }

}
