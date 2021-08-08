package com.midea.cloud.srm.inq.inquiry.service;

import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaSourceDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaSource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
*  <pre>
 *  配额调整-关联寻源列表 服务类
 * </pre>
*
* @author zhi1772778785@163.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-08 16:57:09
 *  修改内容:
 * </pre>
*/
public interface IQuotaSourceService extends IService<QuotaSource> {
    List<QuotaSourceDTO> quotaSourceList(QuotaSource quotaSource);

}
