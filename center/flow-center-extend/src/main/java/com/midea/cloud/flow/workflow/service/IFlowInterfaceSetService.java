package com.midea.cloud.flow.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.flow.process.entity.FlowInterfaceSet;

import java.util.Map;

/**
*  <pre>
 *  工作流接口访问配置表 服务类
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-26 19:28:59
 *  修改内容:
 * </pre>
*/
public interface IFlowInterfaceSetService extends IService<FlowInterfaceSet> {

    /**
     * Description 新增cbpm系统接口
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.26
     * @throws
     **/
    void getProcessSalt() throws Exception;
}
