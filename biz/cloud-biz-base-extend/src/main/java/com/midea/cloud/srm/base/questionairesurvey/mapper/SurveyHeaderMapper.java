package com.midea.cloud.srm.base.questionairesurvey.mapper;

import com.midea.cloud.srm.model.base.questionairesurvey.dto.HeaderScopeVendorDto;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyHeader;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 问卷调查 Mapper 接口
 * </p>
 *
 * @author yancj@1.com
 * @since Apr 14, 2021 5:07:37 PM
 */
public interface SurveyHeaderMapper extends BaseMapper<SurveyHeader> {
    List<HeaderScopeVendorDto> queryHeaderList(HeaderScopeVendorDto headerScopeVendorDto);
}