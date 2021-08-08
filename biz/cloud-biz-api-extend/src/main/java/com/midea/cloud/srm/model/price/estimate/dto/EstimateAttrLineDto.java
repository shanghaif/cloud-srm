package com.midea.cloud.srm.model.price.estimate.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/29
 *  修改内容:
 * </pre>
 */
@Data
public class EstimateAttrLineDto implements Serializable {
    /**
     * 属性名字
     */
    private String attributeName;

    /**
     * 属性类型, 字典编码: FEATURE_ATTRIBUTE_TYPE
     */
    private String attributeType;

    /**
     * 属性值
     */
    private String attributeValue;
}
