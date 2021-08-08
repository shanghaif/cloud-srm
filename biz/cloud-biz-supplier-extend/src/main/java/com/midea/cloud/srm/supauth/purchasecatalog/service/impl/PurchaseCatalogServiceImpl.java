package com.midea.cloud.srm.supauth.purchasecatalog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.CatalogStatusType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.BeanMapUtils;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplierauth.purchasecatalog.entity.PurchaseCatalog;
import com.midea.cloud.srm.model.supplierauth.purchasecatalog.entity.PurchaseCatalogVo;
import com.midea.cloud.srm.supauth.purchasecatalog.mapper.PurchaseCatalogMapper;
import com.midea.cloud.srm.supauth.purchasecatalog.service.IPurchaseCatalogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
*  <pre>
 *  采购目录 服务实现类
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
@Service
public class PurchaseCatalogServiceImpl extends ServiceImpl<PurchaseCatalogMapper, PurchaseCatalog> implements IPurchaseCatalogService {
    /**
     * 采购目录标题列表-中文
     */
    public static final Map<String, String> purchaseCatalogTitle_CH;
    /**
     * 采购目录标题列表-日文
     */
    public static final Map<String, String> purchaseCatalogTitle_JP;
    /**
     * 采购目录标题列表-英文
     */
    public static final Map<String, String> purchaseCatalogTitle_US;

    static {
        purchaseCatalogTitle_CH = new LinkedHashMap<>();
        purchaseCatalogTitle_JP = new LinkedHashMap<>();
        purchaseCatalogTitle_US = new LinkedHashMap<>();

        purchaseCatalogTitle_CH.put("vendorCode","供应商编码");
        purchaseCatalogTitle_CH.put("vendorName","供应商名字");
        purchaseCatalogTitle_CH.put("purchaseOrgName","采购组织");
        purchaseCatalogTitle_CH.put("materialCode","物料编码");
        purchaseCatalogTitle_CH.put("materialName","物料名称");
        purchaseCatalogTitle_CH.put("categoryName","品类");
        purchaseCatalogTitle_CH.put("catalogStatus","状态");
        purchaseCatalogTitle_CH.put("createdBy","创建人");
        purchaseCatalogTitle_CH.put("creationDate","创建时间");
        purchaseCatalogTitle_CH.put("updatedReason","更新原因");
        purchaseCatalogTitle_CH.put("lastUpdatedBy","更新人");
        purchaseCatalogTitle_CH.put("lastUpdateDate","更新时间");

        purchaseCatalogTitle_JP.put("vendorCode","サプライヤーコード");
        purchaseCatalogTitle_JP.put("vendorName","サプライヤ名");
        purchaseCatalogTitle_JP.put("purchaseOrgName","購買組織");
        purchaseCatalogTitle_JP.put("materialCode","材料コーディング");
        purchaseCatalogTitle_JP.put("materialName","素材名");
        purchaseCatalogTitle_JP.put("categoryName","カテゴリー");
        purchaseCatalogTitle_JP.put("catalogStatus","状態");
        purchaseCatalogTitle_JP.put("createdBy","創設者");
        purchaseCatalogTitle_JP.put("creationDate","作成時間");
        purchaseCatalogTitle_JP.put("updatedReason","更新の理由");
        purchaseCatalogTitle_JP.put("lastUpdatedBy","アップデーター");
        purchaseCatalogTitle_JP.put("lastUpdateDate","更新時間");

        purchaseCatalogTitle_US.put("vendorCode","Supplier Code");
        purchaseCatalogTitle_US.put("vendorName","Supplier name");
        purchaseCatalogTitle_US.put("purchaseOrgName","Purchasing organization");
        purchaseCatalogTitle_US.put("materialCode","Material coding");
        purchaseCatalogTitle_US.put("materialName","Material name");
        purchaseCatalogTitle_US.put("categoryName","category");
        purchaseCatalogTitle_US.put("catalogStatus","status");
        purchaseCatalogTitle_US.put("createdBy","founder");
        purchaseCatalogTitle_US.put("creationDate","Creation time");
        purchaseCatalogTitle_US.put("updatedReason","Reason for update");
        purchaseCatalogTitle_US.put("lastUpdatedBy","updater");
        purchaseCatalogTitle_US.put("lastUpdateDate","Update time");
    }

    @Override
    public PageInfo<PurchaseCatalog> listPageByParam(PurchaseCatalog purchaseCatalog) {
        List<PurchaseCatalog> purchaseCatalogList = this.getPurchaseCatalogs(purchaseCatalog);
        return new PageInfo<PurchaseCatalog>(purchaseCatalogList);
    }

    public List<PurchaseCatalog> getPurchaseCatalogs(PurchaseCatalog purchaseCatalog) {
        PurchaseCatalog queryPurchaseCatalog = new PurchaseCatalog();
        if(purchaseCatalog.getPurchaseOrgId() != null){
            queryPurchaseCatalog.setPurchaseOrgId(purchaseCatalog.getPurchaseOrgId());
        }
        if(StringUtils.isNotBlank(purchaseCatalog.getPurchaseOrgCode())){
            queryPurchaseCatalog.setPurchaseOrgCode(purchaseCatalog.getPurchaseOrgCode());
        }
        if(purchaseCatalog.getMaterialId()!= null){
            queryPurchaseCatalog.setMaterialId(purchaseCatalog.getMaterialId());
        }
        if(StringUtils.isNotBlank(purchaseCatalog.getMaterialCode())){
            queryPurchaseCatalog.setMaterialCode(purchaseCatalog.getMaterialCode());
        }
        if(StringUtils.isNotBlank(purchaseCatalog.getCatalogStatus())){
            queryPurchaseCatalog.setCatalogStatus(purchaseCatalog.getCatalogStatus());
        }
        if(purchaseCatalog.getCategoryId() != null){
            queryPurchaseCatalog.setCatalogId(purchaseCatalog.getCategoryId());
        }
        if(StringUtils.isNotBlank(purchaseCatalog.getCategoryCode())){
            queryPurchaseCatalog.setCategoryCode(purchaseCatalog.getCategoryCode());
        }
        if(purchaseCatalog.getVendorId() != null){
            queryPurchaseCatalog.setVendorId(purchaseCatalog.getVendorId());
        }

        QueryWrapper<PurchaseCatalog> wrapper = new QueryWrapper<PurchaseCatalog>(queryPurchaseCatalog);
        if(StringUtils.isNotBlank(purchaseCatalog.getCreatedBy())){
            wrapper.like("CREATED_BY",purchaseCatalog.getCreatedBy());
        }
        if(StringUtils.isNotBlank(purchaseCatalog.getVendorName())){
            wrapper.like("VENDOR_NAME",purchaseCatalog.getVendorName());
        }
        if(StringUtils.isNotBlank(purchaseCatalog.getVendorCode())){
            wrapper.like("VENDOR_CODE",purchaseCatalog.getVendorCode());
        }
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return this.list(wrapper);
    }

    @Override
    public PurchaseCatalog addPurchaseCatalog(PurchaseCatalog purchaseCatalog) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Long id = IdGenrator.generate();
        purchaseCatalog.setCatalogId(id);
        if(StringUtils.isBlank(purchaseCatalog.getCatalogStatus())) {
            purchaseCatalog.setCatalogStatus(CatalogStatusType.VALID.getValue());
        }
        purchaseCatalog.setUpdatedReason("手工新增");
        this.save(purchaseCatalog);
        return purchaseCatalog;
    }

    @Override
    public PurchaseCatalog updatePurchaseCatalog(PurchaseCatalog purchaseCatalog) {
        if(purchaseCatalog.getCatalogId() != null){
            this.updateById(purchaseCatalog);
            return purchaseCatalog;
        }
        return null;
    }

    @Override
    public void bathDeleteByList(List<Long> catalogIds) {
       this.removeByIds(catalogIds);
    }

    @Override
    public PurchaseCatalog modifyStatus(PurchaseCatalog purchaseCatalog) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(purchaseCatalog.getCatalogId() != null && StringUtils.isNotBlank(purchaseCatalog.getCatalogStatus())){
            PurchaseCatalog exstsPurchaseCatalog = this.getById(purchaseCatalog.getCatalogId());
            exstsPurchaseCatalog.setUpdatedReason("手工修改");
            exstsPurchaseCatalog.setCatalogStatus(purchaseCatalog.getCatalogStatus());
            this.updatePurchaseCatalog(exstsPurchaseCatalog);
            return exstsPurchaseCatalog;
        }
        return null;
    }

    @Override
    @Transactional
    public PurchaseCatalog saveOrUpdateCatalog(PurchaseCatalog purchaseCatalog) {
        PurchaseCatalog returnCataLog= new PurchaseCatalog();
        if(purchaseCatalog.getCatalogId() != null){
            returnCataLog=  this.modifyStatus(purchaseCatalog);
        }else{
            returnCataLog = this.addPurchaseCatalog(purchaseCatalog);
        }
        return returnCataLog;
    }

    public void CommonCheck(PurchaseCatalog purchaseCatalog){
        Assert.notNull(purchaseCatalog,LocaleHandler.getLocaleMsg("不能传空值"));

        if(purchaseCatalog.getVendorId() == null
               || purchaseCatalog.getPurchaseOrgId() == null
                 || purchaseCatalog.getMaterialId() == null
        ){
            throw new BaseException(LocaleHandler.getLocaleMsg("供应商、采购组织、物料不能为空"));
        }

        PurchaseCatalog queryPurchaseCatalog = new PurchaseCatalog();
        queryPurchaseCatalog.setVendorId(purchaseCatalog.getVendorId());
        queryPurchaseCatalog.setPurchaseOrgId(purchaseCatalog.getPurchaseOrgId());
        queryPurchaseCatalog.setMaterialId(purchaseCatalog.getMaterialId());
        QueryWrapper<PurchaseCatalog> wrapper = new QueryWrapper<PurchaseCatalog>(queryPurchaseCatalog);
        if(purchaseCatalog.getCatalogId() != null){
            wrapper.ne("CATALOG_ID",purchaseCatalog.getCatalogId());
        }
        if(!CollectionUtils.isEmpty(this.list(wrapper))){
            throw new BaseException(LocaleHandler.getLocaleMsg("采购目录重复"));
        }
    }

    @Override
    public List<PurchaseCatalog> bathSaveOrUpdateCatalog(List<PurchaseCatalog> purchaseCatalogs) {
        List<PurchaseCatalog> results = new ArrayList<>();
        for(PurchaseCatalog  purchaseCatalog:purchaseCatalogs){
            this.CommonCheck(purchaseCatalog);
            results.add(this.saveOrUpdateCatalog(purchaseCatalog));
        }
        return results;
    }

    @Override
    public void saveOrUpdateByQuaSample(PurchaseCatalog purchaseCatalog) {
        PurchaseCatalog query = new PurchaseCatalog();
        query.setVendorId(purchaseCatalog.getVendorId());
        query.setPurchaseOrgId(purchaseCatalog.getPurchaseOrgId());
        query.setMaterialId(purchaseCatalog.getMaterialId());
        QueryWrapper<PurchaseCatalog> queryWrapper = new QueryWrapper<>(query);
        List<PurchaseCatalog> purchaseCatalogs = this.list(queryWrapper);
        if(!CollectionUtils.isEmpty(purchaseCatalogs)){
            PurchaseCatalog result = purchaseCatalogs.get(0).setCatalogStatus(purchaseCatalog.getCatalogStatus());
            result.setUpdatedReason(purchaseCatalog.getUpdatedReason());
            result.setCatalogStatus(purchaseCatalog.getCatalogStatus());
            result.setPurchaseOrgName(purchaseCatalog.getPurchaseOrgName());
            result.setPurchaseOrgCode(purchaseCatalog.getPurchaseOrgCode());
            result.setVendorCode(purchaseCatalog.getVendorCode());
            result.setVendorName(purchaseCatalog.getVendorName());
            result.setCategoryFullName(purchaseCatalog.getCategoryFullName());
            this.updateById(result);
        }else{
            Long id = IdGenrator.generate();
            purchaseCatalog.setCatalogId(id);
           this.save(purchaseCatalog);
        }
    }

    @Override
    public void updateByMaterialTrial(PurchaseCatalog purchaseCatalog) {
        PurchaseCatalog query = new PurchaseCatalog();
        query.setVendorId(purchaseCatalog.getVendorId());
        query.setPurchaseOrgId(purchaseCatalog.getPurchaseOrgId());
        query.setMaterialId(purchaseCatalog.getMaterialId());
        QueryWrapper<PurchaseCatalog> queryWrapper = new QueryWrapper<>(query);
        List<PurchaseCatalog> purchaseCatalogs = this.list(queryWrapper);
        if(!CollectionUtils.isEmpty(purchaseCatalogs)){
            PurchaseCatalog result = purchaseCatalogs.get(0).setCatalogStatus(purchaseCatalog.getCatalogStatus());
            result.setUpdatedReason(purchaseCatalog.getUpdatedReason());
            result.setCatalogStatus(purchaseCatalog.getCatalogStatus());
            this.updateById(result);
        }
    }

    @Override
    public Map<String, String> purchaseCatalogTitle() {
        // 获取当前用户的环境语言格式
        String localeKey = LocaleHandler.getLocaleKey();
        switch (localeKey){
            case "en_US" :
                return purchaseCatalogTitle_US;
            case "ja_JP" :
                return purchaseCatalogTitle_JP;
            default:
                return purchaseCatalogTitle_CH;
        }
    }

    @Override
    public List<List<Object>> queryExportData(PurchaseCatalogVo purchaseCatalogVo) {
        // 非空校验
        Assert.notNull(purchaseCatalogVo.getQueryParam(),"采购目录查询参数不能为空");
        Assert.notEmpty(purchaseCatalogVo.getTitleList(),"采购目录导出标题不能为空");
        Assert.hasLength(purchaseCatalogVo.getFileName(),"导出文件名不能为空");

        // 声明结果集
        List<List<Object>> resultList = new ArrayList<>();
        // 获取查询条件
        PurchaseCatalog purchaseCatalog = purchaseCatalogVo.getQueryParam();
        // 判断是否需要分页
        boolean flag = (null != purchaseCatalog.getPageNum() && purchaseCatalog.getPageNum() >0) && (null != purchaseCatalog.getPageSize() && purchaseCatalog.getPageSize() >0);
        // 设置分页
        if (flag) {
            PageUtil.startPage(purchaseCatalog.getPageNum(), purchaseCatalog.getPageSize());
        }
        // 查询采购目录数据
        List<PurchaseCatalog> purchaseCatalogList = this.getPurchaseCatalogs(purchaseCatalog);
        // 对象转map
        List<Map<String, Object>> purchaseCatalogMapList = BeanMapUtils.objectsToMaps(purchaseCatalogList);
        // 获取采购目录导出标题
        ArrayList<String> purchaseCatalogTitle = purchaseCatalogVo.getTitleList();
        // 获取标题集指定数据
        for(Map<String,Object> purchaseCatalogMap : purchaseCatalogMapList){
            ArrayList<Object> objects = new ArrayList<>();
            for(String title : purchaseCatalogTitle){
                if ("catalogStatus".equals(title)) {
                    // 状态转值
                    Object catalogStatus = purchaseCatalogMap.get(title);
                    switch (catalogStatus.toString()) {
                        case "VALID":
                            objects.add("有效");
                            break;
                        default:
                            objects.add("失效");
                    }
                } else {
                    objects.add(purchaseCatalogMap.get(title));
                }
            }
            resultList.add(objects);
        }
        return resultList;
    }

    @Override
    public List<String> getMultilingualHeader(PurchaseCatalogVo purchaseCatalogVo) {
        // 非空校验
        Assert.notEmpty(purchaseCatalogVo.getTitleList(),"采购目录导出标题不能为空");
        // 获取采购目录导出标题
        ArrayList<String> purchaseCatalogTitleList = purchaseCatalogVo.getTitleList();
        // 获取当前用户的环境语言格式
        String localeKey = LocaleHandler.getLocaleKey();
        // 获取多语言标题
        Map<String, String> purchaseCatalogTitle = null;
        switch (localeKey){
            case "en_US" :
                purchaseCatalogTitle = purchaseCatalogTitle_US;
                break;
            case "ja_JP" :
                purchaseCatalogTitle = purchaseCatalogTitle_JP;
                break;
            default:
                purchaseCatalogTitle = purchaseCatalogTitle_CH;
                break;
        }
        ArrayList<String> resultList = new ArrayList<>();
        for(String title : purchaseCatalogTitleList){
            resultList.add(purchaseCatalogTitle.get(title));
        }
        return resultList;
    }
}
