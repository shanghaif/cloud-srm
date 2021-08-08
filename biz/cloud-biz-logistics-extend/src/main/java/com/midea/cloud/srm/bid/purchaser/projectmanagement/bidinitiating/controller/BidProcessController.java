package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidProcessService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidProcess;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  招标流程表 前端控制器
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-31 18:58:55
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/bidInitiating/bidProcess")
public class BidProcessController extends BaseController {

    @Autowired
    private IBidProcessService iBidProcessService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public BidProcess get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBidProcessService.getById(id);
    }

    /**
     * 根据招标id获取
     *
     * @param bidingId
     */
    @GetMapping("/getByBidingId")
    public BidProcess getByBidingId(Long bidingId) {
        Assert.notNull(bidingId, "id不能为空");
        return iBidProcessService.getOne(new QueryWrapper<BidProcess>(new BidProcess().setBidingId(bidingId)));
    }

    /**
     * 新增
     *
     * @param bidProcess
     */
    @PostMapping("/add")
    public Long add(@RequestBody BidProcess bidProcess) {
        Long id = IdGenrator.generate();
        bidProcess.setProcessConfigId(id);
        iBidProcessService.save(bidProcess);
        return id;
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iBidProcessService.removeById(id);
    }

    /**
     * 修改
     *
     * @param bidProcess
     */
    @PostMapping("/modify")
    public void modify(@RequestBody BidProcess bidProcess) {
        iBidProcessService.updateByBidingId(bidProcess);
    }

    /**
     * 分页查询
     *
     * @param bidProcess
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<BidProcess> listPage(@RequestBody BidProcess bidProcess) {
        PageUtil.startPage(bidProcess.getPageNum(), bidProcess.getPageSize());
        QueryWrapper<BidProcess> wrapper = new QueryWrapper<BidProcess>(bidProcess);
        return new PageInfo<BidProcess>(iBidProcessService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<BidProcess> listAll() {
        return iBidProcessService.list();
    }

}
