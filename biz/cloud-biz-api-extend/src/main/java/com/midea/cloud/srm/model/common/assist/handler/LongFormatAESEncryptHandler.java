package com.midea.cloud.srm.model.common.assist.handler;

import com.midea.cloud.srm.model.common.assist.util.AESUtil;

/**
 * <pre>
 *  Long类型解密处理
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-24 13:53
 *  修改内容:
 * </pre>
 */
public class LongFormatAESEncryptHandler extends AbstractAESEncryptHandler<Long> {

    @Override
    public Long formatResultFiled(String columnValue) {
        return Long.valueOf(AESUtil.decrypt(columnValue));
    }

}
