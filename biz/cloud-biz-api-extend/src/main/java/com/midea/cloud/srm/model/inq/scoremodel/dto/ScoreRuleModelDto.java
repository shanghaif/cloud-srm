package com.midea.cloud.srm.model.inq.scoremodel.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.inq.scoremodel.entity.ScoreRuleModel;
import com.midea.cloud.srm.model.inq.scoremodel.entity.ScoreRuleModelItem;
import lombok.Data;

import java.util.List;

/**
 * 创建人 : tangyuan2@meicloud.com
 * 创建日期 : 2019-12-17
 * 版本 : 1.0
 * 功能描述 :
 * 修改记录
 * 修改后版本：  修改人：    修改时间：   修改内容：
 **/
@Data
public class ScoreRuleModelDto extends BaseDTO {
    private ScoreRuleModel model;
    private List<ScoreRuleModelItem> modelItems;
}
