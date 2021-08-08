package com.midea.cloud.srm.supcooperate.order.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.PoLineSettlementErp;
import com.midea.cloud.srm.supcooperate.order.service.IPoLineSettlementErpService;
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
 *  修改日期: 2020-11-02 15:33:00
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/supco/po-line-settlement-erp")
public class PoLineSettlementErpController extends BaseController {

    @Autowired
    private IPoLineSettlementErpService iPoLineSettlementErpService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public PoLineSettlementErp get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iPoLineSettlementErpService.getById(id);
    }

    /**
    * 新增
    * @param poLineSettlementErp
    */
    @PostMapping("/add")
    public void add(@RequestBody PoLineSettlementErp poLineSettlementErp) {
        Long id = IdGenrator.generate();
        poLineSettlementErp.setPoLineSettlementErpId(id);
        iPoLineSettlementErpService.save(poLineSettlementErp);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iPoLineSettlementErpService.removeById(id);
    }

    /**
    * 修改
    * @param poLineSettlementErp
    */
    @PostMapping("/modify")
    public void modify(@RequestBody PoLineSettlementErp poLineSettlementErp) {
        iPoLineSettlementErpService.updateById(poLineSettlementErp);
    }

    /**
    * 分页查询
    * @param poLineSettlementErp
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<PoLineSettlementErp> listPage(@RequestBody PoLineSettlementErp poLineSettlementErp) {
        PageUtil.startPage(poLineSettlementErp.getPageNum(), poLineSettlementErp.getPageSize());
        QueryWrapper<PoLineSettlementErp> wrapper = new QueryWrapper<PoLineSettlementErp>(poLineSettlementErp);
        wrapper.orderByDesc("CREATION_DATE");
        return new PageInfo<PoLineSettlementErp>(iPoLineSettlementErpService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<PoLineSettlementErp> listAll() {
        return iPoLineSettlementErpService.list();
    }

}
