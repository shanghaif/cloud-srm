package com.midea.cloud.srm.model.supplier.info.dto;

import lombok.Data;

/**
 * <pre>
 * 中标次数
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/1
 *  修改内容:
 * </pre>
 */
@Data
public class BidFrequency {
    /**
     * 年度
     */
    private int year;

    /**
     * 次数
     */
    private int frequency;
}
