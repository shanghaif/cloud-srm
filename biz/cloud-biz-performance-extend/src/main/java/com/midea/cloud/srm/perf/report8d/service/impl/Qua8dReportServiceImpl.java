package com.midea.cloud.srm.perf.report8d.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.report8d.dto.ExcelQua8dReportDto;
import com.midea.cloud.srm.model.perf.report8d.dto.Qua8dReportDTO;
import com.midea.cloud.srm.model.perf.report8d.entity.Qua8dProblem;
import com.midea.cloud.srm.model.perf.report8d.entity.Qua8dReport;
import com.midea.cloud.srm.perf.report8d.mapper.Qua8dReportMapper;
import com.midea.cloud.srm.perf.report8d.service.Qua8dProblemService;
import com.midea.cloud.srm.perf.report8d.service.Qua8dReportService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
* <pre>
 *  8D报告 服务实现类
 * </pre>
*
* @author chenjw90@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 31, 2021 5:47:57 PM
 *  修改内容:
 * </pre>
*/
@Service
public class Qua8dReportServiceImpl extends ServiceImpl<Qua8dReportMapper, Qua8dReport> implements Qua8dReportService {
    @Resource
    private FileCenterClient fileCenterClient;
    @Autowired
    private Qua8dProblemService qua8dProblemService;

    @Override
    public PageInfo<Qua8dReportDTO> listPage(Qua8dReport qua8dReport) {
        PageUtil.startPage(qua8dReport.getPageNum(), qua8dReport.getPageSize());
        List<Qua8dReport> qua8dReportList = getQua8dReports(qua8dReport);

        List<Qua8dReportDTO> qua8dReportDTOList = new ArrayList<Qua8dReportDTO>();
        Qua8dReportDTO qua8dReportDTO = null;
        if (null != qua8dReportList && qua8dReportList.size() > 0) {
            for (Qua8dReport r : qua8dReportList) {
                qua8dReportDTO = new Qua8dReportDTO();
                BeanUtils.copyProperties(r, qua8dReportDTO);
                qua8dReportDTOList.add(qua8dReportDTO);
            }
        }
        return new PageInfo<>(qua8dReportDTOList);
    }

    public List<Qua8dReport> getQua8dReports(Qua8dReport qua8dReport) {
        String vendorName=qua8dReport.getVendorName();
        Long reportId = qua8dReport.getReportId();
        String documentType = qua8dReport.getDocumentType();
        Date creationDate = qua8dReport.getCreationDate();

        if (!StringUtil.isEmpty(qua8dReport.getVendorName())) {
            vendorName = qua8dReport.getVendorName();
            qua8dReport.setVendorName(null);
        }

        if (null != qua8dReport.getReportId()) {
            reportId = qua8dReport.getReportId();
            qua8dReport.setReportId(null);
        }

        if (!StringUtil.isEmpty(qua8dReport.getDocumentType())) {
            documentType = qua8dReport.getDocumentType();
            qua8dReport.setDocumentType(null);
        }

        if (null != qua8dReport.getCreationDate()) {
            creationDate = qua8dReport.getCreationDate();
            qua8dReport.setCreationDate(null);
        }

        QueryWrapper<Qua8dReport> wrapper = new QueryWrapper<>(qua8dReport);

        if (!StringUtil.isEmpty(vendorName)){
            wrapper.eq("VENDOR_NAME", vendorName);
        }

        if (reportId != null){
            wrapper.eq("REPORT_ID", reportId);
        }

        if (!StringUtil.isEmpty(documentType)){
            wrapper.eq("DOCUMENT_TYPE", documentType);
        }

        if (null != creationDate) {
            wrapper.ge("CREATION_DATE", creationDate);
        }
        return this.list(wrapper);
    }

    @Override
    public Qua8dReport getReportById(Long reportId) {
        Assert.notNull(reportId, "参数:reportId,不能为空");
        Qua8dReport qua8dReport = this.getById(reportId);
        Assert.notNull(qua8dReport,"找不到8D报告详情,reportId="+reportId);
        List<Qua8dProblem> qua8dProblemList = qua8dProblemService.list(new QueryWrapper<>(new Qua8dProblem().setReportId(reportId)));
        qua8dReport.setQua8DProblemList(qua8dProblemList);
        return qua8dReport;
    }

    @Override
    @Transactional
    public Long modify(Qua8dReport qua8dReport) {
        Long reportId = qua8dReport.getReportId();
        this.updateById(qua8dReport);
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
        if (!CollectionUtils.isEmpty(qua8dReport.getFileUploads())) {
            fileCenterClient.bindingFileupload(qua8dReport.getFileUploads(), qua8dReport.getReportId());
        }
        return qua8dReport.getReportId();
    }

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查参数
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<ExcelQua8dReportDto> excelQua8dReportDtoList = EasyExcelUtil.readExcelWithModel(file, ExcelQua8dReportDto.class);
        // 检查导入数据是否正确
        AtomicBoolean errorFlag = new AtomicBoolean(true);
        // 导入数据校验
        List<Qua8dReport> qua8dReportList = chackImportParam(excelQua8dReportDtoList, errorFlag);

        if(errorFlag.get()){
            // 保存或者更新
            batchSaveOrUpdate(qua8dReportList);
        }else {
            // 有错误,上传错误文件
            Fileupload errorFileupload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload, qua8dReportList, Qua8dReport.class, file);
            return ImportStatus.importError(errorFileupload.getFileuploadId(),errorFileupload.getFileSourceName());
        }
        return ImportStatus.importSuccess();
    }

    /**
     * 检查导入参数
     * @param excelQua8dReportDtoList
     * @param errorFlag
     * @return 业务实体集合
     */
    public List<Qua8dReport> chackImportParam(List<ExcelQua8dReportDto> excelQua8dReportDtoList, AtomicBoolean errorFlag){
        /**
         * 省略校验过程,数据有错误则把  errorFlag.set(false);
         */
        List<Qua8dReport> qua8dReportList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(excelQua8dReportDtoList)){
            excelQua8dReportDtoList.forEach(qua8dReportDto -> {
                Qua8dReport qua8dReport = new Qua8dReport();
                StringBuffer errorMsg = new StringBuffer();
                BeanCopyUtil.copyProperties(qua8dReport,qua8dReportDto);
                // 检查示例: noticeId 非空
//                Long noticeId = quaSupplierEnoticeDto.getNoticeId();
//                if(ObjectUtils.isEmpty(noticeId)){
//                    errorMsg.append("noticeId不能为空;");
//                    errorFlag.set(false);
//                }else {
//                    quaSupplierEnotice.setNoticeId(noticeId);
//                }
//
//                // .......
//                if(errorMsg.length() > 0){
//                    quaSupplierEnoticeDto.setErrorMsg(errorMsg.toString());
//                }else {
//                    quaSupplierEnotices.add(quaSupplierEnotice);
//                }
                qua8dReportList.add(qua8dReport);
            });
        }
        return qua8dReportList;
    }

    @Override
        public void exportExcel(Qua8dReport excelParam, HttpServletResponse response) throws IOException {
        // 查询数据
        List<Qua8dReport> qua8dReports = getQua8dReports(excelParam);
        List<ExcelQua8dReportDto> excelQua8dReportDto = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(qua8dReports)){
            qua8dReports.forEach(qua8dReport -> {
                ExcelQua8dReportDto qua8dReportDto = new ExcelQua8dReportDto();
                BeanCopyUtil.copyProperties(qua8dReportDto,qua8dReport);
                excelQua8dReportDto.add(qua8dReportDto);
            });
        }
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(ExcelQua8dReportDto.class).sheet(0).sheetName("sheetName").doWrite(excelQua8dReportDto);

    }

    @Transactional
    public void batchUpdate(List<Qua8dReport> qua8dReportList) {
        this.saveOrUpdateBatch(qua8dReportList);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<Qua8dReport> qua8dReportList) throws IOException {
        for(Qua8dReport qua8dReport : qua8dReportList){
            if(qua8dReport.getReportId() == null){
                Long id = IdGenrator.generate();
                qua8dReport.setReportId(id);
            }
        }
        if(!CollectionUtils.isEmpty(qua8dReportList)) {
            batchUpdate(qua8dReportList);
        }
    }

}
