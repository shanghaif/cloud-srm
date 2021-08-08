package com.midea.cloud.srm.base.workbench.service;

import com.midea.cloud.srm.model.flow.query.dto.WorkbenchMyTaskDTO;
import com.midea.cloud.srm.model.flow.vo.WorkbenchMyTaskVo;

import java.util.List;

/*****
 * 工作台
 */
public interface CeeaIWorkbenchService {

    /*****
     * 获取我的待办信息
     * @param workbenchMyTaskDTO
     * @return
     * @throws Exception
     */
    List<WorkbenchMyTaskVo> ceeaFindMyRunningProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO) throws Exception;

    /*****
     * 获取我的已办信息
     * @param workbenchMyTaskDTO
     * @return
     * @throws Exception
     */
    List<WorkbenchMyTaskVo> ceeaFindMyWorkedProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO) throws Exception;

    /****
     * 查询我启动信息
     * @param workbenchMyTaskDTO
     * @return
     * @throws Exception
     */
    List<WorkbenchMyTaskVo>  ceeaFindMyStartProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO) throws Exception;
}
