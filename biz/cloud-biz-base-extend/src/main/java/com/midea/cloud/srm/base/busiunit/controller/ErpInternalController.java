package com.midea.cloud.srm.base.busiunit.controller;

import com.midea.cloud.srm.base.busiunit.service.IBusiUnitService;
import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.model.base.organization.entity.BusinessUnits;
import com.midea.cloud.srm.model.base.organization.entity.InvOrganization;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <pre>
 *  Erp接口Controller
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/22 13:27
 *  修改内容:
 * </pre>
 */
@RestController
@Slf4j
public class ErpInternalController {

    /**ERP业务实体Service接口*/
    @Resource
    private IBusiUnitService iBusiUnitService;
    /**ERPService接口*/
    @Resource
    private IErpService iErpService;

    /**
     * Description 获取库存组织信息，并新增或修改
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.07.30
     * @throws
     **/
    @PostMapping("/base-anon/internal/erp/executeBusiUnit")
    public SoapResponse executeBusiUnit(@RequestBody BusinessUnits businessUnits, String instId, String requestTime){
        SoapResponse response = iBusiUnitService.execute(businessUnits, instId, requestTime);
        return response;
    }

    /**
     * Description 新增或修改库存组织表
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.08.11
     * @throws
     **/
    @PostMapping("/base-anon/internal/erp/saveOrUpdateInvOrganiztions")
    public SoapResponse saveOrUpdateInvOrganiztions(@RequestBody InvOrganization invOrganization, String instId, String requestTime){
        SoapResponse response = iErpService.saveOrUpdateInvOrganiztions(invOrganization, instId, requestTime);
        return response;
    }

}
