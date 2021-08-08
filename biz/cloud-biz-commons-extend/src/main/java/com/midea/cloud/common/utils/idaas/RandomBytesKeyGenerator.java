package com.midea.cloud.common.utils.idaas;

import java.security.SecureRandom;

/**
 * <pre>
 * (idaas平台生成token值用)
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/9
 *  修改内容:
 * </pre>
 */
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
