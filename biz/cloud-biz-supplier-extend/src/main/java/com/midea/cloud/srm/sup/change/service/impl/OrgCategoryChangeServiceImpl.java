package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.CategoryRelChange;
import com.midea.cloud.srm.model.supplier.change.entity.OrgCategoryChange;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategoryQueryDTO;
import com.midea.cloud.srm.sup.change.mapper.OrgCategoryChangeMapper;
import com.midea.cloud.srm.sup.change.service.IDimFieldContextChangeService;
import com.midea.cloud.srm.sup.change.service.IOrgCategoryChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  组织与品类变更 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-28 13:59:38
 *  修改内容:
 * </pre>
*/
@Service
public class OrgCategoryChangeServiceImpl extends ServiceImpl<OrgCategoryChangeMapper, OrgCategoryChange> implements IOrgCategoryChangeService {
    @Autowired
    private IDimFieldContextChangeService iDimFieldContextChangeService;

    @Override
    public List<OrgCategoryChange> getByChangeId(Long chanageId) {
        OrgCategoryChange queryOrgCategoryChange = new OrgCategoryChange();
        queryOrgCategoryChange.setChangeId(chanageId);
        List<OrgCategoryChange> orgCategoryChanges = chanageId != null?this.list(new QueryWrapper<>(queryOrgCategoryChange)):null;
        for(OrgCategoryChange orgCategoryChange:orgCategoryChanges){
            if(orgCategoryChange != null){
                Map<String,Object> dimFieldContexts = iDimFieldContextChangeService.findByOrderId(orgCategoryChange.getOrgCategoryChangeId());
                orgCategoryChange.setDimFieldContexts(dimFieldContexts);
            }
        }
        return orgCategoryChanges;
    }

    @Override
    public void saveOrUpdateOrgCategory(OrgCategoryChange orgCategoryChange, Long companyId, Long changeId) {
        orgCategoryChange.setCompanyId(companyId);
        orgCategoryChange.setChangeId(changeId);
        if(orgCategoryChange.getOrgCategoryChangeId() != null){
            orgCategoryChange.setLastUpdateDate(new Date());
        }else{
            orgCategoryChange.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            orgCategoryChange.setOrgCategoryChangeId(id);
            if(orgCategoryChange.getOrgCategoryId() == null){
                orgCategoryChange.setOrgCategoryId(id);
            }
        }

        this.saveOrUpdate(orgCategoryChange);
        if(!CollectionUtils.isEmpty(orgCategoryChange.getDimFieldContexts())){
            iDimFieldContextChangeService.saveOrUpdateList(orgCategoryChange.getDimFieldContexts(),
                    orgCategoryChange.getOrgCategoryChangeId(),
                    orgCategoryChange.getCategoryId(),
                    companyId,
                    changeId);
        }
    }

    @Override
    public void removeByChangeId(Long changeId) {
        OrgCategoryChange deleteOrgCa = new OrgCategoryChange();
        deleteOrgCa.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deleteOrgCa));
    }

    @Override
    public PageInfo<OrgCategoryChange> listPageChangeByParam(OrgCategoryQueryDTO orgCategoryQueryDTO) {
        List<OrgCategoryChange> orgCategoryChanges = this.list(new QueryWrapper<>(new OrgCategoryChange()
                .setOrgCategoryId(orgCategoryQueryDTO.getOrgCategoryId())
                .setCategoryId(orgCategoryQueryDTO.getCategoryId())));
        return new PageInfo<>(orgCategoryChanges);
    }
}
