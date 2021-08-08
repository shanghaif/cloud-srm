package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.BankInfoChange;
import com.midea.cloud.srm.model.supplier.change.entity.SiteInfoChange;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.sup.change.mapper.SiteInfoChangeMapper;
import com.midea.cloud.srm.sup.change.service.IDimFieldContextChangeService;
import com.midea.cloud.srm.sup.change.service.ISiteInfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  地点信息变更 服务实现类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-25 10:44:41
 *  修改内容:
 * </pre>
*/
@Service
public class SiteInfoChangeServiceImpl extends ServiceImpl<SiteInfoChangeMapper, SiteInfoChange> implements ISiteInfoChangeService {

    @Autowired
    private IDimFieldContextChangeService iDimFieldContextChangeService;

    @Override
    public List<SiteInfoChange> getByChangeId(Long changeId) {
        SiteInfoChange querySiteInfoChange = new SiteInfoChange();
        querySiteInfoChange.setChangeId(changeId);
        List<SiteInfoChange> siteInfoChanges = changeId != null?this.list(new QueryWrapper<>(querySiteInfoChange)):null;
//        for (SiteInfoChange siteInfoChange : siteInfoChanges){
//            if (siteInfoChange != null) {
//                Map<String, Object> dimFieldContexts = iDimFieldContextChangeService.findByOrderId(siteInfoChange.getSiteChangeId());
//                siteInfoChange.setDimFieldContexts(dimFieldContexts);
//            }
//        }
        return siteInfoChanges;
    }

    @Override
    public void saveOrUpdateSite(SiteInfoChange siteInfoChange, Long companyId, Long changeId) {
        siteInfoChange.setChangeId(changeId).setCompanyId(companyId);
        if (siteInfoChange.getSiteChangeId() != null){
            this.updateById(siteInfoChange);
        }else {
            Long id = IdGenrator.generate();
            siteInfoChange.setSiteChangeId(id);
            if (siteInfoChange.getSiteInfoId() == null) {
                siteInfoChange.setSiteInfoId(id);
            }
            this.save(siteInfoChange);
        }
//        if (!CollectionUtils.isEmpty(siteInfoChange.getDimFieldContexts())){
//            iDimFieldContextChangeService.saveOrUpdateList(siteInfoChange.getDimFieldContexts(),
//                    siteInfoChange.getSiteChangeId(),
//                    siteInfoChange.getSiteInfoId(),
//                    siteInfoChange.getCompanyId(),
//                    siteInfoChange.getChangeId()
//            );
//        }
    }

    @Override
    public void removeByChangeId(Long changeId) {
        SiteInfoChange deleteSite = new SiteInfoChange();
        deleteSite.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deleteSite));
    }

    /**
     * 删除供应商地点信息
     * modifiedBy xiexh12@meicloud.com
     * @param siteChangeId
     */
    @Override
    public void removeBySiteChangeId(Long siteChangeId) {
        QueryWrapper<SiteInfoChange> queryWrapper = new QueryWrapper<>(
                new SiteInfoChange().setSiteChangeId(siteChangeId)
        );
        this.remove(queryWrapper);
    }
}
