package com.midea.cloud.srm.feign.base;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.clarify.dto.SourcingInfoQueryDto;
import com.midea.cloud.srm.model.base.clarify.dto.SourcingVendorInfo;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingInfoVO;

import java.util.Collection;

/**
 * @author tanjl11
 * @date 2020/10/08 16:55
 */
public interface IGetSourcingRelateInfo {
    /**
     * 获取寻源单据的报名供应商信息
     *
     * @param typeCode
     * @param sourcingId
     * @return
     */
    Collection<SourcingVendorInfo> getSourcingVendorInfo(String typeCode, Long sourcingId);

    /**
     * 获取寻源项目信息
     * @param queryDto
     * @return
     */
    PageInfo<SourcingInfoVO> querySourcingInfo(SourcingInfoQueryDto queryDto);
}
