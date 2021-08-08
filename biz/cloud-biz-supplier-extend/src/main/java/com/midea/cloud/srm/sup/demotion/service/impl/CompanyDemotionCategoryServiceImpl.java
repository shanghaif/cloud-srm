package com.midea.cloud.srm.sup.demotion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.review.CategoryStatus;
import com.midea.cloud.common.enums.sup.DemotionType;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotion;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionCategory;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.sup.demotion.mapper.CompanyDemotionCategoryMapper;
import com.midea.cloud.srm.sup.demotion.service.ICompanyDemotionCategoryService;
import com.midea.cloud.srm.sup.info.service.IOrgCategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
*  <pre>
 *  供应商升降级品类行表 服务实现类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-05 17:50:08
 *  修改内容:
 * </pre>
*/
@Service
public class CompanyDemotionCategoryServiceImpl extends ServiceImpl<CompanyDemotionCategoryMapper, CompanyDemotionCategory> implements ICompanyDemotionCategoryService {

    @Autowired
    private IOrgCategoryService iOrgCategoryService;

    @Autowired
    private BaseClient baseClient;

    /**
     * 根据供应商id和升降级类型查询品类
     * @param queryDTO
     * @return
     */
    @Override
    public List<CompanyDemotionCategory> queryCategorysByParam(CompanyDemotion queryDTO) {
        List<OrgCategory> categories = new ArrayList<>();
        String demotionType = queryDTO.getDemotionType();
        Long companyId = queryDTO.getCompanyId();
        // 降级至注册
        if (Objects.equals(demotionType, DemotionType.DEMOTION_TO_REGISTERED.getValue())) {
            categories = demotionToRegistered(companyId);
        }
        // 降级至黄牌
        if (Objects.equals(demotionType, DemotionType.DEMOTION_TO_YELLOW.getValue())) {
            categories = demotionToYellow(companyId);
        }
        // 降级至红牌
        if (Objects.equals(demotionType, DemotionType.DEMOTION_TO_RED.getValue())) {
            categories = demotionToRed(companyId);
        }
        // 降级至黑牌
        if (Objects.equals(demotionType, DemotionType.DEMOTION_TO_BLACK.getValue())) {
            categories = demotionToBlack(companyId);
        }
        // 黄牌改善升级
        if (Objects.equals(demotionType, DemotionType.YELLOW_IMPROVE.getValue())) {
            categories = yellowImprove(companyId);
        }

        // 设置品类的全路径id和全路径名称 并将orgCategory转换为companyDemotionCategory
        List<CompanyDemotionCategory> companyDemotionCategories = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(categories)) {
            categories.forEach(orgCategory -> {
                setCategoryFull(orgCategory);
                CompanyDemotionCategory companyDemotionCategory = new CompanyDemotionCategory();
                BeanUtils.copyProperties(orgCategory, companyDemotionCategory);
                companyDemotionCategories.add(companyDemotionCategory);
            });
        }

        return companyDemotionCategories;
    }

    /**
     * 保存或更新升降级品类行
     * @param companyDemotionCategories
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateDemotionCategorys(List<CompanyDemotionCategory> companyDemotionCategories) {
        // 获取数据已有的行数据
        Long companyDemotionId = companyDemotionCategories.get(0).getCompanyDemotionId();
        List<CompanyDemotionCategory> dBCompanyDemotionCategories = this.list(
                new QueryWrapper<CompanyDemotionCategory>(new CompanyDemotionCategory().setCompanyDemotionId(companyDemotionId))
        );

        List<CompanyDemotionCategory> saveList = new ArrayList<>();
        List<Long> deleteIdList = new ArrayList<>();

        Map<Long, CompanyDemotionCategory> map = companyDemotionCategories.stream().filter(x -> Objects.nonNull(x.getCompanyDemotionCategoryId()))
                .collect(Collectors.toMap(x -> x.getCompanyDemotionCategoryId(), Function.identity()));

        Map<Long, CompanyDemotionCategory> dBMap = dBCompanyDemotionCategories.stream()
                .collect(Collectors.toMap(x -> x.getCompanyDemotionCategoryId(), Function.identity()));
        dBMap.forEach((k, v)-> {
            if (!map.containsKey(k)) {
                deleteIdList.add(k);
            }
        });

        companyDemotionCategories.forEach(companyDemotionCategory -> {
            // 行id为空，新增
            if (Objects.isNull(companyDemotionCategory.getCompanyDemotionCategoryId())) {
                Long demotionCategoryId = IdGenrator.generate();
                companyDemotionCategory.setCompanyDemotionCategoryId(demotionCategoryId);
                saveList.add(companyDemotionCategory);
            }
        });
        if (CollectionUtils.isNotEmpty(deleteIdList))
            this.removeByIds(deleteIdList);
        if (CollectionUtils.isNotEmpty(saveList))
            this.saveBatch(saveList);
    }

    /**
     * 降级至注册查询品类
     * @param companyId
     * @return
     */
    public List<OrgCategory> demotionToRegistered(Long companyId) {
        // 1.选择的升降级品类可以选择所有的有合作关系品类
        List<OrgCategory> list = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                .eq(OrgCategory::getCompanyId, companyId)
        );
        Map<Long, OrgCategory> map = list.stream().collect(Collectors.toMap(OrgCategory::getCategoryId, v->v, (v1, v2)->v2));
        List<OrgCategory> categories = map.values().stream().collect(Collectors.toList());
        return categories;
    }

    /**
     * 降级至黄牌查询品类
     * @param companyId
     * @return
     */
    public List<OrgCategory> demotionToYellow(Long companyId) {
        // 2.选择的升降级品类可以选择绿牌合作关系的品类
        List<OrgCategory> list = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                .eq(OrgCategory::getCompanyId, companyId)
                .eq(OrgCategory::getServiceStatus, CategoryStatus.GREEN.name())
        );
        Map<Long, OrgCategory> map = list.stream().collect(Collectors.toMap(OrgCategory::getCategoryId, v->v, (v1, v2)->v2));
        List<OrgCategory> categories = map.values().stream().collect(Collectors.toList());
        return categories;
    }

    /**
     * 降级至红牌查询品类
     * @param companyId
     * @return
     */
    public List<OrgCategory> demotionToRed(Long companyId) {
        // 3.选择的升降级品类可以选择黄牌、绿牌合作关系的品类
        List<String> demotionToRedStatus = Arrays.asList(CategoryStatus.YELLOW.name(), CategoryStatus.GREEN.name());
        List<OrgCategory> list = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                .eq(OrgCategory::getCompanyId, companyId)
                .in(OrgCategory::getServiceStatus, demotionToRedStatus)
        );
        Map<Long, OrgCategory> map = list.stream().collect(Collectors.toMap(OrgCategory::getCategoryId, v->v, (v1, v2)->v2));
        List<OrgCategory> categories = map.values().stream().collect(Collectors.toList());
        return categories;
    }

    /**
     * 降级至黑牌查询品类
     * @param companyId
     * @return
     */
    public List<OrgCategory> demotionToBlack(Long companyId) {
        // 4.自动带出全部品类
        List<OrgCategory> list = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                .eq(OrgCategory::getCompanyId, companyId)
        );
        Map<Long, OrgCategory> map = list.stream().collect(Collectors.toMap(OrgCategory::getCategoryId, v->v, (v1, v2)->v2));
        List<OrgCategory> categories = map.values().stream().collect(Collectors.toList());
        return categories;
    }

    /**
     * 黄牌改善升级
     * @param companyId
     * @return
     */
    public List<OrgCategory> yellowImprove(Long companyId) {
        // 5.选择的升降级品类可以选择黄牌的品类。
        List<OrgCategory> list = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                .eq(OrgCategory::getCompanyId, companyId)
                .eq(OrgCategory::getServiceStatus, CategoryStatus.YELLOW.name())
        );
        Map<Long, OrgCategory> map = list.stream().collect(Collectors.toMap(OrgCategory::getCategoryId, v->v, (v1, v2)->v2));
        List<OrgCategory> categories = map.values().stream().collect(Collectors.toList());
        return categories;
    }

    /**
     * 根据品类id设置全路径
     * @param orgCategory
     */
    public void setCategoryFull(OrgCategory orgCategory) {
        Long categoryId = orgCategory.getCategoryId();
        PurchaseCategory category = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(categoryId));
        Assert.notNull(category, "找不到对应的品类。");
        orgCategory.setCategoryFullName(category.getCategoryFullName());
    }

}
