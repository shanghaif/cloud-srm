package com.midea.cloud.srm.perf.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateIndsLine;

import java.util.List;

/**
*  <pre>
 *  绩效模型指标信息行表 服务类
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
public interface IPerfTemplateIndsLineService extends IService<PerfTemplateIndsLine> {

    /**
     * Description 根据条件获取绩效模型指标行表集合
     * @Param templateLine 绩效模型指标行表实体
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.12
     **/
    List<PerfTemplateIndsLine> findTempateIndsLineByParam(PerfTemplateIndsLine templateIndsLine);
}
