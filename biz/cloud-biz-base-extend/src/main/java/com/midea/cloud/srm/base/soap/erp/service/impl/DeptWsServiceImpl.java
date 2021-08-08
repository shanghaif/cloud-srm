package com.midea.cloud.srm.base.soap.erp.service.impl;

import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.base.dept.service.IDeptService;
import com.midea.cloud.srm.base.soap.erp.service.IDeptWsService;
import com.midea.cloud.srm.model.base.dept.entity.Dept;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.DeptEntity;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.DeptRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
 *  修改日期: 2020/8/15 17:34
 *  修改内容:
 * </pre>
 */

@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.base.soap.erp.service.IDeptWsService")
@Component("iDeptWsService")
public class DeptWsServiceImpl implements IDeptWsService {

    /**erp总线Service*/
    @Resource
    private IErpService iErpService;
    /**部门接口表Service*/
    @Resource
    private IDeptService iDeptService;

    @Override
    public SoapResponse execute(DeptRequest request) {
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

        /**获取部门List,并保存数据*/
        DeptRequest.RequestInfo requestInfo = request.getRequestInfo();
        DeptRequest.RequestInfo.Depts deptsClass = null;
        List<DeptEntity> deptsEntityList = null;
        if (null != requestInfo) {
            deptsClass = requestInfo.getDepts();
            if (null != deptsClass) {
                deptsEntityList = deptsClass.getDept();
            }
        }
        log.info("erp获取部门接口数据: " + (null != request ? request.toString() : "空"));
        List<Dept> deptsList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(deptsEntityList)) {
            for (DeptEntity deptsEntity : deptsEntityList) {
                if (null != deptsEntity) {
                    Dept depts = new Dept();
                    BeanUtils.copyProperties(deptsEntity, depts);
                    String deptId = deptsEntity.getDeptId();
                    depts.setDeptid(StringUtils.isNotEmpty(deptId) ? deptId : null);
                    deptsList.add(depts);
                }
            }
            //集合不为空才报错推送的部门信息
            if(CollectionUtils.isNotEmpty(deptsList)) {
                response = iErpService.saveOrUpdateDepts(deptsList, instId, requestTime);
            }

        }
        log.info("接收erp部门数据用时:"+(System.currentTimeMillis()-startTime)/1000 +"秒");
        return response;
    }
}
