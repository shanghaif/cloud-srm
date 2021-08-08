package com.midea.cloud.srm.ps.anon.external.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.cm.PaymentStatus;
import com.midea.cloud.common.enums.pm.ps.AdvanceApplyStatus;
import com.midea.cloud.common.enums.pm.ps.BoeTypeCode;
import com.midea.cloud.common.enums.pm.ps.FsscStatusCode;
import com.midea.cloud.common.enums.pm.ps.InvoiceStatus;
import com.midea.cloud.common.enums.supcooperate.InvoiceImportStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.base.external.entity.ExternalInterfaceLog;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.ps.advance.entity.AdvanceApplyHead;
import com.midea.cloud.srm.model.pm.ps.anon.external.FsscStatus;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.pm.ps.payment.entity.CeeaPaymentApplyHead;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceAdvance;
import com.midea.cloud.srm.ps.advance.service.IAdvanceApplyHeadService;
import com.midea.cloud.srm.ps.anon.external.mapper.FsscStatusMapper;
import com.midea.cloud.srm.ps.anon.external.service.IFsscStatusService;
import com.midea.cloud.srm.ps.payment.service.ICeeaPaymentApplyHeadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * <pre>
 *  FSSC状态反馈表 服务实现类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-02 08:47:44
 *  修改内容:
 * </pre>
 */
@Slf4j
@Service
public class FsscStatusServiceImpl extends ServiceImpl<FsscStatusMapper, FsscStatus> implements IFsscStatusService {

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private IAdvanceApplyHeadService iAdvanceApplyHeadService;

    @Autowired
    private ICeeaPaymentApplyHeadService iCeeaPaymentApplyHeadService;

    @Autowired
    private SupcooperateClient supcooperateClient;

    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private ApiClient apiClient;


    private static final String KEY_STATUS = "fstatus";
    private static final String KEY_MESSAGE = "message";

    private void logFSSC(Object requestBody ,
                         Map<String,Object> responseBody ,
                         String interfaceName ,
                         String url,
                         String businessNo){
        // 日志收集类
        InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
        interfaceLogDTO.setServiceName(interfaceName); // 说明
        interfaceLogDTO.setServiceType("HTTP"); // 接收方式
        interfaceLogDTO.setType("RECEIVE"); // 接收
        interfaceLogDTO.setDealTime(1L);
        interfaceLogDTO.setBillId(businessNo);
        interfaceLogDTO.setServiceInfo(JSON.toJSONString(requestBody)); // 入参
        interfaceLogDTO.setCreationDateBegin(new Date()); // 开始时间
        interfaceLogDTO.setCreationDateEnd(new Date()); // 结束时间
        interfaceLogDTO.setUrl(url);
        interfaceLogDTO.setReturnInfo(JSON.toJSONString(responseBody)); // 出参
        interfaceLogDTO.setFinishDate(new Date()); // 完成时间
        if(Objects.nonNull(responseBody.get(KEY_STATUS)) && YesOrNo.YES.getValue().equals(responseBody.get(KEY_STATUS))){
            interfaceLogDTO.setStatus("SUCCESS"); // 状态
        }else {
            interfaceLogDTO.setStatus("FAIL");
            interfaceLogDTO.setErrorInfo(JSON.toJSONString(responseBody));
        }
        apiClient.createInterfaceLog(interfaceLogDTO);
    }

    @Override
    @Transactional
    public Map<String, Object> receiveData(FsscStatus fsscStatus) {
        log.info("开始接收状态反馈数据:==========>" + JsonUtil.entityToJsonStr(fsscStatus));
        Map<String, Object> resultMap = new HashMap<>();
        String businessNo = fsscStatus.getFsscNo();
        try {
            boolean error = false;
            if (fsscStatus == null) {
                resultMap.put(KEY_STATUS, YesOrNo.NO.getValue());
                resultMap.put(KEY_MESSAGE, "传入对象不能为空");
                error = true;
            }
            if (StringUtils.isBlank(fsscStatus.getFsscNo())) {
                resultMap.put(KEY_STATUS, YesOrNo.NO.getValue());
                resultMap.put(KEY_MESSAGE, "FSSC单号不能为空");
                error = true;
            }
            if (StringUtils.isBlank(fsscStatus.getPeripheralSystemNum())) {
                resultMap.put(KEY_STATUS, YesOrNo.NO.getValue());
                resultMap.put(KEY_MESSAGE, "外系统单号不能为空");
                error = true;
            }
            if (StringUtils.isBlank(fsscStatus.getStatus())) {
                resultMap.put(KEY_STATUS, YesOrNo.NO.getValue());
                resultMap.put(KEY_MESSAGE, "状态不能为空");
                error = true;
            }
            if(!error){
                //根据不同的单据类型,更新srm中的单据状态 ToDo
                updateSrmStatus(fsscStatus, resultMap);
                log.info("完成更新srm中的单据状态:===============>" + JsonUtil.entityToJsonStr(fsscStatus));
                //保存外部接口传入信息
                this.save(fsscStatus.setFsscStatusId(IdGenrator.generate()));
                log.info("完成保存外部接口传入信息:===============>" + JsonUtil.entityToJsonStr(fsscStatus));
                resultMap.put(KEY_STATUS , YesOrNo.YES.getValue());
                resultMap.put(KEY_MESSAGE , "同步单号数据成功!");
            }
        }catch (Exception e){
            log.error("费控系统同步付款申请单报错" , e);
            resultMap.put(KEY_STATUS , YesOrNo.NO.getValue());
            resultMap.put(KEY_MESSAGE , "同步单号数据失败!");
            resultMap.put("errorMsg",e.getStackTrace());
        }finally {
            try {
                logFSSC(fsscStatus ,
                        resultMap ,
                        "费控系统同步付款申请单",
                        "/api-pm/ps-anon/external/fsscStatus/receiveData" ,
                        businessNo);
                resultMap.put("errorMsg" , "");
            }catch (Exception e){
                log.error("保存接口日志信息失败",e);
                resultMap.put(KEY_STATUS , "N");
                resultMap.put(KEY_MESSAGE , "同步失败！请联系SRM管理员");
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> download(String fileuploadId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            log.info("--------------------------------------开始文件下载-------------------------------------------");
            fileCenterClient.downloadFileByParamForAnon(new Fileupload().setFileuploadId(Long.valueOf(fileuploadId)));
            resultMap.put(KEY_STATUS, YesOrNo.YES.getValue());
            resultMap.put(KEY_MESSAGE, "SUCCESS");
            log.info("--------------------------------------成功下载文件---------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件下载异常:==============================>", e);
            resultMap.put(KEY_STATUS, YesOrNo.NO.getValue());
            resultMap.put(KEY_MESSAGE, "文件下载异常,请联系相关管理员!");
        }
        return resultMap;
    }

    private void updateSrmStatus(FsscStatus fsscStatus, Map<String, Object> resultMap) {
        if (BoeTypeCode.FOREIGN_ADVANCE_PAYMENT.name().equals(fsscStatus.getBoeTypeCode())) {
            //1.修改预付款报账单状态
            //判断反馈的单据状态,做对应的业务处理
            String status = fsscStatus.getStatus();
            String fsscNo = fsscStatus.getFsscNo();
            if (FsscStatusCode.FSSC_STATUS_10.getValue().equals(status)) {
                UpdateWrapper<AdvanceApplyHead> updateWrapper = new UpdateWrapper<>(new AdvanceApplyHead().setBoeNo(fsscNo));
                iAdvanceApplyHeadService.update(new AdvanceApplyHead().setAdvanceApplyStatus(AdvanceApplyStatus.DRAFT.getValue()), updateWrapper);
            } else if (FsscStatusCode.FSSC_STATUS_11.getValue().equals(status)) {
                UpdateWrapper<AdvanceApplyHead> updateWrapper = new UpdateWrapper<>(new AdvanceApplyHead().setBoeNo(fsscNo));
                iAdvanceApplyHeadService.update(new AdvanceApplyHead().setAdvanceApplyStatus(AdvanceApplyStatus.REJECTED.getValue()), updateWrapper);
            } else if (FsscStatusCode.FSSC_STATUS_30.getValue().equals(status)) {
                UpdateWrapper<AdvanceApplyHead> updateWrapper = new UpdateWrapper<>(new AdvanceApplyHead().setBoeNo(fsscNo));
                AdvanceApplyHead advanceApplyHead = iAdvanceApplyHeadService.getOne(updateWrapper);
                BigDecimal applyPayAmount = advanceApplyHead.getApplyPayAmount();
                iAdvanceApplyHeadService.update(new AdvanceApplyHead()
                        .setAdvanceApplyStatus(AdvanceApplyStatus.APPROVAL.getValue())
                        .setUsableAmount(applyPayAmount), updateWrapper);
            } else if (FsscStatusCode.FSSC_STATUS_100.getValue().equals(status)) {

            }
            log.info("----------------------------------修改预付款报账单状态成功---------------------");
        }
        if (BoeTypeCode.PURCHASE_BOE_LGi.name().equals(fsscStatus.getBoeTypeCode())) {
            //2.修改三单匹配单
            //判断反馈的单据状态,做对应的业务处理
            try {
                updatePurchaseBoeLgi(fsscStatus);
                log.info("-------------------------------修改三单匹配单状态成功-----------------------------");
            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put(KEY_STATUS, YesOrNo.NO.getValue());
                resultMap.put(KEY_MESSAGE, "修改三单匹配单状态出现异常,请联系相关管理员!");
                log.error("修改三单匹配单状态时出现异常:====================>", e);
            }
        }
        if (BoeTypeCode.FOREIGN_PAYMENT_BOE.name().equals(fsscStatus.getBoeTypeCode()) ||
                BoeTypeCode.AGENCY_PAYMENT_BOE.name().equals(fsscStatus.getBoeTypeCode())) {
            //3.挂账付款单 ToDo
            //判断反馈的单据状态,做对应的业务处理 ToDo
            String status = fsscStatus.getStatus();
            String fsscNo = fsscStatus.getFsscNo();
            if (FsscStatusCode.FSSC_STATUS_10.getValue().equals(status)) {
                UpdateWrapper<CeeaPaymentApplyHead> updateWrapper = new UpdateWrapper<>(new CeeaPaymentApplyHead().setBoeNo(fsscNo));
                iCeeaPaymentApplyHeadService.update(new CeeaPaymentApplyHead().setReceiptStatus(PaymentStatus.DRAFT.getKey()), updateWrapper);
            } else if (FsscStatusCode.FSSC_STATUS_11.getValue().equals(status)) {
                UpdateWrapper<CeeaPaymentApplyHead> updateWrapper = new UpdateWrapper<>(new CeeaPaymentApplyHead().setBoeNo(fsscNo));
                iCeeaPaymentApplyHeadService.update(new CeeaPaymentApplyHead().setReceiptStatus(PaymentStatus.REJECT.getKey()), updateWrapper);
            } else if (FsscStatusCode.FSSC_STATUS_30.getValue().equals(status)) {
                UpdateWrapper<CeeaPaymentApplyHead> updateWrapper = new UpdateWrapper<>(new CeeaPaymentApplyHead().setBoeNo(fsscNo));
                iCeeaPaymentApplyHeadService.update(new CeeaPaymentApplyHead().setReceiptStatus(PaymentStatus.APPROVAL.getKey()), updateWrapper);
            } else if (FsscStatusCode.FSSC_STATUS_100.getValue().equals(status)) {

            }
        }
    }

    private void updatePurchaseBoeLgi(FsscStatus fsscStatus) {
        String status = fsscStatus.getStatus();
        String fsscNo = fsscStatus.getFsscNo();
        if (FsscStatusCode.FSSC_STATUS_10.getValue().equals(status)) {//单据撤回,对应改变网上开票为新建
            supcooperateClient.statusReturnForAnon(fsscNo, InvoiceStatus.DRAFT.name());
        } else if (FsscStatusCode.FSSC_STATUS_11.getValue().equals(status)) {//单据驳回,对应改变网上开票为已驳回
            List<OnlineInvoiceAdvance> onlineInvoiceAdvances = supcooperateClient.listOnlineInvoiceAdvanceByFsscNoForAnon(fsscNo);
            if (CollectionUtils.isNotEmpty(onlineInvoiceAdvances)) {
                for (OnlineInvoiceAdvance onlineInvoiceAdvance : onlineInvoiceAdvances) {
                    if (onlineInvoiceAdvance == null) continue;
                    //驳回状态后,可用金额需要重新加回本次核销金额
                    AdvanceApplyHead advanceApplyHead = iAdvanceApplyHeadService.getById(onlineInvoiceAdvance.getAdvanceApplyHeadId());
                    BigDecimal usableAmount = advanceApplyHead.getUsableAmount();//预付款可用金额
                    BigDecimal chargeOffAmount = onlineInvoiceAdvance.getChargeOffAmount();//本次核销金额
                    onlineInvoiceAdvance.setUsableAmount(usableAmount.add(chargeOffAmount));
                    iAdvanceApplyHeadService.setQuote(onlineInvoiceAdvance, YesOrNo.YES.getValue());
                }
                supcooperateClient.updateOnlineInvoiceAdvanceByParamForAnon(onlineInvoiceAdvances);
            }
            supcooperateClient.statusReturnForAnon(fsscNo, InvoiceStatus.REJECTED.name());
        } else if (FsscStatusCode.FSSC_STATUS_30.getValue().equals(status)) {//单据完成,对应改变网上开票为已复核
            //设置预付款是否引用标记
            List<OnlineInvoiceAdvance> onlineInvoiceAdvances = supcooperateClient.listOnlineInvoiceAdvanceByFsscNoForAnon(fsscNo);
            if (CollectionUtils.isNotEmpty(onlineInvoiceAdvances)) {
                for (OnlineInvoiceAdvance onlineInvoiceAdvance : onlineInvoiceAdvances) {
                    if (onlineInvoiceAdvance == null) continue;
                    if (BigDecimal.ZERO.compareTo(onlineInvoiceAdvance.getUsableAmount()) != 0) {
                        iAdvanceApplyHeadService.setQuote(onlineInvoiceAdvance, YesOrNo.NO.getValue());
                    }
                }
            }
            supcooperateClient.statusReturnForAnon(fsscNo, InvoiceStatus.REVIEWED.name());
            log.info("----------------------------------已完成三单匹配审批同意------------------------------");
        } else if (FsscStatusCode.FSSC_STATUS_100.getValue().equals(status)) {
            supcooperateClient.importStatusReturnForAnon(fsscNo, InvoiceImportStatus.IMPORTED.name());
            log.info("----------------------------------已完成三单匹配导入----------------------------------");
        }
    }

}
