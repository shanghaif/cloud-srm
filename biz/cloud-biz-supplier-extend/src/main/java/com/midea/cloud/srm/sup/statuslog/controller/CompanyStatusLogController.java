package com.midea.cloud.srm.sup.statuslog.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.statuslog.entity.CompanyStatusLog;
import com.midea.cloud.srm.sup.statuslog.service.ICompanyStatusLogService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  企业状态历史表 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-13 17:43:49
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/statusLog/companyStatusLog")
public class CompanyStatusLogController extends BaseController {

    @Autowired
    private ICompanyStatusLogService iCompanyStatusLogService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public CompanyStatusLog get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iCompanyStatusLogService.getById(id);
    }

    /**
    * 新增
    * @param companyStatusLog
    */
    @PostMapping("/add")
    public void add(@RequestBody CompanyStatusLog companyStatusLog) {
        Long id = IdGenrator.generate();
        companyStatusLog.setStatusLogId(id);
        iCompanyStatusLogService.save(companyStatusLog);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iCompanyStatusLogService.removeById(id);
    }

    /**
    * 修改
    * @param companyStatusLog
    */
    @PostMapping("/modify")
    public void modify(@RequestBody CompanyStatusLog companyStatusLog) {
        iCompanyStatusLogService.updateById(companyStatusLog);
    }

    /**
    * 分页查询
    * @param companyStatusLog
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<CompanyStatusLog> listPage(@RequestBody CompanyStatusLog companyStatusLog) {
        PageUtil.startPage(companyStatusLog.getPageNum(), companyStatusLog.getPageSize());
        QueryWrapper<CompanyStatusLog> wrapper = new QueryWrapper<CompanyStatusLog>(companyStatusLog);
        return new PageInfo<CompanyStatusLog>(iCompanyStatusLogService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<CompanyStatusLog> listAll() { 
        return iCompanyStatusLogService.list();
    }

    /**
     * 查询根据传参查询所有并且根据操作时间排序
     * @return
     */
    @PostMapping("/listAllByParam")
    public List<CompanyStatusLog> listAllByParam(@RequestBody  CompanyStatusLog companyStatusLog) {
        return iCompanyStatusLogService.listAllByParam(companyStatusLog);
    }
 
}
