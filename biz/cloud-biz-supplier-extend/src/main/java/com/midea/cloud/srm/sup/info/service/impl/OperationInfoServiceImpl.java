package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.info.entity.OperationInfo;
import com.midea.cloud.srm.sup.dim.service.IDimFieldContextService;
import com.midea.cloud.srm.sup.info.mapper.OperationInfoMapper;
import com.midea.cloud.srm.sup.info.service.IOperationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;

/**
*  <pre>
 *  经营情况 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 16:21:46
 *  修改内容:
 * </pre>
*/
@Service
public class OperationInfoServiceImpl extends ServiceImpl<OperationInfoMapper, OperationInfo> implements IOperationInfoService {
    @Autowired
    private IDimFieldContextService dimFieldContextService;

    @Override
    public void saveOrUpdateOpratrion(OperationInfo operationInfo, Long companyId) {
        operationInfo.setCompanyId(companyId);
        if(operationInfo.getOpInfoId() != null){
            operationInfo.setLastUpdateDate(new Date());
        }else{
            operationInfo.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            operationInfo.setOpInfoId(id);
        }
        this.saveOrUpdate(operationInfo);
        if(null != operationInfo.getDimFieldContexts() && !CollectionUtils.isEmpty(operationInfo.getDimFieldContexts())){
            dimFieldContextService.saveOrUpdateList(operationInfo.getDimFieldContexts(),operationInfo.getOpInfoId(),companyId);
        }
    }

    @Override
    public OperationInfo getByCompanyId(Long companyId) {
        QueryWrapper<OperationInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("COMPANY_ID",companyId);
        OperationInfo operationInfo = companyId != null?this.getOne(queryWrapper):null;
//        ceea,隆基不需要
//        if(operationInfo != null) {
//            Map<String,Object> dimFieldContexts = dimFieldContextService.findByOrderId(operationInfo.getOpInfoId());
//            operationInfo.setDimFieldContexts(dimFieldContexts);
//        }
        return operationInfo;
    }

    @Override
    public void removeByCompanyId(Long companyId) {
        QueryWrapper<OperationInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_ID",companyId);
        this.remove(wrapper);
    }
}
