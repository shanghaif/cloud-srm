package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.DimFieldContextChange;
import com.midea.cloud.srm.model.supplier.dim.entity.DimFieldContext;
import com.midea.cloud.srm.sup.change.mapper.DimFieldContextChangeMapper;
import com.midea.cloud.srm.sup.change.service.IDimFieldContextChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  维度字段内容变更表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-26 15:19:45
 *  修改内容:
 * </pre>
*/
@Service
public class DimFieldContextChangeServiceImpl extends ServiceImpl<DimFieldContextChangeMapper, DimFieldContextChange> implements IDimFieldContextChangeService {
    @Autowired
    private DimFieldContextChangeMapper dimFieldContextChangeMapper;

    public List<DimFieldContextChange> saveOrUpdateList(Map<String, Object> dimFieldContexts, Long changeOrderId, Long orderId, Long companyId, Long changeId) {
        if(orderId != null && changeId != null){
            Map<String,Object> removeMap = new HashMap<>();
            removeMap.put("CONTEXT_CHANGE_ID",changeOrderId);
            this.removeByMap(removeMap);
        }

        List<DimFieldContextChange> dimFieldList = new ArrayList<>();
        for(Map.Entry<String, Object> entry : dimFieldContexts.entrySet()){
            DimFieldContextChange dimFieldContextChange = new DimFieldContextChange();
            Long id = IdGenrator.generate();
            dimFieldContextChange.setFieldContextId(id);
            dimFieldContextChange.setFieldContextChangeId(id);
            dimFieldContextChange.setContextChangeId(changeOrderId);
            dimFieldContextChange.setContextOrderId(orderId+"");
            dimFieldContextChange.setChangeId(changeId);
            dimFieldContextChange.setFieldCode(entry.getKey());
            dimFieldContextChange.setFieldValue(entry.getValue().toString());
            dimFieldContextChange.setCompanyId(companyId);
            dimFieldList.add(dimFieldContextChange);
        }


        this.saveBatch(dimFieldList);
        return dimFieldList;
    }

    @Override
    public Map<String, Object> findByOrderId(Long changeOrderId) {
        Map<String,Object> map = new HashMap<>();
        //获取所有的头信息
        List<String> keys = dimFieldContextChangeMapper.findKey(changeOrderId);
        //根据key行转列
        if(!CollectionUtils.isEmpty(keys)) {
            map = dimFieldContextChangeMapper.findByKey(keys, changeOrderId);
        }
        return map;
    }

    @Override
    public void deleteByChangeId(Long changeId) {
        DimFieldContextChange dimFieldContextChange = new DimFieldContextChange();
        dimFieldContextChange.setChangeId(changeId);
        QueryWrapper<DimFieldContextChange> queryWrapper = new QueryWrapper<>(dimFieldContextChange);
        this.remove(queryWrapper);
    }
}
