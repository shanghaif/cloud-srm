package com.midea.cloud.srm.logistics.soap.tms.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.logistics.soap.tms.service.ITmsPortService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.soap.tms.entity.TmsPort;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;

import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *  tms港口表(tms系统的数据) 前端控制器
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 14:24:26
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/logistics/tms-port")
public class TmsPortController extends BaseController {

    @Autowired
    private ITmsPortService iTmsPortService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public TmsPort get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iTmsPortService.getById(id);
    }

    /**
     * 新增
     *
     * @param tmsPort
     */
    @PostMapping("/add")
    public void add(@RequestBody TmsPort tmsPort) {
        Long id = IdGenrator.generate();
        tmsPort.setPortId(id);
        iTmsPortService.save(tmsPort);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iTmsPortService.removeById(id);
    }

    /**
     * 修改
     *
     * @param tmsPort
     */
    @PostMapping("/modify")
    public void modify(@RequestBody TmsPort tmsPort) {
        iTmsPortService.updateById(tmsPort);
    }

    /**
     * 分页查询
     *
     * @param tmsPort
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<TmsPort> listPage(@RequestBody TmsPort tmsPort) {
        PageUtil.startPage(tmsPort.getPageNum(), tmsPort.getPageSize());
        QueryWrapper<TmsPort> wrapper = new QueryWrapper<TmsPort>(tmsPort);
        return new PageInfo<TmsPort>(iTmsPortService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<TmsPort> listAll() {
        return iTmsPortService.list();
    }

}
