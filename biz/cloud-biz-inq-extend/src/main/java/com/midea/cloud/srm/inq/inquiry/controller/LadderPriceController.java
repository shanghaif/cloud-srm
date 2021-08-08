package com.midea.cloud.srm.inq.inquiry.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.inquiry.dto.LadderPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.inq.inquiry.service.ILadderPriceService;
import com.midea.cloud.srm.model.inq.inquiry.entity.LadderPrice;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  询价--物料阶梯价表 前端控制器
 * </pre>
*
* @author zhongbh
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:55
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/inquiry/ladderPrice")
public class LadderPriceController extends BaseController {

    @Autowired
    private ILadderPriceService iLadderPriceService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public LadderPrice get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iLadderPriceService.getById(id);
    }

    /**
    * 新增
    * @param ladderPrice
    */
    @PostMapping("/add")
    public void add(@RequestBody LadderPrice ladderPrice) {
        Long id = IdGenrator.generate();
        ladderPrice.setInquiryLadderPriceId(id);
        iLadderPriceService.save(ladderPrice);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iLadderPriceService.removeById(id);
    }

    /**
    * 修改
    * @param ladderPrice
    */
    @PostMapping("/modify")
    public void modify(@RequestBody LadderPrice ladderPrice) {
        iLadderPriceService.updateById(ladderPrice);
    }

    /**
    * 分页查询
    * @param ladderPrice
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<LadderPrice> listPage(@RequestBody LadderPrice ladderPrice) {
        PageUtil.startPage(ladderPrice.getPageNum(), ladderPrice.getPageSize());
        QueryWrapper<LadderPrice> wrapper = new QueryWrapper<LadderPrice>(ladderPrice);
        return new PageInfo<LadderPrice>(iLadderPriceService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<LadderPrice> listAll() { 
        return iLadderPriceService.list();
    }

    /**
     * 修改
     * @param ladderPrices
     */
    @PostMapping("/saveLadderPrices")
    public void modify(@RequestBody LadderPriceDto ladderPrices) {
        iLadderPriceService.savePrices(ladderPrices.getLadderPrices());
    }
}
