package com.midea.cloud.srm.cm.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.cm.model.mapper.ModelLineMapper;
import com.midea.cloud.srm.cm.model.service.IModelElementService;
import com.midea.cloud.srm.cm.model.service.IModelHeadService;
import com.midea.cloud.srm.cm.model.service.IModelLineService;
import com.midea.cloud.srm.model.cm.model.dto.ModelLineDto;
import com.midea.cloud.srm.model.cm.model.entity.ModelElement;
import com.midea.cloud.srm.model.cm.model.entity.ModelHead;
import com.midea.cloud.srm.model.cm.model.entity.ModelLine;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  合同行表 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-24 09:24:06
 *  修改内容:
 * </pre>
*/
@Service
public class ModelLineServiceImpl extends ServiceImpl<ModelLineMapper, ModelLine> implements IModelLineService {
    @Autowired
    private IModelHeadService iModelHeadService;
    @Autowired
    private IModelElementService iModelElementService;

    @Override
    public void add(List<ModelLine> modelLineList) {
        if(CollectionUtils.isNotEmpty(modelLineList)){
            for(ModelLine modelLine: modelLineList){
                Assert.notNull(modelLineList.get(0).getModelHeadId(),"modelHeadId不能为空");
                Assert.notNull(modelLineList.get(0).getContractHeadId(),"contractHeadId不能为空");
                // 获取id
                long id = IdGenrator.generate();
                modelLine.setModelLineId(id);
                // 设置头id
                this.save(modelLine);
            }
        }
    }

    @Override
    public void update(List<ModelLine> modelLineList) {
        if(CollectionUtils.isNotEmpty(modelLineList)){
            for(ModelLine modelLine: modelLineList){
                if(StringUtil.notEmpty(modelLine.getModelLineId())){
                    // 更新
                    Assert.notNull(modelLine.getModelLineId(),"modelLineId不能为空");
                    this.updateById(modelLine);
                }else {
                    // 新增
                    long id = IdGenrator.generate();
                    modelLine.setModelLineId(id);
                    this.save(modelLine);
                }
            }
        }
    }

    @Override
    public List<ModelLine> getModelLine(Long modelHeadId) {
        Assert.notNull(modelHeadId,"modelHeadId不能为空");
        ModelHead modelHead = iModelHeadService.getById(modelHeadId);
        ArrayList<ModelLine> modelLines = new ArrayList<>();
        if(null != modelHead){
            String content = modelHead.getContent();
            if(StringUtil.notEmpty(content)){
                List<String> placeholderList = StringUtil.getPlaceholderList(content);
                if(CollectionUtils.isNotEmpty(placeholderList)){
                    for(String placeholder:placeholderList ){
                        QueryWrapper<ModelElement> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("VARIABLE_SIGN",placeholder);
                        queryWrapper.eq("IS_ACT","Y");
                        List<ModelElement> modelElementList = iModelElementService.list(queryWrapper);
                        if(CollectionUtils.isNotEmpty(modelElementList)){
                            ModelLine modelLine = new ModelLine();
                            ModelElement modelElement = modelElementList.get(0);
                            // 变量符
                            modelLine.setModelKey(modelElement.getVariableName());
                            // 替换值
                            modelLine.setModelValue(modelElement.getInitValue());
                            // 模板id
                            modelLine.setModelHeadId(modelHeadId);
                            modelLines.add(modelLine);
                        }
                    }
                }
            }
        }
        return modelLines;
    }

    @Override
    public ModelLineDto queryContract(Long contractHeadId) {
        Assert.notNull(contractHeadId,"contractHeadId不能为空");
        ModelLineDto modelLineDto = new ModelLineDto();
        QueryWrapper<ModelLine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CONTRACT_HEAD_ID",contractHeadId);
        List<ModelLine> modelLines = this.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(modelLines)){
            Long modelHeadId = modelLines.get(0).getModelHeadId();
            ModelHead modelHead = iModelHeadService.getById(modelHeadId);
            if(null != modelHead){
                modelLineDto.setModelHead(modelHead);
                modelLineDto.setModelLine(modelLines);
            }
        }
        return modelLineDto;
    }

    @Override
    public List<ModelLine> queryContractList(ModelLine modelLine) {
        QueryWrapper<ModelLine> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtil.notEmpty(modelLine.getContractHeadId()),"CONTRACT_HEAD_ID",modelLine.getContractHeadId());
        queryWrapper.like(StringUtil.notEmpty(modelLine.getContractNo()),"CONTRACT_NO",modelLine.getContractNo());
        queryWrapper.groupBy("CONTRACT_HEAD_ID","CONTRACT_NO");
        List<ModelLine> list = this.list(queryWrapper);
        return list;
    }
}
