package com.midea.cloud.srm.inq.scoremodel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.scoremodel.dto.ScoreRuleModelDto;
import com.midea.cloud.srm.model.inq.scoremodel.entity.ScoreRuleModel;

/**
 * <pre>
 *  询价评分规则模板表 服务类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-19 19:21:23
 *  修改内容:
 * </pre>
 */
public interface IScoreRuleModelService extends IService<ScoreRuleModel> {

    ScoreRuleModelDto getHeadById(Long modelId);

    ScoreRuleModelDto getModelByCode(String  modelCode);

    void saveAndUpdate(ScoreRuleModelDto dto);
}
