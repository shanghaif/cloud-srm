package com.midea.cloud.srm.base.workbench.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.workbench.service.CeeaIWorkbenchService;
import com.midea.cloud.srm.model.flow.query.dto.WorkbenchMyTaskDTO;
import com.midea.cloud.srm.model.flow.vo.WorkbenchMyTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 * 修改后版本:
 * 修改人:
 * 修改日期: 2020-9-22 15:28
 * 修改内容:
 * </pre>
 */
@RestController
@RequestMapping(value = "/workbench")
public class CeeaWorkbenchController {

    @Autowired
    private CeeaIWorkbenchService iWorkbenchService;

    /*** 查询我的待办 ***/
    @PostMapping(value = "task/ceeaFindMyRunningProcess")
    public PageInfo<WorkbenchMyTaskVo> ceeaFindMyRunningProcess(@RequestBody WorkbenchMyTaskDTO workbenchMyTaskDTO) throws Exception {
        if (workbenchMyTaskDTO == null) {
            workbenchMyTaskDTO = new WorkbenchMyTaskDTO();
        }
        if ("".equals(workbenchMyTaskDTO.getCreateName())) {
            workbenchMyTaskDTO.setCreateName(null);
        }
        Integer pageNum = workbenchMyTaskDTO.getPageNum();
        Integer pageSize = workbenchMyTaskDTO.getPageSize();
        workbenchMyTaskDTO.setPageNum(null);// 设置成null防止对后面查询的影响
        workbenchMyTaskDTO.setPageSize(null);// 设置成null防止对后面查询的影响
        return PageUtil.pagingByFullData(pageNum, pageSize, this.iWorkbenchService.ceeaFindMyRunningProcess(workbenchMyTaskDTO));
    }

    /*** 查询我的已办 ***/
    @PostMapping(value = "task/ceeaFindMyWorkedProcess")
    public PageInfo<WorkbenchMyTaskVo> ceeaFindMyWorkedProcess(@RequestBody WorkbenchMyTaskDTO workbenchMyTaskDTO) throws Exception {
        if (workbenchMyTaskDTO == null) {
            workbenchMyTaskDTO = new WorkbenchMyTaskDTO();
        }

        Integer pageNum = workbenchMyTaskDTO.getPageNum();
        Integer pageSize = workbenchMyTaskDTO.getPageSize();
        workbenchMyTaskDTO.setPageNum(null);// 设置成null防止对后面查询的影响
        workbenchMyTaskDTO.setPageSize(null);// 设置成null防止对后面查询的影响
        return PageUtil.pagingByFullData(pageNum, pageSize, this.iWorkbenchService.ceeaFindMyWorkedProcess(workbenchMyTaskDTO));
    }

    /*** 查询我启动 ***/
    @PostMapping(value = "task/ceeaFindMyStartProcess")
    public PageInfo<WorkbenchMyTaskVo> ceeaFindMyStartProcess(@RequestBody WorkbenchMyTaskDTO workbenchMyTaskDTO) throws Exception {
        if (workbenchMyTaskDTO == null) {
            workbenchMyTaskDTO = new WorkbenchMyTaskDTO();
        }

        Integer pageNum = workbenchMyTaskDTO.getPageNum();
        Integer pageSize = workbenchMyTaskDTO.getPageSize();
        workbenchMyTaskDTO.setPageNum(null);// 设置成null防止对后面查询的影响
        workbenchMyTaskDTO.setPageSize(null);// 设置成null防止对后面查询的影响
        return PageUtil.pagingByFullData(pageNum, pageSize, this.iWorkbenchService.ceeaFindMyStartProcess(workbenchMyTaskDTO));
    }
}
