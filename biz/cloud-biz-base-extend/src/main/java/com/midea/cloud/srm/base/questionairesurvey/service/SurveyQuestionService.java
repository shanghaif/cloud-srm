package com.midea.cloud.srm.base.questionairesurvey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.ExcelSurveyQuestionDto;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SurveyQuestionDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SurveyQuestionSupplierDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyHeader;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyQuestion;
import java.util.List;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;


/**
* <pre>
 *  问卷调查 服务类
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
public interface SurveyQuestionService extends IService<SurveyQuestion>{
    /*
    批量更新或者批量新增
    */
     void batchSaveOrUpdate(List<SurveyQuestion> surveyQuestionList) throws IOException;

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;

    /*
    导出excel模板文件
    */
    public void exportExcelTemplate(HttpServletResponse response) throws IOException;

    /*
    导入excel 内容
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    /*
     导出excel内容数据
     param ： 传入参数
     */
    void exportExcel(SurveyQuestion excelParam, HttpServletResponse response)throws IOException;

    /*
   分页查询
    */
    PageInfo<SurveyQuestion> listPage(SurveyQuestion surveyQuestion);

    List<Long> getSurveyQuestionIds(Long surveyHeaderId);
    /**
     * 查找问卷问题相关信息
     * @param id
     * @return
     */
    List<SurveyQuestionSupplierDTO> questionSurveyInfo(Long id,Long vendorScopeId);

    /**
     * 导入问卷调查问题文件模板
     * @param response
     */
    void exportSurveyQuestionExcelTemplate(HttpServletResponse response) throws IOException;

    /**
     * 导入问卷调查问题内容
     * @param file
     * @param fileupload
     * @return
     */
    Map<String, Object> importSurveyQuestionExcel(MultipartFile file, Fileupload fileupload);
}
