package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.IntelligentRecommendVO;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidSignUpVO;

/**
 * <pre>
 * 供应商表 服务类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-27 17:36:33
 *  修改内容:
 * </pre>
 */
public interface IBidVendorService extends IService<BidVendor> {
    /**
     * 当供应商端报名的时候，保存一条记录到邀请供应商表
     *
     * @param bidSignUpVO
     * @return
     */
    Long saveBidVendorWhenSignUp(BidSignUpVO bidSignUpVO);

    void saveBidVendorList(List<BidVendor> bidVendorList);

    void updateBatch(List<BidVendor> bidVendorList);

    List<IntelligentRecommendVO> listIntelligentRecommendInfo(Long bidingId);

    List<BidVendor> listVendorContactInfo(List<Long> vendorIdList);
}
