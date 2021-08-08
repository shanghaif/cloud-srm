package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.FinanceInfoChange;
import com.midea.cloud.srm.model.supplier.change.entity.HolderInfoChange;
import com.midea.cloud.srm.sup.change.mapper.HolderInfoChangeMapper;
import com.midea.cloud.srm.sup.change.service.IDimFieldContextChangeService;
import com.midea.cloud.srm.sup.change.service.IHolderInfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
*  <pre>
 *  股东信息变更 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-28 14:13:08
 *  修改内容:
 * </pre>
*/
@Service
public class HolderInfoChangeServiceImpl extends ServiceImpl<HolderInfoChangeMapper, HolderInfoChange> implements IHolderInfoChangeService {
    @Autowired
    private IDimFieldContextChangeService iDimFieldContextChangeService;

    @Override
    public HolderInfoChange getByChangeId(Long chanageId) {
        HolderInfoChange queryHolderInfoChange = new HolderInfoChange();
        queryHolderInfoChange.setChangeId(chanageId);
        HolderInfoChange holderInfoChange =  chanageId != null?this.getOne(new QueryWrapper<>(queryHolderInfoChange)):null;
        if(holderInfoChange != null){
            Map<String,Object> dimFieldContexts = iDimFieldContextChangeService.findByOrderId(holderInfoChange.getHolderChangeId());
            holderInfoChange.setDimFieldContexts(dimFieldContexts);
        }
        return holderInfoChange;
    }

    @Override
    public void saveOrUpdateHolder(HolderInfoChange holderInfoChange, Long companyId, Long changeId) {
        holderInfoChange.setChangeId(changeId);
        holderInfoChange.setCompanyId(companyId);
        if(holderInfoChange.getHolderChangeId() != null){
            this.updateById(holderInfoChange);
        }else {
            Long id = IdGenrator.generate();
            holderInfoChange.setHolderChangeId(id);
            if(holderInfoChange.getHolderInfoId() ==null){
                holderInfoChange.setHolderInfoId(id);
            }
            this.save(holderInfoChange);
        }
        if(!CollectionUtils.isEmpty(holderInfoChange.getDimFieldContexts())){
            iDimFieldContextChangeService.saveOrUpdateList(holderInfoChange.getDimFieldContexts(),
                    holderInfoChange.getHolderChangeId(),
                    holderInfoChange.getHolderInfoId(),
                    holderInfoChange.getCompanyId(),
                    holderInfoChange.getChangeId()
            );
        }
    }

    @Override
    public void removeByChangeId(Long changeId) {
        HolderInfoChange deleteHolder = new HolderInfoChange();
        deleteHolder.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deleteHolder));
    }
}
