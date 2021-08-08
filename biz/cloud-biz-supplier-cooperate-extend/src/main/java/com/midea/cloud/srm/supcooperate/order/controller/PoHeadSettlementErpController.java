package com.midea.cloud.srm.supcooperate.order.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.PoHeadSettlementErp;
import com.midea.cloud.srm.supcooperate.order.service.IPoHeadSettlementErpService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

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
 *  修改日期: 2020-11-02 15:32:59
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/supco/po-head-settlement-erp")
public class PoHeadSettlementErpController extends BaseController {

    @Autowired
    private IPoHeadSettlementErpService iPoHeadSettlementErpService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public PoHeadSettlementErp get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iPoHeadSettlementErpService.getById(id);
    }

    /**
    * 新增
    * @param poHeadSettlementErp
    */
    @PostMapping("/add")
    public void add(@RequestBody PoHeadSettlementErp poHeadSettlementErp) {
        Long id = IdGenrator.generate();
        poHeadSettlementErp.setPoHeadSettlementErpId(id);
        iPoHeadSettlementErpService.save(poHeadSettlementErp);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iPoHeadSettlementErpService.removeById(id);
    }

    /**
    * 修改
    * @param poHeadSettlementErp
    */
    @PostMapping("/modify")
    public void modify(@RequestBody PoHeadSettlementErp poHeadSettlementErp) {
        iPoHeadSettlementErpService.updateById(poHeadSettlementErp);
    }

    /**
    * 分页查询
    * @param poHeadSettlementErp
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<PoHeadSettlementErp> listPage(@RequestBody PoHeadSettlementErp poHeadSettlementErp) {
        PageUtil.startPage(poHeadSettlementErp.getPageNum(), poHeadSettlementErp.getPageSize());
        QueryWrapper<PoHeadSettlementErp> wrapper = new QueryWrapper<PoHeadSettlementErp>(poHeadSettlementErp);
        return new PageInfo<PoHeadSettlementErp>(iPoHeadSettlementErpService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<PoHeadSettlementErp> listAll() {
        return iPoHeadSettlementErpService.list();
    }

}
