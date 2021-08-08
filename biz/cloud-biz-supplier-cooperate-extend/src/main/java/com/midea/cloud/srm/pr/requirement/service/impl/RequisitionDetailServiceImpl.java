package com.midea.cloud.srm.pr.requirement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.Requisition;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequisitionDetail;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.pr.requirement.mapper.RequisitionDetailMapper;
import com.midea.cloud.srm.pr.requirement.service.IRequisitionDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  采购申请明细表（隆基采购申请明细同步） 服务实现类
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
@Service
public class RequisitionDetailServiceImpl extends ServiceImpl<RequisitionDetailMapper, RequisitionDetail> implements IRequisitionDetailService {
    //获取erp推送申请不能插入业务表的行
    @Override
    public List<RequisitionDetail> getErrorDetail(Long headid){
        QueryWrapper<RequisitionDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("LINE_IMPORT_STATUS",1);
        wrapper.eq(headid!=null,"REQUEST_HEADER_ID",headid);
        return this.list(wrapper);
    }
}
