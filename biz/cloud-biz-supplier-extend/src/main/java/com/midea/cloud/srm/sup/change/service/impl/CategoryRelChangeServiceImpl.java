package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.change.entity.CategoryRelChange;
import com.midea.cloud.srm.model.supplier.info.dto.CategoryRelQueryDTO;
import com.midea.cloud.srm.sup.change.mapper.CategoryRelChangeMapper;
import com.midea.cloud.srm.sup.change.service.ICategoryRelChangeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
*  <pre>
 *  可供品类变更 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-05 15:36:01
 *  修改内容:
 * </pre>
*/
@Service
public class CategoryRelChangeServiceImpl extends ServiceImpl<CategoryRelChangeMapper, CategoryRelChange> implements ICategoryRelChangeService {

    @Override
    public void saveOrUpdateList(List<CategoryRelChange> categoryRels, Long companyId, Long changeId) {
        CategoryRelChange del = new CategoryRelChange();
        del.setChangeId(changeId);
        this.remove(new QueryWrapper<>(del));
        if(!CollectionUtils.isEmpty(categoryRels)){
            categoryRels.stream().forEach(rel-> {
                rel.setCompanyId(companyId);
                rel.setChangeId(changeId);
                if(rel.getCategoryRelChangeId() != null){
                    this.updateById(rel);
                }else{
                    Long id = IdGenrator.generate();
                    rel.setCategoryRelChangeId(id);
                    if(rel.getCategoryRelId() == null){
                        rel.setCategoryRelId(id);
                    }
                    this.save(rel);
                }
            });
        }
    }

    @Override
    public List<CategoryRelChange> queryByChangeId(Long changeId) {
        CategoryRelChange query = new CategoryRelChange();
        query.setChangeId(changeId);
        return changeId != null?this.list(new QueryWrapper<>(query)):null;
    }

    @Override
    public void removeByChangeId(Long changeId) {
        CategoryRelChange query = new CategoryRelChange();
        query.setChangeId(changeId);
        this.remove(new QueryWrapper<>(query));
    }

    @Override
    public PageInfo<CategoryRelChange> listPageChangeByParam(CategoryRelQueryDTO categoryRelQueryDTO) {
        return null;
    }
}
