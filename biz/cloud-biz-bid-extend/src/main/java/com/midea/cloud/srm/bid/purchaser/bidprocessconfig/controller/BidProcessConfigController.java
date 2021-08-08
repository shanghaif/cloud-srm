package com.midea.cloud.srm.bid.purchaser.bidprocessconfig.controller;

import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.bidprocessconfig.service.IBidProcessConfigService;
import com.midea.cloud.srm.model.bid.purchaser.bidprocessconfig.entity.BidProcessConfig;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  招标流程配置表 前端控制器
 * </pre>
*
* @author fengdc3@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-16 15:01:13
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/bidProcessConfig/bidProcessConfig")
public class BidProcessConfigController extends BaseController {

    @Autowired
    private IBidProcessConfigService iBidProcessConfigService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public BidProcessConfig get(@RequestParam("id") Long id) {
        Assert.notNull(id, "id不能为空");
        return iBidProcessConfigService.getById(id);
    }

    /**
    * 新增或修改
    * @param bidProcessConfig
    */
    @PostMapping("/saveOrUpdateConfig")
    public void saveOrUpdateConfig(@RequestBody BidProcessConfig bidProcessConfig) {
        iBidProcessConfigService.saveOrUpdateConfig(bidProcessConfig);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iBidProcessConfigService.removeById(id);
    }

    /**
    * 修改
    * @param bidProcessConfig
    */
    @PostMapping("/modify")
    public void modify(@RequestBody BidProcessConfig bidProcessConfig) {
        iBidProcessConfigService.updateById(bidProcessConfig);
    }

    /**
    * 分页查询
    * @param bidProcessConfig
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<BidProcessConfig> listPage(@RequestBody BidProcessConfig bidProcessConfig) {
        PageUtil.startPage(bidProcessConfig.getPageNum(), bidProcessConfig.getPageSize());
        QueryWrapper<BidProcessConfig> wrapper = new QueryWrapper<BidProcessConfig>(bidProcessConfig);
        return new PageInfo<BidProcessConfig>(iBidProcessConfigService.list(wrapper));
    }

    /**
     * 按条件查询列表
     * @param bidProcessConfig
     * @return
     */
    @PostMapping("/list")
    public List<BidProcessConfig> list(@RequestBody BidProcessConfig bidProcessConfig) {
        QueryWrapper<BidProcessConfig> wrapper = new QueryWrapper<BidProcessConfig>(bidProcessConfig);
        return new ArrayList<>(iBidProcessConfigService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<BidProcessConfig> listAll() {
        return iBidProcessConfigService.list();
    }

}
