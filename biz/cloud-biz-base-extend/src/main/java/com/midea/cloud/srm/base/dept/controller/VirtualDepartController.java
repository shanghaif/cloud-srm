package com.midea.cloud.srm.base.dept.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.dept.service.IVirtualDepartService;
import com.midea.cloud.srm.model.base.dept.dto.DeptDto;
import com.midea.cloud.srm.model.base.dept.entity.VirtualDepart;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  虚拟组织表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-22 10:53:05
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/virtual-depart")
public class VirtualDepartController extends BaseController {

    @Autowired
    private IVirtualDepartService iVirtualDepartService;

    /**
     * 查询指定业务实体的组织, 分页
     * @param deptDto
     * @return
     */
    @PostMapping("/pageDept")
    public PageInfo<DeptDto> pageDept(@RequestBody DeptDto deptDto) {
        return iVirtualDepartService.pageDept(deptDto);
    }

    /**
     * 查询指定业务实体的组织, 分页
     * @param deptDto
     * @return
     */
    @PostMapping("/getAll")
    public List<DeptDto> getAll(@RequestBody DeptDto deptDto) {
        return iVirtualDepartService.getAll(deptDto);
    }

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public VirtualDepart get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iVirtualDepartService.getById(id);
    }

    /**
    * 新增
    * @param virtualDepart
    */
    @PostMapping("/add")
    public void add(@RequestBody VirtualDepart virtualDepart) {
        iVirtualDepartService.add(virtualDepart);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iVirtualDepartService.removeById(id);
    }

    /**
    * 修改
    * @param virtualDepart
    */
    @PostMapping("/modify")
    public void modify(@RequestBody VirtualDepart virtualDepart) {
        iVirtualDepartService.modify(virtualDepart);
    }

    /**
    * 分页查询
    * @param virtualDepart
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<VirtualDepart> listPage(@RequestBody VirtualDepart virtualDepart) {
        PageUtil.startPage(virtualDepart.getPageNum(), virtualDepart.getPageSize());
        QueryWrapper<VirtualDepart> wrapper = new QueryWrapper<VirtualDepart>(virtualDepart);
        return new PageInfo<VirtualDepart>(iVirtualDepartService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<VirtualDepart> listAll() { 
        return iVirtualDepartService.list();
    }


    /**
     * 根据条件查询所有
     * @return
     */
    @PostMapping("/virtualDepartListAll")
    public List<VirtualDepart> virtualDepartListAll(VirtualDepart virtualDepart)  {
        Assert.notNull(virtualDepart,"条件不能为空");
        QueryWrapper<VirtualDepart> wrapper = new QueryWrapper<>();
        //业务实体id
        /**
         * 去掉跟组织关联, 部门只跟公司关联
         */
//        wrapper.eq(virtualDepart.getOrgId()!=null,"ORG_ID",virtualDepart.getOrgId());
        //公司id
        wrapper.eq(StringUtils.isNotEmpty(virtualDepart.getCompany()),"COMPANY",virtualDepart.getCompany());
        return iVirtualDepartService.list();
    }

    /**
     * 导入文件模板下载
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws IOException {
        iVirtualDepartService.importModelDownload(response);
    }

    /**
     * 导入文件
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iVirtualDepartService.importExcel(file, fileupload);
    }
}
