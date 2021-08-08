package com.midea.cloud.srm.base.purchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.purchase.mapper.CategoryPurchaseTypeMapper;
import com.midea.cloud.srm.base.purchase.service.ICategoryPurchaseTypeService;
import com.midea.cloud.srm.model.base.purchase.dto.CategoryPurchaseTypeDTO;
import com.midea.cloud.srm.model.base.purchase.entity.CategoryPurchaseType;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  物料小类对应采购类型 服务实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-26 14:15:53
 *  修改内容:
 * </pre>
 */
@Service
public class CategoryPurchaseTypeServiceImpl extends ServiceImpl<CategoryPurchaseTypeMapper, CategoryPurchaseType> implements ICategoryPurchaseTypeService {

    /**
     * 保存或更新
     * @param dto
     */
    @Override
    public void saveOrUpdateCategoryPurchaseTypes(CategoryPurchaseTypeDTO dto) {
        Assert.notNull(dto.getCategoryId(), "传入的物料小类为空！");
        Long categoryId = dto.getCategoryId();

        this.remove(new QueryWrapper<>(new CategoryPurchaseType().setCategoryId(categoryId)));
        List<CategoryPurchaseType> purchaseTypes = dto.getPurchaseTypes();
        if(!CollectionUtils.isEmpty(purchaseTypes)){
            purchaseTypes.forEach(purchaseType->{
                purchaseType.setCategoryId(categoryId)
                        .setCategoryCode(dto.getCategoryCode())
                        .setCategoryName(dto.getCategoryName());
                purchaseType.setId(IdGenrator.generate());
            });
            this.saveBatch(purchaseTypes);
        }
//        List<CategoryPurchaseType> categoryPurchaseTypes = dto.getPurchaseTypes();
//        //传入的采购类型集合为空，则删除数据库这个小类所有的对应关系
//        if (CollectionUtils.isEmpty(categoryPurchaseTypes)) {
//            QueryWrapper<CategoryPurchaseType> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("CATEGORY_ID", categoryId);
//            this.remove(queryWrapper);
//        } else {
//            List<String> purchaseTypeCodes = new ArrayList<>();
//            categoryPurchaseTypes.forEach(categoryPurchaseType -> {
//                purchaseTypeCodes.add(categoryPurchaseType.getCategoryCode());
//            });
//            List<CategoryPurchaseType> categoryPurchaseTypeList = this.list(Wrappers.lambdaQuery(CategoryPurchaseType.class)
//                    .eq(CategoryPurchaseType::getCategoryId, categoryId));
//            //新增
//            if (CollectionUtils.isEmpty(categoryPurchaseTypeList)){
//                categoryPurchaseTypes.forEach(categoryPurchaseType -> {
//                    Assert.notNull(categoryPurchaseType.getPurchaseTypeId(), "请选择采购类型！");
//                    Long id = IdGenrator.generate();
//                    CategoryPurchaseType saveCategoryPurchaseType = new CategoryPurchaseType();
//                    saveCategoryPurchaseType.setId(id)
//                            .setCategoryId(categoryId)
//                            .setCategoryCode(dto.getCategoryCode())
//                            .setCategoryName(dto.getCategoryName())
//                            .setPurchaseTypeId(categoryPurchaseType.getPurchaseTypeId())
//                            .setPurchaseTypeCode(categoryPurchaseType.getPurchaseTypeCode())
//                            .setPurchaseTypeName(categoryPurchaseType.getPurchaseTypeName());
//                    this.save(saveCategoryPurchaseType);
//                });
//            }
//            //先删除，后新增/修改
//            else {
//                QueryWrapper<CategoryPurchaseType> deleteWrapper = new QueryWrapper<>();
//                deleteWrapper.notIn("PURCHASE_TYPE_CODE", purchaseTypeCodes);
//                this.remove(deleteWrapper);
//                categoryPurchaseTypes.forEach(categoryPurchaseType -> {
//                    QueryWrapper<CategoryPurchaseType> categoryPurchaseTypeQueryWrapper = new QueryWrapper<>(
//                            new CategoryPurchaseType().setCategoryId(categoryId).setPurchaseTypeCode(categoryPurchaseType.getPurchaseTypeCode())
//                    );
//                    if (this.count(categoryPurchaseTypeQueryWrapper) == 0) {
//                        Assert.notNull(categoryPurchaseType.getPurchaseTypeId(), "请选择采购类型！");
//                        Long id = IdGenrator.generate();
//                        CategoryPurchaseType saveCategoryPurchaseType = new CategoryPurchaseType();
//                        saveCategoryPurchaseType.setId(id)
//                                .setCategoryId(categoryId)
//                                .setCategoryCode(dto.getCategoryCode())
//                                .setCategoryName(dto.getCategoryName())
//                                .setPurchaseTypeId(categoryPurchaseType.getPurchaseTypeId())
//                                .setPurchaseTypeCode(categoryPurchaseType.getPurchaseTypeCode())
//                                .setPurchaseTypeName(categoryPurchaseType.getPurchaseTypeName());
//                        this.save(saveCategoryPurchaseType);
//                    }
//                });
//            }
//        }
    }

    /**
     * 查询小类对应的采购类型
     * @param categoryId
     * @return
     */
    @Override
    public List<CategoryPurchaseType> listPurchaseTypes(Long categoryId) {
        List<CategoryPurchaseType> categoryPurchaseTypes = this.list(Wrappers.lambdaQuery(CategoryPurchaseType.class)
                .eq(CategoryPurchaseType::getCategoryId, categoryId));
        return categoryPurchaseTypes;
    }
}
