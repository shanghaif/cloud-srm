package com.midea.cloud.srm.base.questionairesurvey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SurveyQuestionDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SurveyResultDto;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <pre>
 *  问卷调查 服务类
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 17, 2021 5:30:44 PM
 *  修改内容:
 * </pre>
*/
public interface SurveyResultService extends IService<SurveyResult>{
    List<SurveyQuestionDTO> queryFeedbackChartResult(Long id);
    /**
     * 保存问卷内容
     * @param surveyResultDtoList
     */
    void saveQuestionnaireInfo(List<SurveyResultDto> surveyResultDtoList);

    /**
     * 导出统计结果
     * @param surveyId
     * @param response
     * @throws IOException
     */
    void resultExport(Long surveyId, HttpServletResponse response)throws IOException;
}
