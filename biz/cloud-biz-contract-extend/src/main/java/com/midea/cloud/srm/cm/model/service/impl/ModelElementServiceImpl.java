package com.midea.cloud.srm.cm.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.contract.ElementEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.cm.model.mapper.ModelElementMapper;
import com.midea.cloud.srm.cm.model.service.IModelElementService;
import com.midea.cloud.srm.cm.model.utils.BeanUtils;
import com.midea.cloud.srm.model.cm.model.entity.ModelElement;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  合同模板元素表 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-24 17:30:16
 *  修改内容:
 * </pre>
*/
@Service
public class ModelElementServiceImpl extends ServiceImpl<ModelElementMapper, ModelElement> implements IModelElementService {

    @Override
    public Map<String, Object> add(ModelElement modelElement) {
        //校验变量符号是否重复
        QueryWrapper<ModelElement> wrapper = new QueryWrapper<>();
        wrapper.eq("VARIABLE_SIGN",modelElement.getVariableSign());
        List<ModelElement> list = this.list(wrapper);
        if(CollectionUtils.isNotEmpty(list)){
            throw new BaseException("变量符号存在已存在");
        }
        // 非固定元素
        modelElement.setIsFixed(ElementEnum.IS_FIXED_N.getKey());
        // 默认激活状态
        if(StringUtil.isEmpty(modelElement.getIsAct())){
            modelElement.setIsAct(ElementEnum.IS_ACT_Y.getKey());
        }
        // 获取id
        Long id = IdGenrator.generate();
        modelElement.setElementId(id);
        this.save(modelElement);
        return BeanUtils.getStringObjectHashMap(id);
    }

    @Override
    public List<ModelElement> getElement(ModelElement modelElement) {
        QueryWrapper<ModelElement> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtil.notEmpty(modelElement.getVariableName()),"VARIABLE_NAME",modelElement.getVariableName());
        queryWrapper.like(StringUtil.notEmpty(modelElement.getVariableNameInfo()),"VARIABLE_NAME_INFO",modelElement.getVariableNameInfo());
        queryWrapper.like(StringUtil.notEmpty(modelElement.getVariableSign()),"VARIABLE_SIGN",modelElement.getVariableSign());
        queryWrapper.eq(StringUtil.notEmpty(modelElement.getIsAct()),"IS_ACT",modelElement.getIsAct());
        queryWrapper.eq(StringUtil.notEmpty(modelElement.getIsFixed()),"IS_FIXED",modelElement.getIsFixed());
        return this.list(queryWrapper);
    }

    @Override
    public void syncFixed(Long elementId) {
        Assert.notNull(elementId,"elementId不能为空");
        ModelElement modelElement = this.getById(elementId);
        UpdateWrapper<ModelElement> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ELEMENT_ID",elementId);
        updateWrapper.set("IS_FIXED",ElementEnum.IS_FIXED_Y.getKey());
        this.update(updateWrapper);
    }

    @Override
    public List<ModelElement> getElementList(List<String> variableSigns) {
        List<ModelElement> modelElements = null;
        if (CollectionUtils.isNotEmpty(variableSigns)){
            QueryWrapper<ModelElement> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("VARIABLE_SIGN",variableSigns);
            modelElements = this.list(queryWrapper);
        }
        return modelElements;
    }
}
