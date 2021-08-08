package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.info.entity.BusinessInfo;
import com.midea.cloud.srm.sup.dim.service.IDimFieldContextService;
import com.midea.cloud.srm.sup.info.mapper.BusinessInfoMapper;
import com.midea.cloud.srm.sup.info.service.IBusinessInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  业务信息 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-07 10:30:12
 *  修改内容:
 * </pre>
*/
@Service
public class BusinessInfoServiceImpl extends ServiceImpl<BusinessInfoMapper, BusinessInfo> implements IBusinessInfoService {
    @Autowired
    private IDimFieldContextService dimFieldContextService;

    @Override
    public void saveOrUpdateBusinessInfo(BusinessInfo businessInfo, Long companyId) {
        businessInfo.setCompanyId(companyId);
        if(businessInfo.getBusinessInfoId() != null){
            businessInfo.setLastUpdateDate(new Date());

        }else{
            businessInfo.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            businessInfo.setBusinessInfoId(id);
        }
        this.saveOrUpdate(businessInfo);
        if(null != businessInfo.getDimFieldContexts() && !CollectionUtils.isEmpty(businessInfo.getDimFieldContexts())){
            dimFieldContextService.saveOrUpdateList(businessInfo.getDimFieldContexts(),businessInfo.getBusinessInfoId(),companyId);
        }
    }

    @Override
    public BusinessInfo getByCompanyId(Long companyId) {
        QueryWrapper<BusinessInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("COMPANY_ID",companyId);
        BusinessInfo businessInfo = companyId != null?this.getOne(queryWrapper):null;
//        ceea,隆基不需要
//        if(businessInfo != null) {
//            Map<String,Object> dimFieldContexts = dimFieldContextService.findByOrderId(businessInfo.getBusinessInfoId());
//            businessInfo.setDimFieldContexts(dimFieldContexts);
//        }
        return businessInfo;
    }

    @Override
    public void removeByCompanyId(Long companyId) {
        QueryWrapper<BusinessInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_ID",companyId);
        this.remove(wrapper);
    }

    @Override
    public List<BusinessInfo> listByCompanyId(Long companyId) {
        QueryWrapper<BusinessInfo> queryWrapper = new QueryWrapper<>(new BusinessInfo().setCompanyId(companyId));
        List<BusinessInfo> businessInfos = companyId != null ? this.list(queryWrapper) : null;
        return businessInfos;
    }
}
