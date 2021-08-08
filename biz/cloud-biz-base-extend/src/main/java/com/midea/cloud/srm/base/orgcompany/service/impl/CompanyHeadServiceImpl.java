package com.midea.cloud.srm.base.orgcompany.service.impl;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.common.utils.WordUtils;
import com.midea.cloud.srm.base.dept.service.ICompaniesService;
import com.midea.cloud.srm.base.organization.service.IErpBranchBankService;
import com.midea.cloud.srm.base.organization.service.IOrganizationService;
import com.midea.cloud.srm.base.orgcompany.mapper.CompanyHeadMapper;
import com.midea.cloud.srm.base.orgcompany.service.ICompanyAddressService;
import com.midea.cloud.srm.base.orgcompany.service.ICompanyBankService;
import com.midea.cloud.srm.base.orgcompany.service.ICompanyHeadService;
import com.midea.cloud.srm.base.orgcompany.service.ICompanyPersonService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.dept.entity.Companies;
import com.midea.cloud.srm.model.base.organization.entity.ErpBranchBank;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.orgcompany.dto.CompanyAddressDto;
import com.midea.cloud.srm.model.base.orgcompany.dto.CompanyBankDto;
import com.midea.cloud.srm.model.base.orgcompany.dto.CompanyPersonDto;
import com.midea.cloud.srm.model.base.orgcompany.entity.CompanyAddress;
import com.midea.cloud.srm.model.base.orgcompany.entity.CompanyBank;
import com.midea.cloud.srm.model.base.orgcompany.entity.CompanyHead;
import com.midea.cloud.srm.model.base.orgcompany.entity.CompanyPerson;
import com.midea.cloud.srm.model.cm.contract.entity.ContractPartner;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
*  <pre>
 *  组织-公司头表 服务实现类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-14 14:32:08
 *  修改内容:
 * </pre>
*/
@Service
public class CompanyHeadServiceImpl extends ServiceImpl<CompanyHeadMapper, CompanyHead> implements ICompanyHeadService {
    @Resource
    private ICompanyPersonService iCompanyPersonService;
    @Resource
    private ICompanyAddressService iCompanyAddressService;
    @Resource
    private ICompanyBankService iCompanyBankService;
    @Resource
    private IOrganizationService iOrganizationService;
    @Resource
    private ICompaniesService iCompaniesService;
    @Resource
    private FileCenterClient fileCenterClient;
    @Resource
    private IErpBranchBankService iErpBranchBankService;

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 文件校验
        EasyExcelUtil.checkParam(file,fileupload);
        AnalysisEventListenerImpl<Object> listener = new AnalysisEventListenerImpl<>();
        ExcelReader excelReader = EasyExcel.read(file.getInputStream(), listener).build();
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 第一个sheet读取类型
        ReadSheet readSheet1 = EasyExcel.readSheet(0).head(CompanyPersonDto.class).build();
        excelReader.read(readSheet1);
        // 新增的公司头
        Map<String, CompanyHead> newCompanyHead = new HashMap<>();
        // 更新的公司头
        List<CompanyHead> companyHeadsUpdates = new ArrayList<>();
        // 联系人信息
        List<Object> companyPersonDtoList = listener.getDatas();
        List<CompanyPersonDto> companyPersonDtos = new ArrayList<>();
        List<CompanyPerson> companyPeople = new ArrayList<>();
        // 获取联系人信息
        getPersonData(errorFlag, newCompanyHead, companyPersonDtoList, companyPersonDtos, companyPeople,companyHeadsUpdates);
        // 清楚数据
        listener.getDatas().clear();

        // 第二个sheet读取类型
        ReadSheet readSheet2 = EasyExcel.readSheet(1).head(CompanyBankDto.class).build();
        excelReader.read(readSheet2);
        List<Object> companyBankDtoList = listener.getDatas();
        List<CompanyBankDto> companyBankDtos = new ArrayList<>();
        List<CompanyBank> companyBanks = new ArrayList<>();
        // 获取银行账户信息
        getCompsnyBank(errorFlag, newCompanyHead, companyPersonDtoList, companyBankDtos, companyBanks, companyBankDtoList);
        listener.getDatas().clear();

        // 公司地址信息
        ReadSheet readSheet3 = EasyExcel.readSheet(2).head(CompanyAddressDto.class).build();
        excelReader.read(readSheet3);
        List<Object> companyAddressDtoList = listener.getDatas();
        ArrayList<CompanyAddressDto> companyAddressDtos = new ArrayList<>();
        ArrayList<CompanyAddress> companyAddresses = new ArrayList<>();
        // 获取银行信息
        getCompanyAddress(errorFlag, newCompanyHead, companyAddressDtoList, companyAddressDtos, companyAddresses);

        if(errorFlag.get()){
            //
            // 字节输出流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayInputStream byteArrayInputStream = null;
            // 有报错信息, 上传文件
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
            WriteSheet sheet0 = EasyExcel.writerSheet(0, "公司联系人信息").head(CompanyPersonDto.class).build();
            WriteSheet sheet1 = EasyExcel.writerSheet(1, "公司账户信息").head(CompanyBankDto.class).build();
            WriteSheet sheet2 = EasyExcel.writerSheet(2, "公司地址信息").head(CompanyAddressDto.class).build();
            excelWriter.write(companyPersonDtos,sheet0).write(companyBankDtos,sheet1).write(companyAddressDtos,sheet2);
            excelWriter.finish();
            byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
            // 上传文件
            fileupload.setFileSourceName("公司信息导入结果.xlsx");
            Fileupload fileupload1 = WordUtils.uploadWordFile(fileCenterClient, byteArrayInputStream, fileupload);
            return ImportStatus.importError(fileupload1.getFileuploadId(),fileupload1.getFileSourceName());
        }else {
            // 处理新增信息头
            if(!newCompanyHead.isEmpty()){
                List<CompanyHead> companyHeads = new ArrayList<>();
                newCompanyHead.forEach((s, companyHead) -> {
                    companyHeads.add(companyHead);
                });
                this.saveBatch(companyHeads);
            }
            if(CollectionUtils.isNotEmpty(companyHeadsUpdates)){
                this.updateBatchById(companyHeadsUpdates);
            }
            // 处理账号信息
            if(CollectionUtils.isNotEmpty(companyPeople)){
                Map<Long, List<CompanyPerson>> companyPersonMap = companyPeople.stream().collect(Collectors.groupingBy(CompanyPerson::getOrgCompanyHeadId));
                if(null != companyPersonMap && !companyPersonMap.isEmpty()){
                    companyPersonMap.forEach((orgCompanyHeadId, companyPeople1) -> {
                        iCompanyPersonService.remove(new QueryWrapper<>(new CompanyPerson().setOrgCompanyHeadId(orgCompanyHeadId)));
                        companyPeople1.forEach(companyPerson -> {
                            companyPerson.setCompanyPersonId(IdGenrator.generate());
                        });
                        iCompanyPersonService.saveBatch(companyPeople1);
                    });
                }
            }
            // 处理账户信息
            if(CollectionUtils.isNotEmpty(companyBanks)){
                Map<Long, List<CompanyBank>> companyBankMap = companyBanks.stream().collect(Collectors.groupingBy(CompanyBank::getOrgCompanyHeadId));
                if(null != companyBankMap && !companyBankMap.isEmpty()){
                    companyBankMap.forEach((orgCompanyHeadId, companyBanks1) -> {
                        iCompanyBankService.remove(new QueryWrapper<>(new CompanyBank().setOrgCompanyHeadId(orgCompanyHeadId)));
                        companyBanks1.forEach(companyBank -> {
                            companyBank.setCompanyBankId(IdGenrator.generate());
                        });
                        iCompanyBankService.saveBatch(companyBanks1);
                    });
                }
            }
            // 处理地址信息
            if(CollectionUtils.isNotEmpty(companyAddresses)){
                Map<Long, List<CompanyAddress>> companyAddressMap = companyAddresses.stream().collect(Collectors.groupingBy(CompanyAddress::getOrgCompanyHeadId));
                if(null != companyAddressMap && !companyAddressMap.isEmpty()){
                    companyAddressMap.forEach((orgCompanyHeadId, companyAddresses1) -> {
                        iCompanyAddressService.remove(new QueryWrapper<>(new CompanyAddress().setOrgCompanyHeadId(orgCompanyHeadId)));
                        companyAddresses1.forEach(companyAddress -> {
                            companyAddress.setCompanyAddressId(IdGenrator.generate());
                        });
                        iCompanyAddressService.saveBatch(companyAddresses1);
                    });
                }
            }
        }
        return ImportStatus.importSuccess();
    }

    private void getCompanyAddress(AtomicBoolean errorFlag, Map<String, CompanyHead> newCompanyHead, List<Object> companyAddressDtoList, ArrayList<CompanyAddressDto> companyAddressDtos, ArrayList<CompanyAddress> companyAddresses) {
        if(CollectionUtils.isNotEmpty(companyAddressDtoList)){
            // 获取公司名字集合
            List<String> companyNameList = new ArrayList<>();
            for(Object o : companyAddressDtoList){
                CompanyAddressDto companyAddressDto = (CompanyAddressDto)o;
                String companyName = companyAddressDto.getCompanyName();
                if(StringUtil.notEmpty(companyName)){
                    companyName = companyName.trim();
                    companyNameList.add(companyName);
                }
            }

            // 所有公司信息
            Map<String, List<Companies>> companiesMap = getCompaniesMap(companyNameList);

            // 查找所有公司头
            Map<String, List<CompanyHead>> companyHeadMap = new HashMap<>();
            if(CollectionUtils.isNotEmpty(companyNameList)){
                companyNameList = companyNameList.stream().distinct().collect(Collectors.toList());
                List<CompanyHead> companyHeadList = this.list(new QueryWrapper<CompanyHead>().in("COMPANY_NAME", companyNameList));
                if(CollectionUtils.isNotEmpty(companyHeadList)){
                    companyHeadMap = companyHeadList.stream().collect(Collectors.groupingBy(CompanyHead::getCompanyName));
                }
            }

            HashSet<String> hashSet = new HashSet<>();
            for(Object o : companyAddressDtoList){
                CompanyAddressDto companyAddressDto = (CompanyAddressDto)o;
                StringBuffer errorMsg = new StringBuffer();
                CompanyAddress companyAddress = new CompanyAddress();
                boolean errorLine = false;
                // 公司ID

                // 公司名称
                String companyName = companyAddressDto.getCompanyName();
                if (StringUtil.notEmpty(companyName)) {
                    companyName = companyName.trim();
                    if (CollectionUtils.isNotEmpty(companyHeadMap.get(companyName))) {
                        CompanyHead companyHead = companyHeadMap.get(companyName).get(0);
                        companyAddress.setCompanyId(companyHead.getCompanyId());
                        companyAddress.setOrgCompanyHeadId(companyHead.getOrgCompanyHeadId());
                    } else if(null != newCompanyHead.get(companyName)) {
                        CompanyHead companyHead = newCompanyHead.get(companyName);
                        companyAddress.setCompanyId(companyHead.getCompanyId());
                        companyAddress.setOrgCompanyHeadId(companyHead.getOrgCompanyHeadId());
                    } else if (CollectionUtils.isNotEmpty(companiesMap.get(companyName))) {
                        // 新增一个公司头
                        Companies companies = companiesMap.get(companyName).get(0);
                        CompanyHead companyHead = new CompanyHead();
                        companyHead.setCompanyId(companies.getCompanyId().toString());
                        companyHead.setCompanyName(companies.getCompanyFullName());
                        companyHead.setOrgCompanyHeadId(IdGenrator.generate());
                        newCompanyHead.put(companyName, companyHead);
                        companyAddress.setCompanyId(companyHead.getCompanyId());
                        companyAddress.setOrgCompanyHeadId(companyHead.getOrgCompanyHeadId());
                    } else {
                        errorFlag.set(true);
                        errorLine = true;
                        errorMsg.append("未找到公司信息; ");
                    }
                } else {
                    errorFlag.set(true);
                    errorLine = true;
                    errorMsg.append("公司名称不能为空; ");
                }

                // 国家
                String country = companyAddressDto.getCountry();
                if(StringUtil.notEmpty(country)){
                    country = country.trim();
                    companyAddress.setCountry(country);
                }

                // 地区
                String area = companyAddressDto.getArea();
                if(StringUtil.notEmpty(area)){
                    area = area.trim();
                    companyAddress.setArea(area);
                }

                // 城市
                String city = companyAddressDto.getCity();
                if(StringUtil.notEmpty(city)){
                    city = city.trim();
                    companyAddress.setCity(city);
                }

                // 详细地址
                String address = companyAddressDto.getAddress();
                if(StringUtil.notEmpty(address)){
                    address = address.trim();
                    companyAddress.setAddress(address);
                }

                // 邮政编码
                String postalCode = companyAddressDto.getPostalCode();
                if(StringUtil.notEmpty(postalCode)){
                    postalCode = postalCode.trim();
                    companyAddress.setPostalCode(postalCode);
                }

                // 地址备注
                String remark = companyAddressDto.getRemark();
                if(StringUtil.notEmpty(remark)){
                    remark = remark.trim();
                    companyAddress.setRemark(remark);
                }

                // 是否激活
                String isActive = companyAddressDto.getIsActive();
                if(StringUtil.notEmpty(isActive)){
                    isActive = isActive.trim();
                    if("Y".equals(isActive) || "N".equals(isActive)){
                        companyAddress.setIsActive(isActive);
                        if(!hashSet.add(companyName)){
                            errorFlag.set(true);
                            errorMsg.append("一个公司只能有一个主账号; ");
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("是否主账号只能填:\"Y\"或\"N\"; ");
                    }
                }

                if(errorMsg.length() > 0){
                    companyAddressDto.setErrorMsg(errorMsg.toString());
                }else {
                    companyAddressDto.setErrorMsg(null);
                }
                companyAddressDtos.add(companyAddressDto);
                companyAddresses.add(companyAddress);
            }
        }
    }

    private void getCompsnyBank(AtomicBoolean errorFlag, Map<String, CompanyHead> newCompanyHead, List<Object> companyPersonDtoList, List<CompanyBankDto> companyBankDtos, List<CompanyBank> companyBanks, List<Object> companyBankDtoList) {
        if(CollectionUtils.isNotEmpty(companyBankDtoList)){
            // 获取公司名字集合
            List<String> companyNameList = new ArrayList<>();
            ArrayList<ErpBranchBank> erpBranchBanks = new ArrayList<>();
            for(Object o : companyPersonDtoList){
                CompanyBankDto companyBankDto = (CompanyBankDto)o;
                String companyName = companyBankDto.getCompanyName();
                if(StringUtil.notEmpty(companyName)){
                    companyName = companyName.trim();
                    companyNameList.add(companyName);
                }
                if(StringUtil.notEmpty(companyBankDto.getBankNum()) &&
                        StringUtil.notEmpty(companyBankDto.getBankName()) &&
                        StringUtil.notEmpty(companyBankDto.getBranchBankName())){
                    ErpBranchBank erpBranchBank = new ErpBranchBank();
                    erpBranchBank.setBankNum(companyBankDto.getBankNum());
                    erpBranchBank.setBankName(companyBankDto.getBankName());
                    erpBranchBank.setBranchBankName(companyBankDto.getBranchBankName());
                    erpBranchBanks.add(erpBranchBank);
                }

            }

            // 所有公司信息
            Map<String, List<Companies>> companiesMap = getCompaniesMap(companyNameList);

            // 查找所有公司头
            Map<String, List<CompanyHead>> companyHeadMap = new HashMap<>();
            if(CollectionUtils.isNotEmpty(companyNameList)){
                companyNameList = companyNameList.stream().distinct().collect(Collectors.toList());
                List<CompanyHead> companyHeadList = this.list(new QueryWrapper<CompanyHead>().in("COMPANY_NAME", companyNameList));
                if(CollectionUtils.isNotEmpty(companyHeadList)){
                    companyHeadMap = companyHeadList.stream().collect(Collectors.groupingBy(CompanyHead::getCompanyName));
                }
            }

            // 查找银行信息
            Map<String, Long> branchBankMap = new HashMap<>();
            if(CollectionUtils.isNotEmpty(erpBranchBanks)){
                List<ErpBranchBank> branchBanks = iErpBranchBankService.listAll(erpBranchBanks);
                if(CollectionUtils.isNotEmpty(branchBanks)){
                    branchBankMap = branchBanks.stream().collect(Collectors.toMap(v -> v.getBankNum() + v.getBankName() + v.getBranchBankName(), k -> k.getBranchBankId()));
                }
            }

            HashSet<String> hashSet = new HashSet<>();
            for(Object o : companyBankDtoList) {
                CompanyBankDto companyBankDto = (CompanyBankDto) o;
                StringBuffer errorMsg = new StringBuffer();
                CompanyBank companyBank = new CompanyBank();
                boolean errorLine = false;
                // 公司ID

                // 公司名称
                String companyName = companyBankDto.getCompanyName();
                if (StringUtil.notEmpty(companyName)) {
                    companyName = companyName.trim();
                    if (CollectionUtils.isNotEmpty(companyHeadMap.get(companyName))) {
                        CompanyHead companyHead = companyHeadMap.get(companyName).get(0);
                        companyBank.setCompanyId(companyHead.getCompanyId());
                        companyBank.setOrgCompanyHeadId(companyHead.getOrgCompanyHeadId());
                    } else if(null != newCompanyHead.get(companyName)) {
                        CompanyHead companyHead = newCompanyHead.get(companyName);
                        companyBank.setCompanyId(companyHead.getCompanyId());
                        companyBank.setOrgCompanyHeadId(companyHead.getOrgCompanyHeadId());
                    } else if (CollectionUtils.isNotEmpty(companiesMap.get(companyName))) {
                        // 新增一个公司头
                        Companies companies = companiesMap.get(companyName).get(0);
                        CompanyHead companyHead = new CompanyHead();
                        companyHead.setCompanyId(companies.getCompanyId().toString());
                        companyHead.setCompanyName(companies.getCompanyFullName());
                        companyHead.setOrgCompanyHeadId(IdGenrator.generate());
                        newCompanyHead.put(companyName, companyHead);
                        companyBank.setCompanyId(companyHead.getCompanyId());
                        companyBank.setOrgCompanyHeadId(companyHead.getOrgCompanyHeadId());
                    } else {
                        errorFlag.set(true);
                        errorLine = true;
                        errorMsg.append("未找到公司信息; ");
                    }
                } else {
                    errorFlag.set(true);
                    errorLine = true;
                    errorMsg.append("公司名称不能为空; ");
                }

                // 银行编号
                String bankNum = companyBankDto.getBankNum();
                if(StringUtil.notEmpty(bankNum)){
                    bankNum = bankNum.trim();
                    companyBank.setBankNum(bankNum);
                }else {
                    errorFlag.set(true);
                    errorLine = true;
                    errorMsg.append("银行编号不能为空; ");
                }

                // 银行名称
                String bankName = companyBankDto.getBankName();
                if(StringUtil.notEmpty(bankName)){
                    bankName = bankName.trim();
                    companyBank.setBankName(bankName);
                }else {
                    errorFlag.set(true);
                    errorLine = true;
                    errorMsg.append("银行名称不能为空; ");
                }

                // 开户行名称
                String branchBankName = companyBankDto.getBranchBankName();
                if(StringUtil.notEmpty(branchBankName)){
                    branchBankName = branchBankName.trim();
                    companyBank.setBranchBankName(branchBankName);
                }else {
                    errorLine = true;
                    errorFlag.set(true);
                    errorMsg.append("开户行名称不能为空; ");
                }

                // 检查银行信息是否存在
                if(!errorLine){
                    String only = companyBank.getBankNum() + companyBank.getBankName() + companyBank.getBranchBankName();
                    if(StringUtil.notEmpty(branchBankMap.get(only))){
                        // 银行ID
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("未找到银行账号相关账户信息; ");
                    }
                }

                // 账户名称
                String accountName = companyBankDto.getAccountName();
                if(StringUtil.notEmpty(accountName)){
                    accountName = accountName.trim();
                    companyBank.setAccountName(accountName);
                }

                // 银行账号
                String bankAccount = companyBankDto.getBankAccount();
                if(StringUtil.notEmpty(bankAccount)){
                    bankAccount = bankAccount.trim();
                    companyBank.setBankAccount(bankAccount);
                }

                // 是否主账号
                String isMain = companyBankDto.getIsMain();
                if(StringUtil.notEmpty(isMain)){
                    isMain = isMain.trim();
                    if("Y".equals(isMain) || "N".equals(isMain)){
                        companyBank.setIsMain(isMain);
                        if(!hashSet.add(companyName)){
                            errorFlag.set(true);
                            errorMsg.append("一个公司只能有一个主账号; ");
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("是否主账号只能填:\"Y\"或\"N\"; ");
                    }
                }

                // 是否启用
                String isActive = companyBankDto.getIsActive();
                if(StringUtil.notEmpty(isActive)){
                    isActive = isActive.trim();
                    if("Y".equals(isActive) || "N".equals(isActive)){
                        companyBank.setIsActive(isActive);
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("是否启用只能填:\"Y\"或\"N\"; ");
                    }
                }

                if(errorMsg.length() > 0){
                    companyBankDto.setErrorMsg(errorMsg.toString());
                }else {
                    companyBankDto.setErrorMsg(null);
                }
                companyBankDtos.add(companyBankDto);
                companyBanks.add(companyBank);
            }
        }
    }

    private void getPersonData(AtomicBoolean errorFlag, Map<String, CompanyHead> newCompanyHead,
                               List<Object> companyPersonDtoList, List<CompanyPersonDto> companyPersonDtos,
                               List<CompanyPerson> companyPeople,List<CompanyHead> companyHeadsUpdates) {
        if(CollectionUtils.isNotEmpty(companyPersonDtoList)){
            // 获取公司名字集合
            List<String> companyNameList = new ArrayList<>();
            for(Object o : companyPersonDtoList){
                CompanyPersonDto companyPersonDto = (CompanyPersonDto)o;
                String companyName = companyPersonDto.getCompanyName();
                if(StringUtil.notEmpty(companyName)){
                    companyName = companyName.trim();
                    companyNameList.add(companyName);
                }
            }

            // 所有公司信息
            Map<String, List<Companies>> companiesMap = getCompaniesMap(companyNameList);

            // 查找所有公司头
            Map<String, List<CompanyHead>> companyHeadMap = new HashMap<>();
            if(CollectionUtils.isNotEmpty(companyNameList)){
                companyNameList = companyNameList.stream().distinct().collect(Collectors.toList());
                List<CompanyHead> companyHeadList = this.list(new QueryWrapper<CompanyHead>().in("COMPANY_NAME", companyNameList));
                if(CollectionUtils.isNotEmpty(companyHeadList)){
                    companyHeadMap = companyHeadList.stream().collect(Collectors.groupingBy(CompanyHead::getCompanyName));
                }
            }

            HashSet<String> hashSet = new HashSet<>();
            HashSet<String> taxNumSet = new HashSet<>();
            HashMap<String, String> companyTaxNumberMap = new HashMap<>();
            for(Object o : companyPersonDtoList){
                CompanyPersonDto companyPersonDto = (CompanyPersonDto)o;
                StringBuffer errorMsg = new StringBuffer();
                CompanyPerson companyPerson = new CompanyPerson();
                // 公司名称
                String companyName = companyPersonDto.getCompanyName();
                if(StringUtil.notEmpty(companyName)){
                    companyName = companyName.trim();
                    if(CollectionUtils.isNotEmpty(companyHeadMap.get(companyName))){
                        CompanyHead companyHead = companyHeadMap.get(companyName).get(0);
                        companyPerson.setCompanyId(companyHead.getCompanyId());
                        companyPerson.setOrgCompanyHeadId(companyHead.getOrgCompanyHeadId());
                        // 税号
                        String taxNumber = companyPersonDto.getTaxNumber();
                        if(StringUtil.notEmpty(taxNumber)){
                            taxNumber = taxNumber.trim();
                            // 判断同一个公司税号必须相同
                            if(companyTaxNumberMap.containsKey(companyName)){
                                String s = companyTaxNumberMap.get(companyName);
                                if(taxNumber.equals(s)){
                                    companyHead.setTaxNumber(taxNumber);
                                }else {
                                    errorFlag.set(true);
                                    errorMsg.append("同一个公司税号必须相同; ");
                                }
                            }else {
                                companyHead.setTaxNumber(taxNumber);
                                companyTaxNumberMap.put(companyName,taxNumber);
                            }
                            if (taxNumSet.add(companyName)) {
                                companyHeadsUpdates.add(companyHead);
                            }
                        }
                    }else if(CollectionUtils.isNotEmpty(companiesMap.get(companyName))){
                        // 新增一个公司头
                        Companies companies = companiesMap.get(companyName).get(0);
                        CompanyHead companyHead = new CompanyHead();
                        companyHead.setCompanyId(companies.getCompanyId().toString());
                        companyHead.setCompanyName(companies.getCompanyFullName());
                        companyHead.setOrgCompanyHeadId(IdGenrator.generate());
                        // 税号
                        String taxNumber = companyPersonDto.getTaxNumber();
                        if(StringUtil.notEmpty(taxNumber)){
                            taxNumber = taxNumber.trim();
                            // 判断同一个公司税号必须相同
                            if(companyTaxNumberMap.containsKey(companyName)){
                                String s = companyTaxNumberMap.get(companyName);
                                if(taxNumber.equals(s)){
                                    companyHead.setTaxNumber(taxNumber);
                                }else {
                                    errorFlag.set(true);
                                    errorMsg.append("同一个公司税号必须相同; ");
                                }
                            }else {
                                companyHead.setTaxNumber(taxNumber);
                                companyTaxNumberMap.put(companyName,taxNumber);
                            }
                        }
                        newCompanyHead.put(companyName,companyHead);
                        companyPerson.setCompanyId(companyHead.getCompanyId());
                        companyPerson.setOrgCompanyHeadId(companyHead.getOrgCompanyHeadId());
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("未找到公司信息; ");
                    }
                }else {
                    errorFlag.set(true);
                    errorMsg.append("公司名称不能为空; ");
                }

                // 联系人名字
                String name = companyPersonDto.getName();
                if(StringUtil.notEmpty(name)){
                    name = name.trim();
                    companyPerson.setName(name);
                }

                // 性别
                String sex = companyPersonDto.getSex();
                if(StringUtil.notEmpty(sex)){
                    sex = sex.trim();
                    if("男".equals(sex) || "女".equals(sex)){
                        switch (sex){
                            case "男":
                                companyPerson.setSex("1");
                                break;
                            case "女":
                                companyPerson.setSex("0");
                                break;
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("性别只能填: \"男\"或\"女\"; ");
                    }
                }

                // 部门
                String department = companyPersonDto.getDepartment();
                if(StringUtil.notEmpty(department)){
                    department = department.trim();
                    companyPerson.setDepartment(department);
                }

                // 职位
                String position = companyPersonDto.getPosition();
                if(StringUtil.notEmpty(position)){
                    position = position.trim();
                    companyPerson.setPosition(position);
                }

                // 联系电话
                String phone = companyPersonDto.getPhone();
                if(StringUtil.notEmpty(phone)){
                    phone = phone.trim();
                    companyPerson.setPhone(phone);
                }

                // 邮箱
                String email = companyPersonDto.getEmail();
                if(StringUtil.notEmpty(email)){
                    email = email.trim();
                    companyPerson.setEmail(email);
                }

                // 是否默认联系人
                String isDefault = companyPersonDto.getIsDefault();
                if(StringUtil.notEmpty(isDefault)){
                    isDefault = isDefault.trim();
                    if("Y".equals(isDefault) || "N".equals(isDefault)){
                        companyPerson.setIsDefault(isDefault);
                        if(StringUtil.notEmpty(companyPerson.getCompanyId()) &&
                                "Y".equals(isDefault) && !hashSet.add(companyName)){
                            errorFlag.set(true);
                            errorMsg.append("一个公司只能有一个默认联系人; ");
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("默认联系人只能填:\"Y\"或\"N\"; ");
                    }
                }

                // 备注
                String remark = companyPersonDto.getRemark();
                if(StringUtil.notEmpty(remark)){
                    remark = remark.trim();
                    companyPerson.setRemark(remark);
                }

                // 是否激活
                String isActive = companyPersonDto.getIsActive();
                if(StringUtil.notEmpty(isActive)){
                    isActive = isActive.trim();
                    if("Y".equals(isActive) || "N".equals(isActive)){
                        companyPerson.setIsActive(isActive);
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("是否启用只能填:\"Y\"或\"N\"; ");
                    }
                }

                if(errorMsg.length() > 0){
                    companyPersonDto.setErrorMsg(errorMsg.toString());
                }else {
                    companyPersonDto.setErrorMsg(null);
                }

                companyPeople.add(companyPerson);
                companyPersonDtos.add(companyPersonDto);
            }
        }
    }

    public Map<String, List<Companies>> getCompaniesMap(List<String> companyNameList) {
        Map<String, List<Companies>> companiesMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(companyNameList)){
            companyNameList = companyNameList.stream().distinct().collect(Collectors.toList());
            List<Companies> companies = iCompaniesService.list(new QueryWrapper<Companies>().in("COMPANY_FULL_NAME", companyNameList));
            if(CollectionUtils.isNotEmpty(companies)){
                companiesMap = companies.stream().collect(Collectors.groupingBy(Companies::getCompanyFullName));
            }
        }
        return companiesMap;
    }

    @Override
    @Transactional
    public Long addOrUpdate(CompanyHead companyHead) {
        Assert.notNull(companyHead,"参数不能为空");
        Assert.notNull(companyHead.getCompanyId(),"缺少参数: companyId");
        // 保存头信息
        if (StringUtil.isEmpty(companyHead.getOrgCompanyHeadId())) {
            companyHead.setOrgCompanyHeadId(IdGenrator.generate());
            this.save(companyHead);
        }else {
            this.updateById(companyHead);
        }

        Long orgCompanyHeadId = companyHead.getOrgCompanyHeadId();
        // 保存联系人信息
        List<CompanyPerson> companyPeoples = companyHead.getCompanyPeoples();
        iCompanyPersonService.remove(new QueryWrapper<>(new CompanyPerson().setOrgCompanyHeadId(orgCompanyHeadId)));
        AtomicInteger personSum = new AtomicInteger(0);
        if (CollectionUtils.isNotEmpty(companyPeoples)) {
            companyPeoples.forEach(companyPerson -> {
                companyPerson.setOrgCompanyHeadId(orgCompanyHeadId);
                companyPerson.setCompanyPersonId(IdGenrator.generate());
                companyPerson.setCompanyId(companyHead.getCompanyId());
                if(YesOrNo.YES.getValue().equals(companyPerson.getIsDefault())){
                    personSum.addAndGet(1);
                }
            });
            iCompanyPersonService.saveBatch(companyPeoples);
        }
        Assert.isTrue(personSum.get() <= 1 ,"联系人只能有1个默认人");


        // 保存地址
        AtomicInteger addressesSum = new AtomicInteger(0);
        List<CompanyAddress> companyAddresses = companyHead.getCompanyAddresses();
        iCompanyAddressService.remove(new QueryWrapper<>(new CompanyAddress().setOrgCompanyHeadId(orgCompanyHeadId)));
        if(CollectionUtils.isNotEmpty(companyAddresses)){
            companyAddresses.forEach(companyAddress -> {
                companyAddress.setOrgCompanyHeadId(orgCompanyHeadId);
                companyAddress.setCompanyAddressId(IdGenrator.generate());
                companyAddress.setCompanyId(companyHead.getCompanyId());
                addressesSum.addAndGet(1);
            });
            iCompanyAddressService.saveBatch(companyAddresses);
        }
        Assert.isTrue(addressesSum.get() <= 1 ,"地址信息只能有1个启用");

        // 保存账户信息
        AtomicInteger bankPersonSum = new AtomicInteger(0);
        List<CompanyBank> companyBanks = companyHead.getCompanyBanks();
        iCompanyBankService.remove(new QueryWrapper<>(new CompanyBank().setOrgCompanyHeadId(orgCompanyHeadId)));
        if(CollectionUtils.isNotEmpty(companyBanks)){
            companyBanks.forEach(companyBank -> {
                companyBank.setOrgCompanyHeadId(orgCompanyHeadId);
                companyBank.setCompanyBankId(IdGenrator.generate());
                companyBank.setCompanyId(companyHead.getCompanyId());
                if(YesOrNo.YES.getValue().equals(companyBank.getIsMain())){
                    bankPersonSum.set(bankPersonSum.get()+1);
                }
            });
            iCompanyBankService.saveBatch(companyBanks);
        }
        Assert.isTrue(bankPersonSum.get() <= 1 ,"账户信息只能有一个主账号");
        return orgCompanyHeadId;
    }

    @Override
    public CompanyHead get(String companyId,String isActive,String isDefault,String isMain) {
        Assert.notNull(companyId,"缺少参数: companyId");
        CompanyHead companyHead = this.getOne(new QueryWrapper<>(new CompanyHead().setCompanyId(companyId)));

        if (null != companyHead) {
            Long orgCompanyHeadId = companyHead.getOrgCompanyHeadId();
            // 查找联系人信息
            List<CompanyPerson> companyPersonList = iCompanyPersonService.list(new QueryWrapper<>(new CompanyPerson().setOrgCompanyHeadId(orgCompanyHeadId)).
                    eq(StringUtil.notEmpty(isActive), "IS_ACTIVE", isActive).
                    eq(StringUtil.notEmpty(isDefault), "IS_DEFAULT", isDefault));
            // 查找地址
            List<CompanyAddress> companyAddressList = iCompanyAddressService.list(new QueryWrapper<>(new CompanyAddress().setOrgCompanyHeadId(orgCompanyHeadId)).
                    eq(StringUtil.notEmpty(isActive), "IS_ACTIVE", isActive));
            // 查找账户信息
            List<CompanyBank> companyBankList = iCompanyBankService.list(new QueryWrapper<>(new CompanyBank().setOrgCompanyHeadId(orgCompanyHeadId)).
                    eq(StringUtil.notEmpty(isMain), "IS_MAIN", isMain).eq(StringUtil.notEmpty(isActive),"IS_ACTIVE",isActive));
            companyHead.
                    setCompanyAddresses(companyAddressList).
                    setCompanyBanks(companyBankList).
                    setCompanyPeoples(companyPersonList);
        }
        return companyHead;
    }

    @Override
    public ContractPartner queryContractPartnerByOuId(Long ouId) {
        Assert.notNull(ouId,"业务实体ID不能为空");
        Organization organization = iOrganizationService.getById(ouId);
        ContractPartner contractPartner = null;
        if (null != organization) {
            contractPartner = new ContractPartner();
            contractPartner.setOuId(organization.getOrganizationId()); // ouID
            contractPartner.setOuCode(organization.getOrganizationCode());
            contractPartner.setOuName(organization.getOrganizationName());
            contractPartner.setPartnerName(organization.getCeeaCompanyName()); // 公司名字
            // 查找公司详情
            if (StringUtil.notEmpty(organization.getCompany())) {
                // 公司详情
                CompanyHead companyHead = get(organization.getCompany(), YesOrNo.YES.getValue(), YesOrNo.YES.getValue(), YesOrNo.YES.getValue());
                if (null != companyHead){
                    /**
                     * 合同合作伙伴处，以下字段根据业务实体查到对应的公司，带出公司信息中，启用的值：
                     * 1、代表人：联系人信息处，是否启用为启用。且为默认联系人行的“联系人姓名”；
                     * 2、联系电话：联系人信息处的“联系方式”；
                     * 3、地址信息：地址信息处，是否启用为启用的“详细地址”；
                     * 4、开户银行：账号信息处，是否启用为启用，是主账号的“开户行名称”；
                     * 5、银行账号：账号信息处，是否启用为启用，是主账号的“银行账号”；
                     * 6、邮编：地址信息处的“邮政编码”；
                     * 7、税号：税号；
                     */
                    contractPartner.setTaxNumber(companyHead.getTaxNumber()); // 税号
                    // 账户信息
                    List<CompanyBank> companyBanks = companyHead.getCompanyBanks();
                    if(CollectionUtils.isNotEmpty(companyBanks)){
                        CompanyBank companyBank = companyBanks.get(0);
                        contractPartner.setBankName(companyBank.getBranchBankName()); // 开户银行
                        contractPartner.setBankAccount(companyBank.getBankAccount()); // 银行账号
                    }
                    // 地址信息
                    List<CompanyAddress> companyAddresses = companyHead.getCompanyAddresses();
                    if(CollectionUtils.isNotEmpty(companyAddresses)){
                        CompanyAddress companyAddress = companyAddresses.get(0);
                        contractPartner.setAddress(companyAddress.getAddress()); // 地址信息
                        contractPartner.setPostCode(companyAddress.getPostalCode()); // 邮编
                    }
                    // 联系人信息
                    List<CompanyPerson> companyPeoples = companyHead.getCompanyPeoples();
                    if(CollectionUtils.isNotEmpty(companyPeoples)){
                        CompanyPerson companyPerson = companyPeoples.get(0);
                        contractPartner.setContactName(companyPerson.getName()); // 代表人
                        contractPartner.setPhone(companyPerson.getPhone()); // 电话
                    }
                }
            }

        }
        return contractPartner;
    }
}
