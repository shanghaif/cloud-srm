package com.midea.cloud.srm.model.inq.inquiry.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  配额计算DTO
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:40:36
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class QuotaCalculateDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 品类id
     */
    private Long categoryId;
    /**
     * 最低折息价格和对应供应商
     */
    private Map<BigDecimal,CompanyInfo> minDiscountPriceMap;
    /**
     *中标的折息价格列表
     */
    private List<BigDecimal> discountPriceList;
    /**
     * 中标供应商列表(按照规定的排名写入)
     */
    private List<CompanyInfo> companyInfoList;
    /**
     * 供应商配额原因类型
     */
    private Map<Integer,List<String>> CompanyInfoServiceStatusMap;
}
