package com.midea.cloud.srm.model.perf.scoring.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  评分人绩效评分 提交结果DTO
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/2/4 11:20
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ScoreManScoringV1SubmitConfirmDTO extends BaseDTO {

    /**
     * 未评分 数据列表
     */
    private List<String> notScoredList;

    /**
     * 项目 分组数据集合
     */


}
