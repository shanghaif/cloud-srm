package com.midea.cloud.srm.model.perf.template.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateHeader;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateCategory;
import lombok.Data;

import java.util.List;

/**
 *  <pre>
 *  绩效模型头表和采购分类表DTO
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-28 16:41:07
 *  修改内容:
 * </pre>
 */
@Data
public class PerfTemplateDTO extends BaseDTO {
    private static final long serialVersionUID = -3476446004755570746L;

    /**绩效模型头对象*/
    private PerfTemplateHeader perfTemplateHeader;

    /**绩效模型采购分类表 对象集合*/
    private List<PerfTemplateCategory> perfTemplateCategoryList;

    /**绩效模型指标维度DTO(包括绩效模型指标维度和绩效指标信息)对象*/
    private List<PerfTemplateDimWeightDTO> perfTemplateDimWeightList;


}
