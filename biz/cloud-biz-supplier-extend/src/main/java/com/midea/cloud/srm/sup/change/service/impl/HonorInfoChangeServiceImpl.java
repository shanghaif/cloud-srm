package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.HolderInfoChange;
import com.midea.cloud.srm.model.supplier.change.entity.HonorInfoChange;
import com.midea.cloud.srm.sup.change.mapper.HonorInfoChangeMapper;
import com.midea.cloud.srm.sup.change.service.IDimFieldContextChangeService;
import com.midea.cloud.srm.sup.change.service.IHonorInfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
*  <pre>
 *  荣誉信息变更 服务实现类
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
public class HonorInfoChangeServiceImpl extends ServiceImpl<HonorInfoChangeMapper, HonorInfoChange> implements IHonorInfoChangeService {
    @Autowired
    private IDimFieldContextChangeService iDimFieldContextChangeService;

    @Override
    public HonorInfoChange getByChangeId(Long chanageId) {
        HonorInfoChange queryHonorInfoChange = new HonorInfoChange();
        queryHonorInfoChange.setChangeId(chanageId);
        HonorInfoChange honorInfoChange = chanageId != null?this.getOne(new QueryWrapper<>(queryHonorInfoChange)):null;
        if(honorInfoChange != null){
            Map<String,Object> dimFieldContexts = iDimFieldContextChangeService.findByOrderId(honorInfoChange.getHonorChangeId());
            honorInfoChange.setDimFieldContexts(dimFieldContexts);
        }
        return honorInfoChange;
    }

    @Override
    public void saveOrUpdateHonor(HonorInfoChange honorInfoChange, Long companyId, Long changeId) {
        honorInfoChange.setChangeId(changeId);
        honorInfoChange.setCompanyId(companyId);
        if(honorInfoChange.getHonorChangeId() != null){
            this.updateById(honorInfoChange);
        }else {
            Long id = IdGenrator.generate();
            honorInfoChange.setHonorChangeId(id);
            if(honorInfoChange.getHonorInfoId() ==null){
                honorInfoChange.setHonorInfoId(id);
            }
            this.save(honorInfoChange);
        }
        if(!CollectionUtils.isEmpty(honorInfoChange.getDimFieldContexts())){
            iDimFieldContextChangeService.saveOrUpdateList(honorInfoChange.getDimFieldContexts(),
                    honorInfoChange.getHonorChangeId(),
                    honorInfoChange.getHonorInfoId(),
                    honorInfoChange.getCompanyId(),
                    honorInfoChange.getChangeId()
            );
        }
    }

    @Override
    public void removeByChangeId(Long changeId) {
        HonorInfoChange deleteHonor = new HonorInfoChange();
        deleteHonor.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deleteHonor));
    }
}
