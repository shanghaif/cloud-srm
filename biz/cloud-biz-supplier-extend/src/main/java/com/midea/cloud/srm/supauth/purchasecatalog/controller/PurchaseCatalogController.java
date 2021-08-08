package com.midea.cloud.srm.supauth.purchasecatalog.controller;

import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.purchasecatalog.entity.PurchaseCatalog;
import com.midea.cloud.srm.model.supplierauth.purchasecatalog.entity.PurchaseCatalogVo;
import com.midea.cloud.srm.supauth.purchasecatalog.service.IPurchaseCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
*  <pre>
 *  采购目录 前端控制器
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
@RestController
@RequestMapping("/purchaseCataLog")
public class PurchaseCatalogController extends BaseController {

    @Autowired
    private IPurchaseCatalogService iPurchaseCatalogService;

    /**
    * 获取
    * @param catalogId
    */
    @GetMapping("/get")
    public PurchaseCatalog get(Long catalogId) {
        Assert.notNull(catalogId, "id不能为空");
        return iPurchaseCatalogService.getById(catalogId);
    }

    /**
    * 新增
    * @param purchaseCatalog
    */
    @PostMapping("/add")
    public PurchaseCatalog add(@RequestBody PurchaseCatalog purchaseCatalog) {
       return iPurchaseCatalogService.addPurchaseCatalog(purchaseCatalog);
    }
    
    /**
    * 删除
    * @param catalogId
    */
    @GetMapping("/delete")
    public void delete(Long catalogId) {
        Assert.notNull(catalogId, "id不能为空");
        iPurchaseCatalogService.removeById(catalogId);
    }

    /**
     *批量删除
     * @param catalogIds
     */
    @PostMapping("/bathDeleteByList")
    public void bathDeleteByList(@RequestBody List<Long> catalogIds) {
        Assert.notNull(catalogIds, "id不能为空");
        iPurchaseCatalogService.bathDeleteByList(catalogIds);
    }

    /**
    * 修改
    * @param purchaseCatalog
    */
    @PostMapping("/modify")
    public PurchaseCatalog modify(@RequestBody PurchaseCatalog purchaseCatalog) {
       return iPurchaseCatalogService.updatePurchaseCatalog(purchaseCatalog);
    }

    /**
     * 修改单据状态
     * @param purchaseCatalog
     */
    @PostMapping("/modifyStatus")
    public void modifyStatus(@RequestBody PurchaseCatalog purchaseCatalog) {
        iPurchaseCatalogService.modifyStatus(purchaseCatalog);
    }

    /**
     * 保存和更新单据信息
     * @param purchaseCatalog
     */
    @PostMapping("/saveOrUpdateCatalog")
    public PurchaseCatalog saveOrUpdateCatalog(@RequestBody PurchaseCatalog purchaseCatalog) {
        iPurchaseCatalogService.CommonCheck(purchaseCatalog);
      return   iPurchaseCatalogService.saveOrUpdateCatalog(purchaseCatalog);
    }

    /**
     * 批量保存保存和更新单据信息
     * @param purchaseCatalogs
     */
    @PostMapping("/bathSaveOrUpdateCatalog")
    public List<PurchaseCatalog> bathSaveOrUpdateCatalog(@RequestBody List<PurchaseCatalog> purchaseCatalogs) {
        return   iPurchaseCatalogService.bathSaveOrUpdateCatalog(purchaseCatalogs);
    }


    /**
    * 分页查询
    * @param purchaseCatalog
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<PurchaseCatalog> listPage(@RequestBody PurchaseCatalog purchaseCatalog) {
        PageUtil.startPage(purchaseCatalog.getPageNum(), purchaseCatalog.getPageSize());
        QueryWrapper<PurchaseCatalog> wrapper = new QueryWrapper<PurchaseCatalog>(purchaseCatalog);
        return new PageInfo<PurchaseCatalog>(iPurchaseCatalogService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<PurchaseCatalog> listAll() { 
        return iPurchaseCatalogService.list();
    }

    /**
     * 分页查询
     * @param purchaseCatalog
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<PurchaseCatalog> listPageByParam(@RequestBody PurchaseCatalog purchaseCatalog) {
        PageUtil.startPage(purchaseCatalog.getPageNum(), purchaseCatalog.getPageSize());
        return iPurchaseCatalogService.listPageByParam(purchaseCatalog);
    }

    /**
     * 获取采购目录多语言标题
     * @return
     */
    @PostMapping("/purchaseCatalogTitle")
    public Map<String,String> purchaseCatalogTitle() {
        return iPurchaseCatalogService.purchaseCatalogTitle();
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestBody PurchaseCatalogVo purchaseCatalogVo, HttpServletResponse response,HttpServletRequest request) throws Exception {
        // 获取导出的数据
        List<List<Object>> dataList = iPurchaseCatalogService.queryExportData(purchaseCatalogVo);
        // 标题
        List<String> head = iPurchaseCatalogService.getMultilingualHeader(purchaseCatalogVo);
        // 文件名
        String fileName = purchaseCatalogVo.getFileName();
        // 获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        // 导出文件
        EasyExcelUtil.writeSimpleBySheet(outputStream,dataList,head);
    }

}
