package com.midea.cloud.srm.inq.inquiry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.inq.inquiry.domain.VendorNResult;
import com.midea.cloud.srm.model.inq.inquiry.entity.VendorN;

import java.util.List;

/**
 * <p>
 * 供应商N值 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-04-07
 */
public interface VendorNMapper extends BaseMapper<VendorN> {

    /**
     * 查询供应商N值
     */
    List<VendorNResult> queryVendorN(Long inquiryId);
}
