package com.midea.cloud.srm.model.inq.inquiry.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.LadderPrice;
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
public class LadderPriceDto extends BaseDTO {
    List<LadderPrice> ladderPrices;
}
