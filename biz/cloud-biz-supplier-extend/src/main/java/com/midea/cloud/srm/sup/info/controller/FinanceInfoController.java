package com.midea.cloud.srm.sup.info.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.entity.FinanceInfo;
import com.midea.cloud.srm.sup.info.service.IFinanceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  财务信息 前端控制器
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-11 09:10:55
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/info/financeInfo")
public class FinanceInfoController extends BaseController {

    @Autowired
    private IFinanceInfoService iFinanceInfoService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public FinanceInfo get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iFinanceInfoService.getById(id);
    }

    /**
    * 新增
    * @param financeInfo
    */
    @PostMapping("/addFinanceInfo")
    public void addFinanceInfo(@RequestBody FinanceInfo financeInfo) {
        Long id = IdGenrator.generate();
        financeInfo.setFinanceInfoId(id);
        iFinanceInfoService.save(financeInfo);
    }
    
    /**
    * 删除
    * @param financeInfoId
    */
    @GetMapping("/delete")
    public void delete(Long financeInfoId) {
        Assert.notNull(financeInfoId, "financeInfoId不能为空");
        iFinanceInfoService.removeById(financeInfoId);
    }

    /**
    * 修改
    * @param financeInfo
    */
    @PostMapping("/modify")
    public void modify(FinanceInfo financeInfo) {
        iFinanceInfoService.updateById(financeInfo);
    }

    /**
    * 分页查询
    * @param financeInfo
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<FinanceInfo> listPage(FinanceInfo financeInfo) {
        PageUtil.startPage(financeInfo.getPageNum(), financeInfo.getPageSize());
        QueryWrapper<FinanceInfo> wrapper = new QueryWrapper<FinanceInfo>(financeInfo);
        return new PageInfo<FinanceInfo>(iFinanceInfoService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<FinanceInfo> listAll() { 
        return iFinanceInfoService.list();
    }

    /**
     * 根据公司ID获取财务信息
     * @param companyId
     * @return FinanceInfo
     */
    @GetMapping("/getByCompanyId")
    public List<FinanceInfo> getFinanceInfoByCompanyId(Long companyId) {
        return iFinanceInfoService.getByCompanyId(companyId);
    }


    /**
     * 根据公司ID和合作组织ID获取财务信息
     * @param companyId
     * @param orgId
     * @return
     */
    @GetMapping("/getByCompanyIdAndOrgId")
    public FinanceInfo getByCompanyIdAndOrgId(Long companyId, Long orgId) {
        return iFinanceInfoService.getByCompanyIdAndOrgId(companyId, orgId);
    }
}
