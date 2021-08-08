package com.midea.cloud.srm.perf.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateDimWeightDTO;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateDimWeight;

import java.util.List;

/**
 *  <pre>
 *  绩效模型指标维度表 服务类
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
public interface IPerfTemplateDimWeightService extends IService<PerfTemplateDimWeight> {

    /**
     * Description 根据条件获取绩效指标维度和指标信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.01
     * @throws BaseException
     **/
    List<PerfTemplateDimWeightDTO> findPerTemplateDimWeightAndLine(PerfTemplateDimWeight perfTemplateDimWeight) throws BaseException;

    /**
     * Description
     * @Param dimWeightId 根据绩效模型维度Id删除记录(包括绩效维度、绩效指标和绩效指标行信息)
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.02
     * @throws BaseException
     **/
    String delPefTemplateDimWeight(Long dimWeightId) throws BaseException;

    /**
     * Description 根据条件获取绩效指标维度(如果有绩效指标和绩效指标行信息就查询)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.12
     * @throws BaseException
     **/
    List<PerfTemplateDimWeight> findPerTemplateDimWeightByParam(PerfTemplateDimWeight templateDimWeight);

}
