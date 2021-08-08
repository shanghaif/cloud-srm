package com.midea.cloud.srm.sup.invite.job;

import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;
import com.midea.cloud.srm.sup.invite.service.InviteVendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/****
 * 邀请供应商定时任务-根据供应商名称判断发出邀请的供应商是否注册，并修改状态
 */
@Job("inviteVendorJob")
@Slf4j
public class InviteVendorJob implements ExecuteableJob {

    @Autowired
    private InviteVendorService inviteVendorService;

    @Override
    public BaseResult executeJob(Map<String, String> params) {
        log.info("邀请供应商注册检查定时任务执行开始");
        try {
            inviteVendorService.updateInviteStatus();
            log.info("邀请供应商注册检查定时任务执行结束");
        } catch (Exception e) {
            log.error("邀请供应商注册检查定时任务执行异常：{}", e.getMessage());
            return BaseResult.build(ResultCode.OPERATION_FAILED, e.getMessage());
        }
        return BaseResult.buildSuccess("执行成功！");
    }
}
