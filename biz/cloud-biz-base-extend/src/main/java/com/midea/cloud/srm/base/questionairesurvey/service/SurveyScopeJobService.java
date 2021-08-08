package com.midea.cloud.srm.base.questionairesurvey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeJob;
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
 *  修改日期: Apr 18, 2021 2:57:24 PM
 *  修改内容:
 * </pre>
*/
public interface SurveyScopeJobService extends IService<SurveyScopeJob>{


    List<SurveyScopeJob> getJobByQuestionId(List<Long> ids);
}
