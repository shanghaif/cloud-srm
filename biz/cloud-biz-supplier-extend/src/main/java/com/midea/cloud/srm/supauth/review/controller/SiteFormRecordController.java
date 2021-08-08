package com.midea.cloud.srm.supauth.review.controller;

import com.midea.cloud.srm.supauth.review.service.ISiteFormRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *  认证结果 前端控制器
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/13 14:34
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/review/siteFormRecord")
public class SiteFormRecordController {

    @Autowired
    ISiteFormRecordService iSiteFormRecordService;

    /**
     * 删除 add by chensl26
     * @param siteFormRecordId
     */
    @GetMapping("/delete")
    public void deleteBySiteFormRecordId(@RequestParam Long siteFormRecordId) {
        iSiteFormRecordService.deleteBySiteFormRecordId(siteFormRecordId);
    }
}
