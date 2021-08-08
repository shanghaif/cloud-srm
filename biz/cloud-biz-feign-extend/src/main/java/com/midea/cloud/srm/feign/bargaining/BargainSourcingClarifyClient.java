package com.midea.cloud.srm.feign.bargaining;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.feign.base.IGetSourcingRelateInfo;
import com.midea.cloud.srm.model.base.clarify.dto.SourcingInfoQueryDto;
import com.midea.cloud.srm.model.base.clarify.dto.SourcingVendorInfo;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

/**
 * @author tanjl11
 * @date 2020/10/10 19:51
 */
@FeignClient(value = "cloud-biz-bargaining", contextId = "cloud-biz-bargaining-sourcing-clarify")
public interface BargainSourcingClarifyClient extends IGetSourcingRelateInfo {

    /**
     * 根据寻源类型和id获取对应的报名供应商信息
     * @param typeCode
     * @param sourcingId
     * @return
     */
    @Override
    @GetMapping("/supplierCooperate/bidSingUp/getSourcingVendorInfo")
    Collection<SourcingVendorInfo> getSourcingVendorInfo(@RequestParam("typeCode") String typeCode, @RequestParam("sourcingId") Long sourcingId);

    @Override
    @PostMapping("/bidInitiating/biding/querySourcingInfo")
    PageInfo<SourcingInfoVO> querySourcingInfo(@RequestBody SourcingInfoQueryDto queryDto);
}
