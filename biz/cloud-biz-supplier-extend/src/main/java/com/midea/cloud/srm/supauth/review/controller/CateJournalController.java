package com.midea.cloud.srm.supauth.review.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.review.entity.CateJournal;
import com.midea.cloud.srm.supauth.review.service.ICateJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  品类日志表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-08 11:11:26
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/review/cateJournal")
public class CateJournalController extends BaseController {

    @Autowired
    private ICateJournalService iCateJournalService;

    /**
     * 删除
     * @param cateJournalId
     */
    @GetMapping("/deleteByCateJournalId")
    public void deleteByCateJournalId(@RequestParam Long cateJournalId) {
        iCateJournalService.removeById(cateJournalId);
    }

}
