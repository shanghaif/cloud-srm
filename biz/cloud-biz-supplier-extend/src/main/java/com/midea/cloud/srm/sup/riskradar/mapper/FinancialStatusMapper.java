package com.midea.cloud.srm.sup.riskradar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.riskradar.entity.FinancialStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 财务状况表 Mapper 接口
 * </p>
 *
 * @author chensl26@meicloud.com
 * @since 2020-09-05
 */
public interface FinancialStatusMapper extends BaseMapper<FinancialStatus> {
    /**
     * 获取供应商近两年财务数据
     * @return
     */
    List<FinancialStatus> getLastTwoYearData(@Param("vendorId") Long vendorId);
}
