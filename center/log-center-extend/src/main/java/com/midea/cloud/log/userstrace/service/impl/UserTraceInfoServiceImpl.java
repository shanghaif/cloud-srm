package com.midea.cloud.log.userstrace.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.NumberUtil;
import com.midea.cloud.log.userstrace.mapper.UserTraceInfoMapper;
import com.midea.cloud.log.userstrace.service.IUserTraceInfoService;
import com.midea.cloud.log.userstrace.service.IUserTraceService;
import com.midea.cloud.srm.model.log.trace.entity.UserTrace;
import com.midea.cloud.srm.model.log.trace.entity.UserTraceInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  用户使用痕迹明细表 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-10 09:01:19
 *  修改内容:
 * </pre>
*/
@Service
public class UserTraceInfoServiceImpl extends ServiceImpl<UserTraceInfoMapper, UserTraceInfo> implements IUserTraceInfoService {
    @Resource
    IUserTraceService iUserTraceService;
    @Override
    @Transactional
    public void add(String username, String userIP, String userType) {
        // 当前时间
        Date date = new Date();
        UserTraceInfo userTraceInfo = new UserTraceInfo();
        Long traceInfoId = IdGenrator.generate();
        userTraceInfo.setTraceInfoId(traceInfoId);
        userTraceInfo.setLoginDate(date);
        userTraceInfo.setLogIp(userIP);
        // 判断头表是否已存在, 获取头表id
        UserTrace userTrace = new UserTrace();
        userTrace.setUsername(username);
        userTrace.setNowDate(DateUtil.dateToLocalDate(date));
        QueryWrapper<UserTrace> queryWrapper = new QueryWrapper<>(userTrace);
        List<UserTrace> userTraceList = iUserTraceService.list(queryWrapper);
        boolean flag = true;
        if(CollectionUtils.isNotEmpty(userTraceList)){
            // 头数据
            UserTrace trace = userTraceList.get(0);
            Long traceId = trace.getTraceId();
            userTraceInfo.setTraceId(traceId);
            // 更新头表数据
            trace.setLoginNum(trace.getLoginNum()+1);
            trace.setLastUpdateDate(new Date());
            // 更新头表
            iUserTraceService.updateById(trace);
        }else {
            // 新增头表行
            UserTrace trace = new UserTrace();
            Long traceId = IdGenrator.generate();
            trace.setTraceId(traceId);
            userTraceInfo.setTraceId(traceId);
            trace.setNowDate(LocalDate.now());
            trace.setUsername(username);
            trace.setLoginNum(1L);
            trace.setCumulativeOnlineTime(0D);
            trace.setCreatedBy(username);
            trace.setCreatedByIp(userIP);
            trace.setCreationDate(new Date());
            trace.setCreatedId(0L);
            trace.setLastUpdateDate(new Date());
            trace.setUserType(userType);
            iUserTraceService.save(trace);
        }
        // 新增行表
        userTraceInfo.setCreatedBy(username);
        userTraceInfo.setCreatedByIp(userIP);
        userTraceInfo.setCreationDate(new Date());
        userTraceInfo.setCreatedId(0L);
        userTraceInfo.setLastUpdateDate(new Date());
        this.save(userTraceInfo);
    }

    @Override
    @Transactional
    public void update(String username, String userIP) {
        // 获取最新的头表
        QueryWrapper<UserTrace> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USERNAME",username);
        queryWrapper.orderByDesc("TRACE_ID");
        IPage<UserTrace> userTracePage = new Page<>(1, 1);
        List<UserTrace>  userTraces = iUserTraceService.getBaseMapper().selectPage(userTracePage, queryWrapper).getRecords();
        if(CollectionUtils.isNotEmpty(userTraces)){
            UserTrace userTrace = userTraces.get(0);
            Long traceId = userTrace.getTraceId();
            // 查找用户登出时间为null的数据
            QueryWrapper<UserTraceInfo> wrapper = new QueryWrapper<>();
            wrapper.isNull("LOGOUT_DATE");
            wrapper.eq("TRACE_ID",traceId);
            wrapper.eq("LOG_IP",userIP);
            wrapper.orderByDesc("TRACE_INFO_ID");
            IPage<UserTraceInfo> userTracePage1 = new Page<>(1, 1);
            List<UserTraceInfo> userTraceInfoList = this.getBaseMapper().selectPage(userTracePage1, wrapper).getRecords();
            if(CollectionUtils.isNotEmpty(userTraceInfoList)){
                UserTraceInfo userTraceInfo = userTraceInfoList.get(0);
                // 登出时间
                Date date = new Date();
                double logoutTime = (double)date.getTime();
                userTraceInfo.setLogoutDate(date);
                // 计算单次会话时间
                double loginTime = (double)(userTraceInfo.getLoginDate().getTime());
                double sub = NumberUtil.sub(logoutTime, loginTime);
                double div = NumberUtil.div(sub, 3600000);
                double singleOnlineTime = NumberUtil.formatDoubleByScale(div, 4);
                userTraceInfo.setSingleOnlineTime(singleOnlineTime);
                // 设置当天累计时间
                double add = NumberUtil.add(userTrace.getCumulativeOnlineTime(),singleOnlineTime);
                userTrace.setCumulativeOnlineTime(add);
                userTraceInfo.setLastUpdateDate(new Date());
                this.updateById(userTraceInfo);
            }
            // 更新头表
            userTrace.setLastUpdateDate(new Date());
            iUserTraceService.updateById(userTrace);
        }
    }
}
