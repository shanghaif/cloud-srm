package com.midea.cloud.srm.rbac.userstrace.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.log.trace.entity.UserTrace;
import com.midea.cloud.srm.rbac.userstrace.service.IUserTraceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  用户使用痕迹表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-08 10:55:34
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/userTraces")
public class UserTraceController {

    @Autowired
    private IUserTraceService iUserTraceService;

    /**
     * 分页查询(向前端展示)
     * @param userTrace
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<UserTrace> listPage(@RequestBody UserTrace userTrace) {
        return iUserTraceService.queryUserTracesDto(userTrace);
    }
}
