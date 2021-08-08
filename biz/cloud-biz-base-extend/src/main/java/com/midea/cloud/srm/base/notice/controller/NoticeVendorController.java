package com.midea.cloud.srm.base.notice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.notice.service.INoticeVendorService;
import com.midea.cloud.srm.model.base.notice.entry.NoticeVendor;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 *  公告接收供应商表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/8 16:41
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/notice/noticeVendor")
public class NoticeVendorController extends BaseController {
    @Autowired
    private INoticeVendorService iNoticeVendorService;

    /**
     * 清除公告所有的供应商
     * @param noticeId
     */
    @GetMapping("/removeAllVendor")
    public void removeAllVendor(@RequestParam("noticeId") Long noticeId) {
        Assert.notNull(noticeId, "noticeId不能为空");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("NOTICE_ID",noticeId);
        iNoticeVendorService.remove(queryWrapper);
    }

    /**
     * 更新读公告状态
     * @param noticeVendor
     */
    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestBody NoticeVendor noticeVendor) {
        Assert.notNull(noticeVendor, "数据错误");
        Assert.notNull(noticeVendor.getNoticeId(), "公告ID不能为空");
        Assert.notNull(noticeVendor.getVendorId(), "采购商ID不能为空");
        Assert.notNull(noticeVendor.getVendorCode(), "采购商编码不能为空");
        Assert.notNull(noticeVendor.getVendorName(), "采购商名称不能为空");

        if(noticeVendor.getNoticeVendorId()==null){
            noticeVendor.setNoticeVendorId(IdGenrator.generate());
        }
        noticeVendor.setReadStatus("Y");

        iNoticeVendorService.saveOrUpdate(noticeVendor);
    }
}
