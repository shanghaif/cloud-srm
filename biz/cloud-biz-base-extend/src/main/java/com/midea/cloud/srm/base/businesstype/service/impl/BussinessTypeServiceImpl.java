package com.midea.cloud.srm.base.businesstype.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.base.businesstype.mapper.BussinessTypeMapper;
import com.midea.cloud.srm.base.businesstype.service.IBussinessTypeService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.businesstype.dto.BussinessTypeImportDto;
import com.midea.cloud.srm.model.base.businesstype.dto.BussinessTypeImportModelDto;
import com.midea.cloud.srm.model.base.businesstype.entity.BussinessType;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  <pre>
 *  业务类型配置表 服务实现类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-21 14:57:34
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class BussinessTypeServiceImpl extends ServiceImpl<BussinessTypeMapper, BussinessType> implements IBussinessTypeService {

    @Override
    public Long saveOrUpdateBussinessType(BussinessType bussinessType) {
        if (Objects.isNull(bussinessType.getBussinessTypeId())) {
            this.save(bussinessType.setBussinessTypeId(IdGenrator.generate()));
        }else {
            this.updateById(bussinessType);
        }
        return bussinessType.getBussinessTypeId();
    }

    @Override
    public PageInfo<BussinessType> listPageByParam(BussinessType bussinessType) {
        PageUtil.startPage(bussinessType.getPageNum(), bussinessType.getPageSize());
        String vendorSiteCode = bussinessType.getVendorSiteCode();
        if (!StringUtil.isEmpty(bussinessType.getVendorSiteCode())){
            vendorSiteCode = bussinessType.getVendorSiteCode();
            bussinessType.setVendorSiteCode(null);
        }
        QueryWrapper<BussinessType> queryWrapper = new QueryWrapper<>(bussinessType);
        if (!StringUtil.isEmpty(vendorSiteCode)){
            queryWrapper.eq("VENDOR_SITE_CODE",vendorSiteCode);
        }
        queryWrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<>(this.list(queryWrapper));
    }

    @Resource
    private BaseClient baseClient;
    @Resource
    private FileCenterClient fileCenterClient;

    @Override
    public void importModelDownload(HttpServletResponse response) throws IOException {
        String fileName = "业务类型配置信息导入模板";
        ArrayList<BussinessTypeImportModelDto> bussinessTypeImportModelDtos = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream,fileName,bussinessTypeImportModelDtos,BussinessTypeImportModelDto.class);
    }

    @Override
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 文件校验
        EasyExcelUtil.checkParam(file,fileupload);
        // 读取excel数据
        List<BussinessTypeImportDto> bussinessTypeImportDtos = readData(file);
        // 保存的数据
        List<BussinessType> bussinessTypes = new ArrayList<>();
        // 错误标识
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 校验数据
        checkData(bussinessTypeImportDtos, bussinessTypes, errorFlag);
        if(errorFlag.get()){
            // 有报错
            fileupload.setFileSourceName("业务类型配置信息导入报错");
            Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    bussinessTypeImportDtos, BussinessTypeImportDto.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            return ImportStatus.importError(fileupload1.getFileuploadId(),fileupload1.getFileSourceName());
        }else {
            if(CollectionUtils.isNotEmpty(bussinessTypes)){
                bussinessTypes.forEach(bussinessType -> bussinessType.setBussinessTypeId(IdGenrator.generate()));
                this.saveBatch(bussinessTypes);
            }
        }
        return ImportStatus.importSuccess();
    }

    private void checkData(List<BussinessTypeImportDto> bussinessTypeImportDtos, List<BussinessType> bussinessTypes, AtomicBoolean errorFlag) {
        if(CollectionUtils.isNotEmpty(bussinessTypeImportDtos)){
            // 事业部字典数据
            Map<String, String> divisionMap = EasyExcelUtil.getDicNameCode("DIVISION",baseClient);
            // 供应商地点名称
            Map<String, String> vendorSiteCodeMap = EasyExcelUtil.getDicNameCode("VENDOR_SITE_CODE",baseClient);
            // 单据类型
            Map<String, String> paymentDocumentTypeMap = EasyExcelUtil.getDicNameCode("PAYMENT_DOCUMENT_TYPE",baseClient);
            // 业务类型
            Map<String, String> businessTypeMap = EasyExcelUtil.getDicNameCode("BUSINESS_TYPE",baseClient);
            AtomicInteger integer = new AtomicInteger(1);
            bussinessTypeImportDtos.forEach(bussinessTypeImportDto -> {
                log.info("第"+integer.get()+"次循环");
                integer.addAndGet(1);
                BussinessType bussinessType = new BussinessType();
                StringBuffer errorMsg = new StringBuffer();
                // 事业部
                String division = bussinessTypeImportDto.getDivision();
                if(StringUtil.notEmpty(division)){
                    division = division.trim();
                    String code = divisionMap.get(division);
                    if(StringUtil.notEmpty(code)){
                        bussinessType.setDivision(division);
                        bussinessType.setDivisionId(code);
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("事业部字典值不存在; ");
                    }
                }else {
                    errorFlag.set(true);
                    errorMsg.append("事业部不能为空; ");
                }

                // 供应商地点名称
                String vendorSiteCode = bussinessTypeImportDto.getVendorSiteCode();
                if(StringUtil.notEmpty(vendorSiteCode)){
                    vendorSiteCode = vendorSiteCode.trim();
                    String code = vendorSiteCodeMap.get(vendorSiteCode);
                    if(StringUtil.notEmpty(code)){
                        bussinessType.setVendorSiteCode(vendorSiteCode);
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("供应商地点字典值不存在; ");
                    }
                }else {
                    errorFlag.set(true);
                    errorMsg.append("供应商地点不能为空; ");
                }

                // 单据类型
                String paymentDocumentType = bussinessTypeImportDto.getPaymentDocumentType();
                if(StringUtil.notEmpty(paymentDocumentType)){
                    paymentDocumentType = paymentDocumentType.trim();
                    String code = paymentDocumentTypeMap.get(paymentDocumentType);
                    if(StringUtil.notEmpty(code)){
                        bussinessType.setPaymentDocumentType(code);
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("单据类型字典值不存在; ");
                    }
                }else {
                    errorFlag.set(true);
                    errorMsg.append("单据类型不能为空; ");
                }

                // 业务类型名称
                String businessTypeName = bussinessTypeImportDto.getBusinessTypeName();
                if(StringUtil.notEmpty(businessTypeName)){
                    businessTypeName = businessTypeName.trim();
                    String code = businessTypeMap.get(businessTypeName);
                    if (StringUtil.notEmpty(code)) {
                        bussinessType.setBusinessTypeName(businessTypeName);
                        bussinessType.setBusinessType(code);
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("业务类型字典值不存在; ");
                    }
                }else {
                    errorFlag.set(true);
                    errorMsg.append("业务类型名称不能为空; ");
                }

                // 是否代付
                String ifPayAgent = bussinessTypeImportDto.getIfPayAgent();
                if(StringUtil.notEmpty(ifPayAgent)){
                    ifPayAgent = ifPayAgent.trim();
                    if(YesOrNo.YES.getValue().equals(ifPayAgent) || YesOrNo.NO.getValue().equals(ifPayAgent)){
                        bussinessType.setIfPayAgent(ifPayAgent);
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("是否代付只能填\"Y\"或\"N\"; ");
                    }
                }else {
                    errorFlag.set(true);
                    errorMsg.append("是否代付不能为空; ");
                }

                // 备注
                String remarks = bussinessTypeImportDto.getRemarks();
                if(StringUtil.notEmpty(remarks)){
                    remarks = remarks.trim();
                    bussinessType.setRemarks(remarks);
                }

                if(errorMsg.length() > 0){
                    bussinessTypeImportDto.setErrorMsg(errorMsg.toString());
                }else {
                    bussinessTypeImportDto.setErrorMsg(null);
                }

                // 默认值
                bussinessType.setStartDate(LocalDate.now());

                bussinessTypes.add(bussinessType);
            });

        }
    }

    private List<BussinessTypeImportDto> readData(MultipartFile file) {
        List<BussinessTypeImportDto> bussinessTypeImportDtos = new ArrayList<>();
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<BussinessTypeImportDto> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream,listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(BussinessTypeImportDto.class).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            bussinessTypeImportDtos = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return bussinessTypeImportDtos;
    }
}
