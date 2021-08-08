package com.midea.cloud.log.useroperation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.log.useroperation.mapper.BusinessOperationMapper;
import com.midea.cloud.log.useroperation.service.IBusinessOperationService;
import com.midea.cloud.srm.model.log.biz.dto.BizOperateLogInfoDto;
import com.midea.cloud.srm.model.log.biz.entity.BizOperateLogInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/5 14:44
 *  修改内容:
 * </pre>
 */
@Service
public class IBusinessOperationServiceImpl extends ServiceImpl<BusinessOperationMapper, BizOperateLogInfo> implements IBusinessOperationService {

    @Override
    public PageInfo<BizOperateLogInfo> listPage(BizOperateLogInfoDto dto) {
        LambdaQueryWrapper<BizOperateLogInfo> wrapper = new LambdaQueryWrapper();
        //设置查询条件
        wrapper.select(BizOperateLogInfo::getBusinessId,
        BizOperateLogInfo::getBusinessNo,
                BizOperateLogInfo::getBusinessTab,
                BizOperateLogInfo::getCreatedId,
                BizOperateLogInfo::getOperateInfo,
                BizOperateLogInfo::getOperateIp,
                BizOperateLogInfo::getOperateTime,
                BizOperateLogInfo::getOperateType,
                BizOperateLogInfo::getUsername,
                BizOperateLogInfo::getUserType,
                BizOperateLogInfo::getCreatedId,
                BizOperateLogInfo::getNickname,
                BizOperateLogInfo::getModel
        );
        //设置判断条件
        setCondition(wrapper, BizOperateLogInfo::getBusinessId, dto.getBusinessId());
        setCondition(wrapper, BizOperateLogInfo::getBusinessTab, dto.getBusinessTab());
        setCondition(wrapper, BizOperateLogInfo::getBusinessNo, dto.getBusinessNo());
        setCondition(wrapper, BizOperateLogInfo::getOperateType, dto.getOperateType());
        setCondition(wrapper, BizOperateLogInfo::getModel, dto.getModel());
        setCondition(wrapper,BizOperateLogInfo::getUsername,dto.getUserAccount());
        wrapper.likeRight(StringUtils.isNotBlank(dto.getUserAccount()),BizOperateLogInfo::getUsername,dto.getUserAccount());
        wrapper.likeRight(StringUtils.isNotBlank(dto.getOperateInfo()), BizOperateLogInfo::getOperateInfo, dto.getOperateInfo());
        String beginTime = dto.getBeginTime();
        wrapper.ge(StringUtils.isNotEmpty(beginTime),BizOperateLogInfo::getOperateTime,beginTime);
        String endTime=dto.getEndTime();
        wrapper.le(StringUtils.isNotEmpty(endTime),BizOperateLogInfo::getOperateTime,endTime);
        wrapper.orderByAsc(BizOperateLogInfo::getOperateTime);
        PageUtil.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(this.list(wrapper));
    }

    public static <T> void setCondition(LambdaQueryWrapper<T> wrapper, SFunction<T, String> condition, String value) {
        if (StringUtils.isNotEmpty(value)) {
            wrapper.eq(condition, value);
        }

    }
}
