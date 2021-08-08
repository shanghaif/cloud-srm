package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.OrgInfoChange;
import com.midea.cloud.srm.model.supplier.change.entity.OtherInfoChange;
import com.midea.cloud.srm.sup.change.mapper.OtherInfoChangeMapper;
import com.midea.cloud.srm.sup.change.service.IDimFieldContextChangeService;
import com.midea.cloud.srm.sup.change.service.IOtherInfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;

/**
*  <pre>
 *  其他信息变更表 服务实现类
 * </pre>
*
* @author zhuwl7@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-26 14:15:34
 *  修改内容:
 * </pre>
*/
@Service
public class OtherInfoChangeServiceImpl extends ServiceImpl<OtherInfoChangeMapper, OtherInfoChange> implements IOtherInfoChangeService {
    @Autowired
    private IDimFieldContextChangeService iDimFieldContextChangeService;

    @Override
    public OtherInfoChange saveOrUpdateOther(OtherInfoChange otherInfoChange, Long companyId, Long changeId) {
        otherInfoChange.setCompanyId(companyId);
        otherInfoChange.setChangeId(changeId);
        if(otherInfoChange.getOtherChangeId() != null){
            otherInfoChange.setLastUpdateDate(new Date());
            this.updateById(otherInfoChange);
        }else{
            otherInfoChange.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            otherInfoChange.setOtherChangeId(id);
            if(otherInfoChange.getOtherInfoId() == null){
                otherInfoChange.setOtherInfoId(id);
            }
            this.save(otherInfoChange);
        }

        if(!CollectionUtils.isEmpty(otherInfoChange.getDimFieldContexts())){
            iDimFieldContextChangeService.saveOrUpdateList(otherInfoChange.getDimFieldContexts(),
                    otherInfoChange.getOtherChangeId(),
                    otherInfoChange.getOtherInfoId(),
                    companyId,changeId);
        }
        return otherInfoChange;
    }

    @Override
    public OtherInfoChange getByChangeId(Long chanageId) {
        OtherInfoChange queryOtherInfoChange = new OtherInfoChange();
        queryOtherInfoChange.setChangeId(chanageId);
        OtherInfoChange otherInfoChange =  chanageId != null?this.getOne(new QueryWrapper<>(queryOtherInfoChange)):null;
        if(otherInfoChange != null){
            Map<String,Object> dimFieldContexts = iDimFieldContextChangeService.findByOrderId(otherInfoChange.getOtherChangeId());
            otherInfoChange.setDimFieldContexts(dimFieldContexts);
        }
        return otherInfoChange;
    }

    @Override
    public void removeByChangeId(Long changeId) {
        OtherInfoChange deleteOther = new OtherInfoChange();
        deleteOther.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deleteOther));
    }
}
