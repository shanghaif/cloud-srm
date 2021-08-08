package com.midea.cloud.srm.base.datatransfer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *   模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-31 11:01:28
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_ou_group_bid_temp")
public class DataTransferBaseOuGroupBidTemp extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("bid_id")
    private Long bidId;

    @TableField("bid_num")
    private String bidNum;

    @TableField("org_erp_id")
    private Integer orgErpId;

    @TableField("org_erp_code")
    private String orgErpCode;

    @TableField("org_erp_name")
    private String orgErpName;

    @TableField("inv_erp_id")
    private Integer invErpId;

    @TableField("inv_erp_code")
    private String invErpCode;

    @TableField("inv_erp_name")
    private String invErpName;


}
