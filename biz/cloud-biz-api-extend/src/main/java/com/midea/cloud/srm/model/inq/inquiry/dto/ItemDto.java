package com.midea.cloud.srm.model.inq.inquiry.dto;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.inq.inquiry.entity.LadderPrice;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  询价-询价信息行表 模型
 * </pre>
*
* @author zhongbh
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-16 14:55:25
 *  修改内容:
 * </pre>
*/
@Data
public class ItemDto extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long inquiryItemId;

    private Long inquiryId;

    private Long organizationId;

    private String fullPathId;

    private Long itemId;

    private String itemCode;

    private String itemDesc;

    private Long categoryId;

    private String categoryName;

    private BigDecimal demandQuantity;

    private String specification;

    private String unit;

    private String isLadder;

    private String ladderType;

    private String itemType;

    private String currency;

    private BigDecimal planDay;

    private BigDecimal orderQuantity;

    private BigDecimal notaxTargrtPrice;

    private BigDecimal notaxPrice;

    private Date fixedPriceBegin;

    private Date fixedPriceEnd;

    private String taxKey;

    private String taxRate;

    private String memo;

    private Long fileRelationId;

    private String fileName;

    private String remark;

    private String categoryCanEdit;

    private String delFlag;

    private String createdBy;

    private Date creationDate;

    private String createdByIp;

    private String lastUpdatedBy;

    private Date lastUpdateDate;

    private String lastUpdatedByIp;

    private Long version;

    private Long tenantId;

    private List<LadderPrice> ladderPrices;

}
