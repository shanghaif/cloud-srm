package com.midea.cloud.log.useroperation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.log.useroperation.service.IBusinessOperationService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.log.biz.dto.BizOperateLogInfoDto;
import com.midea.cloud.srm.model.log.biz.entity.BizOperateLogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/4 16:25
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/businessInfoLog")
public class BusinessOperationController extends BaseController {
    @Autowired
    private IBusinessOperationService service;

    @PostMapping("/inner/save")
    public void saveBusinessLogFromInnerSystem(@RequestBody BizOperateLogInfo info) {
        service.save(info);
    }

    @PostMapping("/listPage")
    public PageInfo<BizOperateLogInfo> listPage(@RequestBody BizOperateLogInfoDto dto) {
        return service.listPage(dto);
    }

    /**
     * 根据单据号获取操作用户账号
     *
     * @param documentId
     * @return
     */
    @GetMapping("/listUser/{documentId}")
    public List<String> listUser(@PathVariable String documentId) {
        return service.list(new LambdaQueryWrapper<BizOperateLogInfo>()
                .select(BizOperateLogInfo::getUsername)
                .eq(BizOperateLogInfo::getBusinessId, documentId)
                .groupBy(BizOperateLogInfo::getUsername))
                .stream().map(BizOperateLogInfo::getUsername).collect(Collectors.toList());
    }
}
