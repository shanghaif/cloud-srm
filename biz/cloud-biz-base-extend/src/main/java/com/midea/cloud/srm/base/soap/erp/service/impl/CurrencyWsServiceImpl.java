package com.midea.cloud.srm.base.soap.erp.service.impl;

import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.base.organization.service.IErpCurrencyService;
import com.midea.cloud.srm.base.soap.erp.service.ICurrencyWsService;
import com.midea.cloud.srm.model.base.organization.entity.ErpCurrency;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.CurrencyEntity;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.CurrencyRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  ERP币种接口实现WebService
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/25 14:50
 *  修改内容:
 * </pre>
 */

@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.base.soap.erp.service.ICurrencyWsService")
@Component("iCurrencyWsService")
public class CurrencyWsServiceImpl implements ICurrencyWsService {

    /**erp总线Service*/
    @Resource
    private IErpService iErpService;
    /**币种接口表Service*/
    @Resource
    private IErpCurrencyService iErpCurrencyService;

    public SoapResponse execute(CurrencyRequest request) {
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

        /**币种List,并保存数据*/
        CurrencyRequest.RequestInfo requestInfo = request.getRequestInfo();
        CurrencyRequest.RequestInfo.Currencys currencysClass = null;
        List<CurrencyEntity> currencysEntityList = null;
        if (null != requestInfo) {
            currencysClass = requestInfo.getCurrencys();
            if (null != currencysClass) {
                currencysEntityList = currencysClass.getCurrency();
            }
        }
        log.info("币种接口数据: " + (null != request ? request.toString() : "空"));
        List<ErpCurrency> erpCurrencysList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(currencysEntityList)) {
            for (CurrencyEntity currencyEntity : currencysEntityList) {
                if (null != currencyEntity) {
                    ErpCurrency erpCurrency = new ErpCurrency();
                    BeanUtils.copyProperties(currencyEntity, erpCurrency);
                    String precision = currencyEntity.getPrecision();
                    erpCurrency.setGiPrecision(new BigDecimal(precision));
                    String extendedPrecision = currencyEntity.getExtendedPrecision();
                    erpCurrency.setExtendedPrecision(new BigDecimal(extendedPrecision));
                    String minimumAccountableUnit = currencyEntity.getMinimumAccountableUnit();
                    erpCurrency.setMinimumAccountableUnit(new BigDecimal(minimumAccountableUnit));
                    erpCurrencysList.add(erpCurrency);
                }
            }
            response = iErpService.saveOrUpdateCurrencys(erpCurrencysList, instId, requestTime);
        }
        log.info("erp获取币种接口插入数据用时:"+(System.currentTimeMillis()-startTime)/1000 +"秒");
        return response;
    }
}
