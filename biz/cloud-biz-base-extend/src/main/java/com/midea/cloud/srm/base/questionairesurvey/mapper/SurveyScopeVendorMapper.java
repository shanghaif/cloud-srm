package com.midea.cloud.srm.base.questionairesurvey.mapper;

import com.midea.cloud.srm.model.base.questionairesurvey.dto.ExcelFeedBackResultDto;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.FeedbackCountDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.FeedbackSupplierDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeVendor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 问卷调查 Mapper 接口
 * </p>
 *
 * @author yancj9@meicloud.com
 * @since Apr 16, 2021 9:08:51 AM
 */
public interface SurveyScopeVendorMapper extends BaseMapper<SurveyScopeVendor> {

    List<FeedbackSupplierDTO> listFeedbackVendor(@Param("feedbackSupplierDTO") FeedbackSupplierDTO feedbackSupplierDTO);

    FeedbackCountDTO queryFeedbackResult(@Param("id") Long id);

    List<ExcelFeedBackResultDto> queryVendorScopeFeedBack(@Param("surveyId")Long surveyId);
}