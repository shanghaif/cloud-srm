package com.midea.cloud.srm.base.questionairesurvey.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.base.StatusCodeEnum;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveyHeaderService;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveyQuestionService;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyHeader;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;


/**
* <pre>
 *  问卷调查 控制层
 * </pre>
*
* @author yancj@1.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 14, 2021 5:07:37 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/base/surveyheader")
public class SurveyHeaderController extends BaseController {



    @Autowired
    private SurveyQuestionService surveyQuestionService;

    @Autowired
    private SurveyHeaderService surveyHeaderService;


    /**
     * 分页查询
     * @param surveyHeader
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<SurveyHeader> listPage(@RequestBody SurveyHeader surveyHeader) {
        return surveyHeaderService.listPage(surveyHeader);
    }

    /**
    * 获取头行详情
    * @param id
    */
    @GetMapping("/get")
    public SurveyHeader get(Long id) {
        Assert.notNull(id, "id不能为空");
        return surveyHeaderService.getDetailById(id);
    }

    /**
    * 新增头行详情
    * @param surveyHeader
    */
    @PostMapping("/addOrUpdate")
    public Long add(@RequestBody SurveyHeader surveyHeader) {
        surveyHeader.setStatusCode(StatusCodeEnum.DRAFT.getValue());
        return  surveyHeaderService.addOrUpdate(surveyHeader);
    }

    
    /**
    * 删除  还需要修改
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        surveyHeaderService.delteSurveyHeader(id);
    }

    /**
     * 发布
     */
    @PostMapping("/submitted")
    public void submitted(@RequestBody SurveyHeader surveyHeader) {
        surveyHeader.setStatusCode(StatusCodeEnum.PUBLISHED.getValue());
        surveyHeader.setPublishDate(new Date());
        surveyHeaderService.addOrUpdate(surveyHeader);
    }

    /**
     * 导入问卷调查问题文件模板
     * @param response
     */
    @RequestMapping("/exportSurveyQuestionExcelTemplate")
    public void exportSurveyQuestionExcelTemplate(HttpServletResponse response)throws IOException{
        surveyQuestionService.exportSurveyQuestionExcelTemplate(response);
    }

    /**
     * 导入问题内容并返回数据给前端
     */
    @RequestMapping("/importSurveyQuestionExcel")
    public Map<String, Object> importSurveyQuestionExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return surveyQuestionService.importSurveyQuestionExcel(file,fileupload);
    }

}
