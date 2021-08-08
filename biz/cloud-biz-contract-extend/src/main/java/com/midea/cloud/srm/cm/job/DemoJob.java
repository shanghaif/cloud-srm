package com.midea.cloud.srm.cm.job;

import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;

import java.util.Map;

/**
 * <pre>
 *  demoJob
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-12 10:44
 *  修改内容:
 * </pre>
 */
@Job("demoJob") // value用于页面配置jobName，确保整个工程唯一
public class DemoJob implements ExecuteableJob {

    @Override
    public BaseResult executeJob(Map<String, String> params) {
        System.out.println("hello, im contract job!!!!!!!!!!!!!!!!!!!!!!");
        return BaseResult.buildSuccess("hello, im contract job!!!!!!!!!!!!!!!!!!!!!!");
    }

}
