package com.midea.cloud.srm.model.logistics.bid.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
*  <pre>
 *  供应商报价头表 模型
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-06 09:08:34
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LgtVendorQuotedDto extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 当前轮次
     */
    private Integer round;

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商编号
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 投标状态   字典:BIDDING_ORDER_STATES
     */
    private String status;
}
