package com.midea.cloud.srm.base.material.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.base.job.MaterialCache;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.dto.*;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemVo;
import com.midea.cloud.srm.model.base.organization.entity.ErpMaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
*  <pre>
 *  物料维护 服务类
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
public interface IMaterialItemService extends IService<MaterialItem> {

    List<MaterialItem> queryMaterialItemByCodes(List<String> materialCodeList);

    PageInfo<MaterialItem> listPage(MaterialItem materialItem);

    void saveOrUpdateMBatch(List<MaterialItem> materialItems);

    void saveOrUpdateM(MaterialItem materialItem);

    List<MaterialItem> listMaterialByCodeBatch(List<String> materialCodeList);

    List<MaterialItem> listMaterialByIdBatch(List<Long> materialIds);

    PageInfo<MaterialQueryDTO> listPageByParam(MaterialQueryDTO materialQueryDTO);

    PageInfo<MaterialQueryDTO> listMaterialByPurchaseCategory(MaterialQueryDTO materialQueryDTO);

    /**
     * Description 根据ID获取物料维护和物料-组织信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.09.14
     * @throws
     **/
    MaterialItem findMaterialItemById(Long materialItemId);

    /**
     * @Description 物料详情
     * @Param [materialItemId]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.25 10:04
     **/
    MaterialItem getMaterialItemById(Long materialItemId);

    /**
     * @Description 详情保存
     * @Param [materialItem]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.28 10:42
     **/
    void ceeaSaveOrUpdate(MaterialItemDto materialItem);

    /**
     * @Description 物料详情
     * @Param [id]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.28 11:10
     **/
    MaterialItemDto ceeaGet(Long id);

	PageInfo<MaterialItemVo> ceeaPurchaseMaterialListPage(MaterialQueryDTO materialQueryDTO);

	/**
	 * 手工创建订单，查询物料
	 * @param materialQueryDTO
	 * @return
	 */
	PageInfo<MaterialItemVo> listForOrder(MaterialQueryDTO materialQueryDTO);

    /**
     * @Description 分布查询采购目录
     * @Param [materialQueryDTO]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.18 10:27
     **/
    PageInfo<MaterialQueryDTO> ceeaListPurchaseCatalogPage(MaterialQueryDTO materialQueryDTO);

    /**
     * @Description 分布查询采购目录
     * @Param [materialQueryDTO]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.18 10:27
     **/
    PageInfo<MaterialQueryDTO> ceeaListPurchaseCatalogPageNew(MaterialQueryDTO materialQueryDTO);

    /**
     * @Description 单个物料加入购物车
     * @Param [materialId]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.18 14:22
     **/
    String ceeaAddToShoppingCart(MaterialQueryDTO materialQueryDTO);

    /**
     * @Description 保存页面上编辑的供应商
     * @Param [materialQueryDTO]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.22 10:01
     **/
    String ceeaUpdateSupplier(List<MaterialQueryDTO> materialQueryDTO);


    /**
     * 根据物料编码查询物料维护
     * @param materialCode
     */
    MaterialItem findMaterialItemByMaterialCode(String materialCode);

    /**
     * @Description 通知供应商
     * @Param [materialIds]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.22 11:46
     **/
    String ceeaNotifyVendor(List<Long> materialIds);

    /**
     * @Description 导入模板下载
     * @Param [response]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.23 11:17
     **/
    void importModelDownload(HttpServletResponse response) throws Exception;

    void outputModelDownload(HttpServletResponse response,  List<MaterialItemErrorDto> errorDtos) throws Exception;

    /**
     * @Description 导入文件
     * @Param [file]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.23 11:18
     **/
	void importExcel(MultipartFile file) throws Exception;

    MaterialItemDto checkImportExcel(MultipartFile file) throws Exception;
    /**
     * @Description 导出物料维护excel
     * @Param: [materialItemExportParam, response]
     * @Return: void
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/24 11:00
     */
    void exportMaterialItemExcel(ExportExcelParam<MaterialItem> materialItemExportParam, HttpServletResponse response) throws IOException;

    /**
     * @Description 查询全部采购目录
     * @Param: [materialQueryDTO]
     * @Return: java.util.List<com.midea.cloud.srm.model.base.material.dto.MaterialQueryDTO>
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/26 16:47
     */
    List<PurchaseCatalogQueryDto> listCeeaListPurchaseCatalog(List<PurchaseCatalogQueryDto> purchaseCatalogQueryDtoList);

    /**
     * 设置erp物料Id
     */
    void setErpMaterialId();

    /**
     * @Description 保存或更新物料信息
     * @param ErpMaterialItem
     */
    MaterialItem saveOrUpdateSrmMaterialItem(ErpMaterialItem ErpMaterialItem,
                                             MaterialItem materialItem,
                                             MaterialCache materialCache);

    /**
     * @Description 保存或更新物料信息(批量)
     */
    void saveOrUpdateSrmMaterialItems(List<ErpMaterialItem> erpMaterialItemList, Map<String, MaterialItem> dbMaterialItemMap, AtomicInteger successCount, AtomicInteger errorCount);

    /**
     * @description 更新保存物料库存关系
     * @param ErpMaterialItem
     */
    void saveOrUpdateSrmMaterialOrg(ErpMaterialItem ErpMaterialItem , MaterialItem materialItem ,
                                    List<Organization> invs,
                                    Map<String, Organization> invOUs,
                                    MaterialCache materialCache) throws BaseException;

    /**
     * 根据物料ID匹配可选税率
     * @param materialId
     * @return
     */
    List<PurchaseTax> queryTaxByItem(Long materialId);


    List<PurchaseTax> queryTaxByItemForOrder(Long materialId);

    Map<Long, Set<String>> queryTaxItemBatch(Collection<Long> materialIds);

    /**
     * 查询所有的目录化物料(为了采购申请转订单)
     * @return
     */
    List<MaterialItem> listAllForTranferOrder(MaterialQueryDTO materialQueryDTO);

    /**
     * 查询所有的物料(为了价格库校验)
     * @return
     */
    List<MaterialItem> listAllForImportPriceLibrary();

    /**
     * 根据物料编码列表查询物料列表
     */
    Map<String,MaterialItem> listMaterialItemsByCodes(List<String> materialCodes);

    /**
     * 根据业务实体,库存组织,和物料稽核查找物料编码和是否用于采购状态
     * @return
     */
    Map<String,String> queryItemIdUserPurchase(ItemCodeUserPurchaseDto itemCodeUserPurchaseDto);

    Map<String,MaterialItem> ListMaterialItemByCategoryCode(List<String> itemCodes);

    PageInfo<MaterialItem> listPageMaterialItemChart(MaterialItem materialItem);
}
