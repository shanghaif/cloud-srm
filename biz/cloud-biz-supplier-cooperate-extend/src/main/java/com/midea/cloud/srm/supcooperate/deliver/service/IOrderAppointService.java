package com.midea.cloud.srm.supcooperate.deliver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.OrderAppointDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.OrderAppoint;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;

import java.util.List;

/**
 * <pre>
 *  指定采购订单表 服务类
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改日期: 2020-08-28 13:59:09
 *  修改人:
 *  修改内容:
 * </pre>
 */
public interface IOrderAppointService extends IService<OrderAppoint> {
    PageInfo<OrderAppoint> orderAppointListPage(DeliverPlanDetail deliverPlanDetail);
    void addBatch(OrderAppointDTO orderAppointDTO);
    PageInfo<OrderDetailDTO> OrderDetailListpage(DeliverPlanDTO deliverPlan);
    List<OrderDetailDTO> OrderDetailList(DeliverPlan deliverPlan);
    void deleteBatch(List<Long> ids);

}
