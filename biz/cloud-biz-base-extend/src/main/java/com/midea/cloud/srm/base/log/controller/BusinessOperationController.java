package com.midea.cloud.srm.base.log.controller;

import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IPUtil;
import com.midea.cloud.srm.feign.log.LogClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.log.biz.dto.BizOperateLogInfoDto;
import com.midea.cloud.srm.model.log.biz.entity.BizOperateLogInfo;
import com.midea.cloud.srm.model.log.biz.holder.BizDocumentInfoHolder;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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
 *  修改日期: 2020/8/6 16:34
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/businessInfoLog")
public class BusinessOperationController extends BaseController {
    static Map<String, String> apiSystemMap;

    static {
        apiSystemMap = new HashMap<>();
        apiSystemMap.put("api-inq", "cloud-biz-inquiry");
        apiSystemMap.put("api-bid", "cloud-biz-bidding");
        apiSystemMap.put("api-flow", "flow-center");
        apiSystemMap.put("api-file", "file-center");
        apiSystemMap.put("api-sup-ce", "cloud-biz-supplier-cooperate");
        apiSystemMap.put("api-sup-auth", "cloud-biz-supplier-auth");
        apiSystemMap.put("api-sup", "cloud-biz-supplier");
        apiSystemMap.put("api-base", "cloud-biz-base");
        apiSystemMap.put("api-rbac", "rbac-center");
        apiSystemMap.put("api-oauth", "oauth-center");
    }

  //转到每个模块下自行写日志
//    @Autowired
//    private LogClient logClient;
//
//    @PostMapping("/save")
//    public void save(@Validated @RequestBody BizOperateLogInfoDto dto, BindingResult result) {
//        if (result.hasErrors()) {
//            throw new BaseException(ResultCode.METHOD_ARGUMENT_NOT_VALID, result.getFieldError().getDefaultMessage());
//        }
//        final LocalDateTime now = LocalDateTime.now();
//        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
//        BizOperateLogInfo logInfo = BizDocumentInfoHolder.get();
//        BeanUtils.copyProperties(dto, logInfo);
//        logInfo.setOperateTime(now);
//        logInfo.setUsername(loginAppUser.getUsername());
//        logInfo.setNickname(loginAppUser.getNickname());
//        logInfo.setUserType(loginAppUser.getUserType());
//        logInfo.setOperateIp(IPUtil.getRemoteIpAddr(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()));
//        String model = apiSystemMap.getOrDefault(dto.getModel(),dto.getModel());
//        logInfo.setModel(model);
//        logClient.saveBusinessLogFromInnerSystem(logInfo);
//
//    }
}
