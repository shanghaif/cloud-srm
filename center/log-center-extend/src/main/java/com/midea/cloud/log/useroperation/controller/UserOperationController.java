package com.midea.cloud.log.useroperation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.log.useroperation.service.IUserOperationService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.log.useroperation.dto.UserOperationDto;
import com.midea.cloud.srm.model.log.useroperation.entity.UserOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 *  <pre>
 *  用户操作日志表 前端控制器
 * </pre>
 *
 * @author wangpr@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-11 13:53:23
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/useroperation")
public class UserOperationController extends BaseController {

    @Autowired
    private IUserOperationService iUserOperationService;

    /**
     * 获取
     * @param id
     */
    @GetMapping("/get")
    public UserOperation get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iUserOperationService.getById(id);
    }

    /**
     * 新增
     * @param userOperation
     */
    @PostMapping("/save")
    public void save(@RequestBody UserOperation userOperation) {
        /**
         * 值记录错误的信息
         */
        if (null != userOperation && "error".equals(userOperation.getResultStatus())) {
            Long id = IdGenrator.generate();
            userOperation.setOperationLogId(id);
            userOperation.setCreatedBy(userOperation.getUsername());
            userOperation.setCreatedByIp(userOperation.getRequestIp());
            userOperation.setCreationDate(new Date());
            userOperation.setCreatedId(0L);
            userOperation.setLastUpdateDate(new Date());
            iUserOperationService.save(userOperation);
        }
    }

    /**
     * 删除
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iUserOperationService.removeById(id);
    }

    /**
     * 修改
     * @param userOperation
     */
    @PostMapping("/modify")
    public void modify(@RequestBody UserOperation userOperation) {
        iUserOperationService.updateById(userOperation);
    }

    /**
     * 分页查询
     * @param userOperation
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<UserOperation> listPage(@RequestBody UserOperationDto userOperation) {
        return iUserOperationService.listPage(userOperation);
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/listAll")
    public List<UserOperation> listAll() {
        return iUserOperationService.list();
    }

}
