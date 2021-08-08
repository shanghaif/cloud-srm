package com.midea.cloud.srm.po.order.service;

import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;

import java.util.List;

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
}
