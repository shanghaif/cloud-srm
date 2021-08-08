package com.midea.cloud.srm.sup.info.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.entity.OrgInfo;
import com.midea.cloud.srm.sup.info.service.IOrgInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  合作组织信息 前端控制器
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
@RequestMapping("/info/orgInfo")
public class OrgInfoController extends BaseController {

    @Autowired
    private IOrgInfoService iOrgInfoService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public OrgInfo get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrgInfoService.getById(id);
    }

    /**
    * 新增
    * @param orgInfo
    */
    @PostMapping("/addOrgInfo")
    public void addOrgInfo(@RequestBody OrgInfo orgInfo) {
        Long id = IdGenrator.generate();
        orgInfo.setOrgInfoId(id);
        iOrgInfoService.save(orgInfo);
    }
    
    /**
    * 删除
    * @param orgInfoId
    */
    @GetMapping("/delete")
    public void delete(Long orgInfoId) {
        Assert.notNull(orgInfoId, "orgInfoId不能为空");
        iOrgInfoService.removeById(orgInfoId);
    }

    /**
    * 修改
    * @param orgInfo
    */
    @PostMapping("/modify")
    public void modify(@RequestBody OrgInfo orgInfo) {
        iOrgInfoService.updateById(orgInfo);
    }

    /**
    * 分页查询
    * @param orgInfo
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<OrgInfo> listPage(@RequestBody OrgInfo orgInfo) {
        PageUtil.startPage(orgInfo.getPageNum(), orgInfo.getPageSize());
        QueryWrapper<OrgInfo> wrapper = new QueryWrapper<OrgInfo>(orgInfo);
        return new PageInfo<OrgInfo>(iOrgInfoService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<OrgInfo> listAll() { 
        return iOrgInfoService.list();
    }

    /**
     * 根据公司ID查询合作组织
     * @param companyId
     * @return
     */
    @GetMapping("/listOrgInfoByCompanyId")
    public List<OrgInfo> listOrgInfoByCompanyId(Long companyId) {
        return iOrgInfoService.listOrgInfoByCompanyId(companyId);
    }

    /**
     * 根据服务状态和供应商ID查询合作组织
     * @param serviceStatus
     * @param companyId
     * @return
     */
    @GetMapping("/listOrgInfoByServiceStatusAndCompanyId")
    public List<OrgInfo> listOrgInfoByServiceStatusAndCompanyId(Long companyId, String...serviceStatus) {
        return iOrgInfoService.listOrgInfoByServiceStatusAndCompanyId(companyId, serviceStatus);
    }

    /**
     * 根据组织ID和公司ID获取合作组织
     * @param orgId
     * @param companyId
     * @return
     */
    @GetMapping("/getOrgInfoByOrgIdAndCompanyId")
    public OrgInfo getOrgInfoByOrgIdAndCompanyId(Long orgId, Long companyId) {
        return iOrgInfoService.getOrgInfoByOrgIdAndCompanyId(orgId, companyId);
    }

    /**
     * 更新组织服务状态
     * @param orgInfo
     */
    @PostMapping("/updateOrgInfoServiceStatus")
    public void updateOrgInfoServiceStatus(@RequestBody OrgInfo orgInfo) {
        iOrgInfoService.updateOrgInfoServiceStatus(orgInfo);
    }
}
