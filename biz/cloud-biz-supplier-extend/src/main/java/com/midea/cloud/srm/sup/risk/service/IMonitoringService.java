package com.midea.cloud.srm.sup.risk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.risk.entity.Monitoring;
import com.midea.cloud.srm.model.supplier.risk.entity.Responses;

import java.util.List;

/**
*  <pre>
 *  供应商风险监控 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-01 13:46:12
 *  修改内容:
 * </pre>
*/
public interface IMonitoringService extends IService<Monitoring> {
    /**
     * 获取风险监控详情
     * @param riskMonitoringId
     * @return
     */
    Monitoring get(Long riskMonitoringId);

    /**
     *  新增风险监控详情
     * @param monitoring
     */
    Long saveOrUpdateMonitoring(Monitoring monitoring,String status);

    /**
     * 分页查询
     * @param monitoring
     * @return
     */
    PageInfo<Monitoring> listPage(Monitoring monitoring);

    /**
     * 提交
     * @param monitoring
     * @return
     */
    Long submit(Monitoring monitoring);

    /**
     * 审批通过
     * @param riskMonitoringId
     * @return
     */
    Long addPass(Long riskMonitoringId);

    /**
     * 关闭审批
     * @param monitoring
     * @return
     */
    Long close(Monitoring monitoring);

    /**
     * 关闭审批通过
     * @param riskMonitoringId
     * @return
     */
    Long closePass(Long riskMonitoringId);

    /**
     * 根据供应商+风险类型查询上一次的应对措施
     * @param vendorId
     * @param riskType
     * @return
     */
    List<Responses> queryResponses (Long vendorId,String riskType);
}
