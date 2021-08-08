package com.midea.cloud.srm.price.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.price.costelement.CostElementStatus;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.price.model.entity.ModelCategory;
import com.midea.cloud.srm.model.price.model.entity.ModelElement;
import com.midea.cloud.srm.model.price.model.entity.ModelHead;
import com.midea.cloud.srm.price.model.mapper.ModelHeadMapper;
import com.midea.cloud.srm.price.model.service.IModelCategoryService;
import com.midea.cloud.srm.price.model.service.IModelElementService;
import com.midea.cloud.srm.price.model.service.IModelHeadService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
*  <pre>
 *  价格模型头表 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:27:17
 *  修改内容:
 * </pre>
*/
@Service
public class ModelHeadServiceImpl extends ServiceImpl<ModelHeadMapper, ModelHead> implements IModelHeadService {

    @Resource
    private BaseClient baseClient;
    @Resource
    private IModelCategoryService iModelCategoryService;
    @Resource
    private IModelElementService iModelElementService;
    @Override
    public PageInfo<ModelHead> listPage(ModelHead modelHead) {
        PageUtil.startPage(modelHead.getPageNum(),modelHead.getPageSize());
        List<ModelHead> modelHeads = this.baseMapper.listPage(modelHead);

        return new PageInfo<>(modelHeads);
    }

    @Override
    @Transactional
    public Long add(ModelHead modelHead) {
        Assert.notNull(modelHead.getOrgId(), LocaleHandler.getLocaleMsg("缺失必要的请求参数")+": OrgId");
        Long priceModelHeadId = IdGenrator.generate();
        // 保存模型头
        this.savePriceHead(modelHead, priceModelHeadId);
        // 保存采购分类
        this.saveModelCategory(modelHead, priceModelHeadId);
        // 保存成本要素
        this.saveModelElement(modelHead, priceModelHeadId);
        return priceModelHeadId;
    }

    public void saveModelElement(ModelHead modelHead, Long priceModelHeadId) {
        List<ModelElement> modelElementList = modelHead.getModelElementList();
        if(CollectionUtils.isNotEmpty(modelElementList)){
            for(ModelElement modelElement : modelElementList){
                Assert.notNull(modelElement.getCostElementId(),LocaleHandler.getLocaleMsg("缺失必要的请求参数")+": CostElementId");
                modelElement.setPriceModelHeadId(priceModelHeadId);
                modelElement.setModelElementId(IdGenrator.generate());
            }
            // 先删除所有再新增
            iModelElementService.remove(new QueryWrapper<>(new ModelElement().setPriceModelHeadId(priceModelHeadId)));
            iModelElementService.saveBatch(modelElementList);
        }
    }

    public void saveModelCategory(ModelHead modelHead, Long priceModelHeadId) {
        List<ModelCategory> modelCategoryList = modelHead.getModelCategoryList();
        if(CollectionUtils.isNotEmpty(modelCategoryList)){
            StringBuffer categoryName = new StringBuffer();
            for(ModelCategory modelCategory : modelCategoryList){
                modelCategory.setPriceModelHeadId(priceModelHeadId);
                modelCategory.setModelCategoryId(IdGenrator.generate());
                categoryName.append(modelCategory.getCategoryName()).append("/");
            }
            // 先删除全部再保存
            iModelCategoryService.remove(new QueryWrapper<>(new ModelCategory().setPriceModelHeadId(priceModelHeadId)));
            iModelCategoryService.saveBatch(modelCategoryList);

            // 维护头表采购分类字段
            modelHead.setCategoryName(StringUtils.left(categoryName.toString(), categoryName.length() - 1));
            this.updateById(modelHead);
        }
    }

    public void savePriceHead(ModelHead modelHead, Long priceModelHeadId) {
        if(StringUtil.isEmpty(modelHead.getStatus())){
            modelHead.setStatus(CostElementStatus.DRAFT.getKey());
        }
        modelHead.setPriceModelCode(baseClient.seqGen(SequenceCodeConstant.SEQ_PRICE_MODEL_CODE));
        modelHead.setPriceModelHeadId(priceModelHeadId);
        this.save(modelHead);
    }

    @Override
    @Transactional
    public Long modify(ModelHead modelHead) {
        Long priceModelHeadId = modelHead.getPriceModelHeadId();
        Assert.notNull(priceModelHeadId,LocaleHandler.getLocaleMsg("缺失必要的请求参数")+": OrgId");
        this.updateById(modelHead);
        // 保存采购分类
        this.saveModelCategory(modelHead, priceModelHeadId);
        // 保存成本要素
        this.saveModelElement(modelHead, priceModelHeadId);
        return priceModelHeadId;
    }

    @Override
    public ModelHead get(Long priceModelHeadId) {
        Assert.notNull(priceModelHeadId,LocaleHandler.getLocaleMsg("缺失必要的请求参数")+": priceModelHeadId");
        ModelHead modelHead = this.getById(priceModelHeadId);
        if(null != modelHead){
            List<ModelCategory> modelCategoryList = iModelCategoryService.list(new QueryWrapper<>(new ModelCategory().setPriceModelHeadId(priceModelHeadId)));
            List<ModelElement> modelElements = iModelElementService.list(new QueryWrapper<>(new ModelElement().setPriceModelHeadId(priceModelHeadId)));
            modelHead.setModelCategoryList(modelCategoryList);
            modelHead.setModelElementList(modelElements);
        }
        return modelHead;
    }

    @Override
    public void delete(Long priceModelHeadId) {
        Assert.notNull(priceModelHeadId,LocaleHandler.getLocaleMsg("缺失必要的请求参数")+": priceModelHeadId");
        this.removeById(priceModelHeadId);
        iModelCategoryService.remove(new QueryWrapper<>(new ModelCategory().setPriceModelHeadId(priceModelHeadId)));
        iModelElementService.remove(new QueryWrapper<>(new ModelElement().setPriceModelHeadId(priceModelHeadId)));
    }

    @Override
    public void takeEffect(Long priceModelHeadId) {
        ModelHead modelHead = this.getById(priceModelHeadId);
        if(null != modelHead){
            modelHead.setStatus(CostElementStatus.VALID.getKey());
            this.updateById(modelHead);
        }
    }

    @Override
    public void failure(Long priceModelHeadId) {
        ModelHead modelHead = this.getById(priceModelHeadId);
        if(null != modelHead){
            modelHead.setStatus(CostElementStatus.INVALID.getKey());
            this.updateById(modelHead);
        }
    }
}
