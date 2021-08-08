package com.midea.cloud.srm.inq.price.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalLadderPrice;

import java.util.List;

/**
*  <pre>
 *  价格审批单-阶梯价表 服务类
 * </pre>
*
* @author linxc6@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-08 15:28:50
 *  修改内容:
 * </pre>
*/
public interface IApprovalLadderPriceService extends IService<ApprovalLadderPrice> {

    /**
     * 批量获取阶梯价
     */
    List<ApprovalLadderPrice> getLadderPrice(List<Long> approvalItemIds);
}
