package com.midea.cloud.srm.base.purchase.service;

import com.midea.cloud.srm.model.base.purchase.dto.CategoryPurchaseTypeDTO;
import com.midea.cloud.srm.model.base.purchase.entity.CategoryPurchaseType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
*  <pre>
 *  物料小类对应采购类型 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-26 14:15:53
 *  修改内容:
 * </pre>
*/
public interface ICategoryPurchaseTypeService extends IService<CategoryPurchaseType> {

    void saveOrUpdateCategoryPurchaseTypes(CategoryPurchaseTypeDTO dto);

    List<CategoryPurchaseType> listPurchaseTypes(Long categoryId);
}
