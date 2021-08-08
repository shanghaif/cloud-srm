package com.midea.cloud.srm.sup.statuslog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.statuslog.entity.CompanyStatusLog;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  企业状态历史表 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-13 17:43:49
 *  修改内容:
 * </pre>
*/
public interface ICompanyStatusLogService extends IService<CompanyStatusLog> {
    void saveStatusLog(Long companyId,
                       Long operatorId,
                       String operatorName,
                       String operatorType,
                       String operationStatus,
                       String operationMemo,
                       Date operationDate,
                       String operationType
                       );

    List<CompanyStatusLog> listAllByParam(CompanyStatusLog companyStatusLog);
}
