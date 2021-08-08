package com.midea.cloud.srm.inq.quote.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.inq.quote.service.IQuoteLadderPriceService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteLadderPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  报价--物料阶梯价表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-24 17:21:00
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/quote/quoteLadderPrice")
public class QuoteLadderPriceController extends BaseController {

    @Autowired
    private IQuoteLadderPriceService iQuoteLadderPriceService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public QuoteLadderPrice get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iQuoteLadderPriceService.getById(id);
    }

    /**
    * 新增
    * @param quoteLadderPrice
    */
    @PostMapping("/add")
    public void add(@RequestBody QuoteLadderPrice quoteLadderPrice) {
        Long id = IdGenrator.generate();
        quoteLadderPrice.setQuoteLadderPriceId(id);
        iQuoteLadderPriceService.save(quoteLadderPrice);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iQuoteLadderPriceService.removeById(id);
    }

    /**
    * 修改
    * @param quoteLadderPrice
    */
    @PostMapping("/modify")
    public void modify(@RequestBody QuoteLadderPrice quoteLadderPrice) {
        iQuoteLadderPriceService.updateById(quoteLadderPrice);
    }

    /**
    * 分页查询
    * @param quoteLadderPrice
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<QuoteLadderPrice> listPage(@RequestBody QuoteLadderPrice quoteLadderPrice) {
        PageUtil.startPage(quoteLadderPrice.getPageNum(), quoteLadderPrice.getPageSize());
        QueryWrapper<QuoteLadderPrice> wrapper = new QueryWrapper<QuoteLadderPrice>(quoteLadderPrice);
        return new PageInfo<QuoteLadderPrice>(iQuoteLadderPriceService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<QuoteLadderPrice> listAll() { 
        return iQuoteLadderPriceService.list();
    }
 
}
