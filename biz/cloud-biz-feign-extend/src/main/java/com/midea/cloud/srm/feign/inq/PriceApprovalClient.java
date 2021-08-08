package com.midea.cloud.srm.feign.inq;

import com.midea.cloud.srm.model.inq.price.dto.InsertPriceApprovalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * FeignClient - 价格审批API
 *
 * @author zixuan.yan@meicloud.com
 */
@FeignClient(value = "cloud-biz-inquiry", contextId = "cloud-biz-inquiry-priceApproval")
public interface PriceApprovalClient {

    @PostMapping("/price/approval/savePriceApproval")
    Long savePriceApproval(@RequestBody InsertPriceApprovalDTO insertPriceApprovalDTO);
}
