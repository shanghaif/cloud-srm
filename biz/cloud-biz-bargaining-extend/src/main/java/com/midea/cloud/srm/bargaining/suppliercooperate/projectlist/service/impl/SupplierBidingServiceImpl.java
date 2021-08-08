package com.midea.cloud.srm.bargaining.suppliercooperate.projectlist.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.bargaining.projectmanagement.evaluation.OrderStatusEnum;
import com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.enums.bargaining.projectmanagement.signupmanagement.SignUpStatus;
import com.midea.cloud.srm.bargaining.suppliercooperate.projectlist.mapper.SupplierBidingMapper;
import com.midea.cloud.srm.bargaining.suppliercooperate.projectlist.service.ISupplierBidingService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.vo.SupplierBidingVO;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;

/**
*  <pre>
 *  招标基础信息表 服务实现类
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-31 11:44:36
 *  修改内容:
 * </pre>
*/
@Service
public class SupplierBidingServiceImpl extends ServiceImpl<SupplierBidingMapper, Biding> implements ISupplierBidingService {

    @Override
    public List<SupplierBidingVO> getSupplierBiding(SupplierBidingVO supplierBidingVO) {
        List<SupplierBidingVO> supplierBidingVOS = new ArrayList();
        supplierBidingVOS = this.getBaseMapper().getSupplierBidingList(supplierBidingVO);
        supplierBidingVOS.forEach(bidingVO->{
            if(StringUtils.isEmpty(bidingVO.getSignUpStatus())){
                bidingVO.setSignUpStatus(SignUpStatus.NO_SIGNUP.getValue());
            }
            if(StringUtils.isEmpty(bidingVO.getOrderStatus())){
                bidingVO.setOrderStatus(OrderStatusEnum.DRAFT.getValue());
            }
            bidingVO.setNow(new Timestamp(new Date().getTime()));
        });
        return supplierBidingVOS;
    }

    @Override
    public WorkCount countCreate(SupplierBidingVO supplierBidingVO) {
        supplierBidingVO.setOrderStatus(OrderStatusEnum.DRAFT.getValue());
        supplierBidingVO.setBidingStatus(BiddingProjectStatus.ACCEPT_BID.getValue());
        Integer count = this.baseMapper.countCreate(supplierBidingVO);

        WorkCount workCount = new WorkCount();
        workCount.setTitle("待投标");
        workCount.setUrl("/vendorBiddingManagement/vendorBiddingList?from=workCount&orderStatus="+supplierBidingVO.getOrderStatus()
                +"&bidingStatus="+supplierBidingVO.getBidingStatus());
        workCount.setCount(count);
        return workCount;
    }
}
