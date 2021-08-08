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
public class StartOrExtendBidingVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long bidingId;//招标id
    private Date endTime;//投标结束时间
    private String extendReason;//延长原因

}
