package com.midea.cloud.srm.sup.statuslog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.statuslog.entity.CompanyStatusLog;
import com.midea.cloud.srm.sup.statuslog.mapper.CompanyStatusLogMapper;
import com.midea.cloud.srm.sup.statuslog.service.ICompanyStatusLogService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  企业状态历史表 服务实现类
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
@Service
public class CompanyStatusLogServiceImpl extends ServiceImpl<CompanyStatusLogMapper, CompanyStatusLog> implements ICompanyStatusLogService {

    @Override
    public void saveStatusLog(Long companyId,
                              Long operatorId,
                              String operatorName,
                              String operatorType,
                              String operationStatus,
                              String operationMemo,
                              Date operationDate,
                              String operationType) {
        CompanyStatusLog companyStatusLog =  new CompanyStatusLog();
        Long id = IdGenrator.generate();
        companyStatusLog.setStatusLogId(id);
        companyStatusLog.setCompanyId(companyId);
        companyStatusLog.setOperatorId(operatorId);
        companyStatusLog.setOperatorName(operatorName);
        companyStatusLog.setOperatorType(operatorType);
        companyStatusLog.setOperationStatus(operationStatus);
        companyStatusLog.setOperationMemo(operationMemo);
        companyStatusLog.setOperationDate(operationDate);
        companyStatusLog.setOperationType(operationType);
        this.save(companyStatusLog);
    }

    @Override
    public List<CompanyStatusLog> listAllByParam(CompanyStatusLog companyStatusLog) {
        QueryWrapper<CompanyStatusLog> wrapper = new QueryWrapper<CompanyStatusLog>(companyStatusLog);
        wrapper.orderByAsc("OPERATION_DATE");
        return companyStatusLog != null ? this.list(wrapper): null;
    }
}
