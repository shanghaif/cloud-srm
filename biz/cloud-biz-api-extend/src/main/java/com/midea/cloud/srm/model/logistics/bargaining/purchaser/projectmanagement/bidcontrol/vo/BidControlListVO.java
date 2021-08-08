package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.bidcontrol.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 投标控制页列表 视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-9 14:32
 *  修改内容:
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class BidControlListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer round;//轮次
    private String vendorCode;//供应商编号
    private String vendorName;//供应商名称
    private String linkManName;//联系人
    private String phone;//电话
    private String email;//邮箱
    private String orderStatus;//投标状态
    private String createdBy;//最后更新人(提交人)
    private String lastUpdatedBy;//最后更新人(提交人)
    private Date lastUpdateDate;//最后更新时间(提交时间)

    private Long    bidVendorId;    // 投标供应商ID
    private String  isProxyBidding; // 是否代理报价（Y / N）
}
