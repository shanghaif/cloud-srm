package com.midea.cloud.srm.base.purchase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.base.purchase.service.IPurchaseCategoryService;
import com.midea.cloud.srm.model.base.material.vo.MaterialMaxCategoryVO;
import com.midea.cloud.srm.model.base.purchase.dto.PurchaseCategoryAllInfo;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  采购分类 前端控制器
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-06 15:38:44
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/purchase/purchaseCategory")
public class PurchaseCategoryController extends BaseController {

    @Autowired
    private IPurchaseCategoryService iPurchaseCategoryService;

    /**
     * 根据中类编码查找小类
     * @param middleCode
     * @return
     */
    @GetMapping("/queryPurchaseCategoryByMiddleCode")
    public List<PurchaseCategory> queryPurchaseCategoryByMiddleCode(@RequestParam("middleCode") String middleCode){
        return iPurchaseCategoryService.queryPurchaseCategoryByMiddleCode(middleCode);
    }

    /**
     * 查找采购分类信息
     * @return 小类ID-全路径信息
     */
    @GetMapping("/queryPurchaseCategoryAllInfo")
    public Map<Long, PurchaseCategoryAllInfo> queryPurchaseCategoryAllInfo(){
        HashMap<Long, PurchaseCategoryAllInfo> infoHashMap = new HashMap<>();
        List<PurchaseCategory> purchaseCategories = this.listAll();
        if (CollectionUtils.isNotEmpty(purchaseCategories)) {
            // 品类集合(id-对象)
            Map<Long, PurchaseCategory> categoryMap = purchaseCategories.stream().collect(Collectors.toMap(PurchaseCategory::getCategoryId, Function.identity(), (k1, k2) -> k1));
            // 小类集合
            List<PurchaseCategory> categories = purchaseCategories.stream().filter(purchaseCategory -> 3 == purchaseCategory.getLevel()).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(categories)){
                categories.forEach(purchaseCategory -> {
                    Long categoryId = purchaseCategory.getCategoryId();
                    String struct = purchaseCategory.getStruct();
                    if(StringUtil.notEmpty(struct)){
                        String[] ids = struct.split("-");
                        PurchaseCategoryAllInfo purchaseCategoryAllInfo = new PurchaseCategoryAllInfo();

                        PurchaseCategory purchaseCategory1 = categoryMap.get(Long.valueOf(ids[0]));
                        if(null != purchaseCategory1){
                            purchaseCategoryAllInfo.setCategoryId1(purchaseCategory1.getCategoryId());
                            purchaseCategoryAllInfo.setCategoryCode1(purchaseCategory1.getCategoryCode());
                            purchaseCategoryAllInfo.setCategoryName1(purchaseCategory1.getCategoryName());
                        }
                        PurchaseCategory purchaseCategory2 = categoryMap.get(Long.valueOf(ids[1]));
                        if(null != purchaseCategory2){
                            purchaseCategoryAllInfo.setCategoryId2(purchaseCategory2.getCategoryId());
                            purchaseCategoryAllInfo.setCategoryCode2(purchaseCategory2.getCategoryCode());
                            purchaseCategoryAllInfo.setCategoryName2(purchaseCategory2.getCategoryName());
                        }
                        PurchaseCategory purchaseCategory3 = categoryMap.get(Long.valueOf(ids[2]));
                        if(null != purchaseCategory3){
                            purchaseCategoryAllInfo.setCategoryId3(purchaseCategory3.getCategoryId());
                            purchaseCategoryAllInfo.setCategoryCode3(purchaseCategory3.getCategoryCode());
                            purchaseCategoryAllInfo.setCategoryName3(purchaseCategory3.getCategoryName());
                        }
                        infoHashMap.put(categoryId,purchaseCategoryAllInfo);
                    }
                });
            }

        }
        return infoHashMap;
    }

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public PurchaseCategory get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iPurchaseCategoryService.getById(id);
    }

    /**
     * 新增
     *
     * @param purchaseCategory
     */
    @PostMapping("/add")
    public void add(PurchaseCategory purchaseCategory) {
        Long id = IdGenrator.generate();
        purchaseCategory.setCategoryId(id);
        iPurchaseCategoryService.save(purchaseCategory);
    }

    /**
     * 删除
     *
     * @param categoryId
     */
    @PostMapping("/delete")
    public void delete(Long categoryId) {
        Assert.notNull(categoryId, "categoryId不能为空");
        iPurchaseCategoryService.removeById(categoryId);
    }

    /**
     * 修改
     *
     * @param purchaseCategory
     */
    @PostMapping("/modify")
    public void modify(PurchaseCategory purchaseCategory) {
        iPurchaseCategoryService.updateById(purchaseCategory);
    }

    /**
     * 分页条件查询
     *
     * @param purchaseCategory
     * @return
     */
    @PostMapping("/listPageByParm")
    public PageInfo<PurchaseCategory> listPageByParm(@RequestBody PurchaseCategory purchaseCategory) {
        return iPurchaseCategoryService.listPageByParm(purchaseCategory);
    }

    /**
     * 根据管理层级设置取对应的采购品类
     *
     * @param purchaseCategory
     * @return
     */
    @PostMapping("/listByLevel")
    public List<PurchaseCategory> listByLevel(@RequestBody PurchaseCategory purchaseCategory) {
        return iPurchaseCategoryService.listByLevel(purchaseCategory);
    }

    /**
     * 根据管理层级设置取对应的物流采购品类
     * @param purchaseCategory
     * @return
     */
    @PostMapping("/listLogisticsCategoryByLevel")
    public List<PurchaseCategory> listLogisticsCategoryByLevel(@RequestBody PurchaseCategory purchaseCategory){
        return iPurchaseCategoryService.listLogisticsCategoryByLevel(purchaseCategory);
    }


    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<PurchaseCategory> listAll() {
        return iPurchaseCategoryService.list();
    }

    /**
     * 查找父品类(根据产品服务层级)
     *
     * @return
     */
    @PostMapping("/listParent")
    public List<PurchaseCategory> listParent() {
        return iPurchaseCategoryService.listParent();
    }

    /**
     * 批量添加采购分类
     *
     * @param purchaseCategories
     */
    @PostMapping("/batchSaveOrUpdate")
    public void batchSaveOrUpdate(@RequestBody List<PurchaseCategory> purchaseCategories) {
        iPurchaseCategoryService.batchSaveOrUpdate(purchaseCategories);
    }

    /**
     * 根据父品类ID查询子品类(1级时,ID传入-1)
     *
     * @param categoryId
     * @return
     */
    @PostMapping("/listChildren")
    public List<PurchaseCategory> listChildren(Long categoryId) {
        return iPurchaseCategoryService.listChildren(categoryId);
    }

    /**
     * 条件查询
     *
     * @param purchaseCategory
     * @return
     */
    @PostMapping("/getByParm")
    public PurchaseCategory getByParm(@RequestBody PurchaseCategory purchaseCategory) {
        return iPurchaseCategoryService.getByParm(purchaseCategory);
    }

    /**
     * 根据名称列表批量查询
     *
     * @param purchaseCategoryNameList
     * @return
     */
    @PostMapping("/listByNameBatch")
    public List<PurchaseCategory> listPurchaseCategoryByNameBatch(@RequestBody List<String> purchaseCategoryNameList) {
        return iPurchaseCategoryService.listByNameBatch(purchaseCategoryNameList);
    }

    /**
     * 导入文件模板下载
     *
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws IOException {
        iPurchaseCategoryService.importModelDownload(response);
    }

    /**
     * 导入文件
     *
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iPurchaseCategoryService.importExcel(file, fileupload);
    }


    /**
     * 导出文件
     *
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws Exception {
        iPurchaseCategoryService.exportExcel(response);
    }

    /**
     * 根据字段模糊查询
     *
     * @param param   编码或名字
     * @param enabled 是否激活(N/Y)
     * @return
     */
    @GetMapping("/queryByParam")
    public List<PurchaseCategory> queryByParam(String param, String enabled) {
        return iPurchaseCategoryService.queryByParam(param, enabled);
    }

    /**
     * 查询指定层级采购分类
     *
     * @param param   编码或名字
     * @param enabled 是否激活(N/Y)
     * @return
     */
    @GetMapping("/queryCategoryByType")
    public List<PurchaseCategory> queryCategoryByType(String param, Integer level, String enabled) {
        return iPurchaseCategoryService.queryCategoryByType(param, level, enabled);
    }

    /**
     * 获取系统采购分类级数列表
     *
     * @return
     */
    @GetMapping("/getCategoryLevel")
    public List<Integer> getCategoryLevel() {
        return iPurchaseCategoryService.getCategoryLevel();
    }

    /**
     * 查找最小级采购品类
     *
     * @param purchaseCategories
     * @return
     */
    @PostMapping("/queryMinLevelCategory")
    public List<PurchaseCategory> queryMinLevelCategory(@RequestBody List<PurchaseCategory> purchaseCategories) {
        return iPurchaseCategoryService.queryMinLevelCategory(purchaseCategories);
    }



    /**
     * 查找物料大类
     *
     * @param purchaseCategory
     * @return
     */
    @PostMapping("/queryMaxLevelCategory")
    public PurchaseCategory queryMaxLevelCategory(@RequestBody PurchaseCategory purchaseCategory) {
        return iPurchaseCategoryService.queryMaxLevelCategory(purchaseCategory);
    }

    /**
     * 根据物料大类编码查询所有的物料小类
     */
    @GetMapping("/listByCategoryCode")
    public List<PurchaseCategory> listByCategoryCode(String categoryCode){
        Assert.notNull(categoryCode,"物料大类编码不能为空。");
        Assert.isTrue(categoryCode.length()==2,"请传入正确的2位物料大类编码。");
        String str ="WHERE\tPARENT_ID\tin\t(SELECT CATEGORY_ID FROM scc_base_purchase_category \n" +
                "WHERE PARENT_ID=(SELECT CATEGORY_ID FROM scc_base_purchase_category WHERE CATEGORY_CODE='"+categoryCode+"'))";
        QueryWrapper<PurchaseCategory> wrapper = new QueryWrapper<>();
        wrapper.last(str);
        return iPurchaseCategoryService.list(wrapper);
    }

    /**
     * 查询所有允许超计划发货的物料小类
     * @return
     */
    @GetMapping("/listMinByIfBeyondDeliver")
    public List<PurchaseCategory> listMinByIfBeyondDeliver() {
        QueryWrapper<PurchaseCategory> wrapper = new QueryWrapper<>();
        wrapper.select("CATEGORY_ID,CATEGORY_CODE,CATEGORY_NAME,LEVEL");
        wrapper.eq("CEEA_IF_BEYOND_DELIVER","Y");
        wrapper.eq("LEVEL","3");
        wrapper.groupBy("CATEGORY_ID");
        return iPurchaseCategoryService.list(wrapper);
    }
    /**
     * 查询是否为允许超计划发货的物料小类
     * @return
     */
    @GetMapping("/MinByIfBeyondDeliver")
    public PurchaseCategory MinByIfBeyondDeliver(Long categoryId) {
        Assert.notNull(categoryId,"无效物料小类信息。");
        QueryWrapper<PurchaseCategory> wrapper = new QueryWrapper<>();
        wrapper.select("CATEGORY_ID,CATEGORY_CODE,CATEGORY_NAME,LEVEL");
        wrapper.eq("CEEA_IF_BEYOND_DELIVER","Y");
        wrapper.eq("LEVEL","3");
        wrapper.groupBy("CATEGORY_ID");
        wrapper.eq("CATEGORY_ID",categoryId);
        List<PurchaseCategory> list = iPurchaseCategoryService.list(wrapper);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }
    /**
     * 判断是否为用于执行到货计划物料
     * @return
     */
    @GetMapping("/MinByIfDeliverPlan")
    public PurchaseCategory MinByIfDeliverPlan(Long categoryId) {
        Assert.notNull(categoryId,"无效物料小类信息。");
        QueryWrapper<PurchaseCategory> wrapper = new QueryWrapper<>();
        wrapper.select("CATEGORY_ID,CATEGORY_CODE,CATEGORY_NAME,LEVEL,CEEA_IF_BEYOND_DELIVER");
        wrapper.eq("CEEA_IF_DELIVER_PLAN","Y");
        wrapper.eq("LEVEL","3");
        wrapper.groupBy("CATEGORY_ID");
        wrapper.eq("CATEGORY_ID",categoryId);
        List<PurchaseCategory> list = iPurchaseCategoryService.list(wrapper);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    /**
     * 判断是否为用于执行到货计划物料
     * @return
     */
    @GetMapping("/checkByIfDeliverPlan")
    public PurchaseCategory checkByIfDeliverPlan(@RequestParam("struct") String struct) {
        PurchaseCategory purchaseCategory = null;
        if (StringUtil.notEmpty(struct)) {
            QueryWrapper<PurchaseCategory> wrapper = new QueryWrapper<>();
            wrapper.eq("CEEA_IF_DELIVER_PLAN","Y");
            wrapper.eq("LEVEL","3");
            wrapper.eq("STRUCT",struct);
            List<PurchaseCategory> list = iPurchaseCategoryService.list(wrapper);
            if(CollectionUtils.isNotEmpty(list)){
                purchaseCategory = list.get(0);
            }
        }
        return purchaseCategory;
    }

    /**
     * 获取所有用于执行到货计划物料
     * @return
     */
    @GetMapping("/listMinByIfDeliverPlan")
    public List<PurchaseCategory> listMinByIfDeliverPlan() {
        //Assert.notNull(categoryId,"无效物料小类信息。");
        QueryWrapper<PurchaseCategory> wrapper = new QueryWrapper<>();
        wrapper.select("CATEGORY_ID,CATEGORY_CODE,CATEGORY_NAME,LEVEL");
        wrapper.eq("CEEA_IF_DELIVER_PLAN","Y");
        wrapper.eq("LEVEL","3");
        wrapper.groupBy("CATEGORY_ID");
        return iPurchaseCategoryService.list(wrapper);
    }

    /**
     * 检查物料的大类编码是否包含50
     * @param materialIds
     * @return
     */
    @PostMapping("/checkBigClassCodeIsContain50")
    public boolean checkBigClassCodeIsContain50(@RequestBody List<Long> materialIds){
        return iPurchaseCategoryService.checkBigClassCodeIsContain50(materialIds);
    }

    /**
     * 通过采购分类全路径名称查找采购分类
     * @param categoryFullNameList
     * @return
     */
    @PostMapping("/queryPurchaseByCategoryFullName")
    public List<PurchaseCategory> queryPurchaseByCategoryFullName(@RequestBody List<String> categoryFullNameList){
        return iPurchaseCategoryService.queryPurchaseByCategoryFullName(categoryFullNameList);
    }

    /**
     * 通过采购分类名字列表查询小类
     * @param categoryNameList
     * @return
     */
    @PostMapping("/queryPurchaseCategoryByLevelNames")
    public Map<String,PurchaseCategory> queryPurchaseCategoryByLevelNames(@RequestBody List<String> categoryNameList){
        Map<String,PurchaseCategory> purchaseCategorieMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(categoryNameList)) {
            categoryNameList = categoryNameList.stream().distinct().collect(Collectors.toList());
            List<PurchaseCategory> purchaseCategories = iPurchaseCategoryService.list(new QueryWrapper<PurchaseCategory>().eq("LEVEL", 3).in("CATEGORY_NAME", categoryNameList));
            if(CollectionUtils.isNotEmpty(purchaseCategories)){
                purchaseCategorieMap = purchaseCategories.stream().collect(Collectors.toMap(PurchaseCategory::getCategoryName, v -> v, (k1, k2) -> k1));
            }
        }
        return purchaseCategorieMap;
    }

    /**
     * 通过采购分类编码列表查询小类
     * @param categoryCodeList
     * @return
     */
    @PostMapping("/queryPurchaseCategoryByLevelCodes")
    public Map<String,PurchaseCategory> queryPurchaseCategoryByLevelCodes(@RequestBody List<String> categoryCodeList){
        Map<String,PurchaseCategory> purchaseCategorieMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(categoryCodeList)) {
            categoryCodeList = categoryCodeList.stream().distinct().collect(Collectors.toList());
            List<PurchaseCategory> purchaseCategories = iPurchaseCategoryService.list(new QueryWrapper<PurchaseCategory>().eq("LEVEL", 3).in("CATEGORY_CODE", categoryCodeList));
            if(CollectionUtils.isNotEmpty(purchaseCategories)){
                purchaseCategorieMap = purchaseCategories.stream().collect(Collectors.toMap(PurchaseCategory::getCategoryCode, v -> v, (k1, k2) -> k1));
            }
        }
        return purchaseCategorieMap;
    }

    /**
     * 通过小类ID查找全名
     * @param ids
     * @return
     */
    @PostMapping("/queryCategoryFullNameByLevelIds")
    public Map<String,String> queryCategoryFullNameByLevelIds(@RequestBody List<Long> ids){
        return iPurchaseCategoryService.queryCategoryFullNameByLevelIds(ids);
    }


    @PostMapping("/queryCategoryMaxCodeByMaterialIds")
    List<MaterialMaxCategoryVO> queryCategoryMaxCodeByMaterialIds(@RequestBody Collection<Long> materialIds) {
        if(CollectionUtils.isEmpty(materialIds)){
            return Collections.emptyList();
        }
        return iPurchaseCategoryService.queryCategoryMaxCodeByMaterialIds(materialIds);
    }


    /**
     * 根据id获取品类信息
     * @param purchaseCategoryIds
     * @return
     */
    @PostMapping("/listCategoryByIds")
    public List<PurchaseCategory> listCategoryByIds(@RequestBody List<Long> purchaseCategoryIds){
        if(purchaseCategoryIds.isEmpty()){
            return new ArrayList<>();
        }
        return iPurchaseCategoryService.listByIds(purchaseCategoryIds);
    }


}
