package com.midea.cloud.srm.base.configguide.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.SupplierDataSourceType;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.categorydv.service.ICategoryDvService;
import com.midea.cloud.srm.base.configguide.mapper.ConfigGuideMapper;
import com.midea.cloud.srm.base.configguide.service.IConfigGuideService;
import com.midea.cloud.srm.base.material.service.IMaterialItemService;
import com.midea.cloud.srm.base.organization.service.IOrganizationService;
import com.midea.cloud.srm.base.purchase.service.IPurchaseCategoryService;
import com.midea.cloud.srm.base.purchase.service.IPurchaseCurrencyService;
import com.midea.cloud.srm.base.purchase.service.IPurchaseTaxService;
import com.midea.cloud.srm.base.purchase.service.IPurchaseUnitService;
import com.midea.cloud.srm.base.sceneattachment.service.ISceneAttachmentService;
import com.midea.cloud.srm.feign.flow.WorkFlowFeign;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.configguide.entity.ConfigGuide;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
*  <pre>
 *  配置导引表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-17 10:14:16
 *  修改内容:
 * </pre>
*/
@Service
public class ConfigGuideServiceImpl extends ServiceImpl<ConfigGuideMapper, ConfigGuide> implements IConfigGuideService {
    @Autowired
    private IMaterialItemService iMaterialItemService;
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private RbacClient rbacClient;
    @Autowired
    private ICategoryDvService iCategoryDvService;
    @Autowired
    private ISceneAttachmentService iSceneAttachmentService;
    @Autowired
    private IPurchaseUnitService iPurchaseUnitService;
    @Autowired
    private IPurchaseTaxService iPurchaseTaxService;
    @Autowired
    private IPurchaseCategoryService iPurchaseCategoryService;
    @Autowired
    private IOrganizationService iOrganizationService;
    @Autowired
    private WorkFlowFeign workFlowFeign;
    @Autowired
    private IPurchaseCurrencyService iPurchaseCurrencyService;


    @Override
    public ConfigGuide getInfoByUser() {
        ConfigGuide configGuide = new ConfigGuide();
        List<ConfigGuide> configGuides = this.list();
        if(CollectionUtils.isEmpty(configGuides)){
            Long id = IdGenrator.generate();
            configGuide.setConfigGuideId(id);
        }else {
            configGuide = configGuides.get(0);
        }

        //对物料导入进行查看
        if(StringUtils.isBlank(configGuide.getMaterialConfig()) ||
                YesOrNo.NO.getValue().equals(configGuide.getMaterialConfig())){
            if(iMaterialItemService.count() >0){
                configGuide.setMaterialConfig(YesOrNo.YES.getValue());
            }
        }
        //对供应商导入进行查看
        if(StringUtils.isBlank(configGuide.getVendorConfig()) ||
                YesOrNo.NO.getValue().equals(configGuide.getVendorConfig())
        ){
            int manumber =supplierClient.getCountByParam(new CompanyInfo().
                    setDataSources(SupplierDataSourceType.MANUALLY_CREATE.getValue()));
            if(manumber > 0){
                configGuide.setVendorConfig(YesOrNo.YES.getValue());
            }
        }
        //对子账号导入进行查看
        if(StringUtils.isBlank(configGuide.getChildConfig()) ||
        YesOrNo.NO.getValue().equals(configGuide.getChildConfig())){
            int countByParam = rbacClient.getCountByParam(new User().setUserType(UserType.VENDOR.name()));
            if(countByParam > 0){
                configGuide.setChildConfig(YesOrNo.YES.getValue());
            }
        }
        //判断品类分工是否有设置
        if(StringUtils.isBlank(configGuide.getDvConfig()) ||
        YesOrNo.NO.getValue().equals(configGuide.getDvConfig())){
            if(iCategoryDvService.count()>0){
                configGuide.setDvConfig(YesOrNo.YES.getValue());
            }
        }
        //判断是否设置附件
        if(StringUtils.isBlank(configGuide.getUploadConfig()) ||
        YesOrNo.NO.getValue().equals(configGuide.getUploadConfig())){
            if(iSceneAttachmentService.count() > 0){
                configGuide.setUploadConfig(YesOrNo.YES.getValue());
            }
        }
        //判断是否设置了单位
        if(StringUtils.isBlank(configGuide.getUnitConfig()) ||
        YesOrNo.NO.getValue().equals(configGuide.getUnitConfig())){
            if(iPurchaseUnitService.count() > 0){
                configGuide.setUnitConfig(YesOrNo.YES.getValue());
            }
        }
        //判断是否设置税
        if(StringUtils.isBlank(configGuide.getTaxConfig()) ||
        YesOrNo.NO.getValue().equals(configGuide.getTaxConfig())){
            if(iPurchaseTaxService.count() > 0){
                configGuide.setTaxConfig(YesOrNo.YES.getValue());
            }
        }
        //判断是否设置币种
        if(StringUtils.isBlank(configGuide.getCurrencyConfig()) ||
        YesOrNo.NO.getValue().equals(configGuide.getCurrencyConfig())){
            if(iPurchaseCurrencyService.count() > 0){
                configGuide.setCurrencyConfig(YesOrNo.YES.getValue());
            }
        }
        //判断采购分类
        if(StringUtils.isBlank(configGuide.getPurchaseConfig()) ||
        YesOrNo.NO.getValue().equals(configGuide.getPurchaseConfig())){
            if(iPurchaseCategoryService.count() >0){
                configGuide.setPurchaseConfig(YesOrNo.YES.getValue());
            }
        }
        //判断组织
        if(StringUtils.isBlank(configGuide.getOrgConfig()) ||
                YesOrNo.NO.getValue().equals(configGuide.getOrgConfig())){
            if(iOrganizationService.count() >0){
                configGuide.setOrgConfig(YesOrNo.YES.getValue());
            }
        }
        //判断是否配置流程
        if(StringUtils.isBlank(configGuide.getFlowTemplateConfig()) ||
                YesOrNo.NO.getValue().equals(configGuide.getFlowTemplateConfig())){
            if(workFlowFeign.getCount() > 0){
                configGuide.setFlowTemplateConfig(YesOrNo.YES.getValue());
            }
        }
        //判断是否配置供应商属性
        if(StringUtils.isBlank(configGuide.getVendorFieldConfig()) ||
                YesOrNo.NO.getValue().equals(configGuide.getVendorFieldConfig())){
            if(supplierClient.getDimTemplateCount() > 0){
                configGuide.setVendorFieldConfig(YesOrNo.YES.getValue());
            }
        }
        this.saveOrUpdate(configGuide);
        return this.getById(configGuide.getConfigGuideId());
    }

    @Override
    @Transactional
    public void saveOrUpdateConfigGuide(ConfigGuide configGuide) {
        List<ConfigGuide> configGuides = this.list();
        if(CollectionUtils.isEmpty(configGuides)){
            Long id = IdGenrator.generate();
            configGuide.setConfigGuideId(id);
            this.save(configGuide);
        }else {
            ConfigGuide existConfigGuide = configGuides.get(0);
            configGuide.setConfigGuideId(existConfigGuide.getConfigGuideId());
            this.updateById(configGuide);
        }
    }
}
