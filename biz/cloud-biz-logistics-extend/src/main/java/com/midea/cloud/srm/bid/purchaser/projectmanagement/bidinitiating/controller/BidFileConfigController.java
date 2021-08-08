package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidFileMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidFileConfigService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidFileConfig;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  供方必须上传附件配置表 前端控制器
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-20 20:13:34
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/bidInitiating/bidFileConfig")
public class BidFileConfigController extends BaseController {

    @Autowired
    private IBidFileConfigService iBidFileConfigService;
    @Autowired
    private BidFileMapper bidFileMapper;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public BidFileConfig get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBidFileConfigService.getById(id);
    }

    /**
     * 新增
     *
     * @param bidFileConfig
     */
    @PostMapping("/add")
    public void add(@RequestBody BidFileConfig bidFileConfig) {
        Long id = IdGenrator.generate();
        bidFileConfig.setRequireId(id);
        iBidFileConfigService.save(bidFileConfig);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iBidFileConfigService.removeById(id);
    }

    /**
     * 修改
     *
     * @param bidFileConfig
     */
    @PostMapping("/modify")
    public void modify(@RequestBody BidFileConfig bidFileConfig) {
        iBidFileConfigService.updateById(bidFileConfig);
    }

    /**
     * 分页查询
     *
     * @param bidFileConfig
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<BidFileConfig> listPage(@RequestBody BidFileConfig bidFileConfig) {
        PageUtil.startPage(bidFileConfig.getPageNum(), bidFileConfig.getPageSize());
        QueryWrapper<BidFileConfig> wrapper = new QueryWrapper<BidFileConfig>(bidFileConfig);
        return new PageInfo<BidFileConfig>(iBidFileConfigService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<BidFileConfig> listAll(@RequestBody BidFileConfig bidFileConfig) {
        List<BidFileConfig> list = iBidFileConfigService.list(new QueryWrapper<>(bidFileConfig));
        return list;
    }

    /**
     * 查询附件内容
     *
     * @return
     */
    @GetMapping("/getFileConfigbyBidingId")
    public List<BidFileConfig> getFileConfigbyBidingId(@RequestParam Long bidingId) {
        QueryWrapper<BidFileConfig> wrapper = new QueryWrapper<BidFileConfig>();
        wrapper.eq("BIDING_ID", bidingId);
        return iBidFileConfigService.list(wrapper);
    }
}
