package com.midea.cloud.srm.base.test.materialImport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.formula.service.IMaterialAttributeRelateService;
import com.midea.cloud.srm.base.material.service.IMaterialItemAttributeService;
import com.midea.cloud.srm.base.material.service.IMaterialItemService;
import com.midea.cloud.srm.base.seq.service.ISeqDefinitionService;
import com.midea.cloud.srm.base.test.materialImport.mapper.MaterialTestMapper;
import com.midea.cloud.srm.base.test.materialImport.service.IMaterialTestService;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.MaterialItemAttribute;
import com.midea.cloud.srm.model.base.material.MaterialItemAttributeRelate;
import com.midea.cloud.srm.model.base.test.MaterialTest;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  物料长宽高属性配置表（导测试数据用） 服务实现类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-26 13:57:30
 *  修改内容:
 * </pre>
*/
@Service
public class MaterialTestServiceImpl extends ServiceImpl<MaterialTestMapper, MaterialTest> implements IMaterialTestService {

    @Autowired
    private IMaterialItemService iMaterialItemService;

    @Autowired
    private ISeqDefinitionService iSeqDefinitionService;

    @Autowired
    private IMaterialItemAttributeService iMaterialItemAttributeService;

    @Autowired
    private IMaterialAttributeRelateService iMaterialAttributeRelateService;

    /**
     * 导入物料测试数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importData() {
        List<MaterialTest> materialTestList = this.list();
        List insertMaterialList = new ArrayList();
        List<MaterialItemAttributeRelate> insertRelateList = new ArrayList<>();

        List<String> attributeCodes = new ArrayList<>();
        attributeCodes.add("SX20092600001");
        attributeCodes.add("SX20092600002");
        attributeCodes.add("SX20092600003");
        attributeCodes.add("SX20092600004");
        attributeCodes.add("SX20092600005");
        attributeCodes.add("SX20092600006");
        List<MaterialItemAttribute> attributeList = iMaterialItemAttributeService.list(
                new QueryWrapper<MaterialItemAttribute>().in("ATTRIBUTE_CODE", attributeCodes));

        for (MaterialTest test : materialTestList){
            QueryWrapper<MaterialItem> materialItemQueryWrapper = new QueryWrapper<>(new MaterialItem().setMaterialCode(test.getMaterialCode()));
            List existMaterialList = iMaterialItemService.list(materialItemQueryWrapper);
            //如果这条物料不存在
            if (existMaterialList.isEmpty()){
                MaterialItem insertMaterial = new MaterialItem();
                Long materialId= IdGenrator.generate();
                insertMaterial.setMaterialId(materialId)
                        .setMaterialCode(test.getMaterialCode())
                        .setMaterialName(test.getMaterialDescr())
                        .setStatus("Y")
                        .setCategoryId(Long.valueOf("7773091163209728"))
                        .setCategoryName("包装材料")
                        .setCategoryFullName("生产材料-通用生产辅材-包装材料")
                        .setUnit("块")
                        .setUnitName("块");
                insertMaterialList.add(insertMaterial);


                for (MaterialItemAttribute attribute : attributeList) {

                    MaterialItemAttributeRelate relate = new MaterialItemAttributeRelate();

                    Long relateId = IdGenrator.generate();
                    relate.setRelateId(relateId);

                    relate.setMaterialAttributeId(attribute.getMaterialAttributeId());
                    relate.setMaterialItemId(materialId);
                    relate.setAttributeCode(attribute.getAttributeCode())
                            .setAttributeName(attribute.getAttributeName());
                    switch (attribute.getAttributeCode()){
                        case "SX20092600001" :
                            relate.setAttributeValue(test.getCardBoard())
                                .setKeyFeature("Y");
                            break;
                        case "SX20092600002" :
                            relate.setAttributeValue(test.getCardType())
                                .setKeyFeature("Y");
                            break;
                        case "SX20092600003" :
                            relate.setAttributeValue(test.getInnerSize())
                                    .setKeyFeature("N");
                            break;
                        case "SX20092600004" :
                            relate.setAttributeValue(test.getMaterialLength())
                                    .setKeyFeature("N");
                            break;
                        case "SX20092600005" :
                            relate.setAttributeValue(test.getMaterialWidth())
                                    .setKeyFeature("N");
                            break;
                        case "SX20092600006" :
                            relate.setAttributeValue(test.getMaterialHeight())
                                    .setKeyFeature("N");
                            break;
                        default:
                            break;
                    }
                    insertRelateList.add(relate);
                }
            }
        }
        iMaterialItemService.saveBatch(insertMaterialList);

        iMaterialAttributeRelateService.saveBatch(insertRelateList);
    }

    /**
     * 创建导入测试物料的属性
     * @modifiedBy xiexh12@meicloud.com
     */
    @Override
    public void createMaterialAttribute() {

        List<String> attributesList = new ArrayList<>();
        List<MaterialItemAttribute> insertMaterialAttributeList = new ArrayList<>();
        attributesList.add("纸板层数");
        attributesList.add("瓦楞类型");
        attributesList.add("内尺寸");
        attributesList.add("长");
        attributesList.add("宽");
        attributesList.add("高");
        attributesList.forEach(attribute -> {
            MaterialItemAttribute materialItemAttribute = new MaterialItemAttribute();
            Long id = IdGenrator.generate();
            materialItemAttribute.setMaterialAttributeId(id)
                    .setAttributeCode(iSeqDefinitionService.genSequencesNumBase(SequenceCodeConstant.SEQ_BASE_MATERIAL_ATTRIBUTE_CODE))
                    .setAttributeName(attribute);
            insertMaterialAttributeList.add(materialItemAttribute);
        });
        iMaterialItemAttributeService.saveBatch(insertMaterialAttributeList);
    }
}
