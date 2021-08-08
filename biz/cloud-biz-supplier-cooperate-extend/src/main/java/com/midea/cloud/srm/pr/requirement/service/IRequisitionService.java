package com.midea.cloud.srm.pr.requirement.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.Requisition;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
*  <pre>
 *  采购申请表（隆基采购申请同步） 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-01 09:45:43
 *  修改内容:
 * </pre>
*/
public interface IRequisitionService extends IService<Requisition> {

    //获取指定erp采购申请头
     List<Requisition> getResetError(Collection<Long> ids);

}
