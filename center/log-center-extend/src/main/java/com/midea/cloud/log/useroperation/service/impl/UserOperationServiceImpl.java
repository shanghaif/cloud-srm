package com.midea.cloud.log.useroperation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.log.useroperation.mapper.UserOperationMapper;
import com.midea.cloud.log.useroperation.service.IUserOperationService;
import com.midea.cloud.srm.model.log.useroperation.dto.UserOperationDto;
import com.midea.cloud.srm.model.log.useroperation.entity.UserOperation;
import org.springframework.stereotype.Service;

/**
*  <pre>
 *  用户操作日志表 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-11 13:53:23
 *  修改内容:
 * </pre>
*/
@Service
public class UserOperationServiceImpl extends ServiceImpl<UserOperationMapper, UserOperation> implements IUserOperationService {
    @Override
    public PageInfo<UserOperation> listPage(UserOperationDto userOperation) {
        PageUtil.startPage(userOperation.getPageNum(), userOperation.getPageSize());
        QueryWrapper<UserOperation> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtil.notEmpty(userOperation.getUsername()),"USERNAME",userOperation.getUsername());
        wrapper.like(StringUtil.notEmpty(userOperation.getMethodName()),"METHOD_NAME",userOperation.getMethodName());
        wrapper.ge(!StringUtil.isEmpty(userOperation.getOperationTimeStart()), "OPERATION_TIME", userOperation.getOperationTimeStart());
        wrapper.like(!StringUtil.isEmpty(userOperation.getResultStatus()), "RESULT_STATUS", userOperation.getResultStatus());
        wrapper.like(!StringUtil.isEmpty(userOperation.getModel()), "MODEL", userOperation.getModel());
        wrapper.le(!StringUtil.isEmpty(userOperation.getOperationTimeEnd()), "OPERATION_TIME", userOperation.getOperationTimeEnd());
        wrapper.like(StringUtil.notEmpty(userOperation.getRequestIp()),"REQUEST_IP",userOperation.getRequestIp());
        wrapper.like(StringUtil.notEmpty(userOperation.getUserType()),"USER_TYPE",userOperation.getUserType());
        wrapper.like(StringUtil.notEmpty(userOperation.getRequestUrl()),"REQUEST_URL",userOperation.getRequestUrl());
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<>(this.list(wrapper));
    }
}
