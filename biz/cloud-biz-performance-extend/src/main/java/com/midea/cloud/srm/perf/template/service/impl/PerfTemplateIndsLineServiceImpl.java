package com.midea.cloud.srm.perf.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateIndsLine;
import com.midea.cloud.srm.perf.template.mapper.PerfTemplateIndsLineMapper;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateIndsLineService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  绩效模型指标信息行表 服务实现类
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-30 10:17:50
 *  修改内容:
 * </pre>
*/
@Service
public class PerfTemplateIndsLineServiceImpl extends ServiceImpl<PerfTemplateIndsLineMapper, PerfTemplateIndsLine>
        implements IPerfTemplateIndsLineService {

    @Override
    public List<PerfTemplateIndsLine> findTempateIndsLineByParam(PerfTemplateIndsLine templateIndsLine) {
        List<PerfTemplateIndsLine> templateIndsLineList = new ArrayList<>();
        try{
            templateIndsLineList = getBaseMapper().findTempateIndsLineByParam(templateIndsLine);
        }catch (Exception e){
            log.error("根据条件获取绩效模型指标行表集合时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return templateIndsLineList;
    }
}
