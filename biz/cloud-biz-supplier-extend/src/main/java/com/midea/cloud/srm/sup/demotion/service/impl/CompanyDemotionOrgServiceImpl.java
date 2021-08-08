package com.midea.cloud.srm.sup.demotion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.review.CategoryStatus;
import com.midea.cloud.common.enums.sup.DemotionType;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.demotion.dto.DemotionOrgQueryDTO;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionOrg;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.sup.demotion.mapper.CompanyDemotionOrgMapper;
import com.midea.cloud.srm.sup.demotion.service.ICompanyDemotionOrgService;
import com.midea.cloud.srm.sup.info.service.IOrgCategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
*  <pre>
 *  供应商升降级业务实体行表 服务实现类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-05 17:50:44
 *  修改内容:
 * </pre>
*/
@Service
public class CompanyDemotionOrgServiceImpl extends ServiceImpl<CompanyDemotionOrgMapper, CompanyDemotionOrg> implements ICompanyDemotionOrgService {

    @Autowired
    private IOrgCategoryService iOrgCategoryService;

    /**
     * 根据供应商id、品类ids和升降级类型 查询OU
     * @param queryDTO
     * @return
     */
    @Override
    public List<CompanyDemotionOrg> queryOrgsByParam(DemotionOrgQueryDTO queryDTO) {
        Long companyId = queryDTO.getCompanyId();
        if (CollectionUtils.isEmpty(queryDTO.getCategoryIds())) {
            return new ArrayList<>();
        }
        List<Long> categoryIds = queryDTO.getCategoryIds();
        String demotionType = queryDTO.getDemotionType();

        List<OrgCategory> categories = new ArrayList<>();

        // 1.降级至注册
        if (Objects.equals(demotionType, DemotionType.DEMOTION_TO_REGISTERED.getValue())) {
            categories = demotionToRegistered(companyId, categoryIds);
        }
        // 2.降级至黄牌
        if (Objects.equals(demotionType, DemotionType.DEMOTION_TO_YELLOW.getValue())) {
            categories = demotionToYellow(companyId, categoryIds);
        }
        // 3.降级至红牌
        if (Objects.equals(demotionType, DemotionType.DEMOTION_TO_RED.getValue())) {
            categories = demotionToRed(companyId, categoryIds);
        }
        // 4.降级至黑牌
        if (Objects.equals(demotionType, DemotionType.DEMOTION_TO_BLACK.getValue())) {
            categories = demotionToBlack(companyId, categoryIds);
        }
        // 5.黄牌改善升级
        if (Objects.equals(demotionType, DemotionType.YELLOW_IMPROVE.getValue())) {
            categories = yellowImprove(companyId, categoryIds);
        }

        // 转换OU，将orgCategory转换为companyDemotionCategory
        List<CompanyDemotionOrg> companyDemotionCategories = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(categories)) {
            categories.forEach(orgCategory -> {
                CompanyDemotionOrg companyDemotionOrg = new CompanyDemotionOrg();
                BeanUtils.copyProperties(orgCategory, companyDemotionOrg);
                companyDemotionCategories.add(companyDemotionOrg);
            });
        }
        return companyDemotionCategories;
    }

    /**
     * 1.降级至注册 查询升降级OU行
     * @param companyId
     * @param categoryIds
     * @return
     */
    public List<OrgCategory> demotionToRegistered(Long companyId, List<Long> categoryIds) {
        // 1.降级至注册带出的OU：仅带出黄牌，绿牌，认证中，一次性
        List<OrgCategory> orgCategories = new ArrayList<>();
        List<String> demotionToRegisteredStatus = Arrays.asList(CategoryStatus.YELLOW.name(), CategoryStatus.GREEN.name(), CategoryStatus.VERIFY.name(), CategoryStatus.ONE_TIME.name());
        if (CollectionUtils.isNotEmpty(categoryIds)) {
            orgCategories = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                    .eq(OrgCategory::getCompanyId, companyId)
                    .in(OrgCategory::getCategoryId, categoryIds)
                    .in(OrgCategory::getServiceStatus, demotionToRegisteredStatus)
            );
        }
        return orgCategories;
    }

    /**
     * 2.降级至黄牌 查询升降级OU行
     * @param companyId
     * @param categoryIds
     * @return
     */
    public List<OrgCategory> demotionToYellow(Long companyId, List<Long> categoryIds) {
        // 2.选择的升降级品类可以选择绿牌合作关系的品类
        List<OrgCategory> orgCategories = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(categoryIds)) {
            orgCategories = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                    .eq(OrgCategory::getCompanyId, companyId)
                    .in(OrgCategory::getCategoryId, categoryIds)
                    .eq(OrgCategory::getServiceStatus, CategoryStatus.GREEN.name())
            );
        }
        return orgCategories;
    }

    /**
     * 3.降级至红牌 查询升降级OU行
     * @param companyId
     * @param categoryIds
     * @return
     */
    public List<OrgCategory> demotionToRed(Long companyId, List<Long> categoryIds) {
        // 3.选择的升降级品类可以选择黄牌、绿牌合作关系的品类
        List<String> demotionToRedStatus = Arrays.asList(CategoryStatus.YELLOW.name(), CategoryStatus.GREEN.name());
        List<OrgCategory> orgCategories = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(categoryIds)) {
            orgCategories = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                    .eq(OrgCategory::getCompanyId, companyId)
                    .in(OrgCategory::getCategoryId, categoryIds)
                    .in(OrgCategory::getServiceStatus, demotionToRedStatus)
            );
        }
        return orgCategories;
    }

    /**
     * 4.降级至黑牌 查询升降级OU行
     * @param companyId
     * @param categoryIds
     * @return
     */
    public List<OrgCategory> demotionToBlack(Long companyId, List<Long> categoryIds) {
        // // 4.降级至黑牌 自动带出全部品类
        List<OrgCategory> orgCategories = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(categoryIds)) {
            orgCategories = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                    .eq(OrgCategory::getCompanyId, companyId)
                    .in(OrgCategory::getCategoryId, categoryIds)
            );
        }
        return orgCategories;
    }

    /**
     * 5.黄牌改善升级 查询升降级OU行
     * @param companyId
     * @param categoryIds
     * @return
     */
    public List<OrgCategory> yellowImprove(Long companyId, List<Long> categoryIds) {
        // 5.黄牌改善升级带出的OU，仅带出黄牌的业务实体
        List<OrgCategory> orgCategories = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(categoryIds)) {
            orgCategories = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                    .eq(OrgCategory::getCompanyId, companyId)
                    .eq(OrgCategory::getServiceStatus, CategoryStatus.YELLOW.name())
            );
        }
        return orgCategories;
    }

    /**
     * 保存或更新升降级OU行
     * @param companyDemotionOrgs
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateDemotionOrgs(List<CompanyDemotionOrg> companyDemotionOrgs) {
        // 获取数据已有的行数据
        Long companyDemotionId = companyDemotionOrgs.get(0).getCompanyDemotionId();
        List<CompanyDemotionOrg> dBCompanyDemotionOrgs = this.list(
                new QueryWrapper<>(new CompanyDemotionOrg().setCompanyDemotionId(companyDemotionId))
        );

        List<CompanyDemotionOrg> saveList = new ArrayList<>();
        List<CompanyDemotionOrg> updateList = new ArrayList<>();
        List<Long> deleteIdList = new ArrayList<>();

        Map<Long, CompanyDemotionOrg> map = companyDemotionOrgs.stream().filter(x -> Objects.nonNull(x.getCompanyDemotionOrgId()))
                .collect(Collectors.toMap(x -> x.getCompanyDemotionOrgId(), Function.identity()));

        Map<Long, CompanyDemotionOrg> dBMap = dBCompanyDemotionOrgs.stream()
                .collect(Collectors.toMap(x -> x.getCompanyDemotionOrgId(), Function.identity()));

        dBMap.forEach((k, v)-> {
            if (!map.containsKey(k)) {
                deleteIdList.add(k);
            }
        });

        companyDemotionOrgs.forEach(companyDemotionOrg -> {
            // 行id为空，新增
            if (Objects.isNull(companyDemotionOrg.getCompanyDemotionOrgId())) {
                Long demotionCategoryId = IdGenrator.generate();
                companyDemotionOrg.setCompanyDemotionOrgId(demotionCategoryId);
                saveList.add(companyDemotionOrg);
            }else {
                updateList.add(companyDemotionOrg);
            }
        });
        if (CollectionUtils.isNotEmpty(deleteIdList))
            this.removeByIds(deleteIdList);
        if (CollectionUtils.isNotEmpty(saveList))
            this.saveBatch(saveList);
        if (CollectionUtils.isNotEmpty(updateList)) {
            this.updateBatchById(updateList);
        }

    }

    /**
     * 获取OU行的key
     * @param companyDemotionOrg
     * @return
     */
    public String getDemotionOrgKey(CompanyDemotionOrg companyDemotionOrg) {
        StringBuffer sb = new StringBuffer();
        sb.append(companyDemotionOrg.getCategoryId()).append("-").append(companyDemotionOrg.getOrgId());
        return sb.toString();
    }

}
