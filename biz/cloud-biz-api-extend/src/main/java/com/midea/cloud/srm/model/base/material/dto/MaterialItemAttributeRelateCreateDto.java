package com.midea.cloud.srm.model.base.material.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/27 14:47
 *  修改内容:
 * </pre>
 */
@Data
public class MaterialItemAttributeRelateCreateDto {

    private Long relateId;
    /**
     * 属性值
     */
    @NotEmpty(message = "属性值不能为空")
    private String attributeValue;

    /**
     * 物料id
     */
    @NotNull(message = "物料id不能为空")
    private Long materialItemId;

    /**
     * 属性id
     */
    @NotNull(message = "属性id不能为空")
    private Long materialAttributeId;


    /**
     * 是否为关键值
     */
    @NotEmpty(message = "是否为关键属性不能为空")
    private String keyFeature;
}
