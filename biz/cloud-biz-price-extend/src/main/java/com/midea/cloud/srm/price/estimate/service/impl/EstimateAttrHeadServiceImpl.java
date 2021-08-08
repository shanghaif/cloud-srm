package com.midea.cloud.srm.price.estimate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.price.costelement.entity.CostElement;
import com.midea.cloud.srm.model.price.costelement.entity.FeatureAttribute;
import com.midea.cloud.srm.model.price.estimate.entity.EstimateAttrHead;
import com.midea.cloud.srm.model.price.estimate.entity.EstimateAttrLine;
import com.midea.cloud.srm.model.price.model.entity.ModelElement;
import com.midea.cloud.srm.price.costelement.service.ICostElementService;
import com.midea.cloud.srm.price.costelement.service.IFeatureAttributeService;
import com.midea.cloud.srm.price.estimate.mapper.EstimateAttrHeadMapper;
import com.midea.cloud.srm.price.estimate.service.IEstimateAttrHeadService;
import com.midea.cloud.srm.price.model.service.IModelElementService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  价格估算属性头表 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:36:39
 *  修改内容:
 * </pre>
*/
@Service
public class EstimateAttrHeadServiceImpl extends ServiceImpl<EstimateAttrHeadMapper, EstimateAttrHead> implements IEstimateAttrHeadService {
    @Resource
    private IModelElementService iModelElementService;
    @Resource
    private ICostElementService iCostElementService;
    @Resource
    private IFeatureAttributeService iFeatureAttributeService;

    @Override
    public List<EstimateAttrHead> get(Long priceModelHeadId) {
        Assert.notNull(priceModelHeadId, LocaleHandler.getLocaleMsg("缺失必要的请求参数")+": priceModelHeadId");
        ArrayList<EstimateAttrHead> estimateAttrHeadArrayList = null;
        List<ModelElement> modelElementList = iModelElementService.list(new QueryWrapper<>(new ModelElement().setPriceModelHeadId(priceModelHeadId)));
        if(CollectionUtils.isNotEmpty(modelElementList)){
            estimateAttrHeadArrayList = new ArrayList<>();
            for(ModelElement modelElement : modelElementList){
                // 获取成本要素
                CostElement costElement = iCostElementService.getById(modelElement.getCostElementId());
                if (null != costElement) {
                    //价格估算属性头表
                    EstimateAttrHead estimateAttrHead = new EstimateAttrHead();
                    estimateAttrHead.setCostElementId(costElement.getCostElementId());
                    estimateAttrHead.setElementType(costElement.getElementType());
                    estimateAttrHead.setElementCode(costElement.getElementCode());
                    estimateAttrHead.setElementName(costElement.getElementName());
                    estimateAttrHead.setElementVersion(costElement.getElementVersion());
                    estimateAttrHead.setCrucialAttributes(costElement.getCrucialAttributes());
                    estimateAttrHead.setRequiredFlag(modelElement.getRequiredFlag());
                    estimateAttrHead.setUnit(costElement.getUnit());
                    // 获取成本要素属性列表
                    List<FeatureAttribute> featureAttributeList = iFeatureAttributeService.list(new QueryWrapper<>(new FeatureAttribute().setCostElementId(costElement.getCostElementId())));
                    if(CollectionUtils.isNotEmpty(featureAttributeList)){
                        ArrayList<EstimateAttrLine> estimateAttrLines = new ArrayList<>();
                        for(FeatureAttribute featureAttribute : featureAttributeList){
                            if (!YesOrNo.YES.getValue().equals(featureAttribute.getDisableFlag())) {
                                EstimateAttrLine estimateAttrLine = new EstimateAttrLine();
                                estimateAttrLine.setAttributeName(featureAttribute.getAttributeName());
                                estimateAttrLine.setAttributeUnit(featureAttribute.getAttributeUnit());
                                estimateAttrLine.setAttributeType(featureAttribute.getAttributeType());
                                estimateAttrLine.setCrucialFlag(featureAttribute.getCrucialFlag());
                                estimateAttrLine.setRequiredFlag(featureAttribute.getRequiredFlag());
                                estimateAttrLine.setEnumAttributeMap(featureAttribute.getAttributeValue());
                                estimateAttrLines.add(estimateAttrLine);
                            }
                        }
                        estimateAttrHead.setEstimateAttrLines(estimateAttrLines);
                    }
                    estimateAttrHeadArrayList.add(estimateAttrHead);
                }
            }
        }
        return estimateAttrHeadArrayList;
    }
}
