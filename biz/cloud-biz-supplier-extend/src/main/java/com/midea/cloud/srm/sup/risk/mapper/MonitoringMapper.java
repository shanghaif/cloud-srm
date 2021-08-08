package com.midea.cloud.srm.sup.risk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.risk.entity.Monitoring;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 供应商风险监控 Mapper 接口
 * </p>
 *
 * @author wangpr@meicloud.com
 * @since 2020-09-01
 */
public interface MonitoringMapper extends BaseMapper<Monitoring> {
    Monitoring queryByVendorIdAndRiskType(@Param("vendorId") Long vendorId, @Param("riskType") String riskType);
}
