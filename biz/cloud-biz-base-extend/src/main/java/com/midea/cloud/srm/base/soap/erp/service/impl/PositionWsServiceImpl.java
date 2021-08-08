package com.midea.cloud.srm.base.soap.erp.service.impl;

import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.base.organization.service.IPositionService;
import com.midea.cloud.srm.base.soap.erp.service.IPositionWsService;
import com.midea.cloud.srm.model.base.organization.entity.Position;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.PositionEntity;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.PositionRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
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
 *  修改日期: 2020/8/17 16:43
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.base.soap.erp.service.IPositionWsService")
@Component("iPositionWsService")
public class PositionWsServiceImpl implements IPositionWsService {

    /**erp总线Service*/
    @Resource
    private IErpService iErpService;
    /**职位接口表Service*/
    @Resource
    private IPositionService iPositionService;

    @Override
    public SoapResponse execute(PositionRequest request) {
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

        /**获取职位List,并保存数据*/
        PositionRequest.RequestInfo requestInfo = request.getRequestInfo();
        PositionRequest.RequestInfo.Positions positionsClass = null;
        List<PositionEntity> positionsEntityList = null;
        if (null != requestInfo) {
            positionsClass = requestInfo.getPositions();
            if (null != positionsClass) {
                positionsEntityList = positionsClass.getPosition();
            }
        }
        log.info("erp获取职位接口数据: " + (null != request ? request.toString() : "空"));
        List<Position> positionsList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(positionsEntityList)) {
            for (PositionEntity positionsEntity : positionsEntityList) {
                if (null != positionsEntity) {
                    Position positions = new Position();
                    BeanUtils.copyProperties(positionsEntity, positions);
                    positionsList.add(positions);
                }
            }
            response = iErpService.saveOrUpdatePositions(positionsList, instId, requestTime);
        }
        log.info("接收erp职位数据用时:"+(System.currentTimeMillis()-startTime)/1000 +"秒");
        return response;
    }
}
