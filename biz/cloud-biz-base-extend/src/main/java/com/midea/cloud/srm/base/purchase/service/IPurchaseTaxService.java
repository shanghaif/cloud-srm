package com.midea.cloud.srm.base.purchase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;

import java.util.List;

/**
*  <pre>
 *  税率设置 服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-27 15:06:10
 *  修改内容:
 * </pre>
*/
public interface IPurchaseTaxService extends IService<PurchaseTax> {


    /**
     * 分页条件查询
     * @return
     */
    PageInfo<PurchaseTax> listPage(PurchaseTax purchaseTax);

    /**
     * 新增或编辑税率
     * @param purchaseTax
     */
    void saveOrUpdateTax(PurchaseTax purchaseTax);

    List<PurchaseTax> listEnableAndLanguage();
}
