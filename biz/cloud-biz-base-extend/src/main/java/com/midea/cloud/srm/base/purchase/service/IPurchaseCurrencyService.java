package com.midea.cloud.srm.base.purchase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;

import java.util.List;

/**
*  <pre>
 *  币种设置 服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-26 14:08:07
 *  修改内容:
 * </pre>
*/
public interface IPurchaseCurrencyService extends IService<PurchaseCurrency> {

    /**
     * 分页条件查询
     * @param purchaseCurrency
     * @return
     */
    PageInfo<PurchaseCurrency> listPage(PurchaseCurrency purchaseCurrency);

    /**
     * 新增或编辑币种
     * @param purchaseCurrency
     */
    void saveOrUpdateCurrency(PurchaseCurrency purchaseCurrency);

    List<PurchaseCurrency> listEnableAndLanguage();

    /**
     * 查询所有币种(价格库导入使用)
     * @return
     */
    List<PurchaseCurrency> listAllCurrencyForImport();
}
