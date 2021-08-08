package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.param;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  查询智能推荐列表 入参
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-29 14:39:33
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class IntelligentRecommendParam {

    private static final long serialVersionUID = 1L;

    private Long orgId; // 采购组织ID
    private String fullPathId; // 组织全路径虚拟ID
    private List<Long> categoryIdList; // 招标需求行采购分类ID列表

}
