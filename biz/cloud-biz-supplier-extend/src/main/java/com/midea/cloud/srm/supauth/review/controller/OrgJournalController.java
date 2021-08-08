package com.midea.cloud.srm.supauth.review.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgJournal;
import com.midea.cloud.srm.supauth.review.service.IOrgJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  合作ou日志表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-08 11:08:06
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/review/orgJournal")
public class OrgJournalController extends BaseController {

    @Autowired
    private IOrgJournalService iOrgJournalService;

    /**
     * 删除
     * @param orgJournalId
     */
    @GetMapping("/deleteByOrgJournalId")
    public void deleteByOrgJournalId(@RequestParam Long orgJournalId) {
        iOrgJournalService.removeById(orgJournalId);
    }
 
}
