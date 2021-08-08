package com.midea.cloud.srm.po.order.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.AppointDeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppointVisitor;
import com.midea.cloud.srm.po.order.service.IDeliveryAppointService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/2/4 20:13
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/po/deliveryAppoint")
public class DeliveryAppointController extends BaseController {
    @Autowired
    private IDeliveryAppointService iDeliveryAppointService;

    /**
     * 批量确认送货预约单
     * @param ids
     */
    @PostMapping("/confirmBatch")
    public void confirmBatch(@RequestBody List<Long> ids) {
        iDeliveryAppointService.confirmBatch(ids);
    }


    /**
     * 批量拒绝送货单
     * @param requestDTO
     */
    @PostMapping("/refuseBatch")
    public void refuseBatch(@RequestBody DeliveryAppointRequestDTO requestDTO) {
        iDeliveryAppointService.refuseBatch(requestDTO);
    }

    /**
     * 分页查询列表
     * @param deliveryAppointRequestDTO
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<DeliveryAppoint> listPage(@RequestBody DeliveryAppointRequestDTO deliveryAppointRequestDTO) {
        return iDeliveryAppointService.listPage(deliveryAppointRequestDTO);
    }


}
