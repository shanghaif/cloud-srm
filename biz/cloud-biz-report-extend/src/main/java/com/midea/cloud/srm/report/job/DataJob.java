package com.midea.cloud.srm.report.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.model.report.supplier.entity.SupplierConfig;
import com.midea.cloud.srm.report.supplier.service.ISupplierConfigService;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *   数据同步
 * </pre>
 *
 * @author kuangzm@meicloud.com
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
@Job("DataJob")
@Slf4j
public class DataJob implements ExecuteableJob {

    @Autowired
    private ApiClient ApiClient;
    
    @Autowired
	private ISupplierConfigService supplierConfigService;

    @Override
    public BaseResult executeJob(Map<String, String> params) {
        try {
        	ApiClient.send("REPORT_SYN", new HashMap(),null);
        	SupplierConfig config = supplierConfigService.getOne((Wrapper<SupplierConfig>) new QueryWrapper());
        	if (null != config) {
        		config.setSynDate( new Date());
        		supplierConfigService.updateById(config);
        	}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
        return BaseResult.build(ResultCode.SUCCESS);
    }

}
