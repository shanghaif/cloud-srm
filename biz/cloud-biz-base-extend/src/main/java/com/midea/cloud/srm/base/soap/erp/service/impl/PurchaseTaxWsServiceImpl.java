package com.midea.cloud.srm.base.soap.erp.service.impl;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.base.purchase.service.IPurchaseTaxService;
import com.midea.cloud.srm.base.soap.erp.service.IPurchaseTaxWsService;
import com.midea.cloud.srm.model.base.organization.entity.ErpPurchaseTax;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.PurchaseTaxEntity;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.PurchaseTaxRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import sun.util.calendar.BaseCalendar;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.sql.Date;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/13 17:56
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.base.soap.erp.service.IPurchaseTaxWsService")
@Component("iPurchaseTaxWsService")
public class PurchaseTaxWsServiceImpl implements IPurchaseTaxWsService {
    /**erp总线Service*/
    @Resource
    private IErpService iErpService;
    /**汇率接口表Service*/
    @Resource
    private IPurchaseTaxService iPurchaseTaxService;

    @Override
    public SoapResponse execute(PurchaseTaxRequest request) {
        Long startTime = System.currentTimeMillis();
        SoapResponse response = new SoapResponse();
        /**获取instId和requestTime*/
        EsbInfoRequest esbInfo = request.getEsbInfo();
        String instId = "";
        String requestTime = "";
        if (null != esbInfo) {
            instId = esbInfo.getInstId();
            requestTime = esbInfo.getRequestTime();
        }

        /**获取汇率List,并保存数据*/
        PurchaseTaxRequest.RequestInfo requestInfo = request.getRequestInfo();
        PurchaseTaxRequest.RequestInfo.PurchaseTaxs purchaseTaxsClass = null;
        List<PurchaseTaxEntity> purchaseTaxsEntityList = null;
        if (null != requestInfo) {
            purchaseTaxsClass = requestInfo.getPurchaseTaxs();
            if (null != purchaseTaxsClass) {
                purchaseTaxsEntityList = purchaseTaxsClass.getPurchaseTax();
            }
        }
        log.info("erp税率接口获取数据: " + (null != request ? request.toString() : "空"));
        List<ErpPurchaseTax> erpPurchaseTaxsList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(purchaseTaxsEntityList)) {
            for (PurchaseTaxEntity purchaseTaxEntity : purchaseTaxsEntityList) {
                if (null != purchaseTaxEntity) {
                    ErpPurchaseTax erpPurchaseTax = new ErpPurchaseTax();
                    BeanUtils.copyProperties(purchaseTaxEntity, erpPurchaseTax);
                    String effectiveFrom = purchaseTaxEntity.getEffectiveFrom();
                    String effectiveTo = purchaseTaxEntity.getEffectiveTo();
                    erpPurchaseTax.setEffectiveFrom(effectiveFrom);
                    erpPurchaseTax.setEffectiveTo(effectiveTo);
                    erpPurchaseTaxsList.add(erpPurchaseTax);
                }
            }
            response = iErpService.saveOrUpdatePurchaseTaxs(erpPurchaseTaxsList, instId, requestTime);
        }
        log.info("erp税率接口插入数据用时:"+(System.currentTimeMillis()-startTime)/1000 +"秒");
        return response;
    }
}
