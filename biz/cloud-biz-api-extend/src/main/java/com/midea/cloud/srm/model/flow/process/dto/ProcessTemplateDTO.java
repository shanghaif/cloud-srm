package com.midea.cloud.srm.model.flow.process.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.flow.process.entity.TemplateHeader;
import com.midea.cloud.srm.model.flow.process.entity.TemplateLines;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  流程模板DTO(包括工作流头和工作流行实体类)
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 13:54
 *  修改内容:
 * </pre>
 * */
public class ProcessTemplateDTO extends BaseDTO {
    private static final long serialVersionUID = -3367603520727980467L;

    /**工作流头模板*/
    @Setter @Getter
    private TemplateHeader templateHeader;

    /** 工作流行表模板集合*/
    @Setter @Getter
    private List<TemplateLines> templateLinesList;
}
