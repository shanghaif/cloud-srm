package com.midea.cloud.srm.inq.inquiry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuoteQueryDto;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuoteHeaderDto;
import com.midea.cloud.srm.model.inq.inquiry.entity.Vendor;

import java.util.List;

/**
*  <pre>
 *  询价-邀请供应商表 服务类
 * </pre>
*
* @author zhongbh
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:55
 *  修改内容:
 * </pre>
*/
public interface IVendorService extends IService<Vendor> {

    List<Vendor> getByHeadId(Long inquiryId);

    List<QuoteHeaderDto> getByVendorId(Long vendorId);
    List<QuoteHeaderDto> getByVendorId(QuoteQueryDto quoteQueryDto);

    QuoteHeaderDto getInquiryInfo(Long vendorId,Long inquiryId);

    /**
     * 批量获取邀请供应商信息
     */
    List<Vendor> getByInquiryVendorIds(List<Long> inquiryVendorIds);

    /**
     * 获取待报价数量
     * @return
     */
    Integer getWaitQuote();
}
