package com.midea.cloud.srm.logistics.expense.service;

import com.midea.cloud.srm.model.logistics.expense.entity.ExpenseItem;
import com.midea.cloud.srm.model.logistics.expense.entity.Port;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
*  <pre>
 *  港口信息维护表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 11:31:42
 *  修改内容:
 * </pre>
*/
public interface IPortService extends IService<Port> {

    /**
     * 条件查询
     * @param port
     * @return
     */
    List<Port> listPageByParam(Port port);

    /**
     * 保存前非空校验
     * @param port
     */
    void checkNotEmptyBeforeSave(Port port);

    /**
     * 数据删除前校验 是否都是拟定状态
     * @param portId
     */
    void checkPortsByIdBeforeDelete(Long portId);

    /**
     * 生效前校验
     * @param portId
     */
    void checkPortsByIdsBeforeEffective(Long portId);

    /**
     * 失效前校验
     * @param portId
     */
    void checkPortsByIdsBeforeInEffective(Long portId);

    /**
     * 保存港口数据(单条)
     * @param port
     */
    void savePort(Port port);

    /**
     * 保存港口数据(批量)
     * @param ports
     */
    void savePorts(List<Port> ports);

    /**
     * 更新港口状态
     * @param portId
     * @param status
     */
    void updatePortStatus(Long portId, String status);

    /**
     * 批量更新港口状态
     * @param portIds
     * @param status
     */
    void updatePortsStatus(List<Long> portIds, String status);

    /**
     * 获取港口数据
     * @param portNameList
     * @return
     */
    List<Port> listPortByNameBatch(List<String> portNameList);
}
