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
 *  修改日期: 2020/9/2 11:23
 *  修改内容:
 * </pre>
 */
@Data
public class PricingFormulaHeaderVO {

    private Long pricingFormulaHeaderId;

    private String pricingFormulaName;

    private String pricingFormulaValue;

    private String pricingFormulaStatus;

    private String pricingFormulaDesc;

    private String createdBy;

    private Date creationDate;

    private String isSeaFoodFormula;

}
