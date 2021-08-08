package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo;

import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.*;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.ScoreRule;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * SourceForm
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SourceForm implements Serializable {

    private Biding bidding;        // 寻源基本信息

    private Collection<BidFile>             bidFiles;       // 招标附件
    private Collection<BidFileConfig>       bidFileConfigs; // 供方必须上传附件配置表
    private Collection<Group>               bidGroups;      // 工作小组

    private BidRequirement demandHeader;   // 寻源需求[头]
    private Collection<BidRequirementLine>  demandLines;    // 寻源需求[行]

    private Collection<BidVendor>           bidVendors;     // 寻源关联供应商

    private ScoreRule scoreRule;      // 评分规则[头]
    private Collection<ScoreRuleLine>       scoreRuleLines; // 评分规则[行]
}
