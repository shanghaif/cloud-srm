package com.midea.cloud.srm.sup.riskraider.r2.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r2.service.IR2RiskInfoService;
import com.midea.cloud.srm.model.supplier.riskraider.r2.entity.R2RiskInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  风险扫描结果表 前端控制器
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:19:50
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/cloud-biz-supplier-extend/r2-risk-info")
public class R2RiskInfoController extends BaseController {

    @Autowired
    private IR2RiskInfoService iR2RiskInfoService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R2RiskInfo get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR2RiskInfoService.getById(id);
    }

    /**
    * 新增
    * @param r2RiskInfo
    */
    @PostMapping("/add")
    public void add(@RequestBody R2RiskInfo r2RiskInfo) {
        Long id = IdGenrator.generate();
        r2RiskInfo.setRiskInfoId(id);
        iR2RiskInfoService.save(r2RiskInfo);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR2RiskInfoService.removeById(id);
    }

    /**
    * 修改
    * @param r2RiskInfo
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R2RiskInfo r2RiskInfo) {
        iR2RiskInfoService.updateById(r2RiskInfo);
    }

    /**
    * 分页查询
    * @param r2RiskInfo
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R2RiskInfo> listPage(@RequestBody R2RiskInfo r2RiskInfo) {
        PageUtil.startPage(r2RiskInfo.getPageNum(), r2RiskInfo.getPageSize());
        QueryWrapper<R2RiskInfo> wrapper = new QueryWrapper<R2RiskInfo>(r2RiskInfo);
        return new PageInfo<R2RiskInfo>(iR2RiskInfoService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R2RiskInfo> listAll() { 
        return iR2RiskInfoService.list();
    }


    /**
     * 查询所有
     * @return
     */
    @PostMapping("/addRiskInfo")
    public void addRiskInfo(@RequestBody CompanyInfo companyInfo) {
        iR2RiskInfoService.saveOrUpdateRiskFromRaider(companyInfo);
    }

 
}
