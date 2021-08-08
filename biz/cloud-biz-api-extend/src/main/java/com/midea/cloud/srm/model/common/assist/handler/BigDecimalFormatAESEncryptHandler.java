package com.midea.cloud.srm.model.common.assist.handler;

import com.midea.cloud.srm.model.common.assist.util.AESUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * <pre>
 *  BigDecimal类型解密处理
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-13 19:03
 *  修改内容:
 * </pre>
 */
public class BigDecimalFormatAESEncryptHandler extends AbstractAESEncryptHandler<BigDecimal> {

    @Override
    public BigDecimal formatResultFiled(String columnValue) {
        if (StringUtils.isBlank(columnValue)) {
            return null;
        }
        return new BigDecimal(AESUtil.decrypt(columnValue));
    }

}
