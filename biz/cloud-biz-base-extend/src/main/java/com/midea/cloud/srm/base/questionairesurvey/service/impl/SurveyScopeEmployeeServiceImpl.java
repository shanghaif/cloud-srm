package com.midea.cloud.srm.base.questionairesurvey.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;


import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.questionairesurvey.mapper.SurveyScopeEmployeeMapper;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveyScopeEmployeeService;


import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeEmployee;


import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveySelection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
/**
* <pre>
 *  问卷调查 服务实现类
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 18, 2021 3:02:32 PM
 *  修改内容:
 * </pre>
*/
@Service
public class SurveyScopeEmployeeServiceImpl extends ServiceImpl<SurveyScopeEmployeeMapper, SurveyScopeEmployee> implements SurveyScopeEmployeeService {


}
