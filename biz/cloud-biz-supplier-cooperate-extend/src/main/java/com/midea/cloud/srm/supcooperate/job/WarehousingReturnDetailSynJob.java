package com.midea.cloud.srm.supcooperate.job;

import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetailErp;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailErpService;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/11/5 17:44
 *  修改内容:
 * </pre>
 */
@Job("WarehousingReturnDetailSynJob")
@Slf4j
public class WarehousingReturnDetailSynJob implements ExecuteableJob {

    @Autowired
    private IWarehousingReturnDetailErpService warehousingReturnDetailErpService;

    @Override
    public BaseResult executeJob(Map<String, String> params) {
        return warehousingReturnDetailErpService.transferErpToSrm();
    }
}
