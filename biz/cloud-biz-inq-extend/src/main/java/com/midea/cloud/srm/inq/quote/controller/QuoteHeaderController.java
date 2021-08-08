package com.midea.cloud.srm.inq.quote.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.inq.quote.service.IQuoteHeaderService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuoteHeaderDto;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteCancelDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteRollbackDTO;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  报价-报价信息头表 前端控制器
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
@RequestMapping("/quote/quoteHeader")
public class QuoteHeaderController extends BaseController {

    @Autowired
    private IQuoteHeaderService iQuoteHeaderService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public QuoteHeader get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iQuoteHeaderService.getById(id);
    }

    /**
    * 新增
    * @param quoteHeader
    */
    @PostMapping("/add")
    public void add(@RequestBody QuoteHeader quoteHeader) {
        Long id = IdGenrator.generate();
        quoteHeader.setQuoteId(id);
        iQuoteHeaderService.save(quoteHeader);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iQuoteHeaderService.removeById(id);
    }

    /**
    * 修改
    * @param quoteHeader
    */
    @PostMapping("/modify")
    public void modify(@RequestBody QuoteHeader quoteHeader) {
        iQuoteHeaderService.updateById(quoteHeader);
    }

    /**
    * 分页查询
    * @param quoteHeader
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<QuoteHeader> listPage(@RequestBody QuoteHeader quoteHeader) {
        PageUtil.startPage(quoteHeader.getPageNum(), quoteHeader.getPageSize());
        QueryWrapper<QuoteHeader> wrapper = new QueryWrapper<QuoteHeader>(quoteHeader);
        return new PageInfo<QuoteHeader>(iQuoteHeaderService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<QuoteHeader> listAll() { 
        return iQuoteHeaderService.list();
    }

    /**
     * 保存报价
     * @param header
     */
    @PostMapping("/saveQuote")
    public Long saveQuote(@RequestBody QuoteHeaderDto header) {
        Assert.notNull(header.getInquiryId(), "询价单id不能为空");
        return iQuoteHeaderService.saveQuote(header);
    }

     /**
     * 作废报价
     */
    @PostMapping("/cancel")
    public void cancelQuote(@RequestBody QuoteCancelDTO request) {
        Assert.notNull(request.getQuoteId(), "报价单id不能为空");
        iQuoteHeaderService.cancelQuote(request);
    }

    /**
     * 作废撤回
     */
    @PostMapping("/rollback")
    public void rollback(@RequestBody QuoteRollbackDTO request) {
        Assert.notNull(request.getQuoteId(), "报价单id不能为空");
        iQuoteHeaderService.rolllbackQuote(request);
    }


}
