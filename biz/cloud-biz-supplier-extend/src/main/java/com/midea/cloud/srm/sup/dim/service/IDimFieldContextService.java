package com.midea.cloud.srm.sup.dim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.dim.entity.DimFieldContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  维度字段内容表 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 15:17:08
 *  修改内容:
 * </pre>
*/
public interface IDimFieldContextService extends IService<DimFieldContext> {
    void saveOrUpdateList(Map<String,Object> dimFieldContexts,Long orderID,Long companyId);
    Map<String,Object> findByOrderId(Long orderId);
    void deleteByCompanyId(Long companyId);
}
