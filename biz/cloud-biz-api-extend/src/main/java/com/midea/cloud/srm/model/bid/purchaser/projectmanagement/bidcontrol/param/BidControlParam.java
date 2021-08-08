package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidcontrol.param;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class BidControlParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long bidingId;//招标id
    private Integer pageNum;//页码
    private Integer pageSize;//页记录条数

}
