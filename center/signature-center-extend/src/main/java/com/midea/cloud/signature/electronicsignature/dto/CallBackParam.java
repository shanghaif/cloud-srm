package com.midea.cloud.signature.electronicsignature.dto;

import lombok.Data;

/**
 * <pre>
 * 回调参数
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/17
 *  修改内容:
 * </pre>
 */
@Data
public class CallBackParam {
    /**
     * 智汇签msign
     */
    private String appId;

    /**
     *
     */
    private String appKey;

    /**
     * 数据体
     */
    private String data;
}
