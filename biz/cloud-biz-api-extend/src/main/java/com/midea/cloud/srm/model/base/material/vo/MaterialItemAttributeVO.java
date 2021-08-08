package com.midea.cloud.srm.model.base.material.vo;

import lombok.Data;

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
 *  修改日期: 2020/8/27 14:46
 *  修改内容:
 * </pre>
 */
@Data
public class MaterialItemAttributeVO {
    private Long materialAttributeId;


    /**
     * 属性编码
     */
    private String attributeCode;

    /**
     * 基材名
     */
    private String attributeName;
}
