package com.midea.cloud.srm.cm.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.enums.contract.ContractStatus;
import com.midea.cloud.common.enums.contract.MilestoneStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;
import com.midea.cloud.srm.cm.contract.service.IContractHeadService;
import com.midea.cloud.srm.cm.contract.service.IPayPlanService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.cm.contract.dto.ContractHeadDTO;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.cm.contract.entity.PayPlan;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *   合同模块任务调度
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-15 16:20
 *  修改内容:
 * </pre>
 */
@Job("ContractJob")
@Slf4j
public class ContractJob implements ExecuteableJob {

    @Autowired
    IContractHeadService iContractHeadService;

    @Autowired
    IPayPlanService iPayPlanService;

    @Autowired
    RbacClient rbacClient;

    @Autowired
    BaseClient baseClient;

    @Override
    public BaseResult executeJob(Map<String, String> params) {
        List<ContractHead> contractHeads = iContractHeadService.listContractHead(new ContractHeadDTO()
                .setContractStatus(ContractStatus.ARCHIVED.name()));
        if (!CollectionUtils.isEmpty(contractHeads)) {
            contractHeads.forEach(contractHead -> {
                if (contractHead != null) {
                    List<PayPlan> payPlans = iPayPlanService.list(new QueryWrapper<>(
                            new PayPlan().setContractHeadId(contractHead.getContractHeadId())));
                    if (!CollectionUtils.isEmpty(payPlans)) {
                        payPlans.forEach(payPlan -> {
                            if (payPlan != null) {
                                if (LocalDate.now().isEqual(payPlan.getMilestoneDate())) {
                                    //里程碑提醒
                                    milestoneReminder(contractHead, payPlan);
                                    //更新里程碑状态为待处理
                                    payPlan.setMilestoneStatus(MilestoneStatus.PENDING.name());
                                    iPayPlanService.updateById(payPlan);
                                }
                            }
                        });
                    }
                }
            });
        }
        return BaseResult.buildSuccess("邮件发送里程碑提醒成功!!!!!!!!!!!!!");
    }

    private void milestoneReminder(ContractHead contractHead, PayPlan payPlan) {
        User user = rbacClient.getUserByParmForAnon(new User().setUsername(contractHead.getCreatedBy()));
        Assert.notNull(user, LocaleHandler.getLocaleMsg("无法找到该用户"));

        if (StringUtils.isNotBlank(user.getEmail())) {
            log.info("已进入里程碑提醒,开始发送邮件...............");
            String title = "合同付款计划里程碑提醒";
            String content = String.format("<p> 根据合同编号[%s]合同名称[%s]里程碑阶段[%s][%s]设定的里程碑日期[%s]已到达,请确认里程碑。</p>",
                    contractHead.getContractNo(), contractHead.getContractName(), payPlan.getMilestoneStage(), payPlan.getMilestone(), payPlan.getMilestoneDate());
            try {
                baseClient.sendEmail(user.getEmail(), title, content);
            } catch (Exception e) {
                log.error("邮件发送里程碑提醒失败，请检查邮箱配置", e);
                throw new BaseException(LocaleHandler.getLocaleMsg("邮件发送里程碑提醒失败，请检查邮箱配置"));
            }
            log.info("邮件发送里程碑提醒成功!!!!!!!!!!!!!");
        }else {
            throw new BaseException(LocaleHandler.getLocaleMsg("该用户尚未绑定邮箱"));
        }
    }

}
