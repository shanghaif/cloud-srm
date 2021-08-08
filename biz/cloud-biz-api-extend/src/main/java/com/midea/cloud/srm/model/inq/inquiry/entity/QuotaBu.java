package com.midea.cloud.srm.model.inq.inquiry.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  配额事业部中间表 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:40:36
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_inquiry_quota_bu")
public class QuotaBu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配额事业部中间表ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 配额ID
     */
    @TableField("QUOTA_ID")
    private Long quotaId;

    /**
     * 事业部名称
     */
    @TableField("BU_CODE")
    private String buCode;

    /**
     * 事业部名称
     */
    @TableField("BU_NAME")
    private String buName;

}
