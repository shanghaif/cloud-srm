package com.midea.cloud.srm.inq.scoremodel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.scoremodel.entity.ScoreRuleModelItem;

import java.util.List;

/**
 * <pre>
 *  评分规则明细表 服务类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-19 19:21:24
 *  修改内容:
 * </pre>
 */
public interface IScoreRuleModelItemService extends IService<ScoreRuleModelItem> {
    List<ScoreRuleModelItem> getByHeadId(Long inquiryId);
}
