package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveySelection;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author liuzh163@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/4/17 1:40
 *  修改内容:
 * </pre>
 */
@Data
public class QuestionDetailDto extends BaseDTO {
    private List<SurveyQuestionSupplierDTO> surveyQuestionSupplierDTOList;
    private SurveyScopeVendorSupplierDto surveyScopeVendorSupplierDto;
}
