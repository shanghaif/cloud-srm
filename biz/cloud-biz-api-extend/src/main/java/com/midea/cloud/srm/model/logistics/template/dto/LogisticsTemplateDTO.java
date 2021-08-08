package com.midea.cloud.srm.model.logistics.template.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.logistics.template.entity.LogisticsTemplateHead;
import com.midea.cloud.srm.model.logistics.template.entity.LogisticsTemplateLine;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  物流模板头行DTO
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/27 17:22
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class LogisticsTemplateDTO extends BaseDTO {

    /**
     * 物流模板头
     */
    private LogisticsTemplateHead templateHead;

    /**
     * 物流申请行
     */
    private List<LogisticsTemplateLine> templateLines;

}
