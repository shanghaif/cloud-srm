package com.midea.cloud.srm.inq.inquiry.service;

import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaRebate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
*  <pre>
 *  配额-预估返利 服务类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:41:44
 *  修改内容:
 * </pre>
*/
public interface IQuotaRebateService extends IService<QuotaRebate> {
    List<QuotaRebate> quotaRebateList(QuotaRebate quotaRebate);
    void quotaRebateAdd(List<QuotaRebate> quotaRebateList);

}
