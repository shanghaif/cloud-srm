package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.OrgCategoryChange;
import com.midea.cloud.srm.model.supplier.change.entity.OrgInfoChange;
import com.midea.cloud.srm.sup.change.mapper.OrgInfoChangeMapper;
import com.midea.cloud.srm.sup.change.service.IDimFieldContextChangeService;
import com.midea.cloud.srm.sup.change.service.IOrgInfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  合作组织信息变更 服务实现类
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
public class OrgInfoChangeServiceImpl extends ServiceImpl<OrgInfoChangeMapper, OrgInfoChange> implements IOrgInfoChangeService {
    @Autowired
    private IDimFieldContextChangeService iDimFieldContextChangeService;

    @Override
    public List<OrgInfoChange> getByChangeId(Long chanageId) {
        OrgInfoChange queryOrgInfoChange = new OrgInfoChange();
        queryOrgInfoChange.setChangeId(chanageId);
        List<OrgInfoChange> orgCategoryChanges = chanageId != null?this.list(new QueryWrapper<>(queryOrgInfoChange)):null;
        for(OrgInfoChange orgInfoChange:orgCategoryChanges){
            if(orgInfoChange != null){
                Map<String,Object> dimFieldContexts = iDimFieldContextChangeService.findByOrderId(orgInfoChange.getOrgChangeId());
                orgInfoChange.setDimFieldContexts(dimFieldContexts);
            }
        }
        return orgCategoryChanges;
    }

    @Override
    public void saveOrUpdateOrg(OrgInfoChange orgInfoChange, Long companyId, Long changeId) {
        orgInfoChange.setChangeId(changeId);
        orgInfoChange.setCompanyId(companyId);
        if(orgInfoChange.getOrgChangeId() != null){
            this.updateById(orgInfoChange);
        }else {
            Long id = IdGenrator.generate();
            orgInfoChange.setOrgChangeId(id);
            if(orgInfoChange.getOrgInfoId() ==null){
                orgInfoChange.setOrgInfoId(id);
            }
            this.save(orgInfoChange);
        }
        if(!CollectionUtils.isEmpty(orgInfoChange.getDimFieldContexts())){
            iDimFieldContextChangeService.saveOrUpdateList(orgInfoChange.getDimFieldContexts(),
                    orgInfoChange.getOrgChangeId(),
                    orgInfoChange.getOrgInfoId(),
                    orgInfoChange.getCompanyId(),
                    orgInfoChange.getChangeId()
            );
        }
    }

    @Override
    public void removeByChangeId(Long changeId) {
        OrgInfoChange deleteOrg = new OrgInfoChange();
        deleteOrg.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deleteOrg));
    }
}
