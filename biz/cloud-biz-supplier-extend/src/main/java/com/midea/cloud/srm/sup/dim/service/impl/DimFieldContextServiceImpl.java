package com.midea.cloud.srm.sup.dim.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.dim.entity.DimFieldContext;
import com.midea.cloud.srm.sup.dim.mapper.DimFieldContextMapper;
import com.midea.cloud.srm.sup.dim.service.IDimFieldContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
*  <pre>
 *  维度字段内容表 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 15:17:08
 *  修改内容:
 * </pre>
*/
@Service
public class DimFieldContextServiceImpl extends ServiceImpl<DimFieldContextMapper, DimFieldContext> implements IDimFieldContextService {

    @Autowired
    private DimFieldContextMapper dimFieldContextMapper;

    @Override
    public void saveOrUpdateList(Map<String,Object> dimFieldContexts, Long orderID,Long companyId) {
        //先根据ORDERID删除旧有字段再保存
        if(orderID != null) {
           Map<String,Object> removeMap = new HashMap<>();
            removeMap.put("CONTEXT_ORDER_ID",orderID);
            this.removeByMap(removeMap);
        }

        List<DimFieldContext> dimFieldList = new ArrayList<>();
        for(Map.Entry<String, Object> entry : dimFieldContexts.entrySet()){
            DimFieldContext dimFieldContext = new DimFieldContext();
            Long id = IdGenrator.generate();
            dimFieldContext.setFieldContextId(id);
            dimFieldContext.setContextOrderId(orderID+"");
            dimFieldContext.setFieldCode(entry.getKey());
            dimFieldContext.setFieldValue(entry.getValue().toString());
            dimFieldContext.setCompanyId(companyId);
            dimFieldList.add(dimFieldContext);
        }


        this.saveOrUpdateBatch(dimFieldList);
    }

    @Override
    public  Map<String,Object> findByOrderId(Long orderId) {
        Map<String,Object> map = new HashMap<>();
        //获取所有的头信息
        List<String> keys = dimFieldContextMapper.findKey(orderId);
        //根据key行转列
        if(!CollectionUtils.isEmpty(keys)) {
            map = dimFieldContextMapper.findByKey(keys, orderId);
        }
        return map;
    }

    @Override
    public void deleteByCompanyId(Long companyId) {
        DimFieldContext dimFieldContext = new DimFieldContext();
        dimFieldContext.setCompanyId(companyId);
        QueryWrapper<DimFieldContext> queryWrapper = new QueryWrapper<>(dimFieldContext);
        this.remove(queryWrapper);

    }


}
