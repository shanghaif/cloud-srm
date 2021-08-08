package com.midea.cloud.srm.base.questionairesurvey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyQuestion;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveySelection;
import java.util.List;
import java.io.IOException;

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
 *  修改日期: Apr 15, 2021 2:47:16 PM
 *  修改内容:
 * </pre>
*/
public interface SurveySelectionService extends IService<SurveySelection>{
    /*
    批量更新或者批量新增
    */
     void batchSaveOrUpdate(List<SurveySelection> surveySelectionList) throws IOException;

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;
    /*
   分页查询
    */
    PageInfo<SurveySelection> listPage(SurveySelection surveySelection);

    void batchDeleteByQuestionId(List<Long> ids);

    List<SurveySelection> getSelectionByQuestionId(List<Long> ids);
}
