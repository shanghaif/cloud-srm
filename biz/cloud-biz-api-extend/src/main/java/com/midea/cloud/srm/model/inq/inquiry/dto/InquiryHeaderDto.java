package com.midea.cloud.srm.model.inq.inquiry.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.*;
import lombok.Data;

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
public class InquiryHeaderDto extends BaseDTO {
    private Header header;
    private List<File> innerFiles;
    private List<File> outerFiles;
    private List<ItemDto> items;
    private List<Vendor> vendors;
    private ScoreRule scoreRule;
    private List<ScoreRuleItem> ruleItems;
    private List<QuoteAuth> quoteAuth;
}
