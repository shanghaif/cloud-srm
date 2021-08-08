package com.midea.cloud.srm.pr.erp.service;

import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequisitionDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.Requisition;
import com.midea.cloud.srm.model.rbac.po.entity.PoAgent;

import java.util.List;

/**
 * <pre>
 *  Erp总线service接口 pmp模块
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/1 14:42
 *  修改内容:
 * </pre>
 */
public interface IErpService {

    /**
     * Description 新增/修改采购申请接口表
     * @Param
     * @return
     * @Author xiexh12@meicloud.com
     * @Date 2020.09.01
     * @throws
     **/
    SoapResponse saveOrUpdateRequisitions(List<RequisitionDTO> requisitionsList, String instId, String requestTime);


}
