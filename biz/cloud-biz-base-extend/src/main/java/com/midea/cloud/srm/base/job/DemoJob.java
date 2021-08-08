package com.midea.cloud.srm.base.job;

import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;

import java.util.Map;

/**
 * <pre>
 *  demo任务示例
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-11 19:04
 *  修改内容:
 * </pre>
 */
@Job("demoJob")
public class DemoJob implements ExecuteableJob {

    @Override
    public BaseResult executeJob(Map<String, String> params) {
        return BaseResult.buildSuccess("hello world");
    }

}
