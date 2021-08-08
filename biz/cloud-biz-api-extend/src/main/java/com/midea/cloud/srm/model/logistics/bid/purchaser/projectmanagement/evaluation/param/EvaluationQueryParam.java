package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.param;

import com.midea.cloud.srm.model.common.BasePage;
import lombok.*;

/**
 * <pre>
 * 评选列表查询参数
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月26日 下午7:49:57
 *  修改内容:
 *          </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class EvaluationQueryParam extends BasePage {

    private static final long serialVersionUID = -2797062020772459505L;

    private Long bidingId;// 招标单ID
    private String targetNum;// 标的编码
    private String targetDesc;// 标的名称
    private String orgName;// 采购组织
    private String vendorName;// 供应商名称
    private String selectionStatus;// 评选状态
    private Integer round;// 显示轮次
    private String itemGroup;// 组合
    //是否为最后一轮
    private String lastRound;

    private String  isAllowNextRound;   // 是否允许进入下一轮
    private Long    requirementLineId;  // 寻源需求行ID

    private Integer rank;
    //去除排名
    private boolean filterQuit=true;
}
