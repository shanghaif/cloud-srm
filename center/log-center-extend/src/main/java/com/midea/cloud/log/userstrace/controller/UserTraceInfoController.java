package com.midea.cloud.log.userstrace.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.log.userstrace.service.IUserTraceInfoService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.log.trace.dto.UserTraceInfoDto;
import com.midea.cloud.srm.model.log.trace.entity.UserTraceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  用户使用痕迹明细表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-10 09:01:19
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/traceinfo")
public class UserTraceInfoController extends BaseController {

    @Autowired
    private IUserTraceInfoService iUserTraceInfoService;

    /**
     * 保存用户登录痕迹
     * @param userTraceInfoDto 用户账号
     */
    @PostMapping("/save")
    public void save(@RequestBody UserTraceInfoDto userTraceInfoDto) {
        iUserTraceInfoService.add(userTraceInfoDto.getUsername(),userTraceInfoDto.getLogIp(),userTraceInfoDto.getUserType());
    }

    /**
     * 更新用户登录痕迹
     * @param userTraceInfoDto 用户账号
     */
    @PostMapping("/update")
    public void update(@RequestBody UserTraceInfoDto userTraceInfoDto) {
        iUserTraceInfoService.update(userTraceInfoDto.getUsername(),userTraceInfoDto.getLogIp());
    }

    /**
    * 分页查询
    * @param userTraceInfo
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<UserTraceInfo> listPage(@RequestBody UserTraceInfo userTraceInfo) {
        PageUtil.startPage(userTraceInfo.getPageNum(), userTraceInfo.getPageSize());
        QueryWrapper<UserTraceInfo> wrapper = new QueryWrapper<>(userTraceInfo);
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<>(iUserTraceInfoService.list(wrapper));
    }
 
}
