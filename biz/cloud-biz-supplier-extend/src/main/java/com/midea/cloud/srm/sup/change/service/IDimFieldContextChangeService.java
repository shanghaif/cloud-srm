package com.midea.cloud.srm.sup.change.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.change.entity.DimFieldContextChange;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  维度字段内容变更表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-26 15:19:45
 *  修改内容:
 * </pre>
*/
public interface IDimFieldContextChangeService extends IService<DimFieldContextChange> {
    List<DimFieldContextChange> saveOrUpdateList(Map<String, Object> dimFieldContexts,
                                                 Long ChangeOrderId,
                                                 Long orderId,
                                                 Long companyId,
                                                 Long changeId);

    Map<String, Object> findByOrderId(Long changeOrderId);

    void deleteByChangeId(Long changeId);

}
