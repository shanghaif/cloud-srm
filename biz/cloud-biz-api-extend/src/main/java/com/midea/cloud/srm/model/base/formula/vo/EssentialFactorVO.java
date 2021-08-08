package com.midea.cloud.srm.model.base.formula.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 *  修改日期: 2020/8/28 16:59
 *  修改内容:
 * </pre>
 */
@Data
public class EssentialFactorVO {

    private Long essentialFactorId;

    /**
     * 要素名称
     */
    private String essentialFactorName;

    /**
     * 要素描述
     */
    private String essentialFactorDesc;

    /**
     * 来源  MATERIAL_MAIN_DATA("MATERIAL_MAIN_DATA", "物料主数据"),
     *     BASE_MATERIAL_PRICE("BASE_MATERIAL_PRICE", "基材物料价格"),
     *     SUPPLIER_QUOTED_PRICE("SUPPLIER_QUOTED_PRICE", "报价");
     */
    private String essentialFactorFrom;

    /**
     * 要素状态
     */
    private String essentialFactorStatus;

    /**
     * 基材id
     */
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
     * 物料主属性id
     */
    private Long materialAttributeId;

    /**
     * 物料主属性名
     */
    private String materialAttributeName;

    /**
     *  DAY_PRICE("DAY_PRICE", "当日价格"),
     *     WEEK_PRICE("WEEK_PRICE", "周均价"),
     *     MONTH_PRICE("MONTH_PRICE", "月均价");
     */
    private String priceType;


    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;
}
