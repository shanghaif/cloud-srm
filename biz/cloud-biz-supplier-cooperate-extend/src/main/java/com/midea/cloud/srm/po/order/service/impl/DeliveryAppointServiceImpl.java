package com.midea.cloud.srm.po.order.service.impl;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;
import com.midea.cloud.srm.po.order.service.IDeliveryAppointService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <pre>
 *  送货预约表 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/28 13:58
 *  修改内容:
 * </pre>
 */
@Service
public class DeliveryAppointServiceImpl implements IDeliveryAppointService {

    @Autowired
    private com.midea.cloud.srm.supcooperate.order.service.IDeliveryAppointService scIDeliveryAppointService;

    /**
     * 批量确认送货预约单
     * @param ids
     */
    @Override
    public void confirmBatch(List<Long> ids) {
        scIDeliveryAppointService.confirmBatch(ids);
    }

    /**
     * 批量拒绝送货单
     * @param requestDTO
     */
    @Override
    public void refuseBatch(DeliveryAppointRequestDTO requestDTO) {
        scIDeliveryAppointService.refuseBatch(requestDTO);
    }

    /**
     * 分页查询列表
     * @param deliveryAppointRequestDTO
     * @return
     */
    @Override
    public PageInfo<DeliveryAppoint> listPage(DeliveryAppointRequestDTO deliveryAppointRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            deliveryAppointRequestDTO.setVendorId(loginAppUser.getCompanyId());
        }
        PageUtil.startPage(deliveryAppointRequestDTO.getPageNum(), deliveryAppointRequestDTO.getPageSize());
        return new PageInfo(scIDeliveryAppointService.listPage(deliveryAppointRequestDTO));
    }
}
