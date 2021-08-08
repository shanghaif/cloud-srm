package com.midea.cloud.srm.cm.contract.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.googlecode.aviator.AviatorEvaluator;
import com.midea.cloud.common.annotation.AuthData;
import com.midea.cloud.common.constants.ContractHeadConst;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.cm.PaymentStatus;
import com.midea.cloud.common.enums.contract.ContractSourceType;
import com.midea.cloud.common.enums.contract.ContractStatus;
import com.midea.cloud.common.enums.contract.ContractType;
import com.midea.cloud.common.enums.contract.ModelKey;
import com.midea.cloud.common.enums.rbac.MenuEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.cm.CmSaopUrl;
import com.midea.cloud.srm.cm.contract.mapper.ContractHeadMapper;
import com.midea.cloud.srm.cm.contract.service.*;
import com.midea.cloud.srm.cm.contract.workflow.ContractFlow;
import com.midea.cloud.srm.cm.model.service.IModelHeadService;
import com.midea.cloud.srm.cm.model.service.IModelLineService;
import com.midea.cloud.srm.cm.soap.ContractExecutePtt;
import com.midea.cloud.srm.cm.template.service.IPayTypeService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.signature.SignatureClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.dept.dto.DeptDto;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuDetailVO;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuGroupDetailVO;
import com.midea.cloud.srm.model.base.purchase.dto.PurchaseCategoryAllInfo;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseUnit;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirement;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.enums.SourceFrom;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormResult;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.SourceForm;
import com.midea.cloud.srm.model.cm.annex.Annex;
import com.midea.cloud.srm.model.cm.contract.dto.*;
import com.midea.cloud.srm.model.cm.contract.entity.*;
import com.midea.cloud.srm.model.cm.contract.soap.Request;
import com.midea.cloud.srm.model.cm.contract.soap.Response;
import com.midea.cloud.srm.model.cm.contract.vo.ContractHeadVO;
import com.midea.cloud.srm.model.cm.model.entity.ModelLine;
import com.midea.cloud.srm.model.cm.template.entity.PayType;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.price.dto.ApprovalToContractDetail;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryContractRequestDTO;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryContractResDTO;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItemPaymentTerm;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.inq.price.enums.SourcingType;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalBiddingItemVO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.ps.advance.entity.AdvanceApplyHead;
import com.midea.cloud.srm.model.pm.ps.advance.vo.AdvanceApplyHeadVo;
import com.midea.cloud.srm.model.pm.ps.payment.dto.CeeaPaymentApplyDTO;
import com.midea.cloud.srm.model.pm.ps.payment.entity.CeeaPaymentApplyPlan;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.signature.SigningParam;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  合同头表 服务实现类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 10:10:46
 *  修改内容:
 * </pre>
 */
@Service
public class ContractHeadServiceImpl extends ServiceImpl<ContractHeadMapper, ContractHead> implements IContractHeadService {

    @Autowired
    FileCenterClient fileCenterClient;

    @Autowired
    IContractLineService iContractLineService;

    @Autowired
    IContractMaterialService iContractMaterialService;

    @Autowired
    IContractPartnerService iContractPartnerService;

    @Autowired
    IPayPlanService iPayPlanService;

    @Autowired
    BaseClient baseClient;

    @Autowired
    InqClient inqClient;

    @Autowired
    IModelLineService iModelLineService;

    @Resource
    IModelHeadService iModelHeadService;

    @Resource
    private SupcooperateClient supcooperateClient;

    @Autowired
    private IPayTypeService iPayTypeService;

    @Resource
    private SupplierClient supplierClient;

    @Resource
    private PmClient pmClient;

    @Resource
    private IAnnexService iAnnexService;

    @Resource
    private RbacClient rbacClient;

    @Resource
    private com.midea.cloud.srm.feign.bid.SourceFormClient sourceFormClientBid;

    @Resource
    private com.midea.cloud.srm.feign.bargaining.SourceFormClient sourceFormClientBargain;

    @Resource
    private ICloseAnnexService iCloseAnnexService;

    @Resource
    private ILevelMaintainService iLevelMaintainService;

    @Autowired
    private ContractFlow contractFlow;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${CM_USER.cmUserName}")
    private String contractUserName;

    @Value("${CM_USER.cmPassword}")
    private String contractPassword;
    @Autowired
    private ContractHeadMapper contractHeadMapper;

    @Resource
    private SignatureClient signatureClient;

    @Override
    public PageInfo<ContractHead> queryContractByVendorId(ContractHead contractHead) {
        Assert.notNull(contractHead,"参数不能为空");
        Assert.notNull(contractHead.getVendorId(),"vendorId不能为空");
        PageUtil.startPage(contractHead.getPageNum(),contractHead.getPageSize());
        ContractDTO contractDTO = new ContractDTO();
        List<ContractHead> contractHeads = this.list(Wrappers.lambdaQuery(ContractHead.class).
                eq(ContractHead::getVendorId, contractHead.getVendorId()).
                eq(ContractHead::getContractStatus, ContractStatus.ARCHIVED));
        if(CollectionUtils.isNotEmpty(contractHeads)){
            contractHeads.forEach(contractHead1 -> {
                List<PayPlan> payPlans = iPayPlanService.list(Wrappers.lambdaQuery(PayPlan.class).
                        eq(PayPlan::getContractHeadId, contractHead1.getContractHeadId()));
                contractHead1.setPayPlans(payPlans);
            });
        }
        return new PageInfo<>(contractHeads);
    }

    public String getContractLevel(List<ContractMaterial> contractMaterials) {
        String contractLevel = null;
        if (CollectionUtils.isNotEmpty(contractMaterials)) {
            ArrayList<String> levels = new ArrayList<>();
            List<String> codes = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            contractMaterials.forEach(contractMaterial -> {
                if (StringUtil.isEmpty(contractMaterial.getAmount())) {
                    contractMaterial.setAmount(BigDecimal.ZERO);
                }
                if (StringUtil.notEmpty(contractMaterial.getMaterialCode())) {
                    codes.add(contractMaterial.getMaterialCode());
                } else {
                    builder.append(contractMaterial.getMaterialName()).append(",");
                }
            });
            // 查找物料

            if (CollectionUtils.isEmpty(codes)) {
                throw new BaseException(String.format("请检查合同物料[%s]是否已维护", builder.toString()));
            }
            List<MaterialItem> materialItems = baseClient.listMaterialByCodeBatch(codes);

            if (CollectionUtils.isNotEmpty(materialItems)) {
                /**
                 * 检查物料的采购分类是否已经维护
                 */
                Map<Long, List<MaterialItem>> materialMap = materialItems.stream().filter(materialItem -> StringUtil.notEmpty(materialItem.getStruct())).collect(Collectors.groupingBy(MaterialItem::getMaterialId));
                if (null != materialMap && !materialMap.isEmpty()) {
                    StringBuffer errorMsg = new StringBuffer();
                    contractMaterials.forEach(contractMaterial -> {
                        if (materialMap.containsKey(contractMaterial.getMaterialId())) {
                            String struct = materialMap.get(contractMaterial.getMaterialId()).get(0).getStruct();
                            contractMaterial.setStruct(struct);
                        } else {
                            errorMsg.append(contractMaterial.getMaterialName()).append(";");
                        }
                    });
                    if (errorMsg.length() > 0) {
                        throw new BaseException("请在配置管理->物料维护:维护物料的品类{" + errorMsg.toString() + "}");
                    }
                } else {
                    throw new BaseException("请在配置管理->物料维护:维护所有物料的品类");
                }
                Map<String, List<ContractMaterial>> collect = contractMaterials.stream().filter(contractMaterial -> null != contractMaterial.getStruct()).collect(Collectors.groupingBy(ContractMaterial::getStruct));
                collect.forEach((struct, contractMaterialList) -> {
                    BigDecimal sum = contractMaterialList.stream().map(ContractMaterial::getAmount).reduce(BigDecimal::add).get();
                    QueryWrapper<LevelMaintain> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("STRUCT", struct).orderByDesc("LEVEL");
                    queryWrapper.and(wrapper -> wrapper.isNull("END_DATA").or().ge("END_DATA", LocalDate.now()));
                    List<LevelMaintain> levelMaintains = iLevelMaintainService.list(queryWrapper);
                    if (CollectionUtils.isNotEmpty(levelMaintains)) {
                        for (LevelMaintain levelMaintain : levelMaintains) {
                            String formula = levelMaintain.getFormula();
                            String replace = StringUtils.replace(formula, "${value}", String.valueOf(sum.doubleValue() / 10000));
                            boolean flag = (boolean) AviatorEvaluator.execute(replace);
                            if (flag) {
                                levels.add(levelMaintain.getLevel());
                                break;
                            }
                        }
                    }
                });
            }
            if (CollectionUtils.isNotEmpty(levels)) {
                levels.sort((o1, o2) -> o1.compareTo(o2));
                contractLevel = levels.get(0);
            }
        }

        return contractLevel;
    }

    @Override
    @Transactional
    public void buyerSaveOrUpdateContractDTO(ContractDTO contractDTO, String contractStatus) {
        ContractHead contractHead = contractDTO.getContractHead();
        List<ContractLine> contractLines = contractDTO.getContractLines();
        List<ContractMaterial> contractMaterials = contractDTO.getContractMaterials();
        List<PayPlan> payPlans = contractDTO.getPayPlans();
        List<Fileupload> fileuploads = contractDTO.getFileuploads();

        //保存或编辑合同头信息
        saveOrUpdateContractHead(contractHead, contractStatus);
        //保存或编辑合同行内容信息
        saveOrUpdateContractLines(contractHead, contractLines);
        //保存或编辑合同物料信息
        saveOrUpdateContractMaterials(contractHead, contractMaterials);
        //保存或编辑合同付款计划
        saveOrUpdatePayPlans(contractHead, payPlans);
        //保存或编辑合同附件
        bindingFileuploads(contractHead, fileuploads);
    }

    @Override
    public PageInfo<ContractHead> listPageByParam(ContractHeadDTO contractHeadDTO) {
        PageUtil.startPage(contractHeadDTO.getPageNum(), contractHeadDTO.getPageSize());
        List<ContractHead> contractHeads = listContractHead(contractHeadDTO);
        return new PageInfo<>(contractHeads);
    }

//    @Override
//    public void export(ContractHeadDTO contractHeadDTO, HttpServletResponse response) throws IOException {
//        Long count = queryCountByList(contractHeadDTO);
//        if (count > 20000){
//            // 检查有没有分页参数
//            if(StringUtil.notEmpty(contractHeadDTO.getPageNum()) && StringUtil.notEmpty(contractHeadDTO.getPageSize())){
//                Assert.isTrue(contractHeadDTO.getPageSize() <= 20000,"单次导出上限为20000");
//            }else {
//                throw new BaseException("导出数量超过20000,请选择分页导出,单次导出上限20000");
//            }
//        }
//        if(StringUtil.notEmpty(contractHeadDTO.getPageNum()) && StringUtil.notEmpty(contractHeadDTO.getPageSize())){
//            PageUtil.startPage(contractHeadDTO.getPageNum(),contractHeadDTO.getPageSize());
//        }
//        Map<String, String> contarctLevelMap = EasyExcelUtil.getDicCodeName("CONTARCT_LEVEL", baseClient);
//        Map<String, String> elemContractTypeMap = EasyExcelUtil.getDicCodeName("ELEM_CONTRACT_TYPE", baseClient);
//        Map<String, String> contractStatusMap = EasyExcelUtil.getDicCodeName("CONTRACT_STATUS", baseClient);
//        Map<String, String> contractTypeMap = EasyExcelUtil.getDicCodeName("CONTRACT_TYPE", baseClient);
//        // 要导出的数据
//        ArrayList<ContractListExportDto> contractListExportDtos = new ArrayList<>();
//        List<ContractHead> contractHeads = listContractHeadExport(contractHeadDTO);
//        if(CollectionUtils.isNotEmpty(contractHeads)){
//            contractHeads.forEach(contractHead -> {
//                ContractListExportDto contractListExportDto = new ContractListExportDto();
//                BeanCopyUtil.copyProperties(contractListExportDto,contractHead);
//                // 日期转换
//                contractListExportDto.setStartDate(DateUtil.localDateToStr(contractHead.getStartDate()));
//                contractListExportDto.setEffectiveDateFrom(DateUtil.localDateToStr(contractHead.getEffectiveDateFrom()));
//                contractListExportDto.setEffectiveDateTo(DateUtil.localDateToStr(contractHead.getEffectiveDateTo()));
//                // 字典转换 : 合同状态/合同类型/操作类型/合同级别
//                contractListExportDto.setContractStatus(contractStatusMap.get(contractHead.getContractStatus()));
//                contractListExportDto.setContractLevel(contarctLevelMap.get(contractHead.getContractLevel()));
//                contractListExportDto.setContractType(contractTypeMap.get(contractHead.getContractType()));
//                contractListExportDto.setContractClass(elemContractTypeMap.get(contractHead.getContractClass()));
//                contractListExportDtos.add(contractListExportDto);
//            });
//        }
//        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "合同列表导出");
//        EasyExcelUtil.writeExcelWithModel(outputStream,"sheet",contractListExportDtos,ContractListExportDto.class);
//    }

    @Override
    public void export(ContractHeadDTO contractHeadDTO, HttpServletResponse response) throws IOException {
        Long count = queryCountByList(contractHeadDTO);
        Map<String, String> contarctLevelMap = EasyExcelUtil.getDicCodeName("CONTARCT_LEVEL", baseClient);
        Map<String, String> elemContractTypeMap = EasyExcelUtil.getDicCodeName("ELEM_CONTRACT_TYPE", baseClient);
        Map<String, String> contractStatusMap = EasyExcelUtil.getDicCodeName("CONTRACT_STATUS", baseClient);
        Map<String, String> contractTypeMap = EasyExcelUtil.getDicCodeName("CONTRACT_TYPE", baseClient);

        if(count <= 20000){
            // 获取导出数据
            List<ContractListExportDto> contractListExportDtos = getContractListExportDtos(contractHeadDTO, contarctLevelMap, elemContractTypeMap, contractStatusMap, contractTypeMap);
            ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "合同列表导出");
            EasyExcelUtil.writeExcelWithModel(outputStream,"sheet",contractListExportDtos,ContractListExportDto.class);
        }else {
            ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "合同列表导出");
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
            Long size = count / 20000 + 1;
            for(int i = 0 ; i < size ; i++){
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "sheet" + i).head(ContractListExportDto.class).build();
                // 要导出的数据
                List<ContractListExportDto> contractListExportDtos = getContractListExportDtos(contractHeadDTO, contarctLevelMap, elemContractTypeMap, contractStatusMap, contractTypeMap, i);
                excelWriter.write(contractListExportDtos,writeSheet);
            }
            excelWriter.finish();
        }
    }

    @Override
    public void exportLine(ContractHeadDTO contractHeadDTO, HttpServletResponse response) throws IOException {
        Map<String, String> contarctLevelMap = EasyExcelUtil.getDicCodeName("CONTARCT_LEVEL", baseClient);
        Map<String, String> elemContractTypeMap = EasyExcelUtil.getDicCodeName("ELEM_CONTRACT_TYPE", baseClient);
        Map<String, String> contractStatusMap = EasyExcelUtil.getDicCodeName("CONTRACT_STATUS", baseClient);
        Map<String, String> sourceTypeMap = EasyExcelUtil.getDicCodeName("Sourc_Type ", baseClient);
        Map<Long, PurchaseCategoryAllInfo> longPurchaseCategoryAllInfoMap = baseClient.queryPurchaseCategoryAllInfo();

        Long count = this.baseMapper.queryContractDescExportcount(contractHeadDTO);
        if(count <= 50000){
            List<ContractDescExportDto> descExportDtoArrayList = getContractDescExportDtos(-1,contractHeadDTO, contarctLevelMap, elemContractTypeMap, contractStatusMap, sourceTypeMap, longPurchaseCategoryAllInfoMap);
            ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "合同详情导出");
            EasyExcelUtil.writeExcelWithModel(outputStream,"sheet",descExportDtoArrayList,ContractDescExportDto.class);
        }else {
            ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "合同详情导出");
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
            count = count <= 500000?count:500000;
            Long size = count / 50000 + 1;
            for(int i = 0 ; i < size ; i++){
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "sheet" + i).head(ContractDescExportDto.class).build();
                // 要导出的数据
                List<ContractDescExportDto> descExportDtoArrayList = getContractDescExportDtos(i,contractHeadDTO, contarctLevelMap, elemContractTypeMap, contractStatusMap, sourceTypeMap, longPurchaseCategoryAllInfoMap);
                excelWriter.write(descExportDtoArrayList,writeSheet);
            }
            excelWriter.finish();
        }

    }

    public List<ContractDescExportDto> getContractDescExportDtos(int i,ContractHeadDTO contractHeadDTO, Map<String, String> contarctLevelMap, Map<String, String> elemContractTypeMap, Map<String, String> contractStatusMap, Map<String, String> sourceTypeMap, Map<Long, PurchaseCategoryAllInfo> longPurchaseCategoryAllInfoMap) {
        List<ContractDescExportDto> descExportDtoArrayList = new ArrayList<>();
        // 分页
        if(-1 != i){
            PageUtil.startPage(i+1,50000);
        }
        List<ContractDescExportDto> contractDescExportDtos = listContractDescExport(contractHeadDTO);
        if(CollectionUtils.isNotEmpty(contractDescExportDtos)){
            Map<Long, List<ContractDescExportDto>> collect = contractDescExportDtos.stream().collect(Collectors.groupingBy(ContractDescExportDto::getContractHeadId));
            if(!collect.isEmpty()){
                collect.forEach((contractHeadId, contractDescExportDtoList) -> {
                    AtomicInteger no = new AtomicInteger(1);
                    contractDescExportDtoList.forEach(contractDescExportDto -> {
                        // 设置行序号
                        contractDescExportDto.setNo(no.getAndAdd(1));
                        // 数据转换
                        contractDescExportDto.setDateTo(DateUtil.localDateToStr(contractDescExportDto.getEffectiveDateFrom()));
                        contractDescExportDto.setDateEnd(DateUtil.localDateToStr(contractDescExportDto.getEffectiveDateTo()));
                        contractDescExportDto.setLineDateTo(DateUtil.localDateToStr(contractDescExportDto.getStartDate()));
                        contractDescExportDto.setLineDateEnd(DateUtil.localDateToStr(contractDescExportDto.getEndDate()));
                        // 字典转换
                        contractDescExportDto.setContractLevel(contarctLevelMap.get(contractDescExportDto.getContractLevel()));
                        contractDescExportDto.setContractStatus(contractStatusMap.get(contractDescExportDto.getContractStatus()));
                        contractDescExportDto.setContractClass(elemContractTypeMap.get(contractDescExportDto.getContractClass()));
                        contractDescExportDto.setSourceType(sourceTypeMap.get(contractDescExportDto.getSourceType()));
                        // 采购分类转换
                        PurchaseCategoryAllInfo purchaseCategoryAllInfo = longPurchaseCategoryAllInfoMap.get(contractDescExportDto.getCategoryId());
                        if(null != purchaseCategoryAllInfo){
                            contractDescExportDto.setCategoryName1(purchaseCategoryAllInfo.getCategoryName3());
                            contractDescExportDto.setCategoryName2(purchaseCategoryAllInfo.getCategoryName2());
                            contractDescExportDto.setCategoryName3(purchaseCategoryAllInfo.getCategoryName1());
                        }
                    });
                    descExportDtoArrayList.addAll(contractDescExportDtoList);
                });
            }
        }
        return descExportDtoArrayList;
    }

    public ArrayList<ContractListExportDto> getContractListExportDtos(ContractHeadDTO contractHeadDTO, Map<String, String> contarctLevelMap, Map<String, String> elemContractTypeMap, Map<String, String> contractStatusMap, Map<String, String> contractTypeMap, int i) {
        ArrayList<ContractListExportDto> contractListExportDtos = new ArrayList<>();
        PageUtil.startPage(i+1,20000);
        List<ContractHead> contractHeads = listContractHeadExport(contractHeadDTO);
        if(CollectionUtils.isNotEmpty(contractHeads)){
            contractHeads.forEach(contractHead -> {
                ContractListExportDto contractListExportDto = new ContractListExportDto();
                BeanCopyUtil.copyProperties(contractListExportDto,contractHead);
                // 日期转换
                contractListExportDto.setStartDate(DateUtil.localDateToStr(contractHead.getStartDate()));
                contractListExportDto.setEffectiveDateFrom(DateUtil.localDateToStr(contractHead.getEffectiveDateFrom()));
                contractListExportDto.setEffectiveDateTo(DateUtil.localDateToStr(contractHead.getEffectiveDateTo()));
                // 字典转换 : 合同状态/合同类型/操作类型/合同级别
                contractListExportDto.setContractStatus(contractStatusMap.get(contractHead.getContractStatus()));
                contractListExportDto.setContractLevel(contarctLevelMap.get(contractHead.getContractLevel()));
                contractListExportDto.setContractType(contractTypeMap.get(contractHead.getContractType()));
                contractListExportDto.setContractClass(elemContractTypeMap.get(contractHead.getContractClass()));
                contractListExportDtos.add(contractListExportDto);
            });
        }
        return contractListExportDtos;
    }

    public ArrayList<ContractListExportDto> getContractListExportDtos(ContractHeadDTO contractHeadDTO, Map<String, String> contarctLevelMap, Map<String, String> elemContractTypeMap, Map<String, String> contractStatusMap, Map<String, String> contractTypeMap) {
        // 要导出的数据
        ArrayList<ContractListExportDto> contractListExportDtos = new ArrayList<>();
        List<ContractHead> contractHeads = listContractHeadExport(contractHeadDTO);
        if(CollectionUtils.isNotEmpty(contractHeads)){
            contractHeads.forEach(contractHead -> {
                ContractListExportDto contractListExportDto = new ContractListExportDto();
                BeanCopyUtil.copyProperties(contractListExportDto,contractHead);
                // 日期转换
                contractListExportDto.setStartDate(DateUtil.localDateToStr(contractHead.getStartDate()));
                contractListExportDto.setEffectiveDateFrom(DateUtil.localDateToStr(contractHead.getEffectiveDateFrom()));
                contractListExportDto.setEffectiveDateTo(DateUtil.localDateToStr(contractHead.getEffectiveDateTo()));
                // 字典转换 : 合同状态/合同类型/操作类型/合同级别
                contractListExportDto.setContractStatus(contractStatusMap.get(contractHead.getContractStatus()));
                contractListExportDto.setContractLevel(contarctLevelMap.get(contractHead.getContractLevel()));
                contractListExportDto.setContractType(contractTypeMap.get(contractHead.getContractType()));
                contractListExportDto.setContractClass(elemContractTypeMap.get(contractHead.getContractClass()));
                contractListExportDtos.add(contractListExportDto);
            });
        }
        return contractListExportDtos;
    }

    @Override
    @AuthData(module = {MenuEnum.CONTRACT_MAINTAIN_LIST, MenuEnum.SUPPLIER_SIGN})
    public List<ContractHead> listContractHead(ContractHeadDTO contractHeadDTO) {
        if (StringUtil.notEmpty(contractHeadDTO.getVendorId())) {
            contractHeadDTO.setVendorIdMan(String.valueOf(contractHeadDTO.getVendorId()));
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String userType = loginAppUser.getUserType();
        if (UserType.VENDOR.name().equals(userType)) {
            if (StringUtil.isEmpty(loginAppUser.getCompanyId())) {
                return null;
            }
            contractHeadDTO.setVendorId(loginAppUser.getCompanyId()).setUserType(UserType.VENDOR.name());
        }
//        else if (UserType.BUYER.name().equals(userType)) {
//            contractHeadDTO.setVendorId(null);
//        }
        //设置时间段查询条件
        Date creationDate = contractHeadDTO.getCreationDate();
        if (creationDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(creationDate);
            cal.add(Calendar.HOUR, 23);
            cal.add(Calendar.MINUTE, 59);
            cal.add(Calendar.SECOND, 59);
            Date endCreationDate = cal.getTime();
            contractHeadDTO.setStartCreationDate(creationDate).setEndCreationDate(endCreationDate);
        }

        return this.baseMapper.listPageByParam(contractHeadDTO);
    }

    @AuthData(module = {MenuEnum.CONTRACT_MAINTAIN_LIST, MenuEnum.SUPPLIER_SIGN})
    public List<ContractDescExportDto> listContractDescExport(ContractHeadDTO contractHeadDTO) {
        if (StringUtil.notEmpty(contractHeadDTO.getVendorId())) {
            contractHeadDTO.setVendorIdMan(String.valueOf(contractHeadDTO.getVendorId()));
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String userType = loginAppUser.getUserType();
        if (UserType.VENDOR.name().equals(userType)) {
            if (StringUtil.isEmpty(loginAppUser.getCompanyId())) {
                return null;
            }
            contractHeadDTO.setVendorId(loginAppUser.getCompanyId()).setUserType(UserType.VENDOR.name());
        }
//        else if (UserType.BUYER.name().equals(userType)) {
//            contractHeadDTO.setVendorId(null);
//        }
        //设置时间段查询条件
        Date creationDate = contractHeadDTO.getCreationDate();
        if (creationDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(creationDate);
            cal.add(Calendar.HOUR, 23);
            cal.add(Calendar.MINUTE, 59);
            cal.add(Calendar.SECOND, 59);
            Date endCreationDate = cal.getTime();
            contractHeadDTO.setStartCreationDate(creationDate).setEndCreationDate(endCreationDate);
        }

        return this.baseMapper.queryContractDescExportDate(contractHeadDTO);
    }

    @AuthData(module = {MenuEnum.CONTRACT_MAINTAIN_LIST, MenuEnum.SUPPLIER_SIGN})
    public List<ContractHead> listContractHeadExport(ContractHeadDTO contractHeadDTO) {
        if (StringUtil.notEmpty(contractHeadDTO.getVendorId())) {
            contractHeadDTO.setVendorIdMan(String.valueOf(contractHeadDTO.getVendorId()));
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String userType = loginAppUser.getUserType();
        if (UserType.VENDOR.name().equals(userType)) {
            if (StringUtil.isEmpty(loginAppUser.getCompanyId())) {
                return null;
            }
            contractHeadDTO.setVendorId(loginAppUser.getCompanyId()).setUserType(UserType.VENDOR.name());
        }
//        else if (UserType.BUYER.name().equals(userType)) {
//            contractHeadDTO.setVendorId(null);
//        }
        //设置时间段查询条件
        Date creationDate = contractHeadDTO.getCreationDate();
        if (creationDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(creationDate);
            cal.add(Calendar.HOUR, 23);
            cal.add(Calendar.MINUTE, 59);
            cal.add(Calendar.SECOND, 59);
            Date endCreationDate = cal.getTime();
            contractHeadDTO.setStartCreationDate(creationDate).setEndCreationDate(endCreationDate);
        }

        return this.baseMapper.listPageByParamExport(contractHeadDTO);
    }

    /**
     * 查询导出的条数
     * @param contractHeadDTO
     * @return
     */
    @Override
    @AuthData(module = {MenuEnum.CONTRACT_MAINTAIN_LIST, MenuEnum.SUPPLIER_SIGN})
    public Long queryCountByList(ContractHeadDTO contractHeadDTO){
        if (StringUtil.notEmpty(contractHeadDTO.getVendorId())) {
            contractHeadDTO.setVendorIdMan(String.valueOf(contractHeadDTO.getVendorId()));
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String userType = loginAppUser.getUserType();
        if (UserType.VENDOR.name().equals(userType)) {
            if (StringUtil.isEmpty(loginAppUser.getCompanyId())) {
                return null;
            }
            contractHeadDTO.setVendorId(loginAppUser.getCompanyId()).setUserType(UserType.VENDOR.name());
        }
        //设置时间段查询条件
        Date creationDate = contractHeadDTO.getCreationDate();
        if (creationDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(creationDate);
            cal.add(Calendar.HOUR, 23);
            cal.add(Calendar.MINUTE, 59);
            cal.add(Calendar.SECOND, 59);
            Date endCreationDate = cal.getTime();
            contractHeadDTO.setStartCreationDate(creationDate).setEndCreationDate(endCreationDate);
        }

        return this.baseMapper.queryCount(contractHeadDTO);
    }

    @Override
    @Transactional
    public void vendorUpdateContractHeadDTO(ContractHeadDTO contractHeadDTO) {
        ContractHead contractHead = new ContractHead();
        BeanUtils.copyProperties(contractHeadDTO, contractHead);
        //供应商确认时,系统设置供应商确认人与供应商确认时间
        if (ContractStatus.SUPPLIER_CONFIRMED.name().equals(contractHead.getContractStatus())) {
            contractHead.setVendorConfirmDate(LocalDate.now())
                    .setVendorConfirmBy(AppUserUtil.getLoginAppUser().getUsername());
        }
        this.updateById(contractHead);
    }

    @Override
    public ContractDTO getContractDTO(Long contractHeadId) {
        Assert.notNull(contractHeadId, "contractHeadId不能为空");
        ContractDTO contractDTO = new ContractDTO();
        ContractHead contractHead = this.getById(contractHeadId);
        List<ContractLine> contractLines = iContractLineService.list(new QueryWrapper<>(
                new ContractLine().setContractHeadId(contractHeadId)));
        List<PayPlan> payPlans = iPayPlanService.list(new QueryWrapper<>(
                new PayPlan().setContractHeadId(contractHeadId)));
        List<ContractMaterial> contractMaterials = iContractMaterialService.list(new QueryWrapper<>(
                new ContractMaterial().setContractHeadId(contractHeadId)));
        List<Fileupload> fileuploads = new ArrayList<>();
        if (contractHead != null) {
            List<Fileupload> templFiles = fileCenterClient.listPage(new Fileupload().setBusinessId(contractHead.getTemplHeadId()), null).getList();
            fileuploads.addAll(templFiles);
            List<Fileupload> contractFiles = fileCenterClient.listPage(new Fileupload().setBusinessId(contractHeadId), null).getList();
            fileuploads.addAll(contractFiles);
        }
        contractDTO.setContractHead(contractHead)
                .setContractLines(contractLines)
                .setContractMaterials(contractMaterials)
                .setPayPlans(payPlans)
                .setFileuploads(fileuploads);
        return contractDTO;
    }

    @Override
    public void buyerUpdateContractStatus(ContractHeadDTO contractHeadDTO, String contractStatus) {
        ContractHead contractHead = this.getById(contractHeadDTO.getContractHeadId());
        Assert.notNull(contractHead, LocaleHandler.getLocaleMsg("contractHead不能为空"));

        //发布时,需校验
        if (ContractStatus.SUPPLIER_CONFIRMING.name().equals(contractStatus)) {
            buyerCheckBeforePublish(contractHead);
        }

        //归档时,需校验
        if (ContractStatus.ARCHIVED.name().equals(contractStatus)) {
//            buyerCheckBeforeArchived(contractHead);
            contractHead.setStartDate(LocalDate.now());
        }

        //采购商审批拒绝
        if (ContractStatus.REFUSED.name().equals(contractStatus)) {
            contractHead.setApprovalAdvice(contractHeadDTO.getApprovalAdvice());
        }

        //采购商撤回审批
        if (ContractStatus.WITHDRAW.name().equals(contractStatus)) {
            contractHead.setApprovalAdvice(contractHeadDTO.getApprovalAdvice());
        }
        //2020-11-02 归档时同步合同到费控系统
//        if (ContractStatus.ARCHIVED.name().equals(contractStatus)) {
//            try {
//                Response response = pushContractInfo(contractHead.getContractHeadId());
//                if ("E".equals(response.getEsbInfo().getReturnStatus())) {
//                    log.error("同步合同到费控系统中失败" + response.getEsbInfo().getReturnMsg());
//                    throw new BaseException(response.getEsbInfo().getReturnMsg());
//                }
//            } catch (BaseException e) {
//                throw new BaseException("归档失败! " + e.getMessage());
//            } catch (Exception e) {
//                log.error("合同归档失败", e);
//                throw new BaseException("归档失败! ");
//            }
//        }

        contractHead.setContractStatus(contractStatus);
        this.updateById(contractHead);
    }

    private void buyerCheckBeforeArchived(ContractHead contractHead) {
        if (!ContractStatus.SUPPLIER_CONFIRMED.name().equals(contractHead.getContractStatus())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("供应商已确认状态的合同,才可归档"));
        }
    }

    @Override
    public void vendorUpdateContractStatus(Long contractHeadId, String contractStatus) {
        ContractHead contractHead = this.getById(contractHeadId);
        Assert.notNull(contractHead, LocaleHandler.getLocaleMsg("contractHead不能为空"));

        //驳回时,需校验
        if (ContractStatus.REJECTED.name().equals(contractStatus)) {
            vendorCheckBeforeReject(contractHead);
        }

        //提交时,需检验
        if (ContractStatus.SUPPLIER_CONFIRMED.name().equals(contractStatus)) {
            vendorCheckBeforeSubmit(contractHead);
            contractHead.setVendorConfirmBy(AppUserUtil.getLoginAppUser().getUsername())
                    .setVendorConfirmDate(LocalDate.now());
        }

        contractHead.setContractStatus(contractStatus);
        this.updateById(contractHead);
    }

    @Override
    @Transactional
    public void buyerDelete(Long contractHeadId) {
        this.removeById(contractHeadId);
        iContractLineService.remove(new QueryWrapper<>(new ContractLine().setContractHeadId(contractHeadId)));
        iPayPlanService.remove(new QueryWrapper<>(new PayPlan().setContractHeadId(contractHeadId)));
        iContractMaterialService.remove(new QueryWrapper<>(new ContractMaterial().setContractHeadId(contractHeadId)));
        fileCenterClient.deleteByParam(new Fileupload().setBusinessId(contractHeadId));
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                //如果有，需要用户等待回写完毕
                Boolean contractUpdateApproval = redisTemplate.opsForSet().isMember("contractUpdateApproval", String.valueOf(contractHeadId));
                if (contractUpdateApproval) {
                    do {
                        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(500));
                    }
                    while (redisTemplate.opsForSet().isMember("contractUpdateApproval", String.valueOf(contractHeadId)));
                }
                inqClient.resetItemsBelongNumber(Collections.singletonList(contractHeadId));
            }
        });
    }

    /**
     * 废弃订单
     *
     * @param contractHeadId
     */
    @Override
    @Transactional
    public void abandon(Long contractHeadId) {
        ContractDTO contractDTO = this.getContractDTOSecond(contractHeadId, null);
        ContractHead contractHead = contractDTO.getContractHead();
        Assert.notNull(ObjectUtils.isEmpty(contractHead), "找不到废弃的订单");
        String contractStatus = contractHead.getContractStatus();
        Assert.isTrue(ContractStatus.WITHDRAW.toString().equals(contractStatus) || ContractStatus.REJECTED.toString().equals(contractStatus), "只有已驳回，已撤回的订单才可以撤回。");
        contractHead.setContractStatus(ContractStatus.ABANDONED.toString());
        this.updateById(contractHead);
        SrmFlowBusWorkflow srmworkflowForm = baseClient.getSrmFlowBusWorkflow(contractHeadId);
        if (srmworkflowForm != null) {
            try {
                contractDTO.setProcessType("N");
                contractFlow.submitContractDTOConfFlow(contractDTO);
            } catch (Exception e) {
                Assert.isTrue(false, "废弃同步审批流失败。");
            }
        }
    }


    @Override
    public List<ContractMaterial> getMaterialsBySourceNumber(String sourceNumber, Long orgId, Long vendorId) {
        List<ContractMaterial> contractMaterials = new ArrayList<>();
        //查询价格目录物料,并且转为合同物料
        if (StringUtil.notEmpty(sourceNumber)) {
            contractMaterials = this.getContractMaterialsBySourceNumber(sourceNumber, contractMaterials);
        } else if (StringUtil.notEmpty(orgId) && StringUtil.notEmpty(vendorId)) {
            this.getContractMaterialsByOrgVendor(orgId, vendorId, contractMaterials);
        }
        return contractMaterials;
    }

    @Override
    public List<ContractMaterial> getMaterialsBySourceNumberAndorgIdAndvendorId(String sourceNumber, Long orgId, Long vendorId) {
        List<ContractMaterial> contractMaterials = new ArrayList<>();
        if (StringUtil.notEmpty(sourceNumber) && StringUtil.notEmpty(orgId) && StringUtil.notEmpty(vendorId)) {
            List<PriceLibrary> priceLibraries = inqClient.listPagePriceLibrary(
                    new PriceLibrary().setSourceNo(sourceNumber).setOrganizationId(orgId).setVendorId(vendorId)).getList();
            if (!CollectionUtils.isEmpty(priceLibraries)) {
                for (PriceLibrary priceLibrary : priceLibraries) {
                    ContractMaterial contractMaterial = new ContractMaterial();
                    if (priceLibrary != null) {
                        BeanUtils.copyProperties(priceLibrary, contractMaterial);
                        contractMaterial.setSourceNumber(sourceNumber)
                                .setMaterialCode(priceLibrary.getItemCode())
                                .setMaterialId(priceLibrary.getItemId())
                                .setMaterialName(priceLibrary.getItemDesc())
                                .setSpecification(priceLibrary.getSpecification())
                                .setUntaxedPrice(priceLibrary.getNotaxPrice())
                                .setTaxedPrice(priceLibrary.getTaxPrice())
//                                .setOrderQuantity(priceLibrary.getOrderQuantity())
                                .setContractQuantity(priceLibrary.getOrderQuantity());
                        if (StringUtils.isNumeric(priceLibrary.getTaxRate())) {
                            contractMaterial.setTaxKey(priceLibrary.getTaxKey());
                            contractMaterial.setTaxRate(new BigDecimal(priceLibrary.getTaxRate()));
                        }
                        //设置单位相关字段
                        String unit = priceLibrary.getUnit();
                        setUnit(contractMaterial, unit);

                        //设置采购品类相关字段
                        Long categoryId = priceLibrary.getCategoryId();
                        setCategory(contractMaterial, categoryId);

                        if (priceLibrary.getNotaxPrice() != null && priceLibrary.getOrderQuantity() != null) {
                            contractMaterial.setAmount(priceLibrary.getNotaxPrice().multiply(priceLibrary.getOrderQuantity()));
                        }
                    }
                    contractMaterials.add(contractMaterial);
                }
            }
        } else {
            throw new BaseException("缺失必填参数: sourceNumber,orgId,vendorId ");
        }
        return contractMaterials;
    }

    public void getContractMaterialsByOrgVendor(Long orgId, Long vendorId, List<ContractMaterial> contractMaterials) {
        List<PriceLibrary> priceLibraries = inqClient.listPagePriceLibrary(
                new PriceLibrary().setOrganizationId(orgId).setVendorId(vendorId)).getList();

        if (CollectionUtils.isNotEmpty(priceLibraries)) {
            for (PriceLibrary priceLibrary : priceLibraries) {
                ContractMaterial contractMaterial = new ContractMaterial();
                if (null != priceLibrary) {
                    BeanUtils.copyProperties(priceLibrary, contractMaterial);
                    contractMaterial.setSourceNumber(priceLibrary.getSourceNo())
                            .setMaterialCode(priceLibrary.getItemCode())
                            .setMaterialId(priceLibrary.getItemId())
                            .setMaterialName(priceLibrary.getItemDesc())
                            .setSpecification(priceLibrary.getSpecification())
                            .setUntaxedPrice(priceLibrary.getNotaxPrice())
                            .setTaxedPrice(priceLibrary.getTaxPrice())
//                            .setOrderQuantity(priceLibrary.getOrderQuantity())
                            .setContractQuantity(priceLibrary.getOrderQuantity());
                    if (StringUtils.isNumeric(priceLibrary.getTaxRate())) {
                        contractMaterial.setTaxKey(priceLibrary.getTaxKey());
                        contractMaterial.setTaxRate(new BigDecimal(priceLibrary.getTaxRate()));
                    }
                    //设置单位相关字段
                    String unit = priceLibrary.getUnit();
                    setUnit(contractMaterial, unit);

                    //设置采购品类相关字段
                    Long categoryId = priceLibrary.getCategoryId();
                    setCategory(contractMaterial, categoryId);

                    if (priceLibrary.getNotaxPrice() != null && priceLibrary.getOrderQuantity() != null) {
                        contractMaterial.setAmount(priceLibrary.getNotaxPrice().multiply(priceLibrary.getOrderQuantity()));
                    }
                    contractMaterials.add(contractMaterial);
                }
            }
        }
    }

    public List<ContractMaterial> getContractMaterialsBySourceNumber(String sourceNumber, List<ContractMaterial> contractMaterials) {
        List<PriceLibrary> priceLibraries = inqClient.listPagePriceLibrary(new PriceLibrary().setSourceNo(sourceNumber)).getList();
        if (!CollectionUtils.isEmpty(priceLibraries)) {
            contractMaterials = priceLibraries.stream().map(priceLibrary -> {
                ContractMaterial contractMaterial = new ContractMaterial();
                if (priceLibrary != null) {
                    BeanUtils.copyProperties(priceLibrary, contractMaterial);
                    contractMaterial.setSourceNumber(sourceNumber)
                            .setMaterialCode(priceLibrary.getItemCode())
                            .setMaterialId(priceLibrary.getItemId())
                            .setMaterialName(priceLibrary.getItemDesc())
                            .setSpecification(priceLibrary.getSpecification())
                            .setUntaxedPrice(priceLibrary.getNotaxPrice())
                            .setTaxedPrice(priceLibrary.getTaxPrice())
//                            .setOrderQuantity(priceLibrary.getOrderQuantity())
                            .setContractQuantity(priceLibrary.getOrderQuantity());
                    if (StringUtils.isNumeric(priceLibrary.getTaxRate())) {
                        contractMaterial.setTaxKey(priceLibrary.getTaxKey());
                        contractMaterial.setTaxRate(new BigDecimal(priceLibrary.getTaxRate()));
                    }
                    //设置单位相关字段
                    String unit = priceLibrary.getUnit();
                    setUnit(contractMaterial, unit);

                    //设置采购品类相关字段
                    Long categoryId = priceLibrary.getCategoryId();
                    setCategory(contractMaterial, categoryId);

                    if (priceLibrary.getNotaxPrice() != null && priceLibrary.getOrderQuantity() != null) {
                        contractMaterial.setAmount(priceLibrary.getNotaxPrice().multiply(priceLibrary.getOrderQuantity()));
                    }
                }
                return contractMaterial;
            }).collect(Collectors.toList());
        }
        return contractMaterials;
    }

    @Override
    @Transactional
    public ContractHead buyerSaveOrUpdateContractDTOSecond(ContractDTO contractDTO, String contractStatus) throws ParseException {
        ContractHead contractHead = contractDTO.getContractHead();
        //附件
        List<Annex> annexes = contractDTO.getAnnexes();
        //付款计划
        List<PayPlan> payPlans = contractDTO.getPayPlans();
        //合同物料
        List<ContractMaterial> contractMaterials = contractDTO.getContractMaterials();
        //合作伙伴
        List<ContractPartner> contractPartners = contractDTO.getContractPartners();
        //合同行表
        List<ModelLine> modelLines = contractDTO.getModelLines();

        // 检查合同数据
        this.checkContractData(annexes, contractHead, payPlans, contractMaterials);

//        /**
//         * 校验合作伙伴
//         * 合作伙伴必须在物料里面存在
//         */
//        if (CollectionUtils.isNotEmpty(contractMaterials)) {
//            if (CollectionUtils.isEmpty(contractPartners) && YesOrNo.NO.getValue().equals(contractHead.getIsFrameworkAgreement())) {
//                // 业务实体集合
//                ArrayList<ContractPartner> contractPartnerList = new ArrayList<>();
//                contractMaterials.forEach(contractMaterial -> {
//                    // 获取物料里面的所有业务实体
//                    ContractPartner contractPartner = new ContractPartner();
//                    // 业务实体ID
//                    Long buId = contractMaterial.getBuId();
//                    // ou组编码
//                    String ceeaOuNumber = contractMaterial.getCeeaOuNumber();
//                    if (StringUtil.notEmpty(buId)) {
//                        ContractPartner contractPartner1 = baseClient.queryContractPartnerByOuId(buId);
//                        BeanCopyUtil.copyProperties(contractPartner, contractPartner1);
//                        contractPartner.setPartnerName(contractMaterial.getBuName());
//                        contractPartner.setPartnerType("甲方");
//                        contractPartner.setOuId(buId);
//                        contractPartner.setMaterialId(contractMaterial.getMaterialId());
//                    } else if (StringUtil.notEmpty(ceeaOuNumber)) {
//                        BaseOuDetail baseOuDetail = baseClient.queryBaseOuDetailByCode(ceeaOuNumber);
//                        if (null != baseOuDetail) {
//                            ContractPartner contractPartner1 = baseClient.queryContractPartnerByOuId(baseOuDetail.getOuId());
//                            BeanCopyUtil.copyProperties(contractPartner, contractPartner1);
//                            contractPartner.setPartnerName(baseOuDetail.getOuName());
//                            contractPartner.setPartnerType("甲方");
//                            contractPartner.setOuId(baseOuDetail.getOuId());
//                            contractPartner.setMaterialId(contractMaterial.getMaterialId());
//                        }
//                    }
//                    contractPartnerList.add(contractPartner);
//                });
//                contractPartners = new ArrayList<>();
//                contractPartners.addAll(contractPartnerList);
//                ContractPartner contractPartner = new ContractPartner();
//                contractPartner.setPartnerType("乙方");
//                contractPartner.setPartnerName(contractHead.getVendorName());
//                contractPartners.add(contractPartner);
//            }
//        }

        // 获取组织信息
        Organization organization = baseClient.get(contractHead.getBuId());
        Assert.notNull(organization, "找不到送审业务实体:" + contractHead.getBuName());
        if (StringUtil.isEmpty(organization.getCeeaCompanyShort()) || StringUtil.isEmpty(organization.getCeeaCompanyCode())) {
            throw new BaseException("请维护送审业务实体的公司简称或公司代码");
        }
        //保存或编辑合同头信息
        Assert.notNull(contractHead, "contractHead不能为空");
        if (StringUtil.isEmpty(contractHead.getContractHeadId())) {
            long id = IdGenrator.generate();
            contractHead.setContractHeadId(id)
                    .setContractStatus(contractStatus)
                    .setContractNo(baseClient.seqGen(SequenceCodeConstant.SEQ_CONTRACT_NO));
            if (YesOrNo.NO.getValue().equals(contractHead.getCeeaIfVirtual())) {
                // 生成合同编号
                String contractCode = null;
                try {
                    contractCode = getContractCode(contractHead, organization);
                } catch (Exception e) {
                    log.error("获取合同编号失败:" + e);
                    throw new BaseException(e.getMessage());
                }
                contractHead.setContractCode(contractCode);
            }
            this.save(contractHead);
        } else {
            // 生成合同编号
            if (StringUtil.isEmpty(contractHead.getContractCode()) && YesOrNo.NO.getValue().equals(contractHead.getCeeaIfVirtual())) {
                // 获取合同编号
                String contractCode = null;
                try {
                    contractCode = getContractCode(contractHead, organization);
                } catch (Exception e) {
                    log.error("获取合同编号失败:" + e);
                    throw new BaseException(e.getMessage());
                }
                contractHead.setContractCode(contractCode);
            }
            contractHead.setContractStatus(contractStatus);
            this.updateById(contractHead);
        }
        //保存或编辑合同行信息
        if (CollectionUtils.isNotEmpty(modelLines)) {
            Long modelHeadId = contractHead.getModelHeadId();
            Assert.notNull(modelHeadId, "modelHeadId不能为空");
            for (ModelLine modelLine : modelLines) {
                if (modelLine == null) continue;
                if (modelLine.getModelLineId() == null) {
                    modelLine.setModelHeadId(modelHeadId);
                    modelLine.setModelLineId(IdGenrator.generate());
                    modelLine.setContractHeadId(contractHead.getContractHeadId());
                    modelLine.setContractNo(contractHead.getContractNo());
                    iModelLineService.save(modelLine);
                } else {
                    iModelLineService.updateById(modelLine);
                }
            }
        }

        // 保存或编辑合同计划
        saveOrUpdatePayPlans(contractHead, payPlans);

        // 保存或编辑合同物料清单
        saveOrUpdateContractMaterials(contractHead, contractMaterials);

        // 保存或编辑合作伙伴
        saveOrUpdateContractPartners(contractHead, contractPartners);

        // 保存或编辑合同附件
        saveOrUpdateContractAnnex(contractHead, annexes);

        return contractHead;
    }

    private void importSave(ContractDTO contractDTO) {
        // 合同头信息
        ContractHead contractHead = contractDTO.getContractHead();
        //付款计划
        List<PayPlan> payPlans = contractDTO.getPayPlans();
        //合同物料
        List<ContractMaterial> contractMaterials = contractDTO.getContractMaterials();
        //合作伙伴
        List<ContractPartner> contractPartners = contractDTO.getContractPartners();

        long id = IdGenrator.generate();
        contractHead.setContractHeadId(id);
        // 判断合同序列号是否存在
        if (StringUtils.isEmpty(contractHead.getContractNo())) {
            contractHead.setContractNo(baseClient.seqGen(SequenceCodeConstant.SEQ_CONTRACT_NO));
        }
        if (StringUtils.isEmpty(contractHead.getContractCode())) {
            Organization organization = baseClient.get(contractHead.getBuId());
            Assert.notNull(organization, "找不到送审业务实体:" + contractHead.getBuName());
            if (StringUtil.notEmpty(organization.getCeeaCompanyShort()) && StringUtil.notEmpty(organization.getCeeaCompanyCode())) {
                // 获取合同编号
                String contractCode = null;
                try {
                    contractCode = getContractCode(contractHead, organization);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                contractHead.setContractCode(contractCode);
            } else {
                throw new BaseException("请维护送审业务实体的公司简称或公司代码");
            }
        }
        this.save(contractHead);
        // 保存或编辑合同计划
        saveOrUpdatePayPlans(contractHead, payPlans);

        // 保存或编辑合同物料清单
        saveOrUpdateContractMaterials(contractHead, contractMaterials);

        // 保存或编辑合作伙伴
        saveOrUpdateContractPartners(contractHead, contractPartners);
    }

    public String getContractCode(ContractHead contractHead, Organization organization) throws ParseException {
        String code = null;
        if (ContractType.MIAN_CONTRACT_ADD.name().equals(contractHead.getContractType())) {
            StringBuffer prefix = new StringBuffer();
            StringBuffer suffix = new StringBuffer();

            // 前缀
            prefix.append("LGi").append("·");
            prefix.append(organization.getCeeaCompanyShort()).append("-");
//            contractCode.append(contractHead.getContractClass()).append("-");
            prefix.append("Pur").append("-");
            String yyyMM = DateUtil.localDateToStr(LocalDate.now(), "yyMM");
            prefix.append(yyyMM).append("-");
            String prefixDtr = prefix.toString();

            // 获取当月同类型合同数量
            QueryWrapper<ContractHead> wrapper = new QueryWrapper<>();
            Map<String, Date> dateMap = DateUtil.getFirstAndLastDayOfMonth();
            wrapper.between("CREATION_DATE", dateMap.get("startDate"), dateMap.get("endDate"));
            wrapper.eq("CONTRACT_TYPE", ContractType.MIAN_CONTRACT_ADD.name());
            int count = this.count(wrapper) + 1;
            String num = String.format("%03d", count);

            // 后缀
            suffix.append("-");
            suffix.append(contractHead.getContractLevel()).append("/").append(organization.getCeeaCompanyCode()).append("-");
            suffix.append("SRM");
            String suffixStr = suffix.toString();

            while (true) {
                try {
                    num = String.format("%03d", count);
                    code = prefixDtr + num + suffixStr;
                    List<ContractHead> contractHeads = this.list(new QueryWrapper<>(new ContractHead().setContractCode(code)));
                    if (CollectionUtils.isEmpty(contractHeads)) {
                        break;
                    } else {
                        count += 1;
                        num = String.format("%03d", count);
                    }
                } catch (Exception e) {
                    break;
                }
            }
        } else if (StringUtil.notEmpty(contractHead.getContractType())) {
            /**
             * LGi·QJ-Pcm-2009-002-LEVEL_01/1300-SRM
             * 原合同编号为LGi·X-Pur-1908-159-C/318-SRM，
             * 那么变更/补充协议（第一次）编号就是LGi·X-Pur-1908-159/01-C/318-SRM，
             * 多了个/01在159后面，第二次02，第三次03
             */
            String contractOldCode = contractHead.getContractOldCode();
            int count = this.count(new QueryWrapper<>(new ContractHead().setContractOldCode(contractOldCode))) + 1;
            String num = "/" + String.format("%02d", count);
            code = getModifyContractCode(contractOldCode, num);
            while (true) {
                try {
                    List<ContractHead> contractHeads = this.list(new QueryWrapper<>(new ContractHead().setContractCode(code)));
                    if (CollectionUtils.isEmpty(contractHeads)) {
                        break;
                    } else {
                        num = "/" + String.format("%02d", count + 1);
                        code = getModifyContractCode(contractOldCode, num);
                    }
                } catch (Exception e) {
                    break;
                }
            }
        } else {
            throw new BaseException("缺少合同类型");
        }
        return code;
    }

    public String getModifyContractCode(String str, String str2) {
        String[] split = StringUtils.split(str, "-");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            if (i != 3) {
                stringBuffer.append(split[i]).append("-");
            } else {
                stringBuffer.append(split[i]);
                stringBuffer.append(str2).append("-");
            }
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }

    @Transactional
    public void saveOrUpdateContractAnnex(ContractHead contractHead, List<Annex> annexes) {
        iAnnexService.remove(new QueryWrapper<>(new Annex().setContractHeadId(contractHead.getContractHeadId())));
        if (!CollectionUtils.isEmpty(annexes)) {
            annexes.forEach(annex -> {
                if (StringUtil.notEmpty(annex.getFileuploadId())) {
                    annex.setAnnexId(IdGenrator.generate());
                    annex.setContractHeadId(contractHead.getContractHeadId());
                    iAnnexService.save(annex);
                }
            });
        }
    }

    public void checkContractData(List<Annex> annexes, ContractHead contractHead, List<PayPlan> payPlans, List<ContractMaterial> contractMaterials) {
        /**
         * “是否便捷合同”仅可在合同总金额小于等于2万时，可勾选；
         * 如果标的更改，导致合同总金额大于2万，保存/提交时需要校验不可勾选；
         * 勾选后，合同模板非必填（无论是否标准）
         */
        String ceeaIsPortableContract = contractHead.getCeeaIsPortableContract();
        BigDecimal includeTaxAmount = contractHead.getIncludeTaxAmount();
        if (YesOrNo.YES.getValue().equals(ceeaIsPortableContract)) {
            if (null != includeTaxAmount && includeTaxAmount.compareTo(new BigDecimal("20000")) > 0) {
                throw new BaseException("“是否便捷合同”仅可在合同总金额小于等于2万时，可勾选；");
            }
        }
        /**
         * 如果是虚拟合同，并且选了框架协议，填充合同编号
         */
        Assert.notNull(contractHead.getCeeaIfVirtual(), "是否虚拟合同不能为空！");
        if (YesOrNo.YES.getValue().equals(contractHead.getCeeaIfVirtual())) {
            Optional.ofNullable(contractHead.getFrameworkAgreementCode()).ifPresent(s -> contractHead.setContractCode(s));
        }

        // 合同类型不能为空
        Assert.notNull(contractHead.getContractClass(), "合同类型不能为空");

        // 合同来源
        if (StringUtil.isEmpty(contractHead.getContractHeadId()) && StringUtil.isEmpty(contractHead.getSourceType())) {
            contractHead.setSourceType(ContractSourceType.MANUALLY_CREATED.name());
        }

        // 物料大类编码为50的物资，不可创建合同
        if (CollectionUtils.isNotEmpty(contractMaterials)) {
            List<Long> materialIds = new ArrayList<>();
            contractMaterials.forEach(contractMaterial -> {
                if (StringUtil.notEmpty(contractMaterial.getMaterialId())) {
                    materialIds.add(contractMaterial.getMaterialId());
                }
                /**
                 * 10_OU_西安隆基,  校验是否总部必填
                 */
                if ("10_OU_西安隆基".equals(contractMaterial.getBuName())) {
                    Assert.notNull(contractHead.getIsHeadquarters(), "是否总部不能为空");
                }
            });
            if (CollectionUtils.isNotEmpty(materialIds)) {
                boolean flag = baseClient.checkBigClassCodeIsContain50(materialIds);
                Assert.isTrue(!flag, "存在物料大类编码为50的不允许创建合同");
            }
        }

        /**
         * 校验付款比例不能超过100
         */
        if (CollectionUtils.isNotEmpty(payPlans)) {
            BigDecimal sum = BigDecimal.ZERO;
            for (PayPlan payPlan : payPlans) {
                if (StringUtil.notEmpty(payPlan.getPaymentRatio())) {
                    sum = sum.add(payPlan.getPaymentRatio());
                }
            }
            if (sum.compareTo(new BigDecimal(100)) != 0) {
                throw new BaseException("阶段付款比例总和必须等于100;");
            }
        }

        // 检查合同变更或补充协议是否有上传附件
        if (StringUtil.notEmpty(contractHead.getContractType()) && !ContractType.MIAN_CONTRACT_ADD.name().equals(contractHead.getContractType())) {
            Assert.isTrue(CollectionUtils.isNotEmpty(annexes), "请上传补充附件");
            List<Annex> annexList = annexes.stream().filter(annex -> StringUtil.isEmpty(annex.getSourceId())).collect(Collectors.toList());
            Assert.isTrue(CollectionUtils.isNotEmpty(annexList), "请上传补充附件");
        }

        /**
         * 1、是否框架协议为否，合同级别系统自动维护，无需用户维护
         * （1）若供应商主档上，存在“是否A级”标识为Y时，合同级别为A级关联；
         * （2）若供应商主档上，存在“是否A级”标识为N时，合同级别按照合同定级规则维护获取；
         *
         * 2、是否框架协议为是，合同级别系统自动维护，无需用户维护
         * （1）若供应商主档上，存在“是否A级”标识为Y时，合同级别为A级关联；
         * （2）若供应商主档上，存在“是否A级”标识为N时，合同级别为A级；
         */
        // 当合同新建是判断
        if (StringUtil.isEmpty(contractHead.getContractHeadId()) && StringUtil.isEmpty(contractHead.getContractLevel())) {
            Assert.notNull(contractHead.getIsFrameworkAgreement(), "框架协议不能为空");
            Assert.notNull(contractHead.getVendorId(), "供应商不能为空");
            CompanyInfo companyInfo = supplierClient.getCompanyInfo(contractHead.getVendorId());
            Assert.notNull(companyInfo, "找不到供应商信息");
            if (YesOrNo.YES.getValue().equals(contractHead.getIsFrameworkAgreement())) {
                // 框架协议
                if (YesOrNo.YES.getValue().equals(companyInfo.getCompanyLevel())) {
                    // 存在“是否A级”标识为Y时，合同级别为A级关联；
                    contractHead.setContractLevel("SA");
                } else {
                    // 存在“是否A级”标识为N时，合同级别为A级；
                    contractHead.setContractLevel("A");
                }
            } else {
                // 非框架协议
                if (YesOrNo.YES.getValue().equals(companyInfo.getCompanyLevel())) {
                    // 存在“是否A级”标识为Y时，合同级别为A级关联；
                    contractHead.setContractLevel("SA");
                } else {
                    // 存在“是否A级”标识为N时，合同级别按照合同定级规则维护获取；
                    String contractLevel = getContractLevel(contractMaterials);
                    Assert.notNull(contractLevel, "检查合同物料的小类和金额是否已维护,请检查物料小类对应的合同定级规则是否已维护!!!");
                    contractHead.setContractLevel(contractLevel);
                }
            }
        }
        // 合同新建的时候检查获取合同级别
//        if (StringUtil.isEmpty(contractHead.getContractHeadId()) && StringUtil.isEmpty(contractHead.getContractLevel())) {
//            Assert.notNull(contractHead.getIsFrameworkAgreement(), "框架协议不能为空");
//            if (YesOrNo.NO.getValue().equals(contractHead.getIsFrameworkAgreement())) {
//                String contractLevel = getContractLevel(contractMaterials);
//                Assert.notNull(contractLevel, "检查合同物料的小类和金额是否已维护,请检查物料小类对应的合同定级规则是否已维护!!!");
//                contractHead.setContractLevel(contractLevel);
//            } else {
//                throw new BaseException("合同级别不能为空");
//            }
//        }

        Assert.notNull(contractHead.getBuId(), "请选择送审业务实体");
        if (ContractType.MIAN_CONTRACT_ALTER.name().equals(contractHead.getContractType())) {
            StringBuffer errorMsg = new StringBuffer();
            AtomicBoolean flag1 = new AtomicBoolean(true);
            AtomicBoolean flag2 = new AtomicBoolean(true);
            if (CollectionUtils.isNotEmpty(contractMaterials)) {
                HashSet<String> hashSet = new HashSet<>();
                contractMaterials.forEach(contractMaterial -> {
                    if (StringUtil.notEmpty(contractMaterial.getSourceId())) {
                        /**
                         * TODO 待采购订单开发完在做校验
                         * 检查物料是否被采购订单引用
                         * 1、可对原合同物料行明细调整数量，或单价，但不可小于合同已下单部分（数量及金额，无需校验单价，单价可提高或降低，降低时校验最调整后物料金额是否小于已下单金额）；
                         */
                    }

                    /**
                     * 1. 寻源单号+业务实体+库存组织+交货地点+物料编码+含税单价+有效期起+有效期至唯一
                     */
                    if (StringUtil.notEmpty(contractMaterial.getSourceNumber())) {
                        String onlyKey = String.valueOf(contractMaterial.getSourceNumber()) + contractMaterial.getBuId() + contractMaterial.getInvId() +
                                contractMaterial.getTradingLocations() + contractMaterial.getMaterialCode() +
                                contractMaterial.getStartDate() + contractMaterial.getEndDate();
                        boolean flag = hashSet.add(onlyKey);
                        if (!flag) {
                            flag1.set(false);
                        }
                    } else {
                        // 校验是否与原有物料行的组织、物料编码一致，若一致，需在原物料行进行修改；
                        String onlyKey = String.valueOf(contractMaterial.getBuId()) + contractMaterial.getInvId() +
                                contractMaterial.getTradingLocations() + contractMaterial.getMaterialCode() +
                                contractMaterial.getStartDate() + contractMaterial.getEndDate();
                        boolean flag = hashSet.add(onlyKey);
                        if (!flag) {
                            flag2.set(false);
                        }
                    }
                });
                Assert.isTrue(flag1.get(), "寻源物料行: 寻源单号+业务实体+库存组织+交货地点+物料编码+有效期起+有效期,存在重复");
                Assert.isTrue(flag2.get(), "手动添加物料行: 业务实体+库存组织+交货地点+物料编码+有效期起+有效期,存在重复");

                /**
                 * 寻源单号+业务实体+库存组织+交货地点+物料编码+价格重复, 有效期不能重复
                 */
//                Map<String, List<ContractMaterial>> map = contractMaterials.stream().collect(Collectors.groupingBy(contractMaterial -> {
//                    return contractMaterial.getSourceNumber() + contractMaterial.getBuId() + contractMaterial.getInvId() +
//                            contractMaterial.getTradingLocations() + contractMaterial.getMaterialCode() + contractMaterial.getContractQuantity();
//                }));
//                map.forEach((s, contractMaterials1) -> {
//                    if(CollectionUtils.isNotEmpty(contractMaterials1)){
//                        // 判断价格有效期不能重叠
//                    }
//                });
            }

            if (CollectionUtils.isNotEmpty(payPlans)) {
                BigDecimal sum = BigDecimal.ZERO;
                for (PayPlan payPlan : payPlans) {

                    /**
                     * 2、编辑时，需校验对应付款计划行是否被拟定或审批中的付款申请引用；若是，则不可编辑对应付款计划行；反之，调整后的阶段付款金额不可大于对应阶段已付金额
                     */
                    // 获取原付款计划id
                    Long sourceId = payPlan.getSourceId();
                    if (StringUtil.notEmpty(sourceId)) {
                        CeeaPaymentApplyDTO ceeaPaymentApplyDTO = pmClient.getPaymentApplyByPayPlanId(sourceId);
                        if (null != ceeaPaymentApplyDTO && null != ceeaPaymentApplyDTO.getCeeaPaymentApplyHead()
                                && null != ceeaPaymentApplyDTO.getCeeaPaymentApplyHead().getReceiptStatus()) {
                            String receiptStatus = ceeaPaymentApplyDTO.getCeeaPaymentApplyHead().getReceiptStatus();
                            if (!PaymentStatus.DRAFT.getKey().equals(receiptStatus) && !PaymentStatus.UNDER_APPROVAL.getKey().equals(receiptStatus)) {
                                // 阶段付款金额不可大于对应阶段已付金额
                                List<CeeaPaymentApplyPlan> ceeaPaymentApplyPlans = ceeaPaymentApplyDTO.getCeeaPaymentApplyPlans();
                                if (CollectionUtils.isNotEmpty(ceeaPaymentApplyPlans)) {
                                    ceeaPaymentApplyPlans.forEach(ceeaPaymentApplyPlan -> {
                                        if (sourceId.equals(ceeaPaymentApplyPlan.getPayPlanId())) {
                                            // 获取已付金额
                                            BigDecimal paidAmountNoTax = ceeaPaymentApplyPlan.getPaidAmountNoTax();
                                            if (payPlan.getStagePaymentAmount().compareTo(paidAmountNoTax) < 0) {
                                                errorMsg.append("付款期数" + payPlan.getPaymentPeriod() + "的阶段付款金额不可大于对应阶段已付金额").append("\n");
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                    // 阶段付款金额总和不可大于合同总金额
                    sum = sum.add(payPlan.getStagePaymentAmount());
                }
                /**
                 * 可新增付款计划行，但阶段付款金额总和不可大于合同总金额
                 */
                if (sum.compareTo(contractHead.getIncludeTaxAmount()) > 0) {
                    errorMsg.append("阶段付款金额总和不可大于合同总金额;").append("\n");
                }
            }

            if (errorMsg.length() > 0) {
                throw new BaseException(errorMsg.toString());
            }
        }
    }

    @Override
    public ContractDTO getContractDTOSecond(Long contractHeadId, String sourceId) {
        Assert.notNull(contractHeadId, "contractHeadId不能为空");
        ContractDTO contractDTO = new ContractDTO();
        ContractHead contractHead = this.getById(contractHeadId);

        boolean flag = false;
        if (StringUtil.notEmpty(sourceId) && "get".equals(sourceId)) {
            flag = true;
        }

        List<ModelLine> modelLines = iModelLineService.list(new QueryWrapper<>(new ModelLine().setContractHeadId(contractHeadId)));
        // 付款计划
        List<PayPlan> payPlans = iPayPlanService.list(new QueryWrapper<>(new PayPlan().setContractHeadId(contractHeadId)));
        if (CollectionUtils.isNotEmpty(payPlans) && flag) {
            payPlans.forEach(payPlan -> {
                Long payPlanId = payPlan.getPayPlanId();
                payPlan.setSourceId(payPlanId);
                payPlan.setIsEdit("Y");
                // 累计已付金额
                BigDecimal paidAmountNoTax = BigDecimal.ZERO;
                // 校验付款申请是否被引用, 状态: 拟定或审批中
                CeeaPaymentApplyDTO ceeaPaymentApplyDTO = pmClient.getPaymentApplyByPayPlanId(payPlanId);
                if (null != ceeaPaymentApplyDTO && null != ceeaPaymentApplyDTO.getCeeaPaymentApplyHead()
                        && null != ceeaPaymentApplyDTO.getCeeaPaymentApplyHead().getReceiptStatus()) {
                    String receiptStatus = ceeaPaymentApplyDTO.getCeeaPaymentApplyHead().getReceiptStatus();
                    if (PaymentStatus.DRAFT.getKey().equals(receiptStatus) || PaymentStatus.UNDER_APPROVAL.getKey().equals(receiptStatus)) {
                        payPlan.setIsEdit("N");
                    } else {
                        // 累计已付金额
                        List<CeeaPaymentApplyPlan> ceeaPaymentApplyPlans = ceeaPaymentApplyDTO.getCeeaPaymentApplyPlans();
                        if (CollectionUtils.isNotEmpty(ceeaPaymentApplyPlans)) {
                            for (CeeaPaymentApplyPlan ceeaPaymentApplyPlan : ceeaPaymentApplyPlans) {
                                if (StringUtil.notEmpty(ceeaPaymentApplyPlan.getPayPlanId()) && payPlanId.compareTo(ceeaPaymentApplyPlan.getPayPlanId()) == 0) {
                                    BigDecimal price = ceeaPaymentApplyPlan.getPaidAmountNoTax();
                                    paidAmountNoTax = paidAmountNoTax.add(price);
                                }
                            }
                        }
                    }
                }
                // 查询已付金额
                payPlan.setPaidAmount(paidAmountNoTax);
            });
        }
        // 物料明细
        List<ContractMaterial> contractMaterials = iContractMaterialService
                .list(new QueryWrapper<>(new ContractMaterial().setContractHeadId(contractHeadId)));
        if (CollectionUtils.isNotEmpty(contractMaterials) && flag) {
            contractMaterials.forEach(contractMaterial -> {
                Long contractMaterialId = contractMaterial.getContractMaterialId();
                contractMaterial.setSourceId(contractMaterialId);
            });
        }

        // 合作伙伴
        List<ContractPartner> contractPartners = iContractPartnerService
                .list(new QueryWrapper<>(new ContractPartner().setContractHeadId(contractHeadId)));
        if (CollectionUtils.isNotEmpty(contractPartners) && flag) {
            contractPartners.forEach(contractPartner -> {
                Long partnerId = contractPartner.getPartnerId();
                contractPartner.setSourceId(partnerId);
            });
        }

        // 附件
        List<Annex> annexes = iAnnexService.list(new QueryWrapper<>(new Annex().setContractHeadId(contractHeadId)));
        if (CollectionUtils.isNotEmpty(annexes) && flag) {
            annexes.forEach(annex -> {
                if (StringUtil.notEmpty(annex.getFileuploadId())) {
                    Long annexId = annex.getAnnexId();
                    annex.setSourceId(annexId);
                }
            });
        }

        // 获取合同关闭附件
        List<CloseAnnex> closeAnnexes = iCloseAnnexService.list(new QueryWrapper<>(new CloseAnnex().setContractHeadId(contractHeadId)));

        // 合并附件
        if (CollectionUtils.isNotEmpty(closeAnnexes)) {
            closeAnnexes.forEach(closeAnnex -> {
                Annex annex = new Annex();
                annex.setFileuploadId(closeAnnex.getFileuploadId());
                annex.setFileSourceName(closeAnnex.getFileSourceName());
                annex.setCreatedBy(closeAnnex.getCreatedBy());
                annex.setCreationDate(closeAnnex.getCreationDate());
                annexes.add(annex);
            });
        }

        contractDTO.setContractHead(contractHead)
                .setModelLines(modelLines)
                .setAnnexes(annexes)
                .setPayPlans(payPlans)
                .setContractMaterials(contractMaterials)
                .setContractPartners(contractPartners)
                .setCloseAnnexes(closeAnnexes);
        return contractDTO;
    }

    @Override
    @Transactional
    public void buyerDeleteSecond(Long contractHeadId) {
        this.removeById(contractHeadId);
        iPayPlanService.remove(new QueryWrapper<>(new PayPlan().setContractHeadId(contractHeadId)));
        iContractMaterialService.remove(new QueryWrapper<>(new ContractMaterial().setContractHeadId(contractHeadId)));
        iModelLineService.remove(new QueryWrapper<>(new ModelLine().setContractHeadId(contractHeadId)));
        iAnnexService.remove(new QueryWrapper<>(new Annex().setContractHeadId(contractHeadId)));
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                inqClient.resetItemsBelongNumber(Collections.singletonList(contractHeadId));
            }
        });
    }

    private void setMaterialList(ContractHead contractHead, ModelLine modelLine) {
        if (ModelKey.materialList.name().equals(modelLine.getModelKey())) {
            String materialListJsonString = modelLine.getModelValue();
            List<ContractMaterial> contractMaterials = new ArrayList<>();
            try {
                contractMaterials = JSONArray.parseArray(materialListJsonString, ContractMaterial.class);
            } catch (Exception e) {
                log.error("操作失败", e);
                throw new BaseException("合同物料清单格式转换出错");
            }
            //保存或编辑合同物料清单
            saveOrUpdateContractMaterials(contractHead, contractMaterials);
            materialListJsonString = JSON.toJSONString(contractMaterials);
            modelLine.setModelValue(materialListJsonString);
        }
    }

    private void setPayPlan(ContractHead contractHead, ModelLine modelLine) {
        if (ModelKey.payPlan.name().equals(modelLine.getModelKey())) {
            String payPlanJsonString = modelLine.getModelValue();
            List<PayPlan> payPlans = new ArrayList<>();
            try {
                payPlans = JSONArray.parseArray(payPlanJsonString, PayPlan.class);
            } catch (Exception e) {
                log.error("操作失败", e);
                throw new BaseException("合同付款计划格式转换出错");
            }
            //保存或编辑合同付款计划
            saveOrUpdatePayPlans(contractHead, payPlans);
            payPlanJsonString = JSON.toJSONString(payPlans);
            modelLine.setModelValue(payPlanJsonString);
        }
    }


    //设置单位相关字段
    private void setUnit(ContractMaterial contractMaterial, String unitCode) {
        if (StringUtils.isNotBlank(unitCode)) {
            List<PurchaseUnit> purchaseUnits = baseClient.listPurchaseUnitByParam(new PurchaseUnit().setUnitCode(unitCode));
            if (!CollectionUtils.isEmpty(purchaseUnits)) {
                PurchaseUnit purchaseUnit = purchaseUnits.get(0);
                contractMaterial.setUnitName(purchaseUnit.getUnitName());
                contractMaterial.setUnitId(purchaseUnit.getUnitId());
                contractMaterial.setUnitCode(unitCode);
            }
        }
    }


    private void setCategory(ContractMaterial contractMaterial, Long categoryId) {
        if (categoryId != null) {
            PurchaseCategory purchaseCategory = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(categoryId));
            if (purchaseCategory != null) {
                contractMaterial.setCategoryId(categoryId)
                        .setCategoryCode(purchaseCategory.getCategoryCode())
                        .setCategoryName(purchaseCategory.getCategoryName());
            }
        }
    }

    private void vendorCheckBeforeSubmit(ContractHead contractHead) {
        if (!ContractStatus.SUPPLIER_CONFIRMING.name().equals(contractHead.getContractStatus())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("待供应商确认状态的合同,才可提交"));
        }
    }

    private void vendorCheckBeforeReject(ContractHead contractHead) {
        if (!ContractStatus.SUPPLIER_CONFIRMING.name().equals(contractHead.getContractStatus())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("待供应商确认状态的合同,才可驳回"));
        }
        Assert.hasText(contractHead.getVendorRejectReason(), LocaleHandler.getLocaleMsg("请填写供应商驳回原因"));
    }

    private void buyerCheckBeforePublish(ContractHead contractHead) {
        if (!ContractStatus.UNPUBLISHED.name().equals(contractHead.getContractStatus())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("未通过审核的合同,不可发布"));
        }
    }

    private void vendorCheckBeforeSaveOrUpdate(ContractHeadDTO contractHeadDTO) {
        if (!ContractStatus.SUPPLIER_CONFIRMING.name().equals(contractHeadDTO.getContractStatus())) {
            throw new BaseException("非供应商待确认状态,不得修改");
        }
    }

    private void buyerCheckBeforeSaveOrUpdate(ContractHead contractHead) {
        Assert.notNull(contractHead, "contractHead不能为空");
        if (StringUtils.isNotBlank(contractHead.getContractStatus())
                && (!ContractStatus.DRAFT.name().equals(contractHead.getContractStatus())
                && !ContractStatus.REJECTED.name().equals(contractHead.getContractStatus()))) {
            throw new BaseException("非拟定或非驳回状态,不得修改");
        }
    }

    private void bindingFileuploads(ContractHead contractHead, List<Fileupload> fileuploads) {
        if (!CollectionUtils.isEmpty(fileuploads)) {
            List<Fileupload> collect = fileuploads.stream().filter(fileupload -> fileupload.getBusinessId() == null)
                    .collect(Collectors.toList());
            fileCenterClient.bindingFileupload(collect, contractHead.getContractHeadId());
        }
    }

    @Transactional
    public void saveOrUpdatePayPlans(ContractHead contractHead, List<PayPlan> payPlans) {
        // 删掉原来的再新增
        iPayPlanService.remove(new QueryWrapper<>(new PayPlan().setContractHeadId(contractHead.getContractHeadId())));
        if (CollectionUtils.isNotEmpty(payPlans)) {
            payPlans.forEach(payPlan -> {
                payPlan.setPayPlanId(IdGenrator.generate());
                payPlan.setContractHeadId(contractHead.getContractHeadId());
            });
            iPayPlanService.saveBatch(payPlans);
        }
    }

    @Transactional
    public void saveOrUpdateContractMaterials(ContractHead contractHead, List<ContractMaterial> contractMaterials) {
        // 把原来的删掉再新增
        iContractMaterialService.remove(new QueryWrapper<>(new ContractMaterial().setContractHeadId(contractHead.getContractHeadId())));

        if (CollectionUtils.isNotEmpty(contractMaterials)) {
            contractMaterials.forEach(contractMaterial -> {
                contractMaterial.setContractHeadId(contractHead.getContractHeadId());
                contractMaterial.setContractMaterialId(IdGenrator.generate());
            });
            iContractMaterialService.saveBatch(contractMaterials);
        }
    }

    @Transactional
    public void saveOrUpdateContractPartners(ContractHead contractHead, List<ContractPartner> contractPartners) {
        // 先删除原来的再新增
        iContractPartnerService.remove(new QueryWrapper<>(new ContractPartner().setContractHeadId(contractHead.getContractHeadId())));
        if (CollectionUtils.isNotEmpty(contractPartners)) {
            contractPartners.forEach(contractPartner -> {
                contractPartner.setContractHeadId(contractHead.getContractHeadId());
                contractPartner.setPartnerId(IdGenrator.generate());
            });
            iContractPartnerService.saveBatch(contractPartners);
        }
    }

    private void saveOrUpdateContractLines(ContractHead contractHead, List<ContractLine> contractLines) {
        if (!CollectionUtils.isEmpty(contractLines)) {
            contractLines.forEach(contractLine -> {
                if (contractLine.getContractLineId() == null) {
                    contractLine.setContractLineId(IdGenrator.generate())
                            .setContractHeadId(contractHead.getContractHeadId());
                    iContractLineService.save(contractLine);
                } else {
                    iContractLineService.updateById(contractLine);
                }
            });
        }
    }

    private void saveOrUpdateContractHead(ContractHead contractHead, String contractStatus) {
        Assert.notNull(contractHead, "contractHead不能为空");
        if (contractHead.getContractHeadId() == null) {
            long id = IdGenrator.generate();
            contractHead.setContractHeadId(id)
                    .setContractStatus(contractStatus)
                    .setContractNo(baseClient.seqGen(SequenceCodeConstant.SEQ_CONTRACT_NO));
            this.save(contractHead);
        } else {
            contractHead.setContractStatus(contractStatus);
            this.updateById(contractHead);
        }
    }

    @Override
    @Transactional
    public void buyerSubmitApprovalSecond(ContractDTO contractDTO) throws ParseException {
    	
    	this.buyerSaveOrUpdateContractDTOSecond(contractDTO, "DRAFT");
    	
        // 保存把状态更改为待审核
        Assert.notNull(contractDTO.getContractHead(), "合同基本信息不能为空");
        
//        Assert.notNull(contractDTO.getContractHead().getContractLevel(), "合同级别不能为空,请先暂存");
    
        // 至少存在一个甲方和一个乙方才能提交
        List<ContractPartner> partners = contractDTO.getContractPartners();
        if (CollectionUtils.isNotEmpty(partners) && partners.size() >= 2) {
            Set<String> onlyKeys = new HashSet<>();
            partners.forEach(contractPartner -> {
                if ("甲方".equals(contractPartner.getPartnerType()) || "乙方".equals(contractPartner.getPartnerType())) {
                    onlyKeys.add(contractPartner.getPartnerType());
                }
            });
            Assert.isTrue(onlyKeys.size() == 2, "合作伙伴必须至少存在一个甲方和一个乙方");
        } else {
            throw new BaseException("合作伙伴必须至少存在一个甲方和一个乙方");
        }

        /**
         * 虚拟合同校验
         * 1. 框架协议必须维护
         */
        if (YesOrNo.YES.getValue().equals(contractDTO.getContractHead().getCeeaIfVirtual())) {
            Assert.notNull(contractDTO.getContractHead().getFrameworkAgreementCode(), "框架协议不能空!");
        }
        if (!ContractType.MIAN_CONTRACT_ADD.name().equals(contractDTO.getContractHead().getContractType())) {
            Assert.notNull(contractDTO.getContractHead().getCeeaContractOldId(), "原合同ID为空,不能提交");
        }
        // 保存合同
        if (!YesOrNo.YES.getValue().equals(contractDTO.getContractHead().getCeeaIfVirtual())) {
            buyerSaveOrUpdateContractDTOSecond(contractDTO, ContractStatus.UNDER_REVIEW.name());
        } else {
            // 虚拟合同直接归档
            contractDTO.getContractHead().setStartDate(LocalDate.now());
            buyerSaveOrUpdateContractDTOSecond(contractDTO, ContractStatus.ARCHIVED.name());
        }

        // 合同覆盖
        if (ContractType.MIAN_CONTRACT_ALTER.name().equals(contractDTO.getContractHead().getContractType())) {
            // 原合同ID
            Long contractOldId = contractDTO.getContractHead().getCeeaContractOldId();
            ContractHead contract = this.getById(contractOldId);
            Assert.notNull(contract, "找不到原合同");
            // 原合同头ID
            Long contractHeadId = contract.getContractHeadId();
            /**
             * 如果合同类型为变更合同, 那么就把数据覆盖回源合同, 并表示是否变更字段
             */
            List<ContractMaterial> contractMaterials = contractDTO.getContractMaterials();
            if (CollectionUtils.isNotEmpty(contractMaterials)) {
                contractMaterials.forEach(contractMaterial -> {
                    Long sourceId = contractMaterial.getSourceId();
                    if (StringUtil.notEmpty(sourceId)) {
                        contractMaterial.setContractMaterialId(sourceId);
                        contractMaterial.setContractHeadId(contractHeadId);
                        contractMaterial.setCreatedBy(null);
                        contractMaterial.setCreationDate(null);
                        contractMaterial.setLastUpdatedBy(null);
                        contractMaterial.setSourceId(null);
                        iContractMaterialService.updateById(contractMaterial);
//                        // 获取原合同物料, 对比字段差异
//                        ContractMaterial contractMaterial1 = iContractMaterialService.getById(sourceId);
//                        if (null != contractMaterial1) {
//                            // 获取差异字段
//                            Map<String, Object> objectOld = BeanMapUtils.beanToMap(contractMaterial1);
//                            Map<String, Object> objectNew = BeanMapUtils.beanToMap(contractMaterial);
//                            List<String> keys = Arrays.asList("sourceNumber", "sourceLineNumber", "materialId",
//                                    "specification", "categoryId", "amount", "contractQuantity", "orderQuantity", "untaxedPrice",
//                                    "taxedPrice", "priceUnit", "peoplePrice", "materialPrice", "taxKey", "unitCode",
//                                    "unitId", "deliveryDate", "isAcceptance", "acceptanceQuantity", "startDate", "endDate",
//                                    "currency", "organizationId", "vendorId", "buId", "tradingLocations", "unAmount",
//                                    "manufacturer", "placeOfOrigin", "isInstallDebug", "shelfLife", "lineRemark",
//                                    "itemNumber", "itemName", "taskNumber", "taskName", "shipFrom", "destination");
//
////                                StringBuffer changeField = new StringBuffer();
//                            for (String key : keys) {
//                                if (null != objectOld.get(key) && !objectOld.get(key).equals(objectNew.get(key))) {
//                                    contractMaterial.setChangeField("Y");
//                                    break;
//                                } else if (null != objectNew.get(key)) {
//                                    contractMaterial.setChangeField("Y");
//                                    break;
//                                }
//                                contractMaterial.setChangeField("N");
//                            }
//
//                            // 更新原合同数据
//                            contractMaterial.setContractHeadId(contractMaterial1.getContractHeadId());
//                            contractMaterial.setContractMaterialId(contractMaterial1.getContractMaterialId());
////                                String str = changeField.toString();
////                                contractMaterial.setChangeField(StringUtils.left(str, str.length() - 1));
//                            iContractMaterialService.updateById(contractMaterial);
//                        }
                    } else {
                        contractMaterial.setContractHeadId(contractHeadId);
                        contractMaterial.setContractMaterialId(IdGenrator.generate());
                        contractMaterial.setChangeField("Y");
                        iContractMaterialService.save(contractMaterial);
                    }
                });
            }

            /**
             * 变更提交成功后，覆盖在原合同中体现，并标识区分是否变更；
             */
            List<PayPlan> payPlans = contractDTO.getPayPlans();
            if (CollectionUtils.isNotEmpty(payPlans)) {
                payPlans.forEach(payPlan -> {
                    Long sourceId = payPlan.getSourceId();
                    if (StringUtil.notEmpty(sourceId)) {
                        payPlan.setPayPlanId(sourceId);
                        payPlan.setContractHeadId(contractHeadId);
                        payPlan.setCreatedBy(null);
                        payPlan.setCreationDate(null);
                        payPlan.setLastUpdatedBy(null);
                        payPlan.setSourceId(null);
                        iPayPlanService.updateById(payPlan);
//                        // 获取原合同付款计划, 对比字段差异
//                        PayPlan payPlan1 = iPayPlanService.getById(sourceId);
//                        if (null != payPlan1) {
//
//                            // 获取差异字段
//                            Map<String, Object> objectOld = BeanMapUtils.beanToMap(payPlan1);
//                            Map<String, Object> objectNew = BeanMapUtils.beanToMap(payPlan);
//                            List<String> keys = Arrays.asList("payTypeId", "payType", "payStage", "payExplain", "payRatio",
//                                    "deductionRatio", "payMethod", "delayedDays", "excludeTaxPayAmount", "taxKey", "payTax",
//                                    "currencyCode", "payDate", "logicalExplain", "payStatus", "paidAmount", "startDate",
//                                    "endDate", "paymentPeriod", "paymentStage", "dateNum", "stagePaymentAmount", "paymentRatio",
//                                    "plannedPaymentDate");
////                                StringBuffer changeField = new StringBuffer();
//                            for (String key : keys) {
//                                if (null != objectOld.get(key) && !objectOld.get(key).equals(objectNew.get(key))) {
//                                    payPlan.setChangeField("Y");
//                                    break;
//                                } else if (null != objectNew.get(key)) {
//                                    payPlan.setChangeField("Y");
//                                    break;
//                                }
//                                payPlan.setChangeField("N");
//                            }
//                            payPlan.setContractHeadId(payPlan1.getContractHeadId());
//                            payPlan.setPayPlanId(payPlan1.getPayPlanId());
////                                String str = changeField.toString();
////                                payPlan.setChangeField(StringUtils.left(str, str.length() - 1));
//                            iPayPlanService.updateById(payPlan);
//                        }
                    } else {
                        payPlan.setContractHeadId(contractHeadId);
                        payPlan.setPayPlanId(IdGenrator.generate());
                        payPlan.setChangeField("Y");
                        iPayPlanService.save(payPlan);
                    }
                });
            }

            /**
             * 变更提交成功后，覆盖在原合同中体现，并标识区分是否变更；
             */
            List<ContractPartner> contractPartners = contractDTO.getContractPartners();
            if (CollectionUtils.isNotEmpty(contractPartners)) {
                contractPartners.forEach(contractPartner -> {
                    Long sourceId = contractPartner.getSourceId();
                    if (StringUtil.notEmpty(sourceId)) {
                        contractPartner.setPartnerId(sourceId);
                        contractPartner.setContractHeadId(contractHeadId);
                        contractPartner.setCreatedBy(null);
                        contractPartner.setCreationDate(null);
                        contractPartner.setLastUpdatedBy(null);
                        contractPartner.setSourceId(null);
                        iContractPartnerService.updateById(contractPartner);
//                        // 获取原合同合作伙伴, 对比字段差异
//                        ContractPartner contractPartner1 = iContractPartnerService.getById(sourceId);
//                        if (null != contractPartner1) {
//                            // 获取差异字段
//                            Map<String, Object> objectOld = BeanMapUtils.beanToMap(contractPartner1);
//                            Map<String, Object> objectNew = BeanMapUtils.beanToMap(contractPartner);
//                            List<String> keys = Arrays.asList("partnerType", "partnerName", "contactName", "phone",
//                                    "address", "bankName", "bankAccount", "postCode", "taxNumber", "startDate",
//                                    "endDate", "enable");
//                            StringBuffer changeField = new StringBuffer();
//                            for (String key : keys) {
//                                if (null != objectOld.get(key) && !objectOld.get(key).equals(objectNew.get(key))) {
//                                    contractPartner.setChangeField("Y");
//                                    break;
//                                } else if (null != objectNew.get(key)) {
//                                    contractPartner.setChangeField("Y");
//                                    break;
//                                }
//                                contractPartner.setChangeField("N");
//                            }
//                            contractPartner.setContractHeadId(contractPartner1.getContractHeadId());
//                            contractPartner.setPartnerId(contractPartner1.getPartnerId());
////                                String str = changeField.toString();
////                                contractPartner.setChangeField(StringUtils.left(str, str.length() - 1));
//                            iContractPartnerService.updateById(contractPartner);
//                        }
                    } else {
                        contractPartner.setContractHeadId(contractHeadId);
                        contractPartner.setPartnerId(IdGenrator.generate());
                        contractPartner.setChangeField("Y");
                        iContractPartnerService.save(contractPartner);
                    }
                });
            }

            /**
             * 2、合同变更提交并审批通过后，需更新至原合同附件中
             */
            List<Annex> annexes = contractDTO.getAnnexes();
            if (CollectionUtils.isNotEmpty(annexes)) {
                annexes.forEach(annex -> {
                    Long sourceId = annex.getSourceId();
                    if (StringUtil.isEmpty(sourceId)) {
                        annex.setContractHeadId(contractHeadId);
                        annex.setAnnexId(IdGenrator.generate());
                        annex.setChangeField("Y");
                        iAnnexService.save(annex);
                    }
                });
            }
        } else if (ContractType.SUPPLEMENTAL_AGREEMENT.name().equals(contractDTO.getContractHead().getContractType())) {
            Long ceeaContractOldId = contractDTO.getContractHead().getCeeaContractOldId();
            Assert.notNull(ceeaContractOldId, "原合同ID不能为空");
            ContractHead head = this.getById(ceeaContractOldId);
            Assert.notNull(head, "找不到原合同,ceeaContractOldId:" + ceeaContractOldId);
            if (null != head) {
                ContractHead contractHead = head;
                if (null != contractHead) {
                    Long contractHeadId = contractHead.getContractHeadId();
                    /**
                     * 2、合同变更提交并审批通过后，需更新至原合同附件中
                     */
                    List<Annex> annexes = contractDTO.getAnnexes();
                    if (CollectionUtils.isNotEmpty(annexes)) {
                        annexes.forEach(annex -> {
                            Long sourceId = annex.getSourceId();
                            if (StringUtil.isEmpty(sourceId)) {
                                annex.setContractHeadId(contractHeadId);
                                annex.setAnnexId(IdGenrator.generate());
                                iAnnexService.save(annex);
                            }
                        });
                    }
                }
            }
        }

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                /**
                 * 虚拟合同不需要走审批流, 状态直接转为已归档
                 */
                if (!YesOrNo.YES.getValue().equals(contractDTO.getContractHead().getCeeaIfVirtual())) {
                    /**
                     * 合同逻辑全部处理完再提交审批流
                     */
                    /* Begin by chenwt24@meicloud.com   2020-10-16 */

                    String formId = null;
                    try {
                        formId = contractFlow.submitContractDTOConfFlow(contractDTO);
                    } catch (Exception e) {
                        throw new BaseException(e.getMessage());
                    }
                    if (StringUtils.isEmpty(formId)) {
                        throw new BaseException(LocaleHandler.getLocaleMsg("提交OA审批失败"));
                    }
                } else {
                    // 虚拟合同提交直接归档, 推送到费控
//                    try {
//                        Response response = pushContractInfo(contractDTO.getContractHead().getContractHeadId());
//                        if ("E".equals(response.getEsbInfo().getReturnStatus())) {
//                            log.error("同步合同到费控系统中失败" + response.getEsbInfo().getReturnMsg());
//                            throw new BaseException(response.getEsbInfo().getReturnMsg());
//                        }
//                    } catch (BaseException e) {
//                        throw new BaseException("归档失败! " + e.getMessage());
//                    } catch (Exception e) {
//                        log.error("合同归档失败", e);
//                        throw new BaseException("归档失败! ");
//                    }

                }
            }
        });
    }

    @Override
    @Transactional
    public void cratePriceChangeSource(List<ContractMaterial> contractMaterials) {
        if (CollectionUtils.isNotEmpty(contractMaterials)) {
            Long contractHeadId = contractMaterials.get(0).getContractHeadId();
            ContractHead contractHead = this.getById(contractHeadId);
            String contractCode = contractHead.getContractCode();
            // 校验物料数据
            checkItemInfo(contractMaterials);
            // 寻源类型
            String sourceType = contractMaterials.get(0).getSourceType();
            // 回写寻缘单号
            String bidingNum = null;
            if (SourcingType.TENDER.getItemValue().equals(sourceType)) {
                // 招标
                SourceForm sourceForm = new SourceForm();
                Biding biding = new Biding();
                biding.setSourceFrom(SourceFrom.CONTRACT.getItemValue());
                BidRequirement bidRequirement = new BidRequirement();
                List<BidRequirementLine> bidRequirementLines = new ArrayList<>();
                List<BidVendor> bidVendors = new ArrayList<>();
                sourceForm.setBidding(biding);
                sourceForm.setBidVendors(bidVendors);
                sourceForm.setDemandHeader(bidRequirement);

                contractMaterials.forEach(contractMaterial -> {
                    BidRequirementLine bidRequirementLine = BidRequirementLine.builder()
                            .targetId(contractMaterial.getMaterialId())
                            .targetNum(contractMaterial.getMaterialCode())
                            .targetDesc(contractMaterial.getMaterialName())
                            .categoryId(contractMaterial.getCategoryId())
                            .categoryCode(contractMaterial.getCategoryCode())
                            .categoryName(contractMaterial.getCategoryName())
                            .quantity(contractMaterial.getContractQuantity().doubleValue())
                            .fullPathId(contractMaterial.getBuFullPathId())
                            .orgId(contractMaterial.getBuId())
                            .orgCode(contractMaterial.getBuCode())
                            .orgName(contractMaterial.getBuName())
                            .invId(contractMaterial.getInvId())
                            .invCode(contractMaterial.getInvCode())
                            .invName(contractMaterial.getInvName())
                            .awardedSupplierId(contractMaterial.getVendorId())
                            .awardedSupplierName(contractMaterial.getVendorName())
                            .deliveryPlace(contractMaterial.getTradingLocations())
                            .fromContractId(contractHeadId)
                            .fromContractCode(contractCode)
                            .fromContractLineId(contractMaterial.getContractMaterialId())
                            .ouId(contractMaterial.getCeeaOuId())
                            .ouName(contractMaterial.getCeeaOuName())
                            .ouNumber(contractMaterial.getCeeaOuNumber())
                            .uomCode(contractMaterial.getUnitCode())
                            .uomDesc(contractMaterial.getUnitName())
                            .build();
                    bidRequirementLines.add(bidRequirementLine);
                });
                sourceForm.setDemandLines(bidRequirementLines);
                // 创建寻源单
                GenerateSourceFormResult generateSourceFormResult = sourceFormClientBid.generateByCm(sourceForm);
                // 回写寻缘单号
                bidingNum = generateSourceFormResult.getSourceForm().getBidding().getBidingNum();
            } else if (SourcingType.RFQ.getItemValue().equals(sourceType)) {
                // 寻比价
                com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm sourceForm = new com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm();
                com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding biding = new com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding();
                biding.setSourceFrom(SourceFrom.CONTRACT.getItemValue());
                com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirement demandHeader = new com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirement();
                ArrayList<com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine> demandLines = new ArrayList<>();
                ArrayList<com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidVendor> bidVendors = new ArrayList<>();
                sourceForm.setBidding(biding);
                sourceForm.setBidVendors(bidVendors);
                sourceForm.setDemandHeader(demandHeader);

                contractMaterials.forEach(contractMaterial -> {
                    com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine bidRequirementLine = com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine.builder()
                            .targetId(contractMaterial.getMaterialId())
                            .targetNum(contractMaterial.getMaterialCode())
                            .targetDesc(contractMaterial.getMaterialName())
                            .categoryId(contractMaterial.getCategoryId())
                            .categoryCode(contractMaterial.getCategoryCode())
                            .categoryName(contractMaterial.getCategoryName())
                            .quantity(contractMaterial.getContractQuantity().doubleValue())
                            .fullPathId(contractMaterial.getBuFullPathId())
                            .orgId(contractMaterial.getBuId())
                            .orgCode(contractMaterial.getBuCode())
                            .orgName(contractMaterial.getBuName())
                            .invId(contractMaterial.getInvId())
                            .invCode(contractMaterial.getInvCode())
                            .invName(contractMaterial.getInvName())
                            .awardedSupplierId(contractMaterial.getVendorId())
                            .awardedSupplierName(contractMaterial.getVendorName())
                            .deliveryPlace(contractMaterial.getTradingLocations())
                            .fromContractId(contractHeadId)
                            .fromContractCode(contractCode)
                            .fromContractLineId(contractMaterial.getContractMaterialId())
                            .ouId(contractMaterial.getCeeaOuId())
                            .ouName(contractMaterial.getCeeaOuName())
                            .ouNumber(contractMaterial.getCeeaOuNumber())
                            .uomCode(contractMaterial.getUnitCode())
                            .uomDesc(contractMaterial.getUnitName())
                            .build();
                    demandLines.add(bidRequirementLine);
                });
                sourceForm.setDemandLines(demandLines);
                com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormResult generateSourceFormResult = sourceFormClientBargain.generateByCm(sourceForm);
                bidingNum = generateSourceFormResult.getSourceForm().getBidding().getBidingNum();
            }
            String finalBidingNum = bidingNum;
            contractMaterials.forEach(contractMaterial -> {
                UpdateWrapper<ContractMaterial> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("CEEA_FOLLOW_SOURCE_NUM", finalBidingNum);
                updateWrapper.eq("CONTRACT_MATERIAL_ID", contractMaterial.getContractMaterialId());
                iContractMaterialService.update(updateWrapper);
            });
        }
    }

    /**
     * 1. 校验是否存在寻源单, 校验是否都是同一个寻源单
     * 2. 校验是寻源类型是否存在
     */
    private void checkItemInfo(List<ContractMaterial> contractMaterials) {
        contractMaterials.forEach(contractMaterial -> {
            Assert.notNull(contractMaterial.getSourceNumber(), "选中物料存在空的寻源单号");
            Assert.notNull(contractMaterial.getSourceType(), "选中物料存在空的寻源类型");

        });
        String sourceNumber = contractMaterials.get(0).getSourceNumber();
        contractMaterials.forEach(contractMaterial -> {
            Assert.isTrue(sourceNumber.equals(contractMaterial.getSourceNumber()), "选中物料不是同一个寻源单号");
        });
    }

    @Override
    @Transactional
    public void priceApprovalWriteBackContract(List<ApprovalBiddingItem> approvalBiddingItemList) {
        if (CollectionUtils.isNotEmpty(approvalBiddingItemList)) {
            ArrayList<ContractMaterial> contractMaterials = new ArrayList<>();
            approvalBiddingItemList.forEach(approvalBiddingItem -> {
                Long fromContractLineId = approvalBiddingItem.getFromContractLineId();
                ContractMaterial contractMaterial = iContractMaterialService.getById(fromContractLineId);
                contractMaterial.setTaxedPrice(approvalBiddingItem.getTaxPrice()); // 含税单价
                if (null != contractMaterial.getContractQuantity() && null != contractMaterial.getTaxedPrice()) {
                    contractMaterial.setAmount(contractMaterial.getContractQuantity().multiply(contractMaterial.getTaxedPrice()));
                }
                if (null != contractMaterial.getAmount() &&
                        contractMaterial.getAmount().compareTo(BigDecimal.ZERO) != 0 &&
                        null != contractMaterial.getTaxRate() &&
                        contractMaterial.getTaxRate().compareTo(BigDecimal.ZERO) != 0
                ) {
                    double taxRate = contractMaterial.getTaxRate().doubleValue();
                    double amount = contractMaterial.getAmount().doubleValue();
                    double unAmount = amount / (taxRate / 100 + 1);
                    contractMaterial.setUnAmount(new BigDecimal(unAmount));
                }
                contractMaterials.add(contractMaterial);
            });
            // 更新物料行价格
            iContractMaterialService.updateBatchById(contractMaterials);
            // 更新合同总金额
            this.baseMapper.updateContractAmount(contractMaterials.get(0).getContractHeadId());
        }

    }

    @Override
    public ContractHead getContractHeadByParam(ContractHeadDTO contractHeadDTO) {
        ContractHead contractHead = new ContractHead();
        BeanUtils.copyProperties(contractHeadDTO, contractHead);
        return this.getOne(new QueryWrapper<>(contractHead));
    }

    /**
     * 校验用户是否有操作权限
     * 控制逻辑: 只有创建人才有修改的权限
     *
     * @param contractHeadId
     */
    public void checkOperationAuthority(Long contractHeadId) {
        ContractHead contractHead = this.getById(contractHeadId);
        Assert.notNull(contractHead, "找不到该合同:contractHeadId = " + contractHeadId);
        String userName = AppUserUtil.getUserName();
        Assert.isTrue(userName.equals(contractHead.getCreatedBy()), "只有合同创建人才能修改!!!");
    }

    @Override
    public List<PriceLibraryContractResDTO> getOnShelvesContractList(PriceLibraryContractRequestDTO priceLibraryContractRequestDTO) {
        Map<String, Object> param = new HashMap<>();
        param.put("priceLibraryList", priceLibraryContractRequestDTO.getPriceLibraryList());
        param.put("contractCode", priceLibraryContractRequestDTO.getContractNo());
        param.put("contractName", priceLibraryContractRequestDTO.getContractName());
        param.put("vendorName", priceLibraryContractRequestDTO.getVendorName());
        param.put("orgName", priceLibraryContractRequestDTO.getCeeaOrgName());
        param.put("OrganizationName", priceLibraryContractRequestDTO.getCeeaOrganizationName());
        List<PriceLibraryContractResDTO> priceLibraryContractResList = this.baseMapper.getOnShelvesContractList(param);
        return priceLibraryContractResList;
    }

    @Override
    public AdvanceApplyHeadVo advanceCheckContract(ContractHeadDTO contractHeadDTO) {
        List<ContractPartner> contractPartners = iContractPartnerService.list(new QueryWrapper<>(new ContractPartner().setContractHeadId(contractHeadDTO.getContractHeadId())));
        List<PayPlan> payPlans = iPayPlanService.list(Wrappers.lambdaQuery(PayPlan.class)
                .select(PayPlan::getPaymentStage)
                .eq(PayPlan::getContractHeadId, contractHeadDTO.getContractHeadId()));
        List<String> paymentStages = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(payPlans)) {
            paymentStages = payPlans.stream().map(payPlan -> (payPlan.getPaymentStage())).collect(Collectors.toList());
        }
        ContractHead contractHead = this.getById(contractHeadDTO.getContractHeadId());
        List<Long> collect = contractPartners.stream().filter(Objects::nonNull).filter(contractPartner -> ("丙方".equals(contractPartner.getPartnerType()))).map(contractPartner -> (contractPartner.getOuId())).collect(Collectors.toList());
        Map<Long, Organization> longOrganizationMap = new HashMap<>();
        Map<Long, List<DeptDto>> longDeptDtoMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(collect)) {
            longOrganizationMap = batchSelectOrganization(collect, longOrganizationMap);
            longDeptDtoMap = baseClient.getAllDeptByOrganizations(collect);
        }
        if (CollectionUtils.isNotEmpty(contractPartners)) {
            for (ContractPartner contractPartner : contractPartners) {
                if (contractPartner == null) continue;
                String partnerType = contractPartner.getPartnerType();
                if ("丙方".equals(partnerType)) {
                    Long ouId = contractPartner.getOuId();
                    Organization organization = longOrganizationMap.get(ouId);
                    String ceeaBusinessCode = organization.getCeeaBusinessCode();
                    String partnerName = contractPartner.getPartnerName();
                    List<DeptDto> deptDtos = longDeptDtoMap.get(ouId);
                    AdvanceApplyHeadVo advanceApplyHeadVo = new AdvanceApplyHeadVo();
                    advanceApplyHeadVo.setIfPayAgent(YesOrNo.YES.getValue()).setPayAgentOrgId(ouId).setPayAgentOrgName(partnerName).setPayAgentOrgCode(ceeaBusinessCode);//设置代付相关信息
                    advanceApplyHeadVo.setDeptDtos(deptDtos);//设置代付部门(全部部门,包括虚拟部门)
                    advanceApplyHeadVo.setPaymentStages(paymentStages);//设置合同付款阶段
                    setBaseByContract(contractHead, advanceApplyHeadVo);//设置根据合同带出基础信息
                    return advanceApplyHeadVo;
                }
            }
        }
        AdvanceApplyHeadVo advanceApplyHeadVo = new AdvanceApplyHeadVo();
        advanceApplyHeadVo.setIfPayAgent(YesOrNo.NO.getValue());
        advanceApplyHeadVo.setPaymentStages(paymentStages);//设置合同付款阶段
        setBaseByContract(contractHead, advanceApplyHeadVo);//设置根据合同带出基础信息
        return advanceApplyHeadVo;
    }

    private Map<Long, Organization> batchSelectOrganization(List<Long> organizationIds, Map<Long, Organization> organizationMap) {
        List<Organization> organizations = baseClient.getOrganizationsByIds(organizationIds);
        if (CollectionUtils.isNotEmpty(organizations)) {
            organizationMap = organizations.stream().collect(Collectors.toMap(Organization::getOrganizationId, Function.identity()));
        }
        return organizationMap;
    }

    @Override
    public PageInfo<ContractHead> listPageEffectiveByParam(ContractHeadDTO contractHeadDTO) {
        PageUtil.startPage(contractHeadDTO.getPageNum(), contractHeadDTO.getPageSize());
        List<ContractHead> contractHeads = this.baseMapper.listPageEffectiveByParam(contractHeadDTO);
        return new PageInfo<>(contractHeads);
    }


    @Override
    public void importModelDownload(HttpServletResponse response) throws Exception {
        ArrayList<ContractHeadModelDTO> contractHeadModelDTOs = new ArrayList<>();
        ArrayList<ContractMaterialModelDTO> contractMaterialModelDTOs = new ArrayList<>();
        ArrayList<PayPlanModelDTO> payPlanModelDTOs = new ArrayList<>();
        ArrayList<ContractPartnerModelDTO> contractPartnerModelDTOs = new ArrayList<>();
        String[] sheetNames = {"合同导入模板", "合同头", "物料明细", "付款计划", "合作伙伴"};
        List<List<? extends Object>> dataLists = new ArrayList<>();
        dataLists.add(contractHeadModelDTOs);
        dataLists.add(contractMaterialModelDTOs);
        dataLists.add(payPlanModelDTOs);
        dataLists.add(contractPartnerModelDTOs);
        Class<? extends Object>[] clazz = new Class[]{ContractHeadModelDTO.class, ContractMaterialModelDTO.class, PayPlanModelDTO.class, ContractPartnerModelDTO.class};
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, sheetNames[0]);
        // 多sheet excel
        EasyExcelUtil.writeExcelWithModel(outputStream, sheetNames, dataLists, clazz);
    }

    /**
     * 导出合同明细
     * @param response
     * @throws Exception
     */
    @Override
    public void importContractMaterialDownload(List<Long>  contractHeadIds,HttpServletResponse response) throws Exception {
        // 标题
        ContractHeadVO contractHeadVO = new ContractHeadVO();
        Field[] declaredFields = contractHeadVO.getClass().getDeclaredFields();
        ArrayList<String> head = new ArrayList<>();
        ArrayList<String> headName = new ArrayList<>();
        for (Field field : declaredFields) {
            head.add(field.getName());
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (null != annotation) {
                headName.add(annotation.value()[0]);
            }
        }
        // 获取导出的数据
        List<List<Object>> dataList = this.queryExportData(contractHeadIds, head);
        // 文件名
        String fileName = "送货单导出";
        // 开始导出
        EasyExcelUtil.exportStart(response, dataList, headName, fileName);
    }



    // 获取导出的数据
    public List<List<Object>> queryExportData(List<Long>  contractHeadIds, List<String> param) {
        //查新需要导出的数据
        //获取当前登陆人信息
        //当登录人不存在的情况下直接返回空
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (null==loginAppUser) {
            return new ArrayList<>();
        }
        QueryWrapper<ContractHead> wrapper = new QueryWrapper<>();
        wrapper.in(CollectionUtils.isNotEmpty(contractHeadIds), "a.CONTRACT_HEAD_ID", contractHeadIds);
        wrapper.eq("a.CREATED_ID",loginAppUser.getUserId());
        List<ContractHeadVO> contractHeadVOList = contractHeadMapper.getContractHeadVOList(wrapper);

        List<List<Object>> dataList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(contractHeadVOList)) {
            List<Map<String, Object>> mapList = BeanMapUtils.objectsToMaps(contractHeadVOList);
            List<String> titleList = param;
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(titleList)) {
                for (Map<String, Object> map : mapList) {
                    ArrayList<Object> objects = new ArrayList<>();
                    for (String key : titleList) {
                        objects.add(map.get(key));
                    }
                    dataList.add(objects);
                }
            }
        }
        return dataList;
    }

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 校验传参
        EasyExcelUtil.checkParam(file, fileupload);

        HashMap<String, Object> result = new HashMap<>();
        // 获取输入流
        InputStream inputStream = file.getInputStream();

        ExcelReader excelReader = EasyExcel.read(inputStream).build();

        AnalysisEventListenerImpl<Object> sheet1Listener = new AnalysisEventListenerImpl();

        AnalysisEventListenerImpl<Object> sheet2Listener = new AnalysisEventListenerImpl();

        AnalysisEventListenerImpl<Object> sheet3Listener = new AnalysisEventListenerImpl();

        AnalysisEventListenerImpl<Object> sheet4Listener = new AnalysisEventListenerImpl();

        ReadSheet sheet1 =
                EasyExcel.readSheet(0).head(ContractHeadModelDTO.class).registerReadListener(sheet1Listener).build();

        ReadSheet sheet2 =
                EasyExcel.readSheet(1).head(ContractMaterialModelDTO.class).registerReadListener(sheet2Listener).build();

        ReadSheet sheet3 =
                EasyExcel.readSheet(2).head(PayPlanModelDTO.class).registerReadListener(sheet3Listener).build();

        ReadSheet sheet4 =
                EasyExcel.readSheet(3).head(ContractPartnerModelDTO.class).registerReadListener(sheet4Listener).build();

        excelReader.read(sheet1, sheet2, sheet3, sheet4);

        // 合同头
        List<Object> sheet1s = sheet1Listener.getDatas();
        // 物料明细
        List<Object> sheet2s = sheet2Listener.getDatas();
        // 付款计划
        List<Object> sheet3s = sheet3Listener.getDatas();
        // 合作伙伴
        List<Object> sheet4s = sheet4Listener.getDatas();

        // 数据库bean
        ArrayList<ContractHead> contractHeads = new ArrayList<>();
        // 导入bean
        ArrayList<ContractHeadModelDTO> contractHeadModelDTOs = new ArrayList<>();
        ArrayList<ContractMaterialModelDTO> contractMaterialModelDTOs = new ArrayList<>();
        ArrayList<PayPlanModelDTO> payPlanModelDTOs = new ArrayList<>();
        ArrayList<ContractPartnerModelDTO> contractPartnerModelDTOs = new ArrayList<>();
        // 分组list
        List<List<ContractMaterial>> contractMaterialGroups = new ArrayList<>();
        List<List<PayPlan>> payPlanGroups = new ArrayList<>();
        List<List<ContractPartner>> contractPartnerGroups = new ArrayList<>();

        // true 为根据合同序列号分类 false根据合同名分类
        Boolean flag = true;
        // 合同头信息
        if (CollectionUtils.isNotEmpty(sheet1s)) {
            for (Object contractHeadModel : sheet1s) {
                HashSet only = new HashSet();
                List<ContractMaterial> contractMaterials = new ArrayList<>();
                List<PayPlan> payPlans = new ArrayList<>();
                List<ContractPartner> contractPartners = new ArrayList<>();
                if (null != contractHeadModel) {
                    StringBuffer contractHeadErrorMessage = new StringBuffer();
                    ContractHeadModelDTO contractHeadModelDTO = (ContractHeadModelDTO) contractHeadModel;
                    ContractHead contractHead = new ContractHead();
                    checkContractHeadParam(contractHeadModelDTO, contractHead, contractHeadErrorMessage);
                    if (StringUtils.isNotEmpty(contractHeadModelDTO.getContractNo())) {
                        flag = true;
                    } else if (StringUtils.isNotEmpty(contractHeadModelDTO.getContractName())) {
                        flag = false;
                    } else {
                        throw new BaseException("合同序号和合同名称都为空，请维护其中一个；");
                    }

                    /*// 框架协议为否  乙方是公司头信息的供应商名称
                    if (StringUtils.isNotEmpty(contractHead.getIsFrameworkAgreement()) && StringUtils.isEmpty(contractHeadModelDTO.getErrorMessage())) {
                        if (contractHead.getIsFrameworkAgreement().equals(YesOrNo.NO.getValue())) {
                            ContractPartner contractPartner = new ContractPartner();
                            contractPartner.setPartnerType(ContractHeadConst.PARTY_B);
                            contractPartner.setPartnerName(contractHead.getVendorName());
                            contractPartners.add(contractPartner);
                        }
                    }*/

                    // 物料明细根据标志分类
                    List<Object> contractMaterialCollect = new ArrayList<>();
                    if (flag) {
                        contractMaterialCollect = sheet2s.stream().filter(s -> contractHeadModelDTO.getContractNo().equals(((ContractMaterialModelDTO) s).getContractNo())).collect(Collectors.toList());
                    } else {
                        contractMaterialCollect = sheet2s.stream().filter(s -> contractHeadModelDTO.getContractName().equals(((ContractMaterialModelDTO) s).getContractName())).collect(Collectors.toList());
                    }
                    if (CollectionUtils.isNotEmpty(contractMaterialCollect)) {
                        BigDecimal amount = new BigDecimal(0);
                        for (Object object1 : contractMaterialCollect) {
                            if (null != object1) {
                                ContractMaterialModelDTO contractMaterialModelDTO = (ContractMaterialModelDTO) object1;
                                ContractMaterial contractMaterial = new ContractMaterial();
                                StringBuffer contractMaterialErrorMessage = new StringBuffer();
                                checkContractMaterialParam(contractMaterialModelDTO, contractMaterial, contractMaterialErrorMessage, contractHead);
                                if (StringUtils.isEmpty(contractMaterialModelDTO.getErrorMessage())) {
                                    // 含税金额
                                    if (StringUtils.isNotEmpty(contractMaterialModelDTO.getContractQuantity())) {
                                        BigDecimal number = contractMaterial.getContractQuantity().multiply(contractMaterial.getTaxedPrice());
                                        contractMaterial.setAmount(number);
                                        // 未税金额
                                        BigDecimal tax = (contractMaterial.getTaxRate().divide(new BigDecimal(100))).add(new BigDecimal(1));
                                        BigDecimal unAmount = number.divide(tax, 2, RoundingMode.HALF_UP);
                                        contractMaterial.setUnAmount(unAmount);
                                        // 合同头总金额
                                        if (StringUtils.isEmpty(contractHeadModelDTO.getIncludeTaxAmount())) {
                                            amount = amount.add(number);
                                        }
                                    }
                                }

                                   /* // 框架协议为否  甲方公司是公司是物料业务实体的公司
                                    if (StringUtils.isNotEmpty(contractHead.getIsFrameworkAgreement()) && StringUtils.isEmpty(contractMaterialModelDTO.getErrorMessage())) {
                                        if (contractHead.getIsFrameworkAgreement().equals(YesOrNo.NO.getValue())) {
                                            List<Organization> organizationList = baseClient.getOrganizationByNameList(Arrays.asList(contractMaterial.getBuName()));
                                            if (CollectionUtils.isNotEmpty(organizationList)) {
                                                Organization organization = organizationList.get(0);
                                                ContractPartner contractPartner = new ContractPartner();
                                                contractPartner.setPartnerName(organization.getCeeaCompanyName());
                                                contractPartner.setPartnerType(ContractHeadConst.PARTY_A);
                                                contractPartner.setOuId(organization.getOrganizationId());
                                                contractPartner.setMaterialId(contractMaterial.getMaterialId());
                                                // 重复的不加入合作伙伴列表
                                                List<ContractPartner> list = new ArrayList<ContractPartner>();
                                                for (ContractPartner partner : contractPartners) {
                                                    if (!contractPartner.getPartnerName().equals(partner.getPartnerName())
                                                            && ContractHeadConst.PARTY_A.equals(partner.getPartnerType())) {
                                                        list.add(contractPartner);
                                                    }
                                                }
                                                contractPartners.addAll(list);
                                            }
                                        }
                                    }*/

                                contractMaterialModelDTOs.add(contractMaterialModelDTO);
                                contractMaterials.add(contractMaterial);
                            }
                        }
                        contractHead.setIncludeTaxAmount(amount);
                    } else {
                        contractHeadModelDTO.setErrorMessage(contractHeadModelDTO.getErrorMessage() + "该合同未找到对应物料明细信息；");
                    }
                    // 付款计划根据标志分类
                    List<Object> payPlanCollect = new ArrayList<>();
                    if (flag) {
                        payPlanCollect = sheet3s.stream().filter(s -> contractHeadModelDTO.getContractNo().equals(((PayPlanModelDTO) s).getContractNo())).collect(Collectors.toList());
                    } else {
                        payPlanCollect = sheet3s.stream().filter(s -> contractHeadModelDTO.getContractName().equals(((PayPlanModelDTO) s).getContractName())).collect(Collectors.toList());
                    }
                    if (CollectionUtils.isNotEmpty(payPlanCollect)) {
                        BigDecimal paymentRatioSum = new BigDecimal(0);
                        BigDecimal includeTaxAmount = contractHead.getIncludeTaxAmount();
                        for (Object object2 : payPlanCollect) {
                            if (null != object2) {
                                PayPlanModelDTO payPlanModelDTO = (PayPlanModelDTO) object2;
                                StringBuffer payPlanErrorMessage = new StringBuffer();
                                PayPlan payPlan = new PayPlan();
                                checkPayPlanParam(payPlanModelDTO, payPlan, payPlanErrorMessage, includeTaxAmount, only);
                                // 一个合同付款比例总和不能大于100
                                if (StringUtils.isEmpty(payPlanModelDTO.getPaymentRatio())) {
                                    paymentRatioSum = paymentRatioSum.add(payPlan.getPaymentRatio());
                                    if (paymentRatioSum.compareTo(new BigDecimal("100")) > 0) {
                                        String errorMessage = payPlanModelDTO.getErrorMessage();
                                        if (StringUtil.notEmpty(errorMessage)) {
                                            payPlanModelDTO.setErrorMessage(errorMessage + " 同一合同累计付款比例已超过100; ");
                                        } else {
                                            payPlanModelDTO.setErrorMessage("同一合同累计付款比例已超过100; ");
                                        }
                                    }
                                }
                                payPlanModelDTOs.add(payPlanModelDTO);
                                payPlan.setPaymentPeriod(payPlans.size() + 1 + "");
                                payPlans.add(payPlan);
                            }
                        }
                    }
//                        else {
//                            contractHeadModelDTO.setErrorMessage(contractHeadModelDTO.getErrorMessage()+"该合同未找到对应付款计划信息信息；");
//                        }
                    // 合作伙伴根据标志分类
                    List<Object> contractPartnerCollect = new ArrayList<>();
                    if (flag) {
                        contractPartnerCollect = sheet4s.stream().filter(s -> contractHeadModelDTO.getContractNo().equals(((ContractPartnerModelDTO) s).getContractNo())).collect(Collectors.toList());
                    } else {
                        contractPartnerCollect = sheet4s.stream().filter(s -> contractHeadModelDTO.getContractName().equals(((ContractPartnerModelDTO) s).getContractName())).collect(Collectors.toList());
                    }
                    if (CollectionUtils.isNotEmpty(contractPartnerCollect)) {
                        contractPartnerCollect.forEach((object3 -> {
                            if (null != object3) {
                                ContractPartnerModelDTO contractPartnerModelDTO = (ContractPartnerModelDTO) object3;
                                StringBuffer contractPartnerErrorMessage = new StringBuffer();
                                ContractPartner contractPartner = new ContractPartner();
                                checkContractPartnerParam(contractPartnerModelDTO, contractPartner, contractPartnerErrorMessage, contractHeadModelDTO);
                                contractPartnerModelDTOs.add(contractPartnerModelDTO);
                                contractPartners.add(contractPartner);
                            }
                        }));
                    } else {
                        contractHeadModelDTO.setErrorMessage(contractHeadModelDTO.getErrorMessage() + "该合同未找到对应合作伙伴信息；");
                    }
                    // 根据分类导入序号分类存储
                    contractHeads.add(contractHead);
                    contractHeadModelDTOs.add(contractHeadModelDTO);
                    contractMaterialGroups.add(contractMaterials);
                    payPlanGroups.add(payPlans);
                    if (CollectionUtils.isNotEmpty(contractPartners)) {
                        // 合作伙伴根据类型 + ou 去重
                        List<ContractPartner> contractPartnerList = ListUtil.listDeduplication(contractPartners, contractPartner -> contractPartner.getPartnerType() + contractPartner.getOuId());
                    }
                    contractPartnerGroups.add(contractPartners);
                }
            }
        }
        // 判断各个sheet导入是否有错误信息
        Boolean contractHeadFlag = false;
        Boolean contractMaterialFlag = false;
        Boolean payPlanFlag = false;
        Boolean contractPartnerFlag = false;
        contractHeadFlag = isHasErrorMessage(contractHeadModelDTOs, contractHeadFlag, ContractHeadModelDTO.class, ContractHeadConst.ERROR_MESSAGE);
        contractMaterialFlag = isHasErrorMessage(contractMaterialModelDTOs, contractMaterialFlag, ContractMaterialModelDTO.class, ContractHeadConst.ERROR_MESSAGE);
        payPlanFlag = isHasErrorMessage(payPlanModelDTOs, payPlanFlag, PayPlanModelDTO.class, ContractHeadConst.ERROR_MESSAGE);
        contractPartnerFlag = isHasErrorMessage(contractPartnerModelDTOs, contractPartnerFlag, ContractPartnerModelDTO.class, ContractHeadConst.ERROR_MESSAGE);

        // 有错误信息 上传文件
        if (contractHeadFlag || contractMaterialFlag || payPlanFlag || contractPartnerFlag) {
            String[] sheetNames = {file.getName(), "合同头", "物料明细", "付款计划", "合作伙伴"};
            List<List<? extends Object>> dataLists = new ArrayList<>();
            dataLists.add(contractHeadModelDTOs);
            dataLists.add(contractMaterialModelDTOs);
            dataLists.add(payPlanModelDTOs);
            dataLists.add(contractPartnerModelDTOs);
            Class<? extends Object>[] clazz = new Class[]{ContractHeadModelDTO.class, ContractMaterialModelDTO.class, PayPlanModelDTO.class, ContractPartnerModelDTO.class};
            Fileupload errorFileUpload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    dataLists, clazz, sheetNames, file.getOriginalFilename(), file.getContentType());
            result.put("status", YesOrNo.NO.getValue());
            result.put("message", "error");
            result.put("fileuploadId", errorFileUpload.getFileuploadId());
            result.put("fileName", errorFileUpload.getFileSourceName());
        } else { // 无错误信息
            for (int i = 0; i < contractHeads.size(); i++) {
                importSave(new ContractDTO().setContractHead(contractHeads.get(i))
                        .setContractMaterials(i <= contractMaterialGroups.size() - 1 ? contractMaterialGroups.get(i) : null)
                        .setContractPartners(i <= contractPartnerGroups.size() - 1 ? contractPartnerGroups.get(i) : null)
                        .setPayPlans(i <= payPlanGroups.size() - 1 ? payPlanGroups.get(i) : null)
                );
            }
            result.put("status", YesOrNo.YES.getValue());
            result.put("message", "success");
        }

        return result;
    }

    @Override
    public Response pushContractInfo(Long contractHeadId) {
        Response contractResponse = new Response();
        ContractHead contractHead = null;
        List<ContractMaterial> contractMaterials = new ArrayList<>();
        List<PayPlan> payPlans = new ArrayList<>();
        try {
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            jaxWsProxyFactoryBean.getInInterceptors().add(new LoggingInInterceptor());
            jaxWsProxyFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
            // 设置代理地址
            /*String contractUrl = "http://soatest.longi.com:8011/CMSSB/Esb/OeBlanket/ProxyServices/CmsSendOeBlanketSoapProxy?wsdl";
            String contractUserName = "longi_xansrm01";
            String contractPassword = "932f8f9cfa6d4bcd8fa8478f697cb227";*/
            jaxWsProxyFactoryBean.setAddress(CmSaopUrl.contractUrl);
            jaxWsProxyFactoryBean.setUsername(this.contractUserName);
            jaxWsProxyFactoryBean.setPassword(this.contractPassword);
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(ContractExecutePtt.class);

            // 创建一个代理接口实现
            ContractExecutePtt service = (ContractExecutePtt) jaxWsProxyFactoryBean.create();
            Request contractRequest = new Request();

            /**设置EsbInfo*/
            Request.EsbInfo esbInfo = new Request.EsbInfo();
            esbInfo.setInstId(String.valueOf(System.currentTimeMillis()));
            esbInfo.setRequestTime(String.valueOf(System.currentTimeMillis()));
            esbInfo.setAttr1("");
            esbInfo.setAttr2("");
            esbInfo.setAttr3("");
            esbInfo.setSysCode(ContractHeadConst.SYS_CODE);
            contractRequest.setEsbInfo(esbInfo);

            /**设置RequestInfo*/
            Request.RequestInfo requestInfo = new Request.RequestInfo();

            Request.RequestInfo.CmsSiCtHeaders header = new Request.RequestInfo.CmsSiCtHeaders();

            Request.RequestInfo.CmsSiCtHeaders.Items items = new Request.RequestInfo.CmsSiCtHeaders.Items();
            // 付款计划
            Request.RequestInfo.CmsSiCtHeaders.CmsPeRtnpayItems listPay = new Request.RequestInfo.CmsSiCtHeaders.CmsPeRtnpayItems();

            List<Request.RequestInfo.CmsSiCtHeaders.CmsPeRtnpayItems.CmsPeRtnpayItem> listPays = new ArrayList<>();
            // 物料
            Request.RequestInfo.CmsSiCtHeaders.CmsSiSubjectMatters listSubject = new Request.RequestInfo.CmsSiCtHeaders.CmsSiSubjectMatters();

            List<Request.RequestInfo.CmsSiCtHeaders.CmsSiSubjectMatters.CmsSiSubjectMatter> listSubjects = new ArrayList<>();

            // 合同
            List<Request.RequestInfo.CmsSiCtHeaders.Items.Item03> listItem03 = new ArrayList<>();
            List<Request.RequestInfo.CmsSiCtHeaders.Items.Item02> listItem02 = new ArrayList<>();
            List<Request.RequestInfo.CmsSiCtHeaders.Items.Item01> listItem01 = new ArrayList<>();
            List<Request.RequestInfo.CmsSiCtHeaders.Items.Item00> listItem00 = new ArrayList<>();

            /*List<ContractPartner> collect = new ArrayList<>();*/
            // 根据合同头id查询合同信息
            ContractDTO contractDTO = getContractDTOSecond(contractHeadId, null);
            if (null != contractDTO) {
                // 合同头信息
                contractHead = contractDTO.getContractHead();
                // 物料信息
                contractMaterials = contractDTO.getContractMaterials();
                // 付款计划
                payPlans = contractDTO.getPayPlans();

                /*List<ContractPartner> contractPartners = contractDTO.getContractPartners();
                // 甲方合作伙伴
                if (CollectionUtils.isNotEmpty(contractPartners)){
                    collect = contractPartners.stream().filter(s -> ContractHeadConst.PARTY_A.equals(s.getPartnerType())).collect(Collectors.toList());
                }*/
            } else {
                log.error("合同头id不存在；");
                throw new BaseException("合同头id不存在");
            }
            // 组装合同头信息
            if (null != contractHead) {
                Request.RequestInfo.CmsSiCtHeaders.Items.Item03 item03 = new Request.RequestInfo.CmsSiCtHeaders.Items.Item03();
                Request.RequestInfo.CmsSiCtHeaders.Items.Item02 item02 = new Request.RequestInfo.CmsSiCtHeaders.Items.Item02();
                Request.RequestInfo.CmsSiCtHeaders.Items.Item00 item00 = new Request.RequestInfo.CmsSiCtHeaders.Items.Item00();
                Request.RequestInfo.CmsSiCtHeaders.Items.Item01 item01 = new Request.RequestInfo.CmsSiCtHeaders.Items.Item01();

                List<DictItemDTO> dictItemDTOS = baseClient.listAllByDictCode("ELEM_CONTRACT_TYPE");
                String contractClass = contractHead.getContractClass();
                String contractClassName = "";
                if (Objects.nonNull(dictItemDTOS)) {
                    for (DictItemDTO dictItemDTO : dictItemDTOS) {
                        if (dictItemDTO.getDictItemCode().equals(contractHead.getContractClass())) {
                            contractClassName = dictItemDTO.getDictItemName();
                        }
                    }
                }
                //合同类型编码
                item01.setLARGETYPECODE(contractClass);
                //合同类型名字
                item01.setLARGETYPENAME(contractClassName);

                //查询org表的erp_org_id
                // 业务实体id
                Organization contractOrg = baseClient.get(contractHead.getBuId());
                item03.setOUID(Objects.isNull(contractOrg) ? "" : contractOrg.getErpOrgId());
                // 是否框架协议
                item03.setISFRAMEWORKAGREEMENT(StringUtils.isNotEmpty(contractHead.getIsFrameworkAgreement()) ? contractHead.getIsFrameworkAgreement() : "");
                // 货币
                item03.setORIGINALCURRENCY(contractHead.getCurrencyCode() == null ? "" : contractHead.getCurrencyCode());
                // 合同总额
                item03.setORIGINALCURRENCYTOTALAMOUNT(contractHead.getIncludeTaxAmount() == null ? "" : contractHead.getIncludeTaxAmount() + "");
                // 签约对方类型
                item03.setSIGNOTHERTYPE(ContractHeadConst.VENDOR);
                // 签约对方名称
                item03.setSIGNOTHERNAME(StringUtils.isNotEmpty(contractHead.getVendorName()) ? contractHead.getVendorName() : "");
                // 签约对方编码
                item03.setSIGNOTHERCODE(StringUtils.isNotEmpty(contractHead.getErpVendorCode()) ? contractHead.getErpVendorCode() : "");
                // 收支类型
                item03.setINCOMEEXPENDTYPECODE(ContractHeadConst.EXPEND);
                String projectCode = "";
                String projectName = "";
                if (Objects.nonNull(contractMaterials)) {
                    for (int i = 0; i < contractMaterials.size(); i++) {
                        ContractMaterial cm = contractMaterials.get(i);
                        if (Objects.nonNull(cm) && StringUtils.isNotBlank(cm.getItemNumber())) {
                            projectCode = cm.getItemNumber();
                            projectName = cm.getItemName();
                            break;
                        }
                    }
                }
                //项目编号
                item03.setBUFFER8(projectCode);
                //项目名称
                item03.setBUFFER9(projectName);
                //总线要求要传递的参数
                item03.setCTSEALSTATUS("30");
                // 签约公司
                    /*if(CollectionUtils.isNotEmpty(collect)) {
                        ContractPartner contractPartner = collect.get(0);
                        Organization organization = baseClient.getOrganization(new Organization().setCeeaCompanyName(contractPartner.getPartnerName()));
                        if(organization != null){
                            item03.setSIGNCOMPANYNAME(organization.getCeeaCompanyName());
                            item03.setSIGNCOMPANYCODE(organization.getCeeaCompanyCode());
                        }
                    }*/
                if (StringUtils.isNotEmpty(contractHead.getBuName())) {
                    List<Organization> organizationByNameList = baseClient.getOrganizationByNameListAnon(Arrays.asList(contractHead.getBuName()));
                    if (CollectionUtils.isNotEmpty(organizationByNameList)) {
                        Organization organization = organizationByNameList.get(0);
                        item03.setSIGNCOMPANYNAME(contractHead.getBuName());
                        item03.setSIGNCOMPANYCODE(organization.getCeeaErpUnitCode());
                    }
                }
                LoginAppUser user = rbacClient.findByUsername(contractHead.getCreatedBy());
                if (user != null) {
                    // 业务谈判人编号
                    item03.setBUSINESSAREACODE(StringUtils.isNotEmpty(user.getCeeaEmpNo()) ? user.getCeeaEmpNo() : "");
                    // 业务谈判人名称
                    item03.setBUSINESSAREANAME(StringUtils.isNotEmpty(user.getNickname()) ? user.getNickname() : "");
                    //操作人
                    item02.setBUSINESSNEGOTIATORNO(StringUtils.isNotEmpty(user.getCeeaEmpNo()) ? user.getCeeaEmpNo() : "");
                    //操作人名称
                    item02.setBUSINESSNEGOTIATORNAME(StringUtils.isNotEmpty(user.getNickname()) ? user.getNickname() : "");
                }
                // 合同名称
                item00.setCTNAME(StringUtils.isNotEmpty(contractHead.getContractName()) ? contractHead.getContractName() : "");
                // 合同编码
                item00.setCTCODE(StringUtils.isNotEmpty(contractHead.getContractCode()) ? contractHead.getContractCode() : "");

                // 合同头ID
                item00.setCTHEADERID(contractHead.getContractHeadId() != null ? contractHead.getContractHeadId() + "" : "");
                item00.setCTSTATUS("30");

                listItem00.add(item00);
                listItem01.add(item01);
                listItem02.add(item02);
                listItem03.add(item03);
            } else {
                log.error("合同头信息为空；");
                throw new BaseException("合同头信息为空");
            }

            // 组装物料信息
            if (CollectionUtils.isNotEmpty(contractMaterials)) {
                contractMaterials.forEach(object -> {
                    Request.RequestInfo.CmsSiCtHeaders.CmsSiSubjectMatters.CmsSiSubjectMatter sub = new Request.RequestInfo.CmsSiCtHeaders.CmsSiSubjectMatters.CmsSiSubjectMatter();
                    // 含税金额
                    if (null != object.getAmount()) {
                        sub.setTAXAMOUNT(object.getAmount());
                    } else {
                        if (object.getTaxedPrice() != null && object.getContractQuantity() != null) {
                            sub.setTAXAMOUNT(object.getTaxedPrice().multiply(object.getContractQuantity()));
                        }
                    }
                    // 税率
                    sub.setTAXRATE(object.getTaxRate() == null ? null : object.getTaxRate());
                    // 未税金额
                    if (null != object.getUnAmount()) {
                        sub.setNOTAXAMOUNT(object.getUnAmount());
                    } else {
                        if (object.getTaxedPrice() != null && object.getContractQuantity() != null && object.getTaxRate() != null) {
                            BigDecimal tax = (object.getTaxRate().divide(new BigDecimal(100))).add(new BigDecimal(1));
                            // 未税金额
                            BigDecimal unAmount = sub.getTAXAMOUNT().divide(tax, 2, RoundingMode.HALF_UP);
                            sub.setNOTAXAMOUNT(unAmount);
                        }
                    }
                    // 行备注
                    sub.setMEMO(StringUtils.isNotEmpty(object.getLineRemark()) ? object.getLineRemark() : "");
                    listSubjects.add(sub);
                });
            }
            // 组装付款计划信息
            //by 去掉组装付款计划信息 2020-11-13  lizhipeng  注：OA审批不过，阻碍项目进度--俊瑾叫删的
            /*if (CollectionUtils.isNotEmpty(payPlans)) {
                // 付款阶段字典
                String[] dict1 = {"PAYMENT_STAGE"};
                Map<String, String> map = setMapKeyValue(dict1);
                payPlans.forEach(Object -> {
                    Request.RequestInfo.CmsSiCtHeaders.CmsPeRtnpayItems.CmsPeRtnpayItem payItem = new Request.RequestInfo.CmsSiCtHeaders.CmsPeRtnpayItems.CmsPeRtnpayItem();
                    // 计划行id
                    payItem.setRTNPAYITEMID(Object.getPayPlanId() == null ? "" : Object.getPayPlanId() + "");
                    // 回款阶段代码
                    payItem.setRTNPAYPHASECODE(StringUtils.isNotEmpty(Object.getPaymentStage()) ? Object.getPaymentStage() : "");
                    // 回款阶段名称
                    payItem.setRTNPAYPHASENAME(map.get(Object.getPaymentStage()));

                    // 计划回款百分比
                    payItem.setPLANRTNPAYRATE(Object.getPaymentRatio() == null ? null : Object.getPaymentRatio());
                    if (Object.getPlannedPaymentDate() != null) {
                        // 计划回款日期
                        payItem.setPLANRTNPAYDATE(DateUtil.localDateToStr(Object.getPlannedPaymentDate()));
                    } else {
                        log.error("计划付款日期为空；");
                    }
                    // 计划回款金额
                    payItem.setPLANRTNPAYAMOUNT(Object.getStagePaymentAmount() == null ? "" : Object.getStagePaymentAmount() + "");
                    listPays.add(payItem);
                });
            } else {
                log.error("付款计划为空；");
                throw new BaseException("付款计划为空");
            }*/

            items.setItem00(listItem00);
            items.setItem01(listItem01);
            items.setItem02(listItem02);
            items.setItem03(listItem03);
            header.setItems(items);
            listSubject.setCmsSiSubjectMatter(listSubjects);
            header.setCmsSiSubjectMatters(listSubject);
            listPay.setCmsPeRtnpayItem(listPays);
            header.setCmsPeRtnpayItems(listPay);
            requestInfo.setCmsSiCtHeaders(header);
            contractRequest.setRequestInfo(requestInfo);
            contractResponse = service.execute(contractRequest);
        } catch (Exception e) {
            log.error("调用接口推送合同信息时报错: ", e);
            throw new BaseException(e.getMessage());
        }
        return contractResponse;
    }

    /**
     * Description 通用 判断是否存在错误信息
     *
     * @return java.lang.Boolean
     * @Param [list, flag, classzz, paramName]
     * @Author fansb3@meicloud.com
     * @Date 2020/10/15
     **/
    private Boolean isHasErrorMessage(List list, Boolean flag, Class classzz, String paramName) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (Object object : list) {
                String errorMsg = "";
                Method[] m = classzz.getMethods();
                for (int i = 0; i < m.length; i++) {
                    if (("get" + paramName).equalsIgnoreCase(m[i].getName())) {
                        try {
                            String name = m[i].getName();
                            errorMsg = (String) m[i].invoke(object);
                        } catch (Exception e) {
                            log.error("操作失败", e);
                        }
                    }
                }
                if (StringUtils.isNotEmpty(errorMsg)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 查询字典集合
     */
    public Map<String, String> setMapValueKey(String[] dict) {
        List<DictItemDTO> dictItemDTOS = baseClient.listByDictCode(Arrays.asList(dict));
        Map<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(dictItemDTOS)) {
            dictItemDTOS.forEach(dictItemDTO -> {
                map.put(dictItemDTO.getDictItemName().trim(), dictItemDTO.getDictItemCode().trim());
            });
        }
        return map;
    }

    public Map<String, String> setMapKeyValue(String[] dict) {
        List<DictItemDTO> dictItemDTOS = baseClient.listByDictCode(Arrays.asList(dict));
        Map<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(dictItemDTOS)) {
            dictItemDTOS.forEach(dictItemDTO -> {
                map.put(dictItemDTO.getDictItemCode().trim(), dictItemDTO.getDictItemName().trim());
            });
        }
        return map;
    }

    /**
     * 合同头信息校验
     *
     * @param contractHeadModelDTO
     * @param contractHead
     * @param errorMessage
     */
    private void checkContractHeadParam(ContractHeadModelDTO contractHeadModelDTO, ContractHead contractHead, StringBuffer errorMessage) {
        String[] dict = {"ELEM_CONTRACT_TYPE", "CONTARCT_LEVEL"};
        Map<String, String> dicValueKey = setMapValueKey(dict);
        // 合同序列号
        if (StringUtils.isNotEmpty(contractHeadModelDTO.getContractNo())) {
            contractHead.setMainContractNo(contractHeadModelDTO.getContractNo());
        }
        // 合同编号
        if (StringUtils.isNotEmpty(contractHeadModelDTO.getContractCode())) {
            contractHead.setContractCode(contractHeadModelDTO.getContractCode());
        }
        // 合同名称
        if (StringUtils.isNotEmpty(contractHeadModelDTO.getContractName())) {
            contractHead.setContractName(contractHeadModelDTO.getContractName());
            contractHead.setContractStatus(ContractStatus.DRAFT.name());
        } else {
            errorMessage.append("合同名称不能为空;");
        }

        // 合同类型
        if (StringUtils.isNotEmpty(contractHeadModelDTO.getContractClass())) {
            String contractClass = contractHeadModelDTO.getContractClass().trim();
            if (StringUtils.isNotEmpty(dicValueKey.get(contractClass))) {
                contractHead.setContractClass(dicValueKey.get(contractClass));
            } else {
                errorMessage.append("合同类型不存在；");
            }
        } else {
            errorMessage.append("合同类型不能为空；");
        }

        // 是否标准
        if (StringUtils.isNotEmpty(contractHeadModelDTO.getEnable())) {
            if (contractHeadModelDTO.getEnable().equals(YesOrNo.NO.getName())) {
                contractHead.setEnable(YesOrNo.NO.getValue());
            } else if (contractHeadModelDTO.getEnable().equals(YesOrNo.YES.getName())) {
                contractHead.setEnable(YesOrNo.YES.getValue());
            } else {
                errorMessage.append("是否标准合同只能填\"是\"或\"否\"");
            }
        } else {
            errorMessage.append("是否标准合同不能为空；");
        }

        // 有效日期从
        String startData = contractHeadModelDTO.getEffectiveDateFrom();
        if (StringUtil.notEmpty(startData)) {
            startData = startData.trim();
            try {
                Date date = DateUtil.parseDate(startData);
                LocalDate localDate = DateUtil.dateToLocalDate(date);
                contractHead.setEffectiveDateFrom(localDate);
            } catch (Exception e) {
                errorMessage.append("有效日期从格式非法; ");
            }
        } else {
            errorMessage.append("有效日期不能为空；");
        }
        // 有效日期至
        String endData = contractHeadModelDTO.getEffectiveDateTo();
        if (StringUtil.notEmpty(endData)) {
            endData = endData.trim();
            try {
                Date date = DateUtil.parseDate(endData);
                LocalDate localDate = DateUtil.dateToLocalDate(date);
                contractHead.setEffectiveDateTo(localDate);
            } catch (Exception e) {
                errorMessage.append("有效日期至格式非法; ");
            }
        } else {
            errorMessage.append("有效日期至不能为空；");
        }
        // 比较大小
        if (contractHead.getEffectiveDateTo() != null && contractHead.getEffectiveDateFrom() != null) {
            if (contractHead.getEffectiveDateFrom().compareTo(contractHead.getEffectiveDateTo()) > 0) {
                errorMessage.append("有效日期从不能大于有效期至；");
            }
        }
        // 供应商名称
        if (StringUtils.isNotEmpty(contractHeadModelDTO.getVendorCode())) {
            String vendorCode = contractHeadModelDTO.getVendorCode().trim();
            CompanyInfo companyInfoByParam = supplierClient.getCompanyInfoByParam(new CompanyInfo().setCompanyCode(vendorCode));
            if (companyInfoByParam != null) {
                if (StringUtils.isNotEmpty(contractHeadModelDTO.getVendorName())) {
                    if (contractHeadModelDTO.getVendorName().equals(companyInfoByParam.getCompanyName())) {
                        contractHead.setVendorCode(companyInfoByParam.getCompanyCode());
                        contractHead.setVendorId(companyInfoByParam.getCompanyId());
                        contractHead.setVendorName(companyInfoByParam.getCompanyName());
                        contractHead.setErpVendorCode(companyInfoByParam.getErpVendorCode());
                        contractHead.setErpVendorId(companyInfoByParam.getErpVendorId());
                    } else {
                        errorMessage.append("供应商名称与供应商编码不匹配；");
                    }
                } else {
                    errorMessage.append("供应商名称不能为空；");
                }
            } else {
                errorMessage.append("供应商编码不存在；");
            }
        } else {
            errorMessage.append("供应商编码不能为空；");
        }

        // 是否框架协议  否  可不填框架协议  是  必填合同级别
        if (StringUtils.isNotEmpty(contractHeadModelDTO.getIsFrameworkAgreement())) {
            if (contractHeadModelDTO.getIsFrameworkAgreement().equals(YesOrNo.NO.getName())) {
                contractHead.setIsFrameworkAgreement(YesOrNo.NO.getValue());
            } else if (contractHeadModelDTO.getIsFrameworkAgreement().equals(YesOrNo.YES.getName())) {
                contractHead.setIsFrameworkAgreement(YesOrNo.YES.getValue());
                // 合同级别
                if (StringUtils.isNotEmpty(contractHeadModelDTO.getContractLevel())) {
                    if (StringUtils.isNotEmpty(dicValueKey.get(contractHeadModelDTO.getContractLevel()))) {
                        contractHead.setContractLevel(dicValueKey.get(contractHeadModelDTO.getContractLevel()));
                    } else {
                        errorMessage.append("合同级别不存在；");
                    }
                } else {
                    errorMessage.append("合同级别不能为空；");
                }
                if (StringUtils.isNotEmpty(contractHeadModelDTO.getIncludeTaxAmount())) {
                    if (StringUtil.isDigit(contractHeadModelDTO.getIncludeTaxAmount())) {
                        contractHead.setIncludeTaxAmount(new BigDecimal(contractHeadModelDTO.getIncludeTaxAmount()));
                    } else {
                        errorMessage.append("合同总金额格式非法；");
                    }
                }
            } else {
                errorMessage.append("是否框架协议只能填\"是\"或\"否\"；");
            }
        } else {
            errorMessage.append("是否框架协议不能为空；");
        }

        /*// 是否电站
        String isPowerStation = contractHeadModelDTO.getIsPowerStation();
        if (StringUtils.isNotEmpty(isPowerStation)) {
            if (isPowerStation.equals(YesOrNo.NO.getName())) {
                contractHead.setIsPowerStation(YesOrNo.NO.getValue());
            } else if (isPowerStation.equals(YesOrNo.YES.getName())) {
                contractHead.setIsPowerStation(YesOrNo.YES.getValue());
            } else {
                errorMessage.append("是否电站只能填\"是\"或\"否\"");
            }
        }*/
        // 送审业务实体
        if (StringUtils.isNotEmpty(contractHeadModelDTO.getBuName())) {
            String buName = contractHeadModelDTO.getBuName();
            List<Organization> organizationByNameList = baseClient.getOrganizationByNameList(Arrays.asList(buName));
            if (CollectionUtils.isNotEmpty(organizationByNameList)) {
                Organization organization = organizationByNameList.get(0);
                contractHead.setBuCode(organization.getOrganizationCode());
                contractHead.setBuId(organization.getOrganizationId());
                contractHead.setBuName(organization.getOrganizationName());
            } else {
                errorMessage.append("送审业务实体不存在；");
            }
        } else {
            errorMessage.append("送审业务实体不能为空；");
        }
        // 币种
        if (StringUtils.isNotEmpty(contractHeadModelDTO.getCurrencyName())) {
            String currencyName = contractHeadModelDTO.getCurrencyName();
            PurchaseCurrency purchaseCurrency = baseClient.getPurchaseCurrencyByParam(new PurchaseCurrency().setCurrencyName(currencyName));
            if (purchaseCurrency != null) {
                contractHead.setCurrencyCode(purchaseCurrency.getCurrencyCode())
                        .setCurrencyId(purchaseCurrency.getCurrencyId()).setCurrencyName(purchaseCurrency.getCurrencyName());
            } else {
                errorMessage.append("币种不存在；");
            }
        } else {
            errorMessage.append("币种不能为空；");
        }
        /*// 是否总部
        String isHeadquarters = contractHeadModelDTO.getIsHeadquarters();
        if (StringUtils.isNotEmpty(isHeadquarters)) {
            if (isHeadquarters.equals(YesOrNo.NO.getName())) {
                contractHead.setIsHeadquarters(YesOrNo.NO.getValue());
            } else if (isHeadquarters.equals(YesOrNo.YES.getName())) {
                contractHead.setIsHeadquarters(YesOrNo.YES.getValue());
            } else {
                errorMessage.append("是否总部只能填\"是\"或\"否\"");
            }
        }*/
        contractHead.setContractType("MIAN_CONTRACT_ADD");
        contractHeadModelDTO.setErrorMessage(errorMessage.toString());
    }


    /**
     * 物料明细校验
     *
     * @param contractMaterialModelDTO
     * @param contractMaterial
     * @param errorMessage
     */
    private void checkContractMaterialParam(ContractMaterialModelDTO contractMaterialModelDTO, ContractMaterial contractMaterial, StringBuffer errorMessage, ContractHead contractHead) {
        // 业务实体
        if (StringUtils.isNotEmpty(contractMaterialModelDTO.getBuName())) {
            String buName = contractMaterialModelDTO.getBuName().trim();
            List<Organization> organizationByNameList = baseClient.getOrganizationByNameList(Arrays.asList(buName));
            if (CollectionUtils.isNotEmpty(organizationByNameList)) {
                Organization organization = organizationByNameList.get(0);
                contractMaterial.setBuCode(organization.getOrganizationCode());
                contractMaterial.setBuId(organization.getOrganizationId());
                contractMaterial.setBuName(organization.getOrganizationName());
                contractMaterial.setBuFullPathId(organization.getFullPathId());
                // 库存组织
                if (StringUtils.isNotEmpty(contractMaterialModelDTO.getInvName())) {
                    String invName = contractMaterialModelDTO.getInvName();
                    List<Organization> Organizations = baseClient.queryIvnByOuId(contractMaterial.getBuId());
                    if (CollectionUtils.isNotEmpty(Organizations)) {
                        Organizations.forEach(organization1 -> {
                            if (invName.equals(organization1.getOrganizationName())) {
                                contractMaterial.setInvCode(organization1.getOrganizationCode())
                                        .setInvId(organization1.getOrganizationId())
                                        .setInvName(organization1.getOrganizationName())
                                        .setInvFullPathId(organization1.getFullPathId());
                            }
                        });
                        if (StringUtils.isEmpty(contractMaterial.getInvName())) {
                            errorMessage.append("库存组织不不存在；");
                        }
                    } else {
                        errorMessage.append("该业务实体下不存在库存组织；");
                    }
                } else {
                    errorMessage.append("库存组织不能为空；");
                }
            } else {
                errorMessage.append("业务实体不存在；");
            }
        } else {
            errorMessage.append("业务实体不能为空；");
        }
        // 物料编码
        if (StringUtils.isNotEmpty(contractMaterialModelDTO.getMaterialCode())) {
            String materialCode = contractMaterialModelDTO.getMaterialCode().trim();
            MaterialItem materialItem = baseClient.findMaterialItemByMaterialCode(materialCode);
            if (materialItem != null) {
                if (materialItem.getMaterialName().equals(contractMaterialModelDTO.getMaterialName())) {
                    boolean flag = baseClient.checkBigClassCodeIsContain50(Arrays.asList(materialItem.getMaterialId()));
                    if (!flag) {
                        contractMaterial.setMaterialCode(materialItem.getMaterialCode())
                                .setMaterialId(materialItem.getMaterialId())
                                .setMaterialName(materialItem.getMaterialName())
                                .setCategoryName(materialItem.getCategoryName()).setCategoryCode(materialItem.getCategoryCode())
                                .setCategoryId(materialItem.getCategoryId())
                                .setUnitName(materialItem.getUnitName())
                                .setSpecification(materialItem.getSpecification());
                    } else {
                        errorMessage.append("物料大类编码为50的不允许创建合同；");
                    }
                } else {
                    errorMessage.append("物料编码与物料名称不匹配；");
                }
            } else {
                errorMessage.append("物料编码不存在；");
            }
        } else {
            errorMessage.append("物料编码不能为空；");
        }
        // 交货地址
        if (StringUtils.isNotEmpty(contractMaterialModelDTO.getTradingLocations())) {
            contractMaterial.setTradingLocations(contractMaterialModelDTO.getTradingLocations());
        }
        // 含税单价
        if (StringUtils.isNotEmpty(contractMaterialModelDTO.getTaxedPrice())) {
            String taxedPrice = contractMaterialModelDTO.getTaxedPrice().trim();
            if (StringUtil.isDigit(taxedPrice)) {
                contractMaterial.setTaxedPrice(new BigDecimal(taxedPrice));
            } else {
                errorMessage.append("含税单价格式错误；");
            }
        } else {
            errorMessage.append("含税单价不能为空；");
        }

        // 数量
        if (StringUtils.isNotEmpty(contractMaterialModelDTO.getContractQuantity())) {
            String contractQuantity = contractMaterialModelDTO.getContractQuantity().trim();
            if (StringUtil.isDigit(contractQuantity)) {
                contractMaterial.setContractQuantity(new BigDecimal(contractQuantity));
            } else {
                errorMessage.append("数量格式错误；");
            }
        }

        // 税率
        if (StringUtils.isNotEmpty(contractMaterialModelDTO.getTaxKey())) {
            String taxKey = contractMaterialModelDTO.getTaxKey().trim();
            List<PurchaseTax> purchaseTaxes = baseClient.listTaxAll();
            if (CollectionUtils.isNotEmpty(purchaseTaxes)) {
                purchaseTaxes.forEach(object -> {
                    if (taxKey.equals(object.getTaxKey())) {
                        contractMaterial.setTaxKey(object.getTaxKey());
                        if (StringUtils.isNotEmpty(contractMaterialModelDTO.getTaxRate())) {
                            if (StringUtil.isDigit(contractMaterialModelDTO.getTaxRate())) {
                                if (new BigDecimal(contractMaterialModelDTO.getTaxRate()).compareTo(object.getTaxCode()) == 0) {
                                    contractMaterial.setTaxRate(object.getTaxCode());

                                } else {
                                    errorMessage.append("税率与税率编码不匹配；");
                                }
                            } else {
                                errorMessage.append("税率格式非法；");
                            }
                        } else {
                            errorMessage.append("税率不能为空；");
                        }
                    }
                });
                if (StringUtils.isEmpty(contractMaterial.getTaxKey())) {
                    errorMessage.append("税率编码不存在；");
                }
            }
        } else {
            errorMessage.append("税率编码不能为空");
        }
        // 价格有效日期从
        String startDate = contractMaterialModelDTO.getStartDate();
        if (StringUtil.notEmpty(startDate)) {
            startDate = startDate.trim();
            try {
                Date date = DateUtil.parseDate(startDate);
                LocalDate localDate = DateUtil.dateToLocalDate(date);
                contractMaterial.setStartDate(localDate);
                if (contractHead.getEffectiveDateFrom() != null) {
                    if (contractHead.getEffectiveDateFrom().compareTo(localDate) > 0) {
                        errorMessage.append("价格有效日期从不能小于合同头有效期从； ");
                    }
                }
            } catch (Exception e) {

            }
        } else {
            errorMessage.append("价格有效日期从不能为空; ");
        }
        // 价格失效日期至
        String endDate = contractMaterialModelDTO.getEndDate();
        if (StringUtil.notEmpty(endDate)) {
            endDate = endDate.trim();
            try {
                Date date = DateUtil.parseDate(endDate);
                LocalDate localDate = DateUtil.dateToLocalDate(date);
                contractMaterial.setEndDate(localDate);
                if (contractHead.getEffectiveDateTo() != null) {
                    if (contractHead.getEffectiveDateTo().compareTo(localDate) < 0) {
                        errorMessage.append("价格有效日期至不能大于合同头有效期至； ");
                    }
                }
            } catch (Exception e) {
                errorMessage.append("价格有效日期至格式非法; ");
            }
        } else {
            errorMessage.append("价格有效日期至不能为空; ");
        }


        // 已下单数量
        if (StringUtils.isNotEmpty(contractMaterialModelDTO.getOrderQuantity())) {
            if (StringUtil.isDigit(contractMaterialModelDTO.getOrderQuantity())) {
                contractMaterial.setOrderQuantity(new BigDecimal(contractMaterialModelDTO.getOrderQuantity()));
            } else {
                errorMessage.append("已下单数量格式非法； ");
            }
        }
        contractMaterialModelDTO.setErrorMessage(errorMessage.toString());

    }

    /**
     * 付款计划校验
     *
     * @param payPlanModelDTO
     * @param payPlan
     * @param errorMessage
     */
    private void checkPayPlanParam(PayPlanModelDTO payPlanModelDTO, PayPlan payPlan, StringBuffer errorMessage, BigDecimal includeTaxAmount, HashSet only) {
        String[] dict = {"PAYMENT_PERIOD", "PAYMENT_MODE"};
        Map<String, String> dictMap = setMapValueKey(dict);
        // 因为付款阶段和天数有重复所以要分开
        String[] dict1 = {"PAYMENT_STAGE"};
        Map<String, String> dictMap1 = setMapValueKey(dict1);

        // 付款阶段
        if (StringUtils.isNotEmpty(payPlanModelDTO.getPaymentStage())) {
            String paymentStage = payPlanModelDTO.getPaymentStage().trim();
            if (StringUtils.isNotEmpty(dictMap1.get(paymentStage))) {
                payPlan.setPaymentStage(dictMap1.get(paymentStage));
            } else {
                errorMessage.append("付款阶段不存在；");
            }
        }
//        else {
//            errorMessage.append("付款阶段不能为空；");
//        }
        // 付款期数
        if (StringUtils.isNotEmpty(payPlanModelDTO.getPaymentPeriod())) {
            if (StringUtil.isDigit(payPlanModelDTO.getPaymentPeriod())) {
                payPlan.setPaymentPeriod(payPlanModelDTO.getPaymentPeriod());
                if (!only.add(payPlanModelDTO.getPaymentPeriod())) {
                    errorMessage.append("付款期重复；");
                }
            } else {
                errorMessage.append("付款期数格式非法；");
            }
        }
//        else {
//            errorMessage.append("付款期数不能为空；");
//        }
        //付款条件
        if (StringUtils.isNotEmpty(payPlanModelDTO.getPayExplain())) {
            String payExplain = payPlanModelDTO.getPayExplain().trim();
            List<PayType> activationPaymentTerms = iPayTypeService.getActivationPaymentTerms();
            if (CollectionUtils.isNotEmpty(activationPaymentTerms)) {
                activationPaymentTerms.forEach(object -> {
                    if (payExplain.equals(object.getPayExplain())) {
                        payPlan.setPayExplain(object.getPayTypeId() + "");
                    }
                });
            }
            if (StringUtils.isEmpty(payPlan.getPayExplain())) {
                errorMessage.append("付款条件不存在；");
            }
        }
//        else {
//            errorMessage.append("付款条件不能为空；");
//        }
        //天数
        if (StringUtils.isNotEmpty(payPlanModelDTO.getDateNum())) {
            String dateNum = payPlanModelDTO.getDateNum().trim();
            if (StringUtils.isNotEmpty(dictMap.get(dateNum))) {
                payPlan.setDateNum(dictMap.get(dateNum));
            } else {
                errorMessage.append("天数不存在；");
            }
        }
//        else {
//            errorMessage.append("天数不能为空；");
//        }
        //付款比例
        if (StringUtils.isNotEmpty(payPlanModelDTO.getPaymentRatio())) {
            String paymentRatio = payPlanModelDTO.getPaymentRatio().trim();
            if (StringUtil.isDigit(paymentRatio)) {
                payPlan.setPaymentRatio(new BigDecimal(paymentRatio));
                payPlan.setStagePaymentAmount(payPlan.getPaymentRatio().multiply(includeTaxAmount).divide(new BigDecimal(100)));
            } else {
                errorMessage.append("付款比例格式非法；");
            }
        }
//        else {
//            errorMessage.append("付款比例不能为空；");
//        }
        //计划付款日期
        String plannedPaymentDate = payPlanModelDTO.getPlannedPaymentDate();
        if (StringUtil.notEmpty(plannedPaymentDate)) {
            try {
                Date date = DateUtil.parseDate(plannedPaymentDate);
                LocalDate localDate = DateUtil.dateToLocalDate(date);
                payPlan.setPlannedPaymentDate(localDate);
            } catch (Exception e) {
                errorMessage.append("计划付款日期格式非法; ");
            }
        }

        //付款方式
        if (StringUtils.isNotEmpty(payPlanModelDTO.getPayMethod())) {
            String payMethod = payPlanModelDTO.getPayMethod().trim();
            if (StringUtils.isNotEmpty(dictMap.get(payMethod))) {
                payPlan.setPayMethod(dictMap.get(payMethod));
            } else {
                errorMessage.append("付款方式不存在；");
            }
        }
//        else {
//            errorMessage.append("付款方式不能为空；");
//        }
        // 现阶段付款金额
        if (StringUtils.isNotEmpty(payPlanModelDTO.getStagePaymentAmount())) {
            if (StringUtil.isDigit(payPlanModelDTO.getStagePaymentAmount())) {
                payPlan.setStagePaymentAmount(new BigDecimal(payPlanModelDTO.getStagePaymentAmount()));
            } else {
                errorMessage.append("现阶段付款金额格式非法；");
            }
        }
        // 已付金额
        if (StringUtils.isNotEmpty(payPlanModelDTO.getPaidAmount())) {
            if (StringUtil.isDigit(payPlanModelDTO.getPaidAmount())) {
                payPlan.setStagePaymentAmount(new BigDecimal(payPlanModelDTO.getPaidAmount()));
            } else {
                errorMessage.append("已付款金额格式非法；");
            }
        }
        payPlanModelDTO.setErrorMessage(errorMessage.toString());
    }

    /**
     * 合作伙伴校验
     *
     * @param contractPartnerModelDTO
     * @param contractPartner
     * @param errorMessage
     */
    private void checkContractPartnerParam(ContractPartnerModelDTO contractPartnerModelDTO, ContractPartner contractPartner, StringBuffer errorMessage, ContractHeadModelDTO contractHeadModelDTO) {
        //合作类型
        if (StringUtils.isNotEmpty(contractPartnerModelDTO.getPartnerType())) {
            String partnerType = contractPartnerModelDTO.getPartnerType().trim();
            if (ContractHeadConst.PARTY_A.equals(partnerType) || ContractHeadConst.PARTY_C.equals(partnerType)) { // 甲方
                contractPartner.setPartnerType(partnerType);
                // 合作伙伴公司名称
                if (StringUtils.isNotEmpty(contractPartnerModelDTO.getPartnerName())) {
                    String partnerName = contractPartnerModelDTO.getPartnerName().trim();
                    Organization organization = baseClient.getOrganization(new Organization().setCeeaCompanyName(partnerName));
                    if (organization != null) {
                        contractPartner.setPartnerName(organization.getCeeaCompanyName());
                    } else {
                        errorMessage.append("伙伴名称不存在；");
                    }
                } else {
                    errorMessage.append("伙伴名称不能为空；");
                }
            } else if (ContractHeadConst.PARTY_B.equals(partnerType)) { // 乙方
                contractPartner.setPartnerType(ContractHeadConst.PARTY_B);
                if (StringUtils.isNotEmpty(contractPartnerModelDTO.getPartnerName())) {
                    if (contractPartnerModelDTO.getPartnerName().equals(contractHeadModelDTO.getVendorName())) {
                        contractPartner.setPartnerName(contractPartnerModelDTO.getPartnerName());
                    } else {
                        errorMessage.append("伙伴名称不存在与供应商名称不一致；");
                    }
                }
            } else {
                errorMessage.append("合作类型不存在；");
            }
        } else {
            errorMessage.append("合作类型不能为空；");
        }
        contractPartnerModelDTO.setErrorMessage(errorMessage.toString());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long genContractFromApproval(ApprovalToContractDetail detail) {
        List<ApprovalBiddingItemVO> approvalItems = detail.getContractLineList();
        ApprovalBiddingItemVO itemInfo = approvalItems.get(0);
        CompanyInfo tempCompanyInfo = supplierClient.getCompanyInfo(itemInfo.getVendorId());
        Long contractId = IdGenrator.generate();
        ContractHead contractHead = new ContractHead()
                .setContractName("来自价格审批单:" + detail.getApprovalNo())
                .setContractNo(baseClient.seqGen(SequenceCodeConstant.SEQ_CONTRACT_NO))
                //主合同新增
                .setContractType(ContractType.MIAN_CONTRACT_ADD.name())
                //类型不需要设置 contract_class
                .setVendorId(tempCompanyInfo.getCompanyId())
                .setVendorCode(tempCompanyInfo.getCompanyCode())
                .setVendorName(tempCompanyInfo.getCompanyName())
                .setErpVendorCode(tempCompanyInfo.getErpVendorCode())
                .setErpVendorId(tempCompanyInfo.getErpVendorId())
                .setContractHeadId(contractId)
                .setEnable(YesOrNo.YES.getValue())
                .setIsFrameworkAgreement(YesOrNo.NO.getValue())
                .setSourceType(detail.getSourceType())
                .setContractStatus(ContractStatus.DRAFT.name())
                .setSourceType(ContractSourceType.PRICE_APPROVAL.name())
                .setCeeaIfVirtual(detail.getCeeaIfVirtual())
                .setSourceNumber(detail.getApprovalNo());

        //行处理
        List<ContractMaterial> contractMaterials = new LinkedList<>();
        Set<Long> ouIds = new HashSet<>();
        Set<String> purchaseNum = new HashSet<>();
        Set<Long> orgIds = new HashSet<>();
        boolean notFind = true;
        for (ApprovalBiddingItemVO e : approvalItems) {
            if (notFind && Objects.equals(e.getStandardCurrency(), e.getCurrencyCode())) {
                notFind = false;
                contractHead
                        .setCurrencyCode(e.getCurrencyCode())
                        .setCurrencyId(e.getCurrencyId())
                        .setCurrencyName(e.getCurrencyName());
            }
            //设置单号id
            e.setFromContractId(e.getFromContractId());
            //ou组id
            Long ouId = e.getOuId();
            if (Objects.nonNull(ouId)) {
                ouIds.add(ouId);
            }
            //采购需求单号
            String purchaseRequestNum = e.getPurchaseRequestNum();
            if (Objects.nonNull(purchaseRequestNum)) {
                purchaseNum.add(purchaseRequestNum);
            }
            //业务实体id
            Long orgId = e.getOrgId();
            if (Objects.nonNull(orgId)) {
                orgIds.add(orgId);
            }
        }
        if (notFind) {
            contractHead.setCurrencyCode("CNY");
            contractHead.setCurrencyName("人民币");
        }


        //批量先获取外围系统数据
        List<BaseOuGroupDetailVO> baseOuGroupDetailVOS = baseClient.queryOuInfoDetailByIds(ouIds);
        List<RequirementHead> requirementHeadByNumber = pmClient.getRequirementHeadByNumber(purchaseNum);
        //给合同行赋值,这里如果是ou组,就把对应信息置空
        //中标总金额
        boolean isCalculate = true;
        BigDecimal total = null;
        for (ApprovalBiddingItemVO approvalBiddingItem : approvalItems) {
            RequirementHead requirementHead = null;
            if (!org.springframework.util.CollectionUtils.isEmpty(requirementHeadByNumber)) {
                //找不到单据，不赋值
                for (RequirementHead head : requirementHeadByNumber) {
                    if (Objects.nonNull(head) && Objects.equals(head.getRequirementHeadNum(), approvalBiddingItem.getPurchaseRequestNum())) {
                        requirementHead = head;
                        break;
                    }
                }
            }
            if (Objects.isNull(requirementHead)) {
                requirementHead = new RequirementHead();
            }
            List<ContractMaterial> temp = createContractMaterialByItem(approvalBiddingItem, baseOuGroupDetailVOS, requirementHead);
            //设置id信息
            temp.forEach(e -> {
                if (Objects.nonNull(e.getCeeaOuId())) {
                    e.setOrderQuantity(null)
                            .setAmount(null)
                            .setUnAmount(null);
                }
                e.setContractHeadId(contractId).setContractMaterialId(IdGenrator.generate());
            });
            contractMaterials.addAll(temp);
            BigDecimal contractQuantity = approvalBiddingItem.getQuotaQuantity();
            BigDecimal taxedPrice = approvalBiddingItem.getTaxPrice();
            if (Objects.isNull(contractQuantity) || Objects.isNull(taxedPrice)) {
                isCalculate = false;
            } else {
                total = Objects.isNull(total) ? contractQuantity.multiply(taxedPrice) : total.add(contractQuantity.multiply(taxedPrice));
            }
        }
        //存进db
        if (isCalculate) {
            contractHead.setIncludeTaxAmount(total);
        }

        // 非框架协议
        CompanyInfo companyInfo = supplierClient.getCompanyInfo(contractHead.getVendorId());
        if (YesOrNo.YES.getValue().equals(companyInfo.getCompanyLevel())) {
            // 存在“是否A级”标识为Y时，合同级别为A级关联；
            contractHead.setContractLevel("SA");
        } else {
            // 存在“是否A级”标识为N时，合同级别按照合同定级规则维护获取；
            String contractLevel = getContractLevel(contractMaterials);
            Assert.notNull(contractLevel, "检查合同物料的小类和金额是否已维护,请检查物料小类对应的合同定级规则是否已维护!!!");
            contractHead.setContractLevel(contractLevel);
        }

        this.save(contractHead);
        //保存物料行
        iContractMaterialService.saveBatch(contractMaterials);
        //生成付款条款
        List<PayPlan> payPlans = new LinkedList<>();
        for (int i = 0; i < itemInfo.getApprovalBiddingItemPaymentTermList().size(); i++) {
            ApprovalBiddingItemPaymentTerm e = itemInfo.getApprovalBiddingItemPaymentTermList().get(i);
            PayPlan payPlan = new PayPlan()
                    .setPayMethod(e.getPaymentWay())
                    .setDateNum(e.getPaymentDayCode())
                    .setPayExplain(String.valueOf(e.getPaymentTerm()))
                    .setDelayedDays(e.getPaymentDay().longValue())
                    .setPayPlanId(IdGenrator.generate())
                    .setPaymentStage(e.getPaymentStage())
                    .setPaymentRatio(e.getPaymentRatio())
                    .setPaymentPeriod(String.valueOf(i + 1))
                    .setContractHeadId(contractId);
            payPlans.add(payPlan);
        }
        iPayPlanService.saveBatch(payPlans);

        //生成乙方,甲方会由前端查其他接口查出来
        ContractPartner contractPartner = new ContractPartner();
        contractPartner.setPartnerType("乙方")
                .setPartnerId(IdGenrator.generate())
                .setContractHeadId(contractId)
                .setPartnerName(contractHead.getVendorName());
        //新增合作伙伴
        iContractPartnerService.save(contractPartner);

        //回写单据号
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                //添加合同校验列表，如果在转换中，不允许删除
                redisTemplate.opsForSet().add("contractUpdateApproval", String.valueOf(contractId));
                approvalItems.forEach(e -> e.setFromContractId(contractId));
                inqClient.updateApprovalBidingInfo(approvalItems);
                redisTemplate.opsForSet().remove("contractUpdateApproval", String.valueOf(contractId));
            }
        });
        return contractId;
    }

    private AdvanceApplyHead setBaseByContract(ContractHead contractHead, AdvanceApplyHeadVo advanceApplyHeadVo) {
        return advanceApplyHeadVo.setVendorId(contractHead.getVendorId())
                .setErpVendorCode(contractHead.getErpVendorCode())
                .setVendorCode(contractHead.getVendorCode())
                .setVendorName(contractHead.getVendorName())
                .setCurrencyId(contractHead.getCurrencyId())
                .setCurrencyCode(contractHead.getCurrencyCode())
                .setCurrencyName(contractHead.getCurrencyName())
                .setIfPowerStation(contractHead.getIsPowerStation())
                .setIncludeTaxAmount(contractHead.getIncludeTaxAmount());
    }

    private List<ContractMaterial> createContractMaterialByItem(ApprovalBiddingItemVO approvalBiddingItem, List<BaseOuGroupDetailVO> ous, RequirementHead requirementHead) {
        //项目编号 , 项目名称
        List<ContractMaterial> result = new LinkedList<>();
        Long ouId = approvalBiddingItem.getOuId();
        boolean isOuGroup = Objects.nonNull(ouId);
        if (isOuGroup) {
            BaseOuGroupDetailVO detailVO = ous.stream().filter(e -> e.getOuGroupId().equals(ouId))
                    .findAny().orElseThrow(() -> new BaseException(String.format("ou组id为%d不存在", ouId)));
            List<BaseOuDetailVO> details = detailVO.getDetails();
            for (BaseOuDetailVO detail : details) {
                ContractMaterial contractMaterial = new ContractMaterial()
                        .setBuCode(detail.getOuCode())
                        .setBuName(detail.getOuName())
                        .setBuId(detail.getOuId())
                        .setInvCode(detail.getInvCode())
                        .setInvId(detail.getInvId())
                        .setInvName(detail.getInvName())
                        .setCeeaOuId(detailVO.getOuGroupId())
                        .setCeeaOuName(detailVO.getOuGroupName())
                        .setCeeaOuNumber(detailVO.getOuGroupCode());
                assignValue(contractMaterial, approvalBiddingItem, requirementHead);
                result.add(contractMaterial);
            }
        } else {
            ContractMaterial contractMaterial = new ContractMaterial()
                    .setBuId(approvalBiddingItem.getOrgId())
                    .setBuName(approvalBiddingItem.getOrgName())
                    .setBuCode(approvalBiddingItem.getOrgCode())
                    .setInvId(approvalBiddingItem.getOrganizationId())
                    .setInvCode(approvalBiddingItem.getOrganizationCode());
            contractMaterial.setInvName(approvalBiddingItem.getOrganizationName());
            assignValue(contractMaterial, approvalBiddingItem, requirementHead);
            result.add(contractMaterial);
        }
        return result;
    }

    //除了inv,bu其他都赋值
    private void assignValue(ContractMaterial contractMaterial, ApprovalBiddingItemVO approvalBiddingItem, RequirementHead requirementHead) {
        contractMaterial.setCeeaSourceLineId(approvalBiddingItem.getApprovalBiddingItemId())
                .setSourceNumber(approvalBiddingItem.getCeeaSourceNo())
                .setSourceType(approvalBiddingItem.getSourceType())
                .setTradingLocations(approvalBiddingItem.getArrivalPlace())
                .setMaterialId(approvalBiddingItem.getItemId())
                .setMaterialCode(approvalBiddingItem.getItemCode())
                .setMaterialName(approvalBiddingItem.getItemName())
                .setCategoryId(approvalBiddingItem.getCategoryId())
                .setCategoryCode(approvalBiddingItem.getCategoryCode())
                .setCategoryName(approvalBiddingItem.getCategoryName())
                .setTradeTerm(approvalBiddingItem.getTradeTerm())
                .setShelfLife(Objects.isNull(approvalBiddingItem.getWarrantyPeriod()) ? null : BigDecimal.valueOf(approvalBiddingItem.getWarrantyPeriod()))
                //中标数量
                .setContractQuantity(approvalBiddingItem.getQuotaQuantity())
                //单位
                .setUnitCode(approvalBiddingItem.getUnit())
                .setUnitName(approvalBiddingItem.getUnit())
                // 含税单价
                .setTaxedPrice(approvalBiddingItem.getTaxPrice())
                //海鲜价价格价格字段补充
                .setIsSeaFoodFormula(approvalBiddingItem.getIsSeaFoodFormula())
                .setFormulaId(approvalBiddingItem.getFormulaId())
                .setFormulaValue(approvalBiddingItem.getFormulaValue())
                .setEssentialFactorValues(approvalBiddingItem.getEssentialFactorValues())
                .setFormulaResult(approvalBiddingItem.getFormulaResult())
                .setPriceJson(approvalBiddingItem.getPriceJson());
        BigDecimal contractQuantity = contractMaterial.getContractQuantity();
        BigDecimal taxedPrice = contractMaterial.getTaxedPrice();
        if (Objects.nonNull(contractQuantity) && Objects.nonNull(taxedPrice)) {
            contractMaterial.setAmount(contractQuantity.multiply(taxedPrice));
        }
        contractMaterial.setTaxKey(approvalBiddingItem.getTaxKey())
                .setTaxRate(approvalBiddingItem.getTaxRate());
        BigDecimal taxRate = contractMaterial.getTaxRate();
        BigDecimal amount = contractMaterial.getAmount();
        if (Objects.nonNull(taxRate) && Objects.nonNull(amount)) {
            contractMaterial.setUnAmount(calculateNoTaxPrice(amount, taxRate.multiply(BigDecimal.valueOf(0.01))));
        }
        contractMaterial.setStartDate(approvalBiddingItem.getStartTime())
                .setEndDate(approvalBiddingItem.getEndTime());
        if (Objects.nonNull(requirementHead)) {
            contractMaterial.setItemNumber(requirementHead.getCeeaProjectNum())
                    .setItemName(requirementHead.getCeeaProjectName());
        }
    }

    private BigDecimal calculateNoTaxPrice(BigDecimal taxPrice, BigDecimal taxRate) {
        //转换成未税价,未含税*（1+税率）=含税
        BigDecimal noTaxPrice = taxPrice.divide(BigDecimal.ONE.add(taxRate), 8, BigDecimal.ROUND_HALF_DOWN);
        return noTaxPrice;
    }

    @Override
    public void bulkMaintenanceFramework(BulkMaintenanceFrameworkParamDto bulkMaintenanceFrameworkParamDto) {
        List<Long> contractIds = bulkMaintenanceFrameworkParamDto.getContractIds();
        if (CollectionUtils.isNotEmpty(contractIds)) {
            String contractCode = bulkMaintenanceFrameworkParamDto.getContractCode();
            String contractName = bulkMaintenanceFrameworkParamDto.getContractName();
            Long contractHeadId = bulkMaintenanceFrameworkParamDto.getContractHeadId();
            if (StringUtil.notEmpty(contractCode) && StringUtil.notEmpty(contractName)) {
                List<ContractHead> contractHeads = this.listByIds(contractIds);
                if (CollectionUtils.isNotEmpty(contractHeads)) {
                    // 检查是否同一个供应商
                    Long vendorId = contractHeads.get(0).getVendorId();
                    Assert.notNull(vendorId, "存在合同供应商为空");
                    contractHeads.forEach(contractHead -> {
                        if (StringUtil.notEmpty(contractHead.getVendorId())) {
                            Assert.isTrue(vendorId.equals(contractHead.getVendorId()), "批量维护框架协议必须是同一个供应商");
                        } else {
                            throw new BaseException("存在合同供应商为空");
                        }
                        // 如果是框架协议，把框架协议编号维护到合同编号上去
                        if (YesOrNo.YES.getValue().equals(contractHead.getCeeaIfVirtual())) {
                            contractHead.setContractCode(contractCode);
                        }
                        contractHead.setFrameworkAgreementId(contractHeadId);
                        contractHead.setFrameworkAgreementCode(contractCode);
                        contractHead.setFrameworkAgreementName(contractName);
                    });
                }
                this.updateBatchById(contractHeads);
            } else {
                throw new BaseException("请选择框架协议");
            }
        }
    }

    @Override
    public void signingCallback(ContractHead head) {
        Long contractHeadId = head.getContractHeadId();
        Assert.notNull(contractHeadId,"业务单据ID不能为空");
        ContractHead contractHead = this.getById(contractHeadId);
        Assert.notNull(contractHead,"找不到该合同单据");
        // 状态
        contractHead.setContractStatus(ContractStatus.ARCHIVED.name());
        contractHead.setStampContractFileuploadId(head.getStampContractFileuploadId());
        contractHead.setStampContractFileName(contractHead.getContractName()+".pdf");
        this.updateById(contractHead);
    }

    @Override
    public Map<String, Object> release(PushContractParam pushContractParam) throws Exception {
        Assert.notNull(pushContractParam.getContractHeadId(),"contractHeadId: 不能为空");
        Assert.notNull(pushContractParam.getFileuploadId(),"fileuploadId: 不能为空");
        ContractHead contractHead = this.getById(pushContractParam.getContractHeadId());
        Assert.notNull(contractHead,"找不到合同: contractHeadId = " + pushContractParam.getContractHeadId());
        SigningParam signingParam = new SigningParam();
        signingParam.setContractCode(contractHead.getContractNo());
        signingParam.setContractName(contractHead.getContractName());
        signingParam.setFileuploadId(pushContractParam.getFileuploadId());
        signingParam.setReceiptId(pushContractParam.getContractHeadId().toString());
        signingParam.setPhone(pushContractParam.getPhone());
        signingParam.setEmail(pushContractParam.getEmail());
        signingParam.setName(pushContractParam.getName());

        // 推送合同
        Map<String, Object> result = signatureClient.addSigning(signingParam);

        // 更新合同状态
        contractHead.setOriginalContractFileuploadId(pushContractParam.getFileuploadId());
//        contractHead.setContractStatus(ContractStatus.SUPPLIER_CONFIRMING.name());
        contractHead.setContractStatus(ContractStatus.ARCHIVED.name());
        this.updateById(contractHead);

        return result;
    }
}
