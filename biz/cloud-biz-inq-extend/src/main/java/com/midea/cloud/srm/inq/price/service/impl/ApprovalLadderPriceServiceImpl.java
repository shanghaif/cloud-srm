package com.midea.cloud.srm.inq.price.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.inq.price.mapper.ApprovalLadderPriceMapper;
import com.midea.cloud.srm.inq.price.service.IApprovalLadderPriceService;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalLadderPrice;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  价格审批单-阶梯价表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
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
@Service
public class ApprovalLadderPriceServiceImpl extends ServiceImpl<ApprovalLadderPriceMapper, ApprovalLadderPrice> implements IApprovalLadderPriceService {

    @Override
    public List<ApprovalLadderPrice> getLadderPrice(List<Long> approvalItemIds) {
        QueryWrapper<ApprovalLadderPrice> itemWrapper = new QueryWrapper<>();
        itemWrapper.in("APPROVAL_ITEM_ID", approvalItemIds)
                .orderByAsc("BEGIN_QUANTITY").orderByAsc("END_QUANTITY");
        return list(itemWrapper);
    }
}
