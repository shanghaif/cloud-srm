package com.midea.cloud.srm.inq.price.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.inq.price.mapper.ApprovalItemMapper;
import com.midea.cloud.srm.inq.price.service.IApprovalItemService;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalItem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  价格审批单行表 服务实现类
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
public class ApprovalItemServiceImpl extends ServiceImpl<ApprovalItemMapper, ApprovalItem> implements IApprovalItemService {

    @Override
    public List<ApprovalItem> queryByApprovalHeaderId(Long approvalHeaderId) {
        QueryWrapper<ApprovalItem> wrapper = new QueryWrapper<>();
        wrapper.eq("APPROVAL_HEADER_ID", approvalHeaderId);
        return list(wrapper);
    }
}
