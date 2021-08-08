package com.midea.cloud.srm.base.purchase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseUnit;

import java.util.List;

/**
*  <pre>
 *  单位设置 服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-28 11:40:17
 *  修改内容:
 * </pre>
*/
public interface IPurchaseUnitService extends IService<PurchaseUnit> {

    /**
     * 分页条件查询
     * @param purchaseUnit
     * @return
     */
    PageInfo<PurchaseUnit> listPage(PurchaseUnit purchaseUnit);


    /**
     * 新增或编辑单位设置
     * @param purchaseUnit
     */
    void saveOrUpdateUnit(PurchaseUnit purchaseUnit);

    List<PurchaseUnit> listEnableAndLanguage();

    List<PurchaseUnit> listPurchaseUnitByCodeList(List<String> purchaseUnitList);
}
