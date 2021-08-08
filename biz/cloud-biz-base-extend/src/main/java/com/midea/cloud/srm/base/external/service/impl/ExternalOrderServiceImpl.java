package com.midea.cloud.srm.base.external.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.base.external.mapper.ExternalOrderMapper;
import com.midea.cloud.srm.base.external.service.IExternalInterfaceLogService;
import com.midea.cloud.srm.base.external.service.IExternalOrderService;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.external.entity.ExternalInterfaceLog;
import com.midea.cloud.srm.model.base.external.entity.ExternalOrder;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  外部订单表 服务实现类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-23 16:40:11
 *  修改内容:
 * </pre>
 */
@Service
public class ExternalOrderServiceImpl extends ServiceImpl<ExternalOrderMapper, ExternalOrder> implements IExternalOrderService {

    @Autowired
    IExternalInterfaceLogService iExternalInterfaceLogService;

    @Autowired
    IExternalOrderService iExternalOrderService;

    @Autowired
    RbacClient rbacClient;

    @Transactional
    @Override
    public void addOrUpdate(ExternalOrder sysExternalOrder) {
        // 记录接口调用
        ExternalInterfaceLog log = new ExternalInterfaceLog();
        log.setInterfaceName("外部接口门户传输订单信息");
        log.setRtype("Recieve");
        log.setContent(JSONObject.toJSONString(sysExternalOrder));
        log.setCreationDate(new Date());
        iExternalInterfaceLogService.save(log);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("RENT_ID", sysExternalOrder.getRentId());
        List<ExternalOrder> checkExternalOrders = iExternalOrderService.list(wrapper);
        if (CollectionUtils.isNotEmpty(checkExternalOrders)) {
            sysExternalOrder.setStartDate(checkExternalOrders.get(0).getStartDate());
            iExternalOrderService.update(sysExternalOrder, wrapper);
        } else {
            iExternalOrderService.save(sysExternalOrder);
            // 添加采购商主账号
            User user = new User().setUsername(sysExternalOrder.getUserAccount()) // 账号
                    .setNickname(sysExternalOrder.getUserName()) // 昵称
                    .setStartDate(sysExternalOrder.getStartDate()) // 生效时间
//                    .setEndDate(sysExternalOrder.getEndDate()) // 失效时间
                    .setPhone(sysExternalOrder.getMobileNumber()) // 电话
                    .setEmail(sysExternalOrder.getMailAddress()); // 邮箱
            rbacClient.registerBuyerMain(user, sysExternalOrder.getUrlAddress());
        }
    }

    @Override
    public Integer getAccountNum() {
        return rbacClient.getAccountNum();
    }
}
