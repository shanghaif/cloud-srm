package com.midea.cloud.srm.sup.change.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.change.entity.CategoryRelChange;
import com.midea.cloud.srm.model.supplier.info.dto.CategoryRelQueryDTO;

import java.util.List;

/**
*  <pre>
 *  可供品类变更 服务类
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
public interface ICategoryRelChangeService extends IService<CategoryRelChange> {

    void saveOrUpdateList(List<CategoryRelChange> categoryRels, Long companyId, Long changeId);

    List<CategoryRelChange> queryByChangeId(Long changeId);

    void removeByChangeId(Long changeId);

    PageInfo<CategoryRelChange> listPageChangeByParam(CategoryRelQueryDTO categoryRelQueryDTO);
}
