package com.midea.cloud.srm.po.order.service;

import java.util.List;

import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;

/**
 * <pre>
 *  送货通知单表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/19 10:41
 *  修改内容:
 * </pre>
 */
public interface IDeliveryNoticeService {
    void releasedBatch(List<DeliveryNotice> ids);

    void importExcelInfo(List<Object> voList);

    /**
     * 新增送货通知单
     * @param deliveryNoticeList
     */
    void add(List<DeliveryNotice> deliveryNoticeList);

    /**
     * 修改送货通知单
     * @param deliveryNoticeList
     */
    void batchUpdate(List<DeliveryNotice> deliveryNoticeList);

    /**
     * 批量删除送货单
     * @param ids
     */
    void batchDelete(List<Long> ids);
}
