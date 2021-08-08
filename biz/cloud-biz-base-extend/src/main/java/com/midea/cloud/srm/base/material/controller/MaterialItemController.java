package com.midea.cloud.srm.base.material.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.material.service.IMaterialItemService;
import com.midea.cloud.srm.base.material.utils.MaterialItemExportUtils;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.dto.*;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemVo;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  物料维护 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-06 15:22:48
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/material/materialItem")
public class MaterialItemController extends BaseController {

    @Autowired
    private IMaterialItemService iMaterialItemService;

    /**
     * 根据业务实体,库存组织,和物料稽核查找物料编码和是否用于采购状态
     * @return
     */
    @PostMapping("/queryItemIdUserPurchase")
    public Map<String, String> queryItemIdUserPurchase(@RequestBody ItemCodeUserPurchaseDto itemCodeUserPurchaseDto){
        return iMaterialItemService.queryItemIdUserPurchase(itemCodeUserPurchaseDto);
    }

    /**
     * 根据物料编码列表查找物料信息和公式
     * @param materialCodeList
     */
    @PostMapping("/queryMaterialItemByCodes")
    public List<MaterialItem> queryMaterialItemByCodes(@RequestBody List<String> materialCodeList){
        return iMaterialItemService.queryMaterialItemByCodes(materialCodeList);
    }

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public MaterialItem get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iMaterialItemService.getMaterialItemById(id);
    }

    /**
    * 新增
    * @param materialItem
    */
    @PostMapping("/add")
    public void add(MaterialItem materialItem) {
        Long id = IdGenrator.generate();
        materialItem.setMaterialId(id);
        iMaterialItemService.save(materialItem);
    }

    /**
    * 删除
    * @param itemId
    */
    @PostMapping("/delete")
    public void delete(Long itemId) {
        Assert.notNull(itemId, "id不能为空");
        iMaterialItemService.removeById(itemId);
    }

    /**
    * 修改
    * @param materialItem
    */
    @PostMapping("/modify")
    public void modify(@RequestBody MaterialItem materialItem) {
        iMaterialItemService.updateById(materialItem);
    }

    /**
    * 分页查询
    * @param materialItem
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<MaterialItem> listPage(@RequestBody MaterialItem materialItem) {
        return iMaterialItemService.listPage(materialItem);
    }

    /**
     * 分页查询物料（报表使用）
     * @param materialItem
     * @return
     */
    @PostMapping("/listPageMaterialItemChart")
    public PageInfo<MaterialItem> listPageMaterialItemChart(@RequestBody MaterialItem materialItem){
        return iMaterialItemService.listPageMaterialItemChart(materialItem);
    }



    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<MaterialItem> listAll() {
        return iMaterialItemService.list();
    }

    /**
     * 批量保存或更新
     */
    @PostMapping("/saveOrUpdateMBatch")
    public void saveOrUpdateMBatch(@RequestBody  List<MaterialItem> materialItems){
        iMaterialItemService.saveOrUpdateMBatch(materialItems);
    }

    /**
     * 保存物料和物料-组织信息
     */
    @PostMapping("/saveMaterialItem")
    public void saveMaterialItem(@RequestBody MaterialItem materialItem){
        iMaterialItemService.saveOrUpdateM(materialItem);
    }

    /**
     * 修改物料和物料-组织信息
     */
    @PostMapping("/updateMaterialItem")
    public void updateMaterialItem(@RequestBody MaterialItem materialItem){
        iMaterialItemService.saveOrUpdateM(materialItem);
    }

    /**
     * 批量物料查选接口
     */
    @PostMapping("/listByParam")
    public List<MaterialItem> listByParam(@RequestBody MaterialItem materialItem){
        QueryWrapper<MaterialItem> wrapper = new QueryWrapper<>(materialItem);
       return  iMaterialItemService.list(wrapper);
    }

    /**
     * 条件批量查询物料接口
     */
    @PostMapping("/listMaterialByCodeBatch")
    public List<MaterialItem> listMaterialByCodeBatch(@RequestBody List<String> materialCodeList){
        return iMaterialItemService.listMaterialByCodeBatch(materialCodeList);
    }

    /**
     * 条件批量查询接口
     * @param materialIds
     * @return
     */
    @PostMapping("/listMaterialByIdBatch")
    public List<MaterialItem> listMaterialByIdBatch(@RequestBody List<Long> materialIds){
        return iMaterialItemService.listMaterialByIdBatch(materialIds);
    }

    /**
     * 条件分页查询(ceea)
     * @param materialQueryDTO
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<MaterialQueryDTO> listPageByParam(@RequestBody MaterialQueryDTO materialQueryDTO) {
        return iMaterialItemService.listPageByParam(materialQueryDTO);
    }

    /**
     * 采购物料维护列表查询
     * @param materialQueryDTO
     * @return
     */
    @PostMapping("/purchaseMaterialListPage")
    public PageInfo<MaterialItemVo> ceeaPurchaseMaterialListPage(MaterialQueryDTO materialQueryDTO){
        return iMaterialItemService.ceeaPurchaseMaterialListPage(materialQueryDTO);
    }

    /**
     * 根据采购分类父列表查找
     */
    @PostMapping("/listMaterialByPurchaseCategory")
    public PageInfo<MaterialQueryDTO> listMaterialByPurchaseCategory(@RequestBody MaterialQueryDTO materialQueryDTO){
        return iMaterialItemService.listMaterialByPurchaseCategory(materialQueryDTO);

    }

    /**
     * 条件查询有物料数据的价格
     */
    @PostMapping("/listForOrder")
    public PageInfo<MaterialItemVo> listForOrder(@RequestBody MaterialQueryDTO materialQueryDTO){
        return iMaterialItemService.listForOrder(materialQueryDTO);
    }

    /**
     * Description 根据ID获取物料维护和物料-组织信息
     * @Param materialItemId 物料维护ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.09.14
     * @throws
     **/
    @GetMapping("/findMaterialItemById")
    public MaterialItem findMaterialItemById(Long materialItemId){
        Assert.notNull(materialItemId, "id不能为空");
        return iMaterialItemService.findMaterialItemById(materialItemId);
    }

    /**
     * @Description 分页查询采购目录
     * @Param [materialQueryDTO]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.18 10:25
     **/
    @PostMapping("/ceeaListPurchaseCatalogPage")
    public PageInfo<MaterialQueryDTO> ceeaListPurchaseCatalogPage(@RequestBody MaterialQueryDTO materialQueryDTO) {
        return iMaterialItemService.ceeaListPurchaseCatalogPageNew(materialQueryDTO);
    }

   /**
    * @Description 查询全部采购目录
    * @Param: [materialQueryDTO]
    * @Return: java.util.List<com.midea.cloud.srm.model.base.material.dto.MaterialQueryDTO>
    * @Author: dengyl23@meicloud.com
    * @Date: 2020/9/26 16:46
    */
    @PostMapping("/listCeeaListPurchaseCatalog")
    public List<PurchaseCatalogQueryDto> listCeeaListPurchaseCatalog(@RequestBody List<PurchaseCatalogQueryDto> purchaseCatalogQueryDtoList){
        return iMaterialItemService.listCeeaListPurchaseCatalog(purchaseCatalogQueryDtoList);
    }

    /**
     * @Description 单个物料加入购物车
     * @Param [materialId]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.18 13:44
     **/
    @PostMapping("/ceeaAddToShoppingCart")
    public String ceeaAddToShoppingCart(@RequestBody MaterialQueryDTO materialQueryDTO) {
    	return iMaterialItemService.ceeaAddToShoppingCart(materialQueryDTO);
    }

    /**
     * @Description 保存页面上编辑的供应商
     * @Param [materialQueryDTO]
     * @return void
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.22 10:00
     **/
    @PostMapping("/ceeaUpdateSupplier")
	public String ceeaUpdateSupplier(@RequestBody List<MaterialQueryDTO> materialQueryDTO) {
    	return iMaterialItemService.ceeaUpdateSupplier(materialQueryDTO);
	}

	/**
	 * @Description 通知供应商
	 * @Param [materialIds]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.22 11:46
	 **/
	@PostMapping("/ceeaNotifyVendor")
	public String ceeaNotifyVendor(@RequestBody List<Long> materialIds) {
		return iMaterialItemService.ceeaNotifyVendor(materialIds);
	}

	/**
	 * @Description 物料详情保存
	 * @Param [materialItem]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.23 15:14
	 **/
	@PostMapping("/ceeaSaveOrUpdate")
	public void ceeaSaveOrUpdate(@RequestBody MaterialItemDto materialItem) {
		iMaterialItemService.ceeaSaveOrUpdate(materialItem);
	}

	@GetMapping("/ceeaGet")
	public MaterialItemDto ceeaGet(Long id) {
		Assert.notNull(id, "id不能为空");
		return iMaterialItemService.ceeaGet(id);
	}

	/**
	 * @Description 导入模板下载
	 * @Param [response]
	 * @return
	 * @A/material/materialItem/ceeaSaveOrUpdateuthor haiping2.li@meicloud.com
	 * @Date 2020.09.23 11:15
	 **/
	@RequestMapping("/importModelDownload")
	public void importModelDownload(HttpServletResponse response) throws Exception {
		iMaterialItemService.importModelDownload(response);
	}

    /**
     * @Description 导出错误信息模板下载
     * @Param [response]
     * @return
     * @Author wuhx29@meicloud.com
     * @Date 2020.10.23 11:15
     **/
    @RequestMapping("/outputModelDownload")
    public void outputModelDownload(HttpServletResponse response,  @RequestBody List<MaterialItemErrorDto> errorDtos) throws Exception {
        iMaterialItemService.outputModelDownload(response, errorDtos);
    }

	/**
	 * @Description 导入文件
	 * @Param [file]
	 * @return void
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.23 11:16
	 **/
	@RequestMapping("/importExcel")
	public void importExcel(@RequestParam("file") MultipartFile file) throws Exception {
		iMaterialItemService.importExcel(file);
	}

    /**
     * @Description 导入文件文件之前要校验
     * @Param [file]
     * @return int 1表示校验通过，0表示校验不通过
     * @Author wuhx29@meicloud.com
     * @Date 2020.10.23 11:16
     **/
    @RequestMapping("/checkImportExcel")
    public MaterialItemDto checkImportExcel(@RequestParam("file") MultipartFile file) throws Exception {
        return iMaterialItemService.checkImportExcel(file);
    }

    @GetMapping("/findMaterialItemByMaterialCode")
    public MaterialItem findMaterialItemByMaterialCode(@RequestParam("materialCode") String materialCode){
        Assert.notNull(materialCode, "物料编码不能为空");
        return iMaterialItemService.findMaterialItemByMaterialCode(materialCode);
    }

    /**
     * @Description 导出物料维护excel表头标题
     * @Param: []
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/24 11:25
     */
    @PostMapping("/exportMaterialItemTitle")
    public Map<String,String> exportMaterialItemTitle(){
        return MaterialItemExportUtils.getMaterItemTitles();
    }

    /**
     * @Description  导出物料维护excel
     * @Param: [materialItemExportParam, response]
     * @Return: void
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/24 11:25
     */
    @PostMapping("/exportMaterialItemExcel")
    public BaseResult exportMaterialItemExcel(@RequestBody ExportExcelParam<MaterialItem> materialItemExportParam, HttpServletResponse response) throws IOException {
        try {
            iMaterialItemService.exportMaterialItemExcel(materialItemExportParam,response);
            return BaseResult.buildSuccess();
        } catch (Exception e) {
            return BaseResult.build(ResultCode.UNKNOWN_ERROR,e.getMessage());
        }
    }

    /**
     * 设置物料的erp物料Id（导数据用，忽略）
     */
    @PostMapping("/setErpMaterialId")
    public void setErpMaterialId(){
        iMaterialItemService.setErpMaterialId();
    }

    /**
     * 根据物料ID匹配可选税率
     * @param materialId
     * @return
     */
    @GetMapping("/queryTaxByItem")
    public List<PurchaseTax> queryTaxByItem(Long materialId){
        return iMaterialItemService.queryTaxByItem(materialId);
    }

    /**
     * 根据物料ID匹配可选税率
     * 创建订单使用
     * @return
     */
    @GetMapping("/queryTaxByItemForOrder")
    public List<PurchaseTax> queryTaxByItemForOrder(@RequestParam("materialId") Long materialId){
        return iMaterialItemService.queryTaxByItemForOrder(materialId);
    }

    /**
     * 根据物料id批量查询物料
     * @param materialIds
     * @return
     */
    @PostMapping("/listMaterialItemsByIds")
    public List<MaterialItem> listMaterialItemsByIds(@RequestBody List<Long> materialIds){
        if(CollectionUtils.isEmpty(materialIds)){
            return Collections.EMPTY_LIST;
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.in("MATERIAL_ID",materialIds);
            return iMaterialItemService.list(queryWrapper);
        }
    }

    @PostMapping("/listAllForTranferOrder")
    public List<MaterialItem> listAllForTranferOrder(@RequestBody MaterialQueryDTO materialQueryDTO){
        return iMaterialItemService.listAllForTranferOrder(materialQueryDTO);
    }

    @GetMapping("/listAllForImportPriceLibrary")
    public List<MaterialItem> listAllForImportPriceLibrary(){
        return iMaterialItemService.listAllForImportPriceLibrary();
    }

    /**
     * 测试批量导出
     */
    @PostMapping("/testExport")
    public void testExport(HttpServletResponse response) throws IOException {
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "测试分页导出");
        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
        /**
         * 分批查询进行导出
         * 示例: 现在要导出30000条数据, 模拟每次查询出10000条数据进行导出(防止全部查出内存溢出), 循环3次
         */
        for(int i = 0 ; i < 3 ; i++){
            WriteSheet writeSheet = EasyExcel.writerSheet(i, "sheet" + i).head(MaterialItemModelDto.class).
                    registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
            // 每次查询10000条数据
            List<MaterialItemModelDto> materialItemModelDtos = new ArrayList<>();
            PageUtil.startPage(1+i,20000);
            List<MaterialItem> materialItems = iMaterialItemService.list();
            if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(materialItems)){
                materialItems.forEach(materialItem -> {
                    MaterialItemModelDto materialItemModelDto = new MaterialItemModelDto();
                    BeanUtils.copyProperties(materialItem,materialItemModelDto);
                    materialItemModelDtos.add(materialItemModelDto);
                });
            }
            excelWriter.write(materialItemModelDtos,writeSheet);
        }
        excelWriter.finish();
    }

}
