package com.midea.cloud.srm.supcooperate.soap.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.enums.pm.po.CeeaWarehousingReturnDetailEnum;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.base.soap.erp.suppliercooperate.dto.WarehousingReturnDetailEntity;
import com.midea.cloud.srm.model.base.soap.erp.suppliercooperate.dto.WarehousingReturnDetailRequest;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.PurchaseRequirementDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetailErp;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailErpService;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailService;
import com.midea.cloud.srm.supcooperate.soap.erp.service.IWarehousingReturnDetailWsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.jws.WebService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/8/22 14:35
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.supcooperate.soap.erp.service.IWarehousingReturnDetailWsService")
@Component("iWarehousingReturnDetailWsService")
public class WarehousingReturnDetailWsServiceImpl implements IWarehousingReturnDetailWsService {
    @Autowired
    private IWarehousingReturnDetailService warehousingReturnDetailService;

    @Override
    public SoapResponse execute(WarehousingReturnDetailRequest request){
        /**获取instId和requestTime*/
        EsbInfoRequest esbInfo = request.getEsbInfo();
        String instId = "";
        String requestTime = "";
        if(null != esbInfo){
            instId = esbInfo.getInstId();
            requestTime = esbInfo.getRequestTime();
        }
        //获取地址接口List,并保存数据
        WarehousingReturnDetailRequest.RequestInfo requestInfo = request.getRequestInfo();
        WarehousingReturnDetailRequest.RequestInfo.WarehousingReturnDetails warehousingReturnDetailsClass = null;
        List<WarehousingReturnDetailEntity> warehousingReturnDetailEntityList = null;
        if(null != requestInfo){
            warehousingReturnDetailsClass = requestInfo.getWarehousingReturnDetails();
            if(null != warehousingReturnDetailsClass){
                warehousingReturnDetailEntityList = warehousingReturnDetailsClass.getWarehousingReturnDetail();
            }
        }
        log.info("erp获取地址接口数据: " + (null != request ? request.toString() : "空"));
        return warehousingReturnDetailService.acceptErpData(warehousingReturnDetailEntityList,instId,requestTime);

    }

}
