package com.midea.cloud.srm.perf.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateLineDTO;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateLine;

import java.util.List;

/**
 *  <pre>
 *  绩效模型指标信息表 服务类
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
public interface IPerfTemplateLineService extends IService<PerfTemplateLine> {

    /**
     * Description 根据绩效模型指标ID或指标库行ID获取绩效指标详情信息
     * @Param indicatorHeaderId 指标库行ID
     * @Param perfTemplateLineId 绩效模型指标ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.01
     * @throws BaseException
     **/
    PerfTemplateLineDTO findPerfTemplateLineAndIndsLine(Long indicatorHeaderId, Long perfTemplateLineId) throws BaseException;

    /**
     * Description 根据条件获取绩效模型指标表集合
     * @Param templateLine 绩效模型指标表实体
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.12
     * @throws BaseException
     **/
    List<PerfTemplateLine> findTempateLineByParam(PerfTemplateLine templateLine);
}
