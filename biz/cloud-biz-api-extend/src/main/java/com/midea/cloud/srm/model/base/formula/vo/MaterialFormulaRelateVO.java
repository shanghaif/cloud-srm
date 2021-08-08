package com.midea.cloud.srm.model.base.formula.vo;

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
 *  修改日期: 2020/9/4 13:58
 *  修改内容:
 * </pre>
 */
@Data
public class MaterialFormulaRelateVO {
    private Long relateId;
    private Long materialId;
    private String pricingFormulaValue;
    /**
     * 公式id
     */
    private Long pricingFormulaHeaderId;
    /**
     * 公式名
     */
    private String pricingFormulaName;
    /**
     * 公式描述
     */
    private String pricingFormulaDesc;
    /**
     * 物料code
     */
    private String materialCode;
    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 规格型号
     */
    private String specification;
    /**
     * 品类id
     */
    private Long categoryId;
    /**
     * 品类名
     */
    private String categoryName;
    /**
     * 单位
     */
    private String unit;
    /**
     * 单位名
     */
    private String unitName;

    private String lastUpdatedBy;

    private Date lastUpdateDate;
}
