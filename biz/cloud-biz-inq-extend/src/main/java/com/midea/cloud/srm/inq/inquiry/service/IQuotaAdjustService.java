package com.midea.cloud.srm.inq.inquiry.service;

import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaAdjustDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaAdjust;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
*  <pre>
 *  配额调整 服务类
 * </pre>
*
* @author zhi1772778785@163.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-08 14:01:49
 *  修改内容:
 * </pre>
*/
public interface IQuotaAdjustService extends IService<QuotaAdjust> {
    List<QuotaAdjust> quotaAdjustList(QuotaAdjustDTO quotaAdjustDTO);
    void quotaAdjustAdd(QuotaAdjustDTO quotaAdjustDTO);
    QuotaAdjustDTO  getQuotaAdjust(Long id);
    void deleteQuotaAdjust(Long id);
    void getApprove(Long id);

}
