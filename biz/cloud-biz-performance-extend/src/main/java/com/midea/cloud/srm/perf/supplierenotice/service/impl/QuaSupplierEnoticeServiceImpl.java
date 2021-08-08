package com.midea.cloud.srm.perf.supplierenotice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.supplierenotice.dto.ExcelQuaSupplierEnoticeDto;
import com.midea.cloud.srm.model.perf.supplierenotice.dto.QuaSupplierEnoticeDTO;
import com.midea.cloud.srm.model.perf.supplierenotice.entity.QuaSupplierEnotice;
import com.midea.cloud.srm.perf.supplierenotice.mapper.QuaSupplierEnoticeMapper;
import com.midea.cloud.srm.perf.supplierenotice.service.QuaSupplierEnoticeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
 *  21 服务实现类
 * </pre>
*
* @author wengzc@media.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Feb 1, 2021 5:12:43 PM
 *  修改内容:
 * </pre>
*/
@Service
public class QuaSupplierEnoticeServiceImpl extends ServiceImpl<QuaSupplierEnoticeMapper, QuaSupplierEnotice> implements QuaSupplierEnoticeService {
    @Resource
    private FileCenterClient fileCenterClient;

    @Transactional
    public void batchUpdate(List<QuaSupplierEnotice> quaSupplierEnoticeList) {
        this.saveOrUpdateBatch(quaSupplierEnoticeList);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<QuaSupplierEnotice> quaSupplierEnoticeList) throws IOException {
        for(QuaSupplierEnotice quaSupplierEnotice : quaSupplierEnoticeList){
            if(quaSupplierEnotice.getNoticeId() == null){
                Long id = IdGenrator.generate();
                quaSupplierEnotice.setNoticeId(id);
            }
        }
        if(!CollectionUtils.isEmpty(quaSupplierEnoticeList)) {
            batchUpdate(quaSupplierEnoticeList);
        }
    }

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查参数
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<ExcelQuaSupplierEnoticeDto> quaSupplierEnoticeDtos = EasyExcelUtil.readExcelWithModel(file, ExcelQuaSupplierEnoticeDto.class);
        // 检查导入数据是否正确
        AtomicBoolean errorFlag = new AtomicBoolean(true);
        // 导入数据校验
        List<QuaSupplierEnotice> quaSupplierEnotices = chackImportParam(quaSupplierEnoticeDtos, errorFlag);

        if(errorFlag.get()){
            // 保存或者更新
            batchSaveOrUpdate(quaSupplierEnotices);
        }else {
            // 有错误,上传错误文件
            Fileupload errorFileupload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload, quaSupplierEnotices, QuaSupplierEnotice.class, file);
            return ImportStatus.importError(errorFileupload.getFileuploadId(),errorFileupload.getFileSourceName());
        }
        return ImportStatus.importSuccess();
    }
    /**
     * 检查导入参数
     * @param excelQuaSupplierEnoticeDto
     * @param errorFlag
     * @return 业务实体集合
     */
    public List<QuaSupplierEnotice> chackImportParam(List<ExcelQuaSupplierEnoticeDto> excelQuaSupplierEnoticeDto, AtomicBoolean errorFlag){
        /**
         * 省略校验过程,数据有错误则把  errorFlag.set(false);
         */
        List<QuaSupplierEnotice> quaSupplierEnotices = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(excelQuaSupplierEnoticeDto)){
            excelQuaSupplierEnoticeDto.forEach(quaSupplierEnoticeDto -> {
                QuaSupplierEnotice quaSupplierEnotice = new QuaSupplierEnotice();
                StringBuffer errorMsg = new StringBuffer();
                BeanCopyUtil.copyProperties(quaSupplierEnotice,quaSupplierEnoticeDto);
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
                quaSupplierEnotices.add(quaSupplierEnotice);
            });
        }
        return quaSupplierEnotices;
    }

    @Override
    public void exportExcel(QuaSupplierEnotice excelParam, HttpServletResponse response) throws IOException {
        // 查询数据
        List<QuaSupplierEnotice> quaSupplierEnotices = getQuaSupplierEnotices(excelParam);
        List<ExcelQuaSupplierEnoticeDto> excelQuaSupplierEnoticeDto = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(quaSupplierEnotices)){
            quaSupplierEnotices.forEach(quaSupplierEnotice -> {
                ExcelQuaSupplierEnoticeDto quaSupplierEnoticeDto = new ExcelQuaSupplierEnoticeDto();
                BeanCopyUtil.copyProperties(quaSupplierEnoticeDto,quaSupplierEnotice);
                excelQuaSupplierEnoticeDto.add(quaSupplierEnoticeDto);
            });
        }
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(ExcelQuaSupplierEnoticeDto.class).sheet(0).sheetName("sheetName").doWrite(excelQuaSupplierEnoticeDto);

    }

    @Override
    public PageInfo<QuaSupplierEnoticeDTO> listPage(QuaSupplierEnotice quaSupplierEnotice) {
        PageUtil.startPage(quaSupplierEnotice.getPageNum(), quaSupplierEnotice.getPageSize());

        List<QuaSupplierEnotice> quaSupplierEnotices = getQuaSupplierEnotices(quaSupplierEnotice);

        List<QuaSupplierEnoticeDTO> quaSupplierEnoticeDTOList = new ArrayList<QuaSupplierEnoticeDTO>();
        QuaSupplierEnoticeDTO quaSupplierEnoticeDTO = null;
        if (null != quaSupplierEnotices && quaSupplierEnotices.size() > 0) {
            for (QuaSupplierEnotice s : quaSupplierEnotices) {
                quaSupplierEnoticeDTO = new QuaSupplierEnoticeDTO();
                BeanUtils.copyProperties(s, quaSupplierEnoticeDTO);
                quaSupplierEnoticeDTOList.add(quaSupplierEnoticeDTO);
            }
        }
        return new PageInfo<>(quaSupplierEnoticeDTOList);
    }

    public List<QuaSupplierEnotice> getQuaSupplierEnotices(QuaSupplierEnotice quaSupplierEnotice) {
        String vendorName=quaSupplierEnotice.getVendorName();
        Long noticeId = quaSupplierEnotice.getNoticeId();
        String orgName = quaSupplierEnotice.getOrgName();
        Date creationDate = quaSupplierEnotice.getCreationDate();

        if (!StringUtil.isEmpty(quaSupplierEnotice.getVendorName())) {
            vendorName = quaSupplierEnotice.getVendorName();
            quaSupplierEnotice.setVendorName(null);
        }

        if (null != quaSupplierEnotice.getNoticeId()) {
            noticeId = quaSupplierEnotice.getNoticeId();
            quaSupplierEnotice.setNoticeId(null);
        }

        if (!StringUtil.isEmpty(quaSupplierEnotice.getOrgName())) {
            orgName = quaSupplierEnotice.getOrgName();
            quaSupplierEnotice.setOrgName(null);
        }

        if (null != quaSupplierEnotice.getCreationDate()) {
            creationDate = quaSupplierEnotice.getCreationDate();
            quaSupplierEnotice.setCreationDate(null);
        }

        QueryWrapper<QuaSupplierEnotice> wrapper = new QueryWrapper<>(quaSupplierEnotice);

        if (!StringUtil.isEmpty(vendorName)){
            wrapper.eq("VENDOR_NAME", vendorName);
        }

        if (noticeId != null){
            wrapper.eq("NOTICE_ID", noticeId);
        }

        if (!StringUtil.isEmpty(orgName)){
            wrapper.eq("ORG_NAME", orgName);
        }

        if (null != creationDate) {
            wrapper.ge("CREATION_DATE", creationDate);
        }

        return this.list(wrapper);
    }

    @Override
    public void add(QuaSupplierEnotice quaSupplierEnotice) {
        Long id = IdGenrator.generate();
        quaSupplierEnotice.setNoticeId(id);
        this.save(quaSupplierEnotice);
        if (!CollectionUtils.isEmpty(quaSupplierEnotice.getFileUploads())) {
            fileCenterClient.bindingFileupload(quaSupplierEnotice.getFileUploads(), quaSupplierEnotice.getNoticeId());
        }
    }
}
