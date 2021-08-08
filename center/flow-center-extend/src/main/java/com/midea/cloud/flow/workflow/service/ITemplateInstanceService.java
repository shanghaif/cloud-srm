package com.midea.cloud.flow.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.flow.process.entity.TemplateInstance;

import java.util.List;

/**
*  <pre>
 *  流程实例表 服务类
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-17 14:05:14
 *  修改内容:
 * </pre>
*/
public interface ITemplateInstanceService extends IService<TemplateInstance> {
    /**
     * Description 根据条件获取流程实例信息（根据时间倒序）
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.17
     * @throws
     **/
    List<TemplateInstance> queryTemplateInstanceByParam(TemplateInstance templateInstance);

}
