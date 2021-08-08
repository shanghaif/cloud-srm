package com.midea.cloud.srm.base.organization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.organization.entity.ErpMaterialItem;

import java.util.List;

/**
*  <pre>
 *  物料维护表（隆基物料同步） 服务类
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
public interface IErpMaterialItemService extends IService<ErpMaterialItem> {
    /**
     * Description 新增/修改物料和类别集子表
     * @Param
     * @return
     * @Author xiexh12@meicloud.com
     * @Date 2020.08.21
     * @throws
     **/
    boolean saveOrUpdateMaterialAndCategorySet(List<ErpMaterialItem> erpMaterialItemList);

    /**
     * 设置这条物料数据的类别 categoryList
     * @param erpMaterialItem
     */
    void setErpMaterialItemCategoryList(ErpMaterialItem erpMaterialItem);
}
