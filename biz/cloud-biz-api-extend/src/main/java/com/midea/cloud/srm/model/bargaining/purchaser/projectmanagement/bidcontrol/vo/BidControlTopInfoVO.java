package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidcontrol.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 投标控制页 视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-9 15:25
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class BidControlTopInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer currentRoundSupplierCount;//本轮需投标的供应商数量
    private Integer submitSupplierCount;//已提交投标供应商数量
    private Date endTime;//本轮投标截止时间
    private String extendReason; // 调整截止时间原因

}
