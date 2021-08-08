package com.midea.cloud.srm.model.perf.template.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateIndsLine;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateLine;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  绩效模型指标信息表和行表对象
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/1 13:54
 *  修改内容:
 * </pre>
 */
@Data
public class PerfTemplateLineDTO extends BaseDTO {

    private static final long serialVersionUID = 1682847765612344057L;
    /**绩效模型指标信息表对象*/
    private PerfTemplateLine PerfTemplateLine;
    /**绩效模型指标信息行表对象集合*/
    private List<PerfTemplateIndsLine> perfTemplateIndsLineList;
}
