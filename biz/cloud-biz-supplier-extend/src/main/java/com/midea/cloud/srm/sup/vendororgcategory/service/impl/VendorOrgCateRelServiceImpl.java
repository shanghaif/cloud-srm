package com.midea.cloud.srm.sup.vendororgcategory.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.review.CategoryStatus;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.FindVendorOrgCateRelParameter;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.VendorOrgCateRelVO;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.VendorOrgCateRelsVO;
import com.midea.cloud.srm.sup.info.mapper.OrgCategoryMapper;
import com.midea.cloud.srm.sup.vendororgcategory.mapper.VendorOrgCateRelMapper;
import com.midea.cloud.srm.sup.vendororgcategory.service.IVendorOrgCateRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implement of {@link IVendorOrgCateRelService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class VendorOrgCateRelServiceImpl implements IVendorOrgCateRelService {

    @Resource
    private VendorOrgCateRelMapper vendorOrgCateRelDao;
    @Autowired
    private OrgCategoryMapper orgCategoryMapper;
    @Autowired
    private BaseClient baseClient;


    @Override
    public List<CompanyInfo> findValidVendors(FindVendorOrgCateRelParameter parameter) {
        return vendorOrgCateRelDao.findValidVendorWithOrgCateRelPermission(parameter);
    }

    @Override
    public List<VendorOrgCateRelsVO> findValidVendorOrgCateRels(FindVendorOrgCateRelParameter parameter) {

        // 查询 供应商组织品类关系集
        List<VendorOrgCateRelVO> vendorOrgCateRelations = vendorOrgCateRelDao.findValidVendorOrgCateRelations(parameter);

        // 合并收集
        return VendorOrgCateRelsVO.mergeAndCollect(vendorOrgCateRelations);
    }

    @Override
    public List<VendorOrgCateRelsVO> findVendorOrgCateRels(FindVendorOrgCateRelParameter parameter) {

        // 查询 供应商组织品类关系集
        List<VendorOrgCateRelVO> vendorOrgCateRelations = vendorOrgCateRelDao.findVendorOrgCateRelations(parameter);

        // 合并收集
        return VendorOrgCateRelsVO.mergeAndCollect(vendorOrgCateRelations);
    }


  /*  @Override
    public List<CompanyInfo> findVendorForLogistic(Collection<String> midCategoryNames) {
        List<Long> categoryIds = baseClient.listCategoryIdsByMidNames(midCategoryNames);
        if (CollectionUtils.isEmpty(categoryIds)) {
            return Collections.EMPTY_LIST;
        }
        return orgCategoryMapper.selectList(Wrappers.lambdaQuery(OrgCategory.class)
                .in(OrgCategory::getCategoryId, categoryIds)
                .in(OrgCategory::getServiceStatus, CategoryStatus.GREEN,
                        CategoryStatus.YELLOW,
                        CategoryStatus.VERIFY,
                        CategoryStatus.ONE_TIME,
                        CategoryStatus.REGISTERED
                )
        ).stream().map(e -> BeanCopyUtil.copyProperties(e, CompanyInfo::new)).collect(Collectors.toList());

    }*/
}
