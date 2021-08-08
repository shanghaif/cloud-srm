package com.midea.cloud.srm.inq.inquiry.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.inq.inquiry.service.IQuoteAuthService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuoteAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *   前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-21 15:25:37
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/inquiry/quoteAuth")
public class QuoteAuthController extends BaseController {

    @Autowired
    private IQuoteAuthService iQuoteAuthService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public QuoteAuth get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iQuoteAuthService.getById(id);
    }

    /**
    * 新增
    * @param quoteAuth
    */
    @PostMapping("/add")
    public void add(@RequestBody QuoteAuth quoteAuth) {
        Long id = IdGenrator.generate();
        quoteAuth.setInquiryQuoteAuthId(id);
        iQuoteAuthService.save(quoteAuth);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iQuoteAuthService.removeById(id);
    }

    /**
    * 修改
    * @param quoteAuth
    */
    @PostMapping("/modify")
    public void modify(@RequestBody QuoteAuth quoteAuth) {
        iQuoteAuthService.updateById(quoteAuth);
    }

    /**
    * 分页查询
    * @param quoteAuth
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<QuoteAuth> listPage(@RequestBody QuoteAuth quoteAuth) {
        PageUtil.startPage(quoteAuth.getPageNum(), quoteAuth.getPageSize());
        QueryWrapper<QuoteAuth> wrapper = new QueryWrapper<QuoteAuth>(quoteAuth);
        return new PageInfo<QuoteAuth>(iQuoteAuthService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<QuoteAuth> listAll() { 
        return iQuoteAuthService.list();
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/getAuth")
    public List<QuoteAuth> getAuth(@RequestBody QuoteAuth quoteAuth) {
        QueryWrapper<QuoteAuth> wrapper = new QueryWrapper<QuoteAuth>(quoteAuth);
        return iQuoteAuthService.list(wrapper);
    }
 
}
