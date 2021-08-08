package com.midea.cloud.srm.model.inq.inquiry.dto;

import com.midea.cloud.srm.model.inq.inquiry.entity.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuotaDTO extends Quota {
    /**
     * 创建日期开始(YYYY-MM-DD)
     */
    private Date startDateTime;

    /**
     * 创建日期结束(YYYY-MM-DD)
     */
    private Date endDateTime;

    /**
     * 事业部名称
     */
    private Long buId;
    /**
     * 事业部集合(不用)
     */
    private List<QuotaBu> quotaBuList;
    /**
     * 事业部集合
     */
    private List<QuotaBu> quotaBuDTOList;
    /**
     * 单据
     */
    private Quota quota;
    /**
     * 配额-预设比例集合
     */
    private List<QuotaPreinstall> quotaPreinstallList;
    /**
     * 配额-配额上下限集合
     */
    private List<QuotaRestrictions> quotaRestrictionsList;
    /**
     * 配额-协议比例集合
     */
    private List<AgreementRatio> agreementRatioList;
    /**
     * 配额-差价标准集合
     */
    private List<PriceStandard> priceStandardList;
    /**
     * 配额-预估返利集合
     */
    private List<QuotaRebate> quotaRebateList;
}
