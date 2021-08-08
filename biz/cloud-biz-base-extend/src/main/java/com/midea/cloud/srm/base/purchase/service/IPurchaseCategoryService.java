package com.midea.cloud.srm.base.purchase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.material.vo.MaterialMaxCategoryVO;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  采购分类 服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-06 15:38:44
 *  修改内容:
 * </pre>
*/
public interface IPurchaseCategoryService extends IService<PurchaseCategory> {

    /**
     * 根据中类编码查找小类
     * @param middleCode
     * @return
     */
    List<PurchaseCategory> queryPurchaseCategoryByMiddleCode(String middleCode);

    /**
     * 查找父品类(根据产品服务层级)
     * @return
     */
    List<PurchaseCategory> listParent();

    /**
     *批量添加采购分类
     * @param purchaseCategories
     */
    void batchSaveOrUpdate(List<PurchaseCategory> purchaseCategories);

    /**
     * 分页条件查询
     * @param purchaseCategory
     * @return
     */
    PageInfo<PurchaseCategory> listPageByParm(PurchaseCategory purchaseCategory);

    /**
     * 根据父品类ID查询子品类(1级时,ID传入-1)
     * @param categoryId
     * @return
     */
    List<PurchaseCategory> listChildren(Long categoryId);

    List<PurchaseCategory> listByLevel(PurchaseCategory purchaseCategory);

    List<PurchaseCategory> listByNameBatch(List<String> purchaseCategoryNameList);

    /**
     * 导入模板下载
     * @param response
     */
    void importModelDownload(HttpServletResponse response) throws IOException;

    /**
     * 导入excel
     * @param file
     * @return

     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    /**
     * 导出excel
     * @throws Exception
     */
    void exportExcel(HttpServletResponse response) throws Exception;

    /**
     * 根据字段模糊查询
     * @param param
     * @param enabled
     * @return
     */
    List<PurchaseCategory> queryByParam(String param,String enabled);

    /**
     * 根据品类全路径Code和品类全路径Name新增或更新
     * @param categoryFullCode,categoryFullName
     * @return
     * @update xiexh12@meicloud.com
     */
    void SaveOrUpdateByCategoryFullCode(String categoryFullCode, String categoryFullName);

    /**
     * 查询指定层级采购分类
     * @param param
     * @param level
     * @param enabled
     * @return
     */
    List<PurchaseCategory> queryCategoryByType(String param, Integer level , String enabled);

    /**
     * 设置品类全路径名称
     * @param purchaseCategory
     */
    void setCategoryFullName(PurchaseCategory purchaseCategory);

    /**
     * 获取系统采购分类级数列表
     * @return
     */
    List<Integer> getCategoryLevel();

    List<PurchaseCategory> queryMinLevelCategory(List<PurchaseCategory> purchaseCategories);

    PurchaseCategory queryMaxLevelCategory(PurchaseCategory purchaseCategory);

    /**
     * 通过物料小类
     * @param materialIds
     * @return
     */
    boolean checkBigClassCodeIsContain50(List<Long> materialIds);

    /**
     * 通过物料id查询对应的大类
     * @param materialIds
     * @return
     */
    List<MaterialMaxCategoryVO> queryCategoryMaxCodeByMaterialIds(Collection<Long> materialIds);

    /**
     * 通过采购分类名查找采购分类
     * @param categoryFullNameList
     * @return
     */
    List<PurchaseCategory> queryPurchaseByCategoryFullName(List<String> categoryFullNameList);

    PurchaseCategory getByParm(PurchaseCategory purchaseCategory);

    Map<String,String> queryCategoryFullNameByLevelIds(List<Long> ids);

    List<PurchaseCategory> listLogisticsCategoryByLevel(PurchaseCategory purchaseCategory);
}
