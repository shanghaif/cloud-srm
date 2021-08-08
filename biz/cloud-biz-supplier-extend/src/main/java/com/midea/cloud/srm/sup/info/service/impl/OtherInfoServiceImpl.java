package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.info.entity.OtherInfo;
import com.midea.cloud.srm.sup.dim.service.IDimFieldContextService;
import com.midea.cloud.srm.sup.info.mapper.OtherInfoMapper;
import com.midea.cloud.srm.sup.info.service.IOtherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;

/**
*  <pre>
 *  其他信息 服务实现类
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
public class OtherInfoServiceImpl extends ServiceImpl<OtherInfoMapper, OtherInfo> implements IOtherInfoService {
    @Autowired
    private IDimFieldContextService dimFieldContextService;

    @Override
    @Transactional
    public void saveOrUpdateOtherInfo(OtherInfo otherInfo, Long companyId) {
        otherInfo.setCompanyId(companyId);
        if(otherInfo.getOtherInfoId() != null){
            otherInfo.setLastUpdateDate(new Date());
        }else{
            otherInfo.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            otherInfo.setOtherInfoId(id);
        }
        this.saveOrUpdate(otherInfo);
        if(null != otherInfo.getDimFieldContexts() && !CollectionUtils.isEmpty(otherInfo.getDimFieldContexts())){
            dimFieldContextService.saveOrUpdateList(otherInfo.getDimFieldContexts(),otherInfo.getOtherInfoId(),companyId);
        }
    }

    @Override
    public OtherInfo getByCompanyId(Long companyId) {
        QueryWrapper<OtherInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("COMPANY_ID",companyId);
        OtherInfo otherInfo = companyId != null?this.getOne(queryWrapper):null;
        if(otherInfo != null) {
            Map<String,Object> dimFieldContexts = dimFieldContextService.findByOrderId(otherInfo.getOtherInfoId());
            otherInfo.setDimFieldContexts(dimFieldContexts);
        }
        return otherInfo;
    }

    @Override
    public void removeByCompanyId(Long companyId) {
        QueryWrapper<OtherInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_ID",companyId);
        this.remove(wrapper);
    }
}
