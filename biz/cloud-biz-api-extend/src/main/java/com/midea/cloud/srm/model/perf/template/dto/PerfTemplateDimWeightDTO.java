package com.midea.cloud.srm.model.perf.template.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateDimWeight;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 * 绩效模型指标信息表(包括绩效模型指标和行表信息)
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/1 15:06
 *  修改内容:
 * </pre>
 */
@Data
public class PerfTemplateDimWeightDTO extends BaseDTO {
    private static final long serialVersionUID = 169886287825866926L;

    /**绩效模型指标维度表*/
    private PerfTemplateDimWeight perfTemplateDimWeight;

    /**绩效模型指标信息表(包括绩效模型指标和行表信息)*/
    private List<PerfTemplateLineDTO> perfTemplateLineList;
}
