package com.midea.cloud.log.job;

import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.log.useroperation.service.IUserOperationService;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;
import com.midea.cloud.srm.model.log.useroperation.dto.UserOperationDto;
import com.midea.cloud.srm.model.log.useroperation.entity.UserOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * <pre>
 *  日志数据清理任务
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-9-8 17:14
 *  修改内容:
 * </pre>
 */
@Job("logDataCleanJob")
public class LogDataCleanJob implements ExecuteableJob {

    @Value("${cloud.scc.logDataClean.maxSurDay:30}")
    private Integer maxSurDay;

    @Autowired
    IUserOperationService iUserOperationService;

    @Override
    public BaseResult executeJob(Map<String, String> params) {
        Object maxSurDay = params.get("maxSurDay");
        if (maxSurDay != null) {
            this.maxSurDay = Integer.valueOf(maxSurDay.toString());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -this.maxSurDay);
        UserOperationDto userOperationDto = new UserOperationDto();
        userOperationDto.setPageNum(1);
        userOperationDto.setPageSize(5000);
        userOperationDto.setOperationTimeEnd(calendar.getTime());
        List<UserOperation> userOperationPageInfos = null;
        while (CollectionUtils.isNotEmpty(userOperationPageInfos = iUserOperationService.listPage(userOperationDto).getList())) {
            List<Long> ids = new ArrayList<Long>();
            userOperationPageInfos.stream().forEach(userOperationPageInfo -> {
                ids.add(userOperationPageInfo.getOperationLogId());
            });
            iUserOperationService.removeByIds(ids);
        }
        return BaseResult.buildSuccess();
    }

}
