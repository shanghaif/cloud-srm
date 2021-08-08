package com.midea.cloud.srm.supauth.review.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.review.entity.FinanceJournal;
import com.midea.cloud.srm.supauth.review.service.IFinanceJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
*  <pre>
 *  资质审查财务信息日志表 前端控制器
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 17:02:03
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/review/financeJournal")
public class FinanceJournalController extends BaseController {

    @Autowired
    private IFinanceJournalService iFinanceJournalService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public FinanceJournal get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iFinanceJournalService.getById(id);
    }

    /**
    * 新增
    * @param financeJournal
    */
    @PostMapping("/add")
    public void add(FinanceJournal financeJournal) {
        Long id = IdGenrator.generate();
        financeJournal.setFinanceJournalId(id);
        iFinanceJournalService.save(financeJournal);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iFinanceJournalService.removeById(id);
    }

    /**
    * 修改
    * @param financeJournal
    */
    @PostMapping("/modify")
    public void modify(FinanceJournal financeJournal) {
        iFinanceJournalService.updateById(financeJournal);
    }

    /**
    * 分页查询
    * @param financeJournal
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<FinanceJournal> listPage(FinanceJournal financeJournal) {
        PageUtil.startPage(financeJournal.getPageNum(), financeJournal.getPageSize());
        QueryWrapper<FinanceJournal> wrapper = new QueryWrapper<FinanceJournal>(financeJournal);
        return new PageInfo<FinanceJournal>(iFinanceJournalService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<FinanceJournal> listAll() { 
        return iFinanceJournalService.list();
    }

    /**
     * 根据reviewFormId,vendorId获取财务信息
     * @param reviewFormId
     * @param vendorId
     * @return
     */
    @GetMapping("/listFinanceJournal")
    public List<FinanceJournal> listFinanceJournal(Long reviewFormId, Long vendorId) {
        return iFinanceJournalService.listFinanceJournal(reviewFormId, vendorId);
    }
}
