package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.HonorInfoChange;
import com.midea.cloud.srm.model.supplier.change.entity.OperationInfoChange;
import com.midea.cloud.srm.sup.change.mapper.OperationInfoChangeMapper;
import com.midea.cloud.srm.sup.change.service.IDimFieldContextChangeService;
import com.midea.cloud.srm.sup.change.service.IOperationInfoChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
*  <pre>
 *  经营情况变更 服务实现类
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
public class OperationInfoChangeServiceImpl extends ServiceImpl<OperationInfoChangeMapper, OperationInfoChange> implements IOperationInfoChangeService {
    @Autowired
    private IDimFieldContextChangeService iDimFieldContextChangeService;

    @Override
    public OperationInfoChange getByChangeId(Long chanageId) {
        OperationInfoChange queryOperationInfoChange = new OperationInfoChange();
        queryOperationInfoChange.setChangeId(chanageId);
        OperationInfoChange operationInfoChange = chanageId != null?this.getOne(new QueryWrapper<>(queryOperationInfoChange)):null;
        if(operationInfoChange != null){
            Map<String,Object> dimFieldContexts = iDimFieldContextChangeService.findByOrderId(operationInfoChange.getOpChangeId());
            operationInfoChange.setDimFieldContexts(dimFieldContexts);
        }
        return operationInfoChange;
    }

    @Override
    public void saveOrUpdateOp(OperationInfoChange operationInfoChange, Long companyId, Long changeId) {
        operationInfoChange.setChangeId(changeId);
        operationInfoChange.setCompanyId(companyId);
        if(operationInfoChange.getOpChangeId() != null){
            this.updateById(operationInfoChange);
        }else {
            Long id = IdGenrator.generate();
            operationInfoChange.setOpChangeId(id);
            if(operationInfoChange.getOpInfoId() ==null){
                operationInfoChange.setOpInfoId(id);
            }
            this.save(operationInfoChange);
        }
        if(!CollectionUtils.isEmpty(operationInfoChange.getDimFieldContexts())){
            iDimFieldContextChangeService.saveOrUpdateList(operationInfoChange.getDimFieldContexts(),
                    operationInfoChange.getOpChangeId(),
                    operationInfoChange.getOpInfoId(),
                    operationInfoChange.getCompanyId(),
                    operationInfoChange.getChangeId()
            );
        }
    }

    @Override
    public void removeByChangeId(Long changeId) {
        OperationInfoChange deleteOp = new OperationInfoChange();
        deleteOp.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deleteOp));
    }
}
