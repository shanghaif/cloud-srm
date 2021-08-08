package com.midea.cloud.srm.base.soap.erp.service.impl;

import com.midea.cloud.common.constants.RedisKey;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.redis.RedisLockUtil;
import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.base.organization.service.IGidailyRateService;
import com.midea.cloud.srm.base.soap.erp.service.IGidailyRateWsService;
import com.midea.cloud.srm.model.base.organization.entity.GidailyRate;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.GidailyRateEntity;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.GidailyRateRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *  ERP汇率接口实现WebService
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/25 11:23
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.base.soap.erp.service.IGidailyRateWsService")
@Component("iGidailyRateWsService")

public class GidailyRateWsServiceImpl implements IGidailyRateWsService {
    /**erp总线Service*/
    @Resource
    private IErpService iErpService;
    @Resource
    private RedisLockUtil redisLockUtil;
    /**汇率接口表Service*/
    @Resource
    private IGidailyRateService iGidailyRateService;

    @Override
    public SoapResponse execute(GidailyRateRequest request) {
        SoapResponse soapResponse = null;
        try {
            // 加锁
            redisLockUtil.lock(RedisKey.GIDAILY_RATEWS_SERVICE_LOCK,60);
            // 执行业务
            soapResponse = getSoapResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(Arrays.toString(e.getStackTrace()));
        }finally {
            // 释放锁
            redisLockUtil.unlock(RedisKey.GIDAILY_RATEWS_SERVICE_LOCK);
        }
        return soapResponse;
    }

    public SoapResponse getSoapResponse(GidailyRateRequest request) {
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
        GidailyRateRequest.RequestInfo requestInfo = request.getRequestInfo();
        GidailyRateRequest.RequestInfo.GidailyRates gidailyRatesClass = null;
        List<GidailyRateEntity> gidailyRatesEntityList = null;
        if (null != requestInfo) {
            gidailyRatesClass = requestInfo.getGidailyRates();
            if (null != gidailyRatesClass) {
                gidailyRatesEntityList = gidailyRatesClass.getGidailyRate();
            }
        }
        log.info("erp获取汇率接口数据: " + (null != request ? request.toString() : "空"));
        List<GidailyRate> gidailyRatesList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(gidailyRatesEntityList)) {
            for (GidailyRateEntity gidailyRateEntity : gidailyRatesEntityList) {
                if (null != gidailyRateEntity) {
                    GidailyRate gidailyRate = new GidailyRate();
                    BeanUtils.copyProperties(gidailyRateEntity, gidailyRate);
                    //如果日期是按年月日时分秒的,按这个转换start
                    //2020-11-16, xiexh12@meicloud.com
                    /*
                        String creationDate = gidailyRateEntity.getCreationDate();
                        String lastUpdateDate = gidailyRateEntity.getLastUpdateDate();
                    */
                    //如果日期是按年月日时分秒的,按这个转换end


                    //如果日期是年月日,按这个转换start
                    String creationDate = DateUtil.formatDateByString(gidailyRateEntity.getCreationDate());
                    String lastUpdateDate = DateUtil.formatDateByString(gidailyRateEntity.getLastUpdateDate());
                    //如果日期是年月日,按这个转换start

                    gidailyRate.setErpCreationDate(creationDate)
                            .setErpCreatedBy(gidailyRateEntity.getCreatedBy())
                            .setErpLastUpdateDate(lastUpdateDate)
                            .setErpLastUpdatedBy(gidailyRateEntity.getLastUpdatedBy());
                    gidailyRatesList.add(gidailyRate);
                }
            }
            response = iErpService.saveOrUpdateGidailyRates(gidailyRatesList, instId, requestTime);
        }
        log.info("erp获取汇率接口插入数据用时:"+(System.currentTimeMillis()-startTime)/1000 +"秒");
        return response;
    }
}
