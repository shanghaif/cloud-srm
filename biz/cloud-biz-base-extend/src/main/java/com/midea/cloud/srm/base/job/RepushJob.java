package com.midea.cloud.srm.base.job;

import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.config.RibbonTemplateWrapper;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;
import com.midea.cloud.repush.service.RepushHandlerService;
import com.midea.cloud.srm.base.purchase.service.impl.PurchaseTaxServiceImpl;
import com.midea.cloud.srm.base.repush.service.RepushService;
import com.midea.cloud.srm.model.base.repush.entity.Repush;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  接口重推任务
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-6 16:42
 *  修改内容:
 * </pre>
 */
@Job("repushJob")
@Slf4j
public class RepushJob implements ExecuteableJob {

    @Autowired
    RepushService repushService;

    @Autowired
    RepushHandlerService repushHandlerService;

    @Autowired
    private RibbonTemplateWrapper ribbonTemplate;

    @Override
    public BaseResult executeJob(Map<String, String> params) {
        Object pageNumObj = params.get("pageNum");
        Object pageSizeObj = params.get("pageSize");
        if (pageNumObj != null && pageSizeObj != null) {
            PageUtil.startPage(Integer.parseInt(pageNumObj.toString()), Integer.parseInt(pageSizeObj.toString()));
        } else {
            PageUtil.startPage(1, 20);
        }
        List<Repush> repushList = repushService.listEffective();
        boolean fail = false;
        for (Repush repush : repushList) {
            try {
                String baseResult = ribbonTemplate.postForObject("http://" + repush.getModule() + "/job-anon/internal/repush/push", repush, String.class);
                if (log.isDebugEnabled()) {
                    log.debug("重推接口成功: {}.{}" + repush.getCallServiceClassName(), repush.getCallServiceMethod());
                }
            } catch (Exception ex) {
                fail = true;
                if (log.isErrorEnabled()) {
                    log.error("重推接口失败: {}.{}" + repush.getCallServiceClassName(), repush.getCallServiceMethod());
                }
            }
        }
        if (fail) {
            BaseResult.build(ResultCode.OPERATION_FAILED, "部分接口重推失败");
        }
        return BaseResult.buildSuccess();
    }

}
