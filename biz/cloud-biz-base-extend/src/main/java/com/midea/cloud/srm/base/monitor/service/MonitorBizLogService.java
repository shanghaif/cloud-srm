package com.midea.cloud.srm.base.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.monitor.entity.MonitorBizLog;
import java.util.List;
import java.io.IOException;

/**
* <pre>
 *  业务监控日志 服务类
 * </pre>
*
* @author huanglj50@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-5-5 10:01:51
 *  修改内容:
 * </pre>
*/
public interface MonitorBizLogService extends IService<MonitorBizLog>{
    /*
    批量更新或者批量新增
    */
     void batchSaveOrUpdate(List<MonitorBizLog> monitorBizLogList) throws IOException;

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;
    /*
   分页查询
    */
    PageInfo<MonitorBizLog> listPage(MonitorBizLog monitorBizLog);

    void deal(Long id) throws IOException;


}
