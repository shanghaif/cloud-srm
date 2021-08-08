package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidingresult.param;

import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  招标结果 入参对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-17 9:05
 *  修改内容:
 * </pre>
 */
@Data
public class BidResultParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long bidingId;
    private Integer pageNum;
    private Integer pageSize;
}
