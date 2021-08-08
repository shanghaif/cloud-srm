package com.midea.cloud.srm.base.traces.controller;

import com.midea.cloud.srm.base.traces.service.UserTraceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/8
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/userTrace")
public class UserTraceController {
    @Resource
    UserTraceService userTraceService;
    /**
     * 记录用户会话开始
     */
    @RequestMapping("/startRecordingSession")
    public void startRecordingSession(){
        userTraceService.startRecordingSession();
    }
}
