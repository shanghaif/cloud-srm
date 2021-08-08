package com.midea.cloud.srm.supcooperate.deliver.service;

import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
*  <pre>
 *  到货计划详情表 服务类
 * </pre>
*
* @author zhi1772778785@163.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27 14:42:31
 *  修改内容:
 * </pre>
*/
public interface IDeliverPlanDetailService extends IService<DeliverPlanDetail> {
    void getDeliverPlanLock(List<Long> ids);
    void getDeliverPlanStatus(List<Long> ids);
    DeliverPlanDetailDTO getDeliverPlanDetail(DeliverPlanDetail deliverPlanDetail);
    void MatchingOrder(DeliverPlanDTO deliverPlanDTO);

}
