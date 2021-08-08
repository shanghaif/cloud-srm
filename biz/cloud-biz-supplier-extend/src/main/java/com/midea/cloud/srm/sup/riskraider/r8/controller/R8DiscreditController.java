package com.midea.cloud.srm.sup.riskraider.r8.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8Discredit;
import com.midea.cloud.srm.sup.riskraider.r8.service.IR8DiscreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  企业财务信息表 前端控制器
 * </pre>
 *
 * @author chenwt24@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:46:05
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/sup/r8-discredit")
public class R8DiscreditController extends BaseController {

    @Autowired
    private IR8DiscreditService iR8DiscreditService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public R8Discredit get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR8DiscreditService.getById(id);
    }

    /**
     * 新增
     *
     * @param r8Discredit
     */
    @PostMapping("/add")
    public void add(@RequestBody R8Discredit r8Discredit) {
        Long id = IdGenrator.generate();
        r8Discredit.setDiscreditId(id);
        iR8DiscreditService.save(r8Discredit);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR8DiscreditService.removeById(id);
    }

    /**
     * 修改
     *
     * @param r8Discredit
     */
    @PostMapping("/modify")
    public void modify(@RequestBody R8Discredit r8Discredit) {
        iR8DiscreditService.updateById(r8Discredit);
    }

    /**
     * 分页查询
     *
     * @param r8Discredit
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<R8Discredit> listPage(@RequestBody R8Discredit r8Discredit) {
        PageUtil.startPage(r8Discredit.getPageNum(), r8Discredit.getPageSize());
        QueryWrapper<R8Discredit> wrapper = new QueryWrapper<R8Discredit>(r8Discredit);
        return new PageInfo<R8Discredit>(iR8DiscreditService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<R8Discredit> listAll() {
        return iR8DiscreditService.list();
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/addDiscredit")
    public void addDiscredit(@RequestBody CompanyInfo companyInfo) {
        iR8DiscreditService.saveOrUpdateR8FromRaider(companyInfo);
    }
}
