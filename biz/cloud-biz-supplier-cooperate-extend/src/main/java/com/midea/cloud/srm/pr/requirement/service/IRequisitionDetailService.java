package com.midea.cloud.srm.pr.requirement.service;

import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequisitionDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
*  <pre>
 *  采购申请明细表（隆基采购申请明细同步） 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-01 10:27:30
 *  修改内容:
 * </pre>
*/
public interface IRequisitionDetailService extends IService<RequisitionDetail> {

    //获取erp推送申请不能插入业务表的行
     List<RequisitionDetail> getErrorDetail(Long headid);

}
