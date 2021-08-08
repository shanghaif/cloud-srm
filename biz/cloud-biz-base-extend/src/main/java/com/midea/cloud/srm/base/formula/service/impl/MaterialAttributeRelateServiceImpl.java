package com.midea.cloud.srm.base.formula.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.formula.mapper.MaterialFormulaRelateMapper;
import com.midea.cloud.srm.base.formula.mapper.PricingFormulaHeaderMapper;
import com.midea.cloud.srm.base.formula.service.IMaterialAttributeRelateService;
import com.midea.cloud.srm.base.material.mapper.MaterialItemAttributeMapper;
import com.midea.cloud.srm.base.material.mapper.MaterialItemAttributeRelateMapper;
import com.midea.cloud.srm.base.material.service.IMaterialItemService;
import com.midea.cloud.srm.model.base.formula.dto.MaterialAttrFormulaDTO;
import com.midea.cloud.srm.model.base.formula.entity.MaterialFormulaRelate;
import com.midea.cloud.srm.model.base.formula.entity.PricingFormulaHeader;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.MaterialItemAttribute;
import com.midea.cloud.srm.model.base.material.MaterialItemAttributeRelate;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *   物料属性导入
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/28 14:55
 *  修改内容:
 * </pre>
 */
@Service
public class MaterialAttributeRelateServiceImpl extends ServiceImpl<MaterialItemAttributeRelateMapper, MaterialItemAttributeRelate> implements IMaterialAttributeRelateService {

    @Autowired
    private IMaterialItemService iMaterialItemService;

    @Autowired
    private MaterialItemAttributeMapper materialItemAttributeMapper;

    @Autowired
    private PricingFormulaHeaderMapper pricingFormulaHeaderMapper;

    @Autowired
    private MaterialItemAttributeRelateMapper materialItemAttributeRelateMapper;

    @Autowired
    private MaterialFormulaRelateMapper materialFormulaRelateMapper;

    /**
     * excel价格公式行转换
     * @param materialAttrFormulaDTOList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void convertDTO(List<MaterialAttrFormulaDTO> materialAttrFormulaDTOList) {
        MaterialAttrFormulaDTO dto = new MaterialAttrFormulaDTO();
        if (CollectionUtils.isNotEmpty(materialAttrFormulaDTOList)){
            if (Objects.nonNull(materialAttrFormulaDTOList.get(0))) {
                dto = materialAttrFormulaDTOList.get(0);
                Assert.notNull(dto.getMaterialCode(), "物料编码为空！");
                String materialCode = dto.getMaterialCode();
                List<MaterialItem> materialItems = iMaterialItemService.list(
                        new QueryWrapper<>(new MaterialItem().setMaterialCode(materialCode))
                );
                //如果这个物料在物料数据库中存在，首先进行删除操作
                if (CollectionUtils.isNotEmpty(materialItems)) {
                    MaterialItem materialItem = materialItems.get(0);
                    //删除这个物料相关的属性关联
                    deleteMaterialAttributeRelates(materialItem);
                    //删除这个物料相关的公式关联
                    deleteMaterialFormulaRelates(materialItem);

                    for (MaterialAttrFormulaDTO materialAttrFormulaDTO : materialAttrFormulaDTOList) {
                        if (Objects.nonNull(materialAttrFormulaDTO)){
                            convertToAttributeRelate(materialAttrFormulaDTO, materialItem);
                        }
                    }
                    convertToFormulaRelate(dto, materialItem);

                }
                //如果这个物料在数据库中存在
            }
        }
    }

    /**
     * 将DTO转为物料-属性关联
     * @param materialAttrFormulaDTO
     * @param materialItem
     */
    public void convertToAttributeRelate(MaterialAttrFormulaDTO materialAttrFormulaDTO, MaterialItem materialItem) {
        MaterialItemAttributeRelate relate = new MaterialItemAttributeRelate();
        Long relateId = IdGenrator.generate();
        relate.setRelateId(relateId);
        relate.setMaterialItemId(materialItem.getMaterialId());
        //设置属性编码
        Assert.notNull(materialAttrFormulaDTO.getAttributeCode(), "物料(" + materialItem.getMaterialName() + ")对应的属性编码为空！");
        String attributeCode = materialAttrFormulaDTO.getAttributeCode();
        List<MaterialItemAttribute> materialItemAttributes = materialItemAttributeMapper.selectList(
                new QueryWrapper<>(new MaterialItemAttribute().setAttributeCode(attributeCode))
        );
        Assert.isTrue(CollectionUtils.isNotEmpty(materialItemAttributes), "物料(" + materialItem.getMaterialName() + ")传入的的属性编码(" + materialAttrFormulaDTO.getAttributeCode() + ")不存在！");
        relate.setMaterialAttributeId(materialItemAttributes.get(0).getMaterialAttributeId())
                .setAttributeCode(attributeCode)
                .setAttributeName(materialItemAttributes.get(0).getAttributeName());
        Assert.notNull(materialAttrFormulaDTO.getAttributeValue(),
                "物料(" + materialItem.getMaterialName() + ")对应的属性(" + materialItemAttributes.get(0).getAttributeName() + ")对应的属性值为空！");
        relate.setAttributeValue(materialAttrFormulaDTO.getAttributeValue())
                .setKeyFeature(Objects.nonNull(materialAttrFormulaDTO.getKeyFeature()) ? materialAttrFormulaDTO.getKeyFeature() : "N");
        this.save(relate);
    }

    /**
     * 将DTO转为物料-公式关联
     */
    public void convertToFormulaRelate(MaterialAttrFormulaDTO materialAttrFormulaDTO, MaterialItem materialItem) {
        MaterialFormulaRelate relate = new MaterialFormulaRelate();
        Long relateId = IdGenrator.generate();
        relate.setRelateId(relateId);
        //设置物料相关属性
        relate.setMaterialId(materialItem.getMaterialId());
        relate.setMaterialName(Objects.nonNull(materialItem.getMaterialName()) ? materialItem.getMaterialName() : null);
        relate.setMaterialCode(Objects.nonNull(materialItem.getMaterialCode()) ? materialItem.getMaterialCode() : null);
        relate.setSpecification(Objects.nonNull(materialItem.getSpecification()) ? materialItem.getSpecification() : null);
        relate.setCategoryId(Objects.nonNull(materialItem.getCategoryId()) ? materialItem.getCategoryId() : null);
        relate.setCategoryName(Objects.nonNull(materialItem.getCategoryName()) ? materialItem.getCategoryName() : null);
        relate.setUnit(materialItem.getUnit());
        relate.setUnitName(materialItem.getUnitName());
        //设置公式相关属性
        Assert.notNull(materialAttrFormulaDTO.getPricingFormulaName(), "物料(" +materialItem.getMaterialName() +")传入的公式名称为空！");
        String formulaName = materialAttrFormulaDTO.getPricingFormulaName();
        QueryWrapper<PricingFormulaHeader> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PRICING_FORMULA_NAME", formulaName);
        List<PricingFormulaHeader> pricingFormulaHeaders = pricingFormulaHeaderMapper.selectList(queryWrapper);
        Assert.isTrue(CollectionUtils.isNotEmpty(pricingFormulaHeaders), "物料(" +materialItem.getMaterialName() +")传入的公式名称("+ formulaName +")找不到，请先维护公式！");
        PricingFormulaHeader pricingFormulaHeader = pricingFormulaHeaders.get(0);
        relate.setPricingFormulaHeaderId(pricingFormulaHeader.getPricingFormulaHeaderId());
        relate.setPricingFormulaName(pricingFormulaHeader.getPricingFormulaName());
        relate.setPricingFormulaDesc(Objects.nonNull(pricingFormulaHeader.getPricingFormulaDesc())?pricingFormulaHeader.getPricingFormulaDesc():null);
        relate.setPricingFormulaStatus(pricingFormulaHeader.getPricingFormulaStatus());
        relate.setPricingFormulaValue(Objects.nonNull(pricingFormulaHeader.getPricingFormulaValue())?pricingFormulaHeader.getPricingFormulaValue():null);
        materialFormulaRelateMapper.insert(relate);
    }

    /**
     * 删除这个物料相关的物料-属性关联
     * @param materialItem
     */
    public void deleteMaterialAttributeRelates(MaterialItem materialItem){
        QueryWrapper<MaterialItemAttributeRelate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("MATERIAL_ITEM_ID", materialItem.getMaterialId());
        materialItemAttributeRelateMapper.delete(queryWrapper);
    }

    /**
     * 删除这个物料相关的物料-公式关联
     */
    public void deleteMaterialFormulaRelates(MaterialItem materialItem){
        QueryWrapper<MaterialFormulaRelate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("MATERIAL_ID", materialItem.getMaterialId());
        materialFormulaRelateMapper.delete(queryWrapper);
    }

}
