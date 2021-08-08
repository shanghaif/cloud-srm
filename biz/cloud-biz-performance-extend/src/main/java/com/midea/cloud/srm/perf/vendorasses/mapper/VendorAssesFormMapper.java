package com.midea.cloud.srm.perf.vendorasses.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaBu;
import com.midea.cloud.srm.model.perf.vendorasses.VendorAssesForm;
import com.midea.cloud.srm.model.perf.vendorasses.dto.VendorAssesFormOV;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 供应商考核单表 Mapper 接口
 * </p>
 *
 * @author wangpr@meiCloud.com
 * @since 2020-05-27
 */
public interface VendorAssesFormMapper extends BaseMapper<VendorAssesForm> {
    List<VendorAssesForm> queryIndicator(@Param("indicatorName") String indicatorName);
    List<VendorAssesFormOV> ListCopy(@Param(Constants.WRAPPER) QueryWrapper<VendorAssesForm> wrapper);
}
