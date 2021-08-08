package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.info.entity.HonorInfo;
import com.midea.cloud.srm.sup.dim.service.IDimFieldContextService;
import com.midea.cloud.srm.sup.info.mapper.HonorInfoMapper;
import com.midea.cloud.srm.sup.info.service.IHonorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;

/**
*  <pre>
 *  荣誉信息 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 16:09:09
 *  修改内容:
 * </pre>
*/
@Service
public class HonorInfoServiceImpl extends ServiceImpl<HonorInfoMapper, HonorInfo> implements IHonorInfoService {
    @Autowired
    private IDimFieldContextService dimFieldContextService;

    public  HonorInfo getByCompanyId(Long companyId) {
        QueryWrapper<HonorInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("COMPANY_ID",companyId);
        HonorInfo honorInfo =  companyId != null?this.getOne(queryWrapper):null;
        if(honorInfo != null) {
            Map<String,Object> dimFieldContexts = dimFieldContextService.findByOrderId(honorInfo.getHonorInfoId());
            honorInfo.setDimFieldContexts(dimFieldContexts);
        }
        return honorInfo;
    }

    @Override
    @Transactional
    public void saveOrUpdateHonor(HonorInfo honorInfo, Long companyId) {
        honorInfo.setCompanyId(companyId);
        if(honorInfo.getHonorInfoId() != null){
            honorInfo.setLastUpdateDate(new Date());
        }else{
            honorInfo.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            honorInfo.setHonorInfoId(id);
        }
        this.saveOrUpdate(honorInfo);
        if(null != honorInfo.getDimFieldContexts() && !CollectionUtils.isEmpty(honorInfo.getDimFieldContexts())){
            dimFieldContextService.saveOrUpdateList(honorInfo.getDimFieldContexts(),honorInfo.getHonorInfoId(),companyId);
        }
    }

    @Override
    public void removeByCompanyId(Long companyId) {
        QueryWrapper<HonorInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_ID",companyId);
        this.remove(wrapper);
    }
}
