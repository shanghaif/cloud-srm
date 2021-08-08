package com.midea.cloud.srm.feign.log;

import com.midea.cloud.srm.model.log.biz.entity.BizOperateLogInfo;
import com.midea.cloud.srm.model.log.trace.dto.UserTraceInfoDto;
import com.midea.cloud.srm.model.log.useroperation.entity.LogDocument;
import com.midea.cloud.srm.model.log.useroperation.entity.UserOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <pre>
 *  日志中心模块 内部调用Feign接口
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-11 17:19
 *  修改内容:
 * </pre>
 */
@FeignClient("log-center")
public interface LogClient {

    // 用户登录日志记录功能 [log] - >>>>>

	//转到rbac模块记录
//    /**
//     * 保存用户登录痕迹
//     *
//     * @param userTraceInfoDto
//     */
//    @PostMapping("/traceinfo/save")
//    void save(@RequestBody UserTraceInfoDto userTraceInfoDto);
//
//    /**
//     * 更新用户登录痕迹
//     *
//     * @param userTraceInfoDto
//     */
//    @PostMapping("/traceinfo/update")
//    void update(@RequestBody UserTraceInfoDto userTraceInfoDto);

	//转到每个模块下自行写日志
//    /**
//     * 新增用户操作日志
//     *
//     * @param userOperation
//     */
//    @PostMapping("/useroperation/save")
//    void save(@RequestBody UserOperation userOperation);
//
//    // 用户登录日志记录功能 [log] - <<<<<
//
//    /**
//     * 业务单据操作日志，用于别的系统新增业务实体时调用
//     *
//     * @param logInfo
//     */
//    @PostMapping("/businessInfoLog/inner/save")
//    void saveBusinessLogFromInnerSystem(@RequestBody BizOperateLogInfo logInfo);
//
//    @PostMapping("/es/saveLog")
//    void saveLogWithEs(@RequestBody LogDocument logDocument);
}
