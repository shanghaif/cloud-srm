package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.material.dto.MaterialQueryDTO;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.base.serviceconfig.entity.ServiceConfig;
import com.midea.cloud.srm.model.supplier.info.dto.CategoryRelQueryDTO;
import com.midea.cloud.srm.model.supplier.info.entity.CategoryRel;
import com.midea.cloud.srm.sup.info.mapper.CategoryRelMapper;
import com.midea.cloud.srm.sup.info.service.ICategoryRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  可供品类 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-05 14:51:06
 *  修改内容:
 * </pre>
*/
@Service
public class CategoryRelServiceImpl extends ServiceImpl<CategoryRelMapper, CategoryRel> implements ICategoryRelService {

    @Autowired
    BaseClient baseClient;

    @Override
    public void saveOrUpdateList(List<CategoryRel> categoryRels, Long companyId) {
        CategoryRel del = new CategoryRel();
        del.setCompanyId(companyId);
        this.remove(new QueryWrapper<>(del));
        if(!CollectionUtils.isEmpty(categoryRels)){
            categoryRels.stream().forEach(rel-> {
                rel.setCompanyId(companyId);
                if(rel.getCategoryRelId() == null){
                    Long id = IdGenrator.generate();
                    rel.setCategoryRelId(id);
                }
                this.saveOrUpdate(rel);
            });
        }
    }

    @Override
    public List<CategoryRel> queryByCompanyId(Long companyId) {
        CategoryRel query = new CategoryRel();
        query.setCompanyId(companyId);
        return companyId != null?this.list(new QueryWrapper<>(query)):null;
    }

    @Override
    public void removeByCompanyId(Long companyId) {
        CategoryRel query = new CategoryRel();
        query.setCompanyId(companyId);
        this.remove(new QueryWrapper<>(query));
    }

    @Override
    public PageInfo<CategoryRel> listPageByParam(CategoryRelQueryDTO categoryRelQueryDTO) {
        List<PurchaseCategory> purchaseCategories = categoryRelQueryDTO.getPurchaseCategories();
        // 查找最小级采购分类
        List<PurchaseCategory> categories = baseClient.queryMinLevelCategory(purchaseCategories);
        // 查找品类关系
        List<CategoryRel> categoryRels = new ArrayList<>();
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(categories)){
            PageUtil.startPage(categoryRelQueryDTO.getPageNum(), categoryRelQueryDTO.getPageSize());
            List<Long> longs = new ArrayList<>();
            categories.forEach(temp->longs.add(temp.getCategoryId()));
            categoryRels = this.baseMapper.listPageByParam(categoryRelQueryDTO, longs);
        }
        return new PageInfo<>(categoryRels);
    }

}
