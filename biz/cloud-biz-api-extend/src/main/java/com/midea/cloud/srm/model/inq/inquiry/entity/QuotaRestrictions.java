package com.midea.cloud.srm.model.inq.inquiry.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  配额-配额上下限表 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:41:44
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_inquiry_quota_restrictions")
public class QuotaRestrictions extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配额上下限ID
     */
    @TableId("QUOTA_RESTRICTIONS_ID")
    private Long quotaRestrictionsId;

    /**
     * 配额ID
     */
    @TableField("QUOTA_ID")
    private Long quotaId;

    /**
     * 限额原因
     */
    @TableField("RESTRICTIONS_TYPE")
    private String restrictionsType;

    /**
     * 运算符
     */
    @TableField("SYMBOL_TYPE")
    private String symbolType;

    /**
     * 比例
     */
    @TableField("PROPORTION")
    private Integer proportion;


}
