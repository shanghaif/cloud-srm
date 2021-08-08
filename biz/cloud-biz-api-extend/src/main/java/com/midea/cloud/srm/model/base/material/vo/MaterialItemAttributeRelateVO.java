package com.midea.cloud.srm.model.base.material.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 *  修改日期: 2020/8/28 11:23
 *  修改内容:
 * </pre>
 */
@Data
public class MaterialItemAttributeRelateVO {
    private Long relateId;

    /**
     * 物料主数据属性维护id
     */
    private Long materialAttributeId;

    private Long materialItemId;
    /**
     * 属性编码
     */
    private String attributeCode;

    /**
     * 基材名
     */
    private String attributeName;
    /**
     * 属性值
     */
    private String attributeValue;

    /**
     * 是否为关键属性
     */
    private String keyFeature;
}
