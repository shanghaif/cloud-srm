package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo;

import lombok.Data;

/**
 * <pre>
 *  智能推荐 视图模型
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
public class IntelligentRecommendVO {

    private static final long serialVersionUID = 1L;

    private Long vendorId;//供应商ID
    private String vendorCode;//供应商编号
    private String vendorName;//供应商名称

}
