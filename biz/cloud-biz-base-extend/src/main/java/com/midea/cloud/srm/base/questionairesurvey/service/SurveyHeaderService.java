package com.midea.cloud.srm.base.questionairesurvey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.HeaderScopeVendorDto;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SurveyQuestionDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyHeader;

import java.io.IOException;
import java.util.List;

/**
* <pre>
 *  测试 服务类
 * </pre>
*
* @author linsb@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 29, 2021 10:18:45 AM
 *  修改内容:
 * </pre>
*/
public interface SurveyHeaderService extends IService<SurveyHeader>{
    /**
     * 保存或更新
     * @param surveyHeader
     * @return
     */
    Long addOrUpdate(SurveyHeader surveyHeader);

    /**
     * 获取详情
     * @param id
     * @return
     */
    SurveyHeader getDetailById(Long id);

    /**
     * 删除问卷单据
     */
    void delteSurveyHeader(Long id);

    /*
   分页查询
    */
    PageInfo<SurveyHeader> listPage(SurveyHeader surveyHeader);
    /**
     * 分页查询
     * @param headerScopeVendorDto
     * @return
     */
    PageInfo<HeaderScopeVendorDto> supplierListPage(HeaderScopeVendorDto headerScopeVendorDto);
}
