package com.midea.cloud.flow.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.flow.workflow.mapper.TemplateInstanceMapper;
import com.midea.cloud.flow.workflow.service.ITemplateInstanceService;
import com.midea.cloud.srm.model.flow.process.entity.TemplateInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  流程实例表 服务实现类
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
@Service
public class TemplateInstanceServiceImpl extends ServiceImpl<TemplateInstanceMapper, TemplateInstance>
        implements ITemplateInstanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateInstanceServiceImpl.class);

    @Override
    public List<TemplateInstance> queryTemplateInstanceByParam(TemplateInstance templateInstance) {
        List<TemplateInstance> templateInstanceList = new ArrayList<>();
        try{
            templateInstanceList = getBaseMapper().getTemplateInstanceByParam(templateInstance);
        }catch (Exception e){
            LOGGER.error("根据条件获取流程实例信息时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getCode(), ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return templateInstanceList;
    }

}
