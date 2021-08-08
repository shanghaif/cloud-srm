package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.info.entity.HolderInfo;
import com.midea.cloud.srm.sup.dim.service.IDimFieldContextService;
import com.midea.cloud.srm.sup.info.mapper.HolderInfoMapper;
import com.midea.cloud.srm.sup.info.service.IHolderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;

/**
*  <pre>
 *  股东信息 服务实现类
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
public class HolderInfoServiceImpl extends ServiceImpl<HolderInfoMapper, HolderInfo> implements IHolderInfoService {
    @Autowired
    private IDimFieldContextService dimFieldContextService;

    @Override
    @Transactional
    public void saveOrUpdateHolder(HolderInfo holderInfo, Long companyId) {
        holderInfo.setCompanyId(companyId);
        if(holderInfo.getHolderInfoId() != null){
            holderInfo.setLastUpdateDate(new Date());
        }else{
            holderInfo.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            holderInfo.setHolderInfoId(id);
        }
        this.saveOrUpdate(holderInfo);
        if(null != holderInfo.getDimFieldContexts() && !CollectionUtils.isEmpty(holderInfo.getDimFieldContexts())){
            dimFieldContextService.saveOrUpdateList(holderInfo.getDimFieldContexts(),holderInfo.getHolderInfoId(),companyId);
        }
    }

    @Override
    public HolderInfo getByCompanyId(Long companyId) {
        QueryWrapper<HolderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("COMPANY_ID",companyId);
        HolderInfo holderInfo = companyId != null?this.getOne(queryWrapper):null;
        if(holderInfo != null) {
           Map<String,Object> dimFieldContexts = dimFieldContextService.findByOrderId(holderInfo.getHolderInfoId());
            holderInfo.setDimFieldContexts(dimFieldContexts);
        }
        return holderInfo;
    }

    @Override
    public void removeByCompanyId(Long companyId) {
        QueryWrapper<HolderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_ID",companyId);
        this.remove(wrapper);
    }
}
