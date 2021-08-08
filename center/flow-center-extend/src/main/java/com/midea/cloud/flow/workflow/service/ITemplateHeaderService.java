package com.midea.cloud.flow.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.flow.process.dto.ProcessTemplateDTO;
import com.midea.cloud.srm.model.flow.process.dto.TemplateHeaderDTO;
import com.midea.cloud.srm.model.flow.process.entity.TemplateHeader;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;

import java.util.List;

/**
*  <pre>
 *  工作流模板头表 服务类
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
public interface ITemplateHeaderService extends IService<TemplateHeader> {

    /**
     * Description 跟条件获取fdTemplateId
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.17
     * @throws
     **/
    String getFdTemplateIdByParam(TemplateHeader templateHeader);

    /**
     * Description 保存流程模板信息
     * @Param 操作成功/操作失败
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     * @throws
     **/
    BaseResult<String> saveProcessTemplate(ProcessTemplateDTO processTemplate);

    /**
     * Description 修改流程模板信息(操作成功时,返回空)
     * @Param 操作成功/操作失败
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     * @throws
     **/
    BaseResult<String> updateProcessTemplate(ProcessTemplateDTO processTemplate) throws BaseException;

    /**
     * Description 根据条件获取流程头、行相关信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.23
     * @throws
     **/
    TemplateHeaderDTO queryProcessTemplateByParam(TemplateHeaderDTO TemplateHeader);

    /**
     * Description 根据条件获取流程头、行相关信息(不需要TemplateName)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.23
     * @throws
     **/
    TemplateHeaderDTO queryProcessTemplateNotTemplateNameByParam(TemplateHeaderDTO TemplateHeader);

    /**
     * Description 根据条件分页获取流程头信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.24
     * @throws
     **/
    List<TemplateHeader> queryTemplateHeaderPageByParam(TemplateHeader templateHeader);

    /**
     * Description 是否有效设置
     * @Param templateHeadId 流程头表ID,enableFlag是否有效
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.22
     * @throws Exception
     **/
    BaseResult<String> updateEnableFlag(Long templateHeadId, String enableFlag)  throws BaseException;
}
