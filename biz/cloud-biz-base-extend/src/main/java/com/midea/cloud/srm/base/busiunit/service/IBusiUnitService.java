package com.midea.cloud.srm.base.busiunit.service;

import com.midea.cloud.srm.model.base.organization.entity.BusinessUnits;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <pre>
 *  ERP 接口
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/21 19:24
 *  修改内容:
 * </pre>
 */
public interface IBusiUnitService {

    /**
     * Description 获取库存组织信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.07.30
     **/
    SoapResponse execute(BusinessUnits businessUnits, String instId, String requestTime);

    /**
     * Description 获取库存组织集合信息,并保存
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.07.30
     **/
    SoapResponse execute(List<BusinessUnits> businessUnitsList, List<Organization> updateBusiOrgList, String instId, String requestTime);
}
