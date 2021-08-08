package com.midea.cloud.srm.sup.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.info.entity.FinanceInfo;

import java.util.List;

/**
*  <pre>
 *  财务信息 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 16:06:05
 *  修改内容:
 * </pre>
*/
public interface IFinanceInfoService extends IService<FinanceInfo> {

    void saveOrUpdateFinace(FinanceInfo financeInfo, Long companyId);

    List<FinanceInfo> getByCompanyId(Long companyId);

    void removeByCompanyId(Long companyId);

    FinanceInfo getByCompanyIdAndOrgId(Long companyId, Long orgId);
}
