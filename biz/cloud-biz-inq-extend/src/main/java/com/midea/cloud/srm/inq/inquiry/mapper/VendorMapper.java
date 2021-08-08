package com.midea.cloud.srm.inq.inquiry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuoteHeaderDto;
import com.midea.cloud.srm.model.inq.inquiry.entity.Vendor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 询价-邀请供应商表 Mapper 接口
 * </p>
 *
 * @author zhongbh
 * @since 2020-03-12
 */
public interface VendorMapper extends BaseMapper<Vendor> {

    List<QuoteHeaderDto> getByVendorId(@Param("vendorId")Long vendorId, @Param("inquiryNo")String inquiryNo, @Param("itemCode")String itemCode, @Param("status")String status, @Param("inquiryStatus")String inquiryStatus, @Param("organizationId")Long organizationId);

    QuoteHeaderDto getByInquiryId(@Param("vendorId")Long vendorId, @Param("inquiryId")Long inquiryId);

}
