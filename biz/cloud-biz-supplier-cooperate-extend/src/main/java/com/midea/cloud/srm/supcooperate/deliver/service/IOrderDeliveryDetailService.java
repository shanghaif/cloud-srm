package com.midea.cloud.srm.supcooperate.deliver.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.OrderDeliveryDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.OrderDeliveryDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
*  <pre>
 *  到货计划明细表 服务类
 * </pre>
*
* @author zhi1772778785@163.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-28 13:59:09
 *  修改内容:
 * </pre>
*/
public interface IOrderDeliveryDetailService extends IService<OrderDeliveryDetail> {
    PageInfo<OrderDeliveryDetail> orderDeliveryDetailListPage(OrderDeliveryDetailDTO orderDeliveryDetailDTO);
    List<OrderDeliveryDetailDTO> orderDeliveryDetailListPageCopy(OrderDeliveryDetailDTO orderDeliveryDetailDTO);
    List<OrderDeliveryDetail> orderDeliveryDetailList(OrderDeliveryDetail orderDeliveryDetail);
    void getAuditNote(Long id);
    /**
     * 获取可以回写的匹配数量
     * @param orderDeliveryDetail
     * @return
     */
     BigDecimal getDeliveryQuantity(OrderDeliveryDetail orderDeliveryDetail);
}
