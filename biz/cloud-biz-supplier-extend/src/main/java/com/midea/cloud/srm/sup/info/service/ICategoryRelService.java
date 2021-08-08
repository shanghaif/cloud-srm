package com.midea.cloud.srm.sup.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.info.dto.CategoryRelQueryDTO;
import com.midea.cloud.srm.model.supplier.info.entity.CategoryRel;

import java.util.List;

/**
*  <pre>
 *  可供品类 服务类
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
public interface ICategoryRelService extends IService<CategoryRel> {

    /**
     * 可供品类保存
     * @param categoryRels
     * @param companyId
     */
    void saveOrUpdateList(List<CategoryRel> categoryRels, Long companyId);

    List<CategoryRel>   queryByCompanyId(Long companyId);

    void removeByCompanyId(Long companyId);

    PageInfo<CategoryRel> listPageByParam(CategoryRelQueryDTO categoryRelQueryDTO);
}
