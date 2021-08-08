package com.midea.cloud.srm.supcooperate.invoice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.VendorAssesFormStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.pm.po.CeeaWarehousingReturnDetailEnum;
import com.midea.cloud.common.enums.pm.ps.InvoiceStatus;
import com.midea.cloud.common.enums.pm.ps.OnlineInvoiceType;
import com.midea.cloud.common.enums.soap.AcceptPoLockOperTypeEnum;
import com.midea.cloud.common.enums.supcooperate.InvoiceImportStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.perf.PerformanceClient;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.soap.DataSourceEnum;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.vendorasses.VendorAssesForm;
import com.midea.cloud.srm.model.pm.ps.anon.external.FsscStatus;
import com.midea.cloud.srm.model.pm.ps.http.*;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.*;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.*;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.model.suppliercooperate.soap.erp.invoice.*;
import com.midea.cloud.srm.model.workflow.service.IFlowBusinessCallbackService;
import com.midea.cloud.srm.supcooperate.SupCmSaopUrl;
import com.midea.cloud.srm.supcooperate.invoice.mapper.OnlineInvoiceMapper;
import com.midea.cloud.srm.supcooperate.invoice.service.*;
import com.midea.cloud.srm.supcooperate.invoice.utils.OnlineInvoiceExportUtils;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import com.midea.cloud.srm.supcooperate.soap.erp.service.ErpAcceptPoRcvRtnLockSoapBizPtt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
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
@Service
@Slf4j
public class OnlineInvoiceServiceImpl extends ServiceImpl<OnlineInvoiceMapper, OnlineInvoice> implements IOnlineInvoiceService , IFlowBusinessCallbackService {
    @Autowired
    private OnlineInvoiceMapper onlineInvoiceMapper;

    @Autowired
    private IInvoiceNoticeService iInvoiceNoticeService;

    @Autowired
    private IOnlineInvoicePunishService iOnlineInvoicePunishService;

    @Autowired
    private IInvoicePunishService iInvoicePunishService;

    @Autowired
    private IOnlineInvoiceDetailService iOnlineInvoiceDetailService;

    @Autowired
    private IOnlineInvoiceAdvanceService iOnlineInvoiceAdvanceService;

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private PmClient pmClient;

    @Autowired
    private IInvoiceDetailService iInvoiceDetailService;

    @Autowired
    private IOrderDetailService iOrderDetailService;

    @Autowired
    private PerformanceClient performanceClient;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private ApiClient apiClient;

    /* 业务类型字典码 */
    public static final LinkedHashMap<String, String> businessTypeMap;

    static {
        businessTypeMap = new LinkedHashMap<>();

        businessTypeMap.put("105", "福利费-HR会签");
        businessTypeMap.put("108", "办公费");
        businessTypeMap.put("109", "车辆费用");
        businessTypeMap.put("113", "培训费-会签");
        businessTypeMap.put("122", "招聘费");
        businessTypeMap.put("135", "广告宣传费");
        businessTypeMap.put("137", "环境保护费");
        businessTypeMap.put("144", "安全费用");
        businessTypeMap.put("145", "保险费-货物");
        businessTypeMap.put("147", "仓储费用");
        businessTypeMap.put("149", "加工费");
        businessTypeMap.put("130", "低值易耗品");
        businessTypeMap.put("150", "检验检测费用");
        businessTypeMap.put("157", "专业费用");
        businessTypeMap.put("159", "修理费");
        businessTypeMap.put("163", "运杂费-设备运费");
        businessTypeMap.put("164", "运杂费");
        businessTypeMap.put("165", "支付外包服务费");
        businessTypeMap.put("166", "知识产权维护费");
        businessTypeMap.put("167", "租赁费");
        businessTypeMap.put("173", "物业管理费");
        businessTypeMap.put("176", "IT系统服务费");
        businessTypeMap.put("284", "机构代发工资、社保、公积金、顾问费用等");
        businessTypeMap.put("183", "材料采购-大宗材料-付款");
        businessTypeMap.put("184", "材料采购-大宗材料-预付");
        businessTypeMap.put("185", "材料采购-辅材(BC类物资&备品备件)-付款");
        businessTypeMap.put("186", "材料采购-辅材(BC类物资&备品备件)-预付");
        businessTypeMap.put("187", "代付业务-材料");
        businessTypeMap.put("202", "电站建设及服务付款(EPC)");
        businessTypeMap.put("203", "电站建设及服务预付款(EPC)");
        businessTypeMap.put("248", "已预付其他款项退款");
        businessTypeMap.put("368", "代付业务-材料-预付");
        businessTypeMap.put("194", "代付业务-设备");
        businessTypeMap.put("215", "固定资产-非生产性资产类-付款");
        businessTypeMap.put("217", "固定资产类-材料采购-付款");
        businessTypeMap.put("218", "固定资产类-材料采购-预付");
        businessTypeMap.put("219", "固定资产-生产设备类-付款");
        businessTypeMap.put("220", "固定资产-生产设备类-预付");
        businessTypeMap.put("221", "固定资产-非生产办公资产类-预付");
        businessTypeMap.put("369", "代付业务-设备-预付");
        businessTypeMap.put("110", "会议费");
        businessTypeMap.put("114", "其他费用");
        businessTypeMap.put("138", "环评费");
        businessTypeMap.put("142", "食堂劳务费");
        businessTypeMap.put("174", "IT工程费");
        businessTypeMap.put("175", "IT耗材费");
        businessTypeMap.put("223", "费用类预付-广告宣传");
        businessTypeMap.put("226", "费用类预付-其他费用");
        businessTypeMap.put("230", "费用类预付-租赁费");
        businessTypeMap.put("246", "无形资产类付款");
        businessTypeMap.put("247", "无形资产类预付");
        businessTypeMap.put("270", "IT库");
        businessTypeMap.put("271", "办公用品库");
        businessTypeMap.put("282", "柴油采购");
    }

    private void logFSSC(Object requestBody,
                         OnlineInvoiceOutputParameters responseBody,
                         String interfaceName,
                         String url,
                         String businessNum) {
        // 日志收集类
        InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
        interfaceLogDTO.setServiceName(interfaceName); // 说明
        interfaceLogDTO.setServiceType("HTTP"); // 接收方式
        interfaceLogDTO.setType("SEND"); // 类型
        interfaceLogDTO.setDealTime(1L);
        interfaceLogDTO.setBillId(businessNum);
        interfaceLogDTO.setServiceInfo(JSON.toJSONString(requestBody)); // 入参
        interfaceLogDTO.setCreationDateBegin(new Date()); // 开始时间
        interfaceLogDTO.setCreationDateEnd(new Date()); // 结束时间
        interfaceLogDTO.setUrl(url);
        interfaceLogDTO.setReturnInfo(JSON.toJSONString(responseBody)); // 出参
        if (responseBody.getXESBRESULTINFOREC() != null && "S".equals(responseBody.getXESBRESULTINFOREC().getRETURNSTATUS())) {
            interfaceLogDTO.setStatus("SUCCESS"); // 状态
            interfaceLogDTO.setFinishDate(new Date()); // 完成时间
        } else {
            interfaceLogDTO.setStatus("FAIL");
            interfaceLogDTO.setErrorInfo(JSON.toJSONString(responseBody));
        }
        apiClient.createInterfaceLog(interfaceLogDTO);
    }

    //锁定接收账号
    @Value("${acceptPoLock.acceptPoLockUsername}")
    private String acceptPoLockUsername;
    //锁定接收密码
    @Value("${acceptPoLock.acceptPoLockPassword}")
    private String acceptPoLockPassword;
    //调用erp地点接口超时时间（单位：毫秒）
    @Value("${acceptPoLock.cxfClientConnectTimeout}")
    private int cxfClientConnectTimeout;

    @Override
    @Transactional
    public OnlineInvoiceSaveDTO createOnlineInvoice(List<InvoiceNoticeDTO> invoiceNoticeDTOS) {
        checkParam(invoiceNoticeDTOS);//ToDo
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        OnlineInvoiceSaveDTO onlineInvoiceSaveDTO = new OnlineInvoiceSaveDTO();
        OnlineInvoice onlineInvoice = new OnlineInvoice();
        List<OnlineInvoiceDetail> onlineInvoiceDetails = new ArrayList<>();
        List<OnlineInvoicePunish> onlineInvoicePunishes = new ArrayList<>();
        List<InvoicePunish> invoicePunishes = new ArrayList<>();
        InvoiceNoticeDTO dto = invoiceNoticeDTOS.get(0);
        CompanyInfo companyInfo = supplierClient.getCompanyInfo(dto.getVendorId());
        BeanUtils.copyProperties(dto, onlineInvoice);
        if (loginAppUser.getUserType().equals(UserType.BUYER.name())) {
            onlineInvoice.setApproverUsername(loginAppUser.getUsername())
                    .setApproverNickname(loginAppUser.getNickname())
                    .setApproverId(loginAppUser.getUserId())
                    .setApproverDeptid(loginAppUser.getCeeaDeptId())
                    .setApproverDept(loginAppUser.getDepartment());
        }
        onlineInvoice.setVirtualInvoice(YesOrNo.NO.getValue())
                .setInvoiceDate(LocalDate.now())
                .setCostTypeName(dto.getCeeaCostType())
                .setContractCode(dto.getContractNo())
                .setCostTypeCode(dto.getCeeaCostTypeCode())
                .setErpVendorCode(companyInfo.getErpVendorCode());
        //1.假如通过开票通知的,默认是非服务类开票  N.
        //2.直接通过采购订单创建的,是服务类开票 Y.
        if (dto != null && StringUtils.isBlank(dto.getInvoiceNoticeNumber())) {
            onlineInvoice.setIsService(YesOrNo.YES.getValue());
            //设置业务类型
            setBusinessType(onlineInvoice, dto);
        } else {
            onlineInvoice.setIsService(YesOrNo.NO.getValue());
        }
        if (CollectionUtils.isNotEmpty(invoiceNoticeDTOS)) {
            for (InvoiceNoticeDTO invoiceNoticeDTO : invoiceNoticeDTOS) {
                if (invoiceNoticeDTO == null) continue;
                Long invoiceDetailId = invoiceNoticeDTO.getInvoiceDetailId();
                BigDecimal invoiceQuantity = invoiceNoticeDTO.getInvoiceQuantity() == null ? BigDecimal.ZERO : invoiceNoticeDTO.getInvoiceQuantity();//本次开票数量
                BigDecimal notInvoiceQuantity = invoiceNoticeDTO.getNotInvoiceQuantity() == null ? BigDecimal.ZERO : invoiceNoticeDTO.getNotInvoiceQuantity();//未开票数量
                BigDecimal unitPriceExcludingTax = invoiceNoticeDTO.getUnitPriceExcludingTax() == null ? BigDecimal.ZERO : invoiceNoticeDTO.getUnitPriceExcludingTax();//单价（不含税）(订单单价)
                BigDecimal unitPriceContainingTax = invoiceNoticeDTO.getUnitPriceContainingTax() == null ? BigDecimal.ZERO : invoiceNoticeDTO.getUnitPriceContainingTax();//单价（含税)
                //封装网上开票发票明细
                OnlineInvoiceDetail onlineInvoiceDetail = new OnlineInvoiceDetail();
                BeanUtils.copyProperties(invoiceNoticeDTO, onlineInvoiceDetail);
                //临时处理合同编号问题 ToDo
                // 2020-12-29号 隆基回迁产品 选择服务类创建网上开票的时候报错bugfix
                Long orderDetailId = invoiceNoticeDTO.getOrderDetailId();
//                InvoiceDetail invoiceDetail = iInvoiceDetailService.getById(invoiceDetailId);
//                String contractCode = invoiceDetail.getContractCode();
//                Long contractHeadId = invoiceDetail.getContractHeadId();
//                String contractNo = invoiceDetail.getContractNo();
//                if (StringUtils.isNotBlank(contractCode)) {
//                    onlineInvoiceDetail.setContractCode(contractCode);
//                    onlineInvoice.setContractCode(contractCode);
//                }else {
//                    onlineInvoiceDetail.setContractCode(contractNo);
//                    onlineInvoice.setContractCode(contractNo);
//                }
                InvoiceDetail invoiceDetail = iInvoiceDetailService.getById(invoiceDetailId);
                OrderDetail orderDetail = iOrderDetailService.getById(orderDetailId);
                String contractCode = "";
                Long contractHeadId = null;
                String contractNo = "";
                if (null != invoiceDetail) {
                    contractCode = invoiceDetail.getContractCode();
                    contractHeadId = invoiceDetail.getContractHeadId();
                    contractNo = invoiceDetail.getContractNo();
                }
                if (null != orderDetail) {
                    contractCode = orderDetail.getCeeaContractNo();
                }
                if (StringUtils.isNotBlank(contractCode)) {
                    onlineInvoiceDetail.setContractCode(contractCode);
                    onlineInvoice.setContractCode(contractCode);
                }else {
                    onlineInvoiceDetail.setContractCode(contractNo);
                    onlineInvoice.setContractCode(contractNo);
                }





                onlineInvoiceDetail.setInvoiceQuantity(invoiceQuantity)
                        .setNotInvoiceQuantity(notInvoiceQuantity);
                BigDecimal noTaxAmount = invoiceQuantity.multiply(unitPriceExcludingTax).setScale(2, BigDecimal.ROUND_HALF_UP);//净价金额(本次)
                onlineInvoiceDetail.setNoTaxAmount(noTaxAmount);
                BigDecimal taxAmount = invoiceQuantity.multiply(unitPriceContainingTax).setScale(2, BigDecimal.ROUND_HALF_UP);//含税金额
                onlineInvoiceDetail.setTaxAmount(taxAmount);
                BigDecimal tax = taxAmount.subtract(noTaxAmount);
                onlineInvoiceDetail.setTax(tax);
                onlineInvoiceDetail.setOrderUnitPriceTaxN(unitPriceExcludingTax);//订单单价(不含税)
                if (YesOrNo.YES.getValue().equals(onlineInvoice.getIsService())) {
                    onlineInvoiceDetail.setOrderDetailId(invoiceNoticeDTO.getOrderDetailId());
                }
                onlineInvoiceDetail.setTxnId(invoiceNoticeDTO.getTxnId())//事务处理ID
                        .setShipLineNum(invoiceNoticeDTO.getShipLineNum());//采购发运行号
                onlineInvoiceDetails.add(onlineInvoiceDetail);
                //回写开票通知开票数量或者回写采购订单开票数量(应该是在保存时回写,不应该创建时回写)
//                if (YesOrNo.YES.getValue().equals(onlineInvoice.getIsService())) {//服务类回写采购订单未开票数量
//                    iOrderDetailService.updateById(new OrderDetail()
//                            .setOrderDetailId(invoiceNoticeDTO.getOrderDetailId())
//                            .setInvoiceQuantity(notInvoiceQuantity.subtract(invoiceQuantity))
//                            .setNotInvoiceQuantity(notInvoiceQuantity.subtract(invoiceQuantity))
//                            .setNoTaxAmount((notInvoiceQuantity.subtract(invoiceQuantity)).multiply(unitPriceExcludingTax)));//净价金额,每次都要回写未开票数量*不含税单价
//                }
//                if (YesOrNo.NO.getValue().equals(onlineInvoice.getIsService())) {//非服务类回写开票通知未开票数量
//                    iInvoiceDetailService.updateById(new InvoiceDetail()
//                            .setInvoiceDetailId(invoiceDetailId)
//                            .setInvoiceQuantity(notInvoiceQuantity.subtract(invoiceQuantity))
//                            .setNotInvoiceQuantity(notInvoiceQuantity.subtract(invoiceQuantity))
//                            .setNoTaxAmount((notInvoiceQuantity.subtract(invoiceQuantity)).multiply(unitPriceExcludingTax)));//净价金额,每次都要回写未开票数量*不含税单价
//                }
            }

            //封装网上开票扣罚明细(需求已修改,废弃)
//            InvoiceNoticeDTO invoiceNoticeDTO = invoiceNoticeDTOS.get(0);
//            if (invoiceNoticeDTO != null && invoiceNoticeDTO.getInvoiceNoticeId() != null) {
//                invoicePunishes = iInvoicePunishService.list(new QueryWrapper<>(new InvoicePunish().setInvoiceNoticeId(invoiceNoticeDTO.getInvoiceNoticeId())));
//            }
//            if (CollectionUtils.isNotEmpty(invoicePunishes)) {
//                for (InvoicePunish invoicePunish : invoicePunishes) {
//                    if (invoicePunish == null) continue;
//                    OnlineInvoicePunish onlineInvoicePunish = new OnlineInvoicePunish();
//                    BeanUtils.copyProperties(invoicePunish, onlineInvoicePunish);
//                    onlineInvoicePunishes.add(onlineInvoicePunish);
//                }
//            }
        }
        //1.非服务类汇总
        /*按照入退货为组合,重新汇总计算系统含税金额和系统税额*/
        BigDecimal taxTotalAmount = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;
        if (YesOrNo.NO.getValue().equals(onlineInvoice.getIsService())) {
            List<OnlineInvoiceDetail> returnOnlineInvoiceDetails = onlineInvoiceDetails.stream().filter(item -> CeeaWarehousingReturnDetailEnum.RETURN.getValue().equals(item.getType())).map(e->BeanCopyUtil.copyProperties(e,OnlineInvoiceDetail::new)).collect(Collectors.toList());//退货开票明细
            List<OnlineInvoiceDetail> receiveOnlineInvoiceDetails = onlineInvoiceDetails.stream().filter(item -> CeeaWarehousingReturnDetailEnum.RECEIVE.getValue().equals(item.getType())).map(e->BeanCopyUtil.copyProperties(e,OnlineInvoiceDetail::new)).collect(Collectors.toList());//入货开票明细
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
            }
            //全return情况:
            if (CollectionUtils.isEmpty(receiveOnlineInvoiceDetails) && CollectionUtils.isNotEmpty(returnOnlineInvoiceDetails)) {
                taxTotalAmount = returnOnlineInvoiceDetails.stream().map(x ->
                        new BigDecimal(StringUtil.StringValue(x.getTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
                totalTax = returnOnlineInvoiceDetails.stream().map(x ->
                        new BigDecimal(StringUtil.StringValue(x.getTax().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
                //全receive和有receive,有return情况:
            } else {
                taxTotalAmount = receiveOnlineInvoiceDetails.stream().map(x ->
                        new BigDecimal(StringUtil.StringValue(x.getTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
                totalTax = receiveOnlineInvoiceDetails.stream().map(x ->
                        new BigDecimal(StringUtil.StringValue(x.getTax().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
        }
        //2.服务类汇总
        if (YesOrNo.YES.getValue().equals(onlineInvoice.getIsService())) {
            taxTotalAmount = onlineInvoiceDetails.stream().map(x ->
                    new BigDecimal(StringUtil.StringValue(x.getTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
            totalTax = onlineInvoiceDetails.stream().map(x ->
                    new BigDecimal(StringUtil.StringValue(x.getTax().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        onlineInvoiceSaveDTO.setOnlineInvoiceDetails(onlineInvoiceDetails)
                .setOnlineInvoice(onlineInvoice.setTaxTotalAmount(taxTotalAmount).setTotalTax(totalTax));
        return onlineInvoiceSaveDTO;
    }

    private void setBusinessType(OnlineInvoice onlineInvoice, InvoiceNoticeDTO dto) {
        String ceeaBusinessSmallCode = dto.getCeeaBusinessSmallCode();
        if (StringUtils.isNotBlank(ceeaBusinessSmallCode)) {
            onlineInvoice.setBusinessType(ceeaBusinessSmallCode.substring(0, 3));
        }
    }

    private void checkParam(List<InvoiceNoticeDTO> invoiceNoticeDTOS) {
        Assert.notEmpty(invoiceNoticeDTOS, LocaleHandler.getLocaleMsg("没有勾选需要创建的网上开票行,请检查!"));
//        ToDo
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

    @Override
    public PageInfo<OnlineInvoice> listPage(OnlineInvoiceQueryDTO onlineInvoiceQueryDTO) {
        PageUtil.startPage(onlineInvoiceQueryDTO.getPageNum(), onlineInvoiceQueryDTO.getPageSize());
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String userType = loginAppUser.getUserType();
        if (OnlineInvoiceType.VENDOR_INVOICE.name().equals(onlineInvoiceQueryDTO.getOnlineInvoiceType())) {
            if (UserType.VENDOR.name().equals(userType)) {
                //防止没注册供应商查看到其它全部供应商的信息
                if (Objects.isNull(loginAppUser.getCompanyId())) {
                    return new PageInfo<>(new ArrayList<>());
                }
                onlineInvoiceQueryDTO.setVendorId(loginAppUser.getCompanyId());
            }
            if (UserType.BUYER.name().equals(userType)) {
                onlineInvoiceQueryDTO.setUserType(userType);
            }
        }
        onlineInvoiceQueryDTO.setOrgIds(CollectionUtils.isNotEmpty(onlineInvoiceQueryDTO.getOrgIds()) ? onlineInvoiceQueryDTO.getOrgIds() : null);
        onlineInvoiceQueryDTO.setBusinessType(StringUtils.isNotBlank(onlineInvoiceQueryDTO.getBusinessType()) ? onlineInvoiceQueryDTO.getBusinessType() : null);
        onlineInvoiceQueryDTO.setTaxInvoiceNum(StringUtils.isNotBlank(onlineInvoiceQueryDTO.getTaxInvoiceNum()) ? onlineInvoiceQueryDTO.getTaxInvoiceNum() : null);
        onlineInvoiceQueryDTO.setFsscNo(StringUtils.isNotBlank(onlineInvoiceQueryDTO.getFsscNo()) ? onlineInvoiceQueryDTO.getFsscNo() : null);
        onlineInvoiceQueryDTO.setPayMethod(StringUtils.isNotBlank(onlineInvoiceQueryDTO.getPayMethod()) ? onlineInvoiceQueryDTO.getPayMethod() : null);
        List<OnlineInvoice> result = new ArrayList<>();
        //网上开票-供应商只显示自身数据,  采购商 显示的数据根据权限配置;
        if (UserType.VENDOR.name().equals(userType)) {
            //供应商
            result = listonlineinvoiceVendor(onlineInvoiceQueryDTO);
        } else if (UserType.BUYER.name().equals(userType)) {
            //采购商
            result = listOnlineInvoiceBuyer(onlineInvoiceQueryDTO);
        }
        return new PageInfo<OnlineInvoice>(result);
    }

    //2020-12-24 隆基产品回迁 重写方法(解决单据创建时间降序排序无效)
    @Override
    public PageInfo<OnlineInvoice> listPageNew(OnlineInvoiceQueryDTO onlineInvoiceQueryDTO) {
        PageUtil.startPage(onlineInvoiceQueryDTO.getPageNum(), onlineInvoiceQueryDTO.getPageSize());
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String userType = loginAppUser.getUserType();
        if (OnlineInvoiceType.VENDOR_INVOICE.name().equals(onlineInvoiceQueryDTO.getOnlineInvoiceType())) {
            if (UserType.VENDOR.name().equals(userType)) {
                //防止没注册供应商查看到其它全部供应商的信息
                if (Objects.isNull(loginAppUser.getCompanyId())) {
                    return new PageInfo<>(new ArrayList<>());
                }
                onlineInvoiceQueryDTO.setVendorId(loginAppUser.getCompanyId());
            }
            if (UserType.BUYER.name().equals(userType)) {
                onlineInvoiceQueryDTO.setUserType(userType);
            }
        }
        onlineInvoiceQueryDTO.setOrgIds(CollectionUtils.isNotEmpty(onlineInvoiceQueryDTO.getOrgIds()) ? onlineInvoiceQueryDTO.getOrgIds() : null);
        onlineInvoiceQueryDTO.setBusinessType(StringUtils.isNotBlank(onlineInvoiceQueryDTO.getBusinessType()) ? onlineInvoiceQueryDTO.getBusinessType() : null);
        onlineInvoiceQueryDTO.setTaxInvoiceNum(StringUtils.isNotBlank(onlineInvoiceQueryDTO.getTaxInvoiceNum()) ? onlineInvoiceQueryDTO.getTaxInvoiceNum() : null);
        onlineInvoiceQueryDTO.setFsscNo(StringUtils.isNotBlank(onlineInvoiceQueryDTO.getFsscNo()) ? onlineInvoiceQueryDTO.getFsscNo() : null);
        onlineInvoiceQueryDTO.setPayMethod(StringUtils.isNotBlank(onlineInvoiceQueryDTO.getPayMethod()) ? onlineInvoiceQueryDTO.getPayMethod() : null);
        List<OnlineInvoice> result = onlineInvoiceMapper.findListNew(onlineInvoiceQueryDTO);
        return new PageInfo<OnlineInvoice>(result);
    }

    //    @AuthData(module = {MenuEnum.ONLINE_INVOICE , MenuEnum.SUPPLIER_SIGN})
    public List<OnlineInvoice> listonlineinvoiceVendor(OnlineInvoiceQueryDTO onlineInvoiceQueryDTO) {
        return onlineInvoiceMapper.findList(onlineInvoiceQueryDTO);
    }

    //    @AuthData(module = MenuEnum.AGENT_ONLINE_INVOICE)
    private List<OnlineInvoice> listOnlineInvoiceBuyer(OnlineInvoiceQueryDTO onlineInvoiceQueryDTO) {
        return onlineInvoiceMapper.findList(onlineInvoiceQueryDTO);
    }

    @Override
    @Transactional
    public BaseResult saveTemporary(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO, String invoiceStatus) {
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        List<OnlineInvoiceDetail> onlineInvoiceDetails = onlineInvoiceSaveDTO.getOnlineInvoiceDetails();
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = onlineInvoiceSaveDTO.getOnlineInvoiceAdvances();
        List<OnlineInvoicePunish> onlineInvoicePunishes = onlineInvoiceSaveDTO.getOnlineInvoicePunishes();
        List<Fileupload> fileuploads = onlineInvoiceSaveDTO.getFileuploads();
        //保存前校验
        checkBeforeSaveTemporary(onlineInvoice);
        //检查未开票数量,第二次保存是就不需要校验未开票数量
        if (onlineInvoice.getOnlineInvoiceId() == null) {
            checkNotInvoiceQuantity(onlineInvoice, onlineInvoiceDetails);
        }
        //更新单据状态时,单据需要保留驳回的状态
        if (InvoiceStatus.REJECTED.name().equals(onlineInvoice.getInvoiceStatus())) {
            invoiceStatus = InvoiceStatus.REJECTED.name();
        }
        BaseResult baseResult = saveOrUpdateOnlineInvoiceSaveDTO(invoiceStatus, onlineInvoice, onlineInvoiceDetails, onlineInvoiceAdvances, onlineInvoicePunishes, fileuploads);
        //保存,校验调整金额是否一致,需要给调用方抛出异常类
        if (ResultCode.UNKNOWN_ERROR.getCode().equals(baseResult.getCode())) {
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return baseResult;
        }
        return baseResult;
        //锁定接收(Erp)
//        BaseResult<Object> baseResult2 = BaseResult.buildSuccess();
//        Long onlineInvoiceId = (Long) baseResult.getData();
//        OnlineInvoice byId = this.getById(onlineInvoiceId);
//        OnlineInvoiceOutputParameters onlineInvoiceOutputParameters = new OnlineInvoiceOutputParameters();
//        if (byId != null && StringUtils.isBlank(byId.getInstid()) && YesOrNo.NO.getValue().equals(onlineInvoice.getIsService())) {
//            //全return的情况不需要调用接收锁定
//            List<OnlineInvoiceDetail> collect = onlineInvoiceDetails.stream().filter(onlineInvoiceDetail -> (CeeaWarehousingReturnDetailEnum.RECEIVE.getValue().equals(onlineInvoiceDetail.getType()))).collect(Collectors.toList());
//            if (CollectionUtils.isNotEmpty(collect)) {
//                baseResult2 = acceptPoLock(onlineInvoice, onlineInvoiceDetails, AcceptPoLockOperTypeEnum.VERIFY.name());
//                onlineInvoiceOutputParameters = (OnlineInvoiceOutputParameters) baseResult2.getData();
//            }
//            if (ResultCode.UNKNOWN_ERROR.getCode().equals(baseResult2.getCode())) {
//                //手动回滚
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                return baseResult2;
//            }
//            if (onlineInvoiceOutputParameters != null && onlineInvoiceOutputParameters.getXESBRESULTINFOREC() != null && "E".equals(onlineInvoiceOutputParameters.getXESBRESULTINFOREC().getRETURNSTATUS())) {
//                //手动回滚
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                baseResult2.setCode(ResultCode.UNKNOWN_ERROR.getCode());
//                baseResult2.setMessage(onlineInvoiceOutputParameters.getXESBRESULTINFOREC().getRETURNMSG());
//                return baseResult2;
//            }
//            if (onlineInvoiceOutputParameters != null && onlineInvoiceOutputParameters.getXESBRESULTINFOREC() != null && "S".equals(onlineInvoiceOutputParameters.getXESBRESULTINFOREC().getRETURNSTATUS())) {
//                String instid = onlineInvoiceOutputParameters.getXESBRESULTINFOREC().getINSTID();
//                this.updateById(new OnlineInvoice().setOnlineInvoiceId(onlineInvoiceId).setInstid(instid));
//            }
//        }
//        baseResult2.setCode(ResultCode.SUCCESS.getCode());
//        baseResult2.setMessage(ResultCode.SUCCESS.getMessage());
//        baseResult2.setData(onlineInvoiceId);
//        return baseResult2;
    }

    private void checkBeforeSaveTemporary(OnlineInvoice onlineInvoice) {
        Assert.notNull(onlineInvoice, "onlineInvoice不能为空");
        OnlineInvoice dbOnlineInvoice = this.getById(onlineInvoice.getOnlineInvoiceId());
        Optional.ofNullable(dbOnlineInvoice).ifPresent(o -> {
            String invoiceStatus = o.getInvoiceStatus();
            if (InvoiceStatus.DROP.name().equals(invoiceStatus)) {
                throw new BaseException(LocaleHandler.getLocaleMsg("当前单据已作废,请检查!"));
            }
            if (InvoiceStatus.UNDER_APPROVAL.name().equals(invoiceStatus)) {
                throw new BaseException(LocaleHandler.getLocaleMsg("当前单据已提交,请检查!"));
            }
        });
    }

    public BaseResult acceptPoLock(OnlineInvoice onlineInvoice, List<OnlineInvoiceDetail> onlineInvoiceDetails, String operType) {
        long startTime = System.currentTimeMillis() / 1000;
        log.info("-----------------------------开始锁定接收-------------------------------------------------" + new Date());
        //代理工厂
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        //设置代理地址
        jaxWsProxyFactoryBean.setAddress(SupCmSaopUrl.acceptPoLockUrl);
        jaxWsProxyFactoryBean.setUsername(acceptPoLockUsername);
        jaxWsProxyFactoryBean.setPassword(acceptPoLockPassword);
        //设置接口类型
        jaxWsProxyFactoryBean.setServiceClass(ErpAcceptPoRcvRtnLockSoapBizPtt.class);
        //创建一个代理接口实现
        ErpAcceptPoRcvRtnLockSoapBizPtt service = (ErpAcceptPoRcvRtnLockSoapBizPtt) jaxWsProxyFactoryBean.create();
        //通过代理对象获取本地客户端
        Client proxy = ClientProxy.getClient(service);
        // 通过本地客户端设置 网络策略配置
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        // 用于配置客户端HTTP端口的属性
        HTTPClientPolicy policy = new HTTPClientPolicy();
        // 超时控制 单位 : 毫秒
        policy.setConnectionTimeout(60 * 60 * 1000);
        policy.setReceiveTimeout(60 * 60 * 1000);
        conduit.setClient(policy);
        //创建请求类
        OnlineInvoiceInputParameters onlineInvoiceInputParameters = new OnlineInvoiceInputParameters();
        APPSCUXPORECEIVELX1139141X1X1 esbInfo = new APPSCUXPORECEIVELX1139141X1X1();
        Date nowDate = new Date();
        esbInfo.setINSTID("");
        esbInfo.setREQUESTTIME(String.valueOf(nowDate.getTime()));
        onlineInvoiceInputParameters.setPESBINFOREC(esbInfo);

        //erp网上开票
        APPSCUXPORECEIVELX1139141X1X7 requestInfos = new APPSCUXPORECEIVELX1139141X1X7();
        List<APPSCUXPORECEIVELX1139141X1X8> ppoinfotblitem = new ArrayList<>();
        APPSCUXPORECEIVELX1139141X1X8 erpOnlineInvoice = new APPSCUXPORECEIVELX1139141X1X8();
        //erp网上开票明细
        APPSCUXPORECEIVEX1139141X1X11 rcvdetails = new APPSCUXPORECEIVEX1139141X1X11();
        List<APPSCUXPORECEIVEX1139141X1X12> rcvdetailsitem = new ArrayList<>();
        //转换ERP参数
        //1.转换erp网上开票
        erpOnlineInvoice.setINVOICENUM(onlineInvoice.getOnlineInvoiceNum());//发票编号
        erpOnlineInvoice.setOPERTYPE(operType);//操作类型
        erpOnlineInvoice.setSOURCESYSCODE(DataSourceEnum.NSRM_SYS.getKey());//来源系统
        erpOnlineInvoice.setIFACECODE("LOCK_RCV");//接口编码
        erpOnlineInvoice.setIFACEMEAN("接收锁定");
        //2.转换erp网上开票明细
        for (OnlineInvoiceDetail onlineInvoiceDetail : onlineInvoiceDetails) {
            if (onlineInvoiceDetail == null || CeeaWarehousingReturnDetailEnum.RETURN.getValue().equals(onlineInvoiceDetail.getType()))
                continue;
            APPSCUXPORECEIVEX1139141X1X12 erpOnlineInvoiceDetail = new APPSCUXPORECEIVEX1139141X1X12();
            erpOnlineInvoiceDetail.setRECEIPTNUM(onlineInvoiceDetail.getReceiveOrderNo());//接收编号
            erpOnlineInvoiceDetail.setRECEIPTLINENUM(StringUtil.StringValue(onlineInvoiceDetail.getTxnId()));//接收行号(事务处理ID)
            String sourceLineId = StringUtil.StringValue(onlineInvoiceDetail.getOnlineInvoiceDetailId());
            //取消锁定的情况,系统行ID不能与正向重复,需要重新生成
            if (AcceptPoLockOperTypeEnum.CANCEL.name().equals(operType)) {
                sourceLineId = StringUtil.StringValue(IdGenrator.generate());
            }
            erpOnlineInvoiceDetail.setSOURCELINEID(sourceLineId);//来源系统行ID
            erpOnlineInvoiceDetail.setINVOICEQTY(onlineInvoiceDetail.getInvoiceQuantity());//开票数量
            rcvdetailsitem.add(erpOnlineInvoiceDetail);
        }
        rcvdetails.setRcvdetailsitem(rcvdetailsitem);
        //3.汇总组合erp参数
        erpOnlineInvoice.setRCVDETAILS(rcvdetails);
        ppoinfotblitem.add(erpOnlineInvoice);
        requestInfos.setPpoinfotblitem(ppoinfotblitem);
        onlineInvoiceInputParameters.setPPOINFOTBL(requestInfos);
        log.info("erp锁定接收参数:" + JsonUtil.entityToJsonStr(onlineInvoiceInputParameters));
        OnlineInvoiceOutputParameters response = null;
        BaseResult baseResult = new BaseResult<>();
        try {
            log.info(JsonUtil.entityToJsonStr(onlineInvoiceInputParameters));
            response = service.erpAcceptPoRcvRtnLockSoapBiz(onlineInvoiceInputParameters);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("错误信息:", e);
            baseResult.setCode(ResultCode.UNKNOWN_ERROR.getCode());
            baseResult.setMessage(LocaleHandler.getLocaleMsg("erp锁定接收接口异常,请联系管理员!"));
            return baseResult;
        } finally {
            //ToDo 加上打印日志
            try {
                logFSSC(JsonUtil.entityToJsonStr(onlineInvoiceInputParameters), response, "网上开票-erp锁定接收接口",
                        SupCmSaopUrl.acceptPoLockUrl, onlineInvoice.getOnlineInvoiceNum());
            } catch (Exception e) {
                log.error("保存接口日志信息失败", e);
            }
        }
        baseResult.setData(response);
        log.info("erp锁定接收返回参数:" + JsonUtil.entityToJsonStr(response));
        long endTime = System.currentTimeMillis() / 1000 - startTime;
        log.info("----------------------------------结束锁定接收----------------------------------------------" + endTime + "s");
        return baseResult;
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
                    InvoiceDetail byId = iInvoiceDetailService.getById(onlineInvoiceDetail.getInvoiceDetailId());
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
    @Transactional
    public OnlineInvoiceSaveDTO submit(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO, String invoiceStatus) {
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        List<OnlineInvoiceDetail> onlineInvoiceDetails = onlineInvoiceSaveDTO.getOnlineInvoiceDetails();
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = onlineInvoiceSaveDTO.getOnlineInvoiceAdvances();
        List<OnlineInvoicePunish> onlineInvoicePunishes = onlineInvoiceSaveDTO.getOnlineInvoicePunishes();
        List<Fileupload> fileuploads = onlineInvoiceSaveDTO.getFileuploads();

        checkBeforeSubmit(onlineInvoice, onlineInvoiceDetails, onlineInvoiceAdvances, onlineInvoicePunishes, fileuploads);//ToDo
        //提交网上开票信息
        saveOrUpdateOnlineInvoiceSaveDTO(invoiceStatus, onlineInvoice, onlineInvoiceDetails, onlineInvoiceAdvances, onlineInvoicePunishes, fileuploads);
        return onlineInvoiceSaveDTO;
    }

    @Override
    @Transactional
    public FSSCResult vendorAbandon(Long onlineInvoiceId) {
        OnlineInvoice onlineInvoice = this.getById(onlineInvoiceId);
        List<OnlineInvoiceDetail> onlineInvoiceDetails = iOnlineInvoiceDetailService.list(new QueryWrapper<>(new OnlineInvoiceDetail().setOnlineInvoiceId(onlineInvoiceId)));
        Assert.notNull(onlineInvoice, LocaleHandler.getLocaleMsg("该单据不存在,请检查!"));
        if (!InvoiceStatus.DRAFT.name().equals(onlineInvoice.getInvoiceStatus()) && !InvoiceStatus.REJECTED.name().equals(onlineInvoice.getInvoiceStatus())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("该单据状态不为新建和已驳回,请检查!"));
        }
        //服务类,恢复未开票数量,
        if (onlineInvoice != null && YesOrNo.YES.getValue().equals(onlineInvoice.getIsService())) {
            restoreOnlineInvoiceDetail(onlineInvoiceDetails);
        }
        //释放扣罚引用和修改考核状态
        List<OnlineInvoicePunish> onlineInvoicePunishes = iOnlineInvoicePunishService.list(new QueryWrapper<>(new OnlineInvoicePunish().setOnlineInvoiceId(onlineInvoice.getOnlineInvoiceId())));
        if (CollectionUtils.isNotEmpty(onlineInvoicePunishes)) {
            for (OnlineInvoicePunish onlineInvoicePunish : onlineInvoicePunishes) {
                if (onlineInvoicePunish == null) continue;
                performanceClient.modify(new VendorAssesForm()
                        .setVendorAssesId(onlineInvoicePunish.getVendorAssesId())
                        .setStatus(VendorAssesFormStatus.ASSESSED.getKey())
                        .setIfQuote(YesOrNo.NO.getValue()));
            }
        }
        //非服务类,恢复开票通知数据
        if (onlineInvoice != null && YesOrNo.NO.getValue().equals(onlineInvoice.getIsService())) {
            for (OnlineInvoiceDetail onlineInvoiceDetail : onlineInvoiceDetails) {
                if (onlineInvoiceDetail == null) continue;
                Long invoiceDetailId = onlineInvoiceDetail.getInvoiceDetailId();//开票通知明细ID
                BigDecimal invoiceQuantity = onlineInvoiceDetail.getInvoiceQuantity() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getInvoiceQuantity();//网上开票明细本次开票数量
                /*查询开票通知对应明细数据*/
                InvoiceDetail invoiceDetail = iInvoiceDetailService.getById(invoiceDetailId);
                Integer lineNum = invoiceDetail.getLineNum();//订单行号
                BigDecimal receiveNum = invoiceDetail.getReceiveNum() == null ? BigDecimal.ZERO : invoiceDetail.getReceiveNum();//开票通知明细接收数量
                BigDecimal unitPriceContainingTax = invoiceDetail.getUnitPriceContainingTax() == null ? BigDecimal.ZERO : invoiceDetail.getUnitPriceContainingTax();//开票通知明细单价（含税)
                BigDecimal noTaxAmount = invoiceDetail.getNoTaxAmount() == null ? BigDecimal.ZERO : invoiceDetail.getNoTaxAmount();//开票通知明细净价金额
                BigDecimal notInvoiceQuantity = invoiceDetail.getNotInvoiceQuantity() == null ? BigDecimal.ZERO : invoiceDetail.getNotInvoiceQuantity();//开票通知明细未开票数量
                BigDecimal unitPriceExcludingTax = invoiceDetail.getUnitPriceExcludingTax() == null ? BigDecimal.ZERO : invoiceDetail.getUnitPriceExcludingTax();//开票通知明细单价(不含税)(订单单价)
                BigDecimal newNotInvoiceQuantity = notInvoiceQuantity.add(invoiceQuantity);//新开票通知明细未开票数量
                BigDecimal newInvoiceQuantity = newNotInvoiceQuantity;//开票通知默认未开票数量等于本次开票数量
                BigDecimal newNoTaxAmount = newNotInvoiceQuantity.multiply(unitPriceExcludingTax);//新开票通知明细本次开票净额
                BigDecimal newTaxAmount = newNotInvoiceQuantity.multiply(unitPriceContainingTax);//新开票通知明细含税金额
                BigDecimal newTax = newTaxAmount.subtract(newNoTaxAmount);//新开票通知明细税额
                if (newNotInvoiceQuantity.compareTo(receiveNum) == 1) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("作废后,订单行号:" + lineNum + "的未开票数量大于接收数量,请联系管理员!"));
                }
                log.info("供应商网上开票作废,回写开票通知数量,{" + onlineInvoice.getOnlineInvoiceId() + "},start:-----------------------------");
                InvoiceDetail newInvoiceDetail = new InvoiceDetail()
                        .setInvoiceDetailId(invoiceDetailId)
                        .setNotInvoiceQuantity(newNotInvoiceQuantity)
                        .setInvoiceQuantity(newInvoiceQuantity)
                        .setNoTaxAmount(newNoTaxAmount)
                        .setTaxAmount(newTaxAmount)
                        .setTax(newTax);
                iInvoiceDetailService.updateById(newInvoiceDetail);
                log.info("供应商网上开票作废,回写开票通知数量," + JsonUtil.entityToJsonStr(newInvoiceDetail) + ",end:-----------------------------");
            }
        }
        this.updateById(onlineInvoice.setInvoiceStatus(InvoiceStatus.DROP.name()));
        //调用费控作废接口(存在费控回写单号,才需调用费控作废接口)(备用,暂时业务不会调用)
        FSSCResult fsscResult = new FSSCResult();
        if (StringUtils.isNotBlank(onlineInvoice.getBoeNo())) {
            fsscResult = pmClient.fsscAbandon(new FsscStatus()
                    .setFsscNo(onlineInvoice.getBoeNo())
                    .setPeripheralSystemNum(onlineInvoice.getOnlineInvoiceNum())
                    .setSourceSysteCode(DataSourceEnum.NSRM_SYS.getKey()));
        }
        if ("500".equals(fsscResult.getCode())) {
            throw new BaseException(LocaleHandler.getLocaleMsg(fsscResult.getMsg()));
        }
        //取消接收(Erp)
        OnlineInvoice byId = this.getById(onlineInvoiceId);
        OnlineInvoiceOutputParameters onlineInvoiceOutputParameters = new OnlineInvoiceOutputParameters();
        BaseResult baseResult = new BaseResult();
        if (byId != null && StringUtils.isNotBlank(byId.getInstid()) && YesOrNo.NO.getValue().equals(onlineInvoice.getIsService())) {
            //全return的情况不需要调用接收锁定
            List<OnlineInvoiceDetail> collect = onlineInvoiceDetails.stream().filter(onlineInvoiceDetail -> (CeeaWarehousingReturnDetailEnum.RECEIVE.getValue().equals(onlineInvoiceDetail.getType()))).collect(Collectors.toList());
//            if (CollectionUtils.isNotEmpty(collect)) {
//                baseResult = acceptPoLock(onlineInvoice, onlineInvoiceDetails, AcceptPoLockOperTypeEnum.CANCEL.name());
//            }
//            if (ResultCode.UNKNOWN_ERROR.getCode().equals(baseResult.getCode())) {
//                throw new BaseException(baseResult.getMessage());
//            }
            onlineInvoiceOutputParameters = (OnlineInvoiceOutputParameters) baseResult.getData();
            if (onlineInvoiceOutputParameters != null && onlineInvoiceOutputParameters.getXESBRESULTINFOREC() != null && "E".equals(onlineInvoiceOutputParameters.getXESBRESULTINFOREC().getRETURNSTATUS())) {
                throw new BaseException(onlineInvoiceOutputParameters.getXESBRESULTINFOREC().getRETURNMSG());
            }
        }
        return fsscResult;
    }

    @Override
    public OnlineInvoiceSaveDTO get(Long onlineInvoiceId) {
        OnlineInvoice onlineInvoice = this.getById(onlineInvoiceId);
        List<OnlineInvoiceDetail> onlineInvoiceDetails = iOnlineInvoiceDetailService.list(new QueryWrapper<>(new OnlineInvoiceDetail().setOnlineInvoiceId(onlineInvoiceId)));
        List<OnlineInvoicePunish> onlineInvoicePunishes = iOnlineInvoicePunishService.list(new QueryWrapper<>(new OnlineInvoicePunish().setOnlineInvoiceId(onlineInvoiceId)));
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = iOnlineInvoiceAdvanceService.list(new QueryWrapper<>(new OnlineInvoiceAdvance().setOnlineInvoiceId(onlineInvoiceId)));
        Fileupload fileupload = new Fileupload().setBusinessId(onlineInvoiceId);
        fileupload.setPageSize(100);
        List<Fileupload> fileuploads = fileCenterClient.listPage(fileupload, null).getList();
        OnlineInvoiceSaveDTO onlineInvoiceSaveDTO = new OnlineInvoiceSaveDTO();
        onlineInvoiceSaveDTO.setOnlineInvoiceDetails(onlineInvoiceDetails).setOnlineInvoicePunishes(onlineInvoicePunishes).setOnlineInvoiceAdvances(onlineInvoiceAdvances).setOnlineInvoice(onlineInvoice).setFileuploads(fileuploads);
        return onlineInvoiceSaveDTO;
    }

    @Override
    @Transactional
    public OnlineInvoiceSaveDTO audit(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO, String invoiceStatus) {
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        Long onlineInvoiceId = onlineInvoice.getOnlineInvoiceId();
        Assert.notNull(onlineInvoiceId, LocaleHandler.getLocaleMsg("onlineInvoiceId不能为空"));
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = onlineInvoiceSaveDTO.getOnlineInvoiceAdvances();
        List<OnlineInvoicePunish> onlineInvoicePunishes = onlineInvoiceSaveDTO.getOnlineInvoicePunishes();
        List<OnlineInvoiceDetail> onlineInvoiceDetails = onlineInvoiceSaveDTO.getOnlineInvoiceDetails();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        //审核前校验
        checkBeforeAudit(onlineInvoice);
//        adjustInvoiceAmount(onlineInvoiceDetails, onlineInvoice, onlineInvoicePunishes);//调整金额
        //审批通过时需要重新计算应付到账日期
        this.saveOrUpdateOnlineInvoice(invoiceStatus, onlineInvoice, loginAppUser);
//        this.updateById(onlineInvoice.setInvoiceStatus(invoiceStatus));
        //保存或更新预付款
        if (CollectionUtils.isNotEmpty(onlineInvoiceAdvances)) {
            for (OnlineInvoiceAdvance onlineInvoiceAdvance : onlineInvoiceAdvances) {
                if (onlineInvoiceAdvance == null) continue;
                if (onlineInvoiceAdvance.getOnlineInvoiceAdvanceId() == null) {
                    iOnlineInvoiceAdvanceService.save(onlineInvoiceAdvance
                            .setOnlineInvoiceAdvanceId(IdGenrator.generate()).setOnlineInvoiceId(onlineInvoice.getOnlineInvoiceId()));
                } else {
                    iOnlineInvoiceAdvanceService.updateById(onlineInvoiceAdvance);
                }
            }
        }
        //保存或更新扣罚
        saveOrUpdateOnlineInvoicePunishes(onlineInvoicePunishes, onlineInvoice);
        return onlineInvoiceSaveDTO;
    }

    private void checkBeforeAudit(OnlineInvoice onlineInvoice) {
        Assert.notNull(onlineInvoice, "onlineInvoice不能为空");
        if (InvoiceStatus.UNDER_APPROVAL.name().equals(onlineInvoice.getInvoiceStatus()) ||
                InvoiceStatus.REJECTED.name().equals(onlineInvoice.getInvoiceStatus())) {
            log.info("网上发票{}状态为新建或审批中，允许往下提交" , onlineInvoice.getOnlineInvoiceNum());
        }else{
            throw new BaseException(LocaleHandler.getLocaleMsg("单据状态为审批中或驳回时,才可审核通过"));
        }
    }

    @Override
    @Transactional
    public void buyerAbandon(Long onlineInvoiceId) {
        OnlineInvoice onlineInvoice = this.getById(onlineInvoiceId);
        List<OnlineInvoiceDetail> onlineInvoiceDetails = iOnlineInvoiceDetailService.list(new QueryWrapper<>(new OnlineInvoiceDetail().setOnlineInvoiceId(onlineInvoiceId)));
        this.updateById(new OnlineInvoice()
                .setOnlineInvoiceId(onlineInvoiceId)
                .setInvoiceStatus(InvoiceStatus.DROP.name()));
        //服务类 ToDo 需要加锁
        if (onlineInvoice != null && YesOrNo.YES.getValue().equals(onlineInvoice.getIsService())) {
            //恢复订单上的未开票数量
            restoreOnlineInvoiceDetail(onlineInvoiceDetails);
            //释放扣罚引用和修改考核状态
            List<OnlineInvoicePunish> onlineInvoicePunishes = iOnlineInvoicePunishService.list(new QueryWrapper<>(new OnlineInvoicePunish().setOnlineInvoiceId(onlineInvoice.getOnlineInvoiceId())));
            if (CollectionUtils.isNotEmpty(onlineInvoicePunishes)) {
                for (OnlineInvoicePunish onlineInvoicePunish : onlineInvoicePunishes) {
                    if (onlineInvoicePunish == null) continue;
                    performanceClient.modify(new VendorAssesForm()
                            .setVendorAssesId(onlineInvoicePunish.getVendorAssesId())
                            .setStatus(VendorAssesFormStatus.ASSESSED.getKey())
                            .setIfQuote(YesOrNo.NO.getValue()));
                }
            }

        }
        //非服务类
        if (onlineInvoice != null && YesOrNo.NO.getValue().equals(onlineInvoice.getIsService())) {
            //恢复开票通知数据
            for (OnlineInvoiceDetail onlineInvoiceDetail : onlineInvoiceDetails) {
                if (onlineInvoiceDetail == null) continue;
                Long invoiceDetailId = onlineInvoiceDetail.getInvoiceDetailId();//开票通知明细ID
                BigDecimal invoiceQuantity = onlineInvoiceDetail.getInvoiceQuantity() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getInvoiceQuantity();//网上开票明细本次开票数量
                /*查询开票通知对应明细数据*/
                InvoiceDetail invoiceDetail = iInvoiceDetailService.getById(invoiceDetailId);
                Integer lineNum = invoiceDetail.getLineNum();//订单行号
                BigDecimal receiveNum = invoiceDetail.getReceiveNum() == null ? BigDecimal.ZERO : invoiceDetail.getReceiveNum();//开票通知明细接收数量
                BigDecimal unitPriceContainingTax = invoiceDetail.getUnitPriceContainingTax() == null ? BigDecimal.ZERO : invoiceDetail.getUnitPriceContainingTax();//开票通知明细单价（含税)
                BigDecimal noTaxAmount = invoiceDetail.getNoTaxAmount() == null ? BigDecimal.ZERO : invoiceDetail.getNoTaxAmount();//开票通知明细净价金额
                BigDecimal notInvoiceQuantity = invoiceDetail.getNotInvoiceQuantity() == null ? BigDecimal.ZERO : invoiceDetail.getNotInvoiceQuantity();//开票通知明细未开票数量
                BigDecimal unitPriceExcludingTax = invoiceDetail.getUnitPriceExcludingTax() == null ? BigDecimal.ZERO : invoiceDetail.getUnitPriceExcludingTax();//开票通知明细单价(不含税)(订单单价)
                BigDecimal newNotInvoiceQuantity = notInvoiceQuantity.add(invoiceQuantity);//新开票通知明细未开票数量
                if (newNotInvoiceQuantity.compareTo(receiveNum) == 1) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("作废后,订单行号:" + lineNum + "的未开票数量大于接收数量,请联系管理员!"));
                }
                BigDecimal newInvoiceQuantity = newNotInvoiceQuantity;//开票通知默认未开票数量等于本次开票数量
                BigDecimal newNoTaxAmount = newNotInvoiceQuantity.multiply(unitPriceExcludingTax);//新开票通知明细本次开票净额
                BigDecimal newTaxAmount = newNotInvoiceQuantity.multiply(unitPriceContainingTax);//新开票通知明细含税金额
                BigDecimal newTax = newTaxAmount.subtract(newNoTaxAmount);//新开票通知明细税额
                iInvoiceDetailService.updateById(new InvoiceDetail()
                        .setInvoiceDetailId(invoiceDetailId)
                        .setNotInvoiceQuantity(newNotInvoiceQuantity)
                        .setInvoiceQuantity(newInvoiceQuantity)
                        .setNoTaxAmount(newNoTaxAmount)
                        .setTaxAmount(newTaxAmount)
                        .setTax(newTax));
            }

            //释放扣罚引用
            List<OnlineInvoicePunish> onlineInvoicePunishes = iOnlineInvoicePunishService.list(new QueryWrapper<>(new OnlineInvoicePunish().setOnlineInvoiceId(onlineInvoice.getOnlineInvoiceId())));
            if (CollectionUtils.isNotEmpty(onlineInvoicePunishes)) {
                for (OnlineInvoicePunish onlineInvoicePunish : onlineInvoicePunishes) {
                    if (onlineInvoicePunish == null) continue;
                    iInvoicePunishService.updateById(new InvoicePunish().setInvoicePunishId(onlineInvoicePunish.getInvoicePunishId())
                            .setIfQuote(YesOrNo.NO.getValue()));
                }
            }
        }
        /*隆基回迁,屏蔽第三方接口 modify by chensl26 2021-2-24*/
        //取消接收(Erp)
//        OnlineInvoiceOutputParameters onlineInvoiceOutputParameters = new OnlineInvoiceOutputParameters();
//        BaseResult baseResult = new BaseResult();
//        if (onlineInvoice != null && StringUtils.isNotBlank(onlineInvoice.getInstid()) && YesOrNo.NO.getValue().equals(onlineInvoice.getIsService())) {
//            //全return的情况不需要调用接收锁定
//            List<OnlineInvoiceDetail> collect = onlineInvoiceDetails.stream().filter(onlineInvoiceDetail -> (CeeaWarehousingReturnDetailEnum.RECEIVE.getValue().equals(onlineInvoiceDetail.getType()))).collect(Collectors.toList());
////            if (CollectionUtils.isNotEmpty(collect)) {
////                baseResult = acceptPoLock(onlineInvoice, onlineInvoiceDetails, AcceptPoLockOperTypeEnum.CANCEL.name());
////            }
////            if (ResultCode.UNKNOWN_ERROR.getCode().equals(baseResult.getCode())) {
////                //手动回滚
////                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
////                return baseResult;
////            }
//            onlineInvoiceOutputParameters = (OnlineInvoiceOutputParameters) baseResult.getData();
//            if (onlineInvoiceOutputParameters != null && onlineInvoiceOutputParameters.getXESBRESULTINFOREC() != null && "E".equals(onlineInvoiceOutputParameters.getXESBRESULTINFOREC().getRETURNSTATUS())) {
//                //手动回滚
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                baseResult.setCode(ResultCode.UNKNOWN_ERROR.getCode());
//                baseResult.setMessage(onlineInvoiceOutputParameters.getXESBRESULTINFOREC().getRETURNMSG());
//                return baseResult;
//            }
//            if (onlineInvoiceOutputParameters != null && onlineInvoiceOutputParameters.getXESBRESULTINFOREC() != null && "S".equals(onlineInvoiceOutputParameters.getXESBRESULTINFOREC().getRETURNSTATUS())) {
//                String instid = onlineInvoiceOutputParameters.getXESBRESULTINFOREC().getINSTID();
//                this.updateById(new OnlineInvoice().setOnlineInvoiceId(onlineInvoiceId).setInstid(instid));
//            }
//        }
//        baseResult.setCode(ResultCode.SUCCESS.getCode());
//        baseResult.setMessage(ResultCode.SUCCESS.getMessage());
//        baseResult.setData(onlineInvoiceId);
//        return baseResult;
    }

    private void restoreOnlineInvoiceDetail(List<OnlineInvoiceDetail> onlineInvoiceDetails) {
        //获取全部订单明细中的订单号
        Set<String> collect = onlineInvoiceDetails.stream().map(onlineInvoiceDetail -> (onlineInvoiceDetail.getOrderNumber())).collect(Collectors.toSet());
        List<Order> orders = iOrderService.list(Wrappers.lambdaQuery(Order.class).select(Order::getOrderId, Order::getOrderNumber).in(Order::getOrderNumber, collect));
        Map<String, Long> stringLongMap = orders.stream().collect(Collectors.toMap(Order::getOrderNumber, Order::getOrderId, (key1, key2) -> key2));
        Collection<Long> orderIds = stringLongMap.values();
        List<OrderDetail> orderDetails = iOrderDetailService.list(Wrappers.lambdaQuery(OrderDetail.class).in(OrderDetail::getOrderId, orderIds));
        Map<Long, List<OrderDetail>> longOrderDetailMap = orderDetails.stream().collect(Collectors.groupingBy(OrderDetail::getOrderId));
        List<OrderDetail> newOrderDetails = new ArrayList<>();
        for (OnlineInvoiceDetail onlineInvoiceDetail : onlineInvoiceDetails) {
            if (onlineInvoiceDetail == null) continue;
            BigDecimal noTaxAmount = onlineInvoiceDetail.getNoTaxAmount() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getNoTaxAmount();//净价金额
            BigDecimal invoiceQuantity = onlineInvoiceDetail.getInvoiceQuantity() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getInvoiceQuantity();//本次开票数量
            BigDecimal notInvoiceQuantity = onlineInvoiceDetail.getNotInvoiceQuantity() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getNotInvoiceQuantity();//未开票数量
            BigDecimal unitPriceExcludingTax = onlineInvoiceDetail.getUnitPriceExcludingTax() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getUnitPriceExcludingTax();//单价（不含税）(订单单价)
            String orderNumber = onlineInvoiceDetail.getOrderNumber();//订单号
            Integer lineNum = onlineInvoiceDetail.getLineNum();//订单行号
            Long orderId = stringLongMap.get(orderNumber);
            List<OrderDetail> orderDetails1 = longOrderDetailMap.get(orderId);
            OrderDetail orderDetail = orderDetails1.stream().filter(o -> (Integer.compare(o.getLineNum(), lineNum) == 0)).collect(Collectors.toList()).get(0);
            BigDecimal oldNotInvoiceQuantity = orderDetail.getNotInvoiceQuantity() == null ? BigDecimal.ZERO : orderDetail.getNotInvoiceQuantity();
            BigDecimal oldInvoiceQuantity = orderDetail.getInvoiceQuantity() == null ? BigDecimal.ZERO : orderDetail.getInvoiceQuantity();
            BigDecimal oldNoTaxAmount = orderDetail.getNoTaxAmount() == null ? BigDecimal.ZERO : orderDetail.getNoTaxAmount();
            BigDecimal receiveNum = orderDetail.getReceiveNum() == null ? BigDecimal.ZERO : orderDetail.getReceiveNum();
            BigDecimal newNotInvoiceQuantity = oldNotInvoiceQuantity.add(notInvoiceQuantity);
            BigDecimal newInvoiceQuantity = newNotInvoiceQuantity;
            BigDecimal newNoTaxAmount = newNotInvoiceQuantity.multiply(unitPriceExcludingTax);
            orderDetail.setNotInvoiceQuantity(newNotInvoiceQuantity).setInvoiceQuantity(newInvoiceQuantity).setNoTaxAmount(newNoTaxAmount);
            newOrderDetails.add(orderDetail);
            if (newNotInvoiceQuantity.compareTo(receiveNum) == 1) {
                throw new BaseException(LocaleHandler.getLocaleMsg("作废后,订单行号:" + lineNum + "的未开票数量大于接收数量,请联系管理员!"));
            }
        }
        iOrderDetailService.updateBatchById(newOrderDetails);
    }

    @Override
    @Transactional
    public void statusReturn(String boeNo, String invoiceStatus) {
        UpdateWrapper<OnlineInvoice> updateWrapper = new UpdateWrapper<>(new OnlineInvoice().setBoeNo(boeNo));
        this.update(new OnlineInvoice().setInvoiceStatus(invoiceStatus), updateWrapper);
        log.info("----------------------费控返回的状态已更新Nsrm对应单据状态----------------");
        OnlineInvoice onlineInvoice = this.getOne(new QueryWrapper<>(new OnlineInvoice().setBoeNo(boeNo)));
        if (onlineInvoice != null) {
            List<OnlineInvoiceDetail> onlineInvoiceDetails = iOnlineInvoiceDetailService.list(new QueryWrapper<>(new OnlineInvoiceDetail().setOnlineInvoiceId(onlineInvoice.getOnlineInvoiceId())));
            if (InvoiceStatus.REVIEWED.name().equals(invoiceStatus)) {
                //全return的情况不需要调用接收锁定取消
                List<OnlineInvoiceDetail> collect = onlineInvoiceDetails.stream().filter(onlineInvoiceDetail -> (CeeaWarehousingReturnDetailEnum.RECEIVE.getValue().equals(onlineInvoiceDetail.getType()))).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    //ToDo 添加返回错误状态捕获
//                    BaseResult baseResult = this.acceptPoLock(onlineInvoice, onlineInvoiceDetails, AcceptPoLockOperTypeEnum.CANCEL.name());
                }
                log.info("-------------------------费控已复核网上开票,且已取消ERP接收锁定-------------------------------");
            }
        }
    }

    @Override
    public void importStatusReturn(String boeNo, String importStatus) {
        UpdateWrapper<OnlineInvoice> updateWrapper = new UpdateWrapper<>(new OnlineInvoice().setBoeNo(boeNo));
        this.update(new OnlineInvoice().setImportStatus(importStatus), updateWrapper);
    }

    @Override
    @Transactional
    public BaseResult saveTemporaryBeforeAudit(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = onlineInvoiceSaveDTO.getOnlineInvoiceAdvances();
        List<OnlineInvoiceDetail> onlineInvoiceDetails = onlineInvoiceSaveDTO.getOnlineInvoiceDetails();
        List<OnlineInvoicePunish> onlineInvoicePunishes = onlineInvoiceSaveDTO.getOnlineInvoicePunishes();
        List<Fileupload> fileuploads = onlineInvoiceSaveDTO.getFileuploads();
        BaseResult baseResult = saveOrUpdateOnlineInvoiceSaveDTO(onlineInvoice.getInvoiceStatus(), onlineInvoice, onlineInvoiceDetails, onlineInvoiceAdvances, onlineInvoicePunishes, fileuploads);
        return baseResult;
    }

    @Override
    public void withdraw(Long onlineInvoiceId) {
        OnlineInvoice onlineInvoice = this.getById(onlineInvoiceId);
        this.updateById(onlineInvoice.setInvoiceStatus(InvoiceStatus.DRAFT.name()));
    }

    @Override
    public void export(OnlineInvoiceQueryDTO onlineInvoiceQueryDTO, HttpServletResponse response) throws Exception {
        List<OnlineInvoice> list = this.listPage(onlineInvoiceQueryDTO).getList();
        CompletableFuture<List<DictItemDTO>> cfInvoiceStatus = CompletableFuture.supplyAsync(() -> baseClient.listAllByParam(new DictItemDTO().setDictCode("INVOICE_STATUS")));
        CompletableFuture<List<DictItemDTO>> cfInvoiceImportStatus = CompletableFuture.supplyAsync(() -> baseClient.listAllByParam(new DictItemDTO().setDictCode("INVOICE_IMPORT_STATUS")));
        CompletableFuture<List<DictItemDTO>> cfBusinessType = CompletableFuture.supplyAsync(() -> baseClient.listAllByParam(new DictItemDTO().setDictCode("BUSINESS_TYPE")));
        CompletableFuture.allOf(cfInvoiceStatus, cfInvoiceImportStatus, cfBusinessType).join();


        List<DictItemDTO> invoiceStatusList = cfInvoiceStatus.get();
        List<DictItemDTO> invoiceImportStatusList = cfInvoiceImportStatus.get();
        List<DictItemDTO> businessTypeList = cfBusinessType.get();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ArrayList<OnlineInvoiceExcelDTO> onlineInvoiceExcelDTOS = new ArrayList<>();

        list.stream().forEach(x -> {
            OnlineInvoiceExcelDTO excelDTO = new OnlineInvoiceExcelDTO();
            BeanUtils.copyProperties(x, excelDTO);
            if (Objects.nonNull(x.getAccountPayableDealine())) {
                excelDTO.setAccountPayableDealine(x.getAccountPayableDealine().format(fmt));
            }
            excelDTO.setCreationDate(DateUtil.parseDateToStr(x.getCreationDate(), DateUtil.YYYY_MM_DD));
            excelDTO.setInvoiceStatus(getDicName(invoiceStatusList, x.getInvoiceStatus()));
            excelDTO.setImportStatus(getDicName(invoiceImportStatusList, x.getImportStatus()));
            excelDTO.setBusinessType(getDicName(businessTypeList, x.getBusinessType()));
            onlineInvoiceExcelDTOS.add(excelDTO);
        });
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "online_invoice");
        EasyExcelUtil.writeExcelWithModel(outputStream, "网上开票", onlineInvoiceExcelDTOS, OnlineInvoiceExcelDTO.class);
    }

    @Override
    @Transactional
    public void restart(List<Long> onlineInvoiceIds) {
        List<OnlineInvoice> onlineInvoices = this.listByIds(onlineInvoiceIds);
        List<Long> collect = onlineInvoices.stream().filter(Objects::nonNull).filter(onlineInvoice -> onlineInvoice.getOnlineInvoiceId() != null).map(OnlineInvoice::getOnlineInvoiceId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(collect)) {
            log.info("--------------------------开始执行重置金额----------------------------");
            /*供方开票时，需更新账期、及应付账款到期日为空*/
            OnlineInvoice byId = onlineInvoices.get(0);
            if (byId != null && byId.getOnlineInvoiceType().equals(OnlineInvoiceType.VENDOR_INVOICE.name())) {
                this.baseMapper.updateDealineAndPeriod(byId.getOnlineInvoiceId());
            }
            /*更新行调整标识为空字符串*/
            iOnlineInvoiceDetailService.updateChangeFlag(collect);
            /*更新发票行本次开票净价、含税单价*/
            iOnlineInvoiceDetailService.updateUnitPrice(collect);
            /*更新发票行含税金额及不含税金额*/
            iOnlineInvoiceDetailService.updateAmount(collect);
            /*更新发票行税额*/
            iOnlineInvoiceDetailService.updateTax(collect);
            /*更新对比结果*/
            iOnlineInvoiceDetailService.updateCompareResult(collect);
            log.info("---------------------------结束执行重置金额----------------------------");
        }
    }

    @Override
    public BaseResult batchAdjust(List<Long> onlineInvoiceIds) {
        log.info("---------------------开始批量调整金额----------------------------");
        List<OnlineInvoice> onlineInvoices = this.listByIds(onlineInvoiceIds);
        List<OnlineInvoiceDetail> onlineInvoiceDetails = iOnlineInvoiceDetailService.list(Wrappers.lambdaQuery(OnlineInvoiceDetail.class)
                .in(OnlineInvoiceDetail::getOnlineInvoiceId, onlineInvoiceIds));
        Map<Long, List<OnlineInvoiceDetail>> map = onlineInvoiceDetails.stream().collect(Collectors.groupingBy(OnlineInvoiceDetail::getOnlineInvoiceId));
        BaseResult baseResult = BaseResult.buildSuccess();
        int num = 1;
        for (OnlineInvoice onlineInvoice : onlineInvoices) {
            log.info("--------开始处理网上开票{} ， 票号内容为：{}" , onlineInvoice.getOnlineInvoiceNum() , JSONObject.toJSONString(onlineInvoice));
            OnlineInvoiceSaveDTO onlineInvoiceSaveDTO = new OnlineInvoiceSaveDTO();
            onlineInvoiceSaveDTO.setOnlineInvoice(onlineInvoice);
            List<OnlineInvoiceDetail> onlineInvoiceDetails1 = map.get(onlineInvoice.getOnlineInvoiceId());
            onlineInvoiceSaveDTO.setOnlineInvoiceDetails(onlineInvoiceDetails1);
            try {
                baseResult = batchAdjustAmount(onlineInvoiceSaveDTO);
            }catch (Exception e){
                e.printStackTrace();
                log.error("调整第"+num+"条数据网上发票推送有点问题",e);
            }
            if (ResultCode.SUCCESS.getCode().equals(baseResult.getCode())) {
                log.info("已成功调整第" + num + "条数据," + JsonUtil.entityToJsonStr(onlineInvoiceSaveDTO));
            }
            num ++;
        }

        log.info("---------------------结束批量调整金额-----------------------------");
        return baseResult;
    }

    @Override
    public OnlineInvoiceQueryDTO queryBatchOnlineInvoiceSaveDTO(List<Long> onlineInvoiceIds) {
        List<OnlineInvoice> onlineInvoices = this.listByIds(onlineInvoiceIds);
        List<OnlineInvoiceDetail> onlineInvoiceDetails = iOnlineInvoiceDetailService.list(Wrappers.lambdaQuery(OnlineInvoiceDetail.class)
                .in(OnlineInvoiceDetail::getOnlineInvoiceId, onlineInvoiceIds));
        Map<Long, List<OnlineInvoiceDetail>> mapOnlineInvoiceDetail = onlineInvoiceDetails.stream().collect(Collectors.groupingBy(OnlineInvoiceDetail::getOnlineInvoiceId));
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = iOnlineInvoiceAdvanceService.list(Wrappers.lambdaQuery(OnlineInvoiceAdvance.class).in(OnlineInvoiceAdvance::getOnlineInvoiceId, onlineInvoiceIds));
        Map<Long, List<OnlineInvoiceAdvance>> mapOnlineInvoiceAdvance = onlineInvoiceAdvances.stream().collect(Collectors.groupingBy(OnlineInvoiceAdvance::getOnlineInvoiceId));
        Map<Long, List<Fileupload>> mapFileupload = new HashMap<>();
        for (Long onlineInvoiceId : onlineInvoiceIds) {
            PageInfo<Fileupload> fileuploadPageInfo = fileCenterClient.listPage(new Fileupload().setBusinessId(onlineInvoiceId), "");
            List<Fileupload> list = fileuploadPageInfo.getList();
            mapFileupload.put(onlineInvoiceId, list);
        }
        OnlineInvoiceQueryDTO onlineInvoiceQueryDTO = new OnlineInvoiceQueryDTO();
        onlineInvoiceQueryDTO.setOnlineInvoices(onlineInvoices)
                .setMapOnlineInvoiceDetail(mapOnlineInvoiceDetail)
                .setMapOnlineInvoiceAdvance(mapOnlineInvoiceAdvance)
                .setMapFileuploads(mapFileupload);
        return onlineInvoiceQueryDTO;
    }

    @Override
    @Transactional
    public void batchRestart(List<Long> onlineInvoiceIds) {
        List<OnlineInvoice> onlineInvoices = this.listByIds(onlineInvoiceIds);
        List<Long> collect = onlineInvoices.stream().filter(Objects::nonNull).filter(onlineInvoice -> onlineInvoice.getOnlineInvoiceId() != null).map(OnlineInvoice::getOnlineInvoiceId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(collect)) {
            log.info("--------------------------开始执行重置金额----------------------------");
            /*供方开票时，需更新账期、及应付账款到期日为空*/
            /*if (byId.getOnlineInvoiceType().equals(OnlineInvoiceType.VENDOR_INVOICE.name())) {
                this.baseMapper.updateDealineAndPeriod(onlineInvoiceId);
            }*/
            /*更新行调整标识为空字符串*/
            iOnlineInvoiceDetailService.updateChangeFlag(collect);
            /*更新发票行本次开票净价、含税单价*/
            iOnlineInvoiceDetailService.updateUnitPrice(collect);
            /*更新发票行含税金额及不含税金额*/
            iOnlineInvoiceDetailService.updateAmount(collect);
            /*更新发票行税额*/
            iOnlineInvoiceDetailService.updateTax(collect);
            /*更新对比结果*/
            iOnlineInvoiceDetailService.updateCompareResult(collect);
            log.info("---------------------------结束执行重置金额----------------------------");
        }
    }



    private BaseResult batchAdjustAmount(OnlineInvoiceSaveDTO onlineInvoiceSaveDTO) {
        OnlineInvoice onlineInvoice = onlineInvoiceSaveDTO.getOnlineInvoice();
        List<OnlineInvoiceDetail> onlineInvoiceDetails = onlineInvoiceSaveDTO.getOnlineInvoiceDetails();
        String invoiceStatus = "";
        if (OnlineInvoiceType.BUYER_INVOICE.name().equals(onlineInvoice.getOnlineInvoiceType())) {
            invoiceStatus = InvoiceStatus.DRAFT.name();
        } else if (OnlineInvoiceType.VENDOR_INVOICE.name().equals(onlineInvoice.getOnlineInvoiceType())){
            invoiceStatus = InvoiceStatus.UNDER_APPROVAL.name();
        } else if (InvoiceStatus.REJECTED.name().equals(onlineInvoice.getInvoiceStatus())) {
            invoiceStatus = InvoiceStatus.REJECTED.name();
        }
        log.info("网上开票票号{}状态为{}", onlineInvoice.getOnlineInvoiceNum() , invoiceStatus);
        BaseResult baseResult = saveTemporary(onlineInvoiceSaveDTO, invoiceStatus);
//        BaseResult baseResult = adjustInvoiceAmount(onlineInvoiceDetails, onlineInvoice, null);
        return baseResult;
    }

    private void checkBeforeSubmit(OnlineInvoice onlineInvoice, List<OnlineInvoiceDetail> onlineInvoiceDetails, List<OnlineInvoiceAdvance> onlineInvoiceAdvances, List<OnlineInvoicePunish> onlineInvoicePunishes, List<Fileupload> fileuploads) {
        Assert.notNull(onlineInvoice, "onlineInvoice不能为空");
        if (InvoiceStatus.DRAFT.name().equals(onlineInvoice.getInvoiceStatus()) ||
                InvoiceStatus.UNDER_APPROVAL.name().equals(onlineInvoice.getInvoiceStatus()) ||
                InvoiceStatus.REJECTED.name().equals(onlineInvoice.getInvoiceStatus())) {
            log.info("网上发票{}状态为新建或审批中，允许往下提交" , onlineInvoice.getOnlineInvoiceNum());
        }else{
            throw new BaseException(LocaleHandler.getLocaleMsg("单据状态为新建时,才可提交"));
        }
    }

    public BaseResult  saveOrUpdateOnlineInvoiceSaveDTO(String invoiceStatus, OnlineInvoice onlineInvoice, List<OnlineInvoiceDetail> onlineInvoiceDetails, List<OnlineInvoiceAdvance> onlineInvoiceAdvances, List<OnlineInvoicePunish> onlineInvoicePunishes, List<Fileupload> fileuploads) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        //发票金额调整
        String onlineInvoiceType = onlineInvoice.getOnlineInvoiceType();
        //采购商才可保存调整
        BaseResult<List<OnlineInvoiceDetail>> baseResult = BaseResult.buildSuccess();
        List<OnlineInvoiceDetail> newOnlineInvoiceDetails = new ArrayList<>();
        //预付退回,虚拟发票开具时,开票明细为空,不需要进入此方法
        if (CollectionUtils.isNotEmpty(onlineInvoiceDetails)) {
            OnlineInvoiceDetail onlineInvoiceDetail = onlineInvoiceDetails.get(0);
            String poUnitPriceChangeFlag = onlineInvoiceDetail.getPoUnitPriceChangeFlag();
            //假如调整标识为空时,才进入调整方法
            if (StringUtils.isEmpty(poUnitPriceChangeFlag)
                    && UserType.BUYER.name().equals(loginAppUser.getUserType())) {
                /**/
                checkBeforeAdjustInvoiceAmount(onlineInvoiceDetails);
                baseResult = adjustInvoiceAmount(onlineInvoiceDetails, onlineInvoice, onlineInvoicePunishes);
                newOnlineInvoiceDetails = baseResult.getData();
                onlineInvoiceDetails = newOnlineInvoiceDetails;
            }
            //调整完金额,给调用模块返回金额校验异常.
            if(ResultCode.UNKNOWN_ERROR.getCode().equals(baseResult.getCode())){
                return baseResult;
            }
        }
        if (onlineInvoice != null) {
            saveOrUpdateOnlineInvoice(invoiceStatus, onlineInvoice, loginAppUser);
            saveOrUpdateOnlineInvoiceDetails(onlineInvoiceDetails, onlineInvoice);
            saveOrUpdateOnlineInvoicePunishes(onlineInvoicePunishes, onlineInvoice);
            saveOrUpdateOnlineInvoiceAdvances(onlineInvoiceAdvances, onlineInvoice);
            if (CollectionUtils.isNotEmpty(fileuploads)) {
                fileCenterClient.bindingFileupload(fileuploads, onlineInvoice.getOnlineInvoiceId());
            }
        }
        BaseResult<Long> baseResult2 = new BaseResult<>();
        BeanUtils.copyProperties(baseResult, baseResult2);
        baseResult2.setData(onlineInvoice.getOnlineInvoiceId());
        return baseResult2;
    }

    private void checkBeforeAdjustInvoiceAmount(List<OnlineInvoiceDetail> onlineInvoiceDetails) {

    }

    private void saveOrUpdateOnlineInvoiceAdvances(List<OnlineInvoiceAdvance> onlineInvoiceAdvances, OnlineInvoice onlineInvoice) {
        if (CollectionUtils.isNotEmpty(onlineInvoiceAdvances)) {
            for (OnlineInvoiceAdvance onlineInvoiceAdvance : onlineInvoiceAdvances) {
                if (onlineInvoiceAdvance == null) continue;
                if (onlineInvoiceAdvance.getOnlineInvoiceAdvanceId() == null) {
                    onlineInvoiceAdvance.setOnlineInvoiceAdvanceId(IdGenrator.generate())
                            .setOnlineInvoiceId(onlineInvoice.getOnlineInvoiceId());
//                            .setTotalChargeOffAmount(onlineInvoiceAdvance.getTotalChargeOffAmount().add(onlineInvoiceAdvance.getChargeOffAmount()))
                    iOnlineInvoiceAdvanceService.save(onlineInvoiceAdvance);
                } else {
//                    OnlineInvoiceAdvance byId = iOnlineInvoiceAdvanceService.getById(onlineInvoiceAdvance.getOnlineInvoiceAdvanceId());
//                    BigDecimal oldChargeOffAmount = byId.getChargeOffAmount();
//                    BigDecimal oldTotalChargeOffAmount = byId.getTotalChargeOffAmount();
//                    BigDecimal newTotalChargeOffAmount = oldTotalChargeOffAmount.subtract(oldChargeOffAmount);
//                    onlineInvoiceAdvance.setTotalChargeOffAmount(newTotalChargeOffAmount.add(onlineInvoiceAdvance.getChargeOffAmount()));
                    iOnlineInvoiceAdvanceService.updateById(onlineInvoiceAdvance);
                }
//                //设置预付款引用标记
//                if (OnlineInvoiceType.BUYER_INVOICE.name().equals(onlineInvoice.getOnlineInvoiceType())) {
//                    pmClient.setQuote(onlineInvoiceAdvance.getAdvanceApplyHeadId(), YesOrNo.YES.getValue());
//                }
            }
        }
    }

    private void saveOrUpdateOnlineInvoicePunishes(List<OnlineInvoicePunish> onlineInvoicePunishes, OnlineInvoice onlineInvoice) {
        if (CollectionUtils.isNotEmpty(onlineInvoicePunishes)) {
            for (OnlineInvoicePunish onlineInvoicePunish : onlineInvoicePunishes) {
                if (onlineInvoicePunish == null) continue;
                if (onlineInvoicePunish.getOnlineInvoicePunishId() == null) {
                    onlineInvoicePunish.setOnlineInvoicePunishId(IdGenrator.generate())
                            .setOnlineInvoiceId(onlineInvoice.getOnlineInvoiceId());
                    iOnlineInvoicePunishService.save(onlineInvoicePunish);
                } else {
                    iOnlineInvoicePunishService.updateById(onlineInvoicePunish);
                }
                //设置扣罚引用或修改供应商考核状态
                updateVendorPunishStatus(onlineInvoice, onlineInvoicePunish);
            }
        }
    }

    private void updateVendorPunishStatus(OnlineInvoice onlineInvoice, OnlineInvoicePunish onlineInvoicePunish) {
        //服务类
        if (YesOrNo.YES.getValue().equals(onlineInvoice.getIsService())) {
            if (InvoiceStatus.DRAFT.name().equals(onlineInvoice.getInvoiceStatus())) {
                performanceClient.modify(new VendorAssesForm()
                        .setVendorAssesId(onlineInvoicePunish.getVendorAssesId())
                        .setIfQuote(YesOrNo.YES.getValue()));
            }
            if (InvoiceStatus.APPROVAL.name().equals(onlineInvoice.getInvoiceStatus())) {
                performanceClient.modify(new VendorAssesForm()
                        .setVendorAssesId(onlineInvoicePunish.getVendorAssesId())
                        .setIfQuote(YesOrNo.YES.getValue())
                        .setStatus(VendorAssesFormStatus.SETTLED.getKey()));
            }
            if (InvoiceStatus.UNDER_APPROVAL.name().equals(onlineInvoice.getInvoiceStatus())) {
                performanceClient.modify(new VendorAssesForm()
                        .setVendorAssesId(onlineInvoicePunish.getVendorAssesId())
                        .setIfQuote(YesOrNo.YES.getValue()));
            }
        }
        //非服务类
        if (YesOrNo.NO.getValue().equals(onlineInvoice.getIsService())) {
            if (InvoiceStatus.DRAFT.name().equals(onlineInvoice.getInvoiceStatus())) {
                iInvoicePunishService.updateById(new InvoicePunish()
                        .setInvoicePunishId(onlineInvoicePunish.getInvoicePunishId())
                        .setIfQuote(YesOrNo.YES.getValue()));
            }
            if (InvoiceStatus.APPROVAL.name().equals(onlineInvoice.getInvoiceStatus())) {
                iInvoicePunishService.updateById(new InvoicePunish()
                        .setInvoicePunishId(onlineInvoicePunish.getInvoicePunishId())
                        .setIfQuote(YesOrNo.YES.getValue()));
            }
            if (InvoiceStatus.UNDER_APPROVAL.name().equals(onlineInvoice.getInvoiceStatus())) {
                iInvoicePunishService.updateById(new InvoicePunish()
                        .setInvoicePunishId(onlineInvoicePunish.getInvoicePunishId())
                        .setIfQuote(YesOrNo.YES.getValue()));
            }
        }
    }

    private void saveOrUpdateOnlineInvoiceDetails(List<OnlineInvoiceDetail> onlineInvoiceDetails, OnlineInvoice onlineInvoice) {
        if (CollectionUtils.isNotEmpty(onlineInvoiceDetails)) {
//            adjustInvoiceAmount(onlineInvoiceDetails, onlineInvoice);//发票金额调整(放在上个方法操作)
            for (OnlineInvoiceDetail onlineInvoiceDetail : onlineInvoiceDetails) {
                if (onlineInvoiceDetail == null) continue;
                //写入合同编号
                if (Objects.nonNull(onlineInvoiceDetail.getContractNo())) {
                    onlineInvoice.setContractCode(onlineInvoiceDetail.getContractNo());
                }
                if (onlineInvoiceDetail.getOnlineInvoiceDetailId() == null) {
                    if (InvoiceStatus.DRAFT.name().equals(onlineInvoice.getInvoiceStatus())) {
                        Long invoiceDetailId = onlineInvoiceDetail.getInvoiceDetailId();
                        BigDecimal invoiceQuantity = onlineInvoiceDetail.getInvoiceQuantity() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getInvoiceQuantity();//本次开票数量
//                        BigDecimal notInvoiceQuantity = onlineInvoiceDetail.getNotInvoiceQuantity() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getNotInvoiceQuantity();//未开票数量
                        BigDecimal unitPriceExcludingTax = onlineInvoiceDetail.getUnitPriceExcludingTax() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getUnitPriceExcludingTax();//单价（不含税）(订单单价)
                        BigDecimal unitPriceContainingTax = onlineInvoiceDetail.getUnitPriceContainingTax() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getUnitPriceContainingTax();//单价（含税)
                        onlineInvoiceDetail.setOnlineInvoiceDetailId(IdGenrator.generate())
                                .setOnlineInvoiceId(onlineInvoice.getOnlineInvoiceId());
//                        BigDecimal noTaxAmount = invoiceQuantity.multiply(unitPriceExcludingTax);//净价金额(本次)
//                        onlineInvoiceDetail.setNoTaxAmount(noTaxAmount);
//                        BigDecimal taxAmount = invoiceQuantity.multiply(unitPriceContainingTax);//含税金额
//                        onlineInvoiceDetail.setTaxAmount(taxAmount);
//                        BigDecimal tax = taxAmount.subtract(noTaxAmount);
//                        onlineInvoiceDetail.setTax(tax);
                        //回写开票通知开票数量或者回写采购订单开票数量(应该是在保存时回写,不应该创建时回写) ToDo 需要加锁

                        checkAndReduceNotInvoiceQuantity(onlineInvoice, onlineInvoiceDetail, invoiceDetailId, invoiceQuantity);
                    }
                    iOnlineInvoiceDetailService.save(onlineInvoiceDetail);
                } else {
                    iOnlineInvoiceDetailService.updateById(onlineInvoiceDetail);
                }
            }
        }
    }

    private synchronized void checkAndReduceNotInvoiceQuantity(OnlineInvoice onlineInvoice, OnlineInvoiceDetail onlineInvoiceDetail, Long invoiceDetailId, BigDecimal invoiceQuantity) {
        if (YesOrNo.YES.getValue().equals(onlineInvoice.getIsService())) {//服务类回写采购订单
            OrderDetail byId = iOrderDetailService.getById(onlineInvoiceDetail.getOrderDetailId());
            BigDecimal notInvoiceQuantity = BigDecimal.ZERO;
            if (byId != null) {
                notInvoiceQuantity = byId.getNotInvoiceQuantity() == null ? BigDecimal.ZERO : byId.getNotInvoiceQuantity();
                int r = notInvoiceQuantity.compareTo(BigDecimal.ZERO);
                if (r == 0) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("未开票数量不足,请检查!"));
                }
            }
            iOrderDetailService.updateById(new OrderDetail()
                    .setOrderDetailId(onlineInvoiceDetail.getOrderDetailId())
                    .setInvoiceQuantity(notInvoiceQuantity.subtract(invoiceQuantity))//本次开票数量
                    .setNotInvoiceQuantity(notInvoiceQuantity.subtract(invoiceQuantity)));//未开票数量
//                                    .setNoTaxAmount((notInvoiceQuantity.subtract(invoiceQuantity)).multiply(unitPriceExcludingTax)));//净价金额,每次都要回写未开票数量*不含税单价
        }
        if (YesOrNo.NO.getValue().equals(onlineInvoice.getIsService())) {//非服务类回写开票通知  ToDo 需要加锁
            InvoiceDetail byId = iInvoiceDetailService.getById(onlineInvoiceDetail.getInvoiceDetailId());
            BigDecimal notInvoiceQuantity = BigDecimal.ZERO;
            if (byId != null) {
                notInvoiceQuantity = byId.getNotInvoiceQuantity() == null ? BigDecimal.ZERO : byId.getNotInvoiceQuantity();
                int r = notInvoiceQuantity.compareTo(BigDecimal.ZERO);
                if (r == 0) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("未开票数量不足,请检查!"));
                }
            }
            BigDecimal newNotInvoiceQuantity = notInvoiceQuantity.subtract(invoiceQuantity);
//                            BigDecimal taxAmount = newNotInvoiceQuantity.multiply(unitPriceContainingTax);
//                            BigDecimal noTaxAmount = newNotInvoiceQuantity.multiply(unitPriceExcludingTax);
            iInvoiceDetailService.updateById(new InvoiceDetail()
                    .setInvoiceDetailId(invoiceDetailId)
                    .setInvoiceQuantity(newNotInvoiceQuantity)
                    .setNotInvoiceQuantity(newNotInvoiceQuantity));
//                                    .setTaxAmount(newNotInvoiceQuantity.multiply(unitPriceContainingTax))
//                                    .setTax(taxAmount.subtract(noTaxAmount))
//                                    .setNoTaxAmount(noTaxAmount));//净价金额,每次都要回写未开票数量*不含税单价
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResult adjustInvoiceAmount(List<OnlineInvoiceDetail> onlineInvoiceDetails, OnlineInvoice onlineInvoice, List<OnlineInvoicePunish> onlineInvoicePunishes) {
        BaseResult<List<OnlineInvoiceDetail>> baseResult = BaseResult.buildSuccess();
        baseResult.setData(onlineInvoiceDetails);
        if (CollectionUtils.isNotEmpty(onlineInvoiceDetails)) {
            if(Objects.isNull(onlineInvoice)){
                log.error("理论上不该出现的问题。网上发票内容居然为空!!!!!!!!!!!!!!!!");
            }
            BigDecimal actualInvoiceAmountN = onlineInvoice.getActualInvoiceAmountN() == null ? BigDecimal.ZERO : onlineInvoice.getActualInvoiceAmountN();//实际发票净额
            BigDecimal taxTotalAmount = onlineInvoice.getTaxTotalAmount() == null ? BigDecimal.ZERO : onlineInvoice.getTaxTotalAmount();//含税金额(系统)
            BigDecimal totalTax = onlineInvoice.getTotalTax() == null ? BigDecimal.ZERO : onlineInvoice.getTotalTax();//税额(系统)
            BigDecimal actualInvoiceAmountY = onlineInvoice.getActualInvoiceAmountY() == null ? BigDecimal.ZERO : onlineInvoice.getActualInvoiceAmountY();//发票总额
            BigDecimal noTaxTotalAmount = taxTotalAmount.subtract(totalTax);//不含税金额(系统)
            BigDecimal differenceTotalAmount = BigDecimal.ZERO;
            //非服务类差异金额,使用不含税金额作比较
            if (YesOrNo.NO.getValue().equals(onlineInvoice.getIsService())) {
                differenceTotalAmount = actualInvoiceAmountN.subtract(noTaxTotalAmount);//非服务类差异总金额
            }
            if (YesOrNo.YES.getValue().equals(onlineInvoice.getIsService())) {
                differenceTotalAmount = actualInvoiceAmountY.subtract(taxTotalAmount);//服务类差异总金额
            }
            log.info("---------------------开始调整发票金额-" +
                            "实际发票金额:{};含税金额:{};含税金额（系统）:{} ; 税额(系统):{} ; 不含税金额(系统):{} ; 差异总金额:{}；发票净额：{} " ,
                    onlineInvoice.getActualInvoiceAmountY() ,
                    taxTotalAmount,
                    taxTotalAmount ,
                    totalTax ,
                    noTaxTotalAmount,
                    differenceTotalAmount,
                    actualInvoiceAmountN);
            //非服务类金额调整
            if (YesOrNo.NO.getValue().equals(onlineInvoice.getIsService())) {
                if (noServiceAdjust(onlineInvoiceDetails, onlineInvoice, baseResult, differenceTotalAmount))
                    return baseResult;
            }
            //服务类金额调整
            if (YesOrNo.YES.getValue().equals(onlineInvoice.getIsService())
                    && onlineInvoice.getTaxTotalAmount().compareTo(onlineInvoice.getActualInvoiceAmountY()) != 0) {
                if (serviceAdjust(onlineInvoiceDetails, onlineInvoice, baseResult, differenceTotalAmount)) {
                    return baseResult;
                }
            }
            //最后把全部数据没调整的设置为N
            onlineInvoiceDetails.forEach(onlineInvoiceDetail -> {
                if (!onlineInvoiceDetail.getPoUnitPriceChangeFlag().equals(YesOrNo.YES.getValue())) {
                    onlineInvoiceDetail.setPoUnitPriceChangeFlag(YesOrNo.NO.getValue());
                }
            });
        }
        return baseResult;
        //需求修改,不需要调整系统含税金额
//        onlineInvoice.setTotalTax(onlineInvoice.getInvoiceTax())
//                .setTaxTotalAmount(onlineInvoice.getActualInvoiceAmountY())
//                .setUnPaidAmount(onlineInvoice.getActualInvoiceAmountY());
    }

    private boolean serviceAdjust(List<OnlineInvoiceDetail> onlineInvoiceDetails, OnlineInvoice onlineInvoice, BaseResult<List<OnlineInvoiceDetail>> baseResult, BigDecimal differenceTotalAmount) {
        //相等的情况,可以直接跳过调整,同时设置不需调整标识
        Boolean ifAdjust = true;
        if (BigDecimal.ZERO.compareTo(differenceTotalAmount) == 0) {
            ifAdjust = false;
        }
        if (BigDecimal.ZERO.compareTo(differenceTotalAmount) != 0) {
            if (CollectionUtils.isNotEmpty(onlineInvoiceDetails)) {
                for (OnlineInvoiceDetail onlineInvoiceDetail : onlineInvoiceDetails) {
                    log.info("服务类发票调整金额");
                    BigDecimal adjustAmount = BigDecimal.ZERO;
                    //调整金额等于0情况
                    if (BigDecimal.ZERO.compareTo(differenceTotalAmount) == 0){
                        break;
                    }
                    //服务类调整金额小于0情况
                    adjustAmount = serviceLessThanZeroForNo(differenceTotalAmount, onlineInvoiceDetail, adjustAmount);
                    //服务类调整金额大于0情况
                    adjustAmount = serviceGreaterThanZeroForNo(differenceTotalAmount, onlineInvoiceDetail, adjustAmount);
                    differenceTotalAmount = adjustAmount;
                }
            }
        }
        //最后把全部数据没调整的设置为N
        onlineInvoiceDetails.forEach(onlineInvoiceDetail -> {
            if (!onlineInvoiceDetail.getPoUnitPriceChangeFlag().equals(YesOrNo.YES.getValue())) {
                onlineInvoiceDetail.setPoUnitPriceChangeFlag(YesOrNo.NO.getValue());
            }
        });
        log.info("--------------------------------调整后的网上开票明细:" + JsonUtil.entityToJsonStr(onlineInvoiceDetails));

        BigDecimal sumTaxAmount = onlineInvoiceDetails.stream().map(x ->
                new BigDecimal(StringUtil.StringValue(x.getTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
        //当只有一行时,就只取一行中的最大值净额
        BigDecimal maxTaxAmount = BigDecimal.ZERO;
        if (onlineInvoiceDetails.size() > 1) {
            maxTaxAmount = onlineInvoiceDetails.stream().map(OnlineInvoiceDetail::getTaxAmount).max((x1, x2) -> x1.compareTo(x2)).get();
        } else if (onlineInvoiceDetails.size() == 1) {
            maxTaxAmount = onlineInvoiceDetails.get(0).getTaxAmount();
        }
        BigDecimal actualInvoiceAmountN = onlineInvoice.getActualInvoiceAmountN();
        log.info("--------maxTaxAmount:" + maxTaxAmount + "--------------------------" +
                "sumTaxAmount:" + sumTaxAmount + "------------------------" +
                "actualInvoiceAmountN:" + actualInvoiceAmountN);
        BigDecimal actualInvoiceAmountY = onlineInvoice.getActualInvoiceAmountY() == null ? BigDecimal.ZERO : onlineInvoice.getActualInvoiceAmountY();
        actualInvoiceAmountY = actualInvoiceAmountY.setScale(2, BigDecimal.ROUND_HALF_UP);
        /*把最后的尾差放到金额最大中*/
        BigDecimal lastDifferentTaxAmount = actualInvoiceAmountY.subtract(sumTaxAmount);//不含税差额
        log.info("--------------含税差额:" + lastDifferentTaxAmount);
        if (lastDifferentTaxAmount.compareTo(BigDecimal.ZERO) != 0) {
            for (OnlineInvoiceDetail newOnlineInvoiceDetail : onlineInvoiceDetails) {
                BigDecimal newTaxAmount = newOnlineInvoiceDetail.getTaxAmount().add(lastDifferentTaxAmount);
                if (newOnlineInvoiceDetail.getTaxAmount().compareTo(maxTaxAmount) == 0) {
                    newOnlineInvoiceDetail
                            .setUnitPriceContainingTax(newTaxAmount.divide(newOnlineInvoiceDetail.getInvoiceQuantity(), 8, BigDecimal.ROUND_HALF_UP))
                            .setTaxAmount(newTaxAmount);
                    break;
                }
            }
        }
        /*最后尾差处理后的新汇总不含税金额*/
        BigDecimal newSumNoTaxAmount = onlineInvoiceDetails.stream().map(x ->
                new BigDecimal(StringUtil.StringValue(x.getNoTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
        /*最后尾差处理后的新汇总含税金额*/
        BigDecimal newSumTaxAmount = onlineInvoiceDetails.stream().map(x ->
                new BigDecimal(StringUtil.StringValue(x.getTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
        actualInvoiceAmountY = onlineInvoice.getActualInvoiceAmountY() == null ? BigDecimal.ZERO : onlineInvoice.getActualInvoiceAmountY();
        actualInvoiceAmountY = actualInvoiceAmountY.setScale(2, BigDecimal.ROUND_HALF_UP);
        log.info("newSumNoTaxAmount:" + newSumNoTaxAmount + "------------------actualInvoiceAmountN:" + actualInvoiceAmountN);
        log.info("newSumTaxAmount:" + newSumTaxAmount + "------------------actualInvoiceAmountY:" + actualInvoiceAmountY);
        baseResult.setData(onlineInvoiceDetails);
        if (newSumTaxAmount.compareTo(actualInvoiceAmountY) != 0 && ifAdjust) {
            baseResult.setCode(ResultCode.UNKNOWN_ERROR.getCode());
            log.error("调整后的总计行含税金额不等于实际开票总额,请检查!");
            baseResult.setMessage(LocaleHandler.getLocaleMsg("调整后的总计行含税金额不等于实际开票总额,请检查!"));
            return true;
        }
        return false;
    }

    private BigDecimal serviceGreaterThanZeroForNo(BigDecimal differenceTotalAmount, OnlineInvoiceDetail onlineInvoiceDetail, BigDecimal adjustAmount) {
        if (differenceTotalAmount.compareTo(BigDecimal.ZERO) == 1) {
            adjustAmount = differenceTotalAmount;//差异金额等于可调整金额
            serviceCalculateData(adjustAmount, onlineInvoiceDetail);
            adjustAmount = BigDecimal.ZERO;
        }
        log.info("单号{} 差异金额剩余{}:====================================>", JSONObject.toJSONString(onlineInvoiceDetail) , adjustAmount);
        return adjustAmount;
    }

    private BigDecimal serviceLessThanZeroForNo(BigDecimal differenceTotalAmount, OnlineInvoiceDetail onlineInvoiceDetail, BigDecimal adjustAmount) {
        if (differenceTotalAmount.compareTo(BigDecimal.ZERO) == -1) {
            BigDecimal taxAmount = onlineInvoiceDetail.getTaxAmount();
            //特殊情况:开票净额小于或等于差异金额,直接置为0
            if (taxAmount.abs().compareTo(differenceTotalAmount.abs()) < 1) {
                serviceCalculateDatasBySetZero(onlineInvoiceDetail, taxAmount);
                //开票净额小于差异金额
                if (taxAmount.abs().compareTo(differenceTotalAmount.abs()) == -1) {
                    adjustAmount = differenceTotalAmount.add(taxAmount);
                }
                //开票净额等于差异金额
                if (taxAmount.abs().compareTo(differenceTotalAmount.abs()) == 0) {
                    adjustAmount = BigDecimal.ZERO;
                }
            }
            //正常调整,开票净额大于差异金额
            if (taxAmount.abs().compareTo(differenceTotalAmount.abs()) == 1) {
                adjustAmount = differenceTotalAmount;//差异金额等于本次的调整金额
                serviceCalculateData(adjustAmount, onlineInvoiceDetail);
                adjustAmount = BigDecimal.ZERO;
            }
//            //正常调整,开票净额小于差异金额
//            if (noTaxAmount.compareTo(differenceTotalAmount.abs()) == -1) {
//                BigDecimal adjustAmount = onlineInvoiceDetail.getNoTaxAmount().negate();
//                calculateData(adjustAmount, onlineInvoiceDetail);
//                differenceTotalAmount = differenceTotalAmount.subtract(adjustAmount);
//            }
        }
        log.info("单号{} ，差异金额剩余:{}====================================>" ,JSONObject.toJSONString(onlineInvoiceDetail) , adjustAmount);
        return adjustAmount;
    }

    private BigDecimal serviceCalculateData(BigDecimal adjustAmount, OnlineInvoiceDetail onlineInvoiceDetail) {
        BigDecimal invoiceQuantity = onlineInvoiceDetail.getInvoiceQuantity();//本次开票数量
        BigDecimal unitPriceExcludingTax = onlineInvoiceDetail.getUnitPriceExcludingTax();//单价（含税,本次开票净价)
        BigDecimal compareResult = adjustAmount.divide(invoiceQuantity, 2, BigDecimal.ROUND_HALF_UP);//对比结果(可调整不含税单价)
        onlineInvoiceDetail.setCompareResult(StringUtil.StringValue(compareResult))
                .setPoUnitPriceChangeFlag(adjustAmount.compareTo(BigDecimal.ZERO) == 0 ? YesOrNo.NO.getValue() : YesOrNo.YES.getValue());//设置对比结果
        BigDecimal newTaxAmount = adjustAmount.add(onlineInvoiceDetail.getTaxAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);//调整后的含税金额
        BigDecimal newUnitPriceContainingTax = newTaxAmount.divide(invoiceQuantity, 8, BigDecimal.ROUND_HALF_UP);//调整后的含税单价
        onlineInvoiceDetail.setUnitPriceContainingTax(newUnitPriceContainingTax);
        BigDecimal taxRate = onlineInvoiceDetail.getTaxRate().divide(new BigDecimal("100"));
        BigDecimal newNoTaxAmount = newTaxAmount.divide((BigDecimal.ONE.add(taxRate)), 2, BigDecimal.ROUND_HALF_UP);//调整后的开票净额
        BigDecimal newTax = newTaxAmount.subtract(newNoTaxAmount);//调整后的开票净额
        BigDecimal newUnitPriceExcludingTax = newNoTaxAmount.divide(invoiceQuantity, 8, BigDecimal.ROUND_HALF_UP);//调整后的开票净价
        onlineInvoiceDetail.setNoTaxAmount(newNoTaxAmount).setTax(newTax).setTaxAmount(newTaxAmount).setUnitPriceExcludingTax(newUnitPriceExcludingTax);
        return adjustAmount;
    }

    private void serviceCalculateDatasBySetZero(OnlineInvoiceDetail onlineInvoiceDetail, BigDecimal taxAmount) {
        onlineInvoiceDetail.setUnitPriceExcludingTax(BigDecimal.ZERO);//单价（含税,本次开票净价)
        onlineInvoiceDetail.setNoTaxAmount(BigDecimal.ZERO);//净价金额
        onlineInvoiceDetail.setTax(BigDecimal.ZERO);//税额
        onlineInvoiceDetail.setUnitPriceContainingTax(BigDecimal.ZERO);//含税单价
        onlineInvoiceDetail.setTaxAmount(BigDecimal.ZERO);//含税金额
        BigDecimal invoiceQuantity = onlineInvoiceDetail.getInvoiceQuantity();
        BigDecimal compareResult = taxAmount.negate().divide(invoiceQuantity, 4, BigDecimal.ROUND_HALF_UP);
        onlineInvoiceDetail.setCompareResult(StringUtil.StringValue(compareResult))
                .setPoUnitPriceChangeFlag(compareResult.compareTo(BigDecimal.ZERO) == 0 ? YesOrNo.NO.getValue() : YesOrNo.YES.getValue());//设置对比结果
    }

    private boolean noServiceAdjust(List<OnlineInvoiceDetail> onlineInvoiceDetails, OnlineInvoice onlineInvoice, BaseResult<List<OnlineInvoiceDetail>> baseResult, BigDecimal differenceTotalAmount) {
        //特殊场景:采购订单编号,行号,接收编号相同的明细行
        //(符合场景的算法公式:
        // 1.先汇总组内行开票净额+剩余的调整差异金额=等于组内调整后的总开票净额(存起来)
        // 2.剩余的调整差异金额÷汇总的开票数量=调整差异净价
        // 3.每行开票净价+调整差异净价=调整后开票净价
        // 4.行内每行更新为调整后的开票净价
        // 5.重新用新的开票净价,算出组内总的开票净额
        // 6.用存起来的值与新的总开票净额对比,还有尾差的话,直接把差异调整到组内第一行的净价金额和含税金额)
        Map<String, List<OnlineInvoiceDetail>> collect = onlineInvoiceDetails.stream()
                .filter(onlineInvoiceDetail -> (StringUtils.isNotBlank(onlineInvoiceDetail.getOrderNumber()) && !ObjectUtils.isEmpty(onlineInvoiceDetail.getLineNum()) && StringUtils.isNotBlank(onlineInvoiceDetail.getReceiveOrderNo())))
                .collect(Collectors.groupingBy(onlineInvoiceDetail -> onlineInvoiceDetail.getOrderNumber() + onlineInvoiceDetail.getReceiveOrderNo() + onlineInvoiceDetail.getLineNum()));
        Collection<List<OnlineInvoiceDetail>> values = collect.values();
        List<List<OnlineInvoiceDetail>> nolists = values.stream().filter(list -> (list.size() < 2)).collect(Collectors.toList());//不符合特殊场景
        List<List<OnlineInvoiceDetail>> yeslists = values.stream().filter(list -> (list.size() > 1)).collect(Collectors.toList());//符合特殊场景
        List<OnlineInvoiceDetail> noList = new ArrayList<>();
        List<OnlineInvoiceDetail> yesList = new ArrayList<>();
        List<OnlineInvoiceDetail> newOnlineInvoiceDetails = new ArrayList<>();
        List<OnlineInvoiceDetail> allOnlineInvoiceDetails = new ArrayList<>();
        //相等的情况,可以直接跳过调整,同时设置不需调整标识
        Boolean ifAdjust = true;
        if (BigDecimal.ZERO.compareTo(differenceTotalAmount) == 0) {
            newOnlineInvoiceDetails = onlineInvoiceDetails;
            ifAdjust = false;
        }
        if (BigDecimal.ZERO.compareTo(differenceTotalAmount) != 0) {
            //1.先调整不符合场景的明细
            if (CollectionUtils.isNotEmpty(nolists)) {
                nolists.forEach(o -> {
                    noList.add(o.get(0));
                });
                noList.sort((o1, o2) -> Integer.compare(o1.getInvoiceRow(), o2.getInvoiceRow()));//排序
                for (OnlineInvoiceDetail onlineInvoiceDetail : noList) {
                    log.info("调整不符合场景的明细");
                    BigDecimal adjustAmount = BigDecimal.ZERO;
                    //调整金额等于0情况
                    if (BigDecimal.ZERO.compareTo(differenceTotalAmount) == 0){
                        break;
                    }
                    //调整金额小于0情况
                    adjustAmount = lessThanZeroForNo(differenceTotalAmount, onlineInvoiceDetail, adjustAmount);
                    //调整金额大于0情况
                    adjustAmount = greaterThanZeroForNo(differenceTotalAmount, onlineInvoiceDetail, adjustAmount);
                    differenceTotalAmount = adjustAmount;
                }
            }
            //2.再调整符合场景的明细
            if (CollectionUtils.isNotEmpty(yeslists)) {
                //各个组合循环
                for (List<OnlineInvoiceDetail> list : yeslists) {
                    BigDecimal adjustAmount = BigDecimal.ZERO;
                    //调整金额等于0情况,直接中断当前循环
                    if (differenceTotalAmount.compareTo(BigDecimal.ZERO) == 0) break;
                    //调整金额小于0情况
                    adjustAmount = lessThanZeroForYes(differenceTotalAmount, list, adjustAmount);
                    log.info("再调整符合场景的明细-调整金额小于0情况；adjustAmount={}",adjustAmount);
                    //调整金额大于0情况
                    adjustAmount = greaterThanZeroForYes(differenceTotalAmount, list, adjustAmount);
                    log.info("再调整符合场景的明细-调整金额大于0情况；adjustAmount={}",adjustAmount);
                    differenceTotalAmount = adjustAmount;
                    yesList.addAll(list);
                }
                for (List<OnlineInvoiceDetail> list : yeslists) {
                    allOnlineInvoiceDetails.addAll(list);
                }
                Iterator<OnlineInvoiceDetail> iterator = allOnlineInvoiceDetails.iterator();
                while (iterator.hasNext()) {
                    OnlineInvoiceDetail next = iterator.next();
                    if (yesList.contains(next)) {
                        iterator.remove();
                    }
                }
                allOnlineInvoiceDetails.addAll(yesList);
            }
            //不符合特殊场景的条数为0时,报错
//            if (CollectionUtils.isEmpty(nolists)) {
//                log.info("不存在单行receive行,请检查!");
//                baseResult.setCode(ResultCode.UNKNOWN_ERROR.getCode());
//                baseResult.setMessage(LocaleHandler.getLocaleMsg("不存在单行receive行,请检查!"));
//                baseResult.setData(newOnlineInvoiceDetails);
//                return true;
//            }
        }
        newOnlineInvoiceDetails.addAll(noList);
        newOnlineInvoiceDetails.addAll(allOnlineInvoiceDetails);
        newOnlineInvoiceDetails.sort((o1, o2) -> Integer.compare(o1.getInvoiceRow(), o2.getInvoiceRow()));//排序
        //最后把全部数据没调整的设置为N
        newOnlineInvoiceDetails.forEach(onlineInvoiceDetail -> {
            if (!onlineInvoiceDetail.getPoUnitPriceChangeFlag().equals(YesOrNo.YES.getValue())) {
                onlineInvoiceDetail.setPoUnitPriceChangeFlag(YesOrNo.NO.getValue());
            }
        });
        log.info("--------------------------------调整后的网上开票明细:" + JsonUtil.entityToJsonStr(newOnlineInvoiceDetails));
//            BigDecimal sumNoTaxAmount = newOnlineInvoiceDetails.stream().map(x ->
//                    new BigDecimal(StringUtil.StringValue(x.getNoTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);

        /*按照入退货为组合,重新汇总计算系统不含税金额*/
        BigDecimal sumNoTaxAmount = getSumNoTaxAmount(onlineInvoiceDetails);
        //当只有一行时,就只取一行中的最大值净额
        BigDecimal maxNoTaxAmount = BigDecimal.ZERO;
        if (noList.size() > 1) {
            maxNoTaxAmount = noList.stream().map(OnlineInvoiceDetail::getNoTaxAmount).max((x1, x2) -> x1.compareTo(x2)).get();
        } else if (noList.size() == 1) {
            maxNoTaxAmount = newOnlineInvoiceDetails.get(0).getNoTaxAmount();
        }
        log.info("--------maxNoTaxAmount:" + maxNoTaxAmount + "--------------------------" +
                "sumNoTaxAmount:" + sumNoTaxAmount + "------------------------" +
                "actualInvoiceAmountN:" + onlineInvoice.getActualInvoiceAmountN());
        sumNoTaxAmount = sumNoTaxAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal actualInvoiceAmountN1 = onlineInvoice.getActualInvoiceAmountN() == null ? BigDecimal.ZERO : onlineInvoice.getActualInvoiceAmountN();
        actualInvoiceAmountN1 = actualInvoiceAmountN1.setScale(2, BigDecimal.ROUND_HALF_UP);
        log.info("sumNoTaxAmount:" + sumNoTaxAmount + "------------------actualInvoiceAmountN1:" + actualInvoiceAmountN1);
        /*按照入退货为组合,重新汇总计算系统含税金额*/
        BigDecimal sumTaxAmount = getSumTaxAmount(onlineInvoiceDetails);
        sumTaxAmount = sumTaxAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal actualInvoiceAmountY = onlineInvoice.getActualInvoiceAmountY() == null ? BigDecimal.ZERO : onlineInvoice.getActualInvoiceAmountY();
        actualInvoiceAmountY = actualInvoiceAmountY.setScale(2, BigDecimal.ROUND_HALF_UP);
        log.info("sumTaxAmount:" + sumTaxAmount + "------------------actualInvoiceAmountY:" + actualInvoiceAmountY);

        /*把最后的尾差放到金额最大中*/
//            BigDecimal lastDifferentTaxAmount = actualInvoiceAmountY.subtract(sumTaxAmount);//含税差额
//            log.info("--------------含税差额:" + lastDifferentTaxAmount);
        BigDecimal lastDifferentNoTaxAmount = actualInvoiceAmountN1.subtract(sumNoTaxAmount);//不含税差额
        log.info("--------------不含税差额:" + lastDifferentNoTaxAmount);
        if (lastDifferentNoTaxAmount.compareTo(BigDecimal.ZERO) != 0) {
            for (OnlineInvoiceDetail newOnlineInvoiceDetail : newOnlineInvoiceDetails) {
                BigDecimal newNoTaxAmount = newOnlineInvoiceDetail.getNoTaxAmount().add(lastDifferentNoTaxAmount);
                BigDecimal newTaxAmount = newOnlineInvoiceDetail.getTaxAmount().add(BigDecimal.ZERO);
                if (newOnlineInvoiceDetail.getNoTaxAmount().compareTo(maxNoTaxAmount) == 0) {
                    newOnlineInvoiceDetail
                            .setNoTaxAmount(newNoTaxAmount)
                            .setUnitPriceExcludingTax(newNoTaxAmount.divide(newOnlineInvoiceDetail.getInvoiceQuantity(), 8, BigDecimal.ROUND_HALF_UP))
                            .setUnitPriceContainingTax(newTaxAmount.divide(newOnlineInvoiceDetail.getInvoiceQuantity(), 8, BigDecimal.ROUND_HALF_UP))
                            .setTaxAmount(newTaxAmount);
                    break;
                }
            }
        }
//            BigDecimal newSumNoTaxAmount = newOnlineInvoiceDetails.stream().map(x ->
//                    new BigDecimal(StringUtil.StringValue(x.getNoTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
//            newSumNoTaxAmount = newSumNoTaxAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
//            BigDecimal newSumTaxAmount = newOnlineInvoiceDetails.stream().map(x ->
//                    new BigDecimal(StringUtil.StringValue(x.getTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
//            newSumTaxAmount = newSumTaxAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal newSumNoTaxAmount = getSumNoTaxAmount(newOnlineInvoiceDetails);
        BigDecimal newSumTaxAmount = getSumTaxAmount(newOnlineInvoiceDetails);
        log.info("newSumNoTaxAmount:" + newSumNoTaxAmount + "------------------actualInvoiceAmountN1:" + actualInvoiceAmountN1);
        log.info("newSumTaxAmount:" + newSumTaxAmount + "------------------actualInvoiceAmountY:" + actualInvoiceAmountY);
        baseResult.setData(newOnlineInvoiceDetails);
        for (OnlineInvoiceDetail onlineInvoiceDetail : newOnlineInvoiceDetails) {
            BigDecimal noTaxAmount = onlineInvoiceDetail.getNoTaxAmount() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getNoTaxAmount();//不含税金额
            BigDecimal invoiceQuantity = onlineInvoiceDetail.getInvoiceQuantity() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getInvoiceQuantity();//本次开票数量
            BigDecimal unitPriceExcludingTax = onlineInvoiceDetail.getUnitPriceExcludingTax() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getUnitPriceExcludingTax();//不含税单价
            BigDecimal taxAmount = onlineInvoiceDetail.getTaxAmount() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getTaxAmount();//含税金额
            BigDecimal unitPriceContainingTax = onlineInvoiceDetail.getUnitPriceContainingTax() == null ? BigDecimal.ZERO : onlineInvoiceDetail.getUnitPriceContainingTax();//含税单价
            boolean noTaxAmountFlag = ((invoiceQuantity.multiply(unitPriceExcludingTax)).setScale(2, BigDecimal.ROUND_HALF_UP)).compareTo(noTaxAmount.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0;
            boolean taxAmountFlag = ((invoiceQuantity.multiply(unitPriceContainingTax)).setScale(2, BigDecimal.ROUND_HALF_UP)).compareTo(taxAmount.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0;
            if (!noTaxAmountFlag && ifAdjust) {
                baseResult.setCode(ResultCode.UNKNOWN_ERROR.getCode());
                baseResult.setMessage(LocaleHandler.getLocaleMsg("第" + onlineInvoiceDetail.getInvoiceRow() + "行的不含税金额相乘不一致"));
                return true;
            }
//                if (!taxAmountFlag) {
//                    //手动回滚
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                    baseResult.setCode(ResultCode.UNKNOWN_ERROR.getCode());
//                    baseResult.setMessage(LocaleHandler.getLocaleMsg("第" + onlineInvoiceDetail.getInvoiceRow() + "行的含税金额相乘不一致"));
//                    return baseResult;
//                }
        }
        ;
        if (newSumNoTaxAmount.compareTo(actualInvoiceAmountN1) != 0 && ifAdjust) {
            baseResult.setCode(ResultCode.UNKNOWN_ERROR.getCode());
            log.error("调整后的总计行净额不等于实际开票净额,请检查!");
            baseResult.setMessage(LocaleHandler.getLocaleMsg("调整后的总计行净额不等于实际开票净额,请检查!"));
            return true;
        }
//            if (newSumTaxAmount.compareTo(actualInvoiceAmountY) != 0 && ifAdjust) {
//                baseResult.setCode(ResultCode.UNKNOWN_ERROR.getCode());
//                log.error("调整后的总计行含税额不等于实际开票总额,请检查!");
//                baseResult.setMessage(LocaleHandler.getLocaleMsg("调整后的总计行含税额不等于实际开票总额,请检查!"));
//                return baseResult;
//            }
        return false;
    }

    //获取含税金额
    private BigDecimal getSumTaxAmount(List<OnlineInvoiceDetail> onlineInvoiceDetails) {
        List<OnlineInvoiceDetail> returnOnlineInvoiceDetails = onlineInvoiceDetails.stream().filter(item -> CeeaWarehousingReturnDetailEnum.RETURN.getValue().equals(item.getType())).map(e-> BeanCopyUtil.copyProperties(e,OnlineInvoiceDetail::new)).collect(Collectors.toList());//退货开票明细
        List<OnlineInvoiceDetail> receiveOnlineInvoiceDetails = onlineInvoiceDetails.stream().filter(item -> CeeaWarehousingReturnDetailEnum.RECEIVE.getValue().equals(item.getType())).map(e->BeanCopyUtil.copyProperties(e,OnlineInvoiceDetail::new)).collect(Collectors.toList());//入货开票明细
        BigDecimal sumNoTaxAmount = BigDecimal.ZERO;
        BigDecimal sumTax = BigDecimal.ZERO;
        BigDecimal sumTaxAmount = BigDecimal.ZERO;
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
        }
        //全return情况:
        if (CollectionUtils.isEmpty(receiveOnlineInvoiceDetails) && CollectionUtils.isNotEmpty(returnOnlineInvoiceDetails)) {
            sumNoTaxAmount = returnOnlineInvoiceDetails.stream().map(x ->
                    new BigDecimal(StringUtil.StringValue(x.getNoTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
//            sumTax = returnOnlineInvoiceDetails.stream().map(x ->
//                    new BigDecimal(StringUtil.StringValue(x.getTax().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
            sumTaxAmount = returnOnlineInvoiceDetails.stream().map(x ->
                    new BigDecimal(StringUtil.StringValue(x.getTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
            //全receive和有receive,有return情况:
        } else {
            sumNoTaxAmount = receiveOnlineInvoiceDetails.stream().map(x ->
                    new BigDecimal(StringUtil.StringValue(x.getNoTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
//            sumTax = receiveOnlineInvoiceDetails.stream().map(x ->
//                    new BigDecimal(StringUtil.StringValue(x.getTax().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
            sumTaxAmount = receiveOnlineInvoiceDetails.stream().map(x ->
                    new BigDecimal(StringUtil.StringValue(x.getTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return sumTaxAmount;
    }

    //获取不含税金额
    private BigDecimal getSumNoTaxAmount(List<OnlineInvoiceDetail> onlineInvoiceDetails) {
        List<OnlineInvoiceDetail> returnOnlineInvoiceDetails = onlineInvoiceDetails.stream().filter(item -> CeeaWarehousingReturnDetailEnum.RETURN.getValue().equals(item.getType())).map(e-> BeanCopyUtil.copyProperties(e,OnlineInvoiceDetail::new)).collect(Collectors.toList());//退货开票明细
        List<OnlineInvoiceDetail> receiveOnlineInvoiceDetails = onlineInvoiceDetails.stream().filter(item -> CeeaWarehousingReturnDetailEnum.RECEIVE.getValue().equals(item.getType())).map(e->BeanCopyUtil.copyProperties(e,OnlineInvoiceDetail::new)).collect(Collectors.toList());//入货开票明细
        BigDecimal sumNoTaxAmount = BigDecimal.ZERO;
        BigDecimal sumTax = BigDecimal.ZERO;
        BigDecimal sumTaxAmount = BigDecimal.ZERO;

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
        }
        //全return情况:
        if (CollectionUtils.isEmpty(receiveOnlineInvoiceDetails) && CollectionUtils.isNotEmpty(returnOnlineInvoiceDetails)) {
            sumNoTaxAmount = returnOnlineInvoiceDetails.stream().map(x ->
                    new BigDecimal(StringUtil.StringValue(x.getNoTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
//            sumTax = returnOnlineInvoiceDetails.stream().map(x ->
//                    new BigDecimal(StringUtil.StringValue(x.getTax().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
            sumTaxAmount = returnOnlineInvoiceDetails.stream().map(x ->
                    new BigDecimal(StringUtil.StringValue(x.getTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
            //全receive和有receive,有return情况:
        } else {
            sumNoTaxAmount = receiveOnlineInvoiceDetails.stream().map(x ->
                    new BigDecimal(StringUtil.StringValue(x.getNoTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
//            sumTax = receiveOnlineInvoiceDetails.stream().map(x ->
//                    new BigDecimal(StringUtil.StringValue(x.getTax().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
            sumTaxAmount = receiveOnlineInvoiceDetails.stream().map(x ->
                    new BigDecimal(StringUtil.StringValue(x.getTaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP)))).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return sumNoTaxAmount;
    }


    private BigDecimal greaterThanZeroForYes(BigDecimal differenceTotalAmount, List<OnlineInvoiceDetail> yeslist, BigDecimal adjustAmount) {
        if (differenceTotalAmount.compareTo(BigDecimal.ZERO) == 1) {
            //组合内各个明细循环
            BigDecimal totalInvoiceQuantity = BigDecimal.ZERO;//组合内总开票数
            BigDecimal totalNoTaxAmount = BigDecimal.ZERO;//组合内总净价金额
            BigDecimal totalTaxAmount = BigDecimal.ZERO;//组合内总含税金额
            for (OnlineInvoiceDetail onlineInvoiceDetail : yeslist) {
                totalInvoiceQuantity = totalInvoiceQuantity.add(onlineInvoiceDetail.getInvoiceQuantity());
                totalNoTaxAmount = totalNoTaxAmount.add(onlineInvoiceDetail.getNoTaxAmount());
                totalTaxAmount = totalTaxAmount.add(onlineInvoiceDetail.getTaxAmount());
            }
            //正常调整
            adjustAmount = differenceTotalAmount;//差异金额等于本次的调整金额
            BigDecimal totalNoTaxAmountAfterAdjust = totalNoTaxAmount.add(adjustAmount);//先算总的调整后的净价金额(用于调组内最后的尾差)
            BigDecimal newTotalNoTaxAmount = BigDecimal.ZERO;//组内通过调整后的开票净价反算后的新总开票净价金额
            BigDecimal compareResult = adjustAmount.divide(totalInvoiceQuantity, 4, BigDecimal.ROUND_HALF_UP);//对比结果(可调整不含税单价)
            for (OnlineInvoiceDetail onlineInvoiceDetail : yeslist) {
                BigDecimal invoiceQuantity = onlineInvoiceDetail.getInvoiceQuantity();//本次开票数量
                BigDecimal unitPriceExcludingTax = onlineInvoiceDetail.getUnitPriceExcludingTax();//单价（含税,本次开票净价)
                onlineInvoiceDetail.setCompareResult(StringUtil.StringValue(compareResult))
                        .setPoUnitPriceChangeFlag(adjustAmount.compareTo(BigDecimal.ZERO) == 0 ? YesOrNo.NO.getValue() : YesOrNo.YES.getValue());//设置对比结果
                BigDecimal newUnitPriceExcludingTax = unitPriceExcludingTax.add(compareResult);//调整后的开票净价
                onlineInvoiceDetail.setUnitPriceExcludingTax(newUnitPriceExcludingTax);
                BigDecimal newNoTaxAmount = invoiceQuantity.multiply(newUnitPriceExcludingTax);//调整后的开票净额
                BigDecimal taxRate = onlineInvoiceDetail.getTaxRate().divide(new BigDecimal("100"));
                BigDecimal newTax = newNoTaxAmount.multiply(taxRate);//调整后的税额
                BigDecimal newTaxAmount = newNoTaxAmount.add(newTax);//调整后的含税金额
                BigDecimal newUnitPriceContainingTax = newTaxAmount.divide(invoiceQuantity, 8, BigDecimal.ROUND_HALF_UP);//调整后的含税单价
                onlineInvoiceDetail.setNoTaxAmount(newNoTaxAmount).setTax(newTax).setTaxAmount(newTaxAmount).setUnitPriceContainingTax(newUnitPriceContainingTax);
                newTotalNoTaxAmount = newTotalNoTaxAmount.add(newNoTaxAmount);
            }
            //最后的尾差处理
            BigDecimal lastDifference = totalNoTaxAmountAfterAdjust.subtract(newTotalNoTaxAmount);
            OnlineInvoiceDetail onlineInvoiceDetail = yeslist.get(0);
            BigDecimal noTaxAmount = onlineInvoiceDetail.getNoTaxAmount();
            BigDecimal taxAmount = onlineInvoiceDetail.getTaxAmount();
            onlineInvoiceDetail.setNoTaxAmount(noTaxAmount.add(lastDifference)).setTaxAmount(taxAmount.add(lastDifference));
            adjustAmount = BigDecimal.ZERO;
        }
        return adjustAmount;
    }

    private BigDecimal lessThanZeroForYes(BigDecimal differenceTotalAmount, List<OnlineInvoiceDetail> yeslist, BigDecimal adjustAmount) {
        if (differenceTotalAmount.compareTo(BigDecimal.ZERO) == -1) {
            //组合内各个明细循环
            BigDecimal totalInvoiceQuantity = BigDecimal.ZERO;//组合内总开票数
            BigDecimal totalNoTaxAmount = BigDecimal.ZERO;//组合内总净价金额
            BigDecimal totalTaxAmount = BigDecimal.ZERO;//组合内总含税金额
            for (OnlineInvoiceDetail onlineInvoiceDetail : yeslist) {
                totalInvoiceQuantity = totalInvoiceQuantity.add(onlineInvoiceDetail.getInvoiceQuantity());
                totalNoTaxAmount = totalNoTaxAmount.add(onlineInvoiceDetail.getNoTaxAmount());
                totalTaxAmount = totalTaxAmount.add(onlineInvoiceDetail.getTaxAmount());
            }
            //特殊情况:组合内总的开票净额小于或者等于差异金额,直接置为0
            if (totalNoTaxAmount.abs().compareTo(differenceTotalAmount.abs()) < 1) {
                for (OnlineInvoiceDetail onlineInvoiceDetail : yeslist) {
                    BigDecimal noTaxAmount = onlineInvoiceDetail.getNoTaxAmount();//初始的开票净价
                    calculateDatasBySetZero(onlineInvoiceDetail, noTaxAmount);
                }
                //开票净额小于差异金额
                if (totalNoTaxAmount.abs().compareTo(differenceTotalAmount.abs()) == -1) {
                    adjustAmount = differenceTotalAmount.add(totalNoTaxAmount);
                }
                //开票净额等于差异金额
                if (totalNoTaxAmount.abs().compareTo(differenceTotalAmount.abs()) == 0) {
                    adjustAmount = BigDecimal.ZERO;
                }
            }
            //正常调整,组合内总的开票净额大于差异金额
            if (totalNoTaxAmount.abs().compareTo(differenceTotalAmount.abs()) == 1) {
                adjustAmount = differenceTotalAmount;//差异金额等于本次的调整金额
                BigDecimal totalNoTaxAmountAfterAdjust = totalNoTaxAmount.add(adjustAmount);//先算总的调整后的净价金额(用于调组内最后的尾差)
                BigDecimal newTotalNoTaxAmount = BigDecimal.ZERO;//组内通过调整后的开票净价反算后的新总开票净价金额
                BigDecimal compareResult = adjustAmount.divide(totalInvoiceQuantity, 4, BigDecimal.ROUND_HALF_UP);//对比结果(可调整不含税单价)
                for (OnlineInvoiceDetail onlineInvoiceDetail : yeslist) {
                    BigDecimal invoiceQuantity = onlineInvoiceDetail.getInvoiceQuantity();//本次开票数量
                    BigDecimal unitPriceExcludingTax = onlineInvoiceDetail.getUnitPriceExcludingTax();//单价（本次开票净价)
                    onlineInvoiceDetail.setCompareResult(StringUtil.StringValue(compareResult))
                            .setPoUnitPriceChangeFlag(adjustAmount.compareTo(BigDecimal.ZERO) == 0 ? YesOrNo.NO.getValue() : YesOrNo.YES.getValue());//设置对比结果
                    BigDecimal newNoTaxAmount = adjustAmount.add(onlineInvoiceDetail.getNoTaxAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);//调整后的开票净额
                    BigDecimal newUnitPriceExcludingTax = newNoTaxAmount.divide(invoiceQuantity, 8, BigDecimal.ROUND_HALF_UP);//调整后的开票净价
                    onlineInvoiceDetail.setUnitPriceExcludingTax(newUnitPriceExcludingTax);
//                    BigDecimal newUnitPriceExcludingTax = unitPriceExcludingTax.add(compareResult);//调整后的开票净价
//                    onlineInvoiceDetail.setUnitPriceExcludingTax(newUnitPriceExcludingTax);
                    BigDecimal taxRate = onlineInvoiceDetail.getTaxRate().divide(new BigDecimal("100"));
                    BigDecimal newTax = newNoTaxAmount.multiply(taxRate);//调整后的税额
                    BigDecimal newTaxAmount = newNoTaxAmount.add(newTax);//调整后的含税金额
                    onlineInvoiceDetail.setNoTaxAmount(newNoTaxAmount).setTax(newTax).setTaxAmount(newTaxAmount);
                    newTotalNoTaxAmount = newTotalNoTaxAmount.add(newNoTaxAmount);
                }
                //最后的尾差处理
                BigDecimal lastDifference = totalNoTaxAmountAfterAdjust.subtract(newTotalNoTaxAmount);
                OnlineInvoiceDetail onlineInvoiceDetail = yeslist.get(0);
                BigDecimal noTaxAmount = onlineInvoiceDetail.getNoTaxAmount();
                BigDecimal taxAmount = onlineInvoiceDetail.getTaxAmount();
                onlineInvoiceDetail.setNoTaxAmount(noTaxAmount.add(lastDifference)).setTaxAmount(taxAmount.add(lastDifference));
                adjustAmount = BigDecimal.ZERO;
            }
        }
        return adjustAmount;
    }

    private void calculateDatasBySetZero(OnlineInvoiceDetail onlineInvoiceDetail, BigDecimal noTaxAmount) {
        onlineInvoiceDetail.setUnitPriceExcludingTax(BigDecimal.ZERO);//单价（含税,本次开票净价)
        onlineInvoiceDetail.setNoTaxAmount(BigDecimal.ZERO);//净价金额
        onlineInvoiceDetail.setTax(BigDecimal.ZERO);//税额
        onlineInvoiceDetail.setUnitPriceContainingTax(BigDecimal.ZERO);//含税单价
        onlineInvoiceDetail.setTaxAmount(BigDecimal.ZERO);//含税金额
        BigDecimal invoiceQuantity = onlineInvoiceDetail.getInvoiceQuantity();
        BigDecimal compareResult = noTaxAmount.negate().divide(invoiceQuantity, 4, BigDecimal.ROUND_HALF_UP);
        onlineInvoiceDetail.setCompareResult(StringUtil.StringValue(compareResult))
                .setPoUnitPriceChangeFlag(compareResult.compareTo(BigDecimal.ZERO) == 0 ? YesOrNo.NO.getValue() : YesOrNo.YES.getValue());//设置对比结果
    }

    private BigDecimal greaterThanZeroForNo(BigDecimal differenceTotalAmount, OnlineInvoiceDetail onlineInvoiceDetail, BigDecimal adjustAmount) {
        if (differenceTotalAmount.compareTo(BigDecimal.ZERO) == 1) {
            adjustAmount = differenceTotalAmount;//差异金额等于可调整金额
            calculateData(adjustAmount, onlineInvoiceDetail);
            adjustAmount = BigDecimal.ZERO;
        }
        log.info("单号{} 差异金额剩余{}:====================================>", JSONObject.toJSONString(onlineInvoiceDetail) , adjustAmount);
        return adjustAmount;
    }

    private BigDecimal lessThanZeroForNo(BigDecimal differenceTotalAmount, OnlineInvoiceDetail onlineInvoiceDetail, BigDecimal adjustAmount) {
        if (differenceTotalAmount.compareTo(BigDecimal.ZERO) == -1) {
            BigDecimal noTaxAmount = onlineInvoiceDetail.getNoTaxAmount();
            //特殊情况:开票净额小于或等于差异金额,直接置为0
            if (noTaxAmount.abs().compareTo(differenceTotalAmount.abs()) < 1) {
                calculateDatasBySetZero(onlineInvoiceDetail, noTaxAmount);
                //开票净额小于差异金额
                if (noTaxAmount.abs().compareTo(differenceTotalAmount.abs()) == -1) {
                    adjustAmount = differenceTotalAmount.add(noTaxAmount);
                }
                //开票净额等于差异金额
                if (noTaxAmount.abs().compareTo(differenceTotalAmount.abs()) == 0) {
                    adjustAmount = BigDecimal.ZERO;
                }
            }
            //正常调整,开票净额大于差异金额
            if (noTaxAmount.abs().compareTo(differenceTotalAmount.abs()) == 1) {
                adjustAmount = differenceTotalAmount;//差异金额等于本次的调整金额
                calculateData(adjustAmount, onlineInvoiceDetail);
                adjustAmount = BigDecimal.ZERO;
            }
//            //正常调整,开票净额小于差异金额
//            if (noTaxAmount.compareTo(differenceTotalAmount.abs()) == -1) {
//                BigDecimal adjustAmount = onlineInvoiceDetail.getNoTaxAmount().negate();
//                calculateData(adjustAmount, onlineInvoiceDetail);
//                differenceTotalAmount = differenceTotalAmount.subtract(adjustAmount);
//            }
        }
        log.info("单号{} ，差异金额剩余:{}====================================>" ,JSONObject.toJSONString(onlineInvoiceDetail) , adjustAmount);
        return adjustAmount;
    }

    private BigDecimal calculateData(BigDecimal adjustAmount, OnlineInvoiceDetail onlineInvoiceDetail) {
        BigDecimal invoiceQuantity = onlineInvoiceDetail.getInvoiceQuantity();//本次开票数量
        BigDecimal unitPriceExcludingTax = onlineInvoiceDetail.getUnitPriceExcludingTax();//单价（含税,本次开票净价)
        BigDecimal compareResult = adjustAmount.divide(invoiceQuantity, 2, BigDecimal.ROUND_HALF_UP);//对比结果(可调整不含税单价)
        onlineInvoiceDetail.setCompareResult(StringUtil.StringValue(compareResult))
                .setPoUnitPriceChangeFlag(adjustAmount.compareTo(BigDecimal.ZERO) == 0 ? YesOrNo.NO.getValue() : YesOrNo.YES.getValue());//设置对比结果
        BigDecimal newNoTaxAmount = adjustAmount.add(onlineInvoiceDetail.getNoTaxAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);//调整后的开票净额
        BigDecimal newUnitPriceExcludingTax = newNoTaxAmount.divide(invoiceQuantity, 8, BigDecimal.ROUND_HALF_UP);//调整后的开票净价
        onlineInvoiceDetail.setUnitPriceExcludingTax(newUnitPriceExcludingTax);
//        BigDecimal newUnitPriceExcludingTax = unitPriceExcludingTax.add(compareResult);//调整后的开票净价
//        onlineInvoiceDetail.setUnitPriceExcludingTax(newUnitPriceExcludingTax);
        BigDecimal taxRate = onlineInvoiceDetail.getTaxRate().divide(new BigDecimal("100"));
        BigDecimal newTax = newNoTaxAmount.multiply(taxRate).setScale(2, BigDecimal.ROUND_HALF_UP);//调整后的税额
        BigDecimal newTaxAmount = newNoTaxAmount.add(newTax);//调整后的含税金额
        BigDecimal newUnitPriceContainingTax = newTaxAmount.divide(invoiceQuantity, 8, BigDecimal.ROUND_HALF_UP);//调整后的含税单价
        onlineInvoiceDetail.setNoTaxAmount(newNoTaxAmount).setTax(newTax).setTaxAmount(newTaxAmount).setUnitPriceContainingTax(newUnitPriceContainingTax);
        return adjustAmount;
    }

    private void saveOrUpdateOnlineInvoice(String invoiceStatus, OnlineInvoice onlineInvoice, LoginAppUser loginAppUser) {
        if (onlineInvoice.getOnlineInvoiceId() == null) {
            //获取当前时间
            LocalDate now = LocalDate.now();
            onlineInvoice.setOnlineInvoiceId(IdGenrator.generate())
                    .setOnlineInvoiceNum(baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PS_ONLINE_INVOICE_CODE))
                    .setInvoiceStatus(invoiceStatus)
                    .setImportStatus(InvoiceImportStatus.NOT_IMPORT.name())
                    .setInvoiceDate(now);

            //设置应付到款日期
            if (UserType.BUYER.name().equals(loginAppUser.getUserType())) {
                setAccountPayableDealine(onlineInvoice, now);
            }
            this.save(onlineInvoice);
        } else {
            //获取当前时间
            LocalDate now = LocalDate.now();
            //设置应付到款日期
            if (UserType.BUYER.name().equals(loginAppUser.getUserType())) {
                setAccountPayableDealine(onlineInvoice, now);
            }
            this.updateById(onlineInvoice.setInvoiceStatus(invoiceStatus));
        }
    }

    private void setAccountPayableDealine(OnlineInvoice onlineInvoice, LocalDate now) {
        String payAccountPeriodCode = onlineInvoice.getPayAccountPeriodCode();
        if (!ObjectUtils.isEmpty(payAccountPeriodCode)) {
            List<DictItemDTO> dictItemDTOS = baseClient.listAllByParam(new DictItemDTO().setDictItemCode(payAccountPeriodCode));
            if (CollectionUtils.isNotEmpty(dictItemDTOS)) {
                DictItemDTO dictItemDTO = dictItemDTOS.get(0);
                String itemDescription = dictItemDTO.getItemDescription();
                if (StringUtils.isNotEmpty(itemDescription)) {
                    onlineInvoice.setAccountPayableDealine(now.plusDays(Long.valueOf(itemDescription)));
                }
            }
        }

    }

//    字典值修改(废弃)
//    private void setAccountPayableDealine(OnlineInvoice onlineInvoice, LocalDate now) {
//        String payAccountPeriodCode = onlineInvoice.getPayAccountPeriodCode();
//        boolean digit = StringUtil.isDigit(payAccountPeriodCode);
//        if (!digit) {
//            throw new BaseException("配置的账期字典编码非数字,请检查!");
//        }
//        if ("0".equals(payAccountPeriodCode)) {
//            onlineInvoice.setAccountPayableDealine(now);
//        } else if ("00".equals(payAccountPeriodCode)) {
//            onlineInvoice.setAccountPayableDealine(now);
//        } else {
//            onlineInvoice.setAccountPayableDealine(now.plusDays(Long.valueOf(payAccountPeriodCode)));
//        }
//    }

    public String getDicName(List<DictItemDTO> list, String code) {
        return list.stream().filter(y -> Objects.equals(y.getDictItemCode(), code)).findFirst().orElse(new DictItemDTO()).getDictItemName();
    }

    public static void main(String[] args) {
//        OnlineInvoiceDetail onlineInvoiceDetail1 = new OnlineInvoiceDetail();
//        onlineInvoiceDetail1.setInvoiceQuantity(new BigDecimal("1"));
//        OnlineInvoiceDetail onlineInvoiceDetail2 = new OnlineInvoiceDetail();
//        onlineInvoiceDetail2.setInvoiceQuantity(new BigDecimal("2"));
//        OnlineInvoiceDetail onlineInvoiceDetail3 = new OnlineInvoiceDetail();
//        onlineInvoiceDetail3.setInvoiceQuantity(new BigDecimal("3"));
//        OnlineInvoiceDetail[] onlineInvoiceDetails = {onlineInvoiceDetail3, onlineInvoiceDetail1, onlineInvoiceDetail2};
//        List<OnlineInvoiceDetail> onlineInvoiceDetailList = Arrays.asList(onlineInvoiceDetails);
//        System.out.println(JsonUtil.arrayToJsonStr(onlineInvoiceDetailList));
//        BigDecimal sum = onlineInvoiceDetailList.stream().map(x ->
//                new BigDecimal(StringUtil.StringValue(x.getInvoiceQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
//        System.out.println(JsonUtil.entityToJsonStr(sum));

        System.out.println(StringUtils.isBlank(""));
    }


    /**
     * @Description 导出物料维护数据
     * @Author: xiexh12 2020-11-15 14:58
     */
    @Override
    public void exportOnlineInvoiceExcel(ExportExcelParam<OnlineInvoice> onlineInvoiceExportParam, HttpServletResponse response) throws IOException {
        // 获取导出的数据
        List<List<Object>> dataList = this.queryExportData(onlineInvoiceExportParam);
        // 标题
        List<String> head = onlineInvoiceExportParam.getMultilingualHeader(onlineInvoiceExportParam, OnlineInvoiceExportUtils.getOnlineInvoiceTitles());
        // 文件名
        String fileName = onlineInvoiceExportParam.getFileName();
        // 开始导出
        EasyExcelUtil.exportStart(response, dataList, head, fileName);
    }

    /**
     * @Description 查询导出数据并转换excel数据集
     * @Author: xiexh12 2020-11-15 15:03
     */
    private List<List<Object>> queryExportData(ExportExcelParam<OnlineInvoice> onlineInvoiceExportParam) {
        OnlineInvoice queryParam = onlineInvoiceExportParam.getQueryParam(); // 查询参数
        /**
         * 先查询要导出的数据有多少条, 大于2万是建议用户填分页参数进行分页导出,每页不能超过2万
         */
        int count = queryCountByParam(queryParam);
        if (count > 20000) {
            /**
             * 要导出的数据超过20000, 要求用户选择分页参数
             */
            if (StringUtil.notEmpty(queryParam.getPageSize()) && StringUtil.notEmpty(queryParam.getPageNum())) {
                Assert.isTrue(queryParam.getPageSize() <= 20000, "分页导出单页上限20000");
            } else {
                throw new BaseException("当前要导出的数据超过20000条,请选择分页导出,单页上限20000;");
            }
        }

        boolean flag = null != queryParam.getPageSize() && null != queryParam.getPageNum();
        if (flag) {
            // 设置分页
            PageUtil.startPage(queryParam.getPageNum(), queryParam.getPageSize());
        }
        List<OnlineInvoice> onlineInvoiceList = this.queryOnlineInvoiceByParam(queryParam);
        // 转Map(对象转map,key:属性名,value:属性值)
        List<Map<String, Object>> mapList = BeanMapUtils.objectsToMaps(onlineInvoiceList);
        // 导出excel的标题
        List<String> titleList = onlineInvoiceExportParam.getTitleList();

        List<List<Object>> results = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(mapList)) {
            mapList.forEach((map) -> {
                List<Object> list = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(titleList)) {
                    titleList.forEach((title) -> {
                        if (Objects.nonNull(map)) {
                            Object value = map.get(title);
                            if ("accountPayableDealine".equals(title)) {
                                String accountPayableDealine = String.valueOf(value);
                                list.add(accountPayableDealine);
                            } else if ("invoiceStatus".equals(title)) {
                                String invoiceStatus = convertInvoiceStatus(String.valueOf(value));
                                list.add(invoiceStatus);
                            } else if ("importStatus".equals(title)) {
                                String importStatus = convertImportStatus(String.valueOf(value));
                                list.add(importStatus);
                            } else if ("businessType".equals(title)) {
                                String businessType = businessTypeMap.get(String.valueOf(value));
                                list.add(businessType);
                            } else {
                                list.add((String) value);
                            }
                        }
                    });
                }
                results.add(list);
            });
        }
        return results;
    }


    /**
     * 根据条件查询符合条件的数据条数
     *
     * @param queryParam
     * @modified xiexh12 2020-11-15 15:11
     */
    private int queryCountByParam(OnlineInvoice queryParam) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        QueryWrapper<OnlineInvoice> queryWrapper = new QueryWrapper<>();
        String onlineInvoiceNum = queryParam.getOnlineInvoiceNum();
        String startDate = Objects.nonNull(queryParam.getStartDate()) ? formatter.format(queryParam.getStartDate()) : "";
        String endDate = Objects.nonNull(queryParam.getEndDate()) ? formatter.format(queryParam.getEndDate()) : "";
        String orgName = queryParam.getOrgName();
        String vendorName = queryParam.getVendorName();
        String costTypeName = queryParam.getCostTypeName();
        String invoiceStatus = queryParam.getInvoiceStatus();
        String importStatus = queryParam.getImportStatus();
        String businessType = queryParam.getBusinessType();
        String taxInvoiceNum = queryParam.getTaxInvoiceNum();
        String fsscNo = queryParam.getFsscNo();
        String payMethod = queryParam.getPayMethod();

        queryWrapper.lambda().eq(StringUtils.isNotBlank(onlineInvoiceNum), OnlineInvoice::getOnlineInvoiceNum, onlineInvoiceNum)
                .like(StringUtils.isNotEmpty(orgName), OnlineInvoice::getOrgName, orgName)
                .like(StringUtils.isNotEmpty(vendorName), OnlineInvoice::getVendorName, vendorName)
                .eq(StringUtils.isNotEmpty(costTypeName), OnlineInvoice::getCostTypeName, costTypeName)
                .eq(StringUtils.isNotEmpty(invoiceStatus), OnlineInvoice::getInvoiceStatus, invoiceStatus)
                .eq(StringUtils.isNotEmpty(importStatus), OnlineInvoice::getImportStatus, importStatus)
                .eq(StringUtils.isNotEmpty(businessType), OnlineInvoice::getBusinessType, businessType)
                .eq(StringUtils.isNotEmpty(taxInvoiceNum), OnlineInvoice::getTaxInvoiceNum, taxInvoiceNum)
                .eq(StringUtils.isNotEmpty(fsscNo), OnlineInvoice::getFsscNo, fsscNo)
                .eq(StringUtils.isNotEmpty(payMethod), OnlineInvoice::getPayMethod, payMethod);
        if (StringUtils.isNotEmpty(startDate)) {
            queryWrapper.lambda().gt(OnlineInvoice::getAccountPayableDealine, startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            queryWrapper.lambda().lt(OnlineInvoice::getAccountPayableDealine, endDate);
        }
        return this.count(queryWrapper);
    }

    /*
     * @Description 获取导出表数据
     * @modified xiexh12 11-15 15:51
     */
    private List<OnlineInvoice> queryOnlineInvoiceByParam(OnlineInvoice queryParam) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        QueryWrapper<OnlineInvoice> queryWrapper = new QueryWrapper<>();
        String onlineInvoiceNum = queryParam.getOnlineInvoiceNum();
        String startDate = Objects.nonNull(queryParam.getStartDate()) ? formatter.format(queryParam.getStartDate()) : "";
        String endDate = Objects.nonNull(queryParam.getEndDate()) ? formatter.format(queryParam.getEndDate()) : "";
        String orgName = queryParam.getOrgName();
        String vendorName = queryParam.getVendorName();
        String costTypeName = queryParam.getCostTypeName();
        String invoiceStatus = queryParam.getInvoiceStatus();
        String importStatus = queryParam.getImportStatus();
        String businessType = queryParam.getBusinessType();
        String taxInvoiceNum = queryParam.getTaxInvoiceNum();
        String fsscNo = queryParam.getFsscNo();
        String payMethod = queryParam.getPayMethod();

        queryWrapper.lambda().eq(StringUtils.isNotBlank(onlineInvoiceNum), OnlineInvoice::getOnlineInvoiceNum, onlineInvoiceNum)
                .like(StringUtils.isNotEmpty(orgName), OnlineInvoice::getOrgName, orgName)
                .like(StringUtils.isNotEmpty(vendorName), OnlineInvoice::getVendorName, vendorName)
                .eq(StringUtils.isNotEmpty(costTypeName), OnlineInvoice::getCostTypeName, costTypeName)
                .eq(StringUtils.isNotEmpty(invoiceStatus), OnlineInvoice::getInvoiceStatus, invoiceStatus)
                .eq(StringUtils.isNotEmpty(importStatus), OnlineInvoice::getImportStatus, importStatus)
                .eq(StringUtils.isNotEmpty(businessType), OnlineInvoice::getBusinessType, businessType)
                .eq(StringUtils.isNotEmpty(taxInvoiceNum), OnlineInvoice::getTaxInvoiceNum, taxInvoiceNum)
                .eq(StringUtils.isNotEmpty(fsscNo), OnlineInvoice::getFsscNo, fsscNo)
                .eq(StringUtils.isNotEmpty(payMethod), OnlineInvoice::getPayMethod, payMethod);
        if (StringUtils.isNotEmpty(startDate)) {
            queryWrapper.lambda().gt(OnlineInvoice::getAccountPayableDealine, startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            queryWrapper.lambda().lt(OnlineInvoice::getAccountPayableDealine, endDate);
        }
        List<OnlineInvoice> OnlineInvoiceList = this.list(queryWrapper);
        return OnlineInvoiceList;
    }

    /**
     * 转换网上发票状态字典吗码
     */
    public String convertInvoiceStatus(String status) {
        String result = "";
        if (StringUtils.isNotEmpty(status)) {
            switch (status) {
                case "DRAFT":
                    result = "新建";
                    break;
                case "UNDER_APPROVAL":
                    result = "审批中";
                    break;
                case "APPROVAL":
                    result = "已审批";
                    break;
                case "REVIEWED":
                    result = "已复核";
                    break;
                case "DROP":
                    result = "已作废";
                    break;
                case "REJECTED":
                    result = "已驳回";
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    /**
     * 转换网上发票导入状态字典码
     */
    public String convertImportStatus(String importStatus) {
        String result = "";
        if (StringUtils.isNotEmpty(importStatus)) {
            switch (importStatus) {
                case "NOT_IMPORT":
                    result = "未导入";
                    break;
                case "IMPORTED":
                    result = "已导入";
                    break;
                default:
                    break;
            }
        }
        return result;
    }


    //----------------------------------------------------------------OA回调分界线-----------------------------------------

    @Override
    public void submitFlow(Long businessId, String param) throws Exception {

    }

    @Override
    public void passFlow(Long businessId, String param) throws Exception {

    }

    @Override
    public void rejectFlow(Long businessId, String param) throws Exception {

    }

    @Override
    public void withdrawFlow(Long businessId, String param) throws Exception {

    }

    @Override
    public void destoryFlow(Long businessId, String param) throws Exception {

    }

    @Override
    public String getVariableFlow(Long businessId, String param) throws Exception {
        return null;
    }

    @Override
    public String getDataPushFlow(Long businessId, String param) throws Exception {
        return null;
    }
}
