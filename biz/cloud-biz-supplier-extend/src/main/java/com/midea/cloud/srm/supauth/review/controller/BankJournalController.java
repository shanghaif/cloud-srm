package com.midea.cloud.srm.supauth.review.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.review.entity.BankJournal;
import com.midea.cloud.srm.supauth.review.service.IBankJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  银行信息日志表 前端控制器
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 17:02:37
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/review/bankJournal")
public class BankJournalController extends BaseController {

    @Autowired
    private IBankJournalService iBankJournalService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public BankJournal get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBankJournalService.getById(id);
    }

    /**
    * 新增
    * @param bankJournal
    */
    @PostMapping("/add")
    public void add(BankJournal bankJournal) {
        Long id = IdGenrator.generate();
        bankJournal.setBankJournalId(id);
        iBankJournalService.save(bankJournal);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iBankJournalService.removeById(id);
    }

    /**
    * 修改
    * @param bankJournal
    */
    @PostMapping("/modify")
    public void modify(BankJournal bankJournal) {
        iBankJournalService.updateById(bankJournal);
    }

    /**
    * 分页查询
    * @param bankJournal
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<BankJournal> listPage(BankJournal bankJournal) {
        PageUtil.startPage(bankJournal.getPageNum(), bankJournal.getPageSize());
        QueryWrapper<BankJournal> wrapper = new QueryWrapper<BankJournal>(bankJournal);
        return new PageInfo<BankJournal>(iBankJournalService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<BankJournal> listAll() { 
        return iBankJournalService.list();
    }

    /**
     * 根据reviewFormId, vendorId获取银行信息
     * @param reviewFormId
     * @param vendorId
     * @return
     */
    @GetMapping("/listBankJournal")
    public List<BankJournal> listBankJournal(Long reviewFormId, Long vendorId) {
        return iBankJournalService.listBankJournal(reviewFormId, vendorId);
    }

    /**
     * 删除
     * @param bankJournalId
     */
    @GetMapping("/deleteByBankJournalId")
    public void deleteByBankJournalId(@RequestParam Long bankJournalId) {
        iBankJournalService.removeById(bankJournalId);
    }
}
