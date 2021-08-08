package com.midea.cloud.srm.sup.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategoryQueryDTO;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategorySaveDTO;
import com.midea.cloud.srm.model.supplier.info.dto.VendorDto;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  组织与品类 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 16:21:46
 *  修改内容:
 * </pre>
*/
public interface IOrgCategoryService extends IService<OrgCategory> {

    /**
     * 根据品类查找供应商
     * @param categoryIds
     * @return
     */
    List<VendorDto> queryCompanyByBusinessModeCode(List<Long> categoryIds);

    void saveOrUpdateOrgCategory(OrgCategory orgCategory, Long companyId);


    List<OrgCategory> getByCompanyId(Long companyId);

    void removeByCompanyId(Long companyId);

    /**
     * 根据公司ID查询组织与品类关系
     * @param companyId
     * @return
     */
    List<OrgCategory> listOrgCategoryByCompanyId(Long companyId);

    /**
     * 根据服务状态和供应商Id查询组织与品类
     * @param serviceStatus
     * @param companyId
     * @return
     */
    List<OrgCategory> listOrgCategoryByServiceStatusAndCompanyId(Long companyId, String... serviceStatus);

    /**
     * 根据品类ID和组织ID获取组织与品类
     * @param categoryId
     * @param orgId
     * @param companyId
     * @return
     */
    OrgCategory getByCategoryIdAndOrgIdAndCompanyId(Long categoryId, Long orgId, Long companyId);
    /**
     * 根据品类ID和公司ID获取组织与品类的品类服务状态
     * @param categoryId
     * @param companyId
     * @return
     */
    List<OrgCategory> getByCategoryIdAndCompanyId(Long categoryId, Long companyId);

    /**
     * 获取所有组织与品类的品类服务状态
     * @return
     */
    List<OrgCategory> getByCategoryAll();

    /**
     * 更新组织与品类服务状态
     * @param orgCategory
     */
    void updateOrgCategoryServiceStatus(OrgCategory orgCategory);

    /**
     * 是否有货源供应商(ceea)
     * @param orgCategory
     * @return
     */
    boolean haveSupplier(OrgCategory orgCategory);

    PageInfo<OrgCategory> listPageOrgCategoryByParam(OrgCategoryQueryDTO orgCategoryQueryDTO);

    void collectOrgCategory(OrgCategorySaveDTO orgCategorySaveDTO);

    /**
     * 查找供应商独家组织+品类
     * @param vendorId
     * @return
     */
    List<OrgCategory> querySingleSourceList(Long vendorId);

    void delete(Long orgCategoryId);

    /**
     * 供应商品类关系导入
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception;

    List<OrgCategory> listForCheck(OrgCategoryQueryDTO orgCategoryQueryDTO);

    /**
     * 获取供应商组织品类关系
     * @param orgCategory
     * @return
     */
    List<OrgCategory> listOrgCategoryByParam(OrgCategory orgCategory);

    /**
     * 供应商 拥有的组织权限
     * @param organization
     * @return
     */
    List<Organization> supplierTree(@RequestBody Organization organization);

    /**
     * 根据品类id智能查询供应商（绩效项目智能添加供应商时调用）
     * @param categoryIds
     * @return
     */
    List<CompanyInfo> listCompanyInfosByCategoryIds(List<Long> categoryIds);
}
