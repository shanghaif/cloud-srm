package com.midea.cloud.srm.supauth.purchasecatalog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplierauth.purchasecatalog.entity.PurchaseCatalog;
import com.midea.cloud.srm.model.supplierauth.purchasecatalog.entity.PurchaseCatalogVo;

import java.util.List;
import java.util.Map;


/**
*  <pre>
 *  采购目录 服务类
 * </pre>
*
* @author zhuwl7@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-23 11:36:03
 *  修改内容:
 * </pre>
*/
public interface IPurchaseCatalogService extends IService<PurchaseCatalog> {

    PageInfo<PurchaseCatalog> listPageByParam(PurchaseCatalog purchaseCatalog);

    PurchaseCatalog addPurchaseCatalog(PurchaseCatalog purchaseCatalog);

    PurchaseCatalog updatePurchaseCatalog(PurchaseCatalog purchaseCatalog);

    void bathDeleteByList(List<Long> catalogIds);

    PurchaseCatalog modifyStatus(PurchaseCatalog purchaseCatalog);

    PurchaseCatalog saveOrUpdateCatalog(PurchaseCatalog purchaseCatalog);

     void CommonCheck(PurchaseCatalog purchaseCatalog);

    List<PurchaseCatalog> bathSaveOrUpdateCatalog(List<PurchaseCatalog> purchaseCatalogs);

    void saveOrUpdateByQuaSample(PurchaseCatalog purchaseCatalog);

    void updateByMaterialTrial(PurchaseCatalog purchaseCatalog);

    Map<String,String> purchaseCatalogTitle();

    List<List<Object>> queryExportData(PurchaseCatalogVo purchaseCatalogVo);

    List<String> getMultilingualHeader(PurchaseCatalogVo purchaseCatalogVo);
}

