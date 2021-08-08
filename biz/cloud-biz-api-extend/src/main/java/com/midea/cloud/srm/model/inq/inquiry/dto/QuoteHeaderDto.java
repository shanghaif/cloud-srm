package com.midea.cloud.srm.model.inq.inquiry.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.File;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 创建人 : tangyuan2@meicloud.com
 * 创建日期 : 2019-12-17
 * 版本 : 1.0
 * 功能描述 :
 * 修改记录
 * 修改后版本：  修改人：    修改时间：   修改内容：
 **/
@Data
public class QuoteHeaderDto extends BaseDTO {
    private Long inquiryId;
    private String inquiryNo;
    private String inquiryTitle;
    private Long organizationId;
    private String fullPathId;
    private String organizationName;
    private Long quoteId;
    private String quoteNo;
    private String quoteStatus;
    private Long vendorId;
    private String quoteRule;
    private String inquiryRule;
    private String inquiryType;
    private String currency;
    private String taxRate;
    private String type;
    private String address;
    private Date beginQuote;
    private Date deadline;
    private String round;
    private String status;
    private String auditStatus;
    private String linkman;
    private String tel;
    private String email;
    private Long childInquiryId;
    private Long parentInquiryId;
    private Long createCompanyId;
    private BigDecimal quoteCnt;
    private BigDecimal inviteCnt;
    private Date publishDate;
    private String cbpmInstanceId;
    private String delFlag;
    private String createdBy;
    private Date creationDate;
    private String createdByIp;
    private String lastUpdatedBy;
    private Date lastUpdateDate;
    private String lastUpdatedByIp;
    private Long version;
    private Long tenantId;
    private String inquiryResult;
    private String priceNum;
    private List<File> outerFiles;
    List<QuoteItemDto> items;
}
