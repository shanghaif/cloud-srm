package com.midea.cloud.srm.base.dept.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.dept.service.ICompaniesService;
import com.midea.cloud.srm.model.base.dept.entity.Companies;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
*  <pre>
 *   前端控制器
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-07 13:59:21
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/dept/companies")
public class CompaniesController extends BaseController {

    @Autowired
    private ICompaniesService iCompaniesService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Companies get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iCompaniesService.getById(id);
    }

    /**
    * 新增
    * @param companies
    */
    @PostMapping("/add")
    public void add(@RequestBody Companies companies) {
        iCompaniesService.save(companies);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iCompaniesService.removeById(id);
    }

    /**
    * 修改
    * @param companies
    */
    @PostMapping("/modify")
    public void modify(@RequestBody Companies companies) {
        iCompaniesService.updateById(companies);
    }

    /**
    * 分页查询
    * @param companies
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Companies> listPage(@RequestBody Companies companies) {
        PageUtil.startPage(companies.getPageNum(), companies.getPageSize());
        QueryWrapper<Companies> wrapper = new QueryWrapper<Companies>(companies);
        return new PageInfo<Companies>(iCompaniesService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Companies> listAll() { 
        return iCompaniesService.list();
    }

    /**
     * 导入公司基本信息数据
     *
     * @param file
     */
    @RequestMapping("/importExcelByBasics")
    public Map<String, Object> importExcelByBasics(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iCompaniesService.importExcelByBasics(file, fileupload);
    }
    /**
     * 下载公司基本信息数据导入模板
     * @return
     */
    @PostMapping("/imporDownloadByBasics")
    public void imporDownloadByBasics(HttpServletResponse response) throws Exception{
        iCompaniesService.imporDownloadByBasics(response);
    }

    /**
     * 根据公司名称查找公司
     * @return
     */
    @PostMapping("/queryCompaniesByCompanyFullNameList")
    public Map<String,Companies> queryCompaniesByCompanyFullNameList(@RequestBody List<String> companyFullNameList){
        Map<String,Companies> companiesMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(companyFullNameList)){
            companyFullNameList = companyFullNameList.stream().distinct().collect(Collectors.toList());
            List<Companies> companiesList = iCompaniesService.list(new QueryWrapper<Companies>().in("COMPANY_FULL_NAME", companyFullNameList));
            if(CollectionUtils.isNotEmpty(companiesList)){
                companiesMap = companiesList.stream().collect(Collectors.toMap(Companies::getCompanyFullName, v -> v, (k1, k2) -> k1));
            }
        }
        return companiesMap;
    }


}
