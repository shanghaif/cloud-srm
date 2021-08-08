package com.midea.cloud.srm.sup.invite.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.invite.entity.InviteVendor;
import com.midea.cloud.srm.sup.invite.service.InviteVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.io.IOException;


/**
 * <pre>
 *  邀请供应商 前端控制器
 * </pre>
 *
 * @author dengbin1@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 30, 2021 9:52:55 AM
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/invite/inviteVendor")
public class InviteVendorController extends BaseController {

    @Autowired
    private InviteVendorService inviteVendorService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public InviteVendor get(Long id) {
        Assert.notNull(id, "id不能为空");
        return inviteVendorService.getById(id);
    }

    /**
     * 新增
     *
     * @param inviteVendor
     */
    @PostMapping("/add")
    public void add(@RequestBody InviteVendor inviteVendor) {
        inviteVendorService.addNewData(inviteVendor);
    }

    /**
     * 批量新增或者修改
     *
     * @param inviteVendorList
     */
    @PostMapping("/batchSaveOrUpdate")
    public void batchSaveOrUpdate(@RequestBody List<InviteVendor> inviteVendorList) throws IOException {
        inviteVendorService.batchSaveOrUpdate(inviteVendorList);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        inviteVendorService.removeById(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @GetMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException {
        inviteVendorService.batchDeleted(ids);
    }

    /**
     * 修改
     *
     * @param inviteVendor
     */
    @PostMapping("/modify")
    public void modify(@RequestBody InviteVendor inviteVendor) {
        inviteVendorService.updateById(inviteVendor);
    }

    /**
     * 分页查询
     *
     * @param inviteVendor
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<InviteVendor> listPage(@RequestBody InviteVendor inviteVendor) {
        return inviteVendorService.listPage(inviteVendor);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<InviteVendor> listAll() {
        return inviteVendorService.list();
    }

    /***
     * 发布
     * @param inviteVendor
     */
    @PostMapping("/publish")
    public void publish(@RequestBody InviteVendor inviteVendor) {
        inviteVendorService.publishInviteVendor(inviteVendor);
    }

    /***
     * 更新邀请供应商注册状态
     */
    @PostMapping("/updateInviteStatus")
    public void updateInviteStatus() {
        inviteVendorService.updateInviteStatus();
    }
}
