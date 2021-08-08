package com.midea.cloud.srm.base.repush.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.config.RibbonTemplateWrapper;
import com.midea.cloud.repush.service.RepushHandlerService;
import com.midea.cloud.srm.base.repush.service.RepushService;
import com.midea.cloud.srm.model.base.repush.entity.Repush;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *  接口重推 前端控制器
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/31 18:10
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/repush")
@Slf4j
public class RepushController {

    @Autowired
    private RepushService repushService;

    /**
     * 分页条件查询
     * @param repush
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<Repush> listPageByParam(@RequestBody Repush repush) {
        PageUtil.startPage(repush.getPageNum(), repush.getPageSize());
        return new PageInfo<Repush>(repushService.listPageByParam(repush));
    }

    /**
     * 根据重推id查询数据详情
     * @param repush
     */
    @PostMapping("/getRepushInfo")
    public List<T> getRepushInfo(Repush repush) {
        Assert.notNull(repush.getRepushId(), "重推id不能为空！");
        return null;
    }

    /**
     * 页面上手动重推
     * @param repush
     */
    @PostMapping("/push")
    public void push(@RequestBody Repush repush) {
        Assert.notNull(repush.getRepushId(), "重推id不能为空！");
        repush = repushService.getById(repush.getRepushId());
        repushService.doRePush(repush);
    }

    /**
     * 页面上批量重推
     * @param repushIdList
     */
    @PostMapping("/multiplePush")
    public void multiplePush(@RequestBody List<Long> repushIdList) {
        List<Repush> repushList = repushService.listByIds(repushIdList);
        repushList.forEach(repush -> repushService.doRePush(repush));
    }
}
