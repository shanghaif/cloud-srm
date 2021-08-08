package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.info.entity.OrgInfo;
import com.midea.cloud.srm.sup.dim.service.IDimFieldContextService;
import com.midea.cloud.srm.sup.info.mapper.OrgInfoMapper;
import com.midea.cloud.srm.sup.info.service.IOrgInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  合作组织信息 服务实现类
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
public class OrgInfoServiceImpl extends ServiceImpl<OrgInfoMapper, OrgInfo> implements IOrgInfoService {
    @Autowired
    private IDimFieldContextService dimFieldContextService;

    @Autowired
    private IOrgInfoService iOrgInfoService;

    @Resource
    private OrgInfoMapper orgInfoMapper;

    @Override
    @Transactional
    public void saveOrUpdateOrg(OrgInfo orgInfo, Long companyId) {
        orgInfo.setCompanyId(companyId);
        if(orgInfo.getOrgInfoId() != null){
            orgInfo.setLastUpdateDate(new Date());
        }else{
            orgInfo.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            orgInfo.setOrgInfoId(id);
        }
        this.saveOrUpdate(orgInfo);
//        if(null != orgInfo.getDimFieldContexts() && !CollectionUtils.isEmpty(orgInfo.getDimFieldContexts())){
//            dimFieldContextService.saveOrUpdateList(orgInfo.getDimFieldContexts(),orgInfo.getOrgInfoId(),companyId);
//        }
    }

    @Override
    public List<OrgInfo> getByCompanyId(Long companyId) {
        QueryWrapper<OrgInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("COMPANY_ID",companyId);
        List<OrgInfo> orgInfos =  companyId != null?this.list(queryWrapper):null;
//        ceea,隆基不需要
//        if(!CollectionUtils.isEmpty(orgInfos)) {
//            for(OrgInfo orgInfo:orgInfos){
//                Map<String,Object> dimFieldContexts = dimFieldContextService.findByOrderId(orgInfo.getOrgInfoId());
//                orgInfo.setDimFieldContexts(dimFieldContexts);
//            }
//        }
        return orgInfos;
    }

    @Override
    public void removeByCompanyId(Long companyId) {
        QueryWrapper<OrgInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_ID",companyId);
        this.remove(wrapper);
    }

    @Override
    public List<OrgInfo> listOrgInfoByCompanyId(Long companyId) {
        Assert.notNull(companyId, "companyId不能为空");
        QueryWrapper<OrgInfo> queryWrapper = new QueryWrapper<>( new OrgInfo().setCompanyId(companyId));
        return iOrgInfoService.list(queryWrapper);
    }

    @Override
    public List<OrgInfo> listOrgInfoByServiceStatusAndCompanyId(Long companyId, String...serviceStatus) {
        Assert.notEmpty(serviceStatus, "serviceStatus不能为空");
        Assert.notNull(companyId, "companyId不能为空");
        OrgInfo orgInfo = new OrgInfo().setCompanyId(companyId);
        //会有两个筛选服务状态条件
        if (serviceStatus.length == 1) {
            orgInfo.setServiceStatus(serviceStatus[0]);
        }

        QueryWrapper<OrgInfo> queryWrapper = new QueryWrapper<>(orgInfo);

        if (serviceStatus.length == 2) {
            queryWrapper.eq("SERVICE_STATUS", serviceStatus[0])
                        .or().eq("SERVICE_STATUS", serviceStatus[1]);
        }
        return iOrgInfoService.list(queryWrapper);
    }

    @Override
    public OrgInfo getOrgInfoByOrgIdAndCompanyId(Long orgId, Long companyId) {
        QueryWrapper<OrgInfo> queryWrapper = new QueryWrapper<>(new OrgInfo()
                .setCompanyId(companyId).setOrgId(orgId));
        List<OrgInfo> orgInfos = iOrgInfoService.list(queryWrapper);
        OrgInfo orgInfo = null;
        if (!CollectionUtils.isEmpty(orgInfos)) {
            orgInfo = orgInfos.get(0);
        }
        return orgInfo;
    }

    @Override
    public void updateOrgInfoServiceStatus(OrgInfo orgInfo) {
        UpdateWrapper<OrgInfo> updateWrapper = new UpdateWrapper<>(new OrgInfo().setOrgId(orgInfo.getOrgId())
                .setCompanyId(orgInfo.getCompanyId()));
        updateWrapper.set("END_DATE", orgInfo.getEndDate()).set("START_DATE", orgInfo.getStartDate());
        this.update(orgInfo, updateWrapper);
    }

    @Override
    public List<OrgInfo> queryOrgInfoByVendorId(Long companyId) {
        return this.baseMapper.queryOrgInfoByVendorId(companyId);
    }
}
