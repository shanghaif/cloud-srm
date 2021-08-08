package com.midea.cloud.srm.base.dept.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.base.dept.service.IDeptService;
import com.midea.cloud.srm.model.base.dept.dto.CompaniesDto;
import com.midea.cloud.srm.model.base.dept.entity.Dept;
import com.midea.cloud.srm.model.common.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
*  <pre>
 *  部门表(隆基部门同步) 前端控制器
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-01 14:07:03
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {

    @Autowired
    private IDeptService iDeptService;

    /**
     * 查询公司的结构树
     * @param company SRM公司ID
     * @param orgId   业务实体ID
     * @return
     */
    @GetMapping("/deptTree")
    public List<Dept> deptTree(String company,Long orgId) {
        return iDeptService.deptTree(company,orgId);
    }

    /**
    * 新增
    * @param dept
    */
    @PostMapping("/addDept")
    public void add(@RequestBody Dept dept) {
        String deptid = String.valueOf(IdGenrator.generate());
        dept.setDeptid(deptid);
        dept.setLastUpdateDate(new Date());
        iDeptService.save(dept);
    }

    /**
     * 根据公司ID获取所有的部门信息
     * @return
     */
    @GetMapping("/listDeptByCompanyId")
    public List<Dept> listDeptByCompanyId(String organizationId) {
        Assert.notNull(organizationId, "公司ID不能为空");
        Dept dept = new Dept();
        dept.setCompany(organizationId);
        return iDeptService.list(new QueryWrapper<>(dept));
    }

    /**
     * 获取
     * @param deptid
     */
    @GetMapping("/getDept")
    public Dept get(String deptid) {
        Assert.notNull(deptid, "id不能为空");
        return iDeptService.getById(deptid);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/deleteDept")
    public void delete(String id) {
        Assert.notNull(id, "id不能为空");
        iDeptService.removeById(id);
    }

    /**
    * 修改
    * @param dept
    */
    @PostMapping("/modifyDept")
    public void modify(@RequestBody Dept dept) {
        iDeptService.updateById(dept);
    }

    /**
    * 分页查询
    * @param dept
    * @return
    */
    @PostMapping("/listDeptPage")
    public PageInfo<Dept> listPage(@RequestBody Dept dept) {
        PageUtil.startPage(dept.getPageNum(), dept.getPageSize());
        QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>(dept);
        return new PageInfo<Dept>(iDeptService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listDeptAll")
    public List<Dept> listAll() { 
        return iDeptService.list();
    }

    /**
     * 查询所有根据公司进行去重
     * @return
     */
    @PostMapping("/listAllByCompany")
    public List<CompaniesDto> listAllByCompany() {
        return iDeptService.listAllByCompany();
    }

    /**
     * 获取祖先部门
     * @param deptid
     */
    @GetMapping("/queryGrandParentDept")
    public Dept queryGrandParentDept(@RequestParam("deptid") String deptid) {
        Assert.notNull(deptid, "id不能为空");
        Dept grandParent = null;
        try {
            Dept parent = iDeptService.getOne(Wrappers.lambdaQuery(Dept.class).eq(Dept::getDeptid, deptid));
            grandParent = iDeptService.getOne(Wrappers.lambdaQuery(Dept.class).eq(Dept::getDeptid, parent.getPartDeptidChn()));
            grandParent = iDeptService.getOne(Wrappers.lambdaQuery(Dept.class).eq(Dept::getDeptid, grandParent.getPartDeptidChn()));
        }catch (Exception e){
            return null;
        }

        return grandParent;
    }

    /**
     * 根据部门名称获取部门信息
     * @param descrList
     * @return 公司+部门  - > 部门信息
     */
    @PostMapping("/queryDeptByDescrList")
    public Map<String,Dept> queryDeptByDescrList(@RequestBody List<String> descrList){
        Map<String,Dept> deptMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(descrList)){
            descrList = descrList.stream().distinct().collect(Collectors.toList());
            List<Dept> descr = iDeptService.list(new QueryWrapper<Dept>().in("DESCR", descrList));
            if(CollectionUtils.isNotEmpty(descr)){
                deptMap = descr.stream().filter(dept -> StringUtil.notEmpty(dept.getCompanyDescr()))
                        .collect(Collectors.toMap(k -> k.getCompanyDescr() + k.getDescr(), v -> v, (k1, k2) -> k1));
            }
        }
        return deptMap;
    }
}
