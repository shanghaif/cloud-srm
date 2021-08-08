package com.midea.cloud.srm.model.base.formula.vo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

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
 *  修改日期: 2020/8/26 22:57
 *  修改内容:
 * </pre>
 */
@Data
public class BaseMaterialVO {
    private Long baseMaterialId;

    /**
     * 基材编码
     */
    private String baseMaterialCode;

    /**
     * 基材名称
     */
    private String baseMaterialName;

    /**
     * 基材类型
     */
    private String baseMaterialType;

    /**
     * 基材状态
     */
    private String baseMaterialStatus;
    /**
     * 是否海鲜价
     */
    private String seaFoodPrice;
    /**
     * 基材单位
     */
    private String baseMaterialUnit;
    /**
     * 计算方式
     */
    private String baseMaterialCalculateType;
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;
}
