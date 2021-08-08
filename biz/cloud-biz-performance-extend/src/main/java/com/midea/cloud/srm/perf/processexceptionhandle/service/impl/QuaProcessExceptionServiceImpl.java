package com.midea.cloud.srm.perf.processexceptionhandle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.model.perf.processexceptionhandle.dto.QuaProcessExceptionDTO;
import com.midea.cloud.srm.model.perf.processexceptionhandle.entity.QuaProcessException;
import com.midea.cloud.srm.model.perf.report8d.entity.Qua8dProblem;
import com.midea.cloud.srm.model.perf.report8d.entity.Qua8dReport;
import com.midea.cloud.srm.perf.processexceptionhandle.mapper.QuaProcessExceptionMapper;
import com.midea.cloud.srm.perf.processexceptionhandle.service.QuaProcessExceptionService;
import com.midea.cloud.srm.perf.report8d.service.Qua8dProblemService;
import com.midea.cloud.srm.perf.report8d.service.Qua8dReportService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
* <pre>
 *  制程异常处理单 服务实现类
 * </pre>
*
* @author chenjw90@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 31, 2021 5:45:00 PM
 *  修改内容:
 * </pre>
*/
@Service
public class QuaProcessExceptionServiceImpl extends ServiceImpl<QuaProcessExceptionMapper, QuaProcessException> implements QuaProcessExceptionService {
    @Autowired
    private Qua8dReportService qua8dReportService;
    @Autowired
    private Qua8dProblemService qua8dProblemService;

    @Override
    public PageInfo<QuaProcessExceptionDTO> listPage(QuaProcessException quaProcessException) {
        PageUtil.startPage(quaProcessException.getPageNum(),quaProcessException.getPageSize());
        List<QuaProcessException> quaProcessExceptionList = getQuaProcessExceptions(quaProcessException);

        List<QuaProcessExceptionDTO> quaProcessExceptionDTOList = new ArrayList<QuaProcessExceptionDTO>();
        QuaProcessExceptionDTO quaProcessExceptionDTO = null;
        if (null != quaProcessExceptionList && quaProcessExceptionList.size() > 0) {
            for (QuaProcessException p : quaProcessExceptionList) {
                quaProcessExceptionDTO = new QuaProcessExceptionDTO();
                BeanUtils.copyProperties(p, quaProcessExceptionDTO);
                quaProcessExceptionDTOList.add(quaProcessExceptionDTO);
            }
        }
        return new PageInfo<>(quaProcessExceptionDTOList);
    }

    public List<QuaProcessException> getQuaProcessExceptions(QuaProcessException quaProcessException) {
        String vendorName=quaProcessException.getVendorName();
        Long billCode = quaProcessException.getBillCode();
        String report8D = quaProcessException.getReport8D();
        Date creationDate = quaProcessException.getCreationDate();

        if (!StringUtil.isEmpty(quaProcessException.getVendorName())) {
            vendorName = quaProcessException.getVendorName();
            quaProcessException.setVendorName(null);
        }

        if (null != quaProcessException.getBillCode()) {
            billCode = quaProcessException.getBillCode();
            quaProcessException.setBillCode(null);
        }

        if (!StringUtil.isEmpty(quaProcessException.getReport8D())) {
            report8D = quaProcessException.getReport8D();
            quaProcessException.setReport8D(null);
        }

        if (null != quaProcessException.getCreationDate()) {
            creationDate = quaProcessException.getCreationDate();
            quaProcessException.setCreationDate(null);
        }

        QueryWrapper<QuaProcessException> wrapper = new QueryWrapper<>(quaProcessException);

        if (!StringUtil.isEmpty(vendorName)){
            wrapper.eq("VENDOR_NAME", vendorName);
        }

        if (billCode != null){
            wrapper.eq("BILL_CODE", billCode);
        }

        if (!StringUtil.isEmpty(report8D)){
            wrapper.eq("REPORT_8D", report8D);
        }

        if (null != creationDate) {
            wrapper.ge("CREATION_DATE", creationDate);
        }

        return this.list(wrapper);

    }

    @Override
    @Transactional
    public Long add8D(Qua8dReport qua8dReport) {
        Long reportId = qua8dReport.getReportId();
        qua8dReport.setReportId(IdGenrator.generate());
        qua8dReportService.save(qua8dReport);
        // 保存问题描述行
        qua8dProblemService.remove(new QueryWrapper<>(new Qua8dProblem().setReportId(reportId)));
        List<Qua8dProblem> qua8dProblemList = qua8dReport.getQua8DProblemList();
        if(CollectionUtils.isNotEmpty(qua8dProblemList)){
            qua8dProblemList.forEach(qua8dProblem -> {
                qua8dProblem.setReportId(qua8dReport.getReportId());
                qua8dProblem.setProblemId(IdGenrator.generate());
            });
            qua8dProblemService.saveBatch(qua8dProblemList);
        }
        return qua8dReport.getReportId();
    }
}
