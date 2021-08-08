package com.midea.cloud.srm.base.questionairesurvey.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveyHeaderService;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.HeaderScopeVendorDto;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 *  修改日期: 2021/4/15 15:30
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/basesupplier/surveyheadersupplier")
public class SurveyHeaderSupplierController extends BaseController {

    @Autowired
    private SurveyHeaderService surveyHeaderService;


    /**
     * 问卷调查-分页查询
     * @param headerScopeVendorDto
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<HeaderScopeVendorDto> listPage(@RequestBody HeaderScopeVendorDto headerScopeVendorDto){
        //headerScopeVendorDto.setVendorCode("C005666");
        return surveyHeaderService.supplierListPage(headerScopeVendorDto);
    }


}
