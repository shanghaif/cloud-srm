package com.midea.cloud.srm.ps.invoice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.OutSystemConvertUnit;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.pm.po.CeeaWarehousingReturnDetailEnum;
import com.midea.cloud.common.enums.pm.ps.BoeTypeCode;
import com.midea.cloud.common.enums.pm.ps.InvoiceStatus;
import com.midea.cloud.common.enums.supcooperate.InvoiceNoticeStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.perf.PerformanceClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.soap.DataSourceEnum;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementHeadQueryDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.ps.advance.entity.AdvanceApplyHead;
import com.midea.cloud.srm.model.pm.ps.anon.external.FsscStatus;
import com.midea.cloud.srm.model.pm.ps.http.*;
import com.midea.cloud.srm.model.supplier.info.entity.BankInfo;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceSaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.*;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;
import com.midea.cloud.srm.ps.advance.service.IAdvanceApplyHeadService;
import com.midea.cloud.srm.ps.http.fssc.service.IFSSCReqService;
import com.midea.cloud.srm.ps.invoice.service.IOnlineInvoiceService;
import com.midea.cloud.srm.supcooperate.invoice.service.IInvoiceDetailService;
import com.midea.cloud.srm.supcooperate.invoice.service.IInvoiceNoticeService;
import com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoiceAdvanceService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  网上开票表 服务实现类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-31 16:47:48
 *  修改内容:
 * </pre>
 */
@Slf4j
@Service
public class OnlineInvoiceServiceImpl implements IOnlineInvoiceService {

    @Autowired
    private IFSSCReqService ifsscReqService;

    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoiceService scIOnlineInvoiceService;

    @Autowired
    private IOnlineInvoiceAdvanceService scIOnlineInvoiceAdvanceService;

    @Autowired
    private IInvoiceDetailService scIInvoiceDetailService;

    @Autowired
    private IInvoiceNoticeService scIInvoiceNoticeService;

    @Autowired
    private IAdvanceApplyHeadService iAdvanceApplyHeadService;

    @Autowired
    private IRequirementHeadService iRequirementHeadService;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private PerformanceClient performanceClient;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IOrderDetailService iOrderDetailService;

    @Override
    public FSSCResult submitOnlineInvoice(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        //提交前检验
        checkBeforeSubmit(onlineInvoiceSaveDTO);
        //提交给费控
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();

        //2020-12-23 隆基回迁产品-去除外部接口对接
        FSSCResult fsscResult = new FSSCResult();
        //0金额发票不传费控
//        log.info("-----------清理网上开票数据--提交到费控---------");
//        if (YesOrNo.YES.getValue().equals(onlineInvoice.getVirtualInvoice()) || onlineInvoice.getTaxTotalAmount().compareTo(BigDecimal.ZERO) != 0) {
//            fsscResult = submitToFSSC(onlineInvoiceSaveDTO);
//        }
//        //如果是WARNING的话,需要手动回滚事务,且返回对应结果给前端进行页面渲染
//        if(FSSCResponseCode.WARN.getCode().equals(fsscResult.getCode())){
//            //手动回滚事务
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return fsscResult;
//        }
//        if (FSSCResponseCode.ERROR.getCode().equals(fsscResult.getCode()) || StringUtils.isBlank(fsscResult.getCode())) {
//            if(StringUtils.isNotBlank(fsscResult.getMsg()) && fsscResult.getMsg().indexOf("已在审批中，在共享单据是") > 0){
//                log.info("----已提交到费控系统-----");
//                log.info(fsscResult.getMsg());
//            }else{
//                throw new BaseException(LocaleHandler.getLocaleMsg(fsscResult.getMsg()));
//            }
//        }
        onlineInvoice.setBoeNo(null).setPrintUrl(null);
        //提交时,释放预付引用标记,且扣减可用金额
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = onlineInvoiceSaveDTO.getOnlineInvoiceAdvances();
        releaseAndReduce(onlineInvoiceAdvances);
        scIOnlineInvoiceService.submit(onlineInvoiceSaveDTO, InvoiceStatus.APPROVAL.name());
        return fsscResult;
    }

    private synchronized void releaseAndReduce(List<OnlineInvoiceAdvance> onlineInvoiceAdvances) {
        if (CollectionUtils.isNotEmpty(onlineInvoiceAdvances)) {
            for (OnlineInvoiceAdvance onlineInvoiceAdvance : onlineInvoiceAdvances) {
                if (onlineInvoiceAdvance == null) continue;
                //检查预付款状态及数量
                AdvanceApplyHead byId = iAdvanceApplyHeadService.getById(onlineInvoiceAdvance.getAdvanceApplyHeadId());
                if (byId != null) {
                    BigDecimal usableAmount = byId.getUsableAmount() == null ? BigDecimal.ZERO : byId.getUsableAmount();
                    if (usableAmount.compareTo(BigDecimal.ZERO) < 1) {
                        throw new BaseException(LocaleHandler.getLocaleMsg("预付款可用金额不足,请检查!"));
                    }
                }
                BigDecimal chargeOffAmount = onlineInvoiceAdvance.getChargeOffAmount();//本次核销金额
                BigDecimal usableAmount = byId.getUsableAmount();//可用金额
                BigDecimal hangAccountAmount = onlineInvoiceAdvance.getHangAccountAmount();//挂账金额(申请金额)
                BigDecimal newUsableAmount = usableAmount.subtract(chargeOffAmount);//更新可用金额(不能大于挂账金额)
                if (newUsableAmount.compareTo(BigDecimal.ZERO) == -1) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("本次核销金额不能大于可用金额,请检查!"));
                }
                onlineInvoiceAdvance.setUsableAmount(newUsableAmount);
                iAdvanceApplyHeadService.setQuote(onlineInvoiceAdvance, YesOrNo.NO.getValue());
            }
        }
    }

    private void checkBeforeSubmit(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        List<OnlineInvoiceDetail> onlineInvoiceDetails = onlineInvoiceSaveDTO.getOnlineInvoiceDetails();
        Assert.notNull(onlineInvoice, "onlineInvoice不能为空");
        if (InvoiceStatus.DRAFT.name().equals(onlineInvoice.getInvoiceStatus()) ||
                InvoiceStatus.UNDER_APPROVAL.name().equals(onlineInvoice.getInvoiceStatus()) ||
                InvoiceStatus.REJECTED.name().equals(onlineInvoice.getInvoiceStatus())) {
            log.info("网上发票{}状态为新建或审批中，允许往下提交" , onlineInvoice.getOnlineInvoiceNum());
        }else{
            throw new BaseException(LocaleHandler.getLocaleMsg("单据状态为新建或驳回时,才可提交"));
        }
        //校验是否已经保存调整过
        if (CollectionUtils.isNotEmpty(onlineInvoiceDetails)) {
            OnlineInvoiceDetail onlineInvoiceDetail = onlineInvoiceDetails.get(0);
            String poUnitPriceChangeFlag = onlineInvoiceDetail.getPoUnitPriceChangeFlag();
            if (StringUtils.isBlank(poUnitPriceChangeFlag)) {
                throw new BaseException(LocaleHandler.getLocaleMsg("单据尚未调整金额,请先保存后,再提交!"));
            }
        }
    }


    private FSSCResult submitToFSSC(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        List<OnlineInvoiceDetail> onlineInvoiceDetails = onlineInvoiceSaveDTO.getOnlineInvoiceDetails();
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = onlineInvoiceSaveDTO.getOnlineInvoiceAdvances();
        List<OnlineInvoicePunish> onlineInvoicePunishes = onlineInvoiceSaveDTO.getOnlineInvoicePunishes();
        List<Fileupload> fileuploadList = new ArrayList<>();
        List<Fileupload> fileuploads = onlineInvoiceSaveDTO.getFileuploads();
        if (CollectionUtils.isNotEmpty(fileuploads)) {
            fileuploads.forEach(f -> {
                PageInfo<Fileupload> files = fileCenterClient.listPage(new Fileupload().setFileuploadId(f.getFileuploadId()), null);
                List<Fileupload> list = files.getList();
                Fileupload fileupload = list.get(0);
                fileuploadList.add(fileupload);
            });
        }
        List<BankInfo> bankInfos = supplierClient.getBankInfosByParam(new BankInfo()
                .setCompanyId(onlineInvoice.getVendorId())
                .setCeeaMainAccount(YesOrNo.YES.getValue())
                .setCeeaEnabled(YesOrNo.YES.getValue()));//ToDo 供应商银行信息可能有多条
        BankInfo bankInfo = new BankInfo();
        if (CollectionUtils.isNotEmpty(bankInfos)) {
            bankInfo = bankInfos.get(0);
        }
        PaymentApplyDto paymentApplyDto = new PaymentApplyDto();
        BoeHeader boeHeader = new BoeHeader();
        List<ZfsBoeLine> zfsBoeLines = new ArrayList<>();
        List<ZfsBoePayment> zfsBoePayments = new ArrayList<>();
        List<AttachmentDto> attachmentDtos = new ArrayList<>();
        List<BoeExpenseCavDto> boeExpenseCavDtos = new ArrayList<>();
        //获取是否为服务类标记
        String isService = onlineInvoice.getIsService();
        //获取是否为核销虚拟开票标记
        String virtualInvoice = onlineInvoice.getVirtualInvoice();
        String invoiceType = "";
        if (onlineInvoice.getTaxTotalAmount().compareTo(BigDecimal.ZERO) == 1) {
            invoiceType = "STANDARD";
        }
        if (onlineInvoice.getTaxTotalAmount().compareTo(BigDecimal.ZERO) == -1) {
            invoiceType = "CREDIT";
        }
        //虚拟发票时,默认传STANDARD
        if (YesOrNo.YES.getValue().equals(virtualInvoice)) {
            invoiceType = "STANDARD";
        }
        boeHeader.setBoeTypeCode(BoeTypeCode.PURCHASE_BOE_LGi.name())
                .setGroupCode("LGi")
                .setSourceSystem(DataSourceEnum.NSRM_SYS.getKey())
                .setSourceSystemNum(StringUtil.StringValue(onlineInvoice.getOnlineInvoiceNum()))
                .setBoeDate(StringUtil.StringValue(onlineInvoice.getInvoiceDate()))
                .setCreateByCode(StringUtil.StringValue(onlineInvoice.getApproverUsername()))//需要传入登录名
                .setEmployeeCode(StringUtil.StringValue(onlineInvoice.getApproverUsername()))//需要传入登录名
                .setApprovalDeptCode(StringUtil.StringValue(onlineInvoice.getApproverDeptid()))
                .setExpenseDeptCode(StringUtil.StringValue(onlineInvoice.getApproverDeptid()))
                .setOperationTypeCode(StringUtil.StringValue(onlineInvoice.getBusinessType()))
                .setPaperAccessories(StringUtil.StringValue(onlineInvoice.getIfPaperAttach()))
                .setPaymentCurrency(StringUtil.StringValue(onlineInvoice.getCurrencyCode()))
                .setRateValue(StringUtil.StringValue(onlineInvoice.getExchangeRate()));
        BigDecimal actualInvoiceAmountY = onlineInvoice.getActualInvoiceAmountY();
        actualInvoiceAmountY = actualInvoiceAmountY.setScale(2, BigDecimal.ROUND_HALF_UP);
        //项目编号,项目名称,任务编号,任务名称,取行上的值
        String projectCode = "";
        String projectName = "";
        String taskName = "";
        String taskNum = "";
        if (CollectionUtils.isNotEmpty(onlineInvoiceDetails)) {
            OnlineInvoiceDetail onlineInvoiceDetail = onlineInvoiceDetails.get(0);
            projectName = onlineInvoiceDetail.getProjectName();
            projectCode = onlineInvoiceDetail.getProjectNum();
            taskName = onlineInvoiceDetail.getTaskName();
            taskNum = onlineInvoiceDetail.getTaskNum();
        }
        boeHeader.setApplyAmount(StringUtil.StringValue(actualInvoiceAmountY))
                .setBoeAbstract(StringUtil.StringValue(onlineInvoice.getComment()))
                .setVendorCode(StringUtil.StringValue(onlineInvoice.getErpVendorCode()))
                .setVendorSiteID(StringUtil.StringValue(onlineInvoice.getCostTypeCode()))//关联供应商地点表
                .setContractCode(StringUtil.StringValue(onlineInvoice.getContractCode()))
                .setProjectCode(StringUtil.StringValue(projectCode))
                .setProjectName(StringUtil.StringValue(projectName))
                .setProjectTaskCode(StringUtil.StringValue(taskNum))
                .setProjectTaskName(StringUtil.StringValue(taskName))
                .setPaymentName(StringUtil.StringValue(onlineInvoice.getPayMethod()))//三单固定挂账
                .setPaymentCode(StringUtil.StringValue(onlineInvoice.getPayMethod()))//三单固定挂账
                .setPayConditionName(StringUtil.StringValue(onlineInvoice.getPayAccountPeriodName()))
                .setPayConditionCode(StringUtil.StringValue(onlineInvoice.getPayAccountPeriodCode()))
                .setAccountsPayableDueDate(StringUtil.StringValue(onlineInvoice.getAccountPayableDealine()))
                .setTaxInvoice(StringUtil.StringValue(onlineInvoice.getTaxInvoiceNum()));
        BigDecimal taxTotalAmount = onlineInvoice.getTaxTotalAmount();
        taxTotalAmount = taxTotalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        boeHeader.setPoAmount(StringUtil.StringValue(taxTotalAmount))
                .setBpCount(onlineInvoice.getBpcount())
                .setBudgetIgnore(onlineInvoice.getBudgetIgnore())//设置预算预警忽略标识
                .setInvoiceType(invoiceType)//ToDo 标准STANDARD：所有行的金额均大于0，按物料+接收号汇总计算的所有净开票数量均大于等于0；贷项通知单CREDIT：所有行的金额均小于0按物料+接收号汇总计算的所有净开票数量均小于等于0；
                .setIsService(isService)//是否服务类
                .setVirtualInvoice(virtualInvoice);//是否“是否核销预付的虚拟发票”。是传“Y”(预付款退回提交三单)，否传“N”(正向提交三单)
        //给费控转换开票明细
        if (CollectionUtils.isNotEmpty(onlineInvoiceDetails)) {
            convertOnlineInvoiceDetailToFssc(onlineInvoice, onlineInvoiceDetails, zfsBoeLines, isService);
        }
        if (CollectionUtils.isNotEmpty(onlineInvoiceAdvances)) {
            for (OnlineInvoiceAdvance onlineInvoiceAdvance : onlineInvoiceAdvances) {
                if (onlineInvoiceAdvance == null) continue;
                BoeExpenseCavDto boeExpenseCavDto = new BoeExpenseCavDto();
                boeExpenseCavDto.setSourceSystemNum(StringUtil.StringValue(onlineInvoiceAdvance.getAdvanceApplyNum()));
                boeExpenseCavDto.setCavAmount(StringUtil.StringValue(onlineInvoiceAdvance.getChargeOffAmount()));
                boeExpenseCavDtos.add(boeExpenseCavDto);
                if (CollectionUtils.isEmpty(onlineInvoiceDetails)) {
                    zfsBoeLines.add(new ZfsBoeLine()
                            .setExpenseAmount(StringUtil.StringValue(onlineInvoiceAdvance.getChargeOffAmount()))
                            .setLineType("ITEM"));
                }
            }
        }
        if (CollectionUtils.isNotEmpty(fileuploadList)) {
            for (Fileupload fileupload : fileuploadList) {
                if (fileupload == null) continue;
                AttachmentDto attachmentDto = new AttachmentDto();
                attachmentDto.setAttachFileName(StringUtil.StringValue(fileupload.getFileSourceName()));
                attachmentDto.setAttachFilePath(StringUtil.StringValue(fileupload.getFileuploadId()));
                attachmentDto.setAttachFileType(StringUtil.StringValue(fileupload.getFileExtendType()));
                attachmentDto.setAttachUploader(StringUtil.StringValue(fileupload.getCreatedBy()));
                attachmentDto.setAttachUploadTime(StringUtil.StringValue(DateUtil.format(fileupload.getCreationDate(), DateUtil.DATE_FORMAT_10)));
                attachmentDtos.add(attachmentDto);
            }
        }
        ZfsBoePayment zfsBoePayment = new ZfsBoePayment();
        zfsBoePayment.setPaymentCode(StringUtil.StringValue(onlineInvoice.getPayMethod()));
        zfsBoePayment.setPaymentName(StringUtil.StringValue(onlineInvoice.getPayMethod()));
        zfsBoePayment.setPaymentModeCode("01");//固定传:挂账 01 ToDo
        zfsBoePayment.setAccountName(StringUtil.StringValue(bankInfo.getBankAccountName()));
        zfsBoePayment.setBankBranchName(StringUtil.StringValue(bankInfo.getOpeningBank()));//开户银行名称对应费控的银行分行
        zfsBoePayment.setBankAccountNum(StringUtil.StringValue(bankInfo.getBankAccount()));
        zfsBoePayment.setBankUnitedCode(StringUtil.StringValue(bankInfo.getUnionCode()));
        zfsBoePayment.setPaymentCurrencyCode(StringUtil.StringValue(bankInfo.getCurrencyCode()));
        zfsBoePayment.setPaymentAmount(onlineInvoice.getUnPaidAmount());//支付币金额
        zfsBoePayment.setPaymentType("0");//ToDo
        zfsBoePayment.setVendorSitesID(StringUtil.StringValue(onlineInvoice.getCostTypeCode()));//供应商地点
        zfsBoePayments.add(zfsBoePayment);
        paymentApplyDto.setBoeHeader(boeHeader).setZfsBoePayments(zfsBoePayments).setAttachmentDtos(attachmentDtos).setZfsBoeLines(zfsBoeLines).setBoeExpenseCavDtos(boeExpenseCavDtos);
        System.out.println(JsonUtil.entityToJsonStr(paymentApplyDto));
        FSSCResult fsscResult = ifsscReqService.submitOnlineInvoice(paymentApplyDto);
        return fsscResult;
    }

    private void convertOnlineInvoiceDetailToFssc(OnlineInvoice onlineInvoice, List<OnlineInvoiceDetail> onlineInvoiceDetails, List<ZfsBoeLine> zfsBoeLines, String isService) {
        //非服务类开票
        if (YesOrNo.NO.getValue().equals(isService)) {
            noServiceOnlineInvoice(onlineInvoice, onlineInvoiceDetails, zfsBoeLines, isService);
        }
        //服务类开票
        if (YesOrNo.YES.getValue().equals(isService)) {
            serviceOnlineInvoice(onlineInvoice, onlineInvoiceDetails, zfsBoeLines, isService);
        }
    }


    private void serviceOnlineInvoice(OnlineInvoice onlineInvoice, List<OnlineInvoiceDetail> onlineInvoiceDetails, List<ZfsBoeLine> zfsBoeLines, String isService) {
        for (OnlineInvoiceDetail onlineInvoiceDetail : onlineInvoiceDetails) {
            if (onlineInvoiceDetail == null) continue;
            //item行
            ZfsBoeLine itemZfsBoeLine = new ZfsBoeLine();
            Order order = iOrderService.getOne(new QueryWrapper<Order>().eq("ORDER_NUMBER", onlineInvoiceDetail.getOrderNumber()));
            List<MaterialItem> materialItems = baseClient.listMaterialByParam(new MaterialItem().setMaterialCode(onlineInvoiceDetail.getItemCode()));
            MaterialItem materialItem = materialItems.get(0);
            OrderDetail orderDetail = iOrderDetailService.getOne(new QueryWrapper<>(new OrderDetail()
                    .setOrderId(order.getOrderId())
                    .setLineNum(onlineInvoiceDetail.getLineNum())));
            RequirementHead requirementHead = iRequirementHeadService.getRequirementHeadByParam(new RequirementHeadQueryDTO()
                    .setRequirementHeadNum(orderDetail.getCeeaRequirementHeadNum()));
            itemZfsBoeLine.setOperationSubTypeCode(requirementHead.getCeeaBusinessSmallCode());// 【是否服务类】为Y时，字段必填:   业务小类
            itemZfsBoeLine.setDeptCode(requirementHead.getCeeaDepartmentId());// 【是否服务类】为Y时，字段必填，控制预算使用
            itemZfsBoeLine.setDocumentNum(orderDetail.getCeeaRequirementHeadNum());// 【是否服务类】为Y时，字段必填，控制预算使用  (采购申请单号)
            itemZfsBoeLine.setLineType("ITEM");//ToDo 行类型为ITEM时：插入发票行上的不含税开票行金额字段（本次开票金额不含税字段）行类型为TAX时：插入发票头上的税额
            itemZfsBoeLine.setInvoiceLine(StringUtil.StringValue(onlineInvoiceDetail.getInvoiceRow()));
            //传费控金额,需要四舍五入,保留两位
            BigDecimal noTaxAmount = onlineInvoiceDetail.getNoTaxAmount() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getNoTaxAmount();
            noTaxAmount = noTaxAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
            itemZfsBoeLine.setExpenseAmount(StringUtil.StringValue(noTaxAmount));//ToDo 行类型为ITEM时：插入发票行上的不含税开票行金额字段（本次开票金额不含税字段）；行类型为TAX时：插入发票头上的税额
            itemZfsBoeLine.setPoUnitPrice(StringUtil.StringValue(onlineInvoiceDetail.getUnitPriceExcludingTax()));//订单单价(不含税)
            itemZfsBoeLine.setPoUnitPriceChangeFlag(onlineInvoiceDetail.getPoUnitPriceChangeFlag());
            itemZfsBoeLine.setAmount(StringUtil.StringValue(noTaxAmount));//发票不含税金额
            itemZfsBoeLine.setQuantityInvoiced(StringUtil.StringValue(onlineInvoiceDetail.getInvoiceQuantity()));//开票数量
            //直接获取对应调整标记,对应调整标记已在调整方法处理
//            if (onlineInvoiceDetail.getCompareResult().equals(StringUtil.StringValue(BigDecimal.ZERO))) {
//                itemZfsBoeLine.setPoUnitPriceChangeFlag(YesOrNo.NO.getValue());
//            } else {
//                itemZfsBoeLine.setPoUnitPriceChangeFlag(YesOrNo.YES.getValue());
//            }
            zfsBoeLines.add(itemZfsBoeLine);
            //tax行
            ZfsBoeLine taxZfsBoeLine = new ZfsBoeLine();
            taxZfsBoeLine.setLineType("TAX");
            taxZfsBoeLine.setItRelation(StringUtil.StringValue(onlineInvoiceDetail.getInvoiceRow()));
            taxZfsBoeLine.setOperationSubTypeCode(requirementHead.getCeeaBusinessSmallCode());// 【是否服务类】为Y时，字段必填:   业务小类
            taxZfsBoeLine.setDeptCode(requirementHead.getCeeaDepartmentId());// 【是否服务类】为Y时，字段必填，控制预算使用
            taxZfsBoeLine.setDocumentNum(orderDetail.getCeeaRequirementHeadNum());// 【是否服务类】为Y时，字段必填，控制预算使用  (采购申请单号)
            taxZfsBoeLine.setExpenseAmount(StringUtil.StringValue(onlineInvoiceDetail.getTax()));
            taxZfsBoeLine.setTaxCode(StringUtil.StringValue(onlineInvoiceDetail.getTaxKey()));
            taxZfsBoeLine.setTaxRate(StringUtil.StringValue(onlineInvoiceDetail.getTaxRate()));
            taxZfsBoeLine.setRateCode(StringUtil.StringValue(onlineInvoiceDetail.getTaxKey()));
            taxZfsBoeLine.setPoUnitPriceChangeFlag(onlineInvoiceDetail.getPoUnitPriceChangeFlag());
            BigDecimal tax = onlineInvoiceDetail.getTax();
            tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
            taxZfsBoeLine.setAmount(StringUtil.StringValue(tax));//发票税额
            taxZfsBoeLine.setQuantityInvoiced(StringUtil.StringValue(onlineInvoiceDetail.getInvoiceQuantity()));//开票数量
            //直接获取对应调整标记,对应调整标记已在调整方法处理
//            if (onlineInvoiceDetail.getCompareResult().equals(StringUtil.StringValue(BigDecimal.ZERO))) {
//                taxZfsBoeLine.setPoUnitPriceChangeFlag(YesOrNo.NO.getValue());
//            } else {
//                taxZfsBoeLine.setPoUnitPriceChangeFlag(YesOrNo.YES.getValue());
//            }
            zfsBoeLines.add(taxZfsBoeLine);
        }
    }

    private void noServiceOnlineInvoice(OnlineInvoice onlineInvoice, List<OnlineInvoiceDetail> onlineInvoiceDetails, List<ZfsBoeLine> zfsBoeLines, String isService) {
        List<OnlineInvoiceDetail> returnOnlineInvoiceDetails = onlineInvoiceDetails.stream().filter(item -> CeeaWarehousingReturnDetailEnum.RETURN.getValue().equals(item.getType())).map(e->BeanCopyUtil.copyProperties(e,OnlineInvoiceDetail::new)).collect(Collectors.toList());//退货开票明细
        List<OnlineInvoiceDetail> receiveOnlineInvoiceDetails = onlineInvoiceDetails.stream().filter(item -> CeeaWarehousingReturnDetailEnum.RECEIVE.getValue().equals(item.getType())).map(e->BeanCopyUtil.copyProperties(e,OnlineInvoiceDetail::new)).collect(Collectors.toList());//入货开票明细
        List<OnlineInvoiceDetail> fsscOnlineInvoiceDetails = new ArrayList<>();//用于传输给费控
        //有receive,有return
        if (CollectionUtils.isNotEmpty(returnOnlineInvoiceDetails) && CollectionUtils.isNotEmpty(receiveOnlineInvoiceDetails)) {
            for (OnlineInvoiceDetail returnOnlineInvoiceDetail : returnOnlineInvoiceDetails) {
                if (returnOnlineInvoiceDetail == null) continue;
                for (OnlineInvoiceDetail receiveOnlineInvoiceDetail : receiveOnlineInvoiceDetails) {
                    if (receiveOnlineInvoiceDetail == null) continue;
                    if (returnOnlineInvoiceDetail.getParentTxnId().compareTo(receiveOnlineInvoiceDetail.getTxnId()) == 0) {
                        BigDecimal receiveNum = returnOnlineInvoiceDetail.getReceiveNum();//退货接收数量
                        BigDecimal invoiceQuantity = returnOnlineInvoiceDetail.getInvoiceQuantity();//退货本次开票数量
                        BigDecimal notInvoiceQuantity = returnOnlineInvoiceDetail.getNotInvoiceQuantity();//退货未开票数量
                        BigDecimal noTaxAmount = returnOnlineInvoiceDetail.getNoTaxAmount();//退货净价金额
                        BigDecimal tax = returnOnlineInvoiceDetail.getTax();//退货税额
                        BigDecimal taxAmount = returnOnlineInvoiceDetail.getTaxAmount();//退货含税金额
                        BigDecimal receiveNum1 = receiveOnlineInvoiceDetail.getReceiveNum();//入库接收数量
                        BigDecimal invoiceQuantity1 = receiveOnlineInvoiceDetail.getInvoiceQuantity();//入库本次开票数量
                        BigDecimal notInvoiceQuantity1 = receiveOnlineInvoiceDetail.getNotInvoiceQuantity();//入库未开票数量
                        BigDecimal noTaxAmount1 = receiveOnlineInvoiceDetail.getNoTaxAmount();//入库净价金额
                        BigDecimal tax1 = receiveOnlineInvoiceDetail.getTax();//入库税额
                        BigDecimal taxAmount1 = receiveOnlineInvoiceDetail.getTaxAmount();//入库含税金额
                        //不知道哪个字段报空指针，全部赋予默认值
                        if(receiveNum == null){
                            receiveNum = BigDecimal.ZERO;
                        }
                        if(invoiceQuantity == null){
                            invoiceQuantity = BigDecimal.ZERO;
                        }
                        if(notInvoiceQuantity == null){
                            notInvoiceQuantity = BigDecimal.ZERO;
                        }
                        if(noTaxAmount == null){
                            noTaxAmount = BigDecimal.ZERO;
                        }
                        if(tax == null){
                            tax = BigDecimal.ZERO;
                        }
                        if(taxAmount == null){
                            taxAmount = BigDecimal.ZERO;
                        }
                        if(receiveNum1 == null){
                            receiveNum1 = BigDecimal.ZERO;
                        }
                        if(invoiceQuantity1 == null){
                            invoiceQuantity1 = BigDecimal.ZERO;
                        }
                        if(notInvoiceQuantity1 == null){
                            notInvoiceQuantity1 = BigDecimal.ZERO;
                        }
                        if(noTaxAmount1 == null){
                            noTaxAmount1 = BigDecimal.ZERO;
                        }
                        if(tax1 == null){
                            tax1 = BigDecimal.ZERO;
                        }
                        if(taxAmount1 == null){
                            taxAmount1 = BigDecimal.ZERO;
                        }
                        BigDecimal newReceiveNum = receiveNum.add(receiveNum1);
                        BigDecimal newInvoiceQuantity = invoiceQuantity.add(invoiceQuantity1);
                        BigDecimal newNotInvoiceQuantity = notInvoiceQuantity.add(notInvoiceQuantity1);
                        BigDecimal unitPriceContainingTax = receiveOnlineInvoiceDetail.getUnitPriceContainingTax();//含税单价
                        BigDecimal unitPriceExcludingTax = receiveOnlineInvoiceDetail.getUnitPriceExcludingTax();//不含税单价
                        BigDecimal newNoTaxAmount = newInvoiceQuantity.multiply(unitPriceExcludingTax).setScale(2, BigDecimal.ROUND_HALF_UP);//合并后的不含税金额
                        BigDecimal newTaxAmount = newInvoiceQuantity.multiply(unitPriceContainingTax).setScale(2, BigDecimal.ROUND_HALF_UP);//合并后的含税金额
                        BigDecimal newTax = newTaxAmount.subtract(newNoTaxAmount).setScale(2, BigDecimal.ROUND_HALF_UP);//合并后的税额
                        receiveOnlineInvoiceDetail
                                .setReceiveNum(newReceiveNum)
                                .setInvoiceQuantity(newInvoiceQuantity)
                                .setNotInvoiceQuantity(newNotInvoiceQuantity)
                                .setNoTaxAmount(newNoTaxAmount)
                                .setTax(newTax)
                                .setTaxAmount(newTaxAmount);
                        break;
                    }
                }
            }
            fsscOnlineInvoiceDetails = receiveOnlineInvoiceDetails
                    .stream()
                    .filter(onlineInvoiceDetail -> (!(onlineInvoiceDetail.getInvoiceQuantity().compareTo(BigDecimal.ZERO) == 0))).collect(Collectors.toList());
        }

        //有return,无receive
        if (CollectionUtils.isNotEmpty(returnOnlineInvoiceDetails) && CollectionUtils.isEmpty(receiveOnlineInvoiceDetails)) {
            returnOnlineInvoiceDetails.forEach(item -> {item.setTxnId(item.getParentTxnId());});//纯return的情况需要将parentTxnId传给费控
            fsscOnlineInvoiceDetails = returnOnlineInvoiceDetails;
        }

        //有receive,无return
        if (CollectionUtils.isNotEmpty(receiveOnlineInvoiceDetails) && CollectionUtils.isEmpty(returnOnlineInvoiceDetails)) {
            fsscOnlineInvoiceDetails = receiveOnlineInvoiceDetails;
        }

//        Map<String , Order> orderMap = supcooperateClient.batchGetOrderIdsByOrderNumbers(
//                fsscOnlineInvoiceDetails.stream().map(OnlineInvoiceDetail::getOrderNumber).collect(Collectors.toList())
//        ).stream().collect(
//                Collectors.toMap(order -> order.getOrderNumber() , order -> order , (o, o2) -> o)
//        );
        List<Order> orders = iOrderService.listOrderByOrderNumberOrErpNumber(fsscOnlineInvoiceDetails.stream().map(OnlineInvoiceDetail::getOrderNumber).collect(Collectors.toList()));
        Map<String, Order> orderMap = orders.stream().collect(Collectors.toMap(Order::getOrderNumber, Function.identity(), (o, o1) -> o));
        Map<String , List<MaterialItem>> materialMap = baseClient.listMaterialByCodeBatch(
                fsscOnlineInvoiceDetails.stream().map(OnlineInvoiceDetail::getItemCode).collect(Collectors.toList())
        ).stream().collect(Collectors.groupingBy(k -> k.getMaterialCode()));

        for (OnlineInvoiceDetail onlineInvoiceDetail : fsscOnlineInvoiceDetails) {
            if (onlineInvoiceDetail == null) continue;
            ZfsBoeLine zfsBoeLine = new ZfsBoeLine();
            //如果为服务类,则以下字段必填   (需求修改,备用)
            //TODO 2020-11-19 清洗网上开票；查询订单的信息，这里要改成批量
            Order order  = orderMap.get(onlineInvoiceDetail.getOrderNumber());
            List<MaterialItem> materialItems = materialMap.get(onlineInvoiceDetail.getItemCode());
//            Order order = supcooperateClient.getOrderByOrderNumber(onlineInvoiceDetail.getOrderNumber());
//            List<MaterialItem> materialItems = baseClient.listMaterialByParam(new MaterialItem().setMaterialCode(onlineInvoiceDetail.getItemCode()));
            MaterialItem materialItem = materialItems.get(0);
//            if (YesOrNo.YES.getValue().equals(isService)) {
//                OrderDetail orderDetail = supcooperateClient.getOrderDetail(new OrderDetail().setOrderId(order.getOrderId()).setLineNum(onlineInvoiceDetail.getLineNum()));
//                RequirementHead requirementHead = iRequirementHeadService.getRequirementHeadByParam(new RequirementHeadQueryDTO().setRequirementHeadNum(orderDetail.getCeeaRequirementHeadNum()));
//                zfsBoeLine.setOperationSubTypeCode(requirementHead.getCeeaBusinessSmallCode());// 【是否服务类】为Y时，字段必填:   业务小类
//                zfsBoeLine.setDeptCode(onlineInvoice.getApproverDeptid());// 【是否服务类】为Y时，字段必填，控制预算使用requirementHead.getCeeaDepartmentId()
//                zfsBoeLine.setDocumentNum(orderDetail.getCeeaRequirementHeadNum());// 【是否服务类】为Y时，字段必填，控制预算使用  (采购申请单号)
//            }
            //如果不为服务类,则以下字段,有则传
//            if (YesOrNo.NO.getValue().equals(isService)) {
//            }
            zfsBoeLine.setPoUnitPriceChangeFlag(onlineInvoiceDetail.getPoUnitPriceChangeFlag());//ToDo 行类型为项时：判断行金额是否调整，调整则为Y，否则为N 行类型为税时：判断SRM发票头上发票税额相比税额（系统）有误调整，有则为Y，无则为N；【是否服务类】或【是否虚拟发票】为Y时，该字段非必填
            BigDecimal poPrice = onlineInvoiceDetail.getOrderUnitPriceTaxN().multiply(onlineInvoiceDetail.getInvoiceQuantity());//行类型为项时：插入SRM发票行上订单价格（订单单价*本次开票数量）行类型为税时：插入SRM发票头上税额（系统）【是否服务类】或【是否虚拟发票】为Y时，该字段非必填
            poPrice = poPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
            zfsBoeLine.setPoPrice(StringUtil.StringValue(poPrice));//行订单金额?
            zfsBoeLine.setPoNum(StringUtil.StringValue(onlineInvoiceDetail.getOrderNumber()));
            zfsBoeLine.setPoLineNum(StringUtil.StringValue(onlineInvoiceDetail.getLineNum()));
            zfsBoeLine.setReciptNum(StringUtil.StringValue(onlineInvoiceDetail.getReceiveOrderNo()));
            zfsBoeLine.setReciptLineNum(StringUtil.StringValue(onlineInvoiceDetail.getTxnId()));//事务处理ID
            zfsBoeLine.setQuantityInvoiced(StringUtil.StringValue(onlineInvoiceDetail.getInvoiceQuantity()));
            //单位转换
            String nsrmUnit = StringUtil.StringValue(onlineInvoiceDetail.getUnit());
            String outSystemUnit = OutSystemConvertUnit.automatch(nsrmUnit);
            if (StringUtils.isBlank(outSystemUnit)) {
                outSystemUnit = nsrmUnit;
            }
            zfsBoeLine.setUnitOfMeas(outSystemUnit);
            zfsBoeLine.setUnitPrice(StringUtil.StringValue(onlineInvoiceDetail.getUnitPriceExcludingTax()));//开票单价(不含税)
//            zfsBoeLine.setPoUnitPrice(StringUtil.StringValue(onlineInvoiceDetail.getUnitPriceExcludingTax()));
            zfsBoeLine.setBuyerName(order.getCeeaEmpUsername());//
            zfsBoeLine.setBuyerNum(StringUtil.StringValue(order.getCeeaEmpNo()));//
            zfsBoeLine.setInventoryItemID(StringUtil.StringValue(materialItem.getCeeaErpMaterialId()));//获取ERP物料ID
            zfsBoeLine.setMaterialDescription(StringUtil.StringValue(onlineInvoiceDetail.getItemName()));
            zfsBoeLine.setDescription(StringUtil.StringValue(onlineInvoiceDetail.getComment()));
            zfsBoeLine.setProductType(StringUtil.StringValue("GOODS"));
            zfsBoeLine.setLineType("ITEM");// 行类型为ITEM时：插入发票行上的不含税开票行金额字段（本次开票金额不含税字段）行类型为TAX时：插入发票头上的税额
            zfsBoeLine.setInvoiceLine(StringUtil.StringValue(onlineInvoiceDetail.getInvoiceRow()));
            //传费控金额,需要四舍五入,保留两位
            BigDecimal noTaxAmount = onlineInvoiceDetail.getNoTaxAmount() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getNoTaxAmount();
            noTaxAmount = noTaxAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
            zfsBoeLine.setExpenseAmount(StringUtil.StringValue(noTaxAmount));// 行类型为ITEM时：插入发票行上的不含税开票行金额字段（本次开票金额不含税字段）；行类型为TAX时：插入发票头上的税额
            zfsBoeLine.setAmount(StringUtil.StringValue(noTaxAmount));//本次开票金额不含税
            zfsBoeLine.setPoUnitPrice(StringUtil.StringValue(onlineInvoiceDetail.getOrderUnitPriceTaxN()));//订单单价(不含税)ToDo 获取的是订单单价
            zfsBoeLine.setPoShipmentNum(StringUtil.StringValue(onlineInvoiceDetail.getShipLineNum()));//PO发运编号
//            zfsBoeLine.setContract(StringUtil.StringValue(onlineInvoice.getContractCode()));//合同编码
//            zfsBoeLine.setRateCode(StringUtil.StringValue(onlineInvoiceDetail.getTaxKey()));//ERP统一税码
//            zfsBoeLine.setTaxCode(StringUtil.StringValue(onlineInvoiceDetail.getTaxKey()));
//            zfsBoeLine.setTaxRate(StringUtil.StringValue(onlineInvoiceDetail.getTaxRate()));
            zfsBoeLines.add(zfsBoeLine);
        }
        //设置TAX行
        setTaxLine(onlineInvoice, onlineInvoiceDetails, zfsBoeLines, isService);
    }

    private void setTaxLine(OnlineInvoice onlineInvoice, List<OnlineInvoiceDetail> onlineInvoiceDetails, List<ZfsBoeLine> zfsBoeLines, String isService) {
        ZfsBoeLine zfsBoeLine = new ZfsBoeLine();
        zfsBoeLine.setLineType("TAX");
        OnlineInvoiceDetail onlineInvoiceDetail = onlineInvoiceDetails.get(0);
        zfsBoeLine.setPoUnitPriceChangeFlag(onlineInvoiceDetail.getPoUnitPriceChangeFlag());
        //直接获取对应调整标记,对应调整标记已在调整方法处理
//        if (onlineInvoiceDetail.getCompareResult().equals(StringUtil.StringValue(BigDecimal.ZERO))) {
//            zfsBoeLine.setPoUnitPriceChangeFlag(YesOrNo.NO.getValue());
//        } else {
//            zfsBoeLine.setPoUnitPriceChangeFlag(YesOrNo.YES.getValue());
//        }
        BigDecimal invoiceTax = onlineInvoice.getInvoiceTax();
        invoiceTax = invoiceTax.setScale(2, BigDecimal.ROUND_HALF_UP);
        zfsBoeLine.setExpenseAmount(StringUtil.StringValue(invoiceTax));
        zfsBoeLine.setAmount(StringUtil.StringValue(invoiceTax));//发票汇总税额
        zfsBoeLine.setPoPrice(StringUtil.StringValue(invoiceTax));//发票汇总税额
        zfsBoeLine.setRateCode(StringUtil.StringValue(onlineInvoiceDetail.getTaxKey()));//ERP统一税码
        zfsBoeLine.setTaxCode(StringUtil.StringValue(onlineInvoiceDetail.getTaxKey()));
        zfsBoeLine.setTaxRate(StringUtil.StringValue(onlineInvoiceDetail.getTaxRate()));
//        if (YesOrNo.NO.getValue().equals(isService)) {
//            BigDecimal totalTax = onlineInvoice.getTotalTax();
//            totalTax = totalTax.setScale(2, BigDecimal.ROUND_HALF_UP);
//            zfsBoeLine.setPoPrice(StringUtil.StringValue(totalTax));
//        }
        zfsBoeLines.add(zfsBoeLine);
    }

    @Override
    @Transactional
    public FSSCResult audit(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        //获取当前单据,及校验
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = onlineInvoiceSaveDTO.getOnlineInvoiceAdvances();
        //审核前需校验
        checkBeforeAudit(onlineInvoice);
        //提交给费控
        FSSCResult fsscResult = new FSSCResult();
//        fsscResult = submitToFSSC(onlineInvoiceSaveDTO);
//        if (FSSCResponseCode.ERROR.getCode().equals(fsscResult.getCode()) || StringUtils.isBlank(fsscResult.getCode())) {
//            if(StringUtils.isNotBlank(fsscResult.getMsg()) && fsscResult.getMsg().indexOf("已在审批中，在共享单据是") > 0){
//                log.info("----已提交到费控系统-----");
//                log.info(fsscResult.getMsg());
//            }else{
//                throw new BaseException(LocaleHandler.getLocaleMsg(fsscResult.getMsg()));
//            }
//        }
//        //如果是WARNING的话,需要手动回滚事务,且返回对应结果给前端进行页面渲染
//        if(FSSCResponseCode.WARN.getCode().equals(fsscResult.getCode())){
//            //手动回滚事务
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return fsscResult;
//        }
//        onlineInvoice.setBoeNo(fsscResult.getBoeNo()).setPrintUrl(fsscResult.getPrintUrl());
        //审核通过时,释放预付引用标记,且扣减可用金额
        releaseAndReduce(onlineInvoiceAdvances);
        //回写费控单号和回写打印URL
        scIOnlineInvoiceService.audit(onlineInvoiceSaveDTO, InvoiceStatus.APPROVAL.name());
        return fsscResult;
    }

    private void checkBeforeAudit(OnlineInvoice onlineInvoice) {
        LocalDate accountPayableDealine = onlineInvoice.getAccountPayableDealine();
        Assert.notNull(accountPayableDealine, LocaleHandler.getLocaleMsg("请先保存,生成必要数据后,再操作审核通过"));
    }

    @Override
    @Transactional
    public void abandon(Long onlineInvoiceId) {
        //获取当前单据,及校验
        OnlineInvoiceSaveDTO onlineInvoiceSaveDTO = scIOnlineInvoiceService.get(onlineInvoiceId);
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = onlineInvoiceSaveDTO.getOnlineInvoiceAdvances();
        Assert.notNull(onlineInvoice, LocaleHandler.getLocaleMsg("该单据不存在,请检查!"));
        if (!InvoiceStatus.DRAFT.name().equals(onlineInvoice.getInvoiceStatus()) && !InvoiceStatus.REJECTED.name().equals(onlineInvoice.getInvoiceStatus())) {
            throw new BaseException("单据状态不为新建或驳回的,不可作废!");
        }
        //设置预付款是否引用标记
        addAdvance(onlineInvoiceAdvances);
        //调用费控作废接口(存在费控回写单号,才需调用费控作废接口)
        FSSCResult fsscResult = new FSSCResult();
        if (StringUtils.isNotBlank(onlineInvoice.getBoeNo())) {
            fsscResult = ifsscReqService.abandon(new FsscStatus()
                    .setFsscNo(onlineInvoice.getBoeNo())
                    .setPeripheralSystemNum(onlineInvoice.getOnlineInvoiceNum())
                    .setSourceSysteCode(DataSourceEnum.NSRM_SYS.getKey()));
        }
        if ("500".equals(fsscResult.getCode()) ||  (StringUtils.isNotBlank(fsscResult.getMsg()) && !"success".equals(fsscResult.getMsg()))) {
            throw new BaseException(LocaleHandler.getLocaleMsg(fsscResult.getMsg()));
        }
        //改变单据状态为已作废
        scIOnlineInvoiceService.buyerAbandon(onlineInvoiceId);
    }

    @Override
    @Transactional
    public BaseResult saveTemporaryOnlineInvoice(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = onlineInvoiceSaveDTO.getOnlineInvoiceAdvances();
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        List<OnlineInvoiceDetail> onlineInvoiceDetails = onlineInvoiceSaveDTO.getOnlineInvoiceDetails();
        List<OnlineInvoicePunish> onlineInvoicePunishes = onlineInvoiceSaveDTO.getOnlineInvoicePunishes();
        //保存前正常校验
        checkBeforeSaveTemporaryOnlineInvoice(onlineInvoice, onlineInvoiceDetails);

        //检查未开票数量
        if (onlineInvoice.getOnlineInvoiceId() == null) {
            checkNotInvoiceQuantity(onlineInvoice, onlineInvoiceDetails);
        }
        //设置预付款引用标记
        occupyAdvance(onlineInvoiceAdvances);

        //保存 modify chensl26 2021-2-24
        BaseResult result = scIOnlineInvoiceService.saveTemporary(onlineInvoiceSaveDTO, InvoiceStatus.DRAFT.name());
        if (ResultCode.UNKNOWN_ERROR.getCode().equals(result.getCode())) {
            throw new BaseException(result.getMessage());
        }

        //虚拟发票需给预付款回写虚拟发票号和ID
        if (YesOrNo.YES.getValue().equals(onlineInvoice.getVirtualInvoice())) {
            Optional.ofNullable(result.getData()).ifPresent(id -> {
                OnlineInvoiceSaveDTO dto = scIOnlineInvoiceService.get((Long)id);
                OnlineInvoice invoice = dto.getOnlineInvoice();
                List<OnlineInvoiceAdvance> invoiceAdvances = dto.getOnlineInvoiceAdvances();
                List<AdvanceApplyHead> advanceApplyHeads = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(invoiceAdvances)) {
                    for (OnlineInvoiceAdvance invoiceAdvance : invoiceAdvances) {
                        AdvanceApplyHead advanceApplyHead = new AdvanceApplyHead();
                        advanceApplyHead.setAdvanceApplyHeadId(invoiceAdvance.getAdvanceApplyHeadId())
                                .setOnlineInvoiceId(invoice.getOnlineInvoiceId())
                                .setOnlineInvoiceNum(invoice.getOnlineInvoiceNum());
                        advanceApplyHeads.add(advanceApplyHead);
                    }
                }
                iAdvanceApplyHeadService.updateBatchById(advanceApplyHeads);
            });
        }
        return result;
    }

    private void checkBeforeSaveTemporaryOnlineInvoice(OnlineInvoice onlineInvoice, List<OnlineInvoiceDetail> onlineInvoiceDetails) {
        if(Objects.isNull(onlineInvoice.getActualInvoiceAmountY()) && !YesOrNo.YES.getValue().equals(onlineInvoice.getVirtualInvoice())){
            throw new BaseException(LocaleHandler.getLocaleMsg("实际发票金额(含税)数额为空,请检查!"));
        }
        if(Objects.isNull(onlineInvoice.getTaxTotalAmount()) && !YesOrNo.YES.getValue().equals(onlineInvoice.getVirtualInvoice())){
            throw new BaseException(LocaleHandler.getLocaleMsg("含税金额为空,请检查!"));
        }
        //校验行上的税率为0时,头上的税额只能输入0
        if (CollectionUtils.isEmpty(onlineInvoiceDetails) && !YesOrNo.YES.getValue().equals(onlineInvoice.getVirtualInvoice())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("未勾选开票明细行,请检查!"));
        }
        if (!YesOrNo.YES.getValue().equals(onlineInvoice.getVirtualInvoice())) {
            OnlineInvoiceDetail onlineInvoiceDetail = onlineInvoiceDetails.get(0);
            BigDecimal taxRate = onlineInvoiceDetail.getTaxRate();
            BigDecimal invoiceTax = onlineInvoice.getInvoiceTax();
            if (BigDecimal.ZERO.compareTo(taxRate) == 0 && invoiceTax.compareTo(BigDecimal.ZERO) != 0) {
                throw new BaseException(LocaleHandler.getLocaleMsg("当前开票明细行为0税率,发票税额只能为0,请检查!"));
            }
        }
    }

    private synchronized void occupyAdvance(List<OnlineInvoiceAdvance> onlineInvoiceAdvances) {
        if (CollectionUtils.isNotEmpty(onlineInvoiceAdvances)) {
            for (OnlineInvoiceAdvance onlineInvoiceAdvance : onlineInvoiceAdvances) {
                if (onlineInvoiceAdvance == null) continue;
                //检查预付款状态及数量
                AdvanceApplyHead byId = iAdvanceApplyHeadService.getById(onlineInvoiceAdvance.getAdvanceApplyHeadId());
                Assert.notNull(byId, LocaleHandler.getLocaleMsg("核销预付款时,系统找不到对应预付款,请联系管理员!"));
                if (byId != null) {
                    BigDecimal usableAmount = byId.getUsableAmount() == null ? BigDecimal.ZERO : byId.getUsableAmount();
                    if (usableAmount.compareTo(BigDecimal.ZERO) < 1) {
                        throw new BaseException(LocaleHandler.getLocaleMsg("预付款可用金额不足,请检查!"));
                    }
                    iAdvanceApplyHeadService.setQuote(onlineInvoiceAdvance
                            .setUsableAmount(byId.getUsableAmount())
                            .setAdvanceApplyHeadId(byId.getAdvanceApplyHeadId()), YesOrNo.YES.getValue());
                }
            }
        }
    }

    private void checkNotInvoiceQuantity(OnlineInvoice onlineInvoice, List<OnlineInvoiceDetail> onlineInvoiceDetails) {
        if (CollectionUtils.isNotEmpty(onlineInvoiceDetails)) {
            for (OnlineInvoiceDetail onlineInvoiceDetail : onlineInvoiceDetails) {
                if (onlineInvoiceDetail == null) continue;
                if (YesOrNo.YES.getValue().equals(onlineInvoice.getIsService())) {//服务类回写采购订单未开票数量
                    OrderDetail byId = iOrderDetailService.getById(onlineInvoiceDetail.getOrderDetailId());
                    if (byId != null) {
                        BigDecimal result = byId.getNotInvoiceQuantity() == null ? BigDecimal.ZERO : byId.getNotInvoiceQuantity();
                        int r = result.compareTo(BigDecimal.ZERO);
                        if (r == 0) {
                            throw new BaseException(LocaleHandler.getLocaleMsg("未开票数量不足,请检查!"));
                        }
                    }
                }
                if (YesOrNo.NO.getValue().equals(onlineInvoice.getIsService())) {//非服务类回写开票通知未开票数量
                    InvoiceDetail byId = scIInvoiceDetailService.getById(onlineInvoiceDetail.getInvoiceDetailId());
                    if (byId != null) {
                        BigDecimal result = byId.getNotInvoiceQuantity() == null ? BigDecimal.ZERO : byId.getNotInvoiceQuantity();
                        int r = result.compareTo(BigDecimal.ZERO);
                        if (r == 0) {
                            throw new BaseException(LocaleHandler.getLocaleMsg("未开票数量不足,请检查!"));
                        }
                    }
                }
            }
        }
    }

    @Override
    public OnlineInvoiceSaveDTO createOnlineInvoice(List<InvoiceNoticeDTO> invoiceNoticeDTOS) {
        checkParam(invoiceNoticeDTOS);
        return scIOnlineInvoiceService.createOnlineInvoice(invoiceNoticeDTOS);
    }

    @Override
    public BaseResult saveTemporaryBeforeAudit(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = onlineInvoiceSaveDTO.getOnlineInvoiceAdvances();
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        List<OnlineInvoiceDetail> onlineInvoiceDetails = onlineInvoiceSaveDTO.getOnlineInvoiceDetails();
        //全return的情况,低开发票,不能调整
//        List<OnlineInvoiceDetail> collect = onlineInvoiceDetails.stream().filter(onlineInvoiceDetail -> (CeeaWarehousingReturnDetailEnum.RECEIVE.getValue().equals(onlineInvoiceDetail.getType()))).collect(Collectors.toList());
//        if (CollectionUtils.isEmpty(collect) && onlineInvoice.getActualInvoiceAmountY().compareTo(onlineInvoice.getTaxTotalAmount()) != 0) {
//            throw new BaseException(LocaleHandler.getLocaleMsg("退货开票明细行不能调整,请检查!"));
//        }
        //设置预付款引用标记
        occupyAdvance(onlineInvoiceAdvances);
        BaseResult baseResult = scIOnlineInvoiceService.saveTemporaryBeforeAudit(onlineInvoiceSaveDTO);
        if (ResultCode.UNKNOWN_ERROR.getCode().equals(baseResult.getCode())){
            throw new BaseException(baseResult.getMessage());
        }
        return baseResult;
    }

    @Override
    @Transactional
    public void batchDelete(List<Long> onlineInvoiceAdvanceIds) {
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = scIOnlineInvoiceAdvanceService.listByIds(onlineInvoiceAdvanceIds);
        //释放预付款引用标记
        addAdvance(onlineInvoiceAdvances);
        scIOnlineInvoiceAdvanceService.removeByIds(onlineInvoiceAdvanceIds);
    }

    @Override
    @Transactional
    public FSSCResult batchSubmit(List<Long> onlineInvoicIds) {
        OnlineInvoiceQueryDTO onlineInvoiceQueryDTO = scIOnlineInvoiceService.queryBatchOnlineInvoiceSaveDTO(onlineInvoicIds);
        Map<Long, List<OnlineInvoiceDetail>> mapOnlineInvoiceDetail = onlineInvoiceQueryDTO.getMapOnlineInvoiceDetail();
        List<OnlineInvoice> onlineInvoices = onlineInvoiceQueryDTO.getOnlineInvoices();
        Map<Long, List<OnlineInvoiceAdvance>> mapOnlineInvoiceAdvance = onlineInvoiceQueryDTO.getMapOnlineInvoiceAdvance();
        Map<Long, List<Fileupload>> mapFileuploads = onlineInvoiceQueryDTO.getMapFileuploads();
        FSSCResult fsscResult = new FSSCResult();
        StringBuilder failNo = new StringBuilder();
        StringBuilder successNo = new StringBuilder();
        for (OnlineInvoice onlineInvoice : onlineInvoices) {
            boolean success = true;
            try {
                log.info("开始处理票号{}---票号内容:{}" , onlineInvoice.getOnlineInvoiceNum() , JSONObject.toJSONString(onlineInvoice));
                OnlineInvoiceSaveDTO onlineInvoiceSaveDTO = new OnlineInvoiceSaveDTO();
                onlineInvoiceSaveDTO.setOnlineInvoice(onlineInvoice);
                onlineInvoiceSaveDTO.setOnlineInvoiceDetails(mapOnlineInvoiceDetail.get(onlineInvoice.getOnlineInvoiceId()));
                onlineInvoiceSaveDTO.setOnlineInvoiceAdvances(mapOnlineInvoiceAdvance.get(onlineInvoice.getOnlineInvoiceId()));
                onlineInvoiceSaveDTO.setFileuploads(mapFileuploads.get(onlineInvoice.getOnlineInvoiceId()));
                fsscResult = SpringContextHolder.getBean(this.getClass()).submitOnlineInvoice(onlineInvoiceSaveDTO);
                log.info(onlineInvoice.getOnlineInvoiceNum() + "开票已传费控----------------------------");
            }catch (Exception e){
                log.error("清洗数据-网上发票提交报错",e);
                failNo.append(onlineInvoice.getOnlineInvoiceId()).append(",");
                success = false;
                log.error("当前网上发票提交失败:num={},需要重新操作,id={}", onlineInvoice.getOnlineInvoiceNum() , onlineInvoice.getOnlineInvoiceId());
            }
            if(success){
                successNo.append(onlineInvoice.getOnlineInvoiceId()).append(",");
                log.info("当前网上发票提交失败:num={},id={}" , onlineInvoice.getOnlineInvoiceNum() , onlineInvoice.getOnlineInvoiceId());
            }
        }
        fsscResult.setMsg("成功："+successNo.toString() +";失败："+failNo.toString());
        return fsscResult;
    }

    @Override
    @Transactional
    public void onlineInvoiceWithdraw(Long onlineInvoiceId) {
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = scIOnlineInvoiceAdvanceService.listOnlineInvoiceAdvanceByOnlineInvoiceId(onlineInvoiceId);
        checkBeforeOnlineInvoiceWithdraw(onlineInvoiceAdvances);
//        addAdvance(onlineInvoiceAdvances);
        scIOnlineInvoiceService.withdraw(onlineInvoiceId);
    }

    private void checkBeforeOnlineInvoiceWithdraw(List<OnlineInvoiceAdvance> onlineInvoiceAdvances) {
        if (CollectionUtils.isNotEmpty(onlineInvoiceAdvances)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("存在核销的预付款明细,撤回前,请删除完!"));
        }
    }

    @Override
    public Long restart(Long onlineInvoiceId) {
        InvoiceNotice byId = scIInvoiceNoticeService.getById(onlineInvoiceId);
        checkBeforeRestart(byId);
        List<Long> onlineInvoiceIds = new ArrayList<>();
        onlineInvoiceIds.add(onlineInvoiceId);
        scIOnlineInvoiceService.restart(onlineInvoiceIds);
        return onlineInvoiceId;
    }

    private void checkBeforeRestart(InvoiceNotice byId) {
        if (byId != null && !InvoiceNoticeStatus.REJECTED.name().equals(byId.getInvoiceNoticeStatus())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("只有驳回状态的单据,才可重置!"));
        }
    }


    public synchronized void addAdvance(List<OnlineInvoiceAdvance> onlineInvoiceAdvances) {
        if (CollectionUtils.isNotEmpty(onlineInvoiceAdvances)) {
            for (OnlineInvoiceAdvance onlineInvoiceAdvance : onlineInvoiceAdvances) {
                if (onlineInvoiceAdvance == null) continue;
//                BigDecimal chargeOffAmount = onlineInvoiceAdvance.getChargeOffAmount();//本次核销金额
//                BigDecimal usableAmount = onlineInvoiceAdvance.getUsableAmount();//可用金额
//                BigDecimal hangAccountAmount = onlineInvoiceAdvance.getHangAccountAmount();//挂账金额(申请金额)
//                BigDecimal newUsableAmount = usableAmount.add(chargeOffAmount);//更新可用金额(不能大于挂账金额)
//                onlineInvoiceAdvance.setUsableAmount(newUsableAmount);
                AdvanceApplyHead advanceApplyHead = iAdvanceApplyHeadService.getById(onlineInvoiceAdvance.getAdvanceApplyHeadId());
                iAdvanceApplyHeadService.setQuote(new OnlineInvoiceAdvance()
                        .setAdvanceApplyHeadId(advanceApplyHead.getAdvanceApplyHeadId())
                        .setUsableAmount(advanceApplyHead.getUsableAmount()), YesOrNo.NO.getValue());
            }
        }
    }

    private void checkParam(List<InvoiceNoticeDTO> invoiceNoticeDTOS) {
        Assert.notEmpty(invoiceNoticeDTOS, LocaleHandler.getLocaleMsg("没有勾选需要创建的网上开票行,请检查!"));
//        1、勾选的数据必须是业务实体+供应商+成本类型+币种+税率+项目+合同编号一致，
        for (InvoiceNoticeDTO invoiceNoticeDTO : invoiceNoticeDTOS) {
            if (invoiceNoticeDTO == null) continue;
            Long orgId = invoiceNoticeDTO.getOrgId() == null ? 0 : invoiceNoticeDTO.getOrgId();
            Long vendorId = invoiceNoticeDTO.getVendorId() == null ? 0 : invoiceNoticeDTO.getVendorId();
            Long organizationId = invoiceNoticeDTO.getOrganizationId() == null ? 0 : invoiceNoticeDTO.getOrganizationId();
            String ceeaCostTypeCode = StringUtils.isBlank(invoiceNoticeDTO.getCeeaCostTypeCode()) ? "" : invoiceNoticeDTO.getCeeaCostTypeCode();
            String currencyCode = StringUtils.isBlank(invoiceNoticeDTO.getCurrencyCode()) ? "" : invoiceNoticeDTO.getCurrencyCode();
            String taxKey = StringUtils.isBlank(invoiceNoticeDTO.getTaxKey()) ? "" : invoiceNoticeDTO.getTaxKey();
            String projectNum = StringUtils.isBlank(invoiceNoticeDTO.getProjectNum()) ? "" : invoiceNoticeDTO.getProjectNum();
            String contractNo = StringUtils.isBlank(invoiceNoticeDTO.getContractNo()) ? "" : invoiceNoticeDTO.getContractNo();
            for (InvoiceNoticeDTO noticeDTO : invoiceNoticeDTOS) {
                if (noticeDTO == null) continue;
                Long orgIdCopy = noticeDTO.getOrgId() == null ? 0 : noticeDTO.getOrgId();
                Long vendorIdCopy = noticeDTO.getVendorId() == null ? 0 : noticeDTO.getVendorId();
                Long organizationIdCopy = noticeDTO.getOrganizationId() == null ? 0 : noticeDTO.getOrganizationId();
                String ceeaCostTypeCodeCopy = StringUtils.isBlank(noticeDTO.getCeeaCostTypeCode()) ? "" : noticeDTO.getCeeaCostTypeCode();
                String currencyCodeCopy = StringUtils.isBlank(noticeDTO.getCurrencyCode()) ? "" : noticeDTO.getCurrencyCode();
                String taxKeyCopy = StringUtils.isBlank(noticeDTO.getTaxKey()) ? "" : noticeDTO.getTaxKey();
                String projectNumCopy = StringUtils.isBlank(noticeDTO.getProjectNum()) ? "" : noticeDTO.getProjectNum();
                String contractNoCopy = StringUtils.isBlank(noticeDTO.getContractNo()) ? "" : noticeDTO.getContractNo();
                if (!(orgId.compareTo(orgIdCopy) == 0
                        && vendorId.compareTo(vendorIdCopy) == 0
                        && organizationId.compareTo(organizationIdCopy) == 0
                        && ceeaCostTypeCode.equals(ceeaCostTypeCodeCopy)
                        && currencyCode.equals(currencyCodeCopy)
                        && taxKey.equals(taxKeyCopy)
                        && projectNum.equals(projectNumCopy)
                        && contractNo.equals(contractNoCopy))) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("存在业务实体+供应商+成本类型+币种+税率+项目+合同编号不一致,请检查!"));
                }
            }
        }
//        2、退货的行和接收的行必须是同一个接收编号、订单编号、行号、同一个事物处理类型（接收）才能放在一起做网上发票以及合并
//        3、同一接收编号入库和退货需同时勾选 ??
//        4、总金额为0的不能创建网上发票

    }


}
