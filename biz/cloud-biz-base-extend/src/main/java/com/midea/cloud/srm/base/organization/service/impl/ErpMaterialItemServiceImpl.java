package com.midea.cloud.srm.base.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.base.organization.mapper.ErpMaterialItemMapper;
import com.midea.cloud.srm.base.organization.service.ICategoryService;
import com.midea.cloud.srm.base.organization.service.IErpMaterialItemService;
import com.midea.cloud.srm.model.base.organization.entity.Category;
import com.midea.cloud.srm.model.base.organization.entity.ErpMaterialItem;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
*  <pre>
 *  物料维护表（隆基物料同步） 服务实现类
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-20 15:19:46
 *  修改内容:
 * </pre>
*/
@Service
public class ErpMaterialItemServiceImpl extends ServiceImpl<ErpMaterialItemMapper, ErpMaterialItem> implements IErpMaterialItemService {

    /**erp物料类别集Service*/
    @Resource
    private ICategoryService iCategoryService;

    @Override
    @Transactional
    public boolean saveOrUpdateMaterialAndCategorySet(List<ErpMaterialItem> erpMaterialItemList) {
        boolean isSaveOrUpdate = false;
        if(CollectionUtils.isNotEmpty(erpMaterialItemList)){
            for(ErpMaterialItem erpMaterialItem : erpMaterialItemList){
                if(null != erpMaterialItem.getItemId()){
                    isSaveOrUpdate = this.saveOrUpdate(erpMaterialItem);
                }

                List<Category> categoryList = erpMaterialItem.getCategoryList();
                if(CollectionUtils.isNotEmpty(categoryList)){
                    for(Category category : categoryList){
                        if(null != category.getCategorySetCode()) {
                            isSaveOrUpdate = iCategoryService.saveOrUpdate(category);
                        }
                    }
                }

            }
        }
        return isSaveOrUpdate;
    }

    /**
     * 设置这条物料数据的类别 categoryList
     * @param erpMaterialItem
     */
    @Override
    public void setErpMaterialItemCategoryList(ErpMaterialItem erpMaterialItem) {
        Assert.notNull(erpMaterialItem, "传入的物料为空！");
        Assert.notNull(erpMaterialItem.getItemId(), "传入的物料的erp物料Id为空！");
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>(
                new Category().setItemId(erpMaterialItem.getItemId())
        );
    }
}
