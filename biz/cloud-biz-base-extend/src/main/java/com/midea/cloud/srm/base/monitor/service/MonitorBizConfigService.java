package com.midea.cloud.srm.base.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.monitor.entity.MonitorBizConfig;
import com.midea.cloud.srm.model.base.monitor.enums.YesOrNo;

import java.io.IOException;
import java.util.List;

/**
* <pre>
 *  业务监控配置 服务类
 * </pre>
*
* @author huanglj50@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-5-5 9:58:31
 *  修改内容:
 * </pre>
*/
public interface MonitorBizConfigService extends IService<MonitorBizConfig>{
    /*
    批量更新或者批量新增
    */
     void batchSaveOrUpdate(List<MonitorBizConfig> monitorBizConfigList) throws IOException;

    Long addOrUpdate(MonitorBizConfig monitorBizConfig);

    /**
     * 根据ID删除
     * @param id 配置ID
     * @throws IOException
     */
    void deleteById(Long id) throws IOException;

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;
    /*
   分页查询
    */
    PageInfo<MonitorBizConfig> listPage(MonitorBizConfig monitorBizConfig);


    /**
     * 批量处理任务
     * @param ids
     * @param yesOrNo
     * @throws IOException
     */
    void batchDealTask(List<Long> ids, YesOrNo yesOrNo) throws IOException;
}
