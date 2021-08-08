package com.midea.cloud.srm.sup.info.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.change.entity.OrgCategoryChange;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategoryImportDro;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategoryQueryDTO;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategorySaveDTO;
import com.midea.cloud.srm.model.supplier.info.dto.VendorDto;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.sup.change.service.IOrgCategoryChangeService;
import com.midea.cloud.srm.sup.info.service.IOrgCategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  组织与品类 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-13 09:13:34
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/info/orgCategory")
public class OrgCategoryController extends BaseController {

    @Autowired
    private IOrgCategoryService iOrgCategoryService;

    @Autowired
    private IOrgCategoryChangeService iOrgCategoryChangeService;

    @Autowired
    private BaseClient baseClient;

    /**
     * 根据品类查找供应商
     * @param categoryIds
     * @return
     */
    @PostMapping("/queryCompanyByBusinessModeCode")
    public List<VendorDto> queryCompanyByBusinessModeCode(@RequestBody List<Long> categoryIds){
        return iOrgCategoryService.queryCompanyByBusinessModeCode(categoryIds);
    }

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public OrgCategory get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrgCategoryService.getById(id);
    }

    /**
    * 新增
    * @param orgCategory
    */
    @PostMapping("/addOrgCategory")
    public void addOrgCategory(@RequestBody OrgCategory orgCategory) {
        Long id = IdGenrator.generate();
        orgCategory.setOrgCategoryId(id);
        iOrgCategoryService.save(orgCategory);
    }

    /**
    * 删除
    * @param orgCategoryId
    */
    @GetMapping("/delete")
    public void delete(Long orgCategoryId) {
        Assert.notNull(orgCategoryId, "orgCategoryId不能为空");
        iOrgCategoryService.delete(orgCategoryId);
    }

    /**
    * 修改
    * @param orgCategory
    */
    @PostMapping("/modify")
    public void modify(@RequestBody OrgCategory orgCategory) {
        iOrgCategoryService.updateById(orgCategory);
    }

    /**
    * 分页查询
    * @param orgCategory
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<OrgCategory> listPage(@RequestBody OrgCategory orgCategory) {
        PageUtil.startPage(orgCategory.getPageNum(), orgCategory.getPageSize());
        QueryWrapper<OrgCategory> wrapper = new QueryWrapper<OrgCategory>(orgCategory);
        return new PageInfo<OrgCategory>(iOrgCategoryService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<OrgCategory> listAll() {
        return iOrgCategoryService.list();
    }

    /**
     * 根据公司ID查询组织与品类关系
     * @param companyId
     * @return
     */
    @GetMapping("/listOrgCategoryByCompanyId")
    public List<OrgCategory> listOrgCategoryByCompanyId(Long companyId) {
        return iOrgCategoryService.listOrgCategoryByCompanyId(companyId);
    }

    /**
     * 根据条件获取组织品类关系
     * @param orgCategory
     * @return
     */
    @PostMapping("/listOrgCategoryByParam")
    public List<OrgCategory> listOrgCategoryByParam(@RequestBody OrgCategory orgCategory){
        return iOrgCategoryService.listOrgCategoryByParam(orgCategory);
    }

    @PostMapping({"/supplierTree"})
    public List<Organization> supplierTree(@RequestBody Organization organization) {
        return iOrgCategoryService.supplierTree(organization);
    }

    /**
     * 根据服务状态和供应商Id查询组织与品类
     * @param serviceStatus
     * @param companyId
     * @return
     */
    @GetMapping("/listOrgCategoryByServiceStatusAndCompanyId")
    public List<OrgCategory> listOrgCategoryByServiceStatusAndCompanyId(Long companyId, String...serviceStatus) {
        return iOrgCategoryService.listOrgCategoryByServiceStatusAndCompanyId(companyId, serviceStatus);
    }

    /**
     * 根据品类ID和组织ID和公司ID获取组织与品类
     * @param categoryId
     * @param orgId
     * @param companyId
     * @return
     */
    @GetMapping("/getByCategoryIdAndOrgIdAndCompanyId")
    public OrgCategory getByCategoryIdAndOrgIdAndCompanyId(Long categoryId, Long orgId, Long companyId) {
        return iOrgCategoryService.getByCategoryIdAndOrgIdAndCompanyId(categoryId, orgId, companyId);
    }

    /**
     * 根据品类ID和公司ID获取组织与品类的品类服务状态
     * @param categoryId
     * @param companyId
     * @return
     */
    @GetMapping("/getByCategoryIdAndCompanyId")
    public List<OrgCategory> getByCategoryIdAndCompanyId(Long categoryId, Long companyId) {
        Assert.notNull(categoryId,"品类信息不能为空");
        Assert.notNull(companyId,"公司信息不能为空");
        return iOrgCategoryService.getByCategoryIdAndCompanyId(categoryId, companyId);
    }

    /**
     * 获取所有组织与品类的品类服务状态
     * @return
     */
    @GetMapping("/getByCategoryAll")
    public List<OrgCategory> getByCategoryAll(){
        return iOrgCategoryService.getByCategoryAll();
    }

    /**
     * 根据条件筛选供应商组织品类关系
     * @param orgCategory
     * @return
     */
    @PostMapping("/getOrgCategoryByOrgCategory")
    public List<OrgCategory> getOrgCategoryByOrgCategory(@RequestBody OrgCategory orgCategory){
        return iOrgCategoryService.list(new QueryWrapper<>(orgCategory));
    }

    /**
     * 根据条件筛选供应商组织品类关系(为了校验)
     * @param orgCategoryQueryDTO
     * @return
     */
    @PostMapping("/getOrgCategoryForCheck")
    public List<OrgCategory> getOrgCategoryForCheck(@RequestBody OrgCategoryQueryDTO orgCategoryQueryDTO){
        return iOrgCategoryService.listForCheck(orgCategoryQueryDTO);
    }

    /**
     * 更新组织与品类服务状态
     * @param orgCategory
     */
    @PostMapping("/updateOrgCategoryServiceStatus")
    public void updateOrgCategoryServiceStatus(@RequestBody OrgCategory orgCategory) {
        iOrgCategoryService.updateOrgCategoryServiceStatus(orgCategory);
    }

    /**
     * 是否有货源供应商(ceea)
     * @param orgCategory
     * @return
     */
    @PostMapping("/haveSupplier")
    public boolean haveSupplier(@RequestBody OrgCategory orgCategory) {
        return iOrgCategoryService.haveSupplier(orgCategory);
    }

    /**
     * 分页条件查询合作品类关系(根据物料大中小类查询 )
     * @param orgCategoryQueryDTO add by chensl26
     * @return
     */
    @PostMapping("/listPageOrgCategoryByParam")
    public PageInfo<OrgCategory> listPageOrgCategoryByParam(@RequestBody OrgCategoryQueryDTO orgCategoryQueryDTO) {
        return iOrgCategoryService.listPageOrgCategoryByParam(orgCategoryQueryDTO);
    }

    /**
     * 分页查询状态变更记录
     * @param orgCategoryQueryDTO  add by chensl26
     * @return
     */
    @PostMapping("/listPageChangeByParam")
    public PageInfo<OrgCategoryChange> listPageChangeByParam(@RequestBody OrgCategoryQueryDTO orgCategoryQueryDTO) {
        return iOrgCategoryChangeService.listPageChangeByParam(orgCategoryQueryDTO);
    }

    /**
     * 汇总合作品类关系
     * @param orgCategorySaveDTO    add by chensl26
     */
    @PostMapping("/collectOrgCategory")
    public void collectOrgCategory(@RequestBody OrgCategorySaveDTO orgCategorySaveDTO) {
        iOrgCategoryService.collectOrgCategory(orgCategorySaveDTO);
    }

    /**
     * 供应商模板导入
     * @param response
     * @throws IOException
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws IOException {
        // OrgCategoryImportDro
        String fileName = "供应商品类关系导入模板";
        ArrayList<OrgCategoryImportDro> orgCategoryImportDros = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream,fileName,orgCategoryImportDros,OrgCategoryImportDro.class);
    }

    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iOrgCategoryService.importExcel(file, fileupload);
    }

    /**
     * 根据品类id智能查询供应商（绩效项目智能添加供应商时调用）
     * @param categoryIds
     * @return
     */
    @PostMapping("/listCompanyInfosByCategoryIds")
    public List<CompanyInfo> listCompanyInfosByCategoryIds(@RequestBody List<Long> categoryIds) {
        if (CollectionUtils.isEmpty(categoryIds)) {
            return new ArrayList<>();
        }
        return iOrgCategoryService.listCompanyInfosByCategoryIds(categoryIds);
    }


}
