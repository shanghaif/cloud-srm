package com.midea.cloud.srm.inq.price.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.inq.price.mapper.ApprovalFileMapper;
import com.midea.cloud.srm.inq.price.service.IApprovalFileService;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalFile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  价格审批单附件 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-23 17:40:03
 *  修改内容:
 * </pre>
*/
@Service
public class ApprovalFileServiceImpl extends ServiceImpl<ApprovalFileMapper, ApprovalFile> implements IApprovalFileService {

    @Override
    public List<ApprovalFile> getByApprovalHeadId(Long approvalHeaderId) {
        QueryWrapper<ApprovalFile> wrapper = new QueryWrapper<>();
        wrapper.eq("APPROVAL_HEADER_ID", approvalHeaderId);
        return list(wrapper);
    }
}
