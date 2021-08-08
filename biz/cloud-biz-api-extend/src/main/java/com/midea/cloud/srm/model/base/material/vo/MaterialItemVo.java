package com.midea.cloud.srm.model.base.material.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/9/12 18:34
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class MaterialItemVo extends MaterialItem {

    /**
     * 下架倒计时 年
     */
    private Integer year;

    /**
     * 下架倒计时 月
     */
    private Integer month;


    /**
     * 下架倒计时 日
     */
    private Integer day;

    /**
     * 物料大类ID
     */
    private Long bigCategoryId;

    /**
     * 物料大类名称
     */
    private String bigCategoryName;

    /**
     * 物料大类编码
     */
    private String bigCategoryCode;

    /**
     * 物料中类ID
     */
    private Long middleCategoryId;

    /**
     * 物料中类名称
     */
    private String middleCategoryName;

    /**
     * 物料中类编码
     */
    private String middleCategoryCode;

    /**
     * 物料小类ID
     */
    private Long categoryId;

    /**
     * 物料小类名称
     */
    private String categoryName;

    /**
     * 物料小类编码
     */
    private String categoryCode;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 业务主体ID
     */
    private Long orgId;

    /**
     * 业务主体编码
     */
    private String orgCode;

    /**
     * 业务主体名称
     */
    private String orgName;

    /**
     * 库存组织ID
     */
    private Long organizationId;

    /**
     * 库存组织编码
     */
    private String organizationCode;

    /**
     * 库存组织名称
     */
    private String organizationName;

    /**
     * 含税单价
     */
    private BigDecimal taxPrice;

    /**
     * 不含税单价
     */
    private BigDecimal noTaxPrice;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 税率编码
     */
    private String taxCode;

    /**
     * 币种id
     */
    private Long currencyId;

    /**
     * 币种编码
     */
    private String currencyCode;


    /**
     * 币种名称
     */
    private String currencyName;

    /**
     * 合同list
     */
    private List<ContractVo> contractVoList;

    /**
     * 合同编号
     */
    private String contractCode;

    /**
     * 可用税率
     */
    private List<PurchaseTax> purchaseTaxList;

    /**
     * 价格来源类型
     */
    @TableField(exist = false)
    private String ceeaPriceSourceType;

    /**
     * 价格来源单据id(contractMaterialId, priceLibraryId)
     */
    @TableField(exist = false)
    private Long ceeaPriceSourceId;


}
