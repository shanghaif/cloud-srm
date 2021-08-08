package com.midea.cloud.srm.model.perf.scoring.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsMan;
import com.midea.cloud.srm.model.perf.scoring.PerfScoreManScoring;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/1/18 22:22
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class PerfScoreManScoringSaveDTO extends BaseDTO {

    /**
     * 绩效评分项目表id
     */
    private Long scoreItemsId;

    /**
     * 绩效模型头表ID
     */
    private Long templateHeadId;

    /**
     * 评分人
     */
    private PerfScoreItemsMan perfScoreItemsMan;

    /**
     * 评分人绩效评分 分配list
     */
    private List<PerfScoreManScoring> perfScoreManScorings;

}
